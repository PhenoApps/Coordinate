package org.wheatgenetics.coordinate.collector;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.ge.GridExporter;
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels;
import org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.model.EntryModel;
import org.wheatgenetics.coordinate.model.EntryModels;
import org.wheatgenetics.coordinate.model.ExcludedEntryModel;
import org.wheatgenetics.coordinate.model.IncludedEntryModel;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.UniqueEntryModels;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.GeneralKeys;
import org.wheatgenetics.coordinate.utils.SoundHelperImpl;

@SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class BaseCollector extends Object implements
        GridDisplayFragment.Handler,
        EntryModels.FilledHandler,
        DataEntryDialogFragment.Handler {
    // region Fields
    @NonNull
    private final AppCompatActivity activity;

    private final GridDisplayFragment gridDisplayFragment;
//    private final DataEntryFragment dataEntryFragment;

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    JoinedGridModel joinedGridModel = null;

    // region Table Fields
    private ProjectsTable projectsTableInstance = null;  // ll
    private GridsTable gridsTableInstance = null;  // ll
    private EntriesTable entriesTableInstance = null;  // ll
    // endregion

    private SoundHelperImpl soundHelper;

    private SharedPreferences preferences;

    private UniqueAlertDialog uniqueAlertDialog = null; // ll


    private GridExportPreprocessor gridExportPreprocessor;

    private GridExporter gridExporter;

    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseCollector(@NonNull final AppCompatActivity activity) {
        super();

        this.activity = activity;

        soundHelper = new SoundHelperImpl(activity);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        @NonNull final FragmentManager fragmentManager =
                this.activity.getSupportFragmentManager();
        this.gridDisplayFragment = (GridDisplayFragment)
                fragmentManager.findFragmentById(R.id.gridDisplayFragment);
//        this.dataEntryFragment = (DataEntryFragment)
//                fragmentManager.findFragmentById(R.id.dataEntryFragment);
    }

    // region Private Methods
    @Nullable
    private EntriesTable entriesTable() {
        return Utils.entriesTable(
                this.entriesTableInstance, this.gridsTable(), this.activity);
    }

    // region Preference Private Methods
    private org.wheatgenetics.coordinate.preference.Utils.Direction getDirection() {
        return org.wheatgenetics.coordinate.preference.Utils.getDirection(this.activity);
    }

    private boolean getUnique() {
        return org.wheatgenetics.coordinate.preference.Utils.getUnique(this.activity);
    }
    // endregion

    private void populateDataEntryFragment() {
        EditText dataEntry = activity.findViewById(R.id.act_collector_data_entry_et);
        if (dataEntry != null) {
            String value = ((CollectorActivity) activity).getEntryValue();
            if (value != null) {
                dataEntry.setText(value);
                dataEntry.setSelection(value.length());
            } else dataEntry.getText().clear();
        }
    }

    private void goToNext(final EntryModel entryModel) {
        final GridsTable gridsTable = this.gridsTable();
        if (this.joinedGridModelIsLoaded() && null != gridsTable)
            if (this.joinedGridModel.goToNext(entryModel, this.getDirection(), this)) {
                gridsTable.update(this.joinedGridModel);             // Update activeRow, activeCol.

                if (null != this.gridDisplayFragment)
                    this.gridDisplayFragment.notifyDataSetChanged();
                this.populateDataEntryFragment();
            }
    }

    private void setEntry(final String entry) {
        EditText dataEntry = activity.findViewById(R.id.act_collector_data_entry_et);
        if (dataEntry != null) {
            dataEntry.setText(entry);
            dataEntry.setSelection(dataEntry.getText().length());
        }
    }

    private void handleDuplicateCheckException(
            @NonNull final String message) {
        if (preferences.getBoolean(GeneralKeys.DUPLICATE_ENTRY_SOUND, false)) {
            soundHelper.playDuplicate();
        }

        if (null == this.uniqueAlertDialog) this.uniqueAlertDialog =
                new UniqueAlertDialog(this.activity);
        this.uniqueAlertDialog.show(message);
    }
    // endregion

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    GridsTable gridsTable() {
        return Utils.gridsTable(
                this.gridsTableInstance, this.activity);
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @Override
    public DisplayModel getDisplayModel() {
        return this.joinedGridModel;
    }

    @Override
    public void toggle(@Nullable final ElementModel elementModel) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean togglePreference = prefs.getBoolean(GeneralKeys.TOGGLE_EXCLUSION_STATE, false);
        if (togglePreference) {
            if (this.joinedGridModelIsLoaded()) {
                final EntriesTable entriesTable =
                        this.entriesTable();
                if (null != entriesTable) {
                    final EntryModel entryModel =
                            (EntryModel) elementModel;
                    if (this.joinedGridModel instanceof
                            CurrentGridUniqueJoinedGridModel) {
                        final CurrentGridUniqueJoinedGridModel
                                currentGridUniqueJoinedGridModel =
                                (CurrentGridUniqueJoinedGridModel)
                                        this.joinedGridModel;
                        try {
                            currentGridUniqueJoinedGridModel.checkThenSetEntryModel(entryModel);//throws
                        } catch (final
                        CheckedIncludedEntryModel.CheckException e) {
                            return;
                        }
                    } else this.joinedGridModel.setEntryModel(entryModel);
                    entriesTable.insertOrUpdate(entryModel);
                    if (entryModel instanceof ExcludedEntryModel)
                        if (this.joinedGridModel.getActiveEntryModel() == entryModel)
                            this.goToNext(entryModel);
                }
            }
        }
    }

    @Override
    public int getActiveRow() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveRow() : -1;
    }

    @Override
    public int getActiveCol() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveCol() : -1;
    }

    @Override
    public void activate(final int row, final int col) {
        final GridsTable gridsTable = this.gridsTable();
        if (null != gridsTable) {
            if (this.joinedGridModelIsLoaded())
                if (this.joinedGridModel.setActiveRowAndActiveCol(row, col))
                    gridsTable.update(this.joinedGridModel);

            this.setEntry(this.getEntryValue());
        }
    }

    @Override
    @Nullable
    public CheckedIncludedEntryModel.Checker getChecker() {
        if (this.joinedGridModel instanceof
                CurrentGridUniqueJoinedGridModel)
            return (CurrentGridUniqueEntryModels)
                    this.joinedGridModel.getEntryModels();
        else return null;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.EntryModels.FilledHandler Overridden Methods
    @Override
    public void handleFilledGrid() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        builder.setNeutralButton(
                activity.getString(R.string.grid_export_text),
                ((dialog, which) -> {
                    dialog.dismiss();
                    preprocessGridExport(joinedGridModel.getId());
                }));
        builder.setTitle(activity.getString(R.string.Coordinate))
                .setMessage(activity.getString(R.string.BaseCollectorFilledGridAlertMessage))
                .setPositiveButton(activity.getString(R.string.UtilsPositiveButtonText), ((dialog, which) -> {
                    dialog.dismiss();
                })).show();

        if (preferences.getBoolean(GeneralKeys.GRID_FILLED_SOUND, false)) {
            soundHelper.playPlonk();
        }
    }

    private void preprocessGridExport(@IntRange(from = 1) final long gridId) {
        this.gridExportPreprocessor().preprocess(gridId);
    }

    @NonNull
    private GridExporter gridExporter() {
        if (null == this.gridExporter)
            this.gridExporter = new GridExporter(activity,
                    12,
                    new GridDeleter.Handler() {
                        @Override
                        public void respondToDeletedGrid() {
                        }
                    },
                    false);
        return this.gridExporter;
    }

    @NonNull
    public GridExportPreprocessor gridExportPreprocessor() {
        if (null == this.gridExportPreprocessor) this.gridExportPreprocessor =
                new GridExportPreprocessor(activity,
                        new GridExportPreprocessor.Handler() {
                            @Override
                            public void exportGrid(
                                    @IntRange(from = 1) final long gridId,
                                    final String fileName) {
                                BaseCollector.this.exportGrid(gridId, fileName);
                            }
                        });
        return this.gridExportPreprocessor;
    }

    private void exportGrid(@IntRange(from = 1) final long gridId,
                            final String fileName) {
        gridExportPreprocessor().handleExport(gridId, fileName, gridExporter(), null);
    }

    @Override
    public void handleFilledRowOrCol() {
        if (preferences.getBoolean(GeneralKeys.NAVIGATION_SOUND, false)) {
            soundHelper.playRowOrColumnEnd();
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler Overridden Methods
    @Override
    public String getEntryValue() {
        if (this.joinedGridModelIsLoaded()) {
            final EntryModel activeEntryModel =
                    this.joinedGridModel.getActiveEntryModel();
            return null == activeEntryModel ? null : activeEntryModel.getValue();
        } else return null;
    }

    @Override
    public String getProjectTitle() {
        if (this.joinedGridModelIsLoaded()) {
            @IntRange(from = 0) final long projectId =
                    this.joinedGridModel.getProjectId();
            final String NONE = "none";

            if (Model.illegal(projectId))
                return NONE;
            else {
                final ProjectModel projectModel =
                        this.projectsTable().get(projectId);
                return null == projectModel ? NONE : projectModel.getTitle();
            }
        } else return "";
    }

    @Override
    public String getTemplateTitle() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getTemplateTitle() : "";
    }

    @Override
    public NonNullOptionalFields getOptionalFields() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.optionalFields() : null;
    }

    @Override
    public void saveEntry(final String entryValue) {
        if (this.joinedGridModelIsLoaded()) {
            final EntryModel activeEntryModel =
                    this.joinedGridModel.getActiveEntryModel();
            if (activeEntryModel instanceof IncludedEntryModel) {
                final EntriesTable entriesTable =
                        this.entriesTable();
                if (null != entriesTable) {
                    final IncludedEntryModel
                            activeIncludedEntryModel =
                            (IncludedEntryModel)
                                    activeEntryModel;
                    if (this.getUnique()) {
                        final String oldEntryValue = activeIncludedEntryModel.getValue();
                        final CheckedIncludedEntryModel
                                checkedIncludedEntryModel =
                                (CheckedIncludedEntryModel)
                                        activeIncludedEntryModel;
                        try {
                            checkedIncludedEntryModel.checkThenSetValue(entryValue) /* throws */;
                        } catch (final
                        CheckedIncludedEntryModel.CheckException
                                e) {
                            if (e instanceof UniqueEntryModels.DuplicateCheckException) {
                                {
                                    final String message = e.getMessage();
                                    this.handleDuplicateCheckException(
                                            null == message ? e.toString() : message);
                                }
                                this.setEntry(oldEntryValue);
                            }
                            return;
                        }
                    } else activeIncludedEntryModel.setValue(entryValue);

                    entriesTable.insertOrUpdate(activeIncludedEntryModel);
                }
            }
            this.goToNext(activeEntryModel);
        }
    }

    @Override
    public void clearEntry() {
        this.saveEntry(null);
    }
    // endregion
    // endregion

    // region Public Methods
    public boolean joinedGridModelIsLoaded() {
        return null != this.joinedGridModel;
    }

    public boolean hasProject() {
        if (this.joinedGridModelIsLoaded()) {
            @IntRange(from = 0) final long projectId = this.joinedGridModel.getProjectId();
            return !Model.illegal(projectId);
        }
        return false;
    }

    public void populateFragments() {
        if (null != this.gridDisplayFragment) {
            this.gridDisplayFragment.populate();
            this.gridDisplayFragment.scrollToActiveCell(getActiveRow(), getActiveCol());
        }
        this.populateDataEntryFragment();
    }

    // region loadJoinedGridModel() Public Methods
    public void loadJoinedGridModel(@IntRange(from = 0) final long gridId) {
        if (Model.illegal(gridId))
            this.joinedGridModel = null;
        else {
            final GridsTable gridsTable = this.gridsTable();
            this.joinedGridModel = null == gridsTable ? null : gridsTable.get(gridId);
        }
    }

    public void loadJoinedGridModelThenPopulate(@IntRange(from = 0) final long gridId) {
        this.loadJoinedGridModel(gridId);
        this.populateFragments();
    }
    // endregion

    public void reloadIfNecessary() {
        if (Utils.gridsTableNeedsReloading(
                this.gridsTableInstance, this.activity))
            this.gridsTableInstance = null;

        if (Utils.entriesTableNeedsReloading(
                this.entriesTableInstance, this.activity))
            this.entriesTableInstance = null;

        if (Utils.joinedGridModelNeedsReloading(
                this.joinedGridModel, this.activity))
            this.loadJoinedGridModelThenPopulate(this.joinedGridModel.getId());
    }

    // region Barcode Public Methods
    @SuppressWarnings({"SameReturnValue"})
    public boolean scanBarcode() {
        //this.barcodeScanner().scan();
        new IntentIntegrator(this.activity)
                .setOrientationLocked(false)
                .setPrompt("Scan a barcode")
                .setBeepEnabled(true).initiateScan();
        return true;
    }

    public boolean parseActivityResult(final int requestCode,
                                       final int resultCode, final Intent data) {
        final boolean handled;
        {
            //final String barcodeScannerResult =
            //        BarcodeScanner.parseActivityResult(
            //                requestCode, resultCode, data);

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result == null) {

                handled = false;

            }  else {
                final String barcodeScannerResult = result.getContents();

                if (barcodeScannerResult != null && !barcodeScannerResult.isEmpty()) {
                    this.setEntry(barcodeScannerResult);
                    this.saveEntry(barcodeScannerResult);
                }

                handled = true;
            }

        }
        return handled;
    }
    // endregion

}