package org.wheatgenetics.coordinate.viewmodel;

/**
 * Uses:
 * androidx.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.viewmodel.ViewModel
 */
public class ExportingViewModel extends org.wheatgenetics.coordinate.viewmodel.ViewModel
{
    private java.lang.String exportFileName;

    // region Public Methods
    public void setIdAndExportFileName(@androidx.annotation.IntRange(from = 1) final long id,
    final java.lang.String exportFileName) { this.setId(id); this.exportFileName = exportFileName; }

    public java.lang.String getExportFileName() { return this.exportFileName; }
    // endregion
}