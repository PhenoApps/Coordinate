package org.wheatgenetics.coordinate.ge;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class GridExportPreprocessor extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void exportGrid(
        @androidx.annotation.IntRange(from = 1) long gridId, java.lang.String fileName);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long                  gridId;
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getGridExportFileNameAlertDialogInstance = null;                                // lazy load
    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    // region getExportFileNameAlertDialog() Private Methods
    private void exportGrid(final java.lang.String fileName)
    { this.handler.exportGrid(this.gridId, fileName); }

    @androidx.annotation.NonNull private
    org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog getExportFileNameAlertDialog()
    {
        if (null == this.getGridExportFileNameAlertDialogInstance)
            this.getGridExportFileNameAlertDialogInstance =
                new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity,
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void handleGetFileNameDone(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.ge.GridExportPreprocessor.this.exportGrid(
                                fileName);
                        }
                    });
        return this.getGridExportFileNameAlertDialogInstance;
    }
    // endregion
    // endregion

    public GridExportPreprocessor(@androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    public void preprocess(final long gridId)
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            this.gridsTable().get(gridId);
        if (null != joinedGridModel)
        {
            this.gridId = gridId;
            this.getExportFileNameAlertDialog().show(
                joinedGridModel.getFirstOptionalFieldDatedValue());
        }
    }
}