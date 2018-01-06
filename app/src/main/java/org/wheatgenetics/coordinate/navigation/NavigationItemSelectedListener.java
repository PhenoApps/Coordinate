package org.wheatgenetics.coordinate.navigation;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.view.MenuItem
 * android.view.View.OnClickListener
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    { public abstract void closeDrawer(); public abstract void deleteGrid(); }

    // region Fields
    private final android.app.Activity activity   ;
    private final java.lang.String     versionName;
    private final org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler
        handler;
    private final android.view.View.OnClickListener versionOnClickListener;

    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = null;
    // endregion

    private void deleteGrid() { assert null != this.handler; this.handler.deleteGrid(); }

    public NavigationItemSelectedListener(final android.app.Activity activity,
    final java.lang.String versionName,
    final org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.activity = activity; this.versionName            = versionName           ;
        this.handler  = handler ; this.versionOnClickListener = versionOnClickListener;
    }

    // region android.support.design.widget.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            // The following six ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.coordinate.R.id.nav_create_grid : break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_grid :
                org.wheatgenetics.coordinate.Utils.confirm(
                    /* context => */ this.activity,
                    /* message => */ org.wheatgenetics.coordinate
                        .R.string.NavigationItemSelectedListenerDeleteGridConfirmation,
                    /* yesRunnable => */ new java.lang.Runnable()
                        {
                            @java.lang.Override
                            public void run()
                            {
                                org.wheatgenetics.coordinate.navigation
                                    .NavigationItemSelectedListener.this.deleteGrid();
                            }
                        });
                break;

            case org.wheatgenetics.coordinate.R.id.nav_create_template: break;
            case org.wheatgenetics.coordinate.R.id.nav_load_template  : break;
            case org.wheatgenetics.coordinate.R.id.nav_delete_template: break;
            case org.wheatgenetics.coordinate.R.id.nav_import_grid    : break;
            case org.wheatgenetics.coordinate.R.id.nav_export_grid    : break;

            case org.wheatgenetics.coordinate.R.id.nav_show_about :
                if (null == this.aboutAlertDialog)
                {
                    final java.lang.String title, msgs[];
                    {
                        assert null != this.activity;
                        final android.content.res.Resources resources =
                            this.activity.getResources();

                        assert null != resources;
                        title = resources.getString(
                            org.wheatgenetics.coordinate.R.string.AboutAlertDialogTitle);
                        msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                            org.wheatgenetics.coordinate.R.string.AboutAlertDialogMsg));
                    }

                    this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                        /* context       => */ this.activity                                     ,
                        /* title         => */ title                                             ,
                        /* versionName   => */ this.versionName                                  ,
                        /* msgs[]        => */ msgs                                              ,
                        /* suppressIndex => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                        /* versionOnClickListener => */ this.versionOnClickListener              );
                }
                this.aboutAlertDialog.show(); break;
        }
        assert null != this.handler; this.handler.closeDrawer(); return true;
    }
    // endregion
}