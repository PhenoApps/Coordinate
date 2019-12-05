package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 *
 * androidx.appcompat.app.ActionBar
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

        // region Configure action bar.
        {
            final androidx.appcompat.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.coordinate.R.id.toolbar);         // From layout/app_bar_main.xml.
            this.setSupportActionBar(toolbar);
        }
        {
            final androidx.appcompat.app.ActionBar supportActionBar = this.getSupportActionBar();
            if (null != supportActionBar) supportActionBar.setTitle(null);
        }
        // endregion
    }
}