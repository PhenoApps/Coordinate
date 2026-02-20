package org.wheatgenetics.coordinate.dialogs;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.StringRes;

/**
 * Migrated from org.phenoapps.androidlibrary.ProgressDialog.
 */
@SuppressWarnings({"unused", "ClassExplicitlyExtendsObject", "deprecation"})
public class ProgressDialog extends Object {
    // region Fields
    private final Context context;
    private final DialogInterface.OnCancelListener onCancelListener;

    private CharSequence title, initialMessage;
    private android.app.ProgressDialog progressDialogInstance = null;   // lazy load
    // endregion

    private android.app.ProgressDialog progressDialog() {
        if (null == this.progressDialogInstance) {
            this.progressDialogInstance = new android.app.ProgressDialog(this.context);
            this.progressDialogInstance.setTitle(this.title);
            this.progressDialogInstance.setMessage(this.initialMessage);
            this.progressDialogInstance.setCancelable(true);
            this.progressDialogInstance.setOnCancelListener(this.onCancelListener);
        }
        return this.progressDialogInstance;
    }

    // region Constructors
    private ProgressDialog(final Context context,
                           final DialogInterface.OnCancelListener onCancelListener) {
        super();
        this.context = context;
        this.onCancelListener = onCancelListener;
    }

    public ProgressDialog(final Context context, final CharSequence title,
                          final CharSequence initialMessage,
                          final DialogInterface.OnCancelListener onCancelListener) {
        this(context, onCancelListener);
        this.title = title;
        this.initialMessage = initialMessage;
    }

    public ProgressDialog(final Context context,
                          @StringRes final int title,
                          @StringRes final int initialMessage,
                          final DialogInterface.OnCancelListener onCancelListener) {
        this(context, onCancelListener);
        if (null != this.context) {
            this.title = this.context.getString(title);
            this.initialMessage = this.context.getString(initialMessage);
        }
    }
    // endregion

    // region Public Methods
    public void show() {
        this.progressDialog().show();
    }

    @SuppressWarnings({"WeakerAccess"})
    public void setMessage(final CharSequence message) {
        this.progressDialog().setMessage(message);
    }

    public void setMessage(@StringRes final int message) {
        if (null != this.context) this.setMessage(this.context.getString(message));
    }

    public void dismiss() {
        if (null != this.progressDialogInstance)
            if (this.progressDialogInstance.isShowing())
                this.progressDialogInstance.dismiss();
    }
    // endregion
}
