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
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialogTester
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester
 */
class TestAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog implements
org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler,
org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
{
    private static enum AlertDialogUnderTest { NEITHER, ADD, CHECK_AND_ADD }

    // region Fields
    private android.widget.TextView textView;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance = null;
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
        addOptionalFieldAlertDialogTester = null;
    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest alertDialogUnderTest =
        org.wheatgenetics.coordinate.TestAlertDialog.AlertDialogUnderTest.NEITHER;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModelInstance = null;
    private org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester
        excludeRowsOrColsAlertDialogTesterInstance = null;
    private org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialogTester
        excludeCellsAlertDialogTester = null;
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


    private org.wheatgenetics.coordinate.model.TemplateModel templateModel()
    {
        if (null == this.templateModelInstance) this.templateModelInstance =
            org.wheatgenetics.coordinate.model.TemplateModel.makeInitial();
        return this.templateModelInstance;
    }

    private org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester
    excludeRowsOrColsAlertDialogTester()
    {
        if (null == this.excludeRowsOrColsAlertDialogTesterInstance)
            this.excludeRowsOrColsAlertDialogTesterInstance =
                new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester(
                    this.activity(), this.templateModel());
        return this.excludeRowsOrColsAlertDialogTesterInstance;
    }

    private void excludeRows() { this.excludeRowsOrColsAlertDialogTester().testExcludeRows(); }
    private void excludeCols() { this.excludeRowsOrColsAlertDialogTester().testExcludeCols(); }

    private void excludeCells()
    {
        if (null == this.excludeCellsAlertDialogTester) this.excludeCellsAlertDialogTester =
            new org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialogTester(
                this.activity(), this.templateModel());
        this.excludeCellsAlertDialogTester.testExcludeCells();
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
            {
                final android.widget.Button addOptionalFieldButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.addOptionalFieldButton);
                assert null != addOptionalFieldButton;
                addOptionalFieldButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.addOptionalField(); }
                });
            }
            {
                final android.widget.Button checkAndAddOptionalFieldButton = (android.widget.Button)
                    view.findViewById(
                        org.wheatgenetics.coordinate.R.id.checkAndAddOptionalFieldButton);
                assert null != checkAndAddOptionalFieldButton;
                checkAndAddOptionalFieldButton.setOnClickListener(
                    new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate
                                .TestAlertDialog.this.checkAndAddOptionalField();
                        }
                    });
            }
            {
                final android.widget.Button refreshTextButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.refreshTextButton);
                assert null != refreshTextButton;
                refreshTextButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.refreshText(); }
                });
            }
            {
                final android.widget.Button excludeRowsButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeRowsButton);
                assert null != excludeRowsButton;
                excludeRowsButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.excludeRows(); }
                });

            }
            {
                final android.widget.Button excludeColsButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeColsButton);
                assert null != excludeColsButton;
                excludeColsButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.excludeCols(); }
                });

            }
            {
                final android.widget.Button excludeCellsButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeCellsButton);
                assert null != excludeCellsButton;
                excludeCellsButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.coordinate.TestAlertDialog.this.excludeCells(); }
                });
            }
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