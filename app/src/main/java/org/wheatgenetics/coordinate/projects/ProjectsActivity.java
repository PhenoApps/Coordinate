package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.projects.ProjectsAdapter
 */
public class ProjectsActivity extends androidx.appcompat.app.AppCompatActivity
{
    // region Fields
    private org.wheatgenetics.coordinate.projects.ProjectsAdapter projectsAdapter = null;
    private org.wheatgenetics.coordinate.pc.ProjectCreator        projectCreator  = null;      // ll
    // endregion

    private void handleCreateProjectDone()
    { if (null != this.projectsAdapter) this.projectsAdapter.notifyDataSetChanged(); }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_projects);

        final android.widget.ListView projectsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.projectsListView);
        if (null != projectsListView)
        {
            projectsListView.setAdapter(this.projectsAdapter =
                new org.wheatgenetics.coordinate.projects.ProjectsAdapter(this));
            projectsListView.setOnItemClickListener(
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
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_projects, menu);
        return true;
    }
    // endregion

    public void onNewProjectMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem)
    {
        if (null == this.projectCreator)
            this.projectCreator = new org.wheatgenetics.coordinate.pc.ProjectCreator(this,
                new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                {
                    @java.lang.Override public void handleCreateProjectDone(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.projects
                            .ProjectsActivity.this.handleCreateProjectDone();
                    }
                });
        this.projectCreator.createAndReturn();
    }
}