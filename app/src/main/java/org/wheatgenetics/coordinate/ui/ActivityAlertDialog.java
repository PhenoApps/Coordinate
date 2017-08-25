package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.view.LayoutInflater
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class ActivityAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
            final android.app.Activity        activity                     ;
    private       android.view.LayoutInflater layoutInflaterInstance = null;

    ActivityAlertDialog(final android.app.Activity activity) { super(); this.activity = activity; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    { return this.builder = new android.app.AlertDialog.Builder(this.activity); }

    android.view.LayoutInflater layoutInflater()
    {
        if (null == this.layoutInflaterInstance)
        {
            assert null != this.activity;
            this.layoutInflaterInstance = this.activity.getLayoutInflater();
        }
        return this.layoutInflaterInstance;
    }
}