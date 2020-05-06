package org.wheatgenetics.coordinate.tc;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class TemplateCreator implements
        AssignTitleRowsColsAlertDialog.Handler,
        SetExcludesOptionalFieldsNumberingAlertDialog.Handler,
        StringGetter {
    // region Fields
    // region Constructor Fields
    private final Activity activity;
    @Types.RequestCode
    private final int requestCode;
    @NonNull
    private final
    TemplateCreator.Handler handler;
    private TemplateModel templateModel;
    // endregion
    private AssignTitleRowsColsAlertDialog
            assignTitleRowsColsAlertDialogInstance = null;                                  // lazy load
    private SetExcludesOptionalFieldsNumberingAlertDialog
            setExcludesOptionalFieldsNumberingAlertDialogInstance = null;                   // lazy load
    public TemplateCreator(final Activity activity,
                           @Types.RequestCode final int requestCode,
                           @NonNull final
                           TemplateCreator.Handler handler) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private AssignTitleRowsColsAlertDialog assignTitleRowsColsAlertDialog() {
        if (null == this.assignTitleRowsColsAlertDialogInstance)
            this.assignTitleRowsColsAlertDialogInstance =
                    new AssignTitleRowsColsAlertDialog(
                            this.activity, this);
        return this.assignTitleRowsColsAlertDialogInstance;
    }

    @NonNull
    private SetExcludesOptionalFieldsNumberingAlertDialog
    setExcludesOptionalFieldsNumberingAlertDialog() {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialogInstance)
            this.setExcludesOptionalFieldsNumberingAlertDialogInstance =
                    new SetExcludesOptionalFieldsNumberingAlertDialog(
                            this.activity, this.requestCode, this);
        return this.setExcludesOptionalFieldsNumberingAlertDialogInstance;
    }
    // endregion

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialog.Handler Overridden Method
    @Override
    public void handleAssignDone() {
        this.setExcludesOptionalFieldsNumberingAlertDialog().show(this.templateModel);
    }

    // region org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog Overridden Method
    @Override
    public void handleSetDone() {
        this.handler.handleTemplateCreated(this.templateModel);
    }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.activity.getString(resId);
    }
    // endregion

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.activity.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    // region Public Methods
    public void create() {
        this.templateModel = TemplateModel.makeUserDefined(this);
        this.assignTitleRowsColsAlertDialog().show(this.templateModel);
    }
    // endregion
    // endregion

    public void continueExcluding(final Bundle bundle) {
        this.templateModel =
                TemplateModel.makeUserDefined(bundle, this);
        this.handleAssignDone();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleTemplateCreated(@NonNull
                                                           TemplateModel templateModel);
    }
    // endregion
}