package org.wheatgenetics.coordinate.tc;

import android.app.Activity;

import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class SetExcludesOptionalFieldsNumberingAlertDialogTester
        implements SetExcludesOptionalFieldsNumberingAlertDialog.Handler {
    // region Fields
    private final Activity activity;
    @Types.RequestCode
    private final int requestCode;
    private final TemplateModel templateModel;

    private SetExcludesOptionalFieldsNumberingAlertDialog
            setExcludesOptionalFieldsNumberingAlertDialog = null;                           // lazy load
    // endregion

    public SetExcludesOptionalFieldsNumberingAlertDialogTester(final Activity activity,
                                                               @Types.RequestCode final int requestCode,
                                                               final TemplateModel templateModel) {
        super();

        this.activity = activity;
        this.requestCode = requestCode;
        this.templateModel = templateModel;
    }

    // region org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler Overridden Method
    @Override
    public void handleSetDone() {
        if (null != this.setExcludesOptionalFieldsNumberingAlertDialog)
            this.setExcludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
    // endregion

    public void testSetExcludesOptionalFieldsNumbering() {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialog)
            this.setExcludesOptionalFieldsNumberingAlertDialog =
                    new SetExcludesOptionalFieldsNumberingAlertDialog(
                            this.activity, this.requestCode, this);
        this.setExcludesOptionalFieldsNumberingAlertDialog.show(this.templateModel);
    }
}