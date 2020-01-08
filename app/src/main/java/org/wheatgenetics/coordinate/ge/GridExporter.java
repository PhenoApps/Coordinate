package org.wheatgenetics.coordinate.ge;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Consts
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.exporter.GridExporter
 * org.wheatgenetics.coordinate.exporter.GridExporter.Helper
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class GridExporter
extends java.lang.Object implements org.wheatgenetics.coordinate.exporter.GridExporter.Helper
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity   ;
                                 private final int                  requestCode;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.deleter.GridDeleter.Handler handler;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load

    private org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel;
    private java.lang.String                                   fileName       ;

    private org.wheatgenetics.coordinate.deleter.GridDeleter   gridDeleterInstance = null;     // ll
    private org.wheatgenetics.coordinate.exporter.GridExporter gridExporter        = null;
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    /** Exported data is saved to this folder. */
    private org.wheatgenetics.androidlibrary.RequestDir exportDir()
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        org.wheatgenetics.coordinate.Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
            this.activity, this.requestCode);                           //  tion, org.wheatgenetics-
                                                                        //  .javalib.Dir.Permission-
        final org.wheatgenetics.androidlibrary.RequestDir result =      //  Exception
            org.wheatgenetics.coordinate.Utils.makeRequestDir(this.activity,
                org.wheatgenetics.coordinate.Consts.COORDINATE_DIR_NAME + "/Export",
                this.requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }

    private org.wheatgenetics.coordinate.deleter.GridDeleter gridDeleter()
    {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.GridDeleter(this.activity, this.handler);
        return this.gridDeleterInstance;
    }
    // endregion

    public GridExporter(@androidx.annotation.NonNull final android.app.Activity activity   ,
                                                     final int                  requestCode,
                        @androidx.annotation.NonNull final
                            org.wheatgenetics.coordinate.deleter.GridDeleter.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override protected void finalize() throws java.lang.Throwable
    {
        if (null != this.gridExporter) { this.gridExporter.cancel(); this.gridExporter= null; }
        super.finalize();
    }

    // region org.wheatgenetics.coordinate.exporter.GridExporter.Helper Overridden Methods
    @java.lang.Override public void deleteGrid()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            this.getJoinedGridModel();
        if (null != joinedGridModel)
            this.gridDeleter().deleteWithoutConfirm(joinedGridModel.getId());
    }

    @java.lang.Override
    public org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel()
    { return this.joinedGridModel; }
    // endregion
    // endregion

    // region Public Methods
    public void export()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            this.getJoinedGridModel();
        if (null != joinedGridModel)
            try
            {
                final org.wheatgenetics.androidlibrary.RequestDir exportDir =
                    new org.wheatgenetics.androidlibrary.RequestDir(
                        /* activity    => */ this.activity                     ,
                        /* parent      => */ this.exportDir()                  ,   // throws IOE, PE
                        /* name        => */ joinedGridModel.getTemplateTitle(),
                        /* requestCode => */ this.requestCode                  );
                exportDir.createIfMissing();             // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
                this.gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(
                    /* context    => */ this.activity,
                    /* exportFile => */ exportDir.createNewFile(   // throws org.wheatgenetics.java-
                        this.fileName + ".csv"),          //  lib.Dir.PermissionException
                    /* exportFileName => */ this.fileName,
                    /* helper         => */this);
                this.gridExporter.execute();
            }
            catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
            {
                if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
            }
    }

    public void export(@androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                                               final java.lang.String fileName)
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            this.gridsTable().get(gridId);
        if (null != joinedGridModel)
        {
            this.joinedGridModel = joinedGridModel; this.fileName = fileName;
            this.export();
        }
    }
    // endregion
}