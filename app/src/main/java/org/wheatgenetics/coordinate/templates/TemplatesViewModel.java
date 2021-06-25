package org.wheatgenetics.coordinate.templates;

import org.wheatgenetics.coordinate.viewmodel.ExportingViewModel;

public class TemplatesViewModel extends ExportingViewModel {
    private String importFileName;

    String getImportFileName() {
        return this.importFileName;
    }

    // region Package Methods
    void setImportFileName(final String importFileName) {
        this.importFileName = importFileName;
    }
    // endregion
}