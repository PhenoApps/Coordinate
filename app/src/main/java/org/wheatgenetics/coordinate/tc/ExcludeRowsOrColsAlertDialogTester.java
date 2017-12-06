package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class ExcludeRowsOrColsAlertDialogTester extends java.lang.Object
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel                   ;
    private java.lang.String                                 rowLabel = null, colLabel = null;
    private org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    // endregion

    // region Private Methods
    private void excludeRows(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludeRow(i);
            i++;
        }
    }

    private void excludeCols(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludeCol(i);
            i++;
        }
    }
    // endregion

    public ExcludeRowsOrColsAlertDialogTester(final android.app.Activity activity)
    { super(); this.activity = activity; }

    // region Public Methods
    public void testExcludeRows(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;

            if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
                new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog(this.activity,
                    org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogRowLabel,
                    new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler()
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
                    org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogRowLabel);
            }

            assert null != this.templateModel; this.excludeRowsAlertDialog.show(
                this.templateModel.rowItems       (this.rowLabel),
                this.templateModel.rowCheckedItems()             );
        }
    }

    public void testExcludeCols(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel;

            if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
                new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog(this.activity,
                    org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogColumnLabel,
                    new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler()
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
                    org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogColumnLabel);
            }

            assert null != this.templateModel; this.excludeColsAlertDialog.show(
                this.templateModel.colItems       (this.colLabel),
                this.templateModel.colCheckedItems()             );
        }
    }
    // endregion
}