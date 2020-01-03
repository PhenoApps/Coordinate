package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.TemplateDeleter
 * org.wheatgenetics.coordinate.TemplateDeleter.TemplateHandler
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler
 */
class TemplateClickAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract void createGrid(@androidx.annotation.IntRange(from = 1) long templateId);
        public abstract void showGrids (@androidx.annotation.IntRange(from = 1) long templateId);

        public abstract void exportTemplate(
        @androidx.annotation.IntRange(from = 1) long templateId, java.lang.String fileName);

        public abstract void respondToDeletedTemplate();
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog.Handler handler;

    @androidx.annotation.IntRange(from = 1) private long    templateId                     ;
                                            private boolean templateHasGrids, isUserDefined;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    // endregion

    private android.content.DialogInterface.OnClickListener onClickListenerInstance = null;    // ll
    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
        templateExportPreprocessorInstance = null;                                      // lazy load
    private org.wheatgenetics.coordinate.TemplateDeleter templateDeleterInstance = null;       // ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }
    // endregion

    private void createGrid() { this.handler.createGrid(this.templateId); }
    private void showGrids () { this.handler.showGrids (this.templateId); }

    // region exportTemplate() Private Methods
    private void exportTemplate(
    @androidx.annotation.IntRange(from = 1) final long             templateId,
                                            final java.lang.String fileName  )
    { this.handler.exportTemplate(templateId, fileName); }

    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor templateExportPreprocessor()
    {
        if (null == this.templateExportPreprocessorInstance)
            this.templateExportPreprocessorInstance =
                new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor(this.activity(),
                    new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler()
                    {
                        @java.lang.Override public void exportTemplate(
                        @androidx.annotation.IntRange(from = 1) final long             templateId,
                                                                final java.lang.String fileName  )
                        {
                            org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog
                                .this.exportTemplate(templateId, fileName);
                        }
                    });
        return this.templateExportPreprocessorInstance;
    }

    private void exportTemplate() { this.templateExportPreprocessor().preprocess(this.templateId); }
    // endregion

    // region deleteTemplate() Private Methods
    private void respondToDeletedTemplate() { this.handler.respondToDeletedTemplate(); }

    private org.wheatgenetics.coordinate.TemplateDeleter templateDeleter()
    {
        if (null == this.templateDeleterInstance) this.templateDeleterInstance =
            new org.wheatgenetics.coordinate.TemplateDeleter(this.activity(),
                new org.wheatgenetics.coordinate.TemplateDeleter.TemplateHandler()
                {
                    @java.lang.Override public void respondToDeletedTemplate(
                    @androidx.annotation.IntRange(from = 1) final long templateId)
                    {
                        org.wheatgenetics.coordinate.templates
                            .TemplateClickAlertDialog.this.respondToDeletedTemplate();
                    }
                });
        return this.templateDeleterInstance;
    }

    private void deleteTemplate() { this.templateDeleter().delete(this.templateId); }
    // endregion

    // region onClickListener() Private Methods
    /**
     * templateHasGrids isUserDefined                  action
     * ================ ============== =======================================
     *         0               0       case 1 shouldn't happen: take no action
     *         0               1       export template
     *         1        doesn't matter show grids
     */
    private void handleCase1()
    {
        if (this.templateHasGrids)
            this.showGrids();
        else
            if (this.isUserDefined) this.exportTemplate();
    }

    /**
     * templateHasGrids isUserDefined                  action
     * ================ ============== =======================================
     *         0        doesn't matter delete template
     *         1               0       case 2 shouldn't happen: take no action
     *         1               1       export template
     */
    private void handleCase2()
    {
        if (!this.templateHasGrids)
            this.deleteTemplate();
        else
            if (this.isUserDefined) this.exportTemplate();
    }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    switch (which)
                    {
                        case 0: org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog
                            .this.createGrid(); break;

                        case 1: org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog
                            .this.handleCase1(); break;

                        case 2: org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog
                            .this.handleCase2(); break;

                        case 3: org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog
                            .this.deleteTemplate(); break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion
    // endregion

    TemplateClickAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.templates.TemplateClickAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure() {}

    void show(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.templateId = templateId;
        {
            @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.StringRes
            final int items[];
            {
                @java.lang.SuppressWarnings({"Convert2Diamond"})
                final java.util.ArrayList<java.lang.Integer> arrayList =
                    new java.util.ArrayList<java.lang.Integer>();

                arrayList.add(
                    org.wheatgenetics.coordinate.R.string.TemplateClickAlertDialogCreateGrid);

                this.templateHasGrids = this.gridsTable().existsInTemplate(this.templateId);
                if (this.templateHasGrids) arrayList.add(
                    org.wheatgenetics.coordinate.R.string.TemplateClickAlertDialogShowGrids);

                {
                    final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                        this.templatesTable().get(this.templateId);
                    if (null == templateModel)
                        return;
                    else
                        this.isUserDefined = !templateModel.isDefaultTemplate();
                }
                if (this.isUserDefined)
                {
                    arrayList.add(org.wheatgenetics.coordinate.R
                        .string.TemplateClickAlertDialogExportTemplate);
                    arrayList.add(org.wheatgenetics.coordinate.R
                        .string.TemplateClickAlertDialogDeleteTemplate);
                }

                items = new int[arrayList.size()];
                @androidx.annotation.IntRange(from = 0) int i = 0;
                for (final java.lang.Integer integer: arrayList) items[i++] = integer;
            }
            this.setItems(items, this.onClickListener());
        }
        this.createShow();
    }
}