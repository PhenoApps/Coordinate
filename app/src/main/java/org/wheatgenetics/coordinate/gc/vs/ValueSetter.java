package org.wheatgenetics.coordinate.gc.vs;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

public class ValueSetter {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final Handler handler;

    private TemplatesTable templatesTableInstance = null;// ll
    private SetOptionalFieldValuesAlertDialog
            setOptionalFieldValuesAlertDialogInstance = null;                               // lazy load
    // endregion

    public ValueSetter(
            @NonNull final Activity activity,
            @NonNull final Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }

    // region Private Methods
    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void clearValues() {
        this.handler.handleSetValuesDone(null);
    }

    @NonNull
    private SetOptionalFieldValuesAlertDialog
    setOptionalFieldValuesAlertDialog() {
        if (null == this.setOptionalFieldValuesAlertDialogInstance)
            this.setOptionalFieldValuesAlertDialogInstance =
                    new SetOptionalFieldValuesAlertDialog(
                            this.activity, this.handler);
        return this.setOptionalFieldValuesAlertDialogInstance;
    }
    // endregion

    private void showSetOptionalFieldValuesAlertDialog(
            @NonNull final
            TemplateModel templateModel,
            @NonNull final
            NonNullOptionalFields optionalFields) {
        this.setOptionalFieldValuesAlertDialog().show(templateModel.getTitle(),
                optionalFields, /* firstCannotBeEmpty => */ templateModel.isDefaultTemplate());
    }

    public void set(@IntRange(from = 1) final long templateId) {
        this.handler.setPerson(null);

        final TemplateModel templateModel =
                this.templatesTable().get(templateId);
        if (null == templateModel)
            this.clearValues();
        else if (templateModel.optionalFieldsIsEmpty())
            this.clearValues();
        else {
            final NonNullOptionalFields
                    optionalFields = templateModel.optionalFieldsClone();
            if (null == optionalFields)
                this.clearValues();
            else
                this.showSetOptionalFieldValuesAlertDialog(templateModel, optionalFields);
        }
    }
}