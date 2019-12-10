package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * android.app.Activity
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
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Types
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.templates.TemplatesAdapter
 */
public class TemplatesActivity extends androidx.appcompat.app.AppCompatActivity
implements org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    private static final int CONFIGURE_IMPORT_MENU_ITEM = 10;

    // region Fields
    private org.wheatgenetics.coordinate.templates.TemplatesAdapter templatesAdapter = null;

    private android.view.MenuItem importMenuItem = null;

    // region newMenuItem Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.tc.TemplateCreator      templateCreator        = null;// ll
    // endregion
    // endregion

    // region Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    private void configureImportMenuItem()
    {
        if (null != this.importMenuItem)
            try
            {
                final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                    org.wheatgenetics.coordinate.Utils.templatesDir(  // throws java.io.IOException,
                        this,                                 //  org.wheatgenetics.javalib-
                        org.wheatgenetics.coordinate.templates        //  .Dir.PermissionException
                            .TemplatesActivity.CONFIGURE_IMPORT_MENU_ITEM);
                this.importMenuItem.setEnabled(templatesDir.atLeastOneXmlFileExists());
            }
            catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
            {
                if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                {
                    this.showLongToast(e.getMessage());
                    this.importMenuItem.setEnabled(false);
                }
            }
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
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
                // noinspection SwitchStatementWithTooFewBranches
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.templates.TemplatesActivity
                        .CONFIGURE_IMPORT_MENU_ITEM: this.configureImportMenuItem(); break;
                }
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (android.app.Activity.RESULT_OK == resultCode && null != data)
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                    if (null != this.templateCreator)
                        this.templateCreator.setExcludedCells(data.getExtras());
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
            {
                not = "";
                if (null != this.templatesAdapter) this.templatesAdapter.notifyDataSetChanged();
            }
            else not = " not";
        }
        this.showLongToast(templateModel.getTitle() + not + " created");
    }
    // endregion
    // endregion

    public void onNewTemplateMenuItemClick(final android.view.MenuItem menuItem)
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,this);
        this.templateCreator.create();
    }
}