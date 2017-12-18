package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
 */
class ExcludeAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    private org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog
        generatedExcludedCellsAlertDialog = null;
    // endregion

    // region Private Methods
    // region exclude(Rows|Cols)() Private Methods
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

    private void exclude(final int which)
    {
        switch (which)
        {
            case 0:
                if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
                    new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog(
                        this.activity(),
                        org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogRowLabel,
                        new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void excludeRowsOrCols(final boolean checkedItems[])
                            {
                                org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.excludeRows(
                                    checkedItems);
                            }
                        });
                assert null != this.templateModel; this.excludeRowsAlertDialog.show(
                    this.templateModel.rowItems(this.getString(org.wheatgenetics.coordinate
                        .R.string.ExcludeRowsOrColsAlertDialogRowLabel)),
                    this.templateModel.rowCheckedItems()                );
                break;

            case 1:
                if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
                    new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog(
                        this.activity(),
                        org.wheatgenetics.coordinate.R.string.ExcludeRowsOrColsAlertDialogColumnLabel,
                        new org.wheatgenetics.coordinate.tc.ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void excludeRowsOrCols(final boolean[] checkedItems)
                            {
                                org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.excludeCols(
                                    checkedItems);
                            }
                        });
                assert null != this.templateModel; this.excludeColsAlertDialog.show(
                    this.templateModel.colItems(this.getString(org.wheatgenetics.coordinate
                        .R.string.ExcludeRowsOrColsAlertDialogColumnLabel)),
                    this.templateModel.colCheckedItems()                   );
                break;

            case 2:
                if (null == this.generatedExcludedCellsAlertDialog)
                    this.generatedExcludedCellsAlertDialog =
                        new org.wheatgenetics.coordinate.tc.GeneratedExcludedCellsAlertDialog(
                            this.activity());
                this.generatedExcludedCellsAlertDialog.show(this.templateModel); break;
        }
    }
    // endregion

    ExcludeAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogTitle)
            .setItems(new int[] {
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRowsItem   ,
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogColsItem   ,
                    org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRandomItem },
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.coordinate.tc.ExcludeAlertDialog.this.exclude(which); }
                })
            .setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}