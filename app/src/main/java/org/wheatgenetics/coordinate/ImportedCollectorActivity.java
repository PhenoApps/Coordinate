package org.wheatgenetics.coordinate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.display.FitToWidthRecyclerView;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.ge.GridExporter;
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.model.ImportedEntryModel;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.preference.GeneralKeys;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;
import org.wheatgenetics.coordinate.utils.InsetHandler;
import org.wheatgenetics.coordinate.utils.Keys;
import org.wheatgenetics.coordinate.utils.SoundHelperImpl;

/**
 * Collect/validation view for imported grids.
 *
 * Unlike CollectorActivity, cells are pre-populated and the user confirms/validates
 * them via barcode scanning or manual approval. The TextView is read-only and shows
 * the current cell's value. Three action buttons are shown at the bottom:
 *   - Edit: replace the value or mark as missing
 *   - Barcode: scan and compare to cell value (audio feedback)
 *   - Approve: mark cell as confirmed and advance to next cell
 */
public class ImportedCollectorActivity extends BackActivity
        implements GridDisplayFragment.Handler {

    private static final String GRID_ID_KEY = "gridId";
    private static Intent INTENT_INSTANCE = null;

    private long mGridId = -1L;
    private JoinedGridModel mGridModel = null;
    private EntriesTable mEntriesTable = null;
    private GridsTable mGridsTable = null;
    private SoundHelperImpl mSoundHelper = null;
    private boolean mFitToWidth = false;
    private Menu mMenu = null;

    private GridExportPreprocessor mGridExportPreprocessor = null;
    private GridExporter mGridExporter = null;

    /** When true, no cell is actively selected (e.g. after the last cell is confirmed). */
    private boolean mCellDeselected = false;

    /** When true, the next barcode scan result should populate the active cell directly (edit mode). */
    private boolean mScanningForEdit = false;

    /** Reference to the approve/cancel button so its icon can be updated when the active cell changes. */
    private ImageButton mBtnApprove = null;

    @NonNull
    public static Intent intent(@NonNull final Context context,
                                @IntRange(from = 1) final long gridId) {
        @NonNull final Intent result =
                null == ImportedCollectorActivity.INTENT_INSTANCE
                        ? ImportedCollectorActivity.INTENT_INSTANCE =
                                new Intent(context, ImportedCollectorActivity.class)
                        : ImportedCollectorActivity.INTENT_INSTANCE;
        return result.putExtra(GRID_ID_KEY, gridId);
    }

    // region Lifecycle
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imported_collector);

        final androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InsetHandler.applyToolbarInsets(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(null);
        }

        final BottomNavigationView bnv = findViewById(R.id.act_imported_collector_bnv);
        if (bnv != null) {
            InsetHandler.applyBottomNavInsets(bnv);
            setupBottomNavigationBar(bnv);
        }

        // Apply left/right insets to the entire content area so the value TextView
        // and action buttons are kept inside the camera cutout / nav bar boundaries.
        InsetHandler.applyRootInsets(
                getWindow().getDecorView().findViewById(android.R.id.content));

        final @Nullable Intent intent = getIntent();
        if (intent != null && intent.hasExtra(GRID_ID_KEY)) {
            mGridId = intent.getLongExtra(GRID_ID_KEY, -1L);
        }
        if (mGridId > 0) {
            loadGrid();
        }

        mSoundHelper = new SoundHelperImpl(this);

        // Wire up action buttons
        final ImageButton btnEdit    = findViewById(R.id.btn_imported_edit);
        final ImageButton btnMissing = findViewById(R.id.btn_imported_missing);
        final ImageButton btnBarcode = findViewById(R.id.btn_imported_barcode);
        mBtnApprove = findViewById(R.id.btn_imported_approve);
        if (btnEdit    != null) btnEdit.setOnClickListener(v -> onEditClicked());
        if (btnMissing != null) btnMissing.setOnClickListener(v -> onMissingClicked());
        if (btnBarcode != null) btnBarcode.setOnClickListener(v -> onBarcodeClicked());
        if (mBtnApprove != null) mBtnApprove.setOnClickListener(v -> onApproveClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        final GridDisplayFragment frag = getGridDisplayFragment();
        if (frag != null) {
            if (mFitToWidth) {
                applyFitToWidth(true);
            } else {
                frag.normalizeCellSizes();
            }
        }
    }
    // endregion

    // region Menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_imported_collector, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.export_grid) {
            onExportClicked();
            return true;
        } else if (item.getItemId() == R.id.action_fit_to_width) {
            mFitToWidth = !mFitToWidth;
            applyFitToWidth(mFitToWidth);
            item.setIcon(mFitToWidth
                    ? R.drawable.ic_fit_to_expand
                    : R.drawable.ic_fit_to_width);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // endregion

    // region Grid loading
    private void loadGrid() {
        mGridsTable = new GridsTable(this);
        mGridModel = mGridsTable.get(mGridId);
        mEntriesTable = new EntriesTable(this);
        refreshDisplay();
    }

    private void refreshDisplay() {
        final GridDisplayFragment frag = getGridDisplayFragment();
        if (frag != null) {
            frag.populate();
        }
        updateValueDisplay();
    }

    private void updateValueDisplay() {
        final TextView tv = findViewById(R.id.act_imported_value_et);
        if (tv == null) return;
        if (mCellDeselected) {
            tv.setText("");
            updateApproveButtonIcon(null);
            return;
        }
        final ImportedEntryModel active = getActiveImportedEntry();
        tv.setText(active != null ? active.getValue() : "");
        updateApproveButtonIcon(active);
    }

    /** Updates the approve button to show a check (unconfirmed) or close/cancel (confirmed) icon. */
    private void updateApproveButtonIcon(@Nullable final ImportedEntryModel active) {
        if (mBtnApprove == null) return;
        final boolean confirmed = active != null && active.isConfirmed();
        mBtnApprove.setImageResource(
                confirmed ? R.drawable.ic_mdi_close : R.drawable.ic_mdi_check_bold);
        mBtnApprove.setContentDescription(
                getString(confirmed
                        ? R.string.ImportedCancelConfirmButton
                        : R.string.ImportedApproveButton));
    }
    // endregion

    // region GridDisplayFragment.Handler
    @Override
    public int getActiveRow() {
        // Integer.MAX_VALUE signals "no cell selected": Math.max(MAX_VALUE, -1) = MAX_VALUE,
        // which matches no real row and does not trigger the -1/-1 "all active" fallback
        // in DataViewHolder.bind(), preventing notifyDataSetChanged() during layout.
        if (mCellDeselected) return Integer.MAX_VALUE;
        return mGridModel != null ? mGridModel.getActiveRow() : -1;
    }

    @Override
    public int getActiveCol() {
        if (mCellDeselected) return Integer.MAX_VALUE;
        return mGridModel != null ? mGridModel.getActiveCol() : -1;
    }

    @Override
    public void activate(final int row, final int col) {
        mCellDeselected = false; // re-enable selection when user taps a cell
        if (mGridModel != null && mGridsTable != null) {
            if (mGridModel.setActiveRowAndActiveCol(row, col)) {
                mGridsTable.update(mGridModel);
            }
            updateValueDisplay();
        }
    }

    @Override
    @Nullable
    public CheckedIncludedEntryModel.Checker getChecker() {
        return null; // no duplicate checking for imported grids
    }

    @Override
    public boolean isImportedMode() {
        return true;
    }

    @Override
    @Nullable
    public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel() {
        return mGridModel;
    }

    @Override
    public void toggle(@Nullable final org.wheatgenetics.coordinate.model.ElementModel elementModel) {
        // Long-press exclusion is disabled for imported grids; no-op
    }
    // endregion

    // region Button handlers
    private void onEditClicked() {
        final ImportedEntryModel active = getActiveImportedEntry();
        if (active == null) return;

        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_imported_edit, null);
        final EditText newValueEt = dialogView.findViewById(R.id.et_new_value);
        newValueEt.setText(active.getValue());

        new AlertDialog.Builder(this)
                .setTitle(R.string.ImportedEditDialogTitle)
                .setView(dialogView)
                .setPositiveButton(R.string.ImportedEditReplace, (d, w) -> {
                    final String newVal = newValueEt.getText().toString().trim();
                    if (!newVal.isEmpty()) replaceActiveValue(newVal);
                })
                .setNeutralButton(R.string.ImportedEditScan, (d, w) -> {
                    mScanningForEdit = true;
                    new IntentIntegrator(this)
                            .setOrientationLocked(false)
                            .setPrompt(getString(R.string.ImportedBarcodeScanPrompt))
                            .setBeepEnabled(false)
                            .initiateScan();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void replaceActiveValue(@NonNull final String newVal) {
        final ImportedEntryModel active = getActiveImportedEntry();
        if (active == null) return;
        active.setValue(newVal);
        if (mEntriesTable != null) {
            mEntriesTable.updateImportedEntryValue(
                    active.getId(), newVal, System.currentTimeMillis());
        }
        notifyGrid();
        updateValueDisplay();
    }

    private void onMissingClicked() {
        markActiveMissing();
    }

    private void markActiveMissing() {
        replaceActiveValue(ImportedEntryModel.MISSING_VALUE);
    }

    private void onBarcodeClicked() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setPrompt(getString(R.string.ImportedBarcodeScanPrompt))
                .setBeepEnabled(false) // we handle audio ourselves
                .initiateScan();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            final String scanned = result.getContents();
            if (mScanningForEdit) {
                mScanningForEdit = false;
                replaceActiveValue(scanned);
            } else {
                final ImportedEntryModel active = getActiveImportedEntry();
                if (active != null && scanned.equals(active.getValue())) {
                    mSoundHelper.playPlonk();  // match → success sound
                    approveAndAdvance();
                } else {
                    mSoundHelper.playDuplicate(); // mismatch → error sound
                }
            }
        } else {
            mScanningForEdit = false; // scan cancelled, reset flag
        }
    }

    private void onApproveClicked() {
        final ImportedEntryModel active = getActiveImportedEntry();
        if (active != null && active.isConfirmed()) {
            // Cell is already confirmed — cancel/remove confirmation
            active.setConfirmedTimestamp(0);
            if (mEntriesTable != null) {
                mEntriesTable.updateConfirmedTimestamp(active.getId(), 0);
            }
            notifyGrid();
            updateApproveButtonIcon(active);
        } else {
            approveAndAdvance();
        }
    }

    private void approveAndAdvance() {
        final ImportedEntryModel active = getActiveImportedEntry();
        boolean justConfirmed = false;
        if (active != null && !active.isConfirmed()) {
            final long now = System.currentTimeMillis();
            active.setConfirmedTimestamp(now);
            if (mEntriesTable != null) {
                mEntriesTable.updateConfirmedTimestamp(active.getId(), now);
            }
            notifyGrid();
            justConfirmed = true;
        }
        final boolean advanced = goToNextCell();
        if (!advanced && justConfirmed) {
            promptExportAfterCompletion();
        }
    }

    private boolean goToNextCell() {
        if (mGridModel == null || mGridsTable == null) return false;
        final int rows = mGridModel.getRows();
        final int cols = mGridModel.getCols();
        int row = mGridModel.getActiveRow() + 1; // getActiveRow() is 0-based
        int col = mGridModel.getActiveCol() + 1; // getActiveCol() is 0-based

        // Advance to next non-excluded cell (left-to-right, top-to-bottom, col-major)
        boolean advanced = false;
        outer:
        for (int c = col; c <= cols; c++) {
            int startRow = (c == col) ? row + 1 : 1;
            for (int r = startRow; r <= rows; r++) {
                final ElementModel em = mGridModel.getElementModel(r, c);
                if (em instanceof ImportedEntryModel) {
                    mGridModel.setActiveRowAndActiveCol(r - 1, c - 1);
                    mGridsTable.update(mGridModel);
                    advanced = true;
                    break outer;
                }
            }
        }

        if (!advanced) {
            // Last cell was just confirmed — deselect so no cell appears highlighted.
            mCellDeselected = true;
        }

        notifyGrid();
        updateValueDisplay();
        return advanced;
    }
    // endregion

    // region Export
    private void onExportClicked() {
        if (mGridId <= 0) return;
        // Save the current person name to the grid record so it appears in the export
        final String person = androidx.preference.PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(GeneralKeys.PERSON_NAME, "");
        if (person != null && !person.isEmpty()) {
            new GridsTable(this).updatePerson(mGridId, person);
        }
        gridExportPreprocessor().preprocess(mGridId);
    }

    private void promptExportAfterCompletion() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.ImportedGridCompleteTitle)
                .setMessage(R.string.ImportedGridCompleteMessage)
                .setPositiveButton(R.string.export_dialog_positive_button_text,
                        (d, w) -> onExportClicked())
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @NonNull
    private GridExportPreprocessor gridExportPreprocessor() {
        if (mGridExportPreprocessor == null) {
            mGridExportPreprocessor = new GridExportPreprocessor(this,
                    new GridExportPreprocessor.Handler() {
                        @Override
                        public void exportGrid(final long gridId, final String fileName) {
                            mGridExportPreprocessor.handleExport(
                                    gridId, fileName, gridExporter(), null);
                        }
                    });
        }
        return mGridExportPreprocessor;
    }

    @NonNull
    private GridExporter gridExporter() {
        if (mGridExporter == null) {
            mGridExporter = new GridExporter(this, 12,
                    new GridDeleter.Handler() {
                        @Override
                        public void respondToDeletedGrid() { /* no-op */ }
                    }, false);
        }
        return mGridExporter;
    }
    // endregion

    // region Fit to width
    private void applyFitToWidth(final boolean fit) {
        final FitToWidthRecyclerView recyclerView = findViewById(R.id.displayRecyclerView);
        if (recyclerView == null) return;

        final HorizontalScrollView hsv = (HorizontalScrollView) recyclerView.getParent();

        float scaleFactor = 1.0f;
        int cellSize = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (fit) {
            final int naturalWidth = recyclerView.getWidth();
            final int targetWidth = hsv.getWidth();
            if (naturalWidth > 0) {
                scaleFactor = Math.min(1.0f, (float) targetWidth / naturalWidth);
            }
            final DisplayModel displayModel = getDisplayModel();
            if (displayModel != null && targetWidth > 0) {
                final int spanCount = 1 + displayModel.getCols();
                final int marginPx = (int) android.util.TypedValue.applyDimension(
                        android.util.TypedValue.COMPLEX_UNIT_DIP, 5,
                        getResources().getDisplayMetrics());
                cellSize = targetWidth / spanCount - 2 * marginPx;
            }
        }

        recyclerView.setFitToWidth(fit, hsv.getWidth());

        final GridDisplayFragment frag = getGridDisplayFragment();
        if (frag != null) {
            frag.resetCellWidth();
            frag.setCompact(fit, scaleFactor, cellSize);
            if (!fit) frag.normalizeCellSizes();
        }
    }
    // endregion

    // region Helpers
    @Nullable
    private ImportedEntryModel getActiveImportedEntry() {
        if (mGridModel == null) return null;
        final int row = mGridModel.getActiveRow() + 1; // model is 0-based, getElementModel is 1-based
        final int col = mGridModel.getActiveCol() + 1;
        if (row <= 0 || col <= 0) return null;
        final ElementModel em =
                mGridModel.getElementModel(row, col);
        return em instanceof ImportedEntryModel ? (ImportedEntryModel) em : null;
    }

    private void notifyGrid() {
        final GridDisplayFragment frag = getGridDisplayFragment();
        if (frag != null) {
            frag.notifyDataSetChanged();
        }
    }

    @Nullable
    private GridDisplayFragment getGridDisplayFragment() {
        return (GridDisplayFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gridDisplayFragment);
    }

    private void setupBottomNavigationBar(@NonNull final BottomNavigationView bnv) {
        bnv.inflateMenu(R.menu.menu_bottom_nav_bar);
        bnv.setOnItemSelectedListener(item -> {
            if (mGridId != -1L) {
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
                        .edit().putLong(Keys.COLLECTOR_LAST_GRID, mGridId).apply();
            }
            final int id = item.getItemId();
            if (id == R.id.action_nav_grids)
                startActivity(GridsActivity.intent(this));
            else if (id == R.id.action_nav_templates)
                startActivity(TemplatesActivity.intent(this));
            else if (id == R.id.action_nav_projects)
                startActivity(ProjectsActivity.intent(this));
            else if (id == R.id.action_nav_settings)
                startActivity(PreferenceActivity.intent(this));
            return true;
        });
    }
    // endregion
}
