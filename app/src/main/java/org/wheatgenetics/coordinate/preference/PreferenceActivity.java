package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bytehamster.lib.preferencesearch.SearchPreference;
import com.bytehamster.lib.preferencesearch.SearchPreferenceFragment;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResultListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;
import org.wheatgenetics.coordinate.utils.InsetHandler;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PreferenceActivity extends BackActivity implements SearchPreferenceResultListener {

    private PreferenceFragment prefsFragment;

    private static Intent INTENT_INSTANCE = null;

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBottomNavigationBarView().setSelectedItemId(R.id.action_nav_settings);
        setupBottomNavigationBar();
    }

    @NonNull
    public static Intent intent(@NonNull final Context context) {
        return null == INTENT_INSTANCE ?
                INTENT_INSTANCE = new Intent(context, PreferenceActivity.class) : INTENT_INSTANCE;
    }

    protected BottomNavigationView getBottomNavigationBarView() {
        return findViewById(R.id.act_preferences_bnv);
    }
    
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        androidx.appcompat.widget.Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InsetHandler.applyToolbarInsets(toolbar);

        InsetHandler.applyBottomNavInsets(getBottomNavigationBarView());
        getBottomNavigationBarView().setSelectedItemId(R.id.action_nav_settings);


        // Set up back stack listener for search screen
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.act_prefs_fragment);
            if (currentFragment instanceof SearchPreferenceFragment) {
                setToolbarBackEnabled();
            }
            else if (currentFragment instanceof PreferenceFragment) {
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    // if navigated to PreferenceFragment from search results
                    // enable the back button
                    setToolbarBackEnabled();
                } else {
                    setToolbarDisabled();
                }
            }
        });

        prefsFragment = new PreferenceFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_prefs_fragment, prefsFragment).commit();
    }

    protected void setToolbarBackEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.bottom_nav_bar_settings));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().show();
        }
    }

    protected void setToolbarDisabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                // if the previous fragment in the stack is PreferenceFragment
                // inflate the fragment container with PreferenceFragment
                // and enable search bar
                callPreferenceFragment();
            } else {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchResultClicked(@NonNull SearchPreferenceResult result) {
        new Handler().post(() -> prefsFragment.onSearchResultClicked(result));
        if (result.getResourceFile() == R.xml.preferences) {
            setToolbarBackEnabled();
        }
    }

    public void setupBottomNavigationBar() {

        BottomNavigationView bottomNavigationView = getBottomNavigationBarView();

        if (bottomNavigationView != null) {
            if (bottomNavigationView.getMenu().size() == 0) {
                bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);
            }

            bottomNavigationView.setSelectedItemId(R.id.action_nav_settings);

            bottomNavigationView.setOnItemSelectedListener((item -> {

                final int grids = R.id.action_nav_grids;
                final int templates = R.id.action_nav_templates;
                final int projects = R.id.action_nav_projects;
                final int settings = R.id.action_nav_settings;

                switch (item.getItemId()) {
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
                    case projects:
                        Intent projectIntent = ProjectsActivity.intent(this);
                        projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(projectIntent);
                        break;
                    case settings:
                        callPreferenceFragment();
                        break;
                    default:
                        break;

                }

                return true;
            }));
        }
    }

    protected void callPreferenceFragment() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.act_prefs_fragment, prefsFragment)
                .commit();

        SearchPreference searchPreference = prefsFragment.findPreference("searchPreference");
        if (searchPreference != null) searchPreference.setVisible(true);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.act_prefs_fragment);

        if (currentFragment instanceof SearchPreferenceFragment) {
            SearchPreference searchPreference = prefsFragment.findPreference("searchPreference");
            if (searchPreference != null) searchPreference.setVisible(true);
        }
        super.onBackPressed();
    }
}