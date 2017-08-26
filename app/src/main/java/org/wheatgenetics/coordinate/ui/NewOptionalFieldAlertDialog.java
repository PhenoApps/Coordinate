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
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class NewOptionalFieldAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler
    {
        public abstract void retry(int errorMsgResId, java.lang.String oldName,
          java.lang.String newDefault);
        public abstract void addOptionalField(java.lang.String newName,
            java.lang.String newDefault);
    }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler handler;

    private java.lang.String        oldName                                    ;
    private android.widget.EditText nameEditText = null, defaultEditText = null;
    // endregion

    // region Private Methods
    private static java.lang.String getText(final android.widget.EditText editText)
    {
        assert null != editText;
        return org.wheatgenetics.javalib.Utils.adjust(editText.getText().toString());
    }

    private void handlePositiveButtonClick(final android.content.DialogInterface dialog)
    {
        final java.lang.String newName =
            org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.getText(this.nameEditText);

        final java.lang.String newDefault =
            org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.getText(
                this.defaultEditText);

        assert null != this.handler;
        if (0 == newName.length())
            this.handler.retry(org.wheatgenetics.coordinate.R.string.new_optional_field_no_name,
                this.oldName, newDefault);
        else
        {
            assert null != dialog; dialog.cancel();
            this.handler.addOptionalField(newName, newDefault);
        }
    }
    // endregion

    NewOptionalFieldAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configure(final int titleId)
    {
        super.configure(titleId).setCancelable(false);

        {
            final android.view.View view = this.layoutInflater().inflate(
                org.wheatgenetics.coordinate.R.layout.optional_new, null);

            assert null != view;
            if (null == this.nameEditText) this.nameEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.fieldEdit);
            if (null == this.defaultEditText) this.defaultEditText = (android.widget.EditText)
                view.findViewById(org.wheatgenetics.coordinate.R.id.valueEdit);

            this.setView(view);
        }

        this.setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.NewOptionalFieldAlertDialog.
                        this.handlePositiveButtonClick(dialog);
                }
            });

        return this.setNegativeButton();
    }

    void show(final java.lang.String oldName, final java.lang.String oldDefault)
    {
        this.configure(org.wheatgenetics.coordinate.R.string.new_optional_field);

        this.oldName = oldName;
        assert null != this.nameEditText   ; this.nameEditText.setText   (oldName   );
        assert null != this.defaultEditText; this.defaultEditText.setText(oldDefault);
        this.show();
    }
}