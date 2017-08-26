package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.widget.EditText
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 * org.wheatgenetics.coordinate.utils.Utils
 */
class ExcludeCellsAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler { public abstract void excludeCells(int amount); }

    private final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler;

    private void excludeCells(final int amount)
    { assert null != this.handler; this.handler.excludeCells(amount); }

    ExcludeCellsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configure(final int titleId)
    {
        super.configure(titleId).setCancelable(false);

        {
            final android.view.View view =
                this.layoutInflater().inflate(org.wheatgenetics.coordinate.R.layout.random, null);

            assert null != view; final android.widget.EditText editText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.cellsEdit);

            assert null != editText; editText.setText("1");

            this.setView(view).setOKPositiveButton(
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.this.excludeCells(
                            org.wheatgenetics.coordinate.utils.Utils.convert(
                                editText.getText().toString()));
                        assert null != dialog; dialog.cancel();
                    }
                });
        }

        return this.setNegativeButton();
    }

    void show()
    {
        this.configure(org.wheatgenetics.coordinate.R.string.random);
        super.show();
    }
}