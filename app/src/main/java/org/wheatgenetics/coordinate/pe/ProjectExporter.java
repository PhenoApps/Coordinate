package org.wheatgenetics.coordinate.pe;

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
 *
 * org.wheatgenetics.coordinate.Consts
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.ProjectExport
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.PerGridProjectExporter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectExporter extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity   ;
                                 private final int                  requestCode;

    @androidx.annotation.IntRange(from = 1) private long             projectId    ;
                                            private java.lang.String directoryName;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load

    private org.wheatgenetics.coordinate.model.PerGridProjectExporter perGridProjectExporter = null;
    private org.wheatgenetics.coordinate.model.EntireProjectProjectExporter
        entireProjectProjectExporter = null;
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
                                                                        //  Exception
        final org.wheatgenetics.androidlibrary.RequestDir result =
            org.wheatgenetics.coordinate.Utils.makeRequestDir(this.activity,
                org.wheatgenetics.coordinate.Consts.COORDINATE_DIR_NAME + "/Export",
                this.requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }
    // endregion

    public ProjectExporter(
    @androidx.annotation.NonNull final android.app.Activity activity   ,
                                 final int                  requestCode)
    { super(); this.activity = activity; this.requestCode = requestCode; }

    @java.lang.Override protected void finalize() throws java.lang.Throwable
    {
        if (null != this.entireProjectProjectExporter)
        {
            this.entireProjectProjectExporter.cancel();
            this.entireProjectProjectExporter = null;
        }

        if (null != this.perGridProjectExporter)
            { this.perGridProjectExporter.cancel(); this.perGridProjectExporter = null; }

        super.finalize();
    }

    // region Public Methods
    public void exportProject()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.gridsTable().loadByProjectId(this.projectId);
        if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.Utils.ProjectExport projectExport =
                org.wheatgenetics.coordinate.Utils.getProjectExport(this.activity);
            if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_PER_GRID
            == projectExport)
            {
                org.wheatgenetics.androidlibrary.RequestDir exportDir;

                final org.wheatgenetics.coordinate.model.JoinedGridModel firstJoinedGridModel =
                    baseJoinedGridModels.get(0);
                if (null == firstJoinedGridModel)
                    exportDir = null;
                else
                    try
                    {
                        final org.wheatgenetics.androidlibrary.RequestDir parentDir =
                            new org.wheatgenetics.androidlibrary.RequestDir(
                                /* activity => */ this.activity   ,
                                /* parent   => */ this.exportDir(),          // throws IOException,
                                                                             //  PermissionException
                                /* name        => */ firstJoinedGridModel.getTemplateTitle(),
                                /* requestCode => */ this.requestCode                       );
                        parentDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
                        exportDir = new org.wheatgenetics.androidlibrary.RequestDir(
                            /* activity    => */ this.activity     ,
                            /* parent      => */ parentDir         ,
                            /* name        => */ this.directoryName,
                            /* requestCode => */ this.requestCode  );
                        exportDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                    }                                    //  netics.javalib.Dir.PermissionException
                    catch (final java.io.IOException |
                    org.wheatgenetics.javalib.Dir.PermissionException e)
                    {
                        if (!(e instanceof
                        org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                            this.showLongToast(e.getMessage());
                        exportDir = null;
                    }

                if (null != exportDir)
                {
                    this.perGridProjectExporter =
                        new org.wheatgenetics.coordinate.model.PerGridProjectExporter(
                            /* baseJoinedGridModels => */ baseJoinedGridModels,
                            /* context              => */ this.activity       ,
                            /* exportDir            => */ exportDir           ,
                            /* exportDirectoryName  => */ this.directoryName  );
                    this.perGridProjectExporter.execute();
                }
            }
            else if (org.wheatgenetics.coordinate.Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT
            == projectExport)
                try
                {
                    this.entireProjectProjectExporter =
                        new org.wheatgenetics.coordinate.model.EntireProjectProjectExporter(
                            /* baseJoinedGridModels => */ baseJoinedGridModels,
                            /* context              => */ this.activity       ,
                            /* exportDir            => */ this.exportDir(),  // throws IOException,
                                                                             //  PermissionException
                            /* exportFileName => */ this.directoryName);
                    this.entireProjectProjectExporter.execute();
                }
                catch (final java.io.IOException |
                org.wheatgenetics.javalib.Dir.PermissionException e)
                {
                    if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                        this.showLongToast(e.getMessage());
                }
        }
    }

    public void export(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.projectId = projectId; this.directoryName = directoryName; this.exportProject(); }
    // endregion
}