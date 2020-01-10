package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources
 * android.view.View.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class AboutAlertDialog extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity              activity   ;
    @androidx.annotation.NonNull private final java.lang.String                  versionName;
    @androidx.annotation.NonNull private final android.view.View.OnClickListener
        versionOnClickListener;

    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialogInstance = null;   // lazy load
    // endregion

    @androidx.annotation.Nullable
    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog()
    {
        if (null == this.aboutAlertDialogInstance)
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String title, msgs[];
            {
                final android.content.res.Resources resources = this.activity.getResources();

                if (null == resources)
                    { title = null; msgs = null; }
                else
                {
                    title = resources.getString(
                        org.wheatgenetics.coordinate.R.string.AboutAlertDialogTitle);
                    msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                        org.wheatgenetics.coordinate.R.string.AboutAlertDialogMsg));
                }
            }

            // noinspection ConstantConditions
            if (null != title && null != msgs) this.aboutAlertDialogInstance =
                new org.wheatgenetics.about.AboutAlertDialog(
                    /* context       => */ this.activity                                     ,
                    /* title         => */ title                                             ,
                    /* versionName   => */ this.versionName                                  ,
                    /* msgs[]        => */ msgs                                              ,
                    /* suppressIndex => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                    /* versionOnClickListener => */ this.versionOnClickListener              );
        }
        return this.aboutAlertDialogInstance;
    }

    public AboutAlertDialog(
    @androidx.annotation.NonNull final android.app.Activity              activity              ,
    @androidx.annotation.NonNull final java.lang.String                  versionName           ,
    @androidx.annotation.NonNull final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.activity               = activity              ;
        this.versionName            = versionName           ;
        this.versionOnClickListener = versionOnClickListener;
    }

    public void show()
    {
        final org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = this.aboutAlertDialog();
        if (null != aboutAlertDialog) aboutAlertDialog.show();
    }
}