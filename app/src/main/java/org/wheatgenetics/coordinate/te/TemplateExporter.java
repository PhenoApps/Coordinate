package org.wheatgenetics.coordinate.te;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.exporter.TemplateExporter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateExporter extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity   ;
                                 private final int                  requestCode;

    @androidx.annotation.IntRange(from = 1) private long            templateId;
                                            private java.lang.String fileName ;

    private org.wheatgenetics.coordinate.exporter.TemplateExporter templateExporter = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable
        templatesTableInstance = null;                                                  // lazy load
    // endregion

    // region Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }
    // endregion

    public TemplateExporter(@androidx.annotation.NonNull final android.app.Activity activity   ,
                                                         final int                  requestCode)
    { super(); this.activity = activity; this.requestCode = requestCode; }

    @java.lang.Override protected void finalize() throws java.lang.Throwable
    {
        if (null != this.templateExporter)
            { this.templateExporter.cancel(); this.templateExporter = null; }
        super.finalize();
    }

    // region Public Methods
    public void export()
    {
        java.io.File exportFile;
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                org.wheatgenetics.coordinate.Utils.templatesDir(             // throws IOException,
                    this.activity, this.requestCode);                        //  PermissionException

            exportFile = templatesDir.createNewFile(      // throws IOException, PermissionException
                this.fileName + ".xml");
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            exportFile = null;
        }

        if (null != exportFile)
        {
            this.templateExporter = new org.wheatgenetics.coordinate.exporter.TemplateExporter(
                /* context       => */ this.activity                             ,
                /* exportFile    => */ exportFile                                ,
                /* templateModel => */ this.templatesTable().get(this.templateId));
            this.templateExporter.execute();
        }
    }

    public void export(@androidx.annotation.IntRange(from = 1) final long templateId,
    final java.lang.String fileName)
    { this.templateId = templateId; this.fileName = fileName; this.export(); }
    // endregion
}