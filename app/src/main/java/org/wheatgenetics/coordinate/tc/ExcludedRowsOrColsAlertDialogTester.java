package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ExcludedRowsOrColsAlertDialogTester extends java.lang.Object
{
    // region Fields
    @android.support.annotation.NonNull private final android.app.Activity activity;
    @android.support.annotation.NonNull private final
        org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
        excludedRowsAlertDialog = null, excludedColsAlertDialog = null;                 // lazy load
    private java.lang.String rowLabel = null, colLabel = null;
    // endregion

    // region Private Methods
    private void excludeRows(@android.support.annotation.NonNull final boolean checkedItems[])
    {
        int i = 1;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedRow(i);
            i++;
        }
    }

    private void excludeCols(@android.support.annotation.NonNull final boolean checkedItems[])
    {
        int i = 1;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedCol(i);
            i++;
        }
    }
    // endregion

    public ExcludedRowsOrColsAlertDialogTester(
    @android.support.annotation.NonNull final android.app.Activity activity,
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.TemplateModel
        templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    // region Public Methods
    public void testExcludedRows()
    {
        if (null == this.excludedRowsAlertDialog) this.excludedRowsAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @android.support.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc
                            .ExcludedRowsOrColsAlertDialogTester.this.excludeRows(checkedItems);
                    }
                });

        if (null == this.rowLabel) this.rowLabel = this.activity.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel);

        this.excludedRowsAlertDialog.show(
            this.templateModel.rowItems       (this.rowLabel),
            this.templateModel.rowCheckedItems()             );
    }

    public void testExcludedCols()
    {
        if (null == this.excludedColsAlertDialog) this.excludedColsAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @android.support.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc
                            .ExcludedRowsOrColsAlertDialogTester.this.excludeCols(checkedItems);
                    }
                });

        if (null == this.colLabel) this.colLabel = this.activity.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel);

        this.excludedColsAlertDialog.show(
            this.templateModel.colItems       (this.colLabel),
            this.templateModel.colCheckedItems()             );
    }
    // endregion
}