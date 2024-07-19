package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PreferenceActivity extends BackActivity {

    private static Intent INTENT_INSTANCE = null;

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

    @NonNull
    public static Intent intent(@NonNull final Context context) {
        return null == INTENT_INSTANCE ?
                INTENT_INSTANCE = new Intent(context, PreferenceActivity.class) : INTENT_INSTANCE;
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_preferences_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;

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
                    startActivity(ProjectsActivity.intent(this));
                    break;
                default:
                    break;

            }

            return true;
        }));
    }
    
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);

        setupBottomNavigationBar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
//        this.setResult();
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}