package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ArrayAdapter
 * android.widget.ListView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.grids.GridsActivity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 *
 * org.wheatgenetics.coordinate.projects.ProjectsActivity
 *
 * org.wheatgenetics.coordinate.templates.TemplatesActivity
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 */
public class MainActivity extends androidx.appcompat.app.AppCompatActivity
implements org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    // region Fields
    // region Create Template Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance  = null;//ll
    private org.wheatgenetics.coordinate.tc.TemplateCreator      templateCreatorInstance = null;//ll
    // endregion

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreatorInstance = null;   // lazy
    // endregion                                                                            //  load

    // region Private Methods
    // region startActivity() Private Methods
    private void startGridsActivity()
    { this.startActivity(org.wheatgenetics.coordinate.grids.GridsActivity.intent(this)); }

    private void startTemplatesActivity()
    {
        this.startActivity(
            org.wheatgenetics.coordinate.templates.TemplatesActivity.intent(this));
    }

    private void startProjectsActivity()
    {
        this.startActivity(
            org.wheatgenetics.coordinate.projects.ProjectsActivity.intent(this));
    }
    // endregion

    // region Create Template Private Methods
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
    // endregion

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator()
    {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
            new org.wheatgenetics.coordinate.pc.ProjectCreator(this);
        return this.projectCreatorInstance;
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        final android.widget.ListView mainListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.mainListView);        // From layout/content_main.xml.
        if (null != mainListView)
        {
            // noinspection Convert2Diamond
            mainListView.setAdapter(new android.widget.ArrayAdapter<java.lang.String>(
                this, org.wheatgenetics.coordinate.R.layout.main_list_item,
                new java.lang.String[]{"Grids", "Templates", "Projects", "Settings", "About"}));
            mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
                {
                    @java.lang.Override
                    public void onItemClick(final android.widget.AdapterView<?> parent,
                    final android.view.View view, final int position, final long id)
                    {
                        switch (position)
                        {
                            case 0: org.wheatgenetics.coordinate.MainActivity
                                .this.startGridsActivity(); break;

                            case 1: org.wheatgenetics.coordinate.MainActivity
                                .this.startTemplatesActivity(); break;

                            case 2: org.wheatgenetics.coordinate.MainActivity
                                .this.startProjectsActivity(); break;

                            case 3: /* TODO */ break;
                            case 4: /* TODO */ break;
                        }
                    }
                });
        }
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_main, menu);
        return true;
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
        final java.lang.String not = this.templatesTable().insert(templateModel) > 0 ? "" : " not";
        this.showLongToast(templateModel.getTitle() + not + " created");
    }
    // endregion
    // endregion

    // region MenuItem Event Handlers
    public void onTemplateMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.templateCreator().create(); }

    public void onProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.projectCreator().create(); }
    // endregion
}