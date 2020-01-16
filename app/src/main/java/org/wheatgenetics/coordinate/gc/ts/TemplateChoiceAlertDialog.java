package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class TemplateChoiceAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void chooseOld(); public abstract void chooseNew(); }

    // region Fields
    @androidx.annotation.StringRes private final int firstItem;
    @androidx.annotation.NonNull   private final
        org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler handler;
    // endregion

    private void choose(final int which)
    {
        switch (which)
        {
            case 0: this.handler.chooseOld(); break;
            case 1: this.handler.chooseNew(); break;
        }
    }

    TemplateChoiceAlertDialog(final android.app.Activity activity,
    @androidx.annotation.StringRes final int firstItem, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler handler)
    { super(activity); this.firstItem = firstItem; this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogTitle)
            .setItems(new int[]{this.firstItem,
                    org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogNewItem},
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.this.choose(
                            which);
                    }
                });
    }
}