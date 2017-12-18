package org.wheatgenetics.coordinate.navigation;

/**
 * Uses:
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.view.MenuItem
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler { public abstract void closeDrawer (); }

    private final org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler
        handler;

    public NavigationItemSelectedListener(
    final org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler handler)
    { super(); this.handler = handler; }

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
            case org.wheatgenetics.coordinate.R.id.nav_create_grid    : break;
            case org.wheatgenetics.coordinate.R.id.nav_delete_grid    : break;
            case org.wheatgenetics.coordinate.R.id.nav_create_template: break;
            case org.wheatgenetics.coordinate.R.id.nav_load_template  : break;
            case org.wheatgenetics.coordinate.R.id.nav_delete_template: break;
            case org.wheatgenetics.coordinate.R.id.nav_import_grid    : break;
            case org.wheatgenetics.coordinate.R.id.nav_export_grid    : break;
            case org.wheatgenetics.coordinate.R.id.nav_show_about     : break;
        }
        assert null != this.handler; this.handler.closeDrawer(); return true;
    }
    // endregion
}