package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class BasePreferenceFragment extends PreferenceFragmentCompat {

    private String mRootKey;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        mRootKey = rootKey;
    }

    protected void setToolbar(String title) {
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            AppCompatActivity act = (AppCompatActivity) getActivity();
            if (act.getSupportActionBar() != null) {
                ActionBar bar = act.getSupportActionBar();
                bar.setTitle(title);
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    // for toolbar with no action or title
    protected void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(null);
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getContext() != null) {
                Context ctx = getContext();
                Intent intent = PreferenceActivity.intent(ctx);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setupBottomNavigationBar() {
        try{
            View view = requireActivity().findViewById(android.R.id.content);

            Context context = getContext();

            if (context != null && view != null){
                final BottomNavigationView bottomNavigationView = view.findViewById(R.id.act_preferences_bnv);

                bottomNavigationView.setOnItemSelectedListener((item -> {

                    final int grids = R.id.action_nav_grids;
                    final int templates = R.id.action_nav_templates;
                    final int projects = R.id.action_nav_projects;
                    final int settings = R.id.action_nav_settings;

                    switch(item.getItemId()) {
                        case grids:
                            Intent gridsIntent = GridsActivity.intent(context);
                            gridsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gridsIntent);
                            break;
                        case templates:
                            Intent templateIntent = TemplatesActivity.intent((context) );
                            templateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(templateIntent);
                            break;
                        case settings:
                            setPreferencesFromResource(R.xml.preferences, mRootKey);
                            initToolbar();
                            break;
                        case projects:
                            Intent projectIntent = ProjectsActivity.intent(context);
                            projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(projectIntent);
                            break;
                        default:
                            break;
                    }

                    return true;
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
