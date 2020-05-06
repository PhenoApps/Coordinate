package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class ExcludedRowsOrColsAlertDialogTester {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final TemplateModel
            templateModel;

    private ExcludedRowsOrColsAlertDialog           // lazy
            excludedRowsAlertDialogInstance = null, excludedColsAlertDialogInstance = null;     // loads
    private String rowLabelInstance = null, colLabelInstance = null;         // lazy loads
    // endregion

    public ExcludedRowsOrColsAlertDialogTester(
            @NonNull final Activity activity,
            @NonNull final TemplateModel
                    templateModel) {
        super();
        this.activity = activity;
        this.templateModel = templateModel;
    }

    // region Private Methods
    @NonNull
    private String rowLabel() {
        if (null == this.rowLabelInstance) this.rowLabelInstance = this.activity.getString(
                R.string.ExcludedRowsOrColsAlertDialogRowLabel);
        return this.rowLabelInstance;
    }

    // region excludedRowsAlertDialog() Private Methods
    private void excludeRows(@SuppressWarnings({"CStyleArrayDeclaration"})
                             @NonNull final boolean checkedItems[]) {
        int i = 1;
        for (final boolean checkedItem : checkedItems) {
            if (checkedItem) this.templateModel.addExcludedRow(i);
            i++;
        }
    }
    // endregion

    @NonNull
    private ExcludedRowsOrColsAlertDialog excludedRowsAlertDialog() {
        if (null == this.excludedRowsAlertDialogInstance) this.excludedRowsAlertDialogInstance =
                new ExcludedRowsOrColsAlertDialog(this.activity,
                        R.string.ExcludedRowsOrColsAlertDialogRowLabel,
                        new ExcludedRowsOrColsAlertDialog.Handler() {
                            @Override
                            public void excludeRowsOrCols(
                                    @SuppressWarnings({"CStyleArrayDeclaration"})
                                    @NonNull final boolean checkedItems[]) {
                                ExcludedRowsOrColsAlertDialogTester
                                        .this.excludeRows(checkedItems);
                            }
                        });
        return this.excludedRowsAlertDialogInstance;
    }

    @NonNull
    private String colLabel() {
        if (null == this.colLabelInstance) this.colLabelInstance = this.activity.getString(
                R.string.ExcludedRowsOrColsAlertDialogColumnLabel);
        return this.colLabelInstance;
    }

    // region excludedColsAlertDialog() Private Methods
    private void excludeCols(@SuppressWarnings({"CStyleArrayDeclaration"})
                             @NonNull final boolean checkedItems[]) {
        int i = 1;
        for (final boolean checkedItem : checkedItems) {
            if (checkedItem) this.templateModel.addExcludedCol(i);
            i++;
        }
    }
    // endregion
    // endregion

    @NonNull
    private ExcludedRowsOrColsAlertDialog excludedColsAlertDialog() {
        if (null == this.excludedColsAlertDialogInstance) this.excludedColsAlertDialogInstance =
                new ExcludedRowsOrColsAlertDialog(this.activity,
                        R.string.ExcludedRowsOrColsAlertDialogColumnLabel,
                        new ExcludedRowsOrColsAlertDialog.Handler() {
                            @Override
                            public void excludeRowsOrCols(
                                    @SuppressWarnings({"CStyleArrayDeclaration"})
                                    @NonNull final boolean checkedItems[]) {
                                ExcludedRowsOrColsAlertDialogTester
                                        .this.excludeCols(checkedItems);
                            }
                        });
        return this.excludedColsAlertDialogInstance;
    }

    // region Public Methods
    public void testExcludedRows() {
        this.excludedRowsAlertDialog().show(
                this.templateModel.rowItems(this.rowLabel()),
                this.templateModel.rowCheckedItems());
    }

    public void testExcludedCols() {
        this.excludedColsAlertDialog().show(
                this.templateModel.colItems(this.colLabel()),
                this.templateModel.colCheckedItems());
    }
    // endregion
}