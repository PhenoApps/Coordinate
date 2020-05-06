package org.wheatgenetics.coordinate.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.wheatgenetics.changelog.ChangeLogAlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.gc.GridCreator;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.model.TemplateType;
import org.wheatgenetics.javalib.Utils;
import org.wheatgenetics.sharedpreferences.SharedPreferences;

abstract class BaseMainActivity extends AppCompatActivity
        implements StringGetter {
    // region Fields
    private TemplatesTable templatesTableInstance = null;//ll
    private String versionName;
    private SharedPreferences
            sharedPreferencesInstances = null;                                              // lazy load

    private ChangeLogAlertDialog
            changeLogAlertDialogInstance = null;                                            // lazy load
    // endregion

    @NonNull
    private ChangeLogAlertDialog changeLogAlertDialog() {
        if (null == this.changeLogAlertDialogInstance) this.changeLogAlertDialogInstance =
                new ChangeLogAlertDialog(
                        /* activity               => */this,
                        /* changeLogRawResourceId => */ R.raw.changelog);
        return this.changeLogAlertDialogInstance;
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this);
        return this.templatesTableInstance;
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
    void showChangeLog() {
        this.changeLogAlertDialog().show();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    abstract GridCreator gridCreator();

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    String versionName() {
        return this.versionName;
    }
    // endregion

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
            this.showChangeLog();
        }
        // endregion
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