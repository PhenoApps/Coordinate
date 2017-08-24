package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 */
abstract class AlertDialog extends java.lang.Object
{
    android.app.AlertDialog.Builder builder = null;

    // region Package Methods
    abstract android.app.AlertDialog.Builder makeBuilder();

    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    { return this.makeBuilder().setTitle(titleId); }
    // endregion
}