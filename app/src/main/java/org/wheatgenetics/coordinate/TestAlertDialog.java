package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
 * org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
class TestAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog implements
org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler
{
    // region Fields
    private android.widget.TextView textView              ;
    private android.widget.Button   addOptionalFieldButton;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance = null;
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
        addOptionalFieldAlertDialogTester = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields()
    {
        if (null == this.nonNullOptionalFieldsInstance) this.nonNullOptionalFieldsInstance =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(null);
        return this.nonNullOptionalFieldsInstance;
    }

    private void addOptionalField()
    {
        if (null == this.addOptionalFieldAlertDialogTester) this.addOptionalFieldAlertDialogTester =
            new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester(
                this.activity(), this);
        this.addOptionalFieldAlertDialogTester.test(this.nonNullOptionalFields());
    }

    private void refreshText()
    {
        final java.lang.String text = null == this.nonNullOptionalFieldsInstance ?
            "" : this.nonNullOptionalFieldsInstance.toJson();
        assert null != this.textView; this.textView.setText(text);
    }
    // endregion

    TestAlertDialog(final android.app.Activity activity) { super(activity);}

    // region Overridden Methods
    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.TestAlertDialogTitle);

        {
            final android.view.View view = this.inflate(org.wheatgenetics.coordinate.R.layout.test);

            assert null != view;
            if (null == this.textView) this.textView = (android.widget.TextView)
                view.findViewById(org.wheatgenetics.coordinate.R.id.textView);

            if (null == this.addOptionalFieldButton)
                this.addOptionalFieldButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.addOptionalFieldButton);
            assert null != this.addOptionalFieldButton;
            this.addOptionalFieldButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.addOptionalField(); }
                });

            this.setView(view);
        }

        this.setOKPositiveButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler Overridden Method
    @java.lang.Override
    public void handleAddOptionalFieldDone() { this.refreshText(); }
    // endregion
    // endregion
}