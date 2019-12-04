package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.Intent
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeCellsActivity
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
 */
class ExcludeAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;                  // lazy loads
    private android.content.Intent intentInstance = null;                               // lazy load
    private org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
        generatedExcludedCellsAlertDialog = null;                                       // lazy load
    // endregion

    // region Private Methods
    // region exclude(Rows|Cols)() Private Methods
    private void excludeRows(@java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.NonNull final boolean checkedItems[])
    {
        if (null != this.templateModel)
        {
            int i = 1; this.templateModel.clearExcludedRows();
            for (final boolean checkedItem: checkedItems)
            {
                if (checkedItem) this.templateModel.addExcludedRow(i);
                i++;
            }
        }
    }

    private void excludeCols(@java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    @androidx.annotation.NonNull final boolean checkedItems[])
    {
        if (null != this.templateModel)
        {
            int i = 1; this.templateModel.clearExcludedCols();
            for (final boolean checkedItem: checkedItems)
            {
                if (checkedItem) this.templateModel.addExcludedCol(i);
                i++;
            }
        }
    }
    // endregion

    @androidx.annotation.NonNull private android.content.Intent intent()
    {
        if (null == this.intentInstance) this.intentInstance = new android.content.Intent(
            this.activity(), org.wheatgenetics.coordinate.tc.ExcludeCellsActivity.class);

        if (null != this.templateModel)
            this.intentInstance.putExtras(this.templateModel.getState());

        return this.intentInstance;
    }

    private void select(@androidx.annotation.IntRange(from = 0, to = 3) final int which)
    {
        switch (which)
        {
            case 0:
                if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
                    new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(
                        this.activity(),
                        org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                        new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override public void excludeRowsOrCols(
                            @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                            @androidx.annotation.NonNull final boolean checkedItems[])
                            {
                                org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.excludeRows(
                                    checkedItems);
                            }
                        });
                if (null != this.templateModel) this.excludeRowsAlertDialog.show(
                    this.templateModel.rowItems(this.getString(org.wheatgenetics.coordinate
                        .R.string.ExcludedRowsOrColsAlertDialogRowLabel)),
                    this.templateModel.rowCheckedItems()                 );
                break;

            case 1:
                if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
                    new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog(
                        this.activity(),
                        org.wheatgenetics.coordinate
                            .R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                        new org.wheatgenetics.coordinate.tc.ExcludedRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override public void excludeRowsOrCols(
                            @androidx.annotation.NonNull final boolean[] checkedItems)
                            {
                                org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.excludeCols(
                                    checkedItems);
                            }
                        });
                if (null != this.templateModel) this.excludeColsAlertDialog.show(
                    this.templateModel.colItems(this.getString(org.wheatgenetics.coordinate
                        .R.string.ExcludedRowsOrColsAlertDialogColumnLabel)),
                    this.templateModel.colCheckedItems()                    );
                break;

            case 2: this.activity().startActivityForResult(this.intent(), this.requestCode); break;

            case 3:
                if (null == this.generatedExcludedCellsAlertDialog)
                    this.generatedExcludedCellsAlertDialog =
                        new org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog(
                            this.activity());
                this.generatedExcludedCellsAlertDialog.show(this.templateModel); break;
        }
    }
    // endregion

    ExcludeAlertDialog(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode)
    { super(activity); this.requestCode = requestCode; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogTitle)
            .setItems(new int[]{
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRowsItem  ,
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogColsItem  ,
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogCellsItem ,
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRandomItem},
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    { org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.select(which); }
                })
            .setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}