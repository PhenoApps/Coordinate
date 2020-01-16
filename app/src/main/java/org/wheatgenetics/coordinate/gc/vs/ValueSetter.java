package org.wheatgenetics.coordinate.gc.vs;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.gc.vs.Handler
 * org.wheatgenetics.coordinate.gc.vs.SetOptionalFieldValuesAlertDialog
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ValueSetter extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity                       activity;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.gc.vs.Handler handler ;

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.gc.vs.SetOptionalFieldValuesAlertDialog
        setOptionalFieldValuesAlertDialogInstance = null;                               // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void clearValues() { this.handler.handleSetValuesDone(null); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.vs.SetOptionalFieldValuesAlertDialog
    setOptionalFieldValuesAlertDialog()
    {
        if (null == this.setOptionalFieldValuesAlertDialogInstance)
            this.setOptionalFieldValuesAlertDialogInstance =
                new org.wheatgenetics.coordinate.gc.vs.SetOptionalFieldValuesAlertDialog(
                    this.activity, this.handler);
        return this.setOptionalFieldValuesAlertDialogInstance;
    }

    private void showSetOptionalFieldValuesAlertDialog(
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        this.setOptionalFieldValuesAlertDialog().show(templateModel.getTitle(),
            optionalFields, /* firstCannotBeEmpty => */ templateModel.isDefaultTemplate());
    }
    // endregion

    public ValueSetter(
    @androidx.annotation.NonNull final android.app.Activity                       activity,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.gc.vs.Handler handler )
    { super(); this.activity = activity; this.handler = handler; }

    public void set(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.handler.setPerson(null);

        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            this.templatesTable().get(templateId);
        if (null == templateModel)
            this.clearValues();
        else
            if (templateModel.optionalFieldsIsEmpty())
                this.clearValues();
            else
            {
                final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                    optionalFields = templateModel.optionalFieldsClone();
                if (null == optionalFields)
                    this.clearValues();
                else
                    this.showSetOptionalFieldValuesAlertDialog(templateModel, optionalFields);
            }
    }
}