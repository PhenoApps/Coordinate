package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class ExcludeAlertDialogTester {
    // region Fields
    private final Activity activity;
    @Types.RequestCode
    private final int requestCode;
    private final TemplateModel templateModel;

    private ExcludeAlertDialog
            excludeAlertDialogInstance = null;                                              // lazy load
    // endregion

    public ExcludeAlertDialogTester(final Activity activity,
                                    @Types.RequestCode final int requestCode,
                                    final TemplateModel templateModel) {
        super();

        this.activity = activity;
        this.requestCode = requestCode;
        this.templateModel = templateModel;
    }

    @NonNull
    private ExcludeAlertDialog excludeAlertDialog() {
        if (null == this.excludeAlertDialogInstance) this.excludeAlertDialogInstance =
                new ExcludeAlertDialog(
                        this.activity, this.requestCode);
        return this.excludeAlertDialogInstance;
    }

    public void testExclude() {
        this.excludeAlertDialog().show(this.templateModel);
    }
}