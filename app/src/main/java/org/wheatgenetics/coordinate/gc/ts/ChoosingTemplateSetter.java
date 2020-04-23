package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
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
 * org.wheatgenetics.coordinate.gc.ts.TemplateSetter
 */
public class ChoosingTemplateSetter extends org.wheatgenetics.coordinate.gc.ts.TemplateSetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void handleTemplateSet(
        @androidx.annotation.IntRange(from = 1) long templateId);
    }

    // region Fields
    // region Constructor Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode                  ;
    @androidx.annotation.NonNull                    private final
        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.Handler handler;
    // endregion

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll

    private org.wheatgenetics.coordinate.tc.TemplateCreator   templateCreatorInstance = null;  // ll
    private org.wheatgenetics.coordinate.model.TemplateModels templateModels          = null;

    private org.wheatgenetics.coordinate.SelectAlertDialog
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

    // region templateCreator Private Methods
    private void handleTemplateCreated(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final long templateId = this.templatesTable().insert(templateModel);
        if (org.wheatgenetics.coordinate.model.Model.illegal(templateId))
            org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                org.wheatgenetics.coordinate.R.string.ChoosingTemplateSetterAlertMessage);
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
    private void createTemplate() { this.templateCreator().create(); }

    private void chooseExisting(@androidx.annotation.IntRange(from = 0) final int which)
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
    private org.wheatgenetics.coordinate.SelectAlertDialog templateChoiceAlertDialog()
    {
        if (null == this.templateChoiceAlertDialogInstance) this.templateChoiceAlertDialogInstance =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity(),
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @java.lang.Override public void select(final int which)
                    {
                        if (which < 0)
                            throw new java.lang.IllegalArgumentException();
                        else
                            if (which == 0)
                                org.wheatgenetics.coordinate.gc.ts
                                    .ChoosingTemplateSetter.this.createTemplate();
                            else
                                org.wheatgenetics.coordinate.gc.ts
                                    .ChoosingTemplateSetter.this.chooseExisting(which - 1);
                    }
                });
        return this.templateChoiceAlertDialogInstance;
    }

    private void showTemplateChoiceAlertDialog()
    {
        @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final java.lang.String items[];
        {
            @androidx.annotation.NonNull final java.lang.String firstItem =
                this.activity().getString(
                    org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogNewItem);

            this.templateModels = this.templatesTable().load();
            if (null == this.templateModels)
                items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
            else
            {
                @androidx.annotation.Nullable final java.lang.String[] titles =
                    this.templateModels.titles();
                if (null == titles)
                    items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
                else
                    if (titles.length <= 0)
                        items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
                    else
                    {
                        // noinspection Convert2Diamond
                        final java.util.ArrayList<java.lang.String> arrayList =
                            new java.util.ArrayList<java.lang.String>(
                                1 + titles.length);
                        arrayList.add(firstItem); arrayList.addAll(java.util.Arrays.asList(titles));
                        arrayList.toArray(items = new java.lang.String[arrayList.size()]);
                    }
            }
        }
        this.templateChoiceAlertDialog().show(
            org.wheatgenetics.coordinate.R.string.TemplateChoiceAlertDialogTitle, items);
    }
    // endregion
    // endregion

    public ChoosingTemplateSetter(                  final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.Handler handler)
    { super(activity); this.requestCode = requestCode; this.handler = handler; }

    // region Public Methods
    public void set() { this.showTemplateChoiceAlertDialog(); }

    public void continueExcluding(final android.os.Bundle bundle)
    { this.templateCreator().continueExcluding(bundle); }
    // endregion
}