package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Intent
 * android.os.Bundle
 * android.view.Menu
 * android.view.View
 * android.widget.AdapterView<>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ArrayAdapter
 * android.widget.ListView
 *
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.templates.TemplatesActivity
 *
 * org.wheatgenetics.coordinate.R
 */
public class MainActivity extends androidx.appcompat.app.AppCompatActivity
{
    private android.content.Intent templatesIntentInstance = null;                      // lazy load

    // region Private Methods
    private android.content.Intent templatesIntent()
    {
        if (null == this.templatesIntentInstance) this.templatesIntentInstance =
            new android.content.Intent(this,
                org.wheatgenetics.coordinate.templates.TemplatesActivity.class);
        return this.templatesIntentInstance;
    }

    private void startTemplatesActivity() { this.startActivity(this.templatesIntent()); }
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
    // endregion
}