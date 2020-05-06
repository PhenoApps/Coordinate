package org.wheatgenetics.coordinate.ge;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.androidlibrary.RequestDir;
import org.wheatgenetics.coordinate.Consts;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.javalib.Dir;

import java.io.IOException;

public class GridExporter implements org.wheatgenetics.coordinate.exporter.GridExporter.Helper {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;
    @NonNull
    private final
    GridDeleter.Handler handler;

    private GridsTable gridsTableInstance = null; // lazy load

    private JoinedGridModel joinedGridModel;
    private String fileName;

    private GridDeleter gridDeleterInstance = null;     // ll
    private org.wheatgenetics.coordinate.exporter.GridExporter gridExporter = null;
    // endregion

    public GridExporter(@NonNull final Activity activity,
                        final int requestCode,
                        @NonNull final
                        GridDeleter.Handler handler) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
        this.handler = handler;
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

    /**
     * Exported data is saved to this folder.
     */
    private RequestDir exportDir()
            throws IOException, Dir.PermissionException {
        Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
                this.activity, this.requestCode);                           //  tion, org.wheatgenetics-
        //  .javalib.Dir.Permission-
        final RequestDir result =      //  Exception
                Utils.makeRequestDir(this.activity,
                        Consts.COORDINATE_DIR_NAME + "/Export",
                        this.requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }
    // endregion

    private GridDeleter gridDeleter() {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
                new GridDeleter(this.activity, this.handler);
        return this.gridDeleterInstance;
    }

    // region Overridden Methods
    @Override
    protected void finalize() throws Throwable {
        if (null != this.gridExporter) {
            this.gridExporter.cancel();
            this.gridExporter = null;
        }
        super.finalize();
    }

    // region org.wheatgenetics.coordinate.exporter.GridExporter.Helper Overridden Methods
    @Override
    public void deleteGrid() {
        final JoinedGridModel joinedGridModel =
                this.getJoinedGridModel();
        if (null != joinedGridModel)
            this.gridDeleter().deleteWithoutConfirm(joinedGridModel.getId());
    }

    @Override
    public JoinedGridModel getJoinedGridModel() {
        return this.joinedGridModel;
    }
    // endregion
    // endregion

    // region Public Methods
    public void export() {
        final JoinedGridModel joinedGridModel =
                this.getJoinedGridModel();
        if (null != joinedGridModel)
            try {
                final RequestDir exportDir =
                        new RequestDir(
                                /* activity    => */ this.activity,
                                /* parent      => */ this.exportDir(),   // throws IOE, PE
                                /* name        => */ joinedGridModel.getTemplateTitle(),
                                /* requestCode => */ this.requestCode);
                exportDir.createIfMissing();             // throws java.io.IOException, org.wheatge-
                //  netics.javalib.Dir.PermissionException
                this.gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(
                        /* context    => */ this.activity,
                        /* exportFile => */ exportDir.createNewFile(   // throws org.wheatgenetics.java-
                        this.fileName + ".csv"),          //  lib.Dir.PermissionException
                        /* exportFileName => */ this.fileName,
                        /* helper         => */this);
                this.gridExporter.execute();
            } catch (final IOException | Dir.PermissionException e) {
                if (!(e instanceof Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
            }
    }

    public void export(@IntRange(from = 1) final long gridId,
                       final String fileName) {
        final JoinedGridModel joinedGridModel =
                this.gridsTable().get(gridId);
        if (null != joinedGridModel) {
            this.joinedGridModel = joinedGridModel;
            this.fileName = fileName;
            this.export();
        }
    }
    // endregion
}