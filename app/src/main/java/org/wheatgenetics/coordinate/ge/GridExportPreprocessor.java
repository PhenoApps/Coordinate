package org.wheatgenetics.coordinate.ge;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.activity.DefineStorageActivity;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil;
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil.Companion.CheckDocumentResult;

public class GridExportPreprocessor {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final
    GridExportPreprocessor.Handler handler;
    @IntRange(from = 1)
    private long gridId;
    private GetExportFileNameAlertDialog
            getGridExportFileNameAlertDialogInstance = null;                                // lazy load
    private GridsTable gridsTableInstance = null; // lazy load
    public GridExportPreprocessor(@NonNull final Activity activity,
                                  @NonNull final
                                  GridExportPreprocessor.Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    // region getExportFileNameAlertDialog() Private Methods
    private void exportGrid(final String fileName) {
        this.handler.exportGrid(this.gridId, fileName);
    }

    @NonNull
    private GetExportFileNameAlertDialog getExportFileNameAlertDialog() {
        if (null == this.getGridExportFileNameAlertDialogInstance)
            this.getGridExportFileNameAlertDialogInstance =
                    new GetExportFileNameAlertDialog(this.activity,
                            new GetExportFileNameAlertDialog.Handler() {
                                @Override
                                public void handleGetFileNameDone(final String fileName) {
                                    GridExportPreprocessor.this.exportGrid(
                                            fileName);
                                }
                            });
        return this.getGridExportFileNameAlertDialogInstance;
    }
    // endregion
    // endregion

    public void preprocess(final long gridId) {
        final JoinedGridModel joinedGridModel =
                this.gridsTable().get(gridId);
        if (null != joinedGridModel) {
            this.gridId = gridId;
            this.getExportFileNameAlertDialog().show(
                    joinedGridModel.getFirstOptionalFieldDatedValue());
        }
    }

    public void handleExport(final long gridId, final String fileName, GridExporter gridExporter, ExportLauncher launcher) {
        // Set up the file name and ID in the view model or any other component
        if (DocumentTreeUtil.Companion.isEnabled(activity)) {
            DocumentTreeUtil.Companion.checkDir(activity, (result) -> {
                if (result == CheckDocumentResult.DISMISS) {
                    launcher.launch(fileName + ".csv");
                } else if (result == CheckDocumentResult.DEFINE) {
                    activity.startActivity(new Intent(activity, DefineStorageActivity.class));
                } else {
                    gridExporter.export(gridId, fileName);
                }
                return null;
            });
        } else {
            if (launcher != null)
                launcher.launch(fileName + ".csv");
        }
    }

    public interface ExportLauncher {
        void launch(String fileName);
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void exportGrid(
                @IntRange(from = 1) long gridId, String fileName);
    }
}