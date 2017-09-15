package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.view.LayoutInflater
 * android.view.View
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
abstract class ActivityAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    // region Fields
    private final android.app.Activity        activity                     ;
    private       android.view.LayoutInflater layoutInflaterInstance = null;
    // endregion

    ActivityAlertDialog(final android.app.Activity activity) { super(); this.activity = activity; }

    // region Overridden Methods
    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder()
    { return this.builder = new android.app.AlertDialog.Builder(this.activity); }

    @java.lang.Override
    java.lang.String getString(final int resId) { return this.getString(this.activity, resId); }
    // endregion

    // region Package Methods
    android.view.LayoutInflater layoutInflater()
    {
        if (null == this.layoutInflaterInstance)
        {
            assert null != this.activity;
            this.layoutInflaterInstance = this.activity.getLayoutInflater();
        }
        return this.layoutInflaterInstance;
    }

    android.view.View inflate(final int resource)
    {
        return this.layoutInflater().inflate(
            /* resource     => */ resource                                      ,
            /* root         => */ new android.widget.LinearLayout(this.activity),
            /* attachToRoot => */ false                                         );
    }
    // endregion
}