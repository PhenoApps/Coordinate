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
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester
 * org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler
 * org.wheatgenetics.coordinate.tc.ExcludeAlertDialogTester
 * org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialogTester
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester
 * org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester
 */
class TestAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog implements
org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler,
org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler,
org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler
{
    private static enum OptionalFieldAlertDialogUnderTest { NEITHER, ADD, CHECK_AND_ADD }

    // region Fields
    private android.widget.TextView textView;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance = null;
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
        addOptionalFieldAlertDialogTester = null;
    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest
        optionalFieldAlertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest.NEITHER;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModelInstance = null;
    private org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialogTester
        excludeRowsOrColsAlertDialogTesterInstance = null;
    private org.wheatgenetics.coordinate.tc.ExcludeCellsAlertDialogTester
        excludeCellsAlertDialogTester = null;
    private org.wheatgenetics.coordinate.tc.ExcludeAlertDialogTester
        excludeAlertDialogTester = null;
    private org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester
        setNumberingAlertDialogTester = null;
    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester
        setExcludesOptionalFieldsNumberingAlertDialogTester = null;
    private org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester
        assignTitleRowsColsAlertDialogTester = null;
    // endregion

    // region Private Methods
    @java.lang.SuppressWarnings("DefaultLocale")
    private void refreshText()
    {
        final java.lang.StringBuilder textBuilder = new java.lang.StringBuilder("");
        {
            if (null != this.nonNullOptionalFieldsInstance)
                textBuilder.append(this.nonNullOptionalFieldsInstance.toJson());

            if (null != this.templateModelInstance)
            {
                {
                    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                        nonNullOptionalFields = this.templateModelInstance.nonNullOptionalFields();
                    if (null != nonNullOptionalFields)
                    {
                        if (textBuilder.length() > 0) textBuilder.append('\n');
                        textBuilder.append(nonNullOptionalFields.toJson());
                    }
                }
                {
                    final java.lang.String excludeCellsAsJson =
                        this.templateModelInstance.getExcludeCellsAsJson();
                    if (null != excludeCellsAsJson)
                    {
                        if (textBuilder.length() > 0) textBuilder.append('\n');
                        textBuilder.append(excludeCellsAsJson);
                    }
                }

                if (textBuilder.length() > 0) textBuilder.append('\n');
                textBuilder.append(java.lang.String.format("colNumbering=%b, rowNumbering=%b",
                    this.templateModelInstance.getColNumbering(),
                    this.templateModelInstance.getRowNumbering()));

                if (textBuilder.length() > 0) textBuilder.append('\n');
                textBuilder.append(java.lang.String.format("title=%s, type=%d, rows=%d, cols=%d",
                    this.templateModelInstance.getTitle()         ,
                    this.templateModelInstance.getType().getCode(),
                    this.templateModelInstance.getRows()          ,
                    this.templateModelInstance.getCols()          ));
            }
        }
        assert null != this.textView; this.textView.setText(textBuilder.toString());
    }

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields()
    {
        return null == this.templateModelInstance                                          ?
            null == this.nonNullOptionalFieldsInstance                                     ?
                this.nonNullOptionalFieldsInstance =
                    new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields() :
                this.nonNullOptionalFieldsInstance                                         :
            this.templateModelInstance.nonNullOptionalFields()                             ;
    }

    private void addOptionalField()
    {
        if (null == this.addOptionalFieldAlertDialogTester) this.addOptionalFieldAlertDialogTester =
            new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester(
                this.activity(), this);
        this.optionalFieldAlertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest.ADD;
        this.addOptionalFieldAlertDialogTester.test(this.nonNullOptionalFields());
    }

    private void checkAndAddOptionalField()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(), this);
        this.optionalFieldAlertDialogUnderTest = org.wheatgenetics.coordinate
            .TestAlertDialog.OptionalFieldAlertDialogUnderTest.CHECK_AND_ADD;
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

    private void exclude()
    {
        if (null == this.excludeAlertDialogTester) this.excludeAlertDialogTester =
            new org.wheatgenetics.coordinate.tc.ExcludeAlertDialogTester(
                this.activity(), this.templateModel());
        this.excludeAlertDialogTester.testExclude();
    }

    private void setNumbering()
    {
        if (null == this.setNumberingAlertDialogTester) this.setNumberingAlertDialogTester =
            new org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester(
                this.activity(), this.templateModel());
        this.setNumberingAlertDialogTester.testSetNumbering();
    }

    private void setExcludesOptionalFieldsNumbering()
    {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialogTester)
            this.setExcludesOptionalFieldsNumberingAlertDialogTester = new
                org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester(
                    this.activity(), this.templateModel());
        this.setExcludesOptionalFieldsNumberingAlertDialogTester
            .testSetExcludesOptionalFieldsNumbering();
    }

    private void assignTitleRowsCols()
    {
        if (null == this.assignTitleRowsColsAlertDialogTester)
            this.assignTitleRowsColsAlertDialogTester =
                new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester(
                    this.activity(), this.templateModel(), this);
        this.assignTitleRowsColsAlertDialogTester.testAssignTitleRowsCols();
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
            {
                final android.widget.Button excludeButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton);
                assert null != excludeButton;
                excludeButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.TestAlertDialog.this.exclude(); }
                    });
            }
            {
                final android.widget.Button setNumberingButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.setNumberingButton);
                assert null != setNumberingButton;
                setNumberingButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.coordinate.TestAlertDialog.this.setNumbering(); }
                    });
            }
            {
                final android.widget.Button setExcludesOptionalFieldsNumberingButton =
                    (android.widget.Button)
                        view.findViewById(R.id.setExcludesOptionalFieldsNumberingButton);
                assert null != setExcludesOptionalFieldsNumberingButton;
                setExcludesOptionalFieldsNumberingButton.setOnClickListener(
                    new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.TestAlertDialog
                                .this.setExcludesOptionalFieldsNumbering();
                        }
                    });
            }
            {
                final android.widget.Button assignTitleRowsColsButton =
                    (android.widget.Button) view.findViewById(R.id.assignTitleRowsColsButton);
                assert null != assignTitleRowsColsButton;
                assignTitleRowsColsButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.TestAlertDialog
                                .this.assignTitleRowsCols();
                        }
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

        if (org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest
        .CHECK_AND_ADD == this.optionalFieldAlertDialogUnderTest)
        {
            assert null != this.checkAndAddOptionalFieldsAlertDialog;
            this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
        }
        else this.optionalFieldAlertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest.NEITHER;
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler Overridden Method
    @java.lang.Override
    public void handleAssignDone() { this.refreshText(); }
    // endregion
    // endregion
}