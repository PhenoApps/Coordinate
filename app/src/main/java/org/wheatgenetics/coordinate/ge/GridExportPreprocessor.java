package org.wheatgenetics.coordinate.ge;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.GetExportFileNameAlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

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
        getGridExportFileNameAlertDialogInstance.setMessage(R.string.grid_export_dialog_directory_message);
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

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void exportGrid(
                @IntRange(from = 1) long gridId, String fileName);
    }
}