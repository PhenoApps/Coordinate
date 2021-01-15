package org.wheatgenetics.androidlibrary;

/**
 * Uses:
 * android.app.ProgressDialog
 * android.content.Context
 * android.content.DialogInterface.OnCancelListener
 *
 * androidx.annotation.StringRes
 */
@SuppressWarnings({"unused", "ClassExplicitlyExtendsObject"})
public class ProgressDialog extends Object
{
    // region Fields
    private final android.content.Context                          context         ;
    private final android.content.DialogInterface.OnCancelListener onCancelListener;

    private CharSequence     title, initialMessage        ;
    private android.app.ProgressDialog progressDialogInstance = null;                   // lazy load
    // endregion

    private android.app.ProgressDialog progressDialog()
    {
        if (null == this.progressDialogInstance)
        {
            this.progressDialogInstance = new android.app.ProgressDialog(this.context);
            this.progressDialogInstance.setTitle           (this.title           );
            this.progressDialogInstance.setMessage         (this.initialMessage  );
            this.progressDialogInstance.setCancelable      (true                 );
            this.progressDialogInstance.setOnCancelListener(this.onCancelListener);
        }
        return this.progressDialogInstance;
    }

    // region Constructors
    private ProgressDialog(
    final android.content.Context                          context         ,
    final android.content.DialogInterface.OnCancelListener onCancelListener)
    { super(); this.context = context; this.onCancelListener = onCancelListener; }

    public ProgressDialog(final android.content.Context context, final CharSequence title,
    final CharSequence initialMessage,
    final android.content.DialogInterface.OnCancelListener onCancelListener)
    { this(context, onCancelListener); this.title = title; this.initialMessage = initialMessage; }

    public ProgressDialog(         final android.content.Context context         ,
    @androidx.annotation.StringRes final int                     title           ,
    @androidx.annotation.StringRes final int                     initialMessage  ,
    final android.content.DialogInterface.OnCancelListener       onCancelListener)
    {
        this(context, onCancelListener);

        if (null != this.context)
        {
            this.title          = this.context.getString(title         );
            this.initialMessage = this.context.getString(initialMessage);
        }
    }
    // endregion

    // region Public Methods
    public void show() { this.progressDialog().show(); }

    @SuppressWarnings({"WeakerAccess"})
    public void setMessage(final CharSequence message)
    { this.progressDialog().setMessage(message); }

    public void setMessage(@androidx.annotation.StringRes final int message)
    { if (null != this.context) this.setMessage(this.context.getString(message)); }

    public void dismiss()
    {
        if (null != this.progressDialogInstance)
            if (this.progressDialogInstance.isShowing()) this.progressDialogInstance.dismiss();
    }
    // endregion
}