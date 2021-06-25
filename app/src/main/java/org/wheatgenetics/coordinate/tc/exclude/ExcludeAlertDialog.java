package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class ExcludeAlertDialog extends AlertDialog {
    // region Fields
    @Types.RequestCode
    private final int requestCode;

    private TemplateModel templateModel;

    private String rowLabelInstance = null, colLabelInstance = null;         // lazy loads

    private ExcludedRowsOrColsAlertDialog          // lazy
            excludedRowsAlertDialogInstance = null, excludedColsAlertDialogInstance = null;    //  loads
    private Intent intentInstance = null;                               // lazy load
    private GeneratedExcludedCellsAlertDialog
            generatedExcludedCellsAlertDialogInstance = null;                               // lazy load
    // endregion

    public ExcludeAlertDialog(final Activity activity,
                              @Types.RequestCode final int requestCode) {
        super(activity);
        this.requestCode = requestCode;
    }

    // region Private Methods
    @Nullable
    private String rowLabel() {
        if (null == this.rowLabelInstance) this.rowLabelInstance = this.getString(
                R.string.ExcludedRowsOrColsAlertDialogRowLabel);
        return this.rowLabelInstance;
    }

    // region excludedRowsOrColsAlertDialog() Private Methods
    private void excludeRows(@SuppressWarnings({"CStyleArrayDeclaration"})
                             @NonNull final boolean checkedItems[]) {
        if (null != this.templateModel) {
            int i = 1;
            this.templateModel.clearExcludedRows();
            for (final boolean checkedItem : checkedItems) {
                if (checkedItem) this.templateModel.addExcludedRow(i);
                i++;
            }
        }
    }
    // endregion

    @NonNull
    private ExcludedRowsOrColsAlertDialog excludedRowsAlertDialog() {
        if (null == this.excludedRowsAlertDialogInstance) this.excludedRowsAlertDialogInstance =
                new ExcludedRowsOrColsAlertDialog(
                        this.activity(),
                        R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                        new ExcludedRowsOrColsAlertDialog.Handler() {
                            @Override
                            public void excludeRowsOrCols(
                                    @SuppressWarnings({"CStyleArrayDeclaration"})
                                    @NonNull final boolean checkedItems[]) {
                                ExcludeAlertDialog.this.excludeRows(
                                        checkedItems);
                            }
                        });
        return this.excludedRowsAlertDialogInstance;
    }

    @Nullable
    private String colLabel() {
        if (null == this.colLabelInstance) this.colLabelInstance = this.getString(
                R.string.ExcludedRowsOrColsAlertDialogColumnLabel);
        return this.colLabelInstance;
    }

    // region excludedColsAlertDialog() Private Methods
    private void excludeCols(@SuppressWarnings({"CStyleArrayDeclaration"})
                             @NonNull final boolean checkedItems[]) {
        if (null != this.templateModel) {
            int i = 1;
            this.templateModel.clearExcludedCols();
            for (final boolean checkedItem : checkedItems) {
                if (checkedItem) this.templateModel.addExcludedCol(i);
                i++;
            }
        }
    }
    // endregion

    @NonNull
    private ExcludedRowsOrColsAlertDialog excludedColsAlertDialog() {
        if (null == this.excludedColsAlertDialogInstance) this.excludedColsAlertDialogInstance =
                new ExcludedRowsOrColsAlertDialog(
                        this.activity(),
                        R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                        new ExcludedRowsOrColsAlertDialog.Handler() {
                            @Override
                            public void excludeRowsOrCols(
                                    @SuppressWarnings({"CStyleArrayDeclaration"})
                                    @NonNull final boolean checkedItems[]) {
                                ExcludeAlertDialog.this.excludeCols(
                                        checkedItems);
                            }
                        });
        return this.excludedColsAlertDialogInstance;
    }

    @NonNull
    private Intent intent() {
        if (null == this.intentInstance) this.intentInstance = new Intent(
                this.activity(), ExcludeCellsActivity.class);

        if (null != this.templateModel) {
            final Bundle bundle = new Bundle();
            this.templateModel.export(bundle);
            this.intentInstance.putExtras(bundle);
        }

        return this.intentInstance;
    }

    @NonNull
    private GeneratedExcludedCellsAlertDialog
    generatedExcludedCellsAlertDialog() {
        if (null == this.generatedExcludedCellsAlertDialogInstance)
            this.generatedExcludedCellsAlertDialogInstance =
                    new GeneratedExcludedCellsAlertDialog(
                            this.activity());
        return this.generatedExcludedCellsAlertDialogInstance;
    }
    // endregion

    private void select(@IntRange(from = 0, to = 3) final int which) {
        switch (which) {
            case 0:
                if (null != this.templateModel) this.excludedRowsAlertDialog().show(
                        /* items       [] => */ this.templateModel.rowItems(this.rowLabel()),
                        /* checkedItems[] => */ this.templateModel.rowCheckedItems());
                break;

            case 1:
                if (null != this.templateModel) this.excludedColsAlertDialog().show(
                        /* items       [] => */ this.templateModel.colItems(this.colLabel()),
                        /* checkedItems[] => */ this.templateModel.colCheckedItems());
                break;

            case 2:
                this.activity().startActivityForResult(this.intent(), this.requestCode);
                break;
            case 3:
                this.generatedExcludedCellsAlertDialog().show(this.templateModel);
                break;
        }
    }

    @Override
    public void configure() {
        this.setTitle(R.string.ExcludeAlertDialogTitle).setItems(
                /* itemIds[] => */ new int[]{
                        R.string.ExcludeAlertDialogRowsItem,
                        R.string.ExcludeAlertDialogColsItem,
                        R.string.ExcludeAlertDialogCellsItem,
                        R.string.ExcludeAlertDialogRandomItem},
                /* onClickListener => */ new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            final DialogInterface dialog, final int which) {
                        ExcludeAlertDialog.this.select(which);
                    }
                })
                .setCancelNegativeButton();
    }

    public void show(final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateModel = templateModel;
            this.show();
        }
    }
}