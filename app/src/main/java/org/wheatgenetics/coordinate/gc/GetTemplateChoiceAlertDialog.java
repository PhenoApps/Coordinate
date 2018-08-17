package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 */
class GetTemplateChoiceAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void chooseOld(); public abstract void chooseNew(); }

    private final org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler handler;

    private void choose(final int which)
    {
        assert null != this.handler; switch (which)
        {
            case 0: this.handler.chooseOld(); break;
            case 1: this.handler.chooseNew(); break;
        }
    }

    GetTemplateChoiceAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.GetTemplateChoiceAlertDialogTitle)
            .setItems(new int[]{
                    org.wheatgenetics.coordinate.R.string.GetTemplateChoiceAlertDialogOldItem,
                    org.wheatgenetics.coordinate.R.string.GetTemplateChoiceAlertDialogNewItem},
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.this.choose(
                            which);
                    }
                });
    }
}