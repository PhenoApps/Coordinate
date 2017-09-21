package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.utils.Utils
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ActivityAlertDialog
 */
class ExcludeCellsAlertDialog extends org.wheatgenetics.coordinate.ui.ActivityAlertDialog
{
    // region Fields
    private android.widget.EditText                          editText                = null ;
    private android.app.AlertDialog                          alertDialog             = null ;
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                  ;
    private boolean                                          onClickListenerReplaced = false;
    // endregion

    private void excludeCells()
    {
        final int amount = org.wheatgenetics.coordinate.utils.Utils.convert(
            org.wheatgenetics.androidlibrary.Utils.getText(this.editText));
        if (amount > 0)
        {
            assert null != this.templateModel; this.templateModel.makeRandomCells(amount);
            assert null != this.alertDialog  ; this.alertDialog.cancel()                 ;
        }
    }

    ExcludeCellsAlertDialog(final android.app.Activity activity) { super(activity); }

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
                assert null != this.editText;
            }
            this.editText.setText("1");

            this.setView(view);
        }

        this.setOKPositiveButton(null);
        return this.setNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            if (null == this.alertDialog)
            {
                if (null == this.builder)
                {
                    this.builder = this.makeBuilder(org.wheatgenetics.coordinate.R.string.random);
                    assert null != this.builder;
                }
                this.alertDialog = this.builder.create();
                assert null != this.alertDialog;
            }

            this.templateModel = templateModel; this.alertDialog.show();

            if (!this.onClickListenerReplaced)
            {
                this.alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(new android.view.View.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.view.View view)
                            {
                                org.wheatgenetics.coordinate.ui.
                                    ExcludeCellsAlertDialog.this.excludeCells();
                            }
                        });
                this.onClickListenerReplaced = true;
            }
        }
    }
}