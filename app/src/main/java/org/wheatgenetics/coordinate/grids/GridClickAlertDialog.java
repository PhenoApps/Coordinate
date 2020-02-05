package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler
 */
class GridClickAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract void collectData(@androidx.annotation.IntRange(from = 1) long gridId);

        public abstract void exportGrid(
        @androidx.annotation.IntRange(from = 1) long gridId, java.lang.String fileName);

        public abstract void respondToDeletedGrid();
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.grids.GridClickAlertDialog.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long gridId;

    private int[]                                           itemsInstance           = null;   // ll
    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;   // ll

    private org.wheatgenetics.coordinate.deleter.GridDeleter gridDeleterInstance = null;// lazy load
    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor
        gridExportPreprocessorInstance = null;                                          // lazy load
    // endregion

    // region Private Methods
    private void collectData() { this.handler.collectData(this.gridId); }

    // region deleteGrid() Private Methods
    private void respondToDeletedGrid() { this.handler.respondToDeletedGrid(); }

    private org.wheatgenetics.coordinate.deleter.GridDeleter gridDeleter()
    {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.GridDeleter(this.activity(),
                new org.wheatgenetics.coordinate.deleter.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.grids
                            .GridClickAlertDialog.this.respondToDeletedGrid();
                    }
                });
        return this.gridDeleterInstance;
    }

    private void deleteGrid() { this.gridDeleter().deleteWithConfirm(this.gridId); }
    // endregion

    // region exportGrid() Private Methods
    private void exportGrid(
    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                            final java.lang.String fileName)
    { this.handler.exportGrid(gridId, fileName); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor gridExportPreprocessor()
    {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.ge.GridExportPreprocessor(this.activity(),
                new org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportGrid(
                    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                                            final java.lang.String fileName)
                    {
                        org.wheatgenetics.coordinate.grids.GridClickAlertDialog.this.exportGrid(
                            gridId, fileName);
                    }
                });
        return this.gridExportPreprocessorInstance;
    }

    private void exportGrid() { this.gridExportPreprocessor().preprocess(this.gridId); }
    // endregion

    @androidx.annotation.NonNull private int[] items()
    {
        if (null == this.itemsInstance) this.itemsInstance = new int[]{
            org.wheatgenetics.coordinate.R.string.GridClickAlertDialogCollectData,
            org.wheatgenetics.coordinate.R.string.GridClickAlertDialogDeleteGrid ,
            org.wheatgenetics.coordinate.R.string.GridClickAlertDialogExportGrid };
        return this.itemsInstance;
    }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    switch (which)
                    {
                        case 0: org.wheatgenetics.coordinate.grids.GridClickAlertDialog
                            .this.collectData(); break;

                        case 1: org.wheatgenetics.coordinate.grids.GridClickAlertDialog
                            .this.deleteGrid(); break;

                        case 2: org.wheatgenetics.coordinate.grids.GridClickAlertDialog
                            .this.exportGrid(); break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    GridClickAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.grids.GridClickAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure() {}

    void show(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.gridId = gridId;
        this.setItems(this.items(), this.onClickListener());
        this.createShow();
    }
}