package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * org.wheatgenetics.coordinate.viewmodel.ExportingViewModel
 */
@java.lang.SuppressWarnings({"WeakerAccess"})
public class TemplatesViewModel extends org.wheatgenetics.coordinate.viewmodel.ExportingViewModel
{
    private java.lang.String importFileName;

    // region Package Methods
    void setImportFileName(final java.lang.String importFileName)
    { this.importFileName = importFileName; }

    java.lang.String getImportFileName() { return this.importFileName; }
    // endregion
}