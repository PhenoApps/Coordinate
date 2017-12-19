package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener
 * org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.TestAlertDialog
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    // region Fields
    private android.support.v4.widget.DrawerLayout                drawerLayout    = null;
    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences     ;
    private org.wheatgenetics.coordinate.TestAlertDialog          testAlertDialog = null;
    // endregion

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_main);

        this.drawerLayout = (android.support.v4.widget.DrawerLayout) this.findViewById(
            org.wheatgenetics.coordinate.R.id.drawer_layout);      // From layout/activity_main.xml.

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.coordinate.R.id.toolbar);   // From layout/app_-
            this.setSupportActionBar(toolbar);                                  //  bar_main.xml.

            {
                final android.support.v7.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final android.support.v7.app.ActionBarDrawerToggle toggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_open ,
                    org.wheatgenetics.coordinate.R.string.navigation_drawer_close);
            assert null != this.drawerLayout; this.drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
        // endregion

        // region Configure navigation menu.
        final android.support.design.widget.NavigationView navigationView =
            (android.support.design.widget.NavigationView) this.findViewById(
                org.wheatgenetics.coordinate.R.id.nav_view);       // From layout/activity_main.xml.
        assert null != navigationView; navigationView.setNavigationItemSelectedListener(
            new org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener(
                new org.wheatgenetics.coordinate.navigation.NavigationItemSelectedListener.Handler()
                {
                    @java.lang.Override
                    public void closeDrawer()
                    { org.wheatgenetics.coordinate.MainActivity.this.closeDrawer(); }
                }));
        // endregion

        this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
            this.getSharedPreferences("Settings", /* mode => */ 0));
    }

    @java.lang.Override
    public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.closeDrawer();
        else
            super.onBackPressed();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.main, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
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
    // endregion

    public void onTestCoordinateButtonClick(final android.view.View view)
    {
        if (null == this.testAlertDialog) this.testAlertDialog = new TestAlertDialog(this);
        this.testAlertDialog.show();
    }
}