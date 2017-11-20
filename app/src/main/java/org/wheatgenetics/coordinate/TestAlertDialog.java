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
 * org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
class TestAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog implements
org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler,
org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
{
    private static enum AlertDialogUnderTest { NEITHER, ADD, CHECK_AND_ADD }

    // region Fields
    private android.widget.TextView textView              ;
    private android.widget.Button   addOptionalFieldButton,
        checkAndAddOptionalFieldButton, refreshTextButton;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance = null;
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
        addOptionalFieldAlertDialogTester = null;
    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest alertDialogUnderTest =
        org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.NEITHER;
    // endregion

    // region Private Methods
    private void refreshText()
    {
        final java.lang.String text = null == this.nonNullOptionalFieldsInstance ?
            "" : this.nonNullOptionalFieldsInstance.toJson();
        assert null != this.textView; this.textView.setText(text);
    }

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
        this.alertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.ADD;
        this.addOptionalFieldAlertDialogTester.test(this.nonNullOptionalFields());
    }

    private void checkAndAddOptionalField()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(), this);
        this.alertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.CHECK_AND_ADD;
        this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
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
            this.textView = (android.widget.TextView)
                view.findViewById(org.wheatgenetics.coordinate.R.id.textView);

            this.addOptionalFieldButton = (android.widget.Button)
                view.findViewById(org.wheatgenetics.coordinate.R.id.addOptionalFieldButton);
            assert null != this.addOptionalFieldButton;
            this.addOptionalFieldButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.addOptionalField(); }
                });

            this.checkAndAddOptionalFieldButton = (android.widget.Button)
                view.findViewById(org.wheatgenetics.coordinate.R.id.checkAndAddOptionalFieldButton);
            assert null != this.checkAndAddOptionalFieldButton;
            this.checkAndAddOptionalFieldButton.setOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    {
                        org.wheatgenetics.coordinate
                            .TestAlertDialog.this.checkAndAddOptionalField();
                    }
                });

            this.refreshTextButton = (android.widget.Button)
                view.findViewById(org.wheatgenetics.coordinate.R.id.refreshTextButton);
            assert null != this.refreshTextButton;
            this.refreshTextButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.refreshText(); }
                });

            this.setView(view);
        }

        this.setOKPositiveButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler and org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void handleAddOptionalFieldDone()
    {
        this.refreshText();

        if (org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.CHECK_AND_ADD
        == this.alertDialogUnderTest)
        {
            assert null != this.checkAndAddOptionalFieldsAlertDialog;
            this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFieldsInstance);
        }
        else this.alertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.NEITHER;
    }
    // endregion
    // endregion
}