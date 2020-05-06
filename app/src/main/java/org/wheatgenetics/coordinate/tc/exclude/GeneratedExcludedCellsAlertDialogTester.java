package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.model.TemplateModel;

public class GeneratedExcludedCellsAlertDialogTester {
    // region Fields
    private final Activity activity;
    private final TemplateModel templateModel;

    private GeneratedExcludedCellsAlertDialog
            generatedExcludedCellsAlertDialogInstance = null;                               // lazy load
    // endregion

    public GeneratedExcludedCellsAlertDialogTester(final Activity activity,
                                                   final TemplateModel templateModel) {
        super();
        this.activity = activity;
        this.templateModel = templateModel;
    }

    @NonNull
    private GeneratedExcludedCellsAlertDialog
    generatedExcludedCellsAlertDialog() {
        if (null == this.generatedExcludedCellsAlertDialogInstance)
            this.generatedExcludedCellsAlertDialogInstance =
                    new GeneratedExcludedCellsAlertDialog(
                            this.activity);
        return this.generatedExcludedCellsAlertDialogInstance;
    }

    public void testGeneratedExcludedCells() {
        this.generatedExcludedCellsAlertDialog().show(this.templateModel);
    }
}