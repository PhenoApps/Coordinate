package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog
 * org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler
 * org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog
 * org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class GridCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler,
org.wheatgenetics.coordinate.SelectAlertDialog.Handler,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler,
org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler { public abstract void handleGridCreated(long gridId); }

    // region Fields
    private final android.app.Activity                                activity;
    private final org.wheatgenetics.coordinate.gc.GridCreator.Handler handler ;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null;

    private java.lang.String                                                  person        ;
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields  optionalFields;
    private org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog
        setOptionalFieldValuesAlertDialog = null;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.SelectAlertDialog       chooseOldAlertDialog   = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.model.TemplateModels    templateModels         = null;

    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator = null;

    private org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog
        getTemplateChoiceAlertDialog = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void setValues()
    {
        this.person = null;

        assert null != this.templateModel;
        if (this.templateModel.optionalFieldsIsEmpty())
        {
            this.optionalFields = null;
            this.handleSetValuesDone();
        }
        else
        {
            this.optionalFields = this.templateModel.optionalFieldsClone();
            if (null == this.setOptionalFieldValuesAlertDialog)
                this.setOptionalFieldValuesAlertDialog =
                    new org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog(
                        this.activity, this);
            this.setOptionalFieldValuesAlertDialog.show(this.templateModel.getTitle(),
                new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
                    this.optionalFields),
                /* firstCannotBeEmpty => */ this.templateModel.isDefaultTemplate());
        }
    }

    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }
    // endregion

    public GridCreator(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.gc.GridCreator.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void setPerson(final java.lang.String person) { this.person = person; }

    @java.lang.Override
    public void handleSetValuesDone()
    {
        final long gridId = this.gridsTable().insert(
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* person         => */ this.person        ,
                /* optionalFields => */ this.optionalFields,
                /* templateModel  => */ this.templateModel ));
        this.person = null; this.optionalFields = null; this.templateModel = null;

        if (gridId <= 0)
            org.wheatgenetics.coordinate.Utils.alert(this.activity,
                org.wheatgenetics.coordinate.R.string.GridCreatorGridAlertMessage);
        assert null != this.handler; this.handler.handleGridCreated(gridId);
    }
    // endregion

    // region org.wheatgenetics.coordinate.SelectAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void select(final int which)
    {
        assert null != this.templateModels; this.templateModel = this.templateModels.get(which);
        this.templateModels = null;
        this.setValues();
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override
    public void handleTemplateCreated(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        this.templateModel = templateModel;

        final boolean inserted;
        {
            final long templateId = this.templatesTable().insert(this.templateModel);
            if (templateId > 0)
            {
                assert null != this.templateModel; this.templateModel.setId(templateId);
                inserted = true;
            }
            else
            {
                org.wheatgenetics.coordinate.Utils.alert(this.activity,
                    org.wheatgenetics.coordinate.R.string.GridCreatorTemplateAlertMessage);
                inserted = false;
            }
        }

        if (inserted)
            this.setValues();
        else
            { assert null != this.handler; this.handler.handleGridCreated(0); }
    }
    // endregion

    // region org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void chooseOld()
    {
        if (null == this.chooseOldAlertDialog) this.chooseOldAlertDialog =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity, this);
        this.templateModels = this.templatesTable().load();
        assert null != this.templateModels; this.chooseOldAlertDialog.show(
            org.wheatgenetics.coordinate.R.string.ChooseOldAlertDialogTitle,
            this.templateModels.titles()                                   );
    }

    @java.lang.Override
    public void chooseNew()
    {
        if (null == this.templateCreator) this.templateCreator =
            new org.wheatgenetics.coordinate.tc.TemplateCreator(this.activity, this);
        this.templateCreator.create();
    }
    // endregion
    // endregion

    public void create()
    {
        if (null == this.getTemplateChoiceAlertDialog) this.getTemplateChoiceAlertDialog =
            new org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog(this.activity, this);
        this.getTemplateChoiceAlertDialog.show();
    }
}