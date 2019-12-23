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
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.grids.AllGridsAdapter
 */
public class GridsActivity extends androidx.appcompat.app.AppCompatActivity
{
    private org.wheatgenetics.coordinate.grids.AllGridsAdapter allGridsAdapter = null;

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
            gridsListView.setAdapter(this.allGridsAdapter =
                new org.wheatgenetics.coordinate.grids.AllGridsAdapter(this));
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

    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.Nullable final android.content.Intent intent  ,
    @androidx.annotation.NonNull  final android.content.Context context)
    {
        return null == intent ? new android.content.Intent(context,
            org.wheatgenetics.coordinate.grids.GridsActivity.class) : intent;
    }
}