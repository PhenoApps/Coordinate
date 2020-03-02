package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.content.pm.PackageManager
 * android.Manifest.permission
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.BackActivity
 * org.wheatgenetics.coordinate.CollectorActivity
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.deleter.TemplateDeleter
 * org.wheatgenetics.coordinate.deleter.TemplateDeleter.TemplateHandler
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.grids.GridsActivity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler
 * org.wheatgenetics.coordinate.te.TemplateExporter
 *
 * org.wheatgenetics.coordinate.ti.MenuItemEnabler
 * org.wheatgenetics.coordinate.ti.TemplateImporter
 * org.wheatgenetics.coordinate.ti.TemplateImporter.Adapter
 * org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
 * org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler
 *
 * org.wheatgenetics.coordinate.templates.TemplatesAdapter
 */
public class TemplatesActivity extends org.wheatgenetics.coordinate.BackActivity
implements org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    private static final int EXPORT_TEMPLATE = 10, CONFIGURE_IMPORT_MENU_ITEM = 20,
        PREPROCESS_TEMPLATE_IMPORT = 30, IMPORT_TEMPLATE = 35;

    // region Fields
    private org.wheatgenetics.coordinate.templates.TemplatesAdapter templatesAdapter = null;

    private org.wheatgenetics.coordinate.gc.StatelessGridCreator
        statelessGridCreatorInstance = null;                                            // lazy load

    private org.wheatgenetics.coordinate.deleter.TemplateDeleter templateDeleterInstance = null;//ll

    // region exportTemplate() Fields
    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
        templateExportPreprocessorInstance = null;                                      // lazy load
    private org.wheatgenetics.coordinate.te.TemplateExporter templateExporterInstance = null; // ll
    // endregion

    // region importMenuItem Fields
    private android.view.MenuItem                           importMenuItem          = null;
    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnablerInstance = null;    // ll
    // endregion

    // region createTemplate() Fields
    private org.wheatgenetics.coordinate.tc.TemplateCreator      templateCreatorInstance = null;//ll
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance  = null;//ll
    // endregion

    // region Import Fields
    private org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
        templateImportPreprocessorInstance = null;                                      // lazy load
    private org.wheatgenetics.coordinate.ti.TemplateImporter templateImporterInstance = null;  // ll
    // endregion

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    // region Private Methods
    // region createGrid() Private Methods
    private void startCollectorActivity(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.startActivity(
            org.wheatgenetics.coordinate.CollectorActivity.intent(this, gridId));
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatelessGridCreator statelessGridCreator()
    {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatelessGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,
                new org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler()
                {
                    @java.lang.Override public void handleGridCreated(
                    @androidx.annotation.IntRange(from = 1) final long gridId)
                    {
                        org.wheatgenetics.coordinate.templates.TemplatesActivity
                            .this.startCollectorActivity(gridId);
                    }
                });
        return this.statelessGridCreatorInstance;
    }

    private void createGrid(@androidx.annotation.IntRange(from = 1) final long templateId)
    { statelessGridCreator().createFromTemplate(templateId); }
    // endregion

    private void notifyDataSetChanged()
    { if (null != this.templatesAdapter) this.templatesAdapter.notifyDataSetChanged(); }

    // region deleteTemplate() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.TemplateDeleter templateDeleter()
    {
        if (null == this.templateDeleterInstance) this.templateDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.TemplateDeleter(this,
                new org.wheatgenetics.coordinate.deleter.TemplateDeleter.TemplateHandler()
                {
                    @java.lang.Override public void respondToDeletedTemplate(
                    @androidx.annotation.IntRange(from = 1) final long templateId)
                    {
                        org.wheatgenetics.coordinate.templates
                            .TemplatesActivity.this.notifyDataSetChanged();
                    }
                });
        return this.templateDeleterInstance;
    }

    private void deleteTemplate(@androidx.annotation.IntRange(from = 1) final long templateId)
    { this.templateDeleter().delete(templateId); }
    // endregion

    // region exportTemplate() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.te.TemplateExporter templateExporter()
    {
        if (null == this.templateExporterInstance) this.templateExporterInstance =
            new org.wheatgenetics.coordinate.te.TemplateExporter(this,
                org.wheatgenetics.coordinate.templates.TemplatesActivity.EXPORT_TEMPLATE);
        return this.templateExporterInstance;
    }

    private void exportTemplate(
    @androidx.annotation.IntRange(from = 1) final long             templateId,
                                            final java.lang.String fileName  )
    { this.templateExporter().export(templateId, fileName); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor templateExportPreprocessor()
    {
        if (null == this.templateExportPreprocessorInstance)
            this.templateExportPreprocessorInstance =
                new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor(this,
                    new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler()
                    {
                        @java.lang.Override public void exportTemplate(
                        @androidx.annotation.IntRange(from = 1) final long             templateId,
                                                                final java.lang.String fileName  )
                        {
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.exportTemplate(templateId, fileName);
                        }
                    });
        return this.templateExportPreprocessorInstance;
    }

    private void exportTemplate(@androidx.annotation.IntRange(from = 1) final long templateId)
    { this.templateExportPreprocessor().preprocess(templateId); }
    // endregion

    private void startGridsActivity(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.startActivity(org.wheatgenetics.coordinate.grids.GridsActivity.templateIdIntent(
            this, templateId));
    }

    // region importMenuItem Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.MenuItemEnabler menuItemEnabler()
    {
        if (null == this.menuItemEnablerInstance) this.menuItemEnablerInstance =
            new org.wheatgenetics.coordinate.ti.MenuItemEnabler(this, org.wheatgenetics
                .coordinate.templates.TemplatesActivity.CONFIGURE_IMPORT_MENU_ITEM);
        return this.menuItemEnablerInstance;
    }

    private void configureImportMenuItem()
    {
        if (null != this.importMenuItem)
            this.importMenuItem.setEnabled(this.menuItemEnabler().shouldBeEnabled());
    }
    // endregion

    // region createTemplate() Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator()
    {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,this);
        return this.templateCreatorInstance;
    }

    private void createTemplate() { this.templateCreator().create(); }
    // endregion

    // region Import Private Methods
    // region importTemplate() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.TemplateImporter templateImporter()
    {
        if (null == this.templateImporterInstance) this.templateImporterInstance =
            new org.wheatgenetics.coordinate.ti.TemplateImporter(this,
                org.wheatgenetics.coordinate.templates.TemplatesActivity.IMPORT_TEMPLATE,
                new org.wheatgenetics.coordinate.ti.TemplateImporter.Adapter()
                {
                    @java.lang.Override public void notifyDataSetChanged()
                    {
                        org.wheatgenetics.coordinate.templates
                            .TemplatesActivity.this.notifyDataSetChanged();
                    }
                });
        return this.templateImporterInstance;
    }

    private void importTemplate(final java.lang.String fileName)
    { this.templateImporter().importTemplate(fileName); }
    // endregion

    // region preprocessTemplateImport() Import Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor templateImportPreprocessor()
    {
        if (null == this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance =
                new org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor(
                    this, org.wheatgenetics.coordinate.templates
                        .TemplatesActivity.PREPROCESS_TEMPLATE_IMPORT,
                    new org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler()
                    {
                        @java.lang.Override
                        public void importTemplate(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.importTemplate(fileName);
                        }
                    });
        return this.templateImportPreprocessorInstance;
    }

    private void preprocessTemplateImport() { this.templateImportPreprocessor().preprocess(); }
    // endregion
    // endregion
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_templates);

        final android.widget.ListView templatesListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.templatesListView);
        if (null != templatesListView) templatesListView.setAdapter(this.templatesAdapter =
            new org.wheatgenetics.coordinate.templates.TemplatesAdapter(this,
                /* onCreateGridButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view)
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.createGrid((java.lang.Long) view.getTag());
                    }
                }, /* onDeleteButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view)
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.deleteTemplate((java.lang.Long) view.getTag());
                    }
                }, /* onExportButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view)
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.exportTemplate((java.lang.Long) view.getTag());
                    }
                }, /* onShowGridsButtonClickListener => */ new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view)
                            org.wheatgenetics.coordinate.templates.TemplatesActivity
                                .this.startGridsActivity((java.lang.Long) view.getTag());
                    }
                }));
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_templates, menu);

        if (null != menu)
        {
            this.importMenuItem = menu.findItem(
                org.wheatgenetics.coordinate.R.id.importTemplateMenuItem);
            this.configureImportMenuItem();
        }

        return true;
    }

    @java.lang.Override public void onRequestPermissionsResult(final int requestCode,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final java.lang.String permissions[],
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final int grantResults[])
    {
        boolean permissionFound = false;
        for (final java.lang.String permission: permissions)
            if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
                { permissionFound = true; break; }

        if (permissionFound) for (final int grantResult: grantResults)
            if (android.content.pm.PackageManager.PERMISSION_GRANTED == grantResult)
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.templates.TemplatesActivity.EXPORT_TEMPLATE:
                        if (null != this.templateExporterInstance)
                            this.templateExporterInstance.export();
                        break;

                    case org.wheatgenetics.coordinate.templates.TemplatesActivity
                    .CONFIGURE_IMPORT_MENU_ITEM: this.configureImportMenuItem(); break;

                    case org.wheatgenetics.coordinate.templates.TemplatesActivity
                    .PREPROCESS_TEMPLATE_IMPORT:
                        if (null != this.templateImportPreprocessorInstance)
                            this.templateImportPreprocessorInstance.preprocess();
                        break;

                    case org.wheatgenetics.coordinate.templates.TemplatesActivity.IMPORT_TEMPLATE:
                        if (null != this.templateImporterInstance)
                            this.templateImporterInstance.importTemplate();
                        break;
                }
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (android.app.Activity.RESULT_OK == resultCode && null != data)
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                    this.statelessGridCreator().continueExcluding(data.getExtras()); break;

                case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                    this.templateCreator().continueExcluding(data.getExtras()); break;
            }
    }

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override public void handleTemplateCreated(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final java.lang.String not;
        {
            final boolean templateCreated = this.templatesTable().insert(templateModel) > 0;
            if (templateCreated)
                { not = ""; this.notifyDataSetChanged(); }
            else
                not = " not";
        }
        this.showLongToast(templateModel.getTitle() + not + " created");
    }
    // endregion
    // endregion

    // region MenuItem Event Handlers
    public void onNewTemplateMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.createTemplate(); }

    public void onImportTemplateMenuItem(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.preprocessTemplateImport(); }
    // endregion

    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        return null == org.wheatgenetics.coordinate.templates.TemplatesActivity.INTENT_INSTANCE ?
            org.wheatgenetics.coordinate.templates.TemplatesActivity.INTENT_INSTANCE =
                new android.content.Intent(context,
                    org.wheatgenetics.coordinate.templates.TemplatesActivity.class) :
            org.wheatgenetics.coordinate.templates.TemplatesActivity.INTENT_INSTANCE;
    }
}