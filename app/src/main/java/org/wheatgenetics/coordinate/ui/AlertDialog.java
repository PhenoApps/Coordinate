package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
abstract class AlertDialog extends java.lang.Object
{
    android.app.AlertDialog.Builder builder = null;

    // region Package Methods
    // region makeBuilder() Package Methods
    abstract android.app.AlertDialog.Builder makeBuilder();

    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    { return this.makeBuilder().setTitle(titleId); }
    // endregion

    // region set() Package Methods
    org.wheatgenetics.coordinate.ui.AlertDialog setView(final android.view.View view)
    {
        assert null != this.builder; this.builder.setView(view);
        return this;
    }

    android.app.AlertDialog.Builder setOKPositiveButton(
    final android.content.DialogInterface.OnClickListener onClickListener)
    {
        assert null != this.builder; return this.builder.setPositiveButton(
            org.wheatgenetics.coordinate.R.string.ok, onClickListener);
    }

    android.app.AlertDialog.Builder setOKPositiveButton()
    {
        return this.setOKPositiveButton(
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    android.app.AlertDialog.Builder setNegativeButton()
    {
        assert null != this.builder;
        return this.builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }
    // endregion
    // endregion
}