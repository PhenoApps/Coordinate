package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
abstract class AlertDialog extends java.lang.Object
{
    android.app.AlertDialog.Builder builder = null;

    // region Package Methods
    abstract android.app.AlertDialog.Builder makeBuilder();

    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    { return this.makeBuilder().setTitle(titleId); }

    android.app.AlertDialog.Builder setNegativeButton()
    {
        assert null != this.builder;
        return this.builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }
    // endregion
}