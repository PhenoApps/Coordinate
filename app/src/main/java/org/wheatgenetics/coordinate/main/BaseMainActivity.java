package org.wheatgenetics.coordinate.main;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.content.res.Resources.NotFoundException
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 * androidx.appcompat.app.ActionBar
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 */
abstract class BaseMainActivity extends androidx.appcompat.app.AppCompatActivity
implements org.wheatgenetics.coordinate.StringGetter
{
    // region Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable  templatesTableInstance = null;//ll
    private java.lang.String                                      versionName                  ;
    private org.wheatgenetics.sharedpreferences.SharedPreferences
        sharedPreferencesInstances = null;                                              // lazy load

    private org.wheatgenetics.changelog.ChangeLogAlertDialog
        changeLogAlertDialogInstance = null;                                            // lazy load
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.changelog.ChangeLogAlertDialog changeLogAlertDialog()
    {
        if (null == this.changeLogAlertDialogInstance) this.changeLogAlertDialogInstance =
            new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */this,
                /* changeLogRawResourceId => */ org.wheatgenetics.coordinate.R.raw.changelog);
        return this.changeLogAlertDialogInstance;
    }

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences()
    {
        if (null == this.sharedPreferencesInstances) this.sharedPreferencesInstances =
            new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */0),this);
        return this.sharedPreferencesInstances;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void showChangeLog() { this.changeLogAlertDialog().show(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull abstract org.wheatgenetics.coordinate.gc.GridCreator gridCreator();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String versionName() { return this.versionName; }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // region Initialize Default Templates
        {
            // Adds default templates to database if they aren't there already.  If they are there
            // then they are updated to their default values.
            @androidx.annotation.NonNull
            final org.wheatgenetics.coordinate.model.TemplateModels defaultTemplateModels =
                org.wheatgenetics.coordinate.model.TemplateModels.makeDefault(this);
            if (defaultTemplateModels.size() > 0)
            {
                @androidx.annotation.NonNull
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    this.templatesTable();
                for (final org.wheatgenetics.coordinate.model.TemplateModel defaultTemplateModel:
                defaultTemplateModels)
                {
                    final org.wheatgenetics.coordinate.model.TemplateType defaultTemplateType =
                        defaultTemplateModel.getType();
                    if (templatesTable.exists(defaultTemplateType))
                    {
                        {
                            @androidx.annotation.Nullable
                            final org.wheatgenetics.coordinate.model.TemplateModel
                                existingTemplateModel = templatesTable.get(defaultTemplateType);
                            if (null != existingTemplateModel)
                                defaultTemplateModel.setId(existingTemplateModel.getId());
                        }
                        templatesTable.update(defaultTemplateModel);
                    }
                    else templatesTable.insert(defaultTemplateModel);
                }
            }
        }
        // endregion

        // region Get version.
        int versionCode;
        try
        {
            final android.content.pm.PackageInfo packageInfo =
                this.getPackageManager().getPackageInfo(       // throws android.content.pm.Package-
                    this.getPackageName(),0);               //  Manager.NameNotFoundException
            if (null == packageInfo)
            {
                versionCode      = 0;
                this.versionName = org.wheatgenetics.javalib.Utils.adjust(null);
            }
            else
            {
                versionCode      = packageInfo.versionCode;
                this.versionName = packageInfo.versionName;
            }
        }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        {
            versionCode      = 0;
            this.versionName = org.wheatgenetics.javalib.Utils.adjust(null);
        }
        // endregion

        // region Set version.
        @androidx.annotation.NonNull
        final org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences =
            this.sharedPreferences();
        if (!sharedPreferences.updateVersionIsSet(versionCode))
            { sharedPreferences.setUpdateVersion(versionCode); this.showChangeLog(); }
        // endregion
    }

    @java.lang.Override protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        final androidx.appcompat.app.ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) supportActionBar.setTitle(null);
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (android.app.Activity.RESULT_OK == resultCode && null != data)
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                    this.gridCreator().continueExcluding(data.getExtras()); break;
            }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.getString(resId); }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException
    { return this.getResources().getQuantityString(resId, quantity, formatArgs); }
    // endregion
    // endregion
}