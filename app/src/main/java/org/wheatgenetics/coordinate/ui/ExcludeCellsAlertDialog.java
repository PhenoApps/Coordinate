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
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.utils.Utils
 *
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class ExcludeCellsAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler { public abstract void excludeCells(int amount); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler        ;
    private       android.widget.EditText                                         editText = null;
    // endregion

    private void excludeCells()
    {
        assert null != this.editText; assert null != this.handler;
        this.handler.excludeCells(org.wheatgenetics.coordinate.utils.Utils.convert(
            org.wheatgenetics.androidlibrary.Utils.getText(this.editText)));
    }

    ExcludeCellsAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId).setCancelable(false);

        {
            final android.view.View view =
                this.layoutInflater().inflate(org.wheatgenetics.coordinate.R.layout.random, null);

            if (null == this.editText)
            {
                assert null != view; this.editText = (android.widget.EditText)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.cellsEdit);
                assert null != editText;
            }
            this.editText.setText("1");

            this.setView(view);
        }

        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.this.excludeCells();
                    assert null != dialog; dialog.cancel();
                }
            });

        return this.setNegativeButton();
    }

    void show() { this.configure(org.wheatgenetics.coordinate.R.string.random); super.show(); }
}