package org.wheatgenetics.coordinate.pe;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import org.phenoapps.permissions.Dir;
import org.phenoapps.permissions.RequestDir;
import org.phenoapps.utils.BaseDocumentTreeUtil;
import org.wheatgenetics.coordinate.Consts;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.exporter.EntireProjectProjectExporter;
import org.wheatgenetics.coordinate.exporter.PerGridProjectExporter;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.preference.Utils;
import org.wheatgenetics.coordinate.utils.FileUtil;
import org.wheatgenetics.coordinate.utils.ZipUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectExporter {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;

    @IntRange(from = 1)
    private long projectId;
    private String directoryName;
    private OutputStream outputStream = null;

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
        org.wheatgenetics.coordinate.Utils.showLongToast(this.activity, text);
    }
    // endregion

    /**
     * Exported data is saved to this folder.
     */
    private RequestDir exportDir() throws IOException, Dir.PermissionException {
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

    private File getExportDir() {
        File parentDir = new File(activity.getExternalFilesDir(null), "Export");
        if (!parentDir.isDirectory()) {
            if (!parentDir.mkdir()) {
                //todo log message
            }
        }

        return parentDir;
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

    private void exportDirectory(BaseJoinedGridModels baseJoinedGridModels, JoinedGridModel firstJoinedGridModel, File exportDir) {
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
            RequestDir requestDir = new RequestDir(
                    /* activity    => */ this.activity,
                    /* parent      => */ parentDir,
                    /* name        => */ this.directoryName,
                    /* requestCode => */ this.requestCode);
            exportDir = requestDir.createIfMissing();     // throws java.io.IOException, org.wheatge-
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
                File exportDir = null;

                final JoinedGridModel firstJoinedGridModel =
                        baseJoinedGridModels.get(0);
                if (null == firstJoinedGridModel)
                    exportDir = null;
                else

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

                        exportDirectory(baseJoinedGridModels, firstJoinedGridModel, exportDir);

                    } else if (BaseDocumentTreeUtil.Companion.isEnabled(activity)) {

                        if (outputStream == null) {

                            DocumentFile dir = BaseDocumentTreeUtil.Companion.createDir(activity, activity.getString(R.string.export_dir));

                            if (dir != null) {

                                DocumentFile docFile = dir.createFile("*/zip", directoryName + ".zip");

                                ContentResolver resolver = activity.getContentResolver();

                                if (resolver != null) {

                                    if (docFile != null) {

                                        try {

                                            OutputStream output = resolver.openOutputStream(docFile.getUri());

                                            zipProject(baseJoinedGridModels, output);

                                            FileUtil.Companion.shareFile(activity, docFile.getUri());

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }

                        } else zipProject(baseJoinedGridModels, outputStream);

                    } else if (outputStream != null) {

                        zipProject(baseJoinedGridModels, outputStream);

                    } else {

                        File exportParentDir = new File(activity.getExternalFilesDir(null), activity.getString(R.string.export_dir));
                        checkMakeDir(exportParentDir);

                        if (exportParentDir.isDirectory()) {
                            File templateDir = new File(exportParentDir, firstJoinedGridModel.getTemplateTitle());
                            checkMakeDir(templateDir);

                            File projectDir = new File(templateDir, directoryName);
                            checkMakeDir(projectDir);

                            exportDir = projectDir;
                        }

                        if (null != exportDir) {
                            this.perGridProjectExporter =
                                    new PerGridProjectExporter(
                                            /* baseJoinedGridModels => */ baseJoinedGridModels,
                                            /* context              => */ this.activity,
                                            /* exportDir            => */ exportDir,
                                            /* exportDirectoryName  => */ this.directoryName);
                            this.perGridProjectExporter.execute();

                            FileUtil.Companion.shareFile(activity, Uri.fromFile(exportDir));
                        }
                    }

            } else if (
                    Utils.ProjectExport.ONE_FILE_ENTIRE_PROJECT
                            == projectExport)
                try {

                    File exportFile = null;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        exportFile = exportDir().createIfMissing();
                        this.entireProjectProjectExporter =
                                new EntireProjectProjectExporter(
                                        /* baseJoinedGridModels => */ baseJoinedGridModels,
                                        /* context              => */ this.activity,
                                        /* exportDir            => */ exportFile,  // throws IOException,
                                        //  PermissionException
                                        /* exportFileName => */ this.directoryName);
                        this.entireProjectProjectExporter.execute();
                    } else if (BaseDocumentTreeUtil.Companion.isEnabled(activity)) {
                        this.entireProjectProjectExporter =
                                new EntireProjectProjectExporter(baseJoinedGridModels,
                                        this.activity, outputStream, this.directoryName);
                        this.entireProjectProjectExporter.execute();
                    } else {
                        exportFile = getExportDir();
                        this.entireProjectProjectExporter =
                                new EntireProjectProjectExporter(
                                        /* baseJoinedGridModels => */ baseJoinedGridModels,
                                        /* context              => */ this.activity,
                                        /* exportDir            => */ exportFile,  // throws IOException,
                                        //  PermissionException
                                        /* exportFileName => */ this.directoryName);
                        this.entireProjectProjectExporter.execute();
                    }

                } catch (final IOException |
                        Dir.PermissionException e) {
                    if (!(e instanceof Dir.PermissionRequestedException))
                        this.showLongToast(e.getMessage());
                }
        }
    }

    private void zipProject(BaseJoinedGridModels models, OutputStream output) {

        //set up a background thread
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            //create a temporary directory to put files in so we have known paths
            File exportParentDir = new File(activity.getExternalFilesDir(null), activity.getString(R.string.export_dir));
            exportParentDir.mkdir();

            //export files to the temporary directory
            ArrayList<String> files = new ArrayList<>();
            int size = models.size();
            for (int i = 0; i < size; i++ ) {
                JoinedGridModel model = models.get(i);
                if (model != null && model.getTemplateTitle() != null) {
                    File temp = new File(exportParentDir, model.getTemplateTitle() + ".csv");
                    try {
                        model.export(temp, model.getTemplateTitle() + ".csv");
                        files.add(temp.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //zip files from the temp directory to the output stream
            try {

                ZipUtil.Companion.zip(files.toArray(new String[] {}), output);

            } catch (IOException e) {
                e.printStackTrace();
            }

            //clean up the temporary files
            try {

                for(String path : files) {
                    new File(path).delete();
                }
                exportParentDir.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void checkMakeDir(File dir) {

        if (!dir.isDirectory()) {
            if (!dir.mkdir()) {
                Log.d("MakeDir", "Make dir failed for: " + dir.getName());
            }
        }
    }

    public void export(@IntRange(from = 1) final long projectId,
                       final String directoryName) {
        this.projectId = projectId;
        this.directoryName = directoryName;
        this.export();
    }

    public void export(final long projectId,
                       final String directoryName,
                       final OutputStream output) {
        this.projectId = projectId;
        this.directoryName = directoryName;
        this.outputStream = output;
        this.export();
    }
    // endregion
}