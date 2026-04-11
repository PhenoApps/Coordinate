package org.wheatgenetics.coordinate.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.StringRes;

import java.util.ArrayList;

/**
 * Abstract base dialog class, migrated from org.phenoapps.androidlibrary.AlertDialog.
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class AlertDialog extends Object {
    // region Fields
    @NonNull
    private final Activity activity;

    private android.app.AlertDialog.Builder builderInstance = null;        // lazy load
    private LayoutInflater layoutInflaterInstance = null;                  // lazy load
    private android.app.AlertDialog alertDialog = null;
    private boolean positiveOnClickListenerReplaced = false;
    // endregion

    // region Private Methods
    @NonNull
    private android.app.AlertDialog.Builder builder() {
        if (null == this.builderInstance)
            this.builderInstance = new android.app.AlertDialog.Builder(this.activity());
        return this.builderInstance;
    }

    private void createAlertDialog() {
        this.alertDialog = this.builder().create();
        this.positiveOnClickListenerReplaced = false;
        if (null == this.alertDialog) throw new AssertionError();
    }

    private void replaceOnClickListener(final int whichButton,
                                        final View.OnClickListener onClickListener) {
        if (null != this.alertDialog) {
            final Button button = this.alertDialog.getButton(whichButton);
            if (null != button) button.setOnClickListener(onClickListener);
        }
    }
    // endregion

    public AlertDialog(@NonNull final Activity activity) {
        super();
        this.activity = activity;
        this.configure();
    }

    // region Public Methods
    public abstract void configure();

    @NonNull
    public Activity activity() {
        return this.activity;
    }

    @NonNull
    public String getString(@StringRes final int resId) {
        return this.activity().getString(resId);
    }

    // region set() Public Methods
    @NonNull
    public AlertDialog setTitle(final String title) {
        this.builder().setTitle(title);
        return this;
    }

    @NonNull
    public AlertDialog setTitle(@StringRes final int resId) {
        this.builder().setTitle(resId);
        return this;
    }

    @NonNull
    public AlertDialog setCancelableToFalse() {
        this.builder().setCancelable(false);
        return this;
    }

    @NonNull
    public AlertDialog setCancelableToTrue() {
        this.builder().setCancelable(true);
        return this;
    }

    @SuppressWarnings({"WeakerAccess"})
    @NonNull
    public AlertDialog setMessage(final String message) {
        this.builder().setMessage(message);
        return this;
    }

    @SuppressWarnings({"unused"})
    @NonNull
    public AlertDialog setMessage(@StringRes final int resId) {
        return this.setMessage(this.getString(resId));
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    @NonNull
    public AlertDialog setItems(
            @SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final String items[],
            final DialogInterface.OnClickListener onClickListener) {
        this.builder().setItems(items, onClickListener);
        return this;
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    @NonNull
    public AlertDialog setItems(
            @SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final int itemIds[],
            final DialogInterface.OnClickListener onClickListener) {
        if (null != itemIds) {
            final int itemIdsLength = itemIds.length;
            if (itemIdsLength > 0) {
                final ArrayList<String> arrayList = new ArrayList<>(itemIdsLength);
                for (@StringRes final Integer itemId : itemIds)
                    arrayList.add(this.getString(itemId));
                @SuppressWarnings({"CStyleArrayDeclaration"})
                final String items[] = arrayList.toArray(new String[0]);
                this.builder().setItems(items, onClickListener);
            }
        }
        return this;
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    @NonNull
    public AlertDialog setMultiChoiceItems(
            @SuppressWarnings({"CStyleArrayDeclaration"}) @Size(min = 1) final CharSequence items[],
            @SuppressWarnings({"CStyleArrayDeclaration"}) final boolean checkedItems[],
            final DialogInterface.OnMultiChoiceClickListener listener) {
        if (null != items) {
            this.builder().setMultiChoiceItems(items, checkedItems, listener);
        }
        return this;
    }

    @NonNull
    public AlertDialog setView(final View view) {
        this.builder().setView(view);
        return this;
    }

    @NonNull
    public AlertDialog setPositiveButton(@StringRes final int resId,
                                         final DialogInterface.OnClickListener onClickListener) {
        this.builder().setPositiveButton(resId, onClickListener);
        return this;
    }

    @SuppressWarnings({"unused"})
    @NonNull
    public AlertDialog setPositiveButton(@StringRes final int resId) {
        return this.setPositiveButton(resId, null);
    }

    @NonNull
    public AlertDialog setOKPositiveButton(final DialogInterface.OnClickListener onClickListener) {
        return this.setPositiveButton(android.R.string.ok, onClickListener);
    }

    @SuppressWarnings({"unused"})
    @NonNull
    public AlertDialog setOKPositiveButton() {
        return this.setOKPositiveButton(
                (dialog, which) -> { if (null != dialog) dialog.cancel(); });
    }

    @NonNull
    public AlertDialog setNegativeButton(@StringRes final int resId,
                                         final DialogInterface.OnClickListener onClickListener) {
        this.builder().setNegativeButton(resId, onClickListener);
        return this;
    }

    @SuppressWarnings({"WeakerAccess"})
    @NonNull
    public AlertDialog setCancelNegativeButton(
            final DialogInterface.OnClickListener onClickListener) {
        return this.setNegativeButton(android.R.string.cancel, onClickListener);
    }

    @SuppressWarnings({"UnusedReturnValue"})
    @NonNull
    public AlertDialog setCancelNegativeButton() {
        return this.setCancelNegativeButton(
                (dialog, which) -> { if (null != dialog) dialog.cancel(); });
    }

    @SuppressWarnings({"UnusedReturnValue"})
    @NonNull
    public AlertDialog setOKNegativeButton() {
        return this.setNegativeButton(android.R.string.ok,
                (dialog, which) -> { if (null != dialog) dialog.dismiss(); });
    }

    @SuppressWarnings({"WeakerAccess"})
    @NonNull
    public AlertDialog setNeutralButton(@StringRes final int resId,
                                        final DialogInterface.OnClickListener onClickListener) {
        this.builder().setNeutralButton(resId, onClickListener);
        return this;
    }

    @SuppressWarnings({"unused"})
    @NonNull
    public AlertDialog setNeutralButton(@StringRes final int resId) {
        return this.setNeutralButton(resId, null);
    }
    // endregion

    // region Inflater Public Methods
    @SuppressWarnings({"WeakerAccess"})
    @NonNull
    public LayoutInflater layoutInflater() {
        if (null == this.layoutInflaterInstance)
            this.layoutInflaterInstance = this.activity().getLayoutInflater();
        return this.layoutInflaterInstance;
    }

    public View inflate(@LayoutRes final int resource) {
        return this.layoutInflater().inflate(
                /* resource     => */ resource,
                /* root         => */ new LinearLayout(this.activity()),
                /* attachToRoot => */ false);
    }
    // endregion

    // region show() Public Methods
    public void show() {
        if (null == this.alertDialog) this.createAlertDialog();
        this.alertDialog.show();
    }

    @SuppressWarnings({"WeakerAccess"})
    public void createShow() {
        this.createAlertDialog();
        this.alertDialog.show();
    }

    @SuppressWarnings({"unused"})
    public void createModifyShow() {
        this.createAlertDialog();
        {
            final Window window = this.alertDialog.getWindow();
            if (null != window) window.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        this.alertDialog.show();
    }
    // endregion

    // region showToast() Public Methods
    @SuppressWarnings({"WeakerAccess"})
    public void showToast(final String text) {
        Toast.makeText(this.activity(), text, Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings({"WeakerAccess"})
    public void showToast(@StringRes final int resId) {
        this.showToast(this.getString(resId));
    }
    // endregion

    public void cancelAlertDialog() {
        if (null != this.alertDialog) this.alertDialog.cancel();
    }

    public boolean positiveOnClickListenerHasBeenReplaced() {
        return this.positiveOnClickListenerReplaced;
    }

    public void replacePositiveOnClickListener(final View.OnClickListener onClickListener) {
        this.replaceOnClickListener(DialogInterface.BUTTON_POSITIVE, onClickListener);
        this.positiveOnClickListenerReplaced = true;
    }
    // endregion
}
