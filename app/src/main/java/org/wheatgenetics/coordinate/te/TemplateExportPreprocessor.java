package org.wheatgenetics.coordinate.te;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateExportPreprocessor extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void exportTemplate(
        @androidx.annotation.IntRange(from = 1) long templateId, java.lang.String fileName);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.app.Activity activity;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long templateId;

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null; //ll
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialogInstance = null;                                    // lazy load
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void exportTempate(final java.lang.String fileName)
    { this.handler.exportTemplate(this.templateId, fileName); }

    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
    getExportFileNameAlertDialog()
    {
        if (null == this.getExportFileNameAlertDialogInstance)
            this.getExportFileNameAlertDialogInstance =
                new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity,
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                    {
                        @java.lang.Override
                        public void handleGetFileNameDone(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
                                .this.exportTempate(fileName);
                        }
                    });
        return this.getExportFileNameAlertDialogInstance;
    }

    private void preprocess(final java.lang.String initialFileName)
    { this.getExportFileNameAlertDialog().show(initialFileName); }
    // endregion

    public TemplateExportPreprocessor(
    @androidx.annotation.NonNull final android.app.Activity activity,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region Public Methods
    public void preprocess(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.templateId = templateId;
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            this.templatesTable().get(templateId);
        if (null != templateModel) this.preprocess(templateModel.getTitle());
    }

    public void preprocess(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
            { this.templateId = templateModel.getId(); this.preprocess(templateModel.getTitle()); }
    }
    // endregion
}