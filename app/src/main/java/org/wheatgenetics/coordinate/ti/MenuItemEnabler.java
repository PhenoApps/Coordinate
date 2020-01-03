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
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class MenuItemEnabler extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity   ;
                                 private final int                  requestCode;
    // endregion

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    public MenuItemEnabler(@androidx.annotation.NonNull final android.app.Activity activity   ,
                                                        final int                  requestCode)
    { super(); this.activity = activity; this.requestCode = requestCode; }

    public boolean shouldBeEnabled()
    {
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                org.wheatgenetics.coordinate.Utils.templatesDir(  // throws java.io.IOException,
                    this.activity   ,                             //  org.wheatgenetics.javalib-
                    this.requestCode);                            //  .Dir.PermissionException
            return templatesDir.atLeastOneXmlFileExists();
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            return false;
        }
    }
}