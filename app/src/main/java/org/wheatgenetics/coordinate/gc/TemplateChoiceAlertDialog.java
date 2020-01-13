package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class TemplateChoiceAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void chooseOld(); public abstract void chooseNew(); }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.gc.TemplateChoiceAlertDialog.Handler handler;

    private void choose(final int which)
    {
        switch (which)
        {
            case 0: this.handler.chooseOld(); break;
            case 1: this.handler.chooseNew(); break;
        }
    }

    TemplateChoiceAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.TemplateChoiceAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogTitle)
            .setItems(new int[]{
                    org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogOldItem,
                    org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogNewItem},
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.coordinate.gc.TemplateChoiceAlertDialog.this.choose(
                            which);
                    }
                });
    }
}