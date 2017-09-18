package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.androidlibrary.Utils.showLongToast
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog
 * org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler
 */
class TemplateCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler,
org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler
{
    interface Handler { public abstract void handleNext(); }

    // region Fields
    private final android.app.Activity                                    activity;
    private final org.wheatgenetics.coordinate.ui.TemplateCreator.Handler handler ;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog newTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog
        extraNewTemplateAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
        optionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeAlertDialog excludeAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog excludeCellsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.NamingAlertDialog       namingAlertDialog       = null;
    // endregion

    // region Private Methods
    private java.lang.String getString(final int resId)
    { assert null != this.activity; return this.activity.getString(resId); }

    // region showLongToast() Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    private void showLongToast(final int text) { this.showLongToast(this.getString(text)); }
    // endregion

    private void performStep2()
    {
        if (null == this.extraNewTemplateAlertDialog) this.extraNewTemplateAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog(this.activity, this);
        this.extraNewTemplateAlertDialog.show();
    }

    // region processExclude(Rows|Cols)() Private Methods
    private void processExcludeRows(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems) if (checkedItem) // TODO: Are they cleared first?
            templateModel.addExcludeRow(i++);
    }

    private void processExcludeCols(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems) if (checkedItem) // TODO: Are they cleared first?
            templateModel.addExcludeCol(i++);
    }
    // endregion
    // endregion

    TemplateCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.TemplateCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void handleEmptyName(final java.lang.String message)
    { this.showLongToast(message); this.create(this.templateModel); }

    @java.lang.Override
    public void handleUnspecifiedRows(final java.lang.String message)
    { this.showLongToast(message); this.create(this.templateModel); }

    @java.lang.Override
    public void handleUnspecifiedCols(final java.lang.String message)
    { this.showLongToast(message); this.create(this.templateModel); }

    @java.lang.Override
    public void createNewTemplate(final java.lang.String name, final int rows, final int cols)
    {
        assert null != this.templateModel;
        this.templateModel.assign(/* title => */ name, /* rows => */ rows, /* cols => */ cols);
        this.performStep2();
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog Overridden Methods
    @java.lang.Override
    public void addOptionalFields()
    {
        if (null == this.optionalFieldsAlertDialog) this.optionalFieldsAlertDialog =
            new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog(this.activity, this);
        assert null != this.templateModel; this.optionalFieldsAlertDialog.show(
            this.templateModel.optionalFieldNames(), this.templateModel.optionalFieldschecks());
    }

    @java.lang.Override
    public void addExcludes()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeAlertDialog(this.activity, this);
        this.excludeAlertDialog.show();
    }

    @java.lang.Override
    public void addNaming()
    {
        if (null == this.namingAlertDialog) this.namingAlertDialog =
            new org.wheatgenetics.coordinate.ui.NamingAlertDialog(this.activity, this);

        assert null != this.templateModel; this.namingAlertDialog.show(
            this.templateModel.getRowNumbering(), this.templateModel.getColNumbering());
    }

    @java.lang.Override
    public void handleNext() { assert null != this.handler; this.handler.handleNext(); }
    // endregion

    // region org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void checkOptionalField(final int i, final boolean b)
    {
        assert null != this.templateModel;
        this.templateModel.setOptionalFieldChecked(/* index => */ i, /* checked => */ b);
    }

    @java.lang.Override
    public void retryAddOptionalField(final int errorMsgResId)
    { this.showLongToast(errorMsgResId); this.addOptionalFields(); }

    @java.lang.Override
    public void addOptionalField(final java.lang.String newName, final java.lang.String newDefault)
    {
        assert null != this.templateModel;
        this.templateModel.addOptionalField(/* name => */ newName, /* value => */ newDefault);
        this.addOptionalFields();
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void excludeRows()
    {
        if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.row,
                new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void process(final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.ui.
                            TemplateCreator.this.processExcludeRows(checkedItems);
                    }
                });
        assert null != this.templateModel; this.excludeRowsAlertDialog.show(
            this.templateModel.rowItems(this.getString(org.wheatgenetics.coordinate.R.string.row)),
            this.templateModel.rowCheckedItems()                                                 );
    }

    @java.lang.Override
    public void excludeCols()
    {
        if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.col,
                new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void process(final boolean[] checkedItems)
                    {
                        org.wheatgenetics.coordinate.ui.
                            TemplateCreator.this.processExcludeCols(checkedItems);
                    }
                });
        assert null != this.templateModel; this.excludeColsAlertDialog.show(
            this.templateModel.colItems(this.getString(org.wheatgenetics.coordinate.R.string.col)),
            this.templateModel.colCheckedItems()                                                 );
    }

    @java.lang.Override
    public void excludeCells()
    {
        if (null == this.excludeCellsAlertDialog) this.excludeCellsAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog(this.activity, this);
        this.excludeCellsAlertDialog.show();
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void excludeCells(final int amount)
    {
        if (amount > 0)
        {
            assert null != this.templateModel;
            this.templateModel.makeRandomCells(amount);
        }
        else this.excludeCells();
    }
    // endregion

    // region org.wheatgenetics.coordinate.ui.NamingAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void setNumbering(final boolean rowNumbering, final boolean colNumbering)
    {
        assert null != this.templateModel;
        this.templateModel.setRowNumbering(rowNumbering);
        this.templateModel.setColNumbering(colNumbering);
    }
    // endregion

    void create(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            if (null == this.newTemplateAlertDialog) this.newTemplateAlertDialog =
                new org.wheatgenetics.coordinate.ui.NewTemplateAlertDialog(this.activity, this);
            this.templateModel = templateModel; this.templateModel.clearExcludesAndOptionalFields();
            this.newTemplateAlertDialog.show(
                this.templateModel.getRows(), this.templateModel.getCols());
        }
    }
}