package org.wheatgenetics.coordinate.gi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parses a CSV file with headers "value", "row", "column" (case-insensitive)
 * and creates an imported grid in the database.
 *
 * The resulting grid uses a sentinel template with TemplateType.IMPORTED (code=4).
 * The grid name (from the filename) is stored in the grid's options JSON as an
 * optional field named "Grid Name".
 */
public class ImportedGridImporter {

    public interface Handler {
        void onImportSuccess(long gridId);
        void onImportError(String message);
    }

    private final Context context;
    private final Handler handler;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final android.os.Handler mainHandler = new android.os.Handler(Looper.getMainLooper());

    public ImportedGridImporter(@NonNull final Context context,
                                @NonNull final Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void importFromUri(@NonNull final Uri uri, @NonNull final String filename) {
        executor.execute(() -> {
            try {
                final InputStream stream = context.getContentResolver().openInputStream(uri);
                if (stream == null) {
                    deliverError(context.getString(R.string.ImportedGridErrorOpenFile));
                    return;
                }
                final long gridId = parseAndInsert(stream, filename);
                mainHandler.post(() -> handler.onImportSuccess(gridId));
            } catch (Exception e) {
                deliverError(e.getMessage() != null
                        ? e.getMessage()
                        : context.getString(R.string.ImportedGridErrorParse));
            }
        });
    }

    private void deliverError(final String message) {
        mainHandler.post(() -> handler.onImportError(message));
    }

    /**
     * Parses the CSV and inserts the template, grid, and entries into the database.
     * @return the id of the newly created grid
     */
    private long parseAndInsert(@NonNull final InputStream stream,
                                @NonNull final String gridName) throws Exception {
        final List<String[]> rows;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rows.add(splitCsv(line));
                }
            }
        }

        if (rows.size() < 2) {
            throw new IllegalArgumentException(
                    context.getString(R.string.ImportedGridErrorNoData));
        }

        // Parse header row (case-insensitive)
        final String[] header = rows.get(0);
        int valueIdx = -1, rowIdx = -1, colIdx = -1;
        for (int i = 0; i < header.length; i++) {
            switch (header[i].trim().toLowerCase()) {
                case "value":  valueIdx = i; break;
                case "row":    rowIdx   = i; break;
                case "column": colIdx   = i; break;
            }
        }
        if (valueIdx < 0 || rowIdx < 0 || colIdx < 0) {
            throw new IllegalArgumentException(
                    context.getString(R.string.ImportedGridErrorMissingHeaders));
        }

        // Collect distinct row and column labels from data rows
        final List<String> rowLabels = new ArrayList<>();
        final List<String> colLabels = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            final String[] cells = rows.get(i);
            if (cells.length > Math.max(rowIdx, Math.max(colIdx, valueIdx))) {
                final String rl = cells[rowIdx].trim();
                final String cl = cells[colIdx].trim();
                if (!rowLabels.contains(rl)) rowLabels.add(rl);
                if (!colLabels.contains(cl)) colLabels.add(cl);
            }
        }

        // Auto-detect ordering: if every label parses as an integer → numeric, else alphabetic
        final Map<String, Integer> rowLabelToIndex = buildLabelMap(rowLabels);
        final Map<String, Integer> colLabelToIndex = buildLabelMap(colLabels);

        final int maxRow = Collections.max(rowLabelToIndex.values());
        final int maxCol = Collections.max(colLabelToIndex.values());

        // Build sorted label arrays (index 0 = label for row/col 1, etc.)
        final String[] sortedRowLabels = new String[maxRow];
        for (Map.Entry<String, Integer> e : rowLabelToIndex.entrySet()) {
            sortedRowLabels[e.getValue() - 1] = e.getKey();
        }
        final String[] sortedColLabels = new String[maxCol];
        for (Map.Entry<String, Integer> e : colLabelToIndex.entrySet()) {
            sortedColLabels[e.getValue() - 1] = e.getKey();
        }

        // Encode as JSON for storage in template entryLabel field
        final String labelsJson = buildLabelsJson(sortedRowLabels, sortedColLabels);

        // Insert sentinel template
        final SQLiteDatabase db = Database.db(context);
        final long now = System.currentTimeMillis();

        final ContentValues templateCv = new ContentValues();
        templateCv.put("title", "Imported");
        templateCv.put("type", 4); // TemplateType.IMPORTED
        templateCv.put("rows", maxRow);
        templateCv.put("cols", maxCol);
        templateCv.put("erand", 0);
        templateCv.putNull("ecells");
        templateCv.putNull("erows");
        templateCv.putNull("ecols");
        templateCv.put("cnumb", 1); // numeric
        templateCv.put("rnumb", 1); // numeric
        templateCv.put("entryLabel", labelsJson); // stores sorted row/col label arrays
        templateCv.putNull("options");
        templateCv.put("stamp", now);
        final long templateId = db.insert("templates", null, templateCv);
        if (templateId < 0) {
            throw new IllegalStateException(
                    context.getString(R.string.ImportedGridErrorInsertTemplate));
        }

        // Build options JSON for the grid: stores the grid name
        final String gridNameFieldName = context.getString(R.string.ImportedGridNameFieldName);
        final String optionsJson = "[{\"field\":" + jsonString(gridNameFieldName)
                + ",\"hint\":\"\",\"value\":" + jsonString(gridName)
                + ",\"checked\":true}]";

        // Insert grid row
        final ContentValues gridCv = new ContentValues();
        gridCv.put("temp", templateId);
        gridCv.putNull("projectId");
        gridCv.putNull("person");
        gridCv.put("activeRow", 0);
        gridCv.put("activeCol", 0);
        gridCv.put("options", optionsJson);
        gridCv.put("stamp", now);
        final long gridId = db.insert("grids", null, gridCv);
        if (gridId < 0) {
            throw new IllegalStateException(
                    context.getString(R.string.ImportedGridErrorInsertGrid));
        }

        // Insert entries
        for (int i = 1; i < rows.size(); i++) {
            final String[] cells = rows.get(i);
            if (cells.length <= Math.max(rowIdx, Math.max(colIdx, valueIdx))) continue;
            final String rowLabel = cells[rowIdx].trim();
            final String colLabel = cells[colIdx].trim();
            final String value    = cells[valueIdx].trim();
            final Integer rowNum  = rowLabelToIndex.get(rowLabel);
            final Integer colNum  = colLabelToIndex.get(colLabel);
            if (rowNum == null || colNum == null) continue;

            final ContentValues entryCv = new ContentValues();
            entryCv.put("grid", gridId);
            entryCv.put("row", rowNum);
            entryCv.put("col", colNum);
            entryCv.put("edata", value);
            entryCv.put("stamp", now);
            entryCv.put("original_value", value);
            entryCv.putNull("confirmed_timestamp");
            db.insert("entries", null, entryCv);
        }

        return gridId;
    }

    /**
     * Builds a label→1-based-index map.
     * If all labels parse as integers: sort numerically, map original label → sorted position.
     * Otherwise: sort alphabetically, map original label → sorted position.
     */
    @NonNull
    private Map<String, Integer> buildLabelMap(@NonNull final List<String> labels) {
        final boolean allNumeric = allParseAsInt(labels);
        final List<String> sorted = new ArrayList<>(labels);
        if (allNumeric) {
            sorted.sort((a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
        } else {
            Collections.sort(sorted);
        }
        final Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            map.put(sorted.get(i), i + 1);
        }
        // Map original labels to positions (handle duplicates by using the sorted position)
        final Map<String, Integer> result = new LinkedHashMap<>();
        for (final String label : labels) {
            result.put(label, map.get(label));
        }
        return result;
    }

    private boolean allParseAsInt(@NonNull final List<String> labels) {
        for (final String label : labels) {
            try {
                Integer.parseInt(label);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Minimal CSV line splitter (handles commas; does not handle quoted fields with embedded commas).
     */
    @NonNull
    private String[] splitCsv(@NonNull final String line) {
        // Handle quoted fields
        final List<String> fields = new ArrayList<>();
        final StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            final char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    /** Escapes a string for JSON. */
    @NonNull
    private String jsonString(@NonNull final String s) {
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    /**
     * Builds a compact JSON string storing sorted row and col label arrays.
     * Format: {"r":["A","B"],"c":["1","2","3"]}
     * Stored in the template's entryLabel field for retrieval during display and export.
     */
    @NonNull
    private String buildLabelsJson(@NonNull final String[] rowLabels,
                                   @NonNull final String[] colLabels) {
        final StringBuilder sb = new StringBuilder("{\"r\":[");
        for (int i = 0; i < rowLabels.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(jsonString(rowLabels[i] != null ? rowLabels[i] : String.valueOf(i + 1)));
        }
        sb.append("],\"c\":[");
        for (int i = 0; i < colLabels.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(jsonString(colLabels[i] != null ? colLabels[i] : String.valueOf(i + 1)));
        }
        sb.append("]}");
        return sb.toString();
    }
}
