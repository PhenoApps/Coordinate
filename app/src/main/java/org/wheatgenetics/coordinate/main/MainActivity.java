package org.wheatgenetics.coordinate.main;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ArrayAdapter
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.AboutAlertDialog
 * org.wheatgenetics.coordinate.CollectorActivity
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.grids.GridsActivity
 *
 * org.wheatgenetics.coordinate.main.BaseMainActivity
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 *
 * org.wheatgenetics.coordinate.preference.PreferenceActivity
 *
 * org.wheatgenetics.coordinate.projects.ProjectsActivity
 *
 * org.wheatgenetics.coordinate.templates.TemplatesActivity
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 */
public class MainActivity extends org.wheatgenetics.coordinate.main.BaseMainActivity
implements org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    // region Fields
    private org.wheatgenetics.coordinate.AboutAlertDialog   aboutAlertDialogInstance = null;   // ll
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreatorInstance  = null;   // ll
    private org.wheatgenetics.coordinate.pc.ProjectCreator  projectCreatorInstance   = null;   // ll
    // endregion

    // region Private Methods
    private void reloadIfNecessary() { /* TODO */ }

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

    private void startPreferenceActivity()
    {
        this.startActivityForResult(
            org.wheatgenetics.coordinate.preference.PreferenceActivity.intent(this),
            org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED                          );
    }

    private void startCollectorActivity(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.startActivity(
            org.wheatgenetics.coordinate.CollectorActivity.INTENT(this, gridId));
    }
    // endregion

    // region AboutAlertDialog Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.AboutAlertDialog aboutAlertDialog()
    {
        if (null == this.aboutAlertDialogInstance) this.aboutAlertDialogInstance =
            new org.wheatgenetics.coordinate.AboutAlertDialog(
                this, this.versionName(), new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    { org.wheatgenetics.coordinate.main.MainActivity.this.showChangeLog(); }
                });
        return this.aboutAlertDialogInstance;
    }

    private void showAboutAlertDialog() { this.aboutAlertDialog().show(); }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatelessGridCreator statelessGridCreator()
    {
        if (null == this.gridCreatorInstance) this.gridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatelessGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,
                new org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler()
                {
                    @java.lang.Override public void handleGridCreated(
                    @androidx.annotation.IntRange(from = 1) final long gridId)
                    {
                        org.wheatgenetics.coordinate.main.MainActivity.this.startCollectorActivity(
                            gridId);
                    }
                });
        return (org.wheatgenetics.coordinate.gc.StatelessGridCreator) this.gridCreatorInstance;
    }

    // region Create Template Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this, text); }

    @androidx.annotation.NonNull
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

        {
            final android.widget.ListView mainListView = this.findViewById(
                org.wheatgenetics.coordinate.R.id.mainListView);    // From layout/content_main.xml.
            if (null != mainListView)
            {
                // noinspection Convert2Diamond
                mainListView.setAdapter(new android.widget.ArrayAdapter<java.lang.String>(
                    this, org.wheatgenetics.coordinate.R.layout.main_list_item,
                    new java.lang.String[]{"Grids", "Templates",
                        "Projects", "Settings", "About"}));
                mainListView.setOnItemClickListener(
                    new android.widget.AdapterView.OnItemClickListener()
                    {
                        @java.lang.Override
                        public void onItemClick(final android.widget.AdapterView<?> parent,
                        final android.view.View view, final int position, final long id)
                        {
                            switch (position)
                            {
                                case 0: org.wheatgenetics.coordinate.main.MainActivity
                                    .this.startGridsActivity(); break;

                                case 1: org.wheatgenetics.coordinate.main.MainActivity
                                    .this.startTemplatesActivity(); break;

                                case 2: org.wheatgenetics.coordinate.main.MainActivity
                                    .this.startProjectsActivity(); break;

                                case 3: org.wheatgenetics.coordinate.main.MainActivity
                                    .this.startPreferenceActivity(); break;

                                case 4: org.wheatgenetics.coordinate.main.MainActivity
                                    .this.showAboutAlertDialog(); break;
                            }
                        }
                    });
            }
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
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE:
                    if (null != this.templateCreatorInstance)
                        this.templateCreatorInstance.setExcludedCells(data.getExtras());
                    break;

                case org.wheatgenetics.coordinate.Types.UNIQUENESS_CLICKED:             // TODO: DRY
                    {
                        final boolean uniquenessPreferenceWasClicked;
                        {
                            final android.os.Bundle bundle = data.getExtras();
                            // noinspection SimplifiableConditionalExpression
                            uniquenessPreferenceWasClicked = null == bundle ?
                                false : bundle.getBoolean(
                                    org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,
                                    false);
                        }
                        if (uniquenessPreferenceWasClicked) this.reloadIfNecessary();
                    }
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
    public void onGridMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.statelessGridCreator().create(); }

    public void onTemplateMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.templateCreator().create(); }

    public void onProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.projectCreator().create(); }
    // endregion
}