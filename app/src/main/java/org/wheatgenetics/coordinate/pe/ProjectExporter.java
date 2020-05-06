package org.wheatgenetics.coordinate.pe;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.androidlibrary.RequestDir;
import org.wheatgenetics.coordinate.Consts;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.exporter.EntireProjectProjectExporter;
import org.wheatgenetics.coordinate.exporter.PerGridProjectExporter;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.preference.Utils;
import org.wheatgenetics.javalib.Dir;

import java.io.IOException;

public class ProjectExporter {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;

    @IntRange(from = 1)
    private long projectId;
    private String directoryName;

    private GridsTable gridsTableInstance = null; // lazy load

    private PerGridProjectExporter
            perGridProjectExporter = null;
    private EntireProjectProjectExporter
            entireProjectProjectExporter = null;
    // endregion

    public ProjectExporter(@NonNull final Activity activity,
                           final int requestCode) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
    }

    // region Private Methods
    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    private void showLongToast(final String text) {
        org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text);
    }
    // endregion

    /**
     * Exported data is saved to this folder.
     */
    private RequestDir exportDir()
            throws IOException, Dir.PermissionException {
        org.wheatgenetics.coordinate.Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
                this.activity, this.requestCode);                           //  tion, org.wheatgenetics-
        //  .javalib.Dir.Permission-
        //  Exception
        final RequestDir result =
                org.wheatgenetics.coordinate.Utils.makeRequestDir(this.activity,
                        Consts.COORDINATE_DIR_NAME + "/Export",
                        this.requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }

    @Override
    protected void finalize() throws Throwable {
        if (null != this.entireProjectProjectExporter) {
            this.entireProjectProjectExporter.cancel();
            this.entireProjectProjectExporter = null;
        }

        if (null != this.perGridProjectExporter) {
            this.perGridProjectExporter.cancel();
            this.perGridProjectExporter = null;
        }

        super.finalize();
    }

    // region Public Methods
    public void export() {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(this.projectId);
        if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0) {
            final Utils.ProjectExport projectExport =
                    Utils.getProjectExport(this.activity);
            if (Utils.ProjectExport.ONE_FILE_PER_GRID
                    == projectExport) {
                RequestDir exportDir;

                final JoinedGridModel firstJoinedGridModel =
                        baseJoinedGridModels.get(0);
                if (null == firstJoinedGridModel)
                    exportDir = null;
                else
                    try {
                        final RequestDir parentDir =
                                new RequestDir(
                                        /* activity => */ this.activity,
                                        /* parent   => */ this.exportDir(),          // throws IOException,
                                        //  PermissionException
                                        /* name        => */ firstJoinedGridModel.getTemplateTitle(),
                                        /* requestCode => */ this.requestCode);
                        parentDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                        //  netics.javalib.Dir.PermissionException
                        exportDir = new RequestDir(
                                /* activity    => */ this.activity,
                                /* parent      => */ parentDir,
                                /* name        => */ this.directoryName,
                                /* requestCode => */ this.requestCode);
                        exportDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
                    }                                    //  netics.javalib.Dir.PermissionException
                    catch (final IOException |
                            Dir.PermissionException e) {
                        if (!(e instanceof
                                Dir.PermissionRequestedException))
                            this.showLongToast(e.getMessage());
                        exportDir = null;
                    }

                if (null != exportDir) {
                    this.perGridProjectExporter =
                            new PerGridProjectExporter(
                                    /* baseJoinedGridModels => */ baseJoinedGridModels,
                                    /* context              => */ this.activity,
                                    /* exportDir            => */ exportDir,
                                    /* exportDirectoryName  => */ this.directoryName);
                    this.perGridProjectExporter.execute();
                }
            } else if (
                    Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT
                            == projectExport)
                try {
                    this.entireProjectProjectExporter =
                            new EntireProjectProjectExporter(
                                    /* baseJoinedGridModels => */ baseJoinedGridModels,
                                    /* context              => */ this.activity,
                                    /* exportDir            => */ this.exportDir(),  // throws IOException,
                                    //  PermissionException
                                    /* exportFileName => */ this.directoryName);
                    this.entireProjectProjectExporter.execute();
                } catch (final IOException |
                        Dir.PermissionException e) {
                    if (!(e instanceof Dir.PermissionRequestedException))
                        this.showLongToast(e.getMessage());
                }
        }
    }

    public void export(@IntRange(from = 1) final long projectId,
                       final String directoryName) {
        this.projectId = projectId;
        this.directoryName = directoryName;
        this.export();
    }
    // endregion
}