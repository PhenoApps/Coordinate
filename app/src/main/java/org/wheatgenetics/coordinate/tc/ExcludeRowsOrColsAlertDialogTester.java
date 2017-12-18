package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ExcludeRowsOrColsAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity                             activity     ;
    private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private java.lang.String rowLabel = null, colLabel = null;
    private org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
        excludedRowsAlertDialog = null, excludedColsAlertDialog = null;
    // endregion

    // region Private Methods
    private void excludeRows(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedRow(i);
            i++;
        }
    }

    private void excludeCols(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedCol(i);
            i++;
        }
    }
    // endregion

    public ExcludeRowsOrColsAlertDialogTester(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    // region Public Methods
    public void testExcludeRows()
    {
        if (null == this.excludedRowsAlertDialog) this.excludedRowsAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void excludeRowsOrCols(final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc
                            .ExcludeRowsOrColsAlertDialogTester.this.excludeRows(checkedItems);
                    }
                });

        if (null == this.rowLabel)
        {
            assert null != this.activity;
            this.rowLabel = this.activity.getString(
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel);
        }

        assert null != this.templateModel; this.excludedRowsAlertDialog.show(
            this.templateModel.rowItems       (this.rowLabel),
            this.templateModel.rowCheckedItems()             );
    }

    public void testExcludeCols()
    {
        if (null == this.excludedColsAlertDialog) this.excludedColsAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void excludeRowsOrCols(final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc
                            .ExcludeRowsOrColsAlertDialogTester.this.excludeCols(checkedItems);
                    }
                });

        if (null == this.colLabel)
        {
            assert null != this.activity;
            this.colLabel = this.activity.getString(
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel);
        }

        assert null != this.templateModel; this.excludedColsAlertDialog.show(
            this.templateModel.colItems       (this.colLabel),
            this.templateModel.colCheckedItems()             );
    }
    // endregion
}