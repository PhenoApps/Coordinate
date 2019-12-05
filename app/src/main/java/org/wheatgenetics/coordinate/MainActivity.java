package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.widget.ArrayAdapter
 * android.widget.ListView
 *
 * androidx.appcompat.app.AppCompatActivity
 * androidx.appcompat.widget.Toolbar
 *
 * org.wheatgenetics.coordinate.R
 */
public class MainActivity extends androidx.appcompat.app.AppCompatActivity
{
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        {
            final androidx.appcompat.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.coordinate.R.id.toolbar);         // From layout/app_bar_main.xml.
            this.setSupportActionBar(toolbar);
        }

        final android.widget.ListView mainListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.mainListView);        // From layout/content_main.xml.
        // noinspection Convert2Diamond
        mainListView.setAdapter(new android.widget.ArrayAdapter<java.lang.String>(this,
            org.wheatgenetics.coordinate.R.layout.main_list_item, new java.lang.String[]{
                "Grids", "Templates", "Projects", "Settings", "About"}));
    }
}