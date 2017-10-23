package org.wheatgenetics.coordinate.ui.tc;

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
 * org.wheatgenetics.coordinate.ui.tc.ExcludeCellsAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.ExcludeRowsOrColsAlertDialog.Handler
 */
class ExcludeAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.tc.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.tc.ExcludeCellsAlertDialog
        excludeCellsAlertDialog = null;
    // endregion

    // region Private Methods
    // region exclude(Rows|Cols)() Private Methods
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

    private void exclude(final int which)
    {
        switch (which)
        {
            case 0:
                if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
                    new org.wheatgenetics.coordinate.ui.tc.ExcludeRowsOrColsAlertDialog(
                        this.activity(), org.wheatgenetics.coordinate.R.string.row,
                        new org.wheatgenetics.coordinate.ui.tc.
                            ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void excludeRowsOrCols(final boolean checkedItems[])
                            {
                                org.wheatgenetics.coordinate.ui.tc.
                                    ExcludeAlertDialog.this.excludeRows(checkedItems);
                            }
                        });
                assert null != this.templateModel; this.excludeRowsAlertDialog.show(
                    this.templateModel.rowItems(this.getString(
                        org.wheatgenetics.coordinate.R.string.row)),
                    this.templateModel.rowCheckedItems());
                break;

            case 1:
                if (null == this.excludeColsAlertDialog) this.excludeColsAlertDialog =
                    new org.wheatgenetics.coordinate.ui.tc.ExcludeRowsOrColsAlertDialog(
                        this.activity(), org.wheatgenetics.coordinate.R.string.col,
                        new org.wheatgenetics.coordinate.ui.tc.
                            ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void excludeRowsOrCols(final boolean[] checkedItems)
                            {
                                org.wheatgenetics.coordinate.ui.tc.
                                    ExcludeAlertDialog.this.excludeCols(checkedItems);
                            }
                        });
                assert null != this.templateModel; this.excludeColsAlertDialog.show(
                    this.templateModel.colItems(this.getString(
                        org.wheatgenetics.coordinate.R.string.col)),
                    this.templateModel.colCheckedItems());
                break;

            case 2:
                if (null == this.excludeCellsAlertDialog) this.excludeCellsAlertDialog =
                    new org.wheatgenetics.coordinate.ui.tc.ExcludeCellsAlertDialog(this.activity());
                this.excludeCellsAlertDialog.show(this.templateModel); break;
        }
    }
    // endregion

    ExcludeAlertDialog(final android.app.Activity activity) { super(activity); }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.exclude_title)
            .setItems(new int[] {
                    org.wheatgenetics.coordinate.R.string.rows   ,
                    org.wheatgenetics.coordinate.R.string.cols   ,
                    org.wheatgenetics.coordinate.R.string.random },
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.coordinate.ui.tc.ExcludeAlertDialog.this.exclude(which); }
                })
            .setCancelNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}