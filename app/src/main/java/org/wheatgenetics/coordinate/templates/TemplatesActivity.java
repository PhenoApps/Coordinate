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
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 * org.wheatgenetics.coordinate.Utils.Handler
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.ti.MenuItemEnabler
 * org.wheatgenetics.coordinate.ti.TemplateImporter
 * org.wheatgenetics.coordinate.ti.TemplateImporter.Adapter
 * org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
 *
 * org.wheatgenetics.coordinate.templates.TemplatesAdapter
 */
public class TemplatesActivity extends androidx.appcompat.app.AppCompatActivity
implements org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    private static final int CONFIGURE_IMPORT_MENU_ITEM = 10,
        PREPROCESS_TEMPLATE_IMPORT = 20, IMPORT_TEMPLATE = 30;

    // region Fields
    private org.wheatgenetics.coordinate.templates.TemplatesAdapter templatesAdapter = null;

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
    // region importMenuItem Private Methods
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

    private void notifyDataSetChanged()
    { if (null != this.templatesAdapter) this.templatesAdapter.notifyDataSetChanged(); }

    // region createTemplate() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

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
                    new org.wheatgenetics.coordinate.Utils.Handler()
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
        if (null != templatesListView)
        {
            templatesListView.setAdapter(this.templatesAdapter =
                new org.wheatgenetics.coordinate.templates.TemplatesAdapter(this));
            templatesListView.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener()
                {
                    @java.lang.Override public void onItemClick(
                    final android.widget.AdapterView<?> parent, final android.view.View view,
                    final int position, final long id)
                    {
                        // TODO
                    }
                });
        }
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
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                    if (null != this.templateCreatorInstance)
                        this.templateCreatorInstance.setExcludedCells(data.getExtras());
                    break;
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