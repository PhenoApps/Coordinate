package org.wheatgenetics.coordinate.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.michaelflisar.changelog.ChangelogBuilder;
import com.michaelflisar.changelog.classes.ImportanceChangelogSorter;
import com.michaelflisar.changelog.internal.ChangelogDialogFragment;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.gc.GridCreator;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.model.TemplateType;
import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.sharedpreferences.SharedPreferences;

public abstract class BaseMainActivity extends AppCompatActivity
        implements StringGetter {
    // region Fields
    private TemplatesTable templatesTableInstance = null;//ll
    private ProjectsTable projectsTableInstance = null;

    private String versionName;
    private SharedPreferences
            sharedPreferencesInstances = null;                                              // lazy load
    private boolean backPressed = false;
    // endregion

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this);
        return this.projectsTableInstance;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    SharedPreferences sharedPreferences() {
        if (null == this.sharedPreferencesInstances) this.sharedPreferencesInstances =
                new SharedPreferences(
                        this.getSharedPreferences("Settings", /* mode => */0), this);
        return this.sharedPreferencesInstances;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected abstract GridCreator gridCreator();

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    String versionName() {
        return this.versionName;
    }
    // endregion

    @Override
    public void onBackPressed() {
        if (backPressed) {
            finish();
        } else {
            Toast.makeText(this, R.string.back_button_twice_press, Toast.LENGTH_SHORT).show();
            backPressed = true;
            new Handler().postDelayed(() -> backPressed = false, 5000);
        }
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // region Initialize Default Templates
        {
            // Adds default templates to database if they aren't there already.  If they are there
            // then they are updated to their default values.
            @NonNull final TemplateModels defaultTemplateModels =
                    TemplateModels.makeDefault(this);
            if (defaultTemplateModels.size() > 0) {
                @NonNull final TemplatesTable templatesTable =
                        this.templatesTable();
                for (final TemplateModel defaultTemplateModel :
                        defaultTemplateModels) {
                    final TemplateType defaultTemplateType =
                            defaultTemplateModel.getType();
                    if (templatesTable.exists(defaultTemplateType)) {
                        {
                            @Nullable final TemplateModel
                                    existingTemplateModel = templatesTable.get(defaultTemplateType);
                            if (null != existingTemplateModel)
                                defaultTemplateModel.setId(existingTemplateModel.getId());
                        }
                        templatesTable.update(defaultTemplateModel);
                    } else templatesTable.insert(defaultTemplateModel);
                }
            }
        }
        // endregion

        // region Get version.
        int versionCode;
        try {
            final PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(       // throws android.content.pm.Package-
                            this.getPackageName(), 0);               //  Manager.NameNotFoundException
            if (null == packageInfo) {
                versionCode = 0;
                this.versionName = Utils.adjust(null);
            } else {
                versionCode = packageInfo.versionCode;
                this.versionName = packageInfo.versionName;
            }
        } catch (final PackageManager.NameNotFoundException e) {
            versionCode = 0;
            this.versionName = Utils.adjust(null);
        }
        // endregion

        // region Set version.
        @NonNull final SharedPreferences sharedPreferences =
                this.sharedPreferences();
        if (!sharedPreferences.updateVersionIsSet(versionCode)) {
            sharedPreferences.setUpdateVersion(versionCode);
            showChangelog(true, false);
        }
        // endregion
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
                .buildAndShowDialog(this, false); // second parameter defines, if the dialog has a dark or light theme
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) supportActionBar.setTitle(null);
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode && null != data)
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode) {
                case Types.CREATE_GRID:
                    this.gridCreator().continueExcluding(data.getExtras());
                    break;
            }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.getResources().getQuantityString(resId, quantity, formatArgs);
    }
    // endregion
    // endregion
}