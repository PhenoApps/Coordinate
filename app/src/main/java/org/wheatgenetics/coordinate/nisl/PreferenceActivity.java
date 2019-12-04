package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.R
 * android.os.Bundle
 * android.view.MenuItem
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.ActionBar
 * androidx.appcompat.app.AppCompatActivity
 * androidx.preference.Preference
 * androidx.preference.Preference.OnPreferenceClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.nisl.PreferenceFragment
 */
public class PreferenceActivity extends androidx.appcompat.app.AppCompatActivity
implements androidx.preference.Preference.OnPreferenceClickListener
{
    private boolean uniquenessPreferenceWasClicked;

    private void setResult()
    {
        final android.content.Intent intent = new android.content.Intent();
        {
            final android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBoolean(
                org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,
                this.uniquenessPreferenceWasClicked                     );
            intent.putExtras(bundle);
        }
        this.setResult(android.app.Activity.RESULT_OK, intent);
    }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState)
            this.uniquenessPreferenceWasClicked = false;
        else
            this.uniquenessPreferenceWasClicked = savedInstanceState.getBoolean(
                org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,false);

        {
            final androidx.appcompat.app.ActionBar supportActionBar = this.getSupportActionBar();
            if (null != supportActionBar)
            {
                supportActionBar.setTitle                 (/* title        => */ null);
                supportActionBar.setDisplayHomeAsUpEnabled(/* showHomeAsUp => */ true);
            }
        }

        // Display PreferenceFragment as the main content.
        this.getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
            new org.wheatgenetics.coordinate.nisl.PreferenceFragment()).commit();
    }

    @java.lang.Override protected void onStart()
    {
        super.onStart(); org.wheatgenetics.androidlibrary.Utils.showLongToast(
            this,"Press \"Back\" when done.");
    }

    @java.lang.Override protected void onSaveInstanceState(
    @androidx.annotation.NonNull final android.os.Bundle outState)
    {
        outState.putBoolean(
            org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,
            this.uniquenessPreferenceWasClicked                     );
        super.onSaveInstanceState(outState);
    }

    @java.lang.Override public boolean onOptionsItemSelected(
    @androidx.annotation.NonNull final android.view.MenuItem item)
    { this.setResult(); this.finish(); return super.onOptionsItemSelected(item); }

    @java.lang.Override public void onBackPressed() { this.setResult(); super.onBackPressed(); }

    // region androidx.preference.Preference.OnPreferenceClickListener Overridden Method
    @java.lang.Override
    public boolean onPreferenceClick(final androidx.preference.Preference preference)
    { this.uniquenessPreferenceWasClicked = true; return true; }
    // endregion
    // endregion
}