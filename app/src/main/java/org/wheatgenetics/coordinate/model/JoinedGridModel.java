package org.wheatgenetics.coordinate.model;

import android.os.Build;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.exporter.CsvWriter;
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class JoinedGridModel extends GridModel
        implements DisplayModel {
    // region Fields
    @NonNull
    private final TemplateModel
            templateModel;
    private EntryModels entryModels = null;

    /**
     * Used by GridCreator.
     */
    public JoinedGridModel(
            @IntRange(from = 0) final long projectId,
            final String person,
            @Nullable final
            NonNullOptionalFields optionalFields,
            @NonNull final StringGetter stringGetter,
            @NonNull final TemplateModel
                    templateModel) {
        super(
                /* templateId     => */ templateModel.getId(),
                /* projectId      => */ projectId,
                /* person         => */ person,
                /* optionalFields => */ optionalFields,
                /* stringGetter   => */ stringGetter);

        this.templateModel = templateModel;
    }
    // endregion

    /**
     * Used by GridsTable.
     */
    public JoinedGridModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 0) final long projectId,
            final String person,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable final String optionalFields,
            @NonNull final StringGetter stringGetter,
            @IntRange(from = 0) final long timestamp,

            @IntRange(from = 1) final long templateId,
            final String title,
            @IntRange(from = 0, to = 2) final int code,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @IntRange(from = 0) final int generatedExcludedCellsAmount,
            @Nullable final String initialExcludedCells,
            @Nullable final String excludedRows,
            @Nullable final String excludedCols,
            @IntRange(from = 0, to = 1) final int colNumbering,
            @IntRange(from = 0, to = 1) final int rowNumbering,
            final String entryLabel,
            @Nullable final String templateOptionalFields,
            @IntRange(from = 0) final long templateTimestamp,

            final EntryModels entryModels) {
        super(id, templateId, projectId, person, activeRow,
                activeCol, optionalFields, stringGetter, timestamp);

        this.templateModel = new TemplateModel(templateId,
                title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
                templateOptionalFields, stringGetter, templateTimestamp);
        this.entryModels = entryModels;
    }

    // region Private Methods
    @Nullable
    private EntryModel getEntryModel(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        // noinspection ConstantConditions
        return this.entryModelsIsNull() ? null : this.getEntryModels().get(row, col);
    }

    // region export*() Private Methods
    private void exportSeed(
            @NonNull final CsvWriter csvWriter,
            final String exportFileName,
            final boolean includeHeader) throws IOException {
        final String tray_idValue, personValue, timestampValue;
        {
            final NonNullOptionalFields optionalFields =
                    this.optionalFields();
            if (null == optionalFields)
                tray_idValue = personValue = timestampValue = null;
            else {
                // noinspection CStyleArrayDeclaration
                final String values[];
                {
                    final String trayName, personName, timestampName;
                    {
                        @NonNull final StringGetter stringGetter =
                                this.stringGetter();

                        trayName = stringGetter.get(R.string.NonNullOptionalFieldsTrayIDFieldName);
                        personName = stringGetter.get(R.string.NonNullOptionalFieldsSeedTrayPersonFieldName);
                        timestampName = stringGetter.get(R.string.JoinedGridModelSeedTrayTimestampFieldName);
                    }
                    values = optionalFields.values(
                            /* names[] => */ new String[]{trayName, personName, timestampName});
                }
                if (null == values)
                    tray_idValue = personValue = timestampValue = null;
                else {
                    tray_idValue = values[0];
                    personValue = values[1];
                    timestampValue = values[2];
                }
            }
        }

        if (includeHeader) csvWriter.writeRecord(new String[]{"tray_id", "cell_id",
                "tray_num", "tray_column", "tray_row", "seed_id", "person", "timestamp"});
        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();

            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 1) int row = 1; row <= rows; row++) {
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {
                        csvWriter.write(tray_idValue);    // tray id
                        csvWriter.write("%s_C%02d_R%d", exportFileName, col, row);    // cell_id
                        csvWriter.write();    // tray_num
                        csvWriter.write(col);    // tray_column
                        csvWriter.write(row);    // tray_row
                        csvWriter.write(entryModel.getSeedExportValue());    // seed_id
                        csvWriter.write(personValue);    // person
                        csvWriter.write(timestampValue);    // timestamp

                        csvWriter.endRecord();
                    }
                }
                //if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    // region export*() Private Methods
    private void exportSeed(
            @NonNull final OutputStream output,
            final String exportFileName,
            @Nullable final JoinedGridModel.Helper
                    helper,
            final boolean includeHeader) throws IOException {
        final String tray_idValue, personValue, timestampValue;
        {
            final NonNullOptionalFields optionalFields =
                    this.optionalFields();
            if (null == optionalFields)
                tray_idValue = personValue = timestampValue = null;
            else {
                // noinspection CStyleArrayDeclaration
                final String values[];
                {
                    final String trayName, personName, timestampName;
                    {
                        @NonNull final StringGetter stringGetter =
                                this.stringGetter();

                        trayName = stringGetter.get(R.string.NonNullOptionalFieldsTrayIDFieldName);
                        personName = stringGetter.get(R.string.NonNullOptionalFieldsSeedTrayPersonFieldName);
                        timestampName = stringGetter.get(R.string.JoinedGridModelSeedTrayTimestampFieldName);
                    }
                    values = optionalFields.values(
                            /* names[] => */ new String[]{trayName, personName, timestampName});
                }
                if (null == values)
                    tray_idValue = personValue = timestampValue = null;
                else {
                    tray_idValue = values[0];
                    personValue = values[1];
                    timestampValue = values[2];
                }
            }
        }

        if (includeHeader) {
            write(output, "tray_id, cell_id, tray_num, tray_column, tray_row, seed_id, person, timestamp", "\n");
        }

        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();

            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 1) int row = 1; row <= rows; row++) {
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {
                        write(output, tray_idValue, ",");
                        String cellId = String.format(Locale.getDefault(), "%s_C%02d_R%d", exportFileName, col, row);
                        write(output, cellId, ",");
                        write(output, ",", ",");
                        write(output, String.valueOf(col), ",");
                        write(output, String.valueOf(row), ",");
                        write(output, entryModel.getSeedExportValue(), ",");
                        write(output, personValue, ",");
                        write(output, timestampValue, "\n");
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        output.close();
    }

    private void exportDNA(
            @NonNull final CsvWriter csvWriter,
            final boolean includeHeader) throws IOException {
        final String timestampValue, plate_idValue, plate_nameValue,
                dna_personValue, notesValue, tissue_typeValue, extractionValue;
        {
            final NonNullOptionalFields optionalFields =
                    this.optionalFields();
            if (null == optionalFields)
                timestampValue = plate_idValue = plate_nameValue = dna_personValue =
                        notesValue = tissue_typeValue = extractionValue = null;
            else {
                // noinspection CStyleArrayDeclaration
                final String values[];
                {
                    final String timestampName, plate_idName, plate_nameName,
                            dna_personName, notesName, tissue_typeName, extractionName;
                    {
                        @NonNull final StringGetter stringGetter =
                                this.stringGetter();

                        timestampName = stringGetter.get(R.string.JoinedGridModelDNAPlateTimestampFieldName);
                        plate_idName = stringGetter.get(R.string.NonNullOptionalFieldsPlateIDFieldName);
                        plate_nameName = stringGetter.get(R.string.NonNullOptionalFieldsPlateNameFieldName);
                        dna_personName = stringGetter.get(R.string.NonNullOptionalFieldsDNAPlatePersonFieldName);
                        notesName = stringGetter.get(R.string.NonNullOptionalFieldsNotesFieldName);
                        tissue_typeName = stringGetter.get(R.string.NonNullOptionalFieldsTissueTypeFieldName);
                        extractionName = stringGetter.get(R.string.NonNullOptionalFieldsExtractionFieldName);
                    }
                    values = optionalFields.values(/* names[] => */ new String[]{
                            timestampName, plate_idName, plate_nameName, dna_personName,
                            notesName, tissue_typeName, extractionName});
                }
                if (null == values)
                    timestampValue = plate_idValue = plate_nameValue = dna_personValue =
                            notesValue = tissue_typeValue = extractionValue = null;
                else {
                    timestampValue = values[0];
                    plate_idValue = values[1];
                    plate_nameValue = values[2];
                    dna_personValue = values[3];
                    notesValue = values[4];
                    tissue_typeValue = values[5];
                    extractionValue = values[6];
                }
            }
        }

        if (includeHeader) csvWriter.writeRecord(new String[]{
                "timestamp", "plate_id", "plate_name", "sample_id", "well_A01", "well_01A",
                "tissue_id", "dna_person", "notes", "tissue_type", "extraction"});
        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();

            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 0) int r = 0; r < rows; r++) {
                    @IntRange(from = 1) final int row = r + 1;
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {
                        csvWriter.write(timestampValue);
                        csvWriter.write(plate_idValue);
                        csvWriter.write(plate_nameValue);
                        {
                            final String sample_id;
                            {
                                final String rowName =
                                        org.wheatgenetics.coordinate.Utils.convert(r);
                                final String colName = String.format(
                                        Locale.getDefault(), "%02d", col);

                                sample_id = String.format(
                                        "%s_%s%s", plate_idValue, rowName, colName);
                                csvWriter.write(sample_id);              // sample_id
                                csvWriter.write("%s%s", rowName, colName);              // well_A01
                                csvWriter.write("%s%s", colName, rowName);              // well_01A
                            }
                            csvWriter.write(entryModel.getDNAExportValue(sample_id));   // tissue_id
                        }
                        csvWriter.write(dna_personValue);
                        csvWriter.write(notesValue);
                        csvWriter.write(tissue_typeValue);
                        csvWriter.write(extractionValue);

                        csvWriter.endRecord();
                    }
                }
                //if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    private void exportDNA(
            @NonNull final OutputStream output,
            @Nullable final JoinedGridModel.Helper
                    helper,
            final boolean includeHeader) throws IOException {
        final String timestampValue, plate_idValue, plate_nameValue,
                dna_personValue, notesValue, tissue_typeValue, extractionValue;
        {
            final NonNullOptionalFields optionalFields =
                    this.optionalFields();
            if (null == optionalFields)
                timestampValue = plate_idValue = plate_nameValue = dna_personValue =
                        notesValue = tissue_typeValue = extractionValue = null;
            else {
                // noinspection CStyleArrayDeclaration
                final String values[];
                {
                    final String timestampName, plate_idName, plate_nameName,
                            dna_personName, notesName, tissue_typeName, extractionName;
                    {
                        @NonNull final StringGetter stringGetter =
                                this.stringGetter();

                        timestampName = stringGetter.get(R.string.JoinedGridModelDNAPlateTimestampFieldName);
                        plate_idName = stringGetter.get(R.string.NonNullOptionalFieldsPlateIDFieldName);
                        plate_nameName = stringGetter.get(R.string.NonNullOptionalFieldsPlateNameFieldName);
                        dna_personName = stringGetter.get(R.string.NonNullOptionalFieldsDNAPlatePersonFieldName);
                        notesName = stringGetter.get(R.string.NonNullOptionalFieldsNotesFieldName);
                        tissue_typeName = stringGetter.get(R.string.NonNullOptionalFieldsTissueTypeFieldName);
                        extractionName = stringGetter.get(R.string.NonNullOptionalFieldsExtractionFieldName);
                    }
                    values = optionalFields.values(/* names[] => */ new String[]{
                            timestampName, plate_idName, plate_nameName, dna_personName,
                            notesName, tissue_typeName, extractionName});
                }
                if (null == values)
                    timestampValue = plate_idValue = plate_nameValue = dna_personValue =
                            notesValue = tissue_typeValue = extractionValue = null;
                else {
                    timestampValue = values[0];
                    plate_idValue = values[1];
                    plate_nameValue = values[2];
                    dna_personValue = values[3];
                    notesValue = values[4];
                    tissue_typeValue = values[5];
                    extractionValue = values[6];
                }
            }
        }

        if (includeHeader) {
            write(output, "timestamp, plate_id, plate_name, sample_id, well_A01, well_01A, tissue_id, dna_person, notes, tissue_type, extraction", "\n");
        }

        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();

            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 0) int r = 0; r < rows; r++) {
                    @IntRange(from = 1) final int row = r + 1;
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {
                        write(output, timestampValue, ",");
                        write(output, plate_idValue, ",");
                        write(output, plate_nameValue, ",");
                        {
                            final String sample_id;
                            {
                                final String rowName =
                                        org.wheatgenetics.coordinate.Utils.convert(r);
                                final String colName = String.format(
                                        Locale.getDefault(), "%02d", col);

                                sample_id = String.format(
                                        "%s_%s%s", plate_idValue, rowName, colName);
                                write(output, sample_id, ",");
                                String well1 = String.format("%s%s", rowName, colName);
                                String well2 = String.format("%s%s", colName, rowName);
                                write(output, well1, ",");
                                write(output, well2, ",");
                            }
                            write(output, entryModel.getDNAExportValue(sample_id), ",");
                        }
                        write(output, dna_personValue, ",");
                        write(output, notesValue, ",");
                        write(output, tissue_typeValue, ",");
                        write(output, extractionValue, "\n");
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        output.close();
    }

    /**
     * This function is only called from devices with API KITKAT
     * Writes to a DocumentFile output stream instead of a typical file.
     * @param output the document file output stream
     * @param value the string value to write
     * @param append either a "," or a newline
     */
    private void write(OutputStream output, String value, String append) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                output.write((value + append).getBytes(StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // endregion

    private void exportUserDefined(
            @NonNull final CsvWriter csvWriter,
            final boolean includeHeader) throws IOException {
        final NonNullOptionalFields optionalFields =
                this.optionalFields();
        if (includeHeader) {
            csvWriter.write(this.templateModel.entryLabelIsNotNull() ?
                    this.templateModel.getEntryLabel() : "Value");
            csvWriter.write("Column");
            csvWriter.write("Row");

            if (null != optionalFields) {
                // noinspection CStyleArrayDeclaration
                final String names[] = optionalFields.names();
                for (final String name : names) csvWriter.write(name);
            }
            csvWriter.endRecord();
        }

        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();
            // noinspection CStyleArrayDeclaration
            final String values[] =
                    null == optionalFields ? null : optionalFields.values();

            final boolean
                    colNumbering = this.templateModel.getColNumbering(),
                    rowNumbering = this.templateModel.getRowNumbering();
            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 1) int row = 1; row <= rows; row++) {
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {
                        csvWriter.write(entryModel.getUserDefinedExportValue());

                        if (colNumbering)
                            csvWriter.write(col);
                        else
                            csvWriter.write(
                                    org.wheatgenetics.coordinate.Utils.convert(col - 1));

                        if (rowNumbering)
                            csvWriter.write(row);
                        else
                            csvWriter.write(
                                    org.wheatgenetics.coordinate.Utils.convert(row - 1));

                        if (null != values)
                            for (final String value : values) csvWriter.write(value);

                        csvWriter.endRecord();
                    }
                }
                //if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    private void exportUserDefined(
            @NonNull final OutputStream output,
            @Nullable final JoinedGridModel.Helper
                    helper,
            final boolean includeHeader) throws IOException {
        final NonNullOptionalFields optionalFields =
                this.optionalFields();
        if (includeHeader) {
            write(output, this.templateModel.entryLabelIsNotNull() ? this.templateModel.getEntryLabel() : "Value", ",");
            write(output, "Column, Row", ",");

            if (null != optionalFields) {
                // noinspection CStyleArrayDeclaration
                final String names[] = optionalFields.names();
                int size = names.length;
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        write(output, names[i], "\n");
                    } else {
                        write(output, names[i], ",");
                    }
                }
            }
        }

        {
            @IntRange(from = 1) final int
                    cols = this.getCols(), rows = this.getRows();
            // noinspection CStyleArrayDeclaration
            final String values[] =
                    null == optionalFields ? null : optionalFields.values();

            final boolean
                    colNumbering = this.templateModel.getColNumbering(),
                    rowNumbering = this.templateModel.getRowNumbering();
            for (@IntRange(from = 1) int col = 1; col <= cols; col++) {
                for (@IntRange(from = 1) int row = 1; row <= rows; row++) {
                    final EntryModel entryModel =
                            this.getEntryModel(row, col);
                    if (null != entryModel) {

                        write(output, entryModel.getUserDefinedExportValue(), ",");

                        if (colNumbering) {
                            write(output, String.valueOf(col), ",");
                        } else {
                            write(output, org.wheatgenetics.coordinate.Utils.convert(col - 1), ",");
                        }

                        if (rowNumbering){
                            write(output, String.valueOf(row), ",");
                        } else {
                            write(output, org.wheatgenetics.coordinate.Utils.convert(row - 1), ",");
                        }

                        if (null != values)
                            for (int i = 0; i < values.length; i++) {
                                if (i == values.length - 1) {
                                    write(output, values[i], "\n");
                                } else {
                                    write(output, values[i], ",");
                                }
                            }
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        output.close();
    }

    @Nullable
    private String optionalFieldValue(
            @Nullable final String name) {
        @Nullable final String result;
        {
            final NonNullOptionalFields optionalFields =
                    this.optionalFields();
            if (null == optionalFields)
                result = null;
            else {
                // noinspection CStyleArrayDeclaration
                final String values[] = optionalFields.values(/* names[] => */
                        org.phenoapps.androidlibrary.Utils.stringArray(name));
                result = null == values ? null : values[0];
            }
        }
        return result;
    }

    private boolean isExcludedRow(@IntRange(from = 1) final int row) {
        return this.templateModel.isExcludedRow(row);
    }

    private boolean isExcludedCol(@IntRange(from = 1) final int col) {
        return this.templateModel.isExcludedCol(col);
    }

    private void excludedCellsFromExcludedRowsAndCols(
            @NonNull final Cells result) {
        @IntRange(from = 1) final int
                rows = this.getRows(), cols = this.getCols();

        for (@IntRange(from = 1) int row = 1; row <= rows; row++)
            if (this.isExcludedRow(row))
                for (@IntRange(from = 1) int col = 1; col <= cols; col++)
                    result.add(row, col);

        for (@IntRange(from = 1) int col = 1; col <= cols; col++)
            if (this.isExcludedCol(col))
                for (@IntRange(from = 1) int row = 1; row <= rows; row++)
                    result.add(row, col);
    }

    @Nullable
    private Cells initialExcludedCells() {
        return this.templateModel.getExcludedCells();
    }

    @NonNull
    private Cells excludedCellsFromTemplate() {
        final Cells result;

        {
            final Cells initialExcludedCells =
                    this.initialExcludedCells();
            result = null == initialExcludedCells ?
                    new Cells(
                            this.getRows(), this.getCols(), this.stringGetter()) :
                    (Cells) initialExcludedCells.clone();
        }
        this.excludedCellsFromExcludedRowsAndCols(result);

        return result;
    }

    private void makeEntryModelsFromExcludedCells(
            final Cells excludedCells) {
        @IntRange(from = 1) final int
                rows = this.getRows(), cols = this.getCols();
        this.entryModels = this.makeEntryModels(rows, cols);
        for (@IntRange(from = 1) int row = 1; row <= rows; row++)
            for (@IntRange(from = 1) int col = 1; col <= cols; col++)
                if (null == excludedCells)
                    this.entryModels.makeIncludedEntry(row, col);
                else if (excludedCells.contains(row, col))
                    this.entryModels.makeExcludedEntry(row, col);
                else
                    this.entryModels.makeIncludedEntry(row, col);
    }

    @Nullable
    private IncludedEntryModel next(
            final EntryModel activeEntryModel,
            final Utils.Direction direction,
            final EntryModels.FilledHandler filledHandler) {
        // noinspection ConstantConditions
        return this.entryModelsIsNull() ? null : this.getEntryModels().next(
                activeEntryModel, direction, filledHandler);
    }
    // endregion

    private boolean setActiveRowAndActiveCol(
            @NonNull final EntryModel nextEntryModel) {
        return this.setActiveRowAndActiveCol(
                nextEntryModel.getRow() - 1, nextEntryModel.getCol() - 1);
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean entryModelsIsNull() {
        return null == this.getEntryModels();
    }
    // endregion

    // region Constructors

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    EntryModels makeEntryModels(
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols) {
        return new EntryModels(
                /* gridId => */ this.getId(), rows, cols, this.stringGetter());
    }

    // region org.wheatgenetics.coordinate.model.DisplayModel Overridden Methods
    @Override
    @IntRange(from = 1)
    public int getRows() {
        return this.templateModel.getRows();
    }
    // endregion

    @Override
    @IntRange(from = 1)
    public int getCols() {
        return this.templateModel.getCols();
    }

    @Override
    public boolean getColNumbering() {
        return this.templateModel.getColNumbering();
    }

    @Override
    public boolean getRowNumbering() {
        return this.templateModel.getRowNumbering();
    }

    @Override
    @Nullable
    public ElementModel getElementModel(
            @IntRange(from = 1) int row,
            @IntRange(from = 1) int col) {
        return this.getEntryModel(row, col);
    }

    // region Package Methods
    @NonNull
    String name() {
        return String.format(Locale.getDefault(),
                "Person: %s\n Template: %s\n Size: (%d, %d) Timestamp: %s\n", this.getPerson(),
                this.getTemplateTitle(), this.getCols(), this.getRows(), this.getFormattedTimestamp());
    }
    // endregion

    @NonNull
    Cells excludedCellsFromEntries() {
        // noinspection ConstantConditions
        final Cells result = this.entryModelsIsNull() ?
                new Cells(
                        this.getRows(), this.getCols(), this.stringGetter()) :
                this.getEntryModels().excludedCells();
        this.excludedCellsFromExcludedRowsAndCols(result);
        return result;
    }

    void exportDocumentFile(final OutputStream stream,
                            final String exportFileName,
                            @Nullable final JoinedGridModel.Helper helper,
                            final boolean includeHeader) throws IOException {
        final TemplateType templateType =
                this.templateModel.getType();
        if (TemplateType.SEED == templateType)
            this.exportSeed(stream, exportFileName, helper, includeHeader);
        else
            if (TemplateType.DNA == templateType)
                this.exportDNA(stream, helper, includeHeader);
            else
                this.exportUserDefined(stream, helper, includeHeader);
    }

    void export(final Writer writer, final String exportFileName,
                final boolean includeHeader) throws IOException {
        final TemplateType templateType =
                this.templateModel.getType();
        final CsvWriter csvWriter =
                new CsvWriter(writer);
        if (TemplateType.SEED == templateType)
            this.exportSeed(csvWriter, exportFileName, includeHeader);    // throws java.io-
        else                                                                      //  .IOException
            if (TemplateType.DNA == templateType)
                this.exportDNA(csvWriter, includeHeader);                 // throws java.io-
            else                                                                  //  .IOException
                this.exportUserDefined(csvWriter, includeHeader);         // throws java.io-
    }                                                                             //  .IOException

    // region Public Methods
    @Nullable
    public String getTitle() {
        @Nullable final String name;
        {
            final TemplateType templateType =
                    this.templateModel.getType();
            @NonNull final StringGetter stringGetter = this.stringGetter();

            if (TemplateType.SEED == templateType)
                name = stringGetter.get(R.string.NonNullOptionalFieldsTrayIDFieldName);
            else if (TemplateType.DNA == templateType)
                name = stringGetter.get(R.string.NonNullOptionalFieldsPlateIDFieldName);
            else
                name = BaseOptionalField.identificationFieldName(stringGetter);
        }
        return this.optionalFieldValue(name);
    }
    // endregion

    public String getTemplateTitle() {
        return this.templateModel.getTitle();
    }

    public void makeEntryModels() throws
            Cells.MaxRowAndOrMaxColOutOfRange,
            Cells.AmountIsTooLarge {
        final Cells excludedCells =
                this.excludedCellsFromTemplate();

        excludedCells.makeRandomCells(       // throws MaxRowAndOrMaxColOutOfRange, AmountIsTooLarge
                /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
                /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void makeEntryModels(@NonNull final
                                Cells projectExcludedCells) throws
            Cells.MaxRowAndOrMaxColOutOfRange,
            Cells.AmountIsTooLarge {
        Cells excludedCells = this.excludedCellsFromTemplate();

        {
            final Cells randomCells =
                    projectExcludedCells.makeRandomCells(                                      // throws
                            /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
                            /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());
            if (null != randomCells)
                if (excludedCells.size() <= 0)
                    excludedCells = randomCells;
                else
                    excludedCells.accumulate(randomCells);
        }

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void setEntryModel(final EntryModel entryModel) {
        if (!this.entryModelsIsNull())
            // noinspection ConstantConditions
            this.getEntryModels().set(entryModel);
    }

    @Nullable
    public EntryModel getActiveEntryModel() {
        return this.getEntryModel(this.getActiveRow() + 1, this.getActiveCol() + 1);
    }

    public boolean setActiveRowAndActiveCol(
            @IntRange(from = 0) final int row,
            @IntRange(from = 0) final int col) {
        final boolean changed;

        if (this.getActiveRow() != row || this.getActiveCol() != col) {
            this.setActiveRow(row);
            this.setActiveCol(col);
            changed = true;
        } else
            changed = false;

        return changed;
    }

    @Nullable
    public EntryModels
    getEntryModels() {
        return this.entryModels;
    }

    public boolean goToNext(
            final EntryModel activeEntryModel,
            final Utils.Direction direction,
            final EntryModels.FilledHandler filledHandler) {
        final IncludedEntryModel nextIncludedEntryModel =
                this.next(activeEntryModel, direction, filledHandler);
        // noinspection SimplifiableConditionalExpression
        return null == nextIncludedEntryModel ? false :
                this.setActiveRowAndActiveCol(nextIncludedEntryModel);
    }

    public boolean activeRowAndOrActiveColWasAdjusted(
            final Utils.Direction direction) {
        final EntryModel activeEntryModel =
                this.getActiveEntryModel();
        // noinspection SimplifiableConditionalExpression
        return activeEntryModel instanceof ExcludedEntryModel ?
                this.goToNext(activeEntryModel, direction, null) : false;
    }

    public boolean export(final File exportFile, final String exportFileName)
            throws IOException {
        final boolean success;
        if (null == exportFile)
            success = false;
        else {

            this.export(                                               // throws java.io.IOException
                    new FileWriter(exportFile) /* throws java.io.IOException */,
                    exportFileName, /* includeHeader => */true);
            success = true;
        }
        return success;
    }

    public boolean export(final OutputStream output, final String exportFileName,
                          final JoinedGridModel.Helper helper)
            throws IOException {
        final boolean success;
        if (null == output || null == helper)
            success = false;
        else {

            this.exportDocumentFile(output, exportFileName, helper, /* includeHeader => */true);
            success = true;
        }
        return success;
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Helper {
        public abstract void publishProgress(@IntRange(from = 1) int col);
    }
    // endregion
}