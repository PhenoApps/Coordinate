package org.wheatgenetics.coordinate;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;
import org.wheatgenetics.coordinate.utils.InsetHandler;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InsetHandler.enableEdgeToEdge(this);

        setContentView(R.layout.activity_about);

        setupBottomNavigationBar();
        InsetHandler.applyBottomNavInsets(findViewById(R.id.act_about_bnv));
        InsetHandler.applyRootInsets(this.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_about_bnv);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_about_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;
            final int settings = R.id.action_nav_settings;
            if (item.getItemId() == grids) {
                Intent gridsIntent = GridsActivity.intent(this);
                gridsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gridsIntent);
            } else if (item.getItemId() == templates) {
                Intent templateIntent = TemplatesActivity.intent(this);
                templateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(templateIntent);
            } else if (item.getItemId() == settings) {
                Intent prefsIntent = PreferenceActivity.intent(this);
                prefsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefsIntent);
            } else if (item.getItemId() == projects) {
                Intent projectIntent = ProjectsActivity.intent(this);
                projectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ProjectsActivity.intent(this));
            }

            return true;
        }));
    }
}
