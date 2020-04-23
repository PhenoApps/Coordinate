package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Types.RequestCode
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog
 * org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler
 * org.wheatgenetics.coordinate.gc.ts.TemplateSetter
 */
public abstract class ChoosingTemplateSetter
extends org.wheatgenetics.coordinate.gc.ts.TemplateSetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleTemplateSet(
        @androidx.annotation.IntRange(from = 1) long templateId);
    }

    // region Fields
    // region Constructor Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode                  ;
    @androidx.annotation.StringRes               private final int templateChoiceAlertDialogOldItem;
    @androidx.annotation.NonNull                 private final
        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.Handler handler;
    // endregion

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll

    // region Choose Old Fields
    private org.wheatgenetics.coordinate.model.TemplateModels templateModels = null;
    private org.wheatgenetics.coordinate.SelectAlertDialog
        chooseOldAlertDialogInstance = null;                                            // lazy load
    // endregion

    // region Choose New Field
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreatorInstance = null;    // ll
    // endregion

    private org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog
        templateChoiceAlertDialogInstance = null;                                       // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }

    // region Choose Old Private Methods
    private void chooseOldAfterSelect(final int which)
    {
        if (null != this.templateModels)
        {
            final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                this.templateModels.get(which);
            if (null != templateModel) this.handler.handleTemplateSet(templateModel.getId());
            this.templateModels = null;
        }
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.SelectAlertDialog chooseOldAlertDialog()
    {
        if (null == this.chooseOldAlertDialogInstance) this.chooseOldAlertDialogInstance =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity(),
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @java.lang.Override public void select(final int which)
                    {
                        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
                            .this.chooseOldAfterSelect(which);
                    }
                });
        return this.chooseOldAlertDialogInstance;
    }
    // endregion

    // region Choose New Private Methods
    private void handleTemplateCreated(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final long templateId = this.templatesTable().insert(templateModel);
        if (org.wheatgenetics.coordinate.model.Model.illegal(templateId))
            org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                org.wheatgenetics.coordinate.R.string.TemplateSetterTemplateAlertMessage);
        else
            this.handler.handleTemplateSet(templateId);
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator()
    {
        if (null == this.templateCreatorInstance) this.templateCreatorInstance =
            new org.wheatgenetics.coordinate.tc.TemplateCreator(this.activity(),
                this.requestCode, new org.wheatgenetics.coordinate.tc.TemplateCreator.Handler()
                {
                    @java.lang.Override
                    public void handleTemplateCreated(@androidx.annotation.NonNull
                    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
                    {
                        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
                            .this.handleTemplateCreated(templateModel);
                    }
                });
        return this.templateCreatorInstance;
    }
    // endregion

    // region templateChoiceAlertDialog Private Methods
    private void chooseOld()
    {
        this.templateModels = this.templatesTable().load();
        if (null != this.templateModels) this.chooseOldAlertDialog().show(
            this.templateChoiceAlertDialogOldItem, this.templateModels.titles());
    }

    private void chooseNew() { this.templateCreator().create(); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog templateChoiceAlertDialog()
    {
        if (null == this.templateChoiceAlertDialogInstance) this.templateChoiceAlertDialogInstance =
            new org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog(
                this.activity(), this.templateChoiceAlertDialogOldItem,
                new org.wheatgenetics.coordinate.gc.ts.TemplateChoiceAlertDialog.Handler()
                {
                    @java.lang.Override public void chooseOld()
                    { org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.this.chooseOld(); }

                    @java.lang.Override public void chooseNew()
                    { org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.this.chooseNew(); }
                });
        return this.templateChoiceAlertDialogInstance;
    }
    // endregion
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    ChoosingTemplateSetter(                         final android.app.Activity     activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                      requestCode,
    @androidx.annotation.StringRes                  final int templateChoiceAlertDialogOldItem,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.Handler handler)
    {
        super(activity);

        this.requestCode                      = requestCode                     ;
        this.templateChoiceAlertDialogOldItem = templateChoiceAlertDialogOldItem;
        this.handler                          = handler                         ;
    }

    // region Public Methods
    public void set() { this.templateChoiceAlertDialog().show(); }

    public void continueExcluding(final android.os.Bundle bundle)
    { this.templateCreator().continueExcluding(bundle); }
    // endregion
}