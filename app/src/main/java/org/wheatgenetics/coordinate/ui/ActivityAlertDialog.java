package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class ActivityAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    final android.app.Activity activity;

    ActivityAlertDialog(final android.app.Activity activity){ super(); this.activity = activity; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    { return this.builder = new android.app.AlertDialog.Builder(this.activity); }
}