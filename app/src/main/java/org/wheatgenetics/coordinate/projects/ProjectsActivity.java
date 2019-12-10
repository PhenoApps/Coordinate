package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.os.Bundle
 * _android.view.Menu
 * android.view.View
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.projects.ProjectsAdapter
 */
public class ProjectsActivity extends androidx.appcompat.app.AppCompatActivity
{
    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_projects);

        final android.widget.ListView projectsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.projectsListView);
        if (null != projectsListView)
        {
            projectsListView.setAdapter(
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
}