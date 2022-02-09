package org.wheatgenetics.coordinate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        setupBottomNavigationBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_about_bnv);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_about);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_about_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;
            final int settings = R.id.action_nav_settings;

            switch(item.getItemId()) {
                case templates:
                    startActivity(TemplatesActivity.intent(this));
                    break;
                case projects:
                    startActivity(ProjectsActivity.intent(this));
                    break;
                case settings:
                    startActivity(PreferenceActivity.intent(this));
                    break;
                case grids:
                    startActivity(GridsActivity.intent(this));
                    break;
                default:
                    break;
            }

            return true;
        }));
    }
}