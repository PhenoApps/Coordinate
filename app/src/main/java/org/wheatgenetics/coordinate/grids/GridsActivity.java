package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.content.Context
 * android.content.Intent
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.widget.AdapterView<?>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.grids.AllGridsAdapter
 * org.wheatgenetics.coordinate.grids.GridsAdapter
 * org.wheatgenetics.coordinate.grids.ProjectGridsAdapter
 */
public class GridsActivity extends androidx.appcompat.app.AppCompatActivity
{
    private static final java.lang.String PROJECT_ID_KEY = "projectId";

    private org.wheatgenetics.coordinate.grids.GridsAdapter gridsAdapter = null;

    // region Private Methods
    private org.wheatgenetics.coordinate.grids.AllGridsAdapter makeAllGridsAdapter()
    { return new org.wheatgenetics.coordinate.grids.AllGridsAdapter(this); }

    @androidx.annotation.NonNull public static android.content.Intent makeIfNullOrReturn(
    @androidx.annotation.Nullable final android.content.Intent  intent ,
    @androidx.annotation.NonNull  final android.content.Context context)
    {
        return null == intent ? new android.content.Intent(context,
            org.wheatgenetics.coordinate.grids.GridsActivity.class) : intent;
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_grids);

        final android.widget.ListView gridsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.gridsListView);
        if (null != gridsListView)
        {
            {
                final android.content.Intent intent = this.getIntent();
                if (null == intent)
                    this.gridsAdapter = this.makeAllGridsAdapter();
                else
                {
                    final java.lang.String PROJECT_ID_KEY =
                        org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY;
                    if (intent.hasExtra(PROJECT_ID_KEY))
                    {
                          @androidx.annotation.IntRange(from = 1) final long projectId =
                              intent.getLongExtra(PROJECT_ID_KEY,-1);
                          this.gridsAdapter =
                              new org.wheatgenetics.coordinate.grids.ProjectGridsAdapter(
                                  this, projectId);
                    }
                    else this.gridsAdapter = this.makeAllGridsAdapter();
                }
            }
            gridsListView.setAdapter(this.gridsAdapter);
            gridsListView.setOnItemClickListener(
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
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_grids, menu);
        return true;
    }
    // endregion

    // region MenuItem Event Handler
    public void onNewGridMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem)
    {
        /*if (null == this.gridGreator)
            this.gridGreator = new org.wheatgenetics.coordinate.tc.GridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_TEMPLATE,this);
        this.gridGreator.create();*/
    }
    // endregion

    // region Public Methods
    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.Nullable final android.content.Intent  intent ,
    @androidx.annotation.NonNull  final android.content.Context context)
    {
        final android.content.Intent result =
            org.wheatgenetics.coordinate.grids.GridsActivity.makeIfNullOrReturn(intent, context);
        {
            final java.lang.String PROJECT_ID_KEY =
                org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY;
            if (result.hasExtra(PROJECT_ID_KEY)) result.removeExtra(PROJECT_ID_KEY);
        }
        return result;
    }

    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.Nullable           final android.content.Intent  intent   ,
    @androidx.annotation.NonNull            final android.content.Context context  ,
    @androidx.annotation.IntRange(from = 1) final long                    projectId)
    {
        return org.wheatgenetics.coordinate.grids.GridsActivity.makeIfNullOrReturn(intent, context)
            .putExtra(org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY, projectId);
    }
    // endregion
}