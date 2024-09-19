package org.wheatgenetics.coordinate.ge;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.permissions.RequestDir;
import org.wheatgenetics.coordinate.Consts;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.phenoapps.permissions.Dir;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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
        org.phenoapps.androidlibrary.Utils.showLongToast(this.activity, text);
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

                File exportFile;

                //check the sdk, if it is below 30 (R) then we don't use scoped storage
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    final RequestDir exportDir =
                            new RequestDir(
                                    /* activity    => */ this.activity,
                                    /* parent      => */ this.exportDir(),   // throws IOE, PE
                                    /* name        => */ joinedGridModel.getTemplateTitle(),
                                    /* requestCode => */ this.requestCode);
                    exportDir.createIfMissing();             // throws java.io.IOException, org.wheatge-
                    exportFile = exportDir.createNewFile(this.fileName + ".csv");

                    //  netics.javalib.Dir.PermissionException
                    this.gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(
                            /* context    => */ this.activity,
                            /* exportFile => */ exportFile,          //  lib.Dir.PermissionException
                            /* exportFileName => */ this.fileName,
                            /* helper         => */this);
                    this.gridExporter.execute();

                } else { //otherwise use the context to get the files directory

                    if (DocumentTreeUtil.Companion.isEnabled(activity)) {

                        DocumentFile exports = DocumentTreeUtil.Companion.createDir(activity, "Exports");

                        if (exports != null && exports.exists()) {

                            DocumentFile templateDir = DocumentTreeUtil.Companion.createDir(activity, "Exports", joinedGridModel.getTemplateTitle());

                            if (templateDir != null && templateDir.exists()) {

                                DocumentFile doc = templateDir.createFile("*/*", this.fileName + ".csv");

                                if (doc != null) {

                                    OutputStream output = this.activity.getContentResolver().openOutputStream(doc.getUri());
                                    this.gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(this.activity, output, this.fileName, this);
                                    this.gridExporter.execute();

                                    FileUtil.Companion.shareFile(activity, doc.getUri());
                                }
                            }
                        }

                    } else {
                        File exportDir = new File(activity.getExternalFilesDir(null), "Exports");
                        if (!exportDir.isDirectory()) {
                            if (!exportDir.mkdir()) {
                                //something went wrong making dir
                            }
                        }

                        File templateDir = new File(exportDir, joinedGridModel.getTemplateTitle());

                        if (!templateDir.isDirectory()){
                            if (!templateDir.mkdir()) {

                            }
                        }

                        exportFile = new File(templateDir, this.fileName + ".csv");

                        //  netics.javalib.Dir.PermissionException
                        this.gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(
                                /* context    => */ this.activity,
                                /* exportFile => */ exportFile,          //  lib.Dir.PermissionException
                                /* exportFileName => */ this.fileName,
                                /* helper         => */this);
                        this.gridExporter.execute();

                        FileUtil.Companion.shareFile(activity, Uri.fromFile(exportFile));
                    }
                }

            } catch (final IOException | Dir.PermissionException e) {
                if (!(e instanceof Dir.PermissionRequestedException))
                    this.showLongToast(e.getMessage());
            }
    }

    private void export(OutputStream output) {

        gridExporter = new org.wheatgenetics.coordinate.exporter.GridExporter(activity, output, this.fileName, this);
        gridExporter.execute();
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

    public void export(final long gridId,
                       final String fileName,
                       final OutputStream output) {
        final JoinedGridModel joinedGridModel =
                this.gridsTable().get(gridId);
        if (null != joinedGridModel) {
            this.joinedGridModel = joinedGridModel;
            this.fileName = fileName;
            this.export(output);
        }
    }
    // endregion
}