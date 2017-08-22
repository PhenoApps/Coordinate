package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExcludeCellsAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void excludeCells(int amount); }

    // region Fields
    private final android.app.Activity                                            activity;
    private final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler ;

    private android.app.AlertDialog         alertDialog  = null;
    private android.app.AlertDialog.Builder builder      = null;
    // endregion

    private void excludeCells(final int amount)
    {
        assert null != this.handler;
        this.handler.excludeCells(amount);
    }

    ExcludeCellsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.random)
                    .setCancelable(false);
                {
                    android.view.View view;
                    {
                        assert null != this.activity;
                        final android.view.LayoutInflater layoutInflater =
                            this.activity.getLayoutInflater();
                        view = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.random, null);
                    }

                    assert null != view;
                    final android.widget.EditText editText = (android.widget.EditText)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.cellsEdit);

                    assert null != editText;
                    editText.setText("1");

                    this.builder.setView(view).setPositiveButton(
                        org.wheatgenetics.coordinate.R.string.ok,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.
                                    this.excludeCells(
                                        org.wheatgenetics.coordinate.utils.Utils.convert(
                                            editText.getText().toString()));

                                assert null != dialog;
                                dialog.cancel();
                            }
                        });
                }
                this.builder.setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}