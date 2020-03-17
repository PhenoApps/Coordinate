package org.wheatgenetics.coordinate.tc.exclude;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.content.Intent
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.exclude.ExcludeCellsActivity
 * org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog
 * org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler
 * org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
 */
public class ExcludeAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private java.lang.String rowLabelInstance = null, colLabelInstance = null;         // lazy loads

    private org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog          // lazy
        excludedRowsAlertDialogInstance = null, excludedColsAlertDialogInstance = null;    //  loads
    private android.content.Intent intentInstance = null;                               // lazy load
    private org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
        generatedExcludedCellsAlertDialogInstance = null;                               // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.Nullable private java.lang.String rowLabel()
    {
        if (null == this.rowLabelInstance) this.rowLabelInstance = this.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel);
        return this.rowLabelInstance;
    }

    // region excludedRowsOrColsAlertDialog() Private Methods
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

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog excludedRowsAlertDialog()
    {
        if (null == this.excludedRowsAlertDialogInstance) this.excludedRowsAlertDialogInstance =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog(
                this.activity(),
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                    @androidx.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog.this.excludeRows(
                            checkedItems);
                    }
                });
        return this.excludedRowsAlertDialogInstance;
    }
    // endregion

    @androidx.annotation.Nullable private java.lang.String colLabel()
    {
        if (null == this.colLabelInstance) this.colLabelInstance = this.getString(
            org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel);
        return this.colLabelInstance;
    }

    // region excludedColsAlertDialog() Private Methods
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

    @androidx.annotation.NonNull private
    org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog excludedColsAlertDialog()
    {
        if (null == this.excludedColsAlertDialogInstance) this.excludedColsAlertDialogInstance =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog(
                this.activity(),
                org.wheatgenetics.coordinate.R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                new org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialog.Handler()
                {
                    @java.lang.Override public void excludeRowsOrCols(
                    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
                    @androidx.annotation.NonNull final boolean checkedItems[])
                    {
                        org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog.this.excludeCols(
                            checkedItems);
                    }
                });
        return this.excludedColsAlertDialogInstance;
    }
    // endregion

    @androidx.annotation.NonNull private android.content.Intent intent()
    {
        if (null == this.intentInstance) this.intentInstance = new android.content.Intent(
            this.activity(), org.wheatgenetics.coordinate.tc.exclude.ExcludeCellsActivity.class);

        if (null != this.templateModel)
        {
            final android.os.Bundle bundle = new android.os.Bundle();
            this.templateModel.export(bundle);
            this.intentInstance.putExtras(bundle);
        }

        return this.intentInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog
    generatedExcludedCellsAlertDialog()
    {
        if (null == this.generatedExcludedCellsAlertDialogInstance)
            this.generatedExcludedCellsAlertDialogInstance =
                new org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialog(
                    this.activity());
        return this.generatedExcludedCellsAlertDialogInstance;
    }

    private void select(@androidx.annotation.IntRange(from = 0, to = 3) final int which)
    {
        switch (which)
        {
            case 0: if (null != this.templateModel) this.excludedRowsAlertDialog().show(
                    /* items       [] => */ this.templateModel.rowItems       (this.rowLabel()),
                    /* checkedItems[] => */ this.templateModel.rowCheckedItems()               );
                break;

            case 1: if (null != this.templateModel) this.excludedColsAlertDialog().show(
                    /* items       [] => */ this.templateModel.colItems       (this.colLabel()),
                    /* checkedItems[] => */ this.templateModel.colCheckedItems()               );
                break;

            case 2: this.activity().startActivityForResult(this.intent(), this.requestCode); break;
            case 3: this.generatedExcludedCellsAlertDialog().show(this.templateModel      ); break;
        }
    }
    // endregion

    public ExcludeAlertDialog(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode)
    { super(activity); this.requestCode = requestCode; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogTitle).setItems(
            /* itemIds[] => */ new int[]{
                org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRowsItem  ,
                org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogColsItem  ,
                org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogCellsItem ,
                org.wheatgenetics.coordinate.R.string.ExcludeAlertDialogRandomItem},
            /* onClickListener => */ new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override public void onClick(
                final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog.this.select(which); }
            })
        .setCancelNegativeButton();
    }

    public void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}