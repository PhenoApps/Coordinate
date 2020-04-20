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
    // region Constructor Fields
    @androidx.annotation.StringRes private final int firstItem;
    @androidx.annotation.NonNull   private final
        org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler handler;
    // endregion

    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    private int                                             itemsInstance[]         = null;    // ll
    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;    // ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private int[] items()
    {
        if (null == this.itemsInstance) this.itemsInstance = new int[]{this.firstItem,
            org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogNewItem};
        return this.itemsInstance;
    }

    private void choose(final int which)
    {
        switch (which)
        {
            case 0: this.handler.chooseOld(); break;
            case 1: this.handler.chooseNew(); break;
        }
    }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override public void onClick(
                final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.this.choose(which); }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    TemplateChoiceAlertDialog(final android.app.Activity activity,
    @androidx.annotation.StringRes final int firstItem, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler handler)
    { super(activity); this.firstItem = firstItem; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogTitle); }

    @java.lang.Override public void show()
    { this.setItems(this.items(), this.onClickListener()); this.createShow(); }
    // endregion
}