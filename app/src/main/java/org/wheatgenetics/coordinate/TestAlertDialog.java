package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
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
 * org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester
 * org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialogTester
 * org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester
 * org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialogTester
 */
@java.lang.SuppressWarnings({"unused"})
class TestAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog implements
org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler   ,
org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler,
org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler           ,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryEnumModifier"})
    private static enum OptionalFieldAlertDialogUnderTest { NEITHER, ADD, CHECK_AND_ADD }

    // region Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode;

    private android.widget.TextView textView;

    private org.wheatgenetics.coordinate.model.TemplateModel                 templateModel = null;
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance = null;                                           // lazy load
    private org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester
        addOptionalFieldAlertDialogTester = null;                                       // lazy load
    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;                                    // lazy load
    private org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest
        optionalFieldAlertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest.NEITHER;

    private org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester
        excludedRowsOrColsAlertDialogTesterInstance = null;                             // lazy load
    private org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialogTester
        generatedExcludedCellsAlertDialogTester = null;                                 // lazy load
    private org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialogTester
        excludeAlertDialogTester = null;                                                // lazy load
    private org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester
        setNumberingAlertDialogTester = null;                                           // lazy load
    private org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester
        setExcludesOptionalFieldsNumberingAlertDialogTester = null;                     // lazy load
    private org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester
        assignTitleRowsColsAlertDialogTester = null;                                    // lazy load

    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator = null;     // lazy load
    // endregion

    // region Private Methods
    @java.lang.SuppressWarnings({"DefaultLocale"}) private void refreshText()
    {
        final java.lang.StringBuilder textBuilder = new java.lang.StringBuilder();

        if (null != this.nonNullOptionalFieldsInstance)
            textBuilder.append(this.nonNullOptionalFieldsInstance.toJson());

        if (null != this.templateModel)
        {
            {
                final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                    nonNullOptionalFields = this.templateModel.optionalFields();
                if (null != nonNullOptionalFields)
                {
                    if (textBuilder.length() > 0) textBuilder.append('\n');
                    textBuilder.append(nonNullOptionalFields.toJson());
                }
            }
            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append("generatedExcludedCellsAmount=").append(
                this.templateModel.getGeneratedExcludedCellsAmount());
            {
                final java.lang.String excludedCellsAsJson =
                    this.templateModel.getExcludedCellsAsJson();
                if (null != excludedCellsAsJson)
                {
                    if (textBuilder.length() > 0) textBuilder.append('\n');
                    textBuilder.append(excludedCellsAsJson);
                }
            }

            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append(java.lang.String.format("colNumbering=%b, rowNumbering=%b",
                this.templateModel.getColNumbering(), this.templateModel.getRowNumbering()));

            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append(java.lang.String.format("title=%s, type=%d, rows=%d, cols=%d",
                this.templateModel.getTitle(), this.templateModel.getType().getCode(),
                this.templateModel.getRows (), this.templateModel.getCols()          ));
        }

        if (null != this.textView) this.textView.setText(textBuilder.toString());
    }

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields()
    {
        return null == this.templateModel                                                  ?
            null == this.nonNullOptionalFieldsInstance                                     ?
                this.nonNullOptionalFieldsInstance =
                    new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields() :
                this.nonNullOptionalFieldsInstance                                         :
            this.templateModel.optionalFields()                                            ;
    }

    private void addOptionalField()
    {
        if (null == this.addOptionalFieldAlertDialogTester) this.addOptionalFieldAlertDialogTester =
            new org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester(
                this.activity(),this);
        this.optionalFieldAlertDialogUnderTest =
            org.wheatgenetics.coordinate.TestAlertDialog.OptionalFieldAlertDialogUnderTest.ADD;
        this.addOptionalFieldAlertDialogTester.test(this.nonNullOptionalFields());
    }

    private void checkAndAddOptionalField()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(),this);
        this.optionalFieldAlertDialogUnderTest = org.wheatgenetics.coordinate
            .TestAlertDialog.OptionalFieldAlertDialogUnderTest.CHECK_AND_ADD;
        this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
    }


    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester
    excludedRowsOrColsAlertDialogTester()
    {
        if (null == this.excludedRowsOrColsAlertDialogTesterInstance)
            this.excludedRowsOrColsAlertDialogTesterInstance =
                new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester(
                    this.activity(), this.templateModel);
        return this.excludedRowsOrColsAlertDialogTesterInstance;
    }

    private void excludeRows() { this.excludedRowsOrColsAlertDialogTester().testExcludedRows(); }
    private void excludeCols() { this.excludedRowsOrColsAlertDialogTester().testExcludedCols(); }

    private void setGeneratedAmount()
    {
        if (null == this.generatedExcludedCellsAlertDialogTester)
            this.generatedExcludedCellsAlertDialogTester =
                new org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialogTester(
                    this.activity(), this.templateModel);
        this.generatedExcludedCellsAlertDialogTester.testGeneratedExcludedCells();
    }

    private void exclude()
    {
        if (null == this.excludeAlertDialogTester) this.excludeAlertDialogTester =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialogTester(
                this.activity(), this.requestCode, this.templateModel);
        this.excludeAlertDialogTester.testExclude();
    }

    private void setNumbering()
    {
        if (null == this.setNumberingAlertDialogTester) this.setNumberingAlertDialogTester =
            new org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester(
                this.activity(), this.templateModel);
        this.setNumberingAlertDialogTester.testSetNumbering();
    }

    private void setExcludesOptionalFieldsNumbering()
    {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialogTester)
            this.setExcludesOptionalFieldsNumberingAlertDialogTester = new
                org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester(
                    this.activity(), this.requestCode, this.templateModel);
        this.setExcludesOptionalFieldsNumberingAlertDialogTester
            .testSetExcludesOptionalFieldsNumbering();
    }

    private void assignTitleRowsCols()
    {
        if (null == this.assignTitleRowsColsAlertDialogTester)
            this.assignTitleRowsColsAlertDialogTester =
                new org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester(
                    this.activity(), this.templateModel,this);
        this.assignTitleRowsColsAlertDialogTester.testAssignTitleRowsCols();
    }


    private void createTemplate()
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this.activity(), this.requestCode,this);
        this.templateCreator.create();
    }
    // endregion

    TestAlertDialog(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode)
    { super(activity); this.requestCode = requestCode; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.TestAlertDialogTitle);
        {
            final android.view.View view = this.inflate(org.wheatgenetics.coordinate.R.layout.test);
            if (null != view)
            {
                this.textView = view.findViewById(org.wheatgenetics.coordinate.R.id.textView);
                {
                    final android.widget.Button addOptionalFieldButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.addOptionalFieldButton);
                    if (null != addOptionalFieldButton) addOptionalFieldButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            {
                                org.wheatgenetics.coordinate
                                    .TestAlertDialog.this.addOptionalField();
                            }
                        });
                }
                {
                    final android.widget.Button checkAndAddOptionalFieldButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.checkAndAddOptionalFieldButton);
                    if (null != checkAndAddOptionalFieldButton)
                    checkAndAddOptionalFieldButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            {
                                org.wheatgenetics.coordinate
                                    .TestAlertDialog.this.checkAndAddOptionalField();
                            }
                        });
                }
                {
                    final android.widget.Button refreshTextButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.refreshTextButton);
                    if (null != refreshTextButton) refreshTextButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.refreshText(); }
                        });
                }
                {
                    final android.widget.Button excludeRowsButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.excludeRowsButton);
                    if (null != excludeRowsButton) excludeRowsButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.excludeRows(); }
                        });

                }
                {
                    final android.widget.Button excludeColsButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.excludeColsButton);
                    if (null != excludeColsButton) excludeColsButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.excludeCols(); }
                        });

                }
                {
                    final android.widget.Button setGeneratedAmountButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.setGeneratedAmountButton);
                    if (null != setGeneratedAmountButton)
                        setGeneratedAmountButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate
                                        .TestAlertDialog.this.setGeneratedAmount();
                                }
                            });
                }
                {
                    final android.widget.Button excludeButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.excludeButton);
                    if (null != excludeButton) excludeButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.exclude(); }
                        });
                }
                {
                    final android.widget.Button setNumberingButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.setNumberingButton);
                    if (null != setNumberingButton) setNumberingButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.setNumbering(); }
                        });
                }
                {
                    final android.widget.Button setExcludesOptionalFieldsNumberingButton =
                        view.findViewById(org.wheatgenetics.coordinate
                            .R.id.setExcludesOptionalFieldsNumberingButton);
                    if (null != setExcludesOptionalFieldsNumberingButton)
                        setExcludesOptionalFieldsNumberingButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate.TestAlertDialog
                                        .this.setExcludesOptionalFieldsNumbering();
                                }
                            });
                }
                {
                    final android.widget.Button assignTitleRowsColsButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.assignTitleRowsColsButton);
                    if (null != assignTitleRowsColsButton)
                        assignTitleRowsColsButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate
                                        .TestAlertDialog.this.assignTitleRowsCols();
                                }
                            });
                }
                {
                    final android.widget.Button createTemplateButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.createTemplateButton);
                    if (null != createTemplateButton) createTemplateButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            { org.wheatgenetics.coordinate.TestAlertDialog.this.createTemplate(); }
                        });
                }
            }
            this.setView(view);
        }
        this.setOKPositiveButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler and org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    {
        this.refreshText();

        switch (this.optionalFieldAlertDialogUnderTest)
        {
            case CHECK_AND_ADD:
                if (null != this.checkAndAddOptionalFieldsAlertDialog)
                    this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
                break;

            case ADD:
                this.optionalFieldAlertDialogUnderTest = org.wheatgenetics.coordinate
                    .TestAlertDialog.OptionalFieldAlertDialogUnderTest.NEITHER;
                break;
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler Overridden Method
    @java.lang.Override public void handleAssignDone() { this.refreshText(); }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override public void handleTemplateCreated(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { this.templateModel = templateModel; this.refreshText(); }
    // endregion
    // endregion
}