package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 *
 * androidx.annotation.Nullable
 * androidx.appcompat.app.ActionBar
 * androidx.appcompat.app.AppCompatActivity
 */
public abstract class BackActivity extends androidx.appcompat.app.AppCompatActivity
{
    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        @androidx.annotation.Nullable
        final androidx.appcompat.app.ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar)
            supportActionBar.setDisplayHomeAsUpEnabled(/* showHomeAsUp => */ true);
    }

    @java.lang.Override public boolean onSupportNavigateUp()
    { this.onBackPressed(); return super.onSupportNavigateUp(); }
    // endregion
}