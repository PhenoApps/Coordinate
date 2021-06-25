package org.wheatgenetics.coordinate.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.AboutActivity;
import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.gc.GridCreator;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.pc.ProjectCreator;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class MainActivity extends BaseMainActivity
        implements TemplateCreator.Handler {
    // region Fields
    private StatelessGridCreator
            statelessGridCreatorInstance = null;                                            // lazy load
    private TemplateCreator templateCreatorInstance = null;    // ll
    private ProjectCreator projectCreatorInstance = null;    // ll
    // endregion

    // region Private Methods
    // region startActivity() Private Methods
    private void startGridsActivity() {
        this.startActivity(GridsActivity.intent(this));
    }

    private void startTemplatesActivity() {
        this.startActivity(
                TemplatesActivity.intent(this));
    }

    private void startProjectsActivity() {
        this.startActivity(
                ProjectsActivity.intent(this));
    }

    private void startPreferenceActivity() {
        this.startActivityForResult(
                PreferenceActivity.intent(this),
                Types.UNIQUENESS_CLICKED);
    }

    private void startCollectorActivity(@IntRange(from = 1) final long gridId) {
        this.startActivity(
                CollectorActivity.intent(this, gridId));
    }
    // endregion

    // region AboutDialog Private Methods
    private void showAboutAlertDialog() {
        Intent intent = new Intent();

        intent.setClassName(MainActivity.this,
                AboutActivity.class.getName());
        startActivity(intent);
    }
    // endregion

    // region Create Template Private Methods
    private void showLongToast(final String text) {
        Utils.showLongToast(this, text);
    }

    @NonNull
    private TemplateCreator templateCreator() {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new TemplateCreator(
                    this, Types.CREATE_TEMPLATE, this);
        return this.templateCreatorInstance;
    }
    // endregion

    private ProjectCreator projectCreator() {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
                new ProjectCreator(this);
        return this.projectCreatorInstance;
    }
    // endregion

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final ListView mainListView = this.findViewById(
                R.id.mainListView);        // From layout/content_main.xml.
        if (null != mainListView) {
            // noinspection Convert2Diamond
            mainListView.setAdapter(new ArrayAdapter<String>(
                    this, /* resource => */ R.layout.main_list_item,
                    /* objects => */ new String[]{
                    this.getString(R.string.MainActivityGridsItem),
                    this.getString(
                            R.string.MainActivityTemplateMenuItemTitle),
                    this.getString(
                            R.string.MainActivityProjectMenuItemTitle),
                    this.getString(R.string.MainActivitySettingsItem),
                    this.getString(R.string.MainActivityAboutItem)}));
            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent,
                                        final View view, final int position, final long id) {
                    switch (position) {
                        case 0:
                            MainActivity
                                    .this.startGridsActivity();
                            break;

                        case 1:
                            MainActivity
                                    .this.startTemplatesActivity();
                            break;

                        case 2:
                            MainActivity
                                    .this.startProjectsActivity();
                            break;

                        case 3:
                            MainActivity
                                    .this.startPreferenceActivity();
                            break;

                        case 4:
                            MainActivity
                                    .this.showAboutAlertDialog();
                            break;
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode && null != data)
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode) {
                case Types.CREATE_TEMPLATE:
                    this.templateCreator().continueExcluding(data.getExtras());
                    break;
            }
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    GridCreator gridCreator() {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
                new StatelessGridCreator(
                        this, Types.CREATE_GRID,
                        new StatelessGridCreator.Handler() {
                            @Override
                            public void handleGridCreated(
                                    @IntRange(from = 1) final long gridId) {
                                MainActivity.this.startCollectorActivity(
                                        gridId);
                            }
                        });
        return this.statelessGridCreatorInstance;
    }

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @Override
    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        @NonNull final String text;
        {
            @NonNull final String format;
            {
                @StringRes final int resId =
                        this.templatesTable().insert(templateModel) > 0 ?
                                R.string.TemplateCreatedToast :
                                R.string.TemplateNotCreatedToast;
                format = this.getString(resId);
            }
            text = String.format(format, templateModel.getTitle());
        }
        this.showLongToast(text);
    }
    // endregion
    // endregion

    // region MenuItem Event Handlers
    public void onGridMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        ((StatelessGridCreator) this.gridCreator()).create();
    }

    public void onTemplateMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.templateCreator().create();
    }

    public void onProjectMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.projectCreator().create();
    }
    // endregion
}