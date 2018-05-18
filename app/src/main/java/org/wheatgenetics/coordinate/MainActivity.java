package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 *
 * org.wheatgenetics.coordinate.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.coordinate.R.id.toolbar);         // From layout/app_bar_main.xml.
            this.setSupportActionBar(toolbar);

            final android.support.v4.widget.DrawerLayout drawerLayout = this.findViewById(
                org.wheatgenetics.coordinate.R.id.drawer_layout);  // From layout/activity_main.xml.
            final android.support.v7.app.ActionBarDrawerToggle toggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_open ,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_close);
            assert null != drawerLayout; drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
        // endregion

        // region Configure navigation view.
        final android.support.design.widget.NavigationView navigationView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.nav_view);           // From layout/activity_main.xml.
        assert null != navigationView; navigationView.setNavigationItemSelectedListener(this);
        // endregion
    }

    @java.lang.Override public void onBackPressed()
    {
        final android.support.v4.widget.DrawerLayout drawerLayout = this.findViewById(
            org.wheatgenetics.coordinate.R.id.drawer_layout);      // From layout/activity_main.xml.
        assert null != drawerLayout;
        if (drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
        else
            super.onBackPressed();
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.main, menu);
        return true;
    }

    @java.lang.Override public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (org.wheatgenetics.coordinate.R.id.action_settings == itemId)
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    // region android.support.design.widget.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override @java.lang.SuppressWarnings({"StatementWithEmptyBody"})
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
        }

        {
            final android.support.v4.widget.DrawerLayout drawerLayout = this.findViewById(
                org.wheatgenetics.coordinate.R.id.drawer_layout);  // From layout/activity_main.xml.
            assert null != drawerLayout;
            drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
        }
        return true;
    }
    // endregion
}