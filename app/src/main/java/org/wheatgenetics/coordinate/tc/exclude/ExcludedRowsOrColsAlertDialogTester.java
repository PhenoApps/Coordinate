package org.wheatgenetics.coordinate.tc.exclude;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ExcludedRowsOrColsAlertDialogTester extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity                        activity;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.model.TemplateModel
        templateModel;

    private org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog           // lazy
        excludedRowsAlertDialogInstance = null, excludedColsAlertDialogInstance = null;     // loads
    private java.lang.String rowLabelInstance = null, colLabelInstance = null;         // lazy loads
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private java.lang.String rowLabel()
    {
        if (null == this.rowLabelInstance) this.rowLabelInstance = this.activity.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel);
        return this.rowLabelInstance;
    }

    // region excludedRowsAlertDialog() Private Methods
    private void excludeRows(@java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.NonNull final boolean checkedItems[])
    {
        int i = 1;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedRow(i);
            i++;
        }
    }

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog excludedRowsAlertDialog()
    {
        if (null == this.excludedRowsAlertDialogInstance) this.excludedRowsAlertDialogInstance =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                    @androidx.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester
                            .this.excludeRows(checkedItems);
                    }
                });
        return this.excludedRowsAlertDialogInstance;
    }
    // endregion

    @androidx.annotation.NonNull private java.lang.String colLabel()
    {
        if (null == this.colLabelInstance) this.colLabelInstance = this.activity.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel);
        return this.colLabelInstance;
    }

    // region excludedColsAlertDialog() Private Methods
    private void excludeCols(@java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.NonNull final boolean checkedItems[])
    {
        int i = 1;
        for (final boolean checkedItem: checkedItems)
        {
            if (checkedItem) this.templateModel.addExcludedCol(i);
            i++;
        }
    }

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog excludedColsAlertDialog()
    {
        if (null == this.excludedColsAlertDialogInstance) this.excludedColsAlertDialogInstance =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog(this.activity,
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                    @androidx.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester
                            .this.excludeCols(checkedItems);
                    }
                });
        return this.excludedColsAlertDialogInstance;
    }
    // endregion
    // endregion

    public ExcludedRowsOrColsAlertDialogTester(
    @androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.TemplateModel
        templateModel)
    { super(); this.activity = activity; this.templateModel = templateModel; }

    // region Public Methods
    public void testExcludedRows()
    {
        this.excludedRowsAlertDialog().show(
            this.templateModel.rowItems       (this.rowLabel()),
            this.templateModel.rowCheckedItems()               );
    }

    public void testExcludedCols()
    {
        this.excludedColsAlertDialog().show(
            this.templateModel.colItems       (this.colLabel()),
            this.templateModel.colCheckedItems()               );
    }
    // endregion
}