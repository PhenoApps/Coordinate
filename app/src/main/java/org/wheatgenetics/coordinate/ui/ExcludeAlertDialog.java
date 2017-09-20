package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
 */
class ExcludeAlertDialog extends org.wheatgenetics.coordinate.ui.InternalItemsAlertDialog
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog
        excludeRowsAlertDialog = null, excludeColsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog excludeCellsAlertDialog = null;
    // endregion

    // region Private Methods
    // region exclude(Rows|Cols)() Private Methods
    private void excludeRows(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
            if (checkedItem) this.templateModel.addExcludeRow(i++);
    }

    private void excludeCols(final boolean checkedItems[])
    {
        int i = 1;
        assert null != checkedItems; assert null != this.templateModel;
        for (final boolean checkedItem: checkedItems)
            if (checkedItem) this.templateModel.addExcludeCol(i++);
    }
    // endregion

    private void exclude(final int which)
    {
        switch (which)
        {
            case 0:
                if (null == this.excludeRowsAlertDialog) this.excludeRowsAlertDialog =
                    new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this.activity,
                        org.wheatgenetics.coordinate.R.string.row,
                        new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void process(final boolean checkedItems[])
                            {
                                org.wheatgenetics.coordinate.ui.
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
                    new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog(this.activity,
                        org.wheatgenetics.coordinate.R.string.col,
                        new org.wheatgenetics.coordinate.ui.ExcludeRowsOrColsAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void process(final boolean[] checkedItems)
                            {
                                org.wheatgenetics.coordinate.ui.
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
                    new org.wheatgenetics.coordinate.ui.ExcludeCellsAlertDialog(this.activity);
                this.excludeCellsAlertDialog.show(this.templateModel); break;
        }
    }
    // endregion

    ExcludeAlertDialog(final android.app.Activity activity)
    {
        super(activity, org.wheatgenetics.coordinate.R.string.exclude_title);
        this.activity = activity;
    }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId).setItems(new java.lang.String[] {
                this.getString(org.wheatgenetics.coordinate.R.string.rows  ),
                this.getString(org.wheatgenetics.coordinate.R.string.cols  ),
                this.getString(org.wheatgenetics.coordinate.R.string.random)},
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.ExcludeAlertDialog.this.exclude(which); }
            });
        return this.setNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.templateModel = templateModel; this.makeConfiguredTrue();
            this.show();
        }
    }
}