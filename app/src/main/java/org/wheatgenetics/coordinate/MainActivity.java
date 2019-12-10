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
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
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
    private android.content.Intent templatesIntentInstance = null,                         // lazy
        projectsIntentInstance = null;                                                     //  loads

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.tc.TemplateCreator      templateCreator        = null;// ll

    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator = null;       // lazy load
    // endregion

    // region Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    // region TemplatesActivity Private Methods
    private android.content.Intent templatesIntent()
    {
        if (null == this.templatesIntentInstance) this.templatesIntentInstance =
            new android.content.Intent(this,
                org.wheatgenetics.coordinate.templates.TemplatesActivity.class);
        return this.templatesIntentInstance;
    }

    private void startTemplatesActivity() { this.startActivity(this.templatesIntent()); }
    // endregion

    // region ProjectsActivity Private Methods
    private android.content.Intent projectsIntent()
    {
        if (null == this.projectsIntentInstance) this.projectsIntentInstance =
            new android.content.Intent(this,
                org.wheatgenetics.coordinate.projects.ProjectsActivity.class);
        return this.projectsIntentInstance;
    }

    private void startProjectsActivity() { this.startActivity(this.projectsIntent()); }
    // endregion

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
                            case 1: org.wheatgenetics.coordinate.MainActivity
                                .this.startTemplatesActivity(); break;

                            case 2: org.wheatgenetics.coordinate.MainActivity
                                .this.startProjectsActivity(); break;
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
                    if (null != this.templateCreator)
                        this.templateCreator.setExcludedCells(data.getExtras());
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
    final android.view.MenuItem menuItem)
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,this);
        this.templateCreator.create();
    }

    public void onProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem)
    {
        if (null == this.projectCreator) this.projectCreator =
            new org.wheatgenetics.coordinate.pc.ProjectCreator(this);
        this.projectCreator.create();
    }
    // endregion
}