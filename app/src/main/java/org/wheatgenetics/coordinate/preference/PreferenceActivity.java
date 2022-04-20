package org.wheatgenetics.coordinate.preference;

import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.AboutActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.R
 * android.os.Bundle
 * android.view.MenuItem
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.preference.Preference
 * androidx.preference.Preference.OnPreferenceClickListener
 *
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.preference.PreferenceFragment
 */
public class PreferenceActivity extends org.wheatgenetics.coordinate.BackActivity
        implements androidx.preference.Preference.OnPreferenceClickListener
{
    // region Fields
    private boolean uniquenessPreferenceWasClicked;

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

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

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_preferences_bnv);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_settings);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_preferences_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;
            final int about = R.id.action_nav_about;

            switch(item.getItemId()) {
                case grids:
                    Intent gridsIntent = GridsActivity.intent(this);
                    gridsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gridsIntent);
                    break;
                case templates:
                    Intent templateIntent = TemplatesActivity.intent(this);
                    templateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(templateIntent);
                    break;
                case about:
                    Intent aboutIntent = new Intent(this, AboutActivity.class);
                    aboutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(aboutIntent);
                    break;
                case projects:
                    Intent projectIntent = ProjectsActivity.intent(this);
                    projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ProjectsActivity.intent(this));
                    break;
                default:
                    break;

            }

            return true;
        }));
    }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
            @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        setupBottomNavigationBar();

        // noinspection SimplifiableConditionalExpression
        this.uniquenessPreferenceWasClicked =
                null == savedInstanceState ? false : savedInstanceState.getBoolean(
                        org.wheatgenetics.coordinate.Types.UNIQUENESS_BUNDLE_KEY,false);

        // Display PreferenceFragment as the main content.
//        this.getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
//                new org.wheatgenetics.coordinate.preference.PreferenceFragment()).commit();
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

    @androidx.annotation.NonNull public static android.content.Intent intent(
            @androidx.annotation.NonNull final android.content.Context context)
    {
        return null == org.wheatgenetics.coordinate.preference.PreferenceActivity.INTENT_INSTANCE ?
                org.wheatgenetics.coordinate.preference.PreferenceActivity.INTENT_INSTANCE =
                        new android.content.Intent(context,
                                org.wheatgenetics.coordinate.preference.PreferenceActivity.class) :
                org.wheatgenetics.coordinate.preference.PreferenceActivity.INTENT_INSTANCE;
    }
}