package org.wheatgenetics.coordinate.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import org.json.JSONException;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Coordinate;
import org.wheatgenetics.coordinate.barcodes.IntentIntegrator;
import org.wheatgenetics.coordinate.barcodes.IntentResult;
import org.wheatgenetics.coordinate.csv.CsvWriter;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.optionalField.OptionalField;
import org.wheatgenetics.coordinate.utils.Constants;
import org.wheatgenetics.coordinate.utils.Utils;

/**
 * Uses:
 * android.support.v7.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class Main extends android.support.v7.app.AppCompatActivity
implements android.view.View.OnClickListener, OnEditorActionListener, OnKeyListener
{
    private static final int STATE_NORMAL   = 0;
    private static final int STATE_DONE     = 1;
    private static final int STATE_ACTIVE   = 2;
    private static final int STATE_INACTIVE = 3;

    // region Template
    private static final int TYPE_SEED    = 0;
    private static final int TYPE_DNA     = 1;
    private static final int TYPE_DEFAULT = 2;
    // endregion

    private static final int MODE_DNA     = 0;
    private static final int MODE_SAVED   = 1;
    private static final int MODE_DEFAULT = 2;

    private static final String TAG = "Coordinate";

    private LinearLayout mLayoutMain    ;
    private LinearLayout mLayoutGrid    ;
    private LinearLayout mLayoutOptional;
    private TableLayout  mTableData     ;

    private TextView templateTextView;

    private EditText mEditData;

    private View curCell = null;

    private long   id         = 0 ;
    private long   grid       = 0 ;
    private String mGridTitle = "";
    private int    mCurRow    = 1 ;
    private int    mCurCol    = 1 ;

    private SharedPreferences ep;

    // region Template
    private int    templateType  = TYPE_SEED;
    private String templateTitle = ""       ;

    private int     rows         =    20;
    private int     cols         =    10;
    private boolean rowNumbering = false;
    private boolean colNumbering = true ;

    private List<Point> excludeCells = new ArrayList<>();

    private List<Integer> excludeRows = new ArrayList<>();
    private List<Integer> excludeCols = new ArrayList<>();
//    private org.wheatgenetics.coordinate.model.TemplatesTable templatesTable;
    // endregion

    private String menuMain[];

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields;

    private DataExporter mTask;
    private long         mLastExportGridId = -1;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout          mDrawerLayout;

    private LinearLayout parent         ;
    private ScrollView   changeContainer;

    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
            this.nonNullOptionalFields);
    }
    
    @Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Plate Id");
        this.nonNullOptionalFields.addDate("Date"    );

        menuMain = new String[]{this.getResources().getString(R.string.template_load),
            this.getResources().getString(R.string.template_new)};

        this.setContentView(R.layout.main);

        final Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        toolbar.bringToFront();

        if (this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setTitle(null);
            this.getSupportActionBar().getThemedContext();
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeButtonEnabled(true);
        }

        ep = this.getSharedPreferences("Settings", 0);

        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        final NavigationView nvDrawer = (NavigationView) this.findViewById(R.id.nvView);
        this.setupDrawerContent(nvDrawer);
        this.setupDrawer();

        parent = new LinearLayout(this);
        changeContainer = new ScrollView(this);
        changeContainer.removeAllViews();
        changeContainer.addView(parent);

        mLayoutMain = (LinearLayout) this.findViewById(R.id.mainLayout);
        mLayoutGrid = (LinearLayout) this.findViewById(R.id.gridArea);
        mLayoutOptional = (LinearLayout) this.findViewById(R.id.optionalLayout);

        mTableData = (TableLayout) this.findViewById(R.id.dataTable);

        this.templateTextView = (TextView) this.findViewById(R.id.templateText);

        mEditData = (EditText) this.findViewById(R.id.dataEdit);
        mEditData.setImeActionLabel(getString(R.string.keyboard_save), KeyEvent.KEYCODE_ENTER);
        mEditData.setOnEditorActionListener(this);

        //mEditData.setOnKeyListener(this);

        this.templateTextView.setText(this.templateTitle);  // model

        mLayoutMain.setVisibility(View.INVISIBLE);

        try { this.initDb(); } catch (final org.json.JSONException e) {}
        this.createDirs();

        if (ep.getLong("CurrentGrid", -1) != -1)
            try { this.loadGrid(ep.getLong("CurrentGrid", -1)); }
            catch (final java.lang.Exception e) {}
        else
            this.menuList();

        this.showTemplateUI();

        if (ep.getInt("UpdateVersion", -1) < this.getVersion())
        {
            final SharedPreferences.Editor ed = ep.edit();
            ed.putInt("UpdateVersion", getVersion());
            ed.apply();
            this.changelog();
        }
    }

    private int getVersion()
    {
        int v = 0;
        try { v = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode; }
        catch (final PackageManager.NameNotFoundException e) { Log.e(TAG, "" + e.getMessage()); }
        return v;
    }

    private void createDirs()
    {
        this.createDir(Constants.MAIN_PATH    );
        this.createDir(Constants.EXPORT_PATH  );
        this.createDir(Constants.TEMPLATE_PATH);
    }

    private void createDir(final File path)
    {
        final File blankFile = new File(path, ".coordinate");

        assert path != null;
        if (!path.exists())
        {
            path.mkdirs();

            try
            {
                blankFile.getParentFile().mkdirs();
                blankFile.createNewFile();
                this.makeFileDiscoverable(blankFile, this);
            }
            catch (final java.io.IOException e) { Log.e(TAG, e.getMessage()); }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.cancelTask();
    }

    private void aboutDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final LayoutInflater layoutInflater = this.getLayoutInflater();

        assert layoutInflater != null;
        final View personView =
            layoutInflater.inflate(R.layout.about, new LinearLayout(this), false);

        assert personView != null;
        final TextView version   = (TextView) personView.findViewById(R.id.tvVersion  );
        final TextView otherApps = (TextView) personView.findViewById(R.id.tvOtherApps);


        final PackageManager packageManager = this.getPackageManager();
        assert packageManager != null;
        try
        {
            final PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            assert packageInfo != null;
            version.setText(getResources().getString(R.string.versiontitle) + " " + packageInfo.versionName);
        }
        catch (final PackageManager.NameNotFoundException e) { Log.e(TAG, e.getMessage()); }

        version.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v) { changelog(); }
            });

        otherApps.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v) { showOtherAppsDialog(); }
            });

        builder.setCancelable(true);
        builder.setTitle(this.getResources().getString(R.string.about));
        builder.setView(personView);
        builder.setNegativeButton(this.getResources().getString(R.string.ok),
            new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.dismiss(); }
            });
        builder.show();
    }

    private void changelog()
    {
        this.parent.setOrientation(LinearLayout.VERTICAL);
        this.parseLog(R.raw.changelog_releases);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
        builder.setTitle(this.getResources().getString(R.string.updatemsg));
        builder.setView(changeContainer)
            .setCancelable(true)
            .setPositiveButton(this.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which)
                    { dialog.dismiss(); }
                });
        builder.create().show();
    }

    private void parseLog(final int resId)
    {
        try
        {
            final InputStream inputStream = getResources().openRawResource(resId);
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader br = new BufferedReader(inputStreamReader, 8192);

            final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(20, 5, 20, 0);

            String curVersionName = null;
            String line;

            while ((line = br.readLine()) != null)
            {
                final TextView header  = new TextView(this);
                final TextView content = new TextView(this);
                final TextView spacer  = new TextView(this);
                final View     ruler   = new View(this);

                header.setLayoutParams(lp);
                content.setLayoutParams(lp);
                spacer.setLayoutParams(lp);
                ruler.setLayoutParams(lp);

                spacer.setTextSize(5);

                ruler.setBackgroundColor(getResources().getColor(R.color.main_colorAccent));
                header.setTextAppearance(getApplicationContext(), R.style.ChangelogTitles);
                content.setTextAppearance(getApplicationContext(), R.style.ChangelogContent);

                if (line.length() == 0)
                {
                    curVersionName = null;
                    spacer.setText("\n");
                    parent.addView(spacer);
                }
                else if (curVersionName == null)
                {
                    final String[] lineSplit = line.split("/");
                    curVersionName = lineSplit[1];
                    header.setText(curVersionName);
                    parent.addView(header);
                    parent.addView(ruler);
                }
                else
                {
                    content.setText("•  " + line);
                    parent.addView(content);
                }
            }
        }
        catch (final java.io.IOException e) { throw new RuntimeException(e); }
    }

    private void showOtherAppsDialog()
    {
        final ListView listView = new ListView(this);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(final AdapterView<?> parent, final View view,
                final int position, final long id)
                {
                    if (position >= 0 && position <= 2)
                    {
                        final String[] links = {
                            "https://play.google.com/store/apps/details?id=com.fieldbook.tracker",
                            "https://play.google.com/store/apps/details?id=org.wheatgenetics.inventory",
                            "http://wheatgenetics.org/apps"};  // TODO: update these links
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(links[position])));
                    }
                }
            });

        final String[] appsArray = new String[3];

        appsArray[0] = "Field Book";
        appsArray[1] = "Inventory";
        appsArray[2] = "1KK";
        //appsArray[3] = "Intercross";
        //appsArray[4] = "Rangle";

        final Integer app_images[] = {R.drawable.other_ic_field_book, R.drawable.other_ic_inventory, R.drawable.other_ic_1kk};
        final CustomListAdapter adapterImg = new CustomListAdapter(this, app_images, appsArray);
        listView.setAdapter(adapterImg);

        final AlertDialog.Builder otherAppsAlert = new AlertDialog.Builder(this);
        otherAppsAlert.setCancelable(true);
        otherAppsAlert.setTitle(getResources().getString(R.string.otherapps));
        otherAppsAlert.setView(listView);
        otherAppsAlert.setNegativeButton(getResources().getString(R.string.ok),
            new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.dismiss(); }
            });
        otherAppsAlert.show();
    }

    private class CustomListAdapter extends ArrayAdapter<String>
    {
        String  color_names[];
        Integer image_id[]   ;
        Context context      ;

        CustomListAdapter(final Activity context, final Integer image_id[], final String text[])
        {
            super(
                /* context  => */ context         ,
                /* resource => */ R.layout.appline,
                /* objects  => */ text            );
            this.color_names = text    ;
            this.image_id    = image_id;
            this.context     = context ;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent)
        {
            final LayoutInflater inflater =
                (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View single_row = inflater.inflate(R.layout.appline, null, true);
            final TextView textView = (TextView) single_row.findViewById(R.id.txt);
            final ImageView imageView = (ImageView) single_row.findViewById(R.id.img);
            textView.setText(this.color_names[position]);
            imageView.setImageResource(this.image_id[position]);
            return single_row;
        }
    }

    private void resetDatabase()
    {
        org.wheatgenetics.coordinate.utils.Utils.confirm(this,
            this.getString(org.wheatgenetics.coordinate.R.string.reset_database        ),
            this.getString(org.wheatgenetics.coordinate.R.string.reset_database_message),
            new java.lang.Runnable()
            {
                @Override
                public void run()
                {
                    org.wheatgenetics.coordinate.activities.Main.this.deleteDatabase(
                        "seedtray1.db");                                       // TODO: Encapsulate!
                    org.wheatgenetics.coordinate.database.Database.initialize(
                        org.wheatgenetics.coordinate.activities.Main.this);
                    org.wheatgenetics.coordinate.activities.Main.this.finish();
                }
            }, null);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        new MenuInflater(Main.this).inflate(R.menu.mainmenu, menu);
        menu.findItem(R.id.barcode_camera).setVisible(true);
        return true;
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(final View drawerView)
            {
                {
                    final TextView personTextView = (TextView) findViewById(R.id.nameLabel);
                    personTextView.setText(ep.getString("Person", ""));
                }

                {
                    final TextView templateTextView = (TextView) findViewById(R.id.templateLabel);
                    templateTextView.setText(
                        org.wheatgenetics.coordinate.activities.Main.this.templateTitle);  // model
                }
            }

            @Override
            public void onDrawerClosed(final View drawerView) {}
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupDrawerContent(final NavigationView navigationView)
    {
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(final MenuItem item)
                {
                    try
                    {
                        selectDrawerItem(item);                     // throws org.json.JSONException
                        return true;
                    }
                    catch (final org.json.JSONException e) { return false; }
                }
            });
    }

    private void selectDrawerItem(final MenuItem menuItem) throws org.json.JSONException
    {
        assert menuItem != null;
        switch (menuItem.getItemId())
        {
            case R.id.menu_new_grid:
                this.menuNewGrid();                                 // throws org.json.JSONException
                break;

            case R.id.menu_import:
                this.importGrid();
                break;

            case R.id.menu_export:
                if (this.templateTitle.equals(""))  // model
                    this.makeToast(getString(R.string.grid_empty));
                else
                    this.exportData();
                break;

            case R.id.menu_load_template:
                this.menuTemplateLoad();
                break;

            case R.id.menu_delete_grid:
                this.menuDeleteGrid();
                break;

            case R.id.menu_delete_template:
                this.menuDeleteTemplate();
                break;

            case R.id.menu_new_template:
                this.inputTemplateNew();
                break;

            case R.id.about:
                this.aboutDialog();
                break;

            // Keeping this for debugging purposes
            /*case R.id.reset_database:
                this.resetDatabase();
                break;

            case R.id.copy_database:
                this.copydb();
                break;*/
        }
        mDrawerLayout.closeDrawers();
    }

    private void copydb()
    {
        final File f = new File("/data/data/org.wheatgenetics.coordinate/databases/seedtray1.db");
        FileInputStream  fis = null;
        FileOutputStream fos = null;

        try
        {
            fis = new FileInputStream(f);                    // throws java.io.FileNotFoundException
            fos = new FileOutputStream("/mnt/sdcard/db_dump.db");  // throws java.io.FileNotFoundException
            while (true)
            {
                final int i = fis.read();                              // throws java.io.IOException
                if (i != -1)
                    fos.write(i);                                      // throws java.io.IOException
                else
                    break;
            }
            fos.flush();
            this.makeFileDiscoverable(new File("/mnt/sdcard/db_dump.db"), this);
            Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
        }
        catch (final java.io.IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch (final java.io.IOException ioe) {}
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item)) return true;

        assert item != null;
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.barcode_camera:
                this.barcodeScan();
                break;
        }

        return true;
    }

    private void barcodeScan()
    {
        final IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    private void makeToast(final String message)
    { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }

    private void menuNewGrid() throws org.json.JSONException
    {
        if (this.grid == 0)
            this.menuList();
        else
            if (this.grid >= 0 && mLastExportGridId == this.grid)
                this.newGridNow();                                  // throws org.json.JSONException
            else
                Utils.confirm(this, Coordinate.appName, this.getString(R.string.new_grid_warning),
                    new Runnable()
                    {
                        @Override
                        public void run() { exportData(); }
                    },
                    new Runnable()
                    {
                        @Override
                        public void run()
                        { try { newGridNow(); } catch (final org.json.JSONException e) {} }
                    });
    }

    private void newGridNow() throws org.json.JSONException
    {
        this.deleteGrid(this.grid);

        if (this.templateType == TYPE_SEED)  // model
        {
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.getByType(this.templateType))  // database, model
                    this.copyTemplate(templatesTable);              // throws org.json.JSONException
            }
            this.inputSeed();
        }
        else if (this.templateType == TYPE_DNA)  // model
        {
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.getByType(this.templateType))  // database, model
                    this.copyTemplate(templatesTable);              // throws org.json.JSONException
            }
            this.excludeCells.clear();
            this.excludeCells.add(new Point(this.randomBox(this.cols), this.randomBox(this.rows)));

            this.inputTemplateInput(MODE_DNA);                      // throws org.json.JSONException
        }
        else
        {
            // reset options?
            this.inputTemplateInput(MODE_SAVED);                    // throws org.json.JSONException
        }
    }

    private void menuDeleteGrid()
    {
        if (this.grid == 0) return;

        Utils.confirm(this, Coordinate.appName, getString(R.string.delete_grid_warning),
            new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        if (Main.this.deleteGrid(org.wheatgenetics.coordinate.activities.Main.this.grid))  // throw java.lang.Exception
                        {
                            Toast.makeText(Main.this, getString(R.string.grid_deleted), Toast.LENGTH_LONG).show();
                            org.wheatgenetics.coordinate.activities.Main.this.grid = 0;
                            mLayoutOptional.setVisibility(View.INVISIBLE);
                            mLayoutGrid.setVisibility(View.INVISIBLE);
                            menuList();
                        }
                        else
                            Toast.makeText(Main.this, getString(R.string.grid_not_deleted), Toast.LENGTH_LONG).show();
                    }
                    catch (final java.lang.Exception e) {}
                }
            }, null);
    }

    @Override
    public boolean onKey(final View v, final int keyCode, final KeyEvent event)
    {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
        {
            this.saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            this.saveData();
            return true;
        }
        else
            if (event != null)
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                {
                    this.saveData();
                    return true;
                }
        return false;
    }

    //TODO don't toggle already selected cell
    @Override
    public void onClick(final View v)  // v is a cell
    {
        int    c = -1;
        int    r = -1;
        Object obj;

        obj = v.getTag(R.string.cell_col);
        if (obj instanceof Integer) c = (Integer) obj;

        obj = v.getTag(R.string.cell_row);
        if (obj instanceof Integer) r = (Integer) obj;

        if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c))
        {
            mEditData.setText("");
            return;
        }

        if (c != -1 && r != -1)
        {
            mCurRow = r;
            mCurCol = c;

            String data = getDataEntry(this.grid, mCurRow, mCurCol);

            if (data != null && data.contains("exclude")) return;

            setCellState(v, STATE_ACTIVE);

            if (data == null) data = "";

            mEditData.setSelectAllOnFocus(true);
            mEditData.setText(data);
            mEditData.selectAll();
            mEditData.requestFocus();
        }

        resetCurrentCell();
        curCell = v;
    }

    // Adds default templates to database
    private void initDb() throws org.json.JSONException  // model
    {
        this.templateTitle = "Seed Tray";
        this.templateType  = TYPE_SEED ;
        this.rows          = 6         ;
        this.cols          = 20        ;
        this.rowNumbering  = true      ;
        this.colNumbering  = true      ;

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Tray"  , /* hint => */ "Tray ID"    );
        this.nonNullOptionalFields.add    ("Person", /* hint => */ "Person name");
        this.nonNullOptionalFields.addDate("Date"                               );

        this.excludeRows = new ArrayList<>();
        this.excludeRows.add(2);
        this.excludeRows.add(5);

        this.excludeCols  = new ArrayList<>();
        this.excludeCells = new ArrayList<>();

        this.createDb(this.templateType);                           // throws org.json.JSONException

        this.templateTitle = "DNA Plate";
        this.rows          = 8          ;
        this.cols          = 12         ;
        this.templateType  = TYPE_DNA   ;
        this.rowNumbering  = true       ;
        this.colNumbering  = false      ;

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add("Plate", /* hint => */ "Plate ID"                     ); // TODO dna
        this.nonNullOptionalFields.add("Plate Name"                                          );
        this.nonNullOptionalFields.add("Notes"                                               );
        this.nonNullOptionalFields.add("tissue_type", /* value => */ "Leaf", /* hint => */ "");
        this.nonNullOptionalFields.add("extraction" , /* value => */ "CTAB", /* hint => */ "");
        this.nonNullOptionalFields.add("person"                                              );
        this.nonNullOptionalFields.add("date"                                                );

        this.excludeRows = new ArrayList<>();
        this.excludeCols = new ArrayList<>();

        this.excludeCells = new ArrayList<>();

        this.createDb(this.templateType);                           // throws org.json.JSONException

        this.templateTitle = "";
    }

    private boolean createDb(final int type) throws org.json.JSONException  // database, model
    {
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);

        templatesTable.title = this.templateTitle;
        templatesTable.type  = this.templateType ;
        templatesTable.rows  = this.rows         ;
        templatesTable.cols  = this.cols         ;

        templatesTable.excludeCells = Utils.pointListToJson(this.excludeCells); // throws org.json.JSONException
        templatesTable.excludeCols  = Utils.integerListToJson(this.excludeCols);
        templatesTable.excludeRows  = Utils.integerListToJson(this.excludeRows);

        templatesTable.options = this.nonNullOptionalFields.toJson();     // throws org.json.JSONException

        templatesTable.colNumbering = this.colNumbering ? 1 : 0;
        templatesTable.rowNumbering = this.rowNumbering ? 1 : 0;

        templatesTable.stamp = System.currentTimeMillis();

        if (templatesTable.getByType(type))
            return templatesTable.update();
        else
            return templatesTable.insert() > 0;
    }

    private void menuList()
    {
        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.template_options));
        builder.setItems(menuMain, new OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { if (which == 0) menuTemplateLoad(); else inputTemplateNew(); }
            });
        builder.show();
    }

    private void menuTemplateLoad()
    {
        final List<org.wheatgenetics.coordinate.database.TemplatesTable> templates = new ArrayList<>();

        final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        final Cursor cursor = tmp.load();  // database
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.copy(cursor)) templates.add(templatesTable);  // database, model
            }
            cursor.close();
        }

        final int    size    = templates.size();
        final String items[] = new String[size];
        for (int i = 0; i < size; i++)
        {
            final org.wheatgenetics.coordinate.database.TemplatesTable item = templates.get(i);
            items[i] = item.title;  // model
        }

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(menuMain[0]);
        builder.setItems(items, new OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    if (which < 0 || which > templates.size()) return;

                    final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
                        templates.get(which);
                    try { copyTemplate(tmp); } catch (org.json.JSONException e) {}

                    if (which == 0)
                        inputSeed();
                    else if (which == 1)
                    {
                        org.wheatgenetics.coordinate.activities.Main.this.excludeCells.clear();
                        org.wheatgenetics.coordinate.activities.Main.this.excludeCells.add(
                            new Point(
                                randomBox(org.wheatgenetics.coordinate.activities.Main.this.cols),
                                randomBox(org.wheatgenetics.coordinate.activities.Main.this.rows)));

                        try { inputTemplateInput(MODE_DNA); } catch (final java.lang.Exception e) {}
                    }
                    else
                        try { inputTemplateInput(MODE_SAVED); }
                        catch (final java.lang.Exception e) {}
                }
            });
        builder.show();
    }

    private void menuDeleteTemplate()
    {
        final List<org.wheatgenetics.coordinate.database.TemplatesTable> templates =
            new ArrayList<>();

        final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        final Cursor cursor = tmp.load();  // database
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                    new org.wheatgenetics.coordinate.database.TemplatesTable(this);
                if (templatesTable.copy(cursor)) templates.add(templatesTable);  // database, model
            }
            cursor.close();
        }

        final int    size    = templates.size();
        final String items[] = new String[size];

        for (int i = 0; i < size; i++)
        {
            final org.wheatgenetics.coordinate.database.TemplatesTable item = templates.get(i);
            items[i] = item.title;  // model
        }

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_template));
        builder.setItems(items, new OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    if (which < 0 || which > templates.size()) return;

                    final org.wheatgenetics.coordinate.database.TemplatesTable tmp =
                        templates.get(which);

                    Utils.confirm(Main.this, getString(R.string.delete_template),
                        getString(R.string.delete_template_warning), new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    if (deleteTemplate(tmp.id))  // throws java.lang.Exception
                                    {
                                        Toast.makeText(Main.this,
                                            getString(R.string.template_deleted),
                                            Toast.LENGTH_LONG).show();
                                        menuList();
                                    }
                                    else
                                        Toast.makeText(Main.this,
                                            getString(R.string.template_not_deleted),
                                            Toast.LENGTH_LONG).show();
                                }
                                catch (final java.lang.Exception e) {}
                            }
                        }, null);
                }
            });
        builder.show();
    }

    private void copyTemplate(
    final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable)  // model
    throws org.json.JSONException
    {
        this.id            = templatesTable.id   ;
        this.templateTitle = templatesTable.title;
        this.templateType  = templatesTable.type ;
        this.rows          = templatesTable.rows ;
        this.cols          = templatesTable.cols ;

        this.excludeCells = Utils.jsonToPointList(templatesTable.excludeCells);  // throws org.json.JSONException

        this.excludeCols = Utils.jsonToIntegerList(templatesTable.excludeCols);  // throws org.json.JSONException
        this.excludeRows = Utils.jsonToIntegerList(templatesTable.excludeRows);  // throws org.json.JSONException

        this.nonNullOptionalFields = new NonNullOptionalFields(templatesTable.options); // throws org.json.JSONException

        this.rowNumbering = templatesTable.rowNumbering == 1;
        this.colNumbering = templatesTable.colNumbering == 1;
    }

    private void inputTemplateNew()
    {
        this.excludeCells = new ArrayList<>();
        this.excludeRows  = new ArrayList<>();
        this.excludeCols  = new ArrayList<>();

        this.nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        this.nonNullOptionalFields.add    ("Identification");
        this.nonNullOptionalFields.add    ("Person"        );
        this.nonNullOptionalFields.addDate("Date"          );

        final LayoutInflater inflater = Main.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.template_new, new LinearLayout(this), false);

        final EditText nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        final EditText rowsEdit = (EditText) view.findViewById(R.id.rowsEdit);
        final EditText colsEdit = (EditText) view.findViewById(R.id.colsEdit);

        nameEdit.setText("");
        rowsEdit.setText(this.rows <= 0 ? "" : String.valueOf(this.rows));
        colsEdit.setText(this.cols <= 0 ? "" : String.valueOf(this.cols));

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(menuMain[1]);
        builder.setView(view);
        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    dialog.cancel();

                    final String sname = nameEdit.getText().toString().trim();
                    final String scols = colsEdit.getText().toString().trim();
                    final String srows = rowsEdit.getText().toString().trim();

                    org.wheatgenetics.coordinate.activities.Main.this.templateTitle = sname;           // model
                    org.wheatgenetics.coordinate.activities.Main.this.rows = Utils.getInteger(srows);  // model
                    org.wheatgenetics.coordinate.activities.Main.this.cols = Utils.getInteger(scols);  // model

                    if (sname.length() == 0)
                    {
                        Toast.makeText(Main.this, getString(R.string.template_no_name),
                            Toast.LENGTH_LONG).show();
                        inputTemplateNew();
                        return;
                    }

                    if (srows.length() == 0 || org.wheatgenetics.coordinate.activities.Main.this.rows == -1)
                    {
                        Toast.makeText(Main.this, getString(R.string.no_rows),
                            Toast.LENGTH_LONG).show();
                        inputTemplateNew();
                        return;
                    }

                    if (scols.length() == 0 || org.wheatgenetics.coordinate.activities.Main.this.cols == -1)
                    {
                        Toast.makeText(Main.this, getString(R.string.no_cols),
                            Toast.LENGTH_LONG).show();
                        inputTemplateNew();
                        return;
                    }

                    inputTemplateNewExtra();
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(final DialogInterface dialog, final int which)
            { dialog.cancel(); }
        });

        builder.show();
    }

    private void inputTemplateNewExtra()
    {
        final LayoutInflater inflater = Main.this.getLayoutInflater();
        final View view =
            inflater.inflate(R.layout.template_new_extra, new LinearLayout(this), false);

        final Button optionalButton = (Button) view.findViewById(R.id.optionalButton);
        final Button excludeButton  = (Button) view.findViewById(R.id.excludeButton );
        final Button namingButton   = (Button) view.findViewById(R.id.namingButton  );

        optionalButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v) { inputOptional(); }
            });

        excludeButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v) { inputExclude(); }
            });

        namingButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v) { inputNaming(); }
            });

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.template_new);
        builder.setView(view);
        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    dialog.cancel();
                    try { inputTemplateInput(MODE_DEFAULT); } catch (final java.lang.Exception e) {}
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputSeed()
    {
        final LayoutInflater layoutInflater = Main.this.getLayoutInflater();

        assert layoutInflater != null;
        final View gridView =
            layoutInflater.inflate(R.layout.grid_new, new LinearLayout(this), false);

        assert gridView != null;
        final LinearLayout linearLayout = (LinearLayout) gridView.findViewById(R.id.optionalLayout);

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();

        final ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();

        // load options
        assert linearLayout != null;
        for (final OptionalField optionalField: checkedOptionalFields)
        {
            final View optionalFieldView =
                layoutInflater.inflate(R.layout.optional_edit, linearLayout, false);

            {
                assert optionalFieldView != null;
                final TextView optionalFieldTextView =
                    (TextView) optionalFieldView.findViewById(R.id.optionText);

                assert optionalFieldTextView != null;
                optionalFieldTextView.setText(optionalField.getName());
            }

            {
                final EditText optionalFieldEditText =
                    (EditText) optionalFieldView.findViewById(R.id.optionEdit);

                assert optionalFieldEditText != null;
                optionalFieldEditText.setText(optionalField.getValue());
                optionalFieldEditText.setHint(optionalField.getHint ());

                editTextArrayList.add(optionalFieldEditText);
            }

            linearLayout.addView(optionalFieldView);
        }

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.templateTitle);
        builder.setView(gridView);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                          int      i               = 0;
                    final EditText editTextArray[] =
                        editTextArrayList.toArray(new EditText[editTextArrayList.size()]);

                    assert nonNullOptionalFields != null;
                    for (final OptionalField optionalField: nonNullOptionalFields) // Danger: CheckedOptionalFields above but nonNullOptionalFields here.
                    {
                        final EditText editText = editTextArray[i];
                        if (editText != null)
                        {
                            final String value = editText.getText().toString().trim();
                            if (i == 0 && value.length() == 0)
                            {
                                Utils.toast(Main.this,
                                    optionalField.getHint() + getString(R.string.not_empty));
                                inputSeed();
                                return;
                            }

                            optionalField.setValue(value);

                            if (optionalField.nameEqualsIgnoreCase("Person")
                            ||  optionalField.nameEqualsIgnoreCase("Name"  ))
                            {
                                final SharedPreferences.Editor ed = ep.edit();
                                ed.putString("Person", optionalField.getValue());
                                ed.apply();
                            }
                        }
                        i++;
                    }

                    assert dialog != null;
                    dialog.cancel();
                    loadTemplate(TYPE_SEED);
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        final AlertDialog dlg = builder.create();
        assert dlg != null;
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dlg.setCancelable(false);
        dlg.show();
    }

    private void tempLoad(final int mode) throws org.json.JSONException
    {
        if (mode == MODE_DNA)
            this.loadTemplate(TYPE_DNA);
        else if (mode == MODE_SAVED)
            this.loadTemplate(TYPE_DEFAULT);
        else
            this.newTemplate(TYPE_DEFAULT);                         // throws org.json.JSONException
    }

    //TODO merge this method with the one above
    private void inputTemplateInput(final int mode) throws org.json.JSONException
    {
        assert this.nonNullOptionalFields != null;
        if (this.nonNullOptionalFields.isEmpty())
        {
            this.tempLoad(mode);                                             // throws JSONException
            return;
        }

        final LayoutInflater layoutInflater = Main.this.getLayoutInflater();

        assert layoutInflater != null;
        final View gridView =
            layoutInflater.inflate(R.layout.grid_new, new LinearLayout(this), false);

        assert gridView != null;
        final LinearLayout linearLayout = (LinearLayout) gridView.findViewById(R.id.optionalLayout);

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields =
            this.makeCheckedOptionalFields();

        final ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();

        // load options
        assert linearLayout != null;
        for (final OptionalField optionalField: checkedOptionalFields)
        {
            final View optionalFieldView =
                layoutInflater.inflate(R.layout.optional_edit, linearLayout, false);

            {
                assert optionalFieldView != null;
                final TextView optionalFieldTextView =
                        (TextView) optionalFieldView.findViewById(R.id.optionText);

                assert optionalFieldTextView != null;
                optionalFieldTextView.setText(optionalField.getName());
            }

            {
                final EditText optionalFieldEditText =
                        (EditText) optionalFieldView.findViewById(R.id.optionEdit);

                assert optionalFieldEditText != null;
                optionalFieldEditText.setText(optionalField.getValue());
                optionalFieldEditText.setHint(optionalField.getHint());

                editTextArrayList.add(optionalFieldEditText);
            }

            linearLayout.addView(optionalFieldView);
        }

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.templateTitle);  // model
        builder.setView(gridView);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.create), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                          int      i               = 0;
                    final EditText editTextArray[] =
                        editTextArrayList.toArray(new EditText[editTextArrayList.size()]);

                    assert nonNullOptionalFields != null;
                    for (final OptionalField optionalField: nonNullOptionalFields)
                    {
                        final EditText editText = editTextArray[i];
                        if (editText != null)
                        {
                            if (mode == MODE_DNA)
                            {
                                final String value = editText.getText().toString().trim();
                                if (i == 0 && value.length() == 0)
                                {
                                    Utils.toast(Main.this,
                                        optionalField.getHint() + getString(R.string.not_empty));
                                    try { inputTemplateInput(MODE_DNA); }
                                    catch (final java.lang.Exception e) {}
                                    return;
                                }
                            }

                            optionalField.setValue(editText.getText().toString().trim());

                            if (optionalField.nameEqualsIgnoreCase("Person")
                            ||  optionalField.nameEqualsIgnoreCase("Name"  ))
                            {
                                final SharedPreferences.Editor ed = ep.edit();
                                ed.putString("Person", optionalField.getValue());
                                ed.apply();
                            }
                        }
                        i++;
                    }

                    assert dialog != null;
                    dialog.cancel();
                    try { tempLoad(mode); } catch (final java.lang.Exception e) {}
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        final AlertDialog dlg = builder.create();
        assert dlg != null;
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputOptional()
    {
        final ArrayList<String > itemArrayList      = new ArrayList<String >();
        final ArrayList<Boolean> selectionArrayList = new ArrayList<Boolean>();

        assert this.nonNullOptionalFields != null;
        for (final OptionalField optionalField: this.nonNullOptionalFields)
        {
            itemArrayList.add     (optionalField.getName   ());
            selectionArrayList.add(optionalField.getChecked());
        }


        final String itemArray[] = itemArrayList.toArray(new String[itemArrayList.size()]);

        final int     selectionArrayListSize = selectionArrayList.size();
        final boolean selectionArray[]       = new boolean[selectionArrayListSize];

        for (int i = 0; i < selectionArrayListSize; i++)
            selectionArray[i] = selectionArrayList.get(i);

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.optional_fields));
        builder.setMultiChoiceItems(itemArray, selectionArray, new OnMultiChoiceClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which,
                final boolean isChecked)
                {
                    assert nonNullOptionalFields != null;
                    nonNullOptionalFields.get(which).setChecked(isChecked);
                }
            });

        builder.setNeutralButton(getString(R.string.add_new), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    dialog.cancel();
                    inputOptionalNew("", "");
                }
            });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputOptionalNew(final String field, final String value)
    {
        final LayoutInflater layoutInflater = Main.this.getLayoutInflater();

        assert layoutInflater != null;
        final View view = layoutInflater.inflate(R.layout.optional_new, null);

        final EditText fieldEdit = (EditText) view.findViewById(R.id.fieldEdit);
        final EditText valueEdit = (EditText) view.findViewById(R.id.valueEdit);

        fieldEdit.setText(field);
        valueEdit.setText(value);

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.new_optional_field));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    final String sfield = fieldEdit.getText().toString().trim();
                    final String svalue = valueEdit.getText().toString().trim();

                    if (sfield.length() == 0)
                    {
                        Toast.makeText(Main.this, getString(R.string.new_optional_field_no_name),
                            Toast.LENGTH_LONG).show();
                        inputOptionalNew(field, value);
                        return;
                    }
                    dialog.cancel();

                    assert nonNullOptionalFields != null;
                    nonNullOptionalFields.add(sfield, /* value => */ svalue, /* hint => */ "");

                    inputOptional();
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        final AlertDialog dlg = builder.create();
        assert dlg != null;
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputExclude()
    {
        final String[] items = {getString(R.string.rows), getString(R.string.cols), getString(R.string.random)};

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exclude_title));
        builder.setItems(items, new OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    if (which == 0)
                        inputExcludeBoxes(0);
                    else if (which == 1)
                        inputExcludeBoxes(1);
                    else if (which == 2)
                        inputExcludeInput();
                }
            });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputExcludeBoxes(final int type)
    {
        final int     total        = type == 0 ? this.rows : this.cols;
        final boolean selections[] = new boolean[total];
        final String  items[]      = new String[total];

        for (int i = 0; i < total; i++)
        {
            items[i] = String.format("%s %d", (type == 0 ? getString(R.string.row) : getString(R.string.col)), i + 1);
            selections[i] = (type == 0 ? this.excludeRows.contains(Integer.valueOf(i + 1)) : this.excludeCols.contains(Integer.valueOf(i + 1)));
        }

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exclude_title) + " - " + (type == 0 ? getString(R.string.rows) : getString(R.string.cols)));
        builder.setMultiChoiceItems(items, selections, new OnMultiChoiceClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which,
                final boolean isChecked) { selections[which] = isChecked; }
            });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    // get choices
                    for (int i = 0; i < total; i++) if (selections[i])
                        if (type == 0)
                            org.wheatgenetics.coordinate.activities.Main.this.excludeRows.add(i + 1);
                        else
                            org.wheatgenetics.coordinate.activities.Main.this.excludeCols.add(i + 1);
                }
            });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.show();
    }

    private void inputExcludeInput()
    {
        final LayoutInflater layoutInflater = Main.this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.random, null);

        final EditText cellsEdit = (EditText) view.findViewById(R.id.cellsEdit);

        cellsEdit.setText("1");

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.random));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    final String str = cellsEdit.getText().toString();

                    org.wheatgenetics.coordinate.activities.Main.this.excludeCells =
                        new ArrayList<>();

                    final int cells = Utils.getInteger(str);
                    if (cells > 0)
                        for (int i = 0; i < cells; i++)
                        {
                            final Point point = new Point(
                                randomBox(org.wheatgenetics.coordinate.activities.Main.this.cols),
                                randomBox(org.wheatgenetics.coordinate.activities.Main.this.rows));
                            org.wheatgenetics.coordinate.activities.Main.this.excludeCells.add(point); // FIXME check exclusivity
                        }
                    else inputExcludeInput();

                    dialog.cancel();
                }
            });

        final AlertDialog dlg = builder.create();
        assert dlg != null;
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputNaming()
    {
        final LayoutInflater layoutInflater = Main.this.getLayoutInflater();
        final View           view           = layoutInflater.inflate(R.layout.naming, null);

        final Spinner rowSpinner = (Spinner) view.findViewById(R.id.rowSpinner);
        final Spinner colSpinner = (Spinner) view.findViewById(R.id.colSpinner);

        rowSpinner.setSelection(this.rowNumbering ? 0 : 1);
        colSpinner.setSelection(this.colNumbering ? 0 : 1);

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.naming));
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.activities.Main.this.rowNumbering = (rowSpinner.getSelectedItemPosition() == 0);
                    org.wheatgenetics.coordinate.activities.Main.this.colNumbering = (colSpinner.getSelectedItemPosition() == 0);

                    dialog.cancel();
                }
            });

        final AlertDialog dlg = builder.create();
        assert dlg != null;
        dlg.setCancelable(false);
        dlg.show();
    }

    private void importGrid()
    {
        String names[]   = new String[1];
        long   indexes[] = new long  [1];

        int pos = 0;

        final GridsTable gridsTable = new GridsTable(this);
        final Cursor     gridCursor = gridsTable.getAllGrids();
        if (gridCursor != null)
        {
            final int size = gridCursor.getCount();

            names   = new String[size];
            indexes = new long  [size];

            while (gridCursor.moveToNext())
            {
                final GridsTable tmpG = new GridsTable(this);
                if (tmpG.copyAll(gridCursor))
                {
                    names[pos] = String.format(
                        "Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", tmpG.title,
                        tmpG.templateTitle, tmpG.cols, tmpG.rows, Utils.formatDate(tmpG.stamp));
                    indexes[pos++] = tmpG.id;
                }
            }
            gridCursor.close();
        }

        if (pos == 0)
        {
            Utils.alert(this, Coordinate.appName, getString(R.string.no_templates));
            return;
        }

        final long[] gridIds = indexes;

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.import_grid));
        builder.setItems(names, new OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    if (which < gridIds.length)
                    {
                        final long id = gridIds[which];

                        final SharedPreferences.Editor ed = ep.edit();
                        ed.putLong("CurrentGrid", id);
                        ed.apply();

                        try
                        {
                            final GridsTable grd = new GridsTable(Main.this);
                            if (grd.get(id))
                            {
                                org.wheatgenetics.coordinate.activities.Main.this.templateTitle =  // model
                                    grd.templateTitle;
                                org.wheatgenetics.coordinate.activities.Main.this.grid = grd.id;
                                mGridTitle = grd.title;
                                org.wheatgenetics.coordinate.activities.Main.this.templateType =  // model
                                    grd.templateType;
                                org.wheatgenetics.coordinate.activities.Main.this.rows = grd.rows;  // model
                                org.wheatgenetics.coordinate.activities.Main.this.cols = grd.cols;  // model

                                final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                                    new org.wheatgenetics.coordinate.database.TemplatesTable(Main.this);

                                if (templatesTable.get(grd.templateId))  // database
                                {
                                    try
                                    {
                                        nonNullOptionalFields =
                                            new NonNullOptionalFields(templatesTable.options);  // model
                                    }
                                    catch (final JSONException e) {}

                                    org.wheatgenetics.coordinate.activities.Main.this.rowNumbering = templatesTable.rowNumbering == 1;  // model
                                    org.wheatgenetics.coordinate.activities.Main.this.colNumbering = templatesTable.colNumbering == 1;  // model
                                }

                                org.wheatgenetics.coordinate.activities.Main.this.excludeCells.clear();  // model
                                org.wheatgenetics.coordinate.activities.Main.this.excludeRows.clear();   // model
                                org.wheatgenetics.coordinate.activities.Main.this.excludeCols.clear();   // model

                                populateTemplate();
                                showTemplateUI();
                            }
                            else
                                Utils.alert(Main.this, Coordinate.appName, getString(R.string.import_grid_failed));
                        }
                        catch (final java.lang.Exception e) {}
                        dialog.cancel();
                    }
                }
            });
        builder.show();
    }

    private void loadGrid(final long id) throws org.json.JSONException
    {
        final GridsTable grd = new GridsTable(this);
        if (grd.get(id))
        {
            this.templateTitle = grd.templateTitle;  // model
            this.grid          = grd.id           ;
            mGridTitle = grd.title;
            this.templateType = grd.templateType;  // model
            this.rows         = grd.rows        ;  // model
            this.cols         = grd.cols        ;  // model

            this.excludeCells.clear();  // model
            this.excludeRows.clear();   // model
            this.excludeCols.clear();   // model

            org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
                new org.wheatgenetics.coordinate.database.TemplatesTable(this);

            if (templatesTable.get(grd.templateId))  // database
            {
                this.nonNullOptionalFields = new NonNullOptionalFields(templatesTable.options); // throws org.json.JSONException, model

                this.rowNumbering = templatesTable.rowNumbering == 1;  // model
                this.colNumbering = templatesTable.colNumbering == 1;  // model
            }

            populateTemplate();
            showTemplateUI();
        }
        else Utils.alert(Main.this, Coordinate.appName, getString(R.string.import_grid_failed));
    }

    private boolean isExcludedCell(final int r, final int c)
    {
        for (int i = 0; i < this.excludeCells.size(); i++)
        {
            final Point point = this.excludeCells.get(i);
            if (point.equals(c, r)) return true;
        }
        return false;
    }

    private boolean isExcludedRow(final int r)
    { return this.excludeRows.contains(Integer.valueOf(r)); }

    private boolean isExcludedCol(final int c)
    { return this.excludeCols.contains(Integer.valueOf(c)); }

    private int randomBox(final int size)
    {
        final Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        return rand.nextInt(size - 1) + 1;
    }

    private void saveData()
    {
        View view;

        String data = mEditData.getText().toString().trim();

        boolean ret;

        final EntriesTable entriesTable = new EntriesTable(this);
        if (entriesTable.getByGrid(this.grid, mCurRow, mCurCol))
        {
            entriesTable.value = data;
            ret = entriesTable.update();
        }
        else
        {
            entriesTable.grid = this.grid;
            entriesTable.row = mCurRow;
            entriesTable.col = mCurCol;
            entriesTable.value = data;
            ret = entriesTable.insert() > 0;
        }

        if (!ret)
        {
            Toast.makeText(Main.this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        view = mTableData.findViewWithTag(Utils.getTag(mCurRow, mCurCol));

        if (view != null)
            if (data.length() != 0)
                setCellState(view, STATE_DONE);
            else
                setCellState(view, STATE_NORMAL);

        boolean endOfCell = false;

        mCurRow++;
        if (mCurRow > this.rows || (mCurRow == this.rows && (isExcludedRow(this.rows) || isExcludedCell(this.rows, mCurCol))))
        {
            mCurRow = this.rows;

            mCurCol++;
            if (mCurCol > this.cols || (mCurCol == this.cols && (isExcludedCol(this.cols) || isExcludedCell(mCurRow, this.cols))))
            {
                mCurCol = this.cols;
                mCurCol = this.rows;

                endOfCell = true;
            }
            else mCurRow = 1;
        }

        if (!endOfCell)
            if (isExcludedRow(mCurRow) || isExcludedCol(mCurCol) || isExcludedCell(mCurRow, mCurCol))
                if (!getNextFreeCell()) endOfCell = true;

        data = getDataEntry(this.grid, mCurRow, mCurCol);

        if (data == null) data = "";

        mEditData.setSelectAllOnFocus(true);
        mEditData.setText(data);
        mEditData.selectAll();
        mEditData.requestFocus();

        view = mTableData.findViewWithTag(Utils.getTag(mCurRow, mCurCol));
        if (view != null)
            if (!isExcludedRow(mCurRow) && !isExcludedCol(mCurCol) && !isExcludedCell(mCurRow, mCurCol))
                setCellState(view, STATE_ACTIVE);

        resetCurrentCell();
        curCell = view;

        if (endOfCell)
        {
            Utils.alert(this, Coordinate.appName, getString(R.string.grid_filled));
            completeSound();
        }
    }

    private void completeSound()
    {
        try
        {
            final int resID = getResources().getIdentifier("plonk", "raw", getPackageName());
            final MediaPlayer chimePlayer = MediaPlayer.create(Main.this, resID);
            chimePlayer.start();

            chimePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(final MediaPlayer mp) { mp.release(); }
                });
        }
        catch (final java.lang.Exception e) { Log.e(TAG, "" + e.getMessage()); }
    }

    private void resetCurrentCell()
    {
        if (curCell != null)
        {
            int r = -1;
            int c = -1;

            Object obj;

            obj = curCell.getTag(R.string.cell_col);
            if (obj instanceof Integer) c = (Integer) obj;

            obj = curCell.getTag(R.string.cell_row);
            if (obj instanceof Integer) r = (Integer) obj;

            if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c))
                setCellState(curCell, STATE_INACTIVE);
            else
            {
                String data = getDataEntry(this.grid, r, c);
                if (data == null) data = "";

                if (data.length() == 0)
                    setCellState(curCell, STATE_NORMAL);
                else
                    setCellState(curCell, STATE_DONE);
            }
        }
    }

    private boolean deleteTemplate(final long id)
    {
        boolean ret;
        org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        if (!templatesTable.get(id)) return false;  // database

        if (templatesTable.type == TYPE_SEED || templatesTable.type == TYPE_DNA)  // model
        {
            makeToast(getString(R.string.template_not_deleted_default));
            return false;
        }

        final GridsTable gridsTable = new GridsTable(this);
        final Cursor     cursor     = gridsTable.loadByTemplate(id);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                final GridsTable g = new GridsTable(this);
                if (g.copy(cursor)) this.deleteGrid(g.id);
            }
            cursor.close();
        }

        ret = templatesTable.delete(id);  // database
        return ret;
    }

    private boolean deleteGrid(final long id)
    {
        boolean ret;

        final GridsTable gridsTable = new GridsTable(this);
        ret = gridsTable.delete(id);

        final EntriesTable entriesTable = new EntriesTable(this);
        entriesTable.deleteByGrid(id);

        return ret;
    }

    private long createGrid(final long template)
    {
        final GridsTable gridsTable = new GridsTable(this);
        gridsTable.template = template;
        gridsTable.stamp = System.currentTimeMillis();
        assert this.nonNullOptionalFields != null;
        gridsTable.title = this.nonNullOptionalFields.get(0).getValue();
        return gridsTable.insert();
    }

    private void newTemplate(final int templateType) throws org.json.JSONException
    {
        this.templateType = templateType;  // model
        final org.wheatgenetics.coordinate.database.TemplatesTable templatesTable =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this);
        templatesTable.title = this.templateTitle;  // model
        templatesTable.type  = templateType;        // model
        templatesTable.cols  = this.cols;           // model
        templatesTable.rows  = this.rows;           // model

        templatesTable.excludeCells = Utils.pointListToJson(this.excludeCells);  // throws org.json.JSONException, model
        templatesTable.excludeCols = Utils.integerListToJson(this.excludeCols);  // model
        templatesTable.excludeRows = Utils.integerListToJson(this.excludeRows);  // model

        templatesTable.options = this.nonNullOptionalFields.toJson();     // throws org.json.JSONException, model

        templatesTable.colNumbering = this.colNumbering ? 1 : 0;  // model
        templatesTable.rowNumbering = this.rowNumbering ? 1 : 0;  // model

        templatesTable.stamp = System.currentTimeMillis();  // model

        final long id = templatesTable.insert();  // database
        if (id > 0)
        {
            // deleteTemplate(this.id); //TODO

            this.id = id;

            final long grid = this.createGrid(this.id);
            if (grid > 0)
            {
                this.grid = grid;

                final SharedPreferences.Editor ed = this.ep.edit();
                ed.putLong("CurrentGrid", grid);
                ed.apply();

                this.populateTemplate();
                this.showTemplateUI();
            }
            else Utils.alert(this, Coordinate.appName, getString(R.string.create_grid_fail));
        }
        else Utils.alert(this, Coordinate.appName, getString(R.string.create_template_fail));
    }

    private void loadTemplate(final int templateType)
    {
        this.templateType = templateType;  // model

        final long grid = createGrid(this.id);
        if (grid > 0)
        {
            this.grid = grid;

            final SharedPreferences.Editor ed = ep.edit();
            ed.putLong("CurrentGrid", grid);
            ed.apply();

            mLayoutOptional.setVisibility(View.VISIBLE);
            mLayoutGrid.setVisibility(View.VISIBLE);

            populateTemplate();
            showTemplateUI();
        }
        else Utils.alert(this, Coordinate.appName, getString(R.string.create_grid_fail));

    }

    private void showTemplateUI()
    {
        this.templateTextView.setText(this.templateTitle);  // model

        mLayoutMain.setVisibility(View.VISIBLE);

        final java.lang.String data = this.getDataEntry(this.grid, 1, 1);
        mEditData.setText(data == null ? "" : data);
    }

    // first non excluded cell
    private boolean getNextFreeCell()
    {
        int c;
        int r = 1;

        for (c = mCurCol; c <= this.cols; c++)
        {
            if (isExcludedCol(c)) continue;

            for (r = mCurRow; r <= this.rows; r++)
            {
                if (isExcludedRow(r)) continue;
                if (!isExcludedCell(r, c)) break;
            }
            break;
        }

        mCurRow = r;
        mCurCol = c;
        return true;
    }

    private void populateTemplate()
    {
        this.mCurRow = 1;
        this.mCurCol = 1;

        this.getNextFreeCell();

        final LayoutInflater layoutInflater = this.getLayoutInflater();

        this.mLayoutOptional.removeAllViews();

        final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
            checkedOptionalFields = this.makeCheckedOptionalFields();
        boolean first = true;
        for (final OptionalField optionalField: checkedOptionalFields)
        {
            final View view =
                layoutInflater.inflate(R.layout.optional_line, new LinearLayout(this), false);
            {
                assert view != null;
                final TextView fieldText = (TextView) view.findViewById(R.id.fieldText);
                assert fieldText != null;
                fieldText.setText(optionalField.getName());
            }
            {
                final TextView valueText = (TextView) view.findViewById(R.id.valueText);
                assert valueText != null;
                if (first)
                {
                    valueText.setText(this.mGridTitle);
                    first = false;
                }
                else valueText.setText(optionalField.getValue());
            }
            this.mLayoutOptional.addView(view);
        }
        this.mTableData.removeAllViews();

        // header
        @SuppressLint("InflateParams")
        final TableRow hrow = (TableRow) layoutInflater.inflate(R.layout.table_row, null);
        int chcol = 0;
        for (int c = 0; c < (this.cols + 1); c++)
        {
            @SuppressLint("InflateParams")
            final LinearLayout cell_top = (LinearLayout) layoutInflater.inflate(R.layout.table_cell_top, null);
            final TextView cell_txt = (TextView) cell_top.findViewById(R.id.dataCell);

            if (c == 0)
                cell_txt.setText("");
            else
            {
                cell_txt.setText("" + (this.colNumbering ? c : Character.toString((char) ('A' + chcol)))); // Row
                chcol++;
                if (chcol >= 26) chcol = 0;
            }
            hrow.addView(cell_top);
        }
        this.mTableData.addView(hrow);

        // body
        int chrow = 0;
        for (int r = 1; r < (this.rows + 1); r++)
        {
            @SuppressLint("InflateParams")
            final TableRow brow = (TableRow) layoutInflater.inflate(R.layout.table_row, null);

            final boolean excludedRow = isExcludedRow(r);

            for (int c = 0; c < (this.cols + 1); c++)
            {
                final boolean excludedCol = isExcludedCol(c);

                @SuppressLint("InflateParams")
                final LinearLayout cell_box = (LinearLayout) layoutInflater.inflate(R.layout.table_cell_box, null);
                final TextView cell_cnt = (TextView) cell_box.findViewById(R.id.dataCell);

                @SuppressLint("InflateParams")
                final LinearLayout cell_left = (LinearLayout) layoutInflater.inflate(R.layout.table_cell_left, null);
                final TextView cell_num = (TextView) cell_left.findViewById(R.id.dataCell);

                if (c == 0)
                {
                    cell_num.setText("" + (this.rowNumbering ? r : Character.toString((char) ('A' + chrow)))); // Row
                    chrow++;
                    if (chrow >= 26) chrow = 0;

                    brow.addView(cell_left);
                }
                else
                {
                    final String data = getDataEntry(this.grid, r, c);
                    this.setCellState(cell_cnt, STATE_NORMAL);

                    if (data != null && data.trim().length() != 0)
                        this.setCellState(cell_cnt, STATE_DONE);

                    if (r == mCurRow && c == mCurCol)
                    {
                        this.setCellState(cell_cnt, STATE_ACTIVE);
                        curCell = cell_cnt;
                    }

                    if (excludedRow || excludedCol || isExcludedCell(r, c))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.saveExcludedCell(r, c);
                    }

                    if (data != null && data.equals("exclude"))
                    {
                        this.setCellState(cell_cnt, STATE_INACTIVE);
                        this.excludeCells.add(new Point(c, r));
                    }

                    cell_cnt.setOnClickListener(this);
                    cell_cnt.setTag(Utils.getTag(r, c));
                    cell_cnt.setTag(R.string.cell_col, c);
                    cell_cnt.setTag(R.string.cell_row, r);
                    brow.addView(cell_box);
                }
            }
            mTableData.addView(brow);
        }
    }

    private void saveExcludedCell(final int r, final int c)
    {
        boolean ret;

        final EntriesTable entriesTable = new EntriesTable(this);
        if (entriesTable.getByGrid(this.grid, r, c))
        {
            entriesTable.value = "exclude";
            ret = entriesTable.update();
        }
        else
        {
            entriesTable.grid = this.grid;
            entriesTable.row = r;
            entriesTable.col = c;
            entriesTable.value = "exclude";
            ret = entriesTable.insert() > 0;
        }

        if (!ret)
        {
            Toast.makeText(Main.this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private String getDataEntry(final long grid, final int r, final int c)
    {
        String data = null;

        final EntriesTable entriesTable = new EntriesTable(this);
        if (entriesTable.getByGrid(grid, r, c)) data = entriesTable.value;

        return data;
    }

    private void setCellState(final View cell, final int state)
    {
        assert cell != null;
        if (state == STATE_DONE)
            cell.setBackgroundResource(R.drawable.table_cell_done);
        else if (state == STATE_ACTIVE)
            cell.setBackgroundResource(R.drawable.table_cell_active);
        else if (state == STATE_INACTIVE)
            cell.setBackgroundResource(R.drawable.table_cell_inactive);
        else
            cell.setBackgroundResource(R.drawable.table_cell);
    }

    private void exportData()
    {
        assert this.nonNullOptionalFields != null;
        final String name = this.nonNullOptionalFields.get(0).getValue() +
            "_" + Utils.getCurrentDate().replace(".", "_");

        final LayoutInflater layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        final View view = layoutInflater.inflate(R.layout.file_input, null);

        final EditText nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        nameEdit.setText(name);

        final Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.filename_set));
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                {
                    dialog.cancel();

                    final String filename = nameEdit.getText().toString().trim();
                    if (filename.length() == 0) {
                        Utils.alert(Main.this, Coordinate.appName, getString(R.string.filename_empty));
                        return;
                    }

                    final File path = new File(Constants.EXPORT_PATH,
                        org.wheatgenetics.coordinate.activities.Main.this.templateTitle);  // model
                    createDir(path);

                    mTask = new DataExporter(Main.this,
                        org.wheatgenetics.coordinate.activities.Main.this.id, filename, path.getAbsolutePath());
                    mTask.execute();
                }
            });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final DialogInterface dialog, final int which)
                { dialog.cancel(); }
            });
        builder.show();
    }

    private void cancelTask()
    {
        if (mTask != null)
        {
            mTask.cancel(true);
            mTask = null;
        }
    }

    @Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private class DataExporter extends AsyncTask<Void, String, Boolean>
    {
        private Context        mContext;
        private ProgressDialog mDlg;
        private long           mTempId;

        private String mTempPath;
        private String mTempName;

        private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable;

        private String mMsg = null;

        private File mFile;

        DataExporter(final Context context, final long id, final String name, final String path)
        {
            mContext  = context;
            mTempId   = id     ;
            mTempName = name   ;
            mTempPath = path   ;
        }

        @Override
        protected void onProgressUpdate(String... msg)
        {
            if (msg == null) return;

            final String text = msg[0];
            if (text == null) return;

            mDlg.setMessage(text);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            mDlg = new ProgressDialog(mContext);
            mDlg.setTitle(getString(R.string.exporting_title));
            mDlg.setMessage(getString(R.string.exporting_body));
            mDlg.setCancelable(true);
            mDlg.setOnCancelListener(new OnCancelListener()
                {
                    @Override
                    public void onCancel(final DialogInterface dialog) { cancelTask(); }
                });
            mDlg.show();
        }

        @Override
        protected void onPostExecute(final Boolean result)
        {
            if (mDlg != null && mDlg.isShowing())
            {
                mDlg.dismiss();
                mDlg = null;
            }

            // TODO when grid is reset, make a new one
            if (result != null && result)
            {
                mLastExportGridId = org.wheatgenetics.coordinate.activities.Main.this.grid;
                Utils.alert(mContext, Coordinate.appName, getString(R.string.export_success),
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Utils.confirm(mContext, Coordinate.appName,
                                getString(R.string.clear_grid), new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        try
                                        {
                                            final EntriesTable entriesTable = new EntriesTable(Main.this);
                                            if (entriesTable.deleteByGrid(org.wheatgenetics.coordinate.activities.Main.this.grid))
                                            {
                                                populateTemplate();
                                                showTemplateUI();
                                            }
                                            else Utils.toast(mContext, getString(R.string.clear_fail));
                                        }
                                        catch (final java.lang.Exception e)
                                        {
                                            Utils.toast(mContext, getString(R.string.clear_fail));
                                            return;
                                        }
                                        share();
                                    }
                                },
                                new Runnable()
                                {
                                    @Override
                                    public void run() { share(); }
                                });
                        }
                    });
            }
            else
            {
                if (mMsg == null) mMsg = getString(R.string.export_no_data);
                Utils.alert(mContext, getString(R.string.export), mMsg);
            }
        }

        private void share()
        {
            final String path = mFile.getAbsolutePath();

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, getString(R.string.share_file)));
        }

        @Override
        protected Boolean doInBackground(final Void... bparams)
        {
            boolean ret;

            try
            {
                templatesTable = new org.wheatgenetics.coordinate.database.TemplatesTable(Main.this);
            }
            catch (final java.lang.Exception e)
            {
                mMsg = getString(R.string.import_template_failed);
                return false;
            }
            if (!templatesTable.get(mTempId))
            {
                mMsg = getString(R.string.import_template_failed);
                return false;
            }

            if (templatesTable.type == TYPE_SEED)  // model
                ret = exportSeed();
            else if (templatesTable.type == TYPE_DNA)  // model
                ret = exportDna();
            else ret = exportDefault();

            return ret;
        }

        private boolean exportDefault()
        {
            boolean ret = false;

            final String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) mFile.delete();

            CsvWriter csvOutput;
            FileWriter writer;

            try
            {
                writer    = new FileWriter(mFile, false);
                csvOutput = new CsvWriter (writer, ',' );

                //Titles
                csvOutput.write("Value" );
                csvOutput.write("Column");
                csvOutput.write("Row"   );

                assert nonNullOptionalFields != null;
                for (final OptionalField optionalField: nonNullOptionalFields)
                    csvOutput.write(optionalField.getName());

                csvOutput.endRecord();

                int row;
                int col;
                for (int c = 0; c < templatesTable.cols; c++)  // model
                {
                    col = c + 1;
                    for (int r = 0; r < templatesTable.rows; r++)  // model
                    {
                        row = r + 1;

                        String data;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                            data = "exclude";
                        else
                        {
                            data = getDataEntry(
                                org.wheatgenetics.coordinate.activities.Main.this.grid, row, col);
                            if (data == null) data = "";
                        }

                        csvOutput.write(data               );
                        csvOutput.write(String.valueOf(col));
                        csvOutput.write(String.valueOf(row));

                        for (final OptionalField optionalField: nonNullOptionalFields)
                            csvOutput.write(optionalField.getValue());

                        csvOutput.endRecord();
                    }

                    publishProgress(getString(R.string.exporting_column_title) + col);
                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }

        private boolean exportDna()
        {
            boolean ret = false;
            final String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) mFile.delete();

            CsvWriter  csvOutput;
            FileWriter writer;

            String date = "";
            String plate_id = "";
            String plate_name = "";

            String dna_person = "";
            String notes = "";
            String tissue_type = "";
            String extraction = "";

            assert nonNullOptionalFields != null;
            for (final OptionalField optionalField: nonNullOptionalFields)
                if (optionalField.nameEqualsIgnoreCase("date"))
                    date = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("Plate"))
                    plate_id = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("Plate Name"))
                    plate_name = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("Notes"))
                    notes = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("tissue_type"))
                    tissue_type = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("extraction"))
                    extraction = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("person"))
                    dna_person = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("date"))
                    date = optionalField.getValue();

            try
            {
                writer = new FileWriter(mFile, false);

                csvOutput = new CsvWriter(writer, ',');

                csvOutput.write("date"       );
                csvOutput.write("plate_id"   );
                csvOutput.write("plate_name" );
                csvOutput.write("sample_id"  );
                csvOutput.write("well_A01"   );
                csvOutput.write("well_01A"   );
                csvOutput.write("tissue_id"  );
                csvOutput.write("dna_person" );
                csvOutput.write("notes"      );
                csvOutput.write("tissue_type");
                csvOutput.write("extraction" );

                csvOutput.endRecord();

                int row, col;
                for (int c = 0; c < templatesTable.cols; c++)  // model
                {
                    col = c + 1;
                    for (int r = 0; r < templatesTable.rows; r++)  // model
                    {
                        row = r + 1;

                        final String rowName = Character.toString((char) ('A' + r));
                        final String colName = String.format("%02d", col);
                        final String sample_id = String.format("%s_%s%s", plate_id, rowName, colName);

                        String tissue_id;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                            tissue_id = "BLANK_" + sample_id;
                        else
                        {
                            tissue_id = getDataEntry(
                                org.wheatgenetics.coordinate.activities.Main.this.grid, row, col);
                            if (tissue_id == null || tissue_id.trim().length() == 0)
                                tissue_id = "BLANK_" + sample_id;
                        }

                        csvOutput.write(date                                   );
                        csvOutput.write(plate_id                               );
                        csvOutput.write(plate_name                             );
                        csvOutput.write(sample_id                              ); // sample_id
                        csvOutput.write(String.format("%s%s", rowName, colName)); // well_A01
                        csvOutput.write(String.format("%s%s", colName, rowName)); // well_01A
                        csvOutput.write(tissue_id                              );
                        csvOutput.write(dna_person.replace(" ", "_")           );
                        csvOutput.write(notes                                  );
                        csvOutput.write(tissue_type                            );
                        csvOutput.write(extraction                             );

                        csvOutput.endRecord();
                    }
                    publishProgress(getString(R.string.exporting_column_title) + col);
                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }

        private boolean exportSeed()
        {
            boolean ret = false;

            final String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) mFile.delete();

            CsvWriter  csvOutput;
            FileWriter writer;

            String person = "";
            String date = "";
            String trayid = "";

            assert nonNullOptionalFields != null;
            for (final OptionalField optionalField: nonNullOptionalFields)
                if (optionalField.nameEqualsIgnoreCase("Tray"))
                    trayid = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("Person"))
                    person = optionalField.getValue();
                else if (optionalField.nameEqualsIgnoreCase("date"))
                    date = optionalField.getValue();

            try
            {
                writer = new FileWriter(mFile, false);

                csvOutput = new CsvWriter(writer, ',');

                csvOutput.write("tray_id"    );
                csvOutput.write("cell_id"    );
                csvOutput.write("tray_num"   );
                csvOutput.write("tray_column");
                csvOutput.write("tray_row"   );
                csvOutput.write("seed_id"    );
                csvOutput.write("person"     );
                csvOutput.write("date"       );
                csvOutput.endRecord();

                int row;
                int col;
                for (int c = 0; c < templatesTable.cols; c++)  // model
                {
                    col = c + 1;
                    for (int r = 0; r < templatesTable.rows; r++)  // model
                    {
                        row = r + 1;

                        String data;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col))
                            data = "exclude";
                        else
                        {
                            data = getDataEntry(
                                org.wheatgenetics.coordinate.activities.Main.this.grid, row, col);
                            if (data == null) data = "BLANK_";
                        }

                        csvOutput.write(trayid                                            ); // tray id
                        csvOutput.write(String.format("%s_C%02d_R%d", mTempName, col, row)); // "cell_id"
                        csvOutput.write(""                                                ); // "tray_num"
                        csvOutput.write(String.valueOf(col)                               ); // "tray_column"
                        csvOutput.write(String.valueOf(row)                               ); // "tray_row"
                        csvOutput.write(data                                              ); // "seed_id"
                        csvOutput.write(person.replace(" ", "_")                          ); // "person"
                        csvOutput.write(date                                              ); // "date"
                        csvOutput.endRecord();
                    }

                    publishProgress(getString(R.string.exporting_column_title) + col);
                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        final IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        assert scanResult != null;
        if (scanResult != null)
        {
            final String barcodeText = scanResult.getContents();
            mEditData.setText(barcodeText);
            this.saveData();
        }
    }

    private void makeFileDiscoverable(final File file, final Context context)
    {
        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, null, null);
        context.sendBroadcast(new Intent(
            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}