package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.R
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v7.app.ActionBar
 * android.support.v7.app.AppCompatActivity
 * android.view.MenuItem
 *
 * org.wheatgenetics.coordinate.nisl.PreferenceFragment
 */
public class PreferenceActivity extends android.support.v7.app.AppCompatActivity
{
    @java.lang.Override protected void onCreate(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        {
            final android.support.v7.app.ActionBar supportActionBar =
                this.getSupportActionBar();
            if (null != supportActionBar)
            {
                supportActionBar.setTitle                 (/* title        => */ null);
                supportActionBar.setDisplayHomeAsUpEnabled(/* showHomeAsUp => */ true);
            }
        }

        // Display PreferenceFragment as the main content.
        this.getFragmentManager().beginTransaction().replace(android.R.id.content,
            new org.wheatgenetics.coordinate.nisl.PreferenceFragment()).commit();
    }

    @java.lang.Override public boolean onOptionsItemSelected(final android.view.MenuItem item)
    { this.finish(); return super.onOptionsItemSelected(item); }
}