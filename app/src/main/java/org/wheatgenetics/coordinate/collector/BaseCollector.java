package org.wheatgenetics.coordinate.collector;

/**
 * Uses:
 * android.content.Intent
 * android.media.MediaPlayer
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.appcompat.app.AppCompatActivity
 * androidx.fragment.app.FragmentManager
 *
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels
 * org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.UniqueEntryModels.DuplicateCheckException
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.preference.Utils
 * org.wheatgenetics.coordinate.preference.Utils.Advancement
 *
 * org.wheatgenetics.coordinate.collector.DataEntryFragment
 * org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
 * org.wheatgenetics.coordinate.collector.UniqueAlertDialog
 * org.wheatgenetics.coordinate.collector.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
abstract class BaseCollector extends java.lang.Object implements
org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.model.EntryModels.FilledHandler        ,
org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
{
    // region Fields
    @androidx.annotation.NonNull private final androidx.appcompat.app.AppCompatActivity activity;

    private final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment gridDisplayFragment;
    private final org.wheatgenetics.coordinate.collector.DataEntryFragment     dataEntryFragment  ;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel = null;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    private org.wheatgenetics.coordinate.database.GridsTable    gridsTableInstance    = null;  // ll
    private org.wheatgenetics.coordinate.database.EntriesTable  entriesTableInstance  = null;  // ll
    // endregion

    private android.media.MediaPlayer gridEndMediaPlayer = null,
        rowOrColumnEndMediaPlayer = null, disallowedDuplicateMediaPlayer = null;       // lazy loads
    private org.wheatgenetics.coordinate.collector.UniqueAlertDialog uniqueAlertDialog = null; // ll

    private org.wheatgenetics.zxing.BarcodeScanner barcodeScannerInstance = null;       // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        return org.wheatgenetics.coordinate.collector.Utils.entriesTable(
            this.entriesTableInstance, this.gridsTable(), this.activity);
    }

    // region Preference Private Methods
    private org.wheatgenetics.coordinate.preference.Utils.Advancement getAdvancement()
    { return org.wheatgenetics.coordinate.preference.Utils.getAdvancement(this.activity); }

    private boolean getUniqueness()
    { return org.wheatgenetics.coordinate.preference.Utils.getUniqueness(this.activity); }

    private boolean getSoundOn()
    { return org.wheatgenetics.coordinate.preference.Utils.getSoundOn(this.activity); }
    // endregion

    private void goToNext(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (this.joinedGridModelIsLoaded() && null != gridsTable)
            if (this.joinedGridModel.goToNext(entryModel, this.getAdvancement(),this))
            {
                gridsTable.update(this.joinedGridModel);             // Update activeRow, activeCol.
                this.populateFragments();
            }
    }

    private void setEntry(final java.lang.String entry)
    { if (null != this.dataEntryFragment) this.dataEntryFragment.setEntry(entry); }

    private void handleDuplicateCheckException(
    @androidx.annotation.NonNull final java.lang.String message)
    {
        if (this.getSoundOn())
        {
            if (null == this.disallowedDuplicateMediaPlayer)
                this.disallowedDuplicateMediaPlayer = android.media.MediaPlayer.create(
                    this.activity, org.wheatgenetics.coordinate.R.raw.unsure);
            this.disallowedDuplicateMediaPlayer.start();
        }

        if (null == this.uniqueAlertDialog) this.uniqueAlertDialog =
            new org.wheatgenetics.coordinate.collector.UniqueAlertDialog(this.activity);
        this.uniqueAlertDialog.show(message);
    }

    @androidx.annotation.NonNull private org.wheatgenetics.zxing.BarcodeScanner barcodeScanner()
    {
        if (null == this.barcodeScannerInstance) this.barcodeScannerInstance =
            new org.wheatgenetics.zxing.BarcodeScanner(this.activity);
        return this.barcodeScannerInstance;
    }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        return org.wheatgenetics.coordinate.collector.Utils.gridsTable(
            this.gridsTableInstance, this.activity);
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseCollector(@androidx.annotation.NonNull
    final androidx.appcompat.app.AppCompatActivity activity)
    {
        super();

        this.activity = activity;

        @androidx.annotation.NonNull
        final androidx.fragment.app.FragmentManager fragmentManager =
            this.activity.getSupportFragmentManager();
        this.gridDisplayFragment = (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment)
            fragmentManager.findFragmentById(org.wheatgenetics.coordinate.R.id.gridDisplayFragment);
        this.dataEntryFragment = (org.wheatgenetics.coordinate.collector.DataEntryFragment)
            fragmentManager.findFragmentById(org.wheatgenetics.coordinate.R.id.dataEntryFragment);
    }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.joinedGridModel; }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                this.entriesTable();
            if (null != entriesTable)
            {
                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                    (org.wheatgenetics.coordinate.model.EntryModel) elementModel;
                if (this.joinedGridModel instanceof
                org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                {
                    final org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel
                        currentGridUniqueJoinedGridModel =
                            (org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
                                this.joinedGridModel;
                    try
                    {
                        currentGridUniqueJoinedGridModel.checkThenSetEntryModel(entryModel);//throws
                    }
                    catch (final
                    org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException e)
                    { return; }
                }
                else this.joinedGridModel.setEntryModel(entryModel);
                entriesTable.insertOrUpdate(entryModel);
                if (entryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                    if (this.joinedGridModel.getActiveEntryModel() == entryModel)
                        this.goToNext(entryModel);
            }
        }
    }

    @java.lang.Override public int getActiveRow()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveRow() : -1; }

    @java.lang.Override public int getActiveCol()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getActiveCol() : -1; }

    @java.lang.Override public void activate(final int row, final int col)
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        if (null != gridsTable)
        {
            if (this.joinedGridModelIsLoaded())
                if (this.joinedGridModel.setActiveRowAndActiveCol(row, col))
                    gridsTable.update(this.joinedGridModel);

            this.setEntry(this.getEntryValue());
        }
    }

    @java.lang.Override @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker()
    {
        if (this.joinedGridModel instanceof
        org.wheatgenetics.coordinate.model.CurrentGridUniqueJoinedGridModel)
            return (org.wheatgenetics.coordinate.model.CurrentGridUniqueEntryModels)
                this.joinedGridModel.getEntryModels();
        else
            return null;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.EntryModels.FilledHandler Overridden Methods
    @java.lang.Override public void handleFilledGrid()
    {
        org.wheatgenetics.coordinate.Utils.alert(this.activity,
            org.wheatgenetics.coordinate.R.string.MainActivityFilledGridAlertMessage);

        if (this.getSoundOn())
        {
            if (null == this.gridEndMediaPlayer)
                this.gridEndMediaPlayer = android.media.MediaPlayer.create(
                    this.activity, org.wheatgenetics.coordinate.R.raw.plonk);
            this.gridEndMediaPlayer.start();
        }
    }

    @java.lang.Override public void handleFilledRowOrCol()
    {
        if (this.getSoundOn())
        {
            if (null == this.rowOrColumnEndMediaPlayer)
                this.rowOrColumnEndMediaPlayer = android.media.MediaPlayer.create(
                    this.activity, org.wheatgenetics.coordinate.R.raw.row_or_column_end);
            this.rowOrColumnEndMediaPlayer.start();
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public java.lang.String getEntryValue()
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            return null == activeEntryModel ? null : activeEntryModel.getValue();
        }
        else return null;
    }

    @java.lang.Override public java.lang.String getProjectTitle()
    {
        if (this.joinedGridModelIsLoaded())
        {
            @androidx.annotation.IntRange(from = 0) final long projectId =
                this.joinedGridModel.getProjectId();
            if (org.wheatgenetics.coordinate.model.Model.illegal(projectId))
                return "none";
            else
            {
                final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                    this.projectsTable().get(projectId);
                return null == projectModel ? "none" : projectModel.getTitle();
            }
        }
        else return "";
    }

    @java.lang.Override public java.lang.String getTemplateTitle()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getTemplateTitle() : ""; }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.optionalFields() : null; }

    @java.lang.Override public void saveEntry(final java.lang.String entryValue)
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
                this.joinedGridModel.getActiveEntryModel();
            if (activeEntryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.database.EntriesTable entriesTable =
                    this.entriesTable();
                if (null != entriesTable)
                {
                    final org.wheatgenetics.coordinate.model.IncludedEntryModel
                        activeIncludedEntryModel =
                            (org.wheatgenetics.coordinate.model.IncludedEntryModel)
                                activeEntryModel;
                    if (this.getUniqueness())
                    {
                        final java.lang.String oldEntryValue = activeIncludedEntryModel.getValue();
                        final org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
                            checkedIncludedEntryModel =
                                (org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel)
                                    activeIncludedEntryModel;
                        try
                        { checkedIncludedEntryModel.checkThenSetValue(entryValue) /* throws */; }
                        catch (final
                        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.CheckException
                        e)
                        {
                            if (e instanceof org.wheatgenetics.coordinate.model
                            .UniqueEntryModels.DuplicateCheckException)
                            {
                                {
                                    final java.lang.String message = e.getMessage();
                                    this.handleDuplicateCheckException(
                                        null == message ? e.toString() : message);
                                }
                                this.setEntry(oldEntryValue);
                            }
                            return;
                        }
                    }
                    else activeIncludedEntryModel.setValue(entryValue);

                    entriesTable.insertOrUpdate(activeIncludedEntryModel);
                }
            }
            this.goToNext(activeEntryModel);
        }
    }

    @java.lang.Override public void clearEntry() { this.saveEntry(null); }
    // endregion
    // endregion

    // region Public Methods
    public boolean joinedGridModelIsLoaded() { return null != this.joinedGridModel; }

    public void populateFragments()
    {
        if (null != this.gridDisplayFragment) this.gridDisplayFragment.populate();
        if (null != this.dataEntryFragment  ) this.dataEntryFragment.populate  ();
    }

    // region loadJoinedGridModel() Public Methods
    public void loadJoinedGridModel(@androidx.annotation.IntRange(from = 0) final long gridId)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(gridId))
            this.joinedGridModel = null;
        else
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            this.joinedGridModel = null == gridsTable ? null : gridsTable.get(gridId);
        }
    }

    public void loadJoinedGridModelThenPopulate(@androidx.annotation.IntRange(from = 0)
    final long gridId) { this.loadJoinedGridModel(gridId); this.populateFragments(); }
    // endregion

    public void reloadIfNecessary()
    {
        if (org.wheatgenetics.coordinate.collector.Utils.gridsTableNeedsReloading(
        this.gridsTableInstance, this.activity))
            this.gridsTableInstance = null;

        if (org.wheatgenetics.coordinate.collector.Utils.entriesTableNeedsReloading(
        this.entriesTableInstance, this.activity))
            this.entriesTableInstance = null;

        if (org.wheatgenetics.coordinate.collector.Utils.joinedGridModelNeedsReloading(
        this.joinedGridModel, this.activity))
            this.loadJoinedGridModelThenPopulate(this.joinedGridModel.getId());
    }

    // region Barcode Public Methods
    @java.lang.SuppressWarnings({"SameReturnValue"}) public boolean scanBarcode()
    { this.barcodeScanner().scan(); return true; }

    public boolean parseActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        final boolean handled;
        {
            final java.lang.String barcodeScannerResult =
                org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                    requestCode, resultCode, data);
            if (null == barcodeScannerResult)
                handled = false;
            else
            {
                this.setEntry(barcodeScannerResult); this.saveEntry(barcodeScannerResult);
                handled = true;
            }
        }
        return handled;
    }
    // endregion

    public void release()
    {
        if (null != this.disallowedDuplicateMediaPlayer)
            this.disallowedDuplicateMediaPlayer.release();
        if (null != this.rowOrColumnEndMediaPlayer) this.rowOrColumnEndMediaPlayer.release();
        if (null != this.gridEndMediaPlayer       ) this.gridEndMediaPlayer.release       ();
    }
    // endregion
}