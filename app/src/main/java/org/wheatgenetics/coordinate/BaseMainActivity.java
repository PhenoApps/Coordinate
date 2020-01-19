package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.os.Bundle
 * android.view.View
 * android.view.View.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.AboutAlertDialog
 * org.wheatgenetics.coordinate.R
 */
public abstract class BaseMainActivity extends androidx.appcompat.app.AppCompatActivity
{
    // region Fields
    private java.lang.String                                      versionName;
    private org.wheatgenetics.sharedpreferences.SharedPreferences
        sharedPreferencesInstances = null;                                              // lazy load

    private org.wheatgenetics.changelog.ChangeLogAlertDialog
        changeLogAlertDialogInstance = null;                                            // lazy load

    private org.wheatgenetics.coordinate.AboutAlertDialog aboutAlertDialogInstance = null;  // lazy
                                                                                            //  load
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance  = null;//ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.changelog.ChangeLogAlertDialog changeLogAlertDialog()
    {
        if (null == this.changeLogAlertDialogInstance) this.changeLogAlertDialogInstance =
            new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */this,
                /* changeLogRawResourceId => */ org.wheatgenetics.coordinate.R.raw.changelog);
        return this.changeLogAlertDialogInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.AboutAlertDialog aboutAlertDialog()
    {
        if (null == this.aboutAlertDialogInstance) this.aboutAlertDialogInstance =
            new org.wheatgenetics.coordinate.AboutAlertDialog(
                this, this.versionName(), new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                { org.wheatgenetics.coordinate.BaseMainActivity.this.showChangeLog(); }
            });
        return this.aboutAlertDialogInstance;
    }
    // endregion

    // region Protected Methods
    protected java.lang.String versionName() { return this.versionName; }

    @androidx.annotation.NonNull
    protected org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences()
    {
        if (null == this.sharedPreferencesInstances) this.sharedPreferencesInstances =
            new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */0));
        return this.sharedPreferencesInstances;
    }

    protected void showChangeLog() { this.changeLogAlertDialog().show(); }

    protected void showAboutAlertDialog() { this.aboutAlertDialog().show(); }

    @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        return this.templatesTableInstance;
    }
    // endregion

    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // region Initialize Default Templates
        {
            // Adds default templates to database if they aren't there already.  If they are there
            // then they are updated to their default values.
            final org.wheatgenetics.coordinate.model.TemplateModels defaultTemplateModels =
                org.wheatgenetics.coordinate.model.TemplateModels.makeDefault();
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
        final org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences =
            this.sharedPreferences();
        if (!sharedPreferences.updateVersionIsSet(versionCode))
            { sharedPreferences.setUpdateVersion(versionCode); this.showChangeLog(); }
        // endregion
    }
}