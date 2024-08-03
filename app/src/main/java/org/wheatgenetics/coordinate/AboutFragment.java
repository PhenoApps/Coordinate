package org.wheatgenetics.coordinate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.michaelflisar.changelog.ChangelogBuilder;
import com.michaelflisar.changelog.classes.ImportanceChangelogSorter;
import com.michaelflisar.changelog.internal.ChangelogDialogFragment;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.preference.BasePreferenceFragment;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class AboutFragment extends MaterialAboutFragment {
    //todo move to fragments so aboutactivity can extend base activity

    BasePreferenceFragment basePreferenceFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basePreferenceFragment = new BasePreferenceFragment();
        setToolbar();
        setupBottomNavigationBar();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.post(() -> {
//            basePreferenceFragment.setToolbar(getString(R.string.preferences_about_title));
//            setupBottomNavigationBar();
//        });
//    }

    public void setToolbar() {
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            AppCompatActivity act = (AppCompatActivity) getActivity();
            if (act.getSupportActionBar() != null) {
                ActionBar bar = act.getSupportActionBar();
                bar.setTitle(getString(R.string.preferences_about_title));
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    public void setupBottomNavigationBar() {
        try {
            View view = getActivity().findViewById(android.R.id.content);

            Context context = getContext();

            if (context != null && view != null) {
                final BottomNavigationView bottomNavigationView = view.findViewById(R.id.act_preferences_bnv);

                bottomNavigationView.setOnItemSelectedListener((item -> {

                    final int grids = R.id.action_nav_grids;
                    final int templates = R.id.action_nav_templates;
                    final int projects = R.id.action_nav_projects;
                    final int settings = R.id.action_nav_settings;

                    switch (item.getItemId()) {
                        case grids:
                            Intent gridsIntent = GridsActivity.intent(context);
                            gridsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gridsIntent);
                            break;
                        case templates:
                            Intent templateIntent = TemplatesActivity.intent((context));
                            templateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(templateIntent);
                            break;
                        case settings:
                            Intent prefsIntent = PreferenceActivity.intent((context));
                            prefsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(prefsIntent);
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

    @Override
    @NonNull
    public MaterialAboutList getMaterialAboutList(@NonNull Context c) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();

        // Add items to card
        int colorIcon = R.color.mal_color_icon_light_theme;

        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .icon(R.mipmap.ic_launcher)
                .build());

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_info),
                getString(R.string.about_version_title),
                false));

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(getString(R.string.changelog_title))
                .icon(R.drawable.ic_about_changelog)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        showChangelog(false, false);
                    }
                })
                .build());

        appCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_manual),
                getString(R.string.about_manual_title),
                false,
                Uri.parse("https://docs.coordinate.phenoapps.org/en/latest/coordinate.html")));

        appCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_rate),
                getString(R.string.about_rate),
                null
        ));

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(getString(R.string.about_project_lead_title));

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(getString(R.string.about_developer_trife))
                .subText(getString(R.string.about_developer_trife_location))
                .icon(R.drawable.ic_pref_profile_person)
                .build());

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(c,
                getResources().getDrawable(R.drawable.ic_about_email),
                getString(R.string.about_email_title),
                true,
                getString(R.string.about_developer_trife_email),
                "Coordinate Question"));

        MaterialAboutCard.Builder contributorsCardBuilder = new MaterialAboutCard.Builder();
        contributorsCardBuilder.title(getString(R.string.about_contributors_title));

        contributorsCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_contributors),
                getString(R.string.about_contributors_developers_title),
                false,
                Uri.parse("https://github.com/PhenoApps/Coordinate#-contributors")));

        /*contributorsCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(getString(R.string.about_translators_title))
                .subText(getString(R.string.about_translators_text))
                .icon(R.drawable.ic_about_translators)
                .build());*/

        contributorsCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_funding),
                getString(R.string.about_contributors_funding_title),
                false,
                Uri.parse("https://github.com/PhenoApps/Coordinate#-funding")));

        MaterialAboutCard.Builder technicalCardBuilder = new MaterialAboutCard.Builder();
        technicalCardBuilder.title(getString(R.string.about_technical_title));

        technicalCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.about_github_title)
                .icon(R.drawable.ic_about_github)
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://github.com/PhenoApps/Coordinate")))
                .build());

        technicalCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.libraries_title)
                .icon(R.drawable.ic_about_libraries)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        new LibsBuilder()
                                .withActivityTheme(R.style.AppThemeNoActionBar)
                                .withAutoDetect(true)
                                .withActivityTitle(getString(R.string.libraries_title))
                                .withLicenseShown(true)
                                .withVersionShown(true)
                                .start(getContext());
                    }
                })
                .build());

        MaterialAboutCard.Builder otherAppsCardBuilder = new MaterialAboutCard.Builder();
        otherAppsCardBuilder.title(getString(R.string.about_title_pheno_apps));

        otherAppsCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                getResources().getDrawable(R.drawable.ic_about_website),
                "PhenoApps.org",
                false,
                Uri.parse("http://phenoapps.org/")));

        otherAppsCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Field Book")
                .icon(R.drawable.other_ic_field_book)
                .setOnClickAction(openAppOrStore("com.fieldbook.tracker", c))
                .build());

        otherAppsCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Intercross")
                .icon(R.drawable.other_ic_intercross)
                .setOnClickAction(openAppOrStore("org.phenoapps.intercross", c))
                .build());

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), contributorsCardBuilder.build(), otherAppsCardBuilder.build(), technicalCardBuilder.build());
    }

    private void showChangelog(Boolean managedShow, Boolean rateButton) {
        ChangelogDialogFragment builder = new ChangelogBuilder()
                .withUseBulletList(true) // true if you want to show bullets before each changelog row, false otherwise
                .withManagedShowOnStart(managedShow)  // library will take care to show activity/dialog only if the changelog has new infos and will only show this new infos
                .withRateButton(rateButton) // enable this to show a "rate app" button in the dialog => clicking it will open the play store; the parent activity or target fragment can also implement IChangelogRateHandler to handle the button click
                .withSummary(false, true) // enable this to show a summary and a "show more" button, the second paramter describes if releases without summary items should be shown expanded or not
                .withTitle(getString(R.string.changelog_title)) // provide a custom title if desired, default one is "Changelog <VERSION>"
                .withOkButtonLabel("OK") // provide a custom ok button text if desired, default one is "OK"
                .withSorter(new ImportanceChangelogSorter())
                .buildAndShowDialog((AppCompatActivity) getActivity(), false); // second parameter defines, if the dialog has a dark or light theme
    }

    private MaterialAboutItemOnClickAction openAppOrStore(final String packageName, Context c) {
        PackageManager packageManager = getContext().getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);

            return new MaterialAboutItemOnClickAction() {
                @Override
                public void onClick() {
                    Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(launchIntent);
                }
            };
        } catch (PackageManager.NameNotFoundException e) {
            return ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
        }
    }
}
