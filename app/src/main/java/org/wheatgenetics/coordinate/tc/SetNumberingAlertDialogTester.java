package org.wheatgenetics.coordinate.tc;

import android.app.Activity;

import org.wheatgenetics.coordinate.model.TemplateModel;

public class SetNumberingAlertDialogTester {
    // region Fields
    private final Activity activity;
    private final TemplateModel templateModel;

    private SetNumberingAlertDialog
            setNumberingAlertDialog = null;                                                 // lazy load
    // endregion

    public SetNumberingAlertDialogTester(final Activity activity,
                                         final TemplateModel templateModel) {
        super();
        this.activity = activity;
        this.templateModel = templateModel;
    }

    public void testSetNumbering() {
        if (null == this.setNumberingAlertDialog) this.setNumberingAlertDialog =
                new SetNumberingAlertDialog(this.activity);
        this.setNumberingAlertDialog.show(this.templateModel);
    }
}