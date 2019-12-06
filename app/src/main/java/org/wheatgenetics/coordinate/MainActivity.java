package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.view.Menu
 * android.widget.ArrayAdapter
 * android.widget.ListView
 *
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 */
public class MainActivity extends androidx.appcompat.app.AppCompatActivity
{
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        final android.widget.ListView mainListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.mainListView);        // From layout/content_main.xml.
        if (null != mainListView)
            // noinspection Convert2Diamond
            mainListView.setAdapter(new android.widget.ArrayAdapter<java.lang.String>(this,
                org.wheatgenetics.coordinate.R.layout.main_list_item, new java.lang.String[]{
                    "Grids", "Templates", "Projects", "Settings", "About"}));
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_main, menu);
        return true;
    }
}