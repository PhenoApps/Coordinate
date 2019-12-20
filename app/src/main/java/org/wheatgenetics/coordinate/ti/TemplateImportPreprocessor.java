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
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateImportPreprocessor extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity                     activity   ;
                                 private final int                                      requestCode;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.Utils.Handler   handler;
    // endregion

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    public TemplateImportPreprocessor(
    @androidx.annotation.NonNull final android.app.Activity activity, final int requestCode,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.Utils.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    public void preprocess()
    {
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                org.wheatgenetics.coordinate.Utils.templatesDir(             // throws IOException,
                    this.activity, this.requestCode);                        //  PermissionException
            org.wheatgenetics.coordinate.Utils.selectExportedTemplate(
                templatesDir, this.activity, this.handler);
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
        }
    }
}