package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.AlertDialog.Builder
 * android.content.Context
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class ContextAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    final android.content.Context context;

    ContextAlertDialog(final android.content.Context context) { super(); this.context = context; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    { return this.builder = new android.app.AlertDialog.Builder(this.context); }
}