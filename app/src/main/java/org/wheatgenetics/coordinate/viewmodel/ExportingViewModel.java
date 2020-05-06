package org.wheatgenetics.coordinate.viewmodel;

import androidx.annotation.IntRange;

public class ExportingViewModel extends ViewModel {
    private String exportFileName;

    // region Public Methods
    public void setIdAndExportFileName(@IntRange(from = 1) final long id,
                                       final String exportFileName) {
        this.setId(id);
        this.exportFileName = exportFileName;
    }

    public String getExportFileName() {
        return this.exportFileName;
    }
    // endregion
}