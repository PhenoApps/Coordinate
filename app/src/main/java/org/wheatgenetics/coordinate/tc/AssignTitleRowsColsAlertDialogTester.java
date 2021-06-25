package org.wheatgenetics.coordinate.tc;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.model.TemplateModel;

public class AssignTitleRowsColsAlertDialogTester
        implements AssignTitleRowsColsAlertDialog.Handler {
    // region Fields
    private final Activity activity;
    private final TemplateModel templateModel;
    @NonNull
    private final
    AssignTitleRowsColsAlertDialogTester.Handler handler;
    private AssignTitleRowsColsAlertDialog
            assignTitleRowsColsAlertDialog = null;                                          // lazy load

    public AssignTitleRowsColsAlertDialogTester(final Activity activity,
                                                final TemplateModel templateModel,
                                                @NonNull final
                                                AssignTitleRowsColsAlertDialogTester.Handler handler) {
        super();
        this.activity = activity;
        this.templateModel = templateModel;
        this.handler = handler;
    }
    // endregion

    @Override
    public void handleAssignDone() {
        this.handler.handleAssignDone();
    }

    public void testAssignTitleRowsCols() {
        if (null == this.assignTitleRowsColsAlertDialog) this.assignTitleRowsColsAlertDialog =
                new AssignTitleRowsColsAlertDialog(
                        this.activity, this);
        this.assignTitleRowsColsAlertDialog.show(this.templateModel);
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleAssignDone();
    }
}