package org.wheatgenetics.coordinate.ti;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateImportPreprocessor extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void importTemplate(java.lang.String fileName); }

    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity   ;
                                 private final int                  requestCode;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler handler;
    // endregion

    // region Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    // region selectExportedTemplate() Private Methods
    private void importTemplate(java.lang.String fileName)
    { this.handler.importTemplate(fileName); }

    private void selectExportedTemplate(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.TemplatesDir templatesDir)
    {
        // noinspection CStyleArrayDeclaration
        final java.lang.String fileNames[];
        try { fileNames = templatesDir.listXml() /* throws PermissionException */; }
        catch (final org.wheatgenetics.javalib.Dir.PermissionException e)
        { this.showLongToast(e.getMessage()); return; }

        if (null != fileNames) if (fileNames.length > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectAlertDialog =
                new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                    new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                    {
                        @java.lang.Override public void select(final int which)
                        {
                            org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
                                .this.importTemplate(fileNames[which]);
                        }
                    });
            selectAlertDialog.show(
                org.wheatgenetics.coordinate.R.string.SelectExportedTemplateTitle, fileNames);
        }
    }
    // endregion
    // endregion

    public TemplateImportPreprocessor(
    @androidx.annotation.NonNull final android.app.Activity activity, final int requestCode,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    public void preprocess()
    {
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                org.wheatgenetics.coordinate.Utils.templatesDir(             // throws IOException,
                    this.activity, this.requestCode);                        //  PermissionException
            this.selectExportedTemplate(templatesDir);
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
        }
    }
}