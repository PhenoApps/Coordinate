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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Coordinate;
import org.wheatgenetics.coordinate.barcodes.IntentIntegrator;
import org.wheatgenetics.coordinate.barcodes.IntentResult;
import org.wheatgenetics.coordinate.csv.CsvWriter;
import org.wheatgenetics.coordinate.database.DatabaseHelper;
import org.wheatgenetics.coordinate.database.Entry;
import org.wheatgenetics.coordinate.database.Grid;
import org.wheatgenetics.coordinate.database.Template;
import org.wheatgenetics.coordinate.objects.OptionalField;
import org.wheatgenetics.coordinate.utils.Constants;
import org.wheatgenetics.coordinate.utils.Utils;

public class Main extends AppCompatActivity implements android.view.View.OnClickListener, OnEditorActionListener, OnKeyListener {
    private static final int STATE_NORMAL = 0;
    private static final int STATE_DONE = 1;
    private static final int STATE_ACTIVE = 2;
    private static final int STATE_INACTIVE = 3;

    private static final int TYPE_SEED = 0;
    private static final int TYPE_DNA = 1;
    private static final int TYPE_DEFAULT = 2;

    private static final int MODE_DNA = 0;
    private static final int MODE_SAVED = 1;
    private static final int MODE_DEFAULT = 2;

    public final String TAG = "Coordinate";
    public final String DATE_FORMAT = "yyyy-mm-dd";

    private LinearLayout mLayoutMain;
    private LinearLayout mLayoutGrid;
    private LinearLayout mLayoutOptional;
    private TableLayout mTableData;

    private TextView mTextTemplate;

    private EditText mEditData;

    public View curCell = null;

    private long mId = 0;
    private long mGrid = 0;
    private String mGridTitle = "";
    private int mCurRow = 1;
    private int mCurCol = 1;

    private int mType = 0;
    private String mTemplate = "";

    private SharedPreferences ep;

    private int mRows = 20;
    private int mCols = 10;
    private boolean mRowNumbering = false;
    private boolean mColNumbering = true;

    private List<Point> mExcludeCells = new ArrayList<>();

    private List<Integer> mExcludeRows = new ArrayList<>();
    private List<Integer> mExcludeCols = new ArrayList<>();
    private String[] menuMain;

    private List<OptionalField> mOptions;

    private DataExporter mTask;
    public long mLastExportGridId = -1;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private LinearLayout parent;
    private ScrollView changeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOptions = new ArrayList<>();
        mOptions.add(new OptionalField("Plate Id"                       ));
        mOptions.add(new OptionalField("Date", /* hint => */ DATE_FORMAT));

        menuMain = new String[]{getResources().getString(R.string.template_load), getResources().getString(R.string.template_new)};

        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.bringToFront();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().getThemedContext();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ep = getSharedPreferences("Settings", 0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        setupDrawer();

        parent = new LinearLayout(this);
        changeContainer = new ScrollView(this);
        changeContainer.removeAllViews();
        changeContainer.addView(parent);

        mLayoutMain = (LinearLayout) findViewById(R.id.mainLayout);
        mLayoutGrid = (LinearLayout) findViewById(R.id.gridArea);
        mLayoutOptional = (LinearLayout) findViewById(R.id.optionalLayout);

        mTableData = (TableLayout) findViewById(R.id.dataTable);

        mTextTemplate = (TextView) findViewById(R.id.templateText);

        mEditData = (EditText) findViewById(R.id.dataEdit);
        mEditData.setImeActionLabel(getString(R.string.keyboard_save), KeyEvent.KEYCODE_ENTER);
        mEditData.setOnEditorActionListener(this);

        //mEditData.setOnKeyListener(this);

        mTextTemplate.setText(mTemplate);

        mLayoutMain.setVisibility(View.INVISIBLE);

        initDb();
        createDirs();

        if (ep.getLong("CurrentGrid", -1) != -1) {
            loadGrid(ep.getLong("CurrentGrid", -1));
        } else {
            menuList();
        }

        showTemplateUI();

        SharedPreferences.Editor ed = ep.edit();
        if (ep.getInt("UpdateVersion", -1) < getVersion()) {
            ed.putInt("UpdateVersion", getVersion());
            ed.apply();
            changelog();
        }
    }

    public int getVersion() {
        int v = 0;
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "" + e.getMessage());
        }
        return v;
    }

    private void createDirs() {
        createDir(Constants.MAIN_PATH);
        createDir(Constants.EXPORT_PATH);
        createDir(Constants.TEMPLATE_PATH);
    }

    private void createDir(File path) {
        File blankFile = new File(path, ".coordinate");

        if (!path.exists()) {
            path.mkdirs();

            try {
                blankFile.getParentFile().mkdirs();
                blankFile.createNewFile();
                makeFileDiscoverable(blankFile, this);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void aboutDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View personView = inflater.inflate(R.layout.about, new LinearLayout(this), false);
        TextView version = (TextView) personView.findViewById(R.id.tvVersion);
        TextView otherApps = (TextView) personView.findViewById(R.id.tvOtherApps);


        final PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            version.setText(getResources().getString(R.string.versiontitle) + " " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changelog();
            }
        });

        otherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOtherAppsDialog();
            }
        });

        alert.setCancelable(true);
        alert.setTitle(getResources().getString(R.string.about));
        alert.setView(personView);
        alert.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void changelog() {
        parent.setOrientation(LinearLayout.VERTICAL);
        parseLog(R.raw.changelog_releases);

        AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
        builder.setTitle(getResources().getString(R.string.updatemsg));
        builder.setView(changeContainer)
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void parseLog(int resId) {
        try {
            InputStream is = getResources().openRawResource(resId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr, 8192);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(20, 5, 20, 0);

            String curVersionName = null;
            String line;

            while ((line = br.readLine()) != null) {
                TextView header = new TextView(this);
                TextView content = new TextView(this);
                TextView spacer = new TextView(this);
                View ruler = new View(this);

                header.setLayoutParams(lp);
                content.setLayoutParams(lp);
                spacer.setLayoutParams(lp);
                ruler.setLayoutParams(lp);

                spacer.setTextSize(5);

                ruler.setBackgroundColor(getResources().getColor(R.color.main_colorAccent));
                header.setTextAppearance(getApplicationContext(), R.style.ChangelogTitles);
                content.setTextAppearance(getApplicationContext(), R.style.ChangelogContent);

                if (line.length() == 0) {
                    curVersionName = null;
                    spacer.setText("\n");
                    parent.addView(spacer);
                } else if (curVersionName == null) {
                    final String[] lineSplit = line.split("/");
                    curVersionName = lineSplit[1];
                    header.setText(curVersionName);
                    parent.addView(header);
                    parent.addView(ruler);
                } else {
                    content.setText("•  " + line);
                    parent.addView(content);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showOtherAppsDialog() {
        final AlertDialog.Builder otherAppsAlert = new AlertDialog.Builder(this);

        ListView myList = new ListView(this);
        myList.setDivider(null);
        myList.setDividerHeight(0);
        String[] appsArray = new String[3];

        appsArray[0] = "Field Book";
        appsArray[1] = "Inventory";
        appsArray[2] = "1KK";
        //appsArray[3] = "Intercross";
        //appsArray[4] = "Rangle";

        Integer app_images[] = {R.drawable.other_ic_field_book, R.drawable.other_ic_inventory, R.drawable.other_ic_1kk};
        final String[] links = {"https://play.google.com/store/apps/details?id=com.fieldbook.tracker",
                "https://play.google.com/store/apps/details?id=org.wheatgenetics.inventory",
                "http://wheatgenetics.org/apps"}; //TODO update these links

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View arg1, int which, long arg3) {
                Uri uri = Uri.parse(links[which]);
                Intent intent;

                switch (which) {
                    case 0:
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
            }
        });

        CustomListAdapter adapterImg = new CustomListAdapter(this, app_images, appsArray);
        myList.setAdapter(adapterImg);

        otherAppsAlert.setCancelable(true);
        otherAppsAlert.setTitle(getResources().getString(R.string.otherapps));
        otherAppsAlert.setView(myList);
        otherAppsAlert.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        otherAppsAlert.show();
    }

    public class CustomListAdapter extends ArrayAdapter<String> {
        String[] color_names;
        Integer[] image_id;
        Context context;

        public CustomListAdapter(Activity context, Integer[] image_id, String[] text) {
            super(context, R.layout.appline, text);
            this.color_names = text;
            this.image_id = image_id;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View single_row = inflater.inflate(R.layout.appline, null, true);
            TextView textView = (TextView) single_row.findViewById(R.id.txt);
            ImageView imageView = (ImageView) single_row.findViewById(R.id.img);
            textView.setText(color_names[position]);
            imageView.setImageResource(image_id[position]);
            return single_row;
        }
    }

    public void resetDatabase() {
        Utils.confirm(this, getString(R.string.reset_database), getString(R.string.reset_database_message), new Runnable() {
            @Override
            public void run() {
                Main.this.deleteDatabase("seedtray1.db");
                DatabaseHelper dbh = new DatabaseHelper(Main.this);
                Coordinate.db = dbh.getWritableDatabase();
                finish();
            }
        }, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(Main.this).inflate(R.menu.mainmenu, menu);

        menu.findItem(R.id.barcode_camera).setVisible(true);

        return true;
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                TextView person = (TextView) findViewById(R.id.nameLabel);
                person.setText(ep.getString("Person", ""));

                TextView template = (TextView) findViewById(R.id.templateLabel);
                template.setText(mTemplate);
            }

            public void onDrawerClosed(View view) {
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_new_grid:
                menuNewGrid();
                break;

            case R.id.menu_import:
                importGrid();
                break;

            case R.id.menu_export:
                if (mTemplate.equals("")) {
                    makeToast(getString(R.string.grid_empty));
                } else {
                    exportData();
                }
                break;

            case R.id.menu_load_template:
                menuTemplateLoad();
                break;

            case R.id.menu_delete_grid:
                menuDeleteGrid();
                break;

            case R.id.menu_delete_template:
                menuDeleteTemplate();
                break;

            case R.id.menu_new_template:
                inputTemplateNew();
                break;

            case R.id.about:
                aboutDialog();
                break;

            // Keeping this for debugging purposes
            /*case R.id.reset_database:
                resetDatabase();
                break;

            case R.id.copy_database:
                copydb();
                break;*/
        }

        mDrawerLayout.closeDrawers();
    }

    private void copydb() {
        File f = new File("/data/data/org.wheatgenetics.coordinate/databases/seedtray1.db");
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(f);
            fos = new FileOutputStream("/mnt/sdcard/db_dump.db");
            while (true) {
                int i = fis.read();
                if (i != -1) {
                    fos.write(i);
                } else {
                    break;
                }
            }
            fos.flush();
            makeFileDiscoverable(new File("/mnt/sdcard/db_dump.db"), this);
            Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException ioe) {
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.barcode_camera:
                barcodeScan();
                break;
        }

        return true;
    }

    private void barcodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void menuNewGrid() {
        if (mGrid == 0) {
            menuList();
        } else {
            if (mGrid >= 0 && mLastExportGridId == mGrid) {
                newGridNow();
            } else {

                Utils.confirm(this, Coordinate.mAppName, getString(R.string.new_grid_warning), new Runnable() {
                    @Override
                    public void run() {
                        exportData();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        newGridNow();
                    }
                });
            }
        }
    }

    private void newGridNow() {
        deleteGrid(mGrid);

        if (mType == TYPE_SEED) {
            Template temp = new Template();
            if (temp.getByType(mType)) {
                copyTemplate(temp);
            }

            inputSeed();
        } else if (mType == TYPE_DNA) {
            Template temp = new Template();
            if (temp.getByType(mType)) {
                copyTemplate(temp);
            }

            mExcludeCells.clear();
            mExcludeCells.add(new Point(randomBox(mCols), randomBox(mRows)));

            inputTemplateInput(MODE_DNA);
        } else {
            // reset options?
            inputTemplateInput(MODE_SAVED);
        }

    }

    private void menuDeleteGrid() {
        if (mGrid == 0)
            return;

        Utils.confirm(this, Coordinate.mAppName, getString(R.string.delete_grid_warning), new Runnable() {
            @Override
            public void run() {
                if (deleteGrid(mGrid)) {
                    Toast.makeText(Main.this, getString(R.string.grid_deleted), Toast.LENGTH_LONG).show();
                    mGrid = 0;
                    mLayoutOptional.setVisibility(View.INVISIBLE);
                    mLayoutGrid.setVisibility(View.INVISIBLE);
                    menuList();
                } else
                    Toast.makeText(Main.this, getString(R.string.grid_not_deleted), Toast.LENGTH_LONG).show();
            }
        }, null);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            saveData();
            return true;
        } else {
            if (event != null) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    saveData();
                    return true;
                }
            }
        }
        return false;
    }

    //TODO don't toggle already selected cell
    @Override
    public void onClick(View cell) {
        int c = -1;
        int r = -1;
        Object obj;

        obj = cell.getTag(R.string.cell_col);
        if (obj instanceof Integer)
            c = (Integer) obj;

        obj = cell.getTag(R.string.cell_row);
        if (obj instanceof Integer)
            r = (Integer) obj;

        if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c)) {
            mEditData.setText("");
            return;
        }

        if (c != -1 && r != -1) {
            mCurRow = r;
            mCurCol = c;

            String data = getDataEntry(mGrid, mCurRow, mCurCol);

            if (data != null && data.contains("exclude")) {
                return;
            }

            setCellState(cell, STATE_ACTIVE);

            if (data == null) {
                data = "";
            }

            mEditData.setSelectAllOnFocus(true);
            mEditData.setText(data);
            mEditData.selectAll();
            mEditData.requestFocus();
        }

        resetCurrentCell();
        curCell = cell;
    }

    // Adds default templates to database
    private void initDb() {
        mTemplate = "Seed Tray";
        mType = TYPE_SEED;
        mRows = 6;
        mCols = 20;
        mRowNumbering = true;
        mColNumbering = true;

        mOptions = new ArrayList<>();
        mOptions.add(new OptionalField("Tray"  , /* hint => */ "Tray ID"    ));
        mOptions.add(new OptionalField("Person", /* hint => */ "Person name"));
        mOptions.add(new OptionalField("Date"  , /* hint => */ DATE_FORMAT  ));

        mExcludeRows = new ArrayList<>();
        mExcludeRows.add(2);
        mExcludeRows.add(5);

        mExcludeCols = new ArrayList<>();
        mExcludeCells = new ArrayList<>();

        createDb(mType);

        mTemplate = "DNA Plate";
        mRows = 8;
        mCols = 12;
        mType = TYPE_DNA;
        mRowNumbering = true;
        mColNumbering = false;

        mOptions = new ArrayList<>();
        mOptions.add(new OptionalField("Plate", /* hint => */ "Plate ID"                     )); // TODO dna
        mOptions.add(new OptionalField("Plate Name"                                          ));
        mOptions.add(new OptionalField("Notes"                                               ));
        mOptions.add(new OptionalField("tissue_type", /* value => */ "Leaf", /* hint => */ ""));
        mOptions.add(new OptionalField("extraction" , /* value => */ "CTAB", /* hint => */ ""));
        mOptions.add(new OptionalField("person"                                              ));
        mOptions.add(new OptionalField("date", /* hint => */ DATE_FORMAT                     ));

        mExcludeRows = new ArrayList<>();
        mExcludeCols = new ArrayList<>();

        mExcludeCells = new ArrayList<>();

        createDb(mType);

        mTemplate = "";
    }

    private boolean createDb(int type) {
        Template temp = new Template();
        boolean found = temp.getByType(type);

        temp.title = mTemplate;
        temp.type = mType;
        temp.rows = mRows;
        temp.cols = mCols;

        temp.ecells = Utils.pointToJson(mExcludeCells);
        temp.ecols = Utils.listToJson(mExcludeCols);
        temp.erows = Utils.listToJson(mExcludeRows);

        temp.options = Utils.optionsToJson(mOptions);

        temp.cnumbering = mColNumbering ? 1 : 0;
        temp.rnumbering = mRowNumbering ? 1 : 0;

        temp.stamp = System.currentTimeMillis();

        boolean ret;
        if (found)
            ret = temp.update();
        else
            ret = (temp.insert() > 0);

        return ret;
    }

    public void menuList() {
        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.template_options));
        dialog.setItems(menuMain, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    menuTemplateLoad();
                } else {
                    inputTemplateNew();
                }
            }
        });
        dialog.show();
    }

    private void menuTemplateLoad() {
        final List<Template> templates = new ArrayList<>();

        Template tmp = new Template();
        Cursor cursor = tmp.load();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Template temp = new Template();
                if (temp.copy(cursor)) {
                    templates.add(temp);
                }
            }
            cursor.close();
        }

        int size = templates.size();

        String[] items = new String[size];

        for (int i = 0; i < size; i++) {
            Template item = templates.get(i);
            items[i] = item.title;
        }

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(menuMain[0]);
        dialog.setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which < 0 || which > templates.size()) {
                    return;
                }

                Template tmp = templates.get(which);
                copyTemplate(tmp);

                if (which == 0) {
                    inputSeed();
                } else if (which == 1) {
                    mExcludeCells.clear();
                    mExcludeCells.add(new Point(randomBox(mCols), randomBox(mRows)));

                    inputTemplateInput(MODE_DNA);
                } else {
                    inputTemplateInput(MODE_SAVED);
                }
            }

        });
        dialog.show();
    }

    private void menuDeleteTemplate() {
        final List<Template> templates = new ArrayList<>();

        Template tmp = new Template();
        Cursor cursor = tmp.load();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Template temp = new Template();
                if (temp.copy(cursor)) {
                    templates.add(temp);
                }
            }
            cursor.close();
        }

        int size = templates.size();

        String[] items = new String[size];

        for (int i = 0; i < size; i++) {
            Template item = templates.get(i);
            items[i] = item.title;
        }

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.delete_template));
        dialog.setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which < 0 || which > templates.size()) {
                    return;
                }

                final Template tmp = templates.get(which);

                Utils.confirm(Main.this, getString(R.string.delete_template), getString(R.string.delete_template_warning), new Runnable() {
                    @Override
                    public void run() {
                        if (deleteTemplate(tmp.id)) {
                            Toast.makeText(Main.this, getString(R.string.template_deleted), Toast.LENGTH_LONG).show();
                            menuList();
                        } else
                            Toast.makeText(Main.this, getString(R.string.template_not_deleted), Toast.LENGTH_LONG).show();
                    }
                }, null);
            }

        });
        dialog.show();
    }

    private void copyTemplate(Template tmp) {
        mId = tmp.id;
        mTemplate = tmp.title;
        mType = tmp.type;
        mRows = tmp.rows;
        mCols = tmp.cols;

        mExcludeCells = Utils.jsonToPoints(tmp.ecells);

        mExcludeCols = Utils.jsonToList(tmp.ecols);
        mExcludeRows = Utils.jsonToList(tmp.erows);

        mOptions = Utils.jsonToOptions(tmp.options);

        mRowNumbering = tmp.rnumbering == 1;
        mColNumbering = tmp.cnumbering == 1;
    }

    private void inputTemplateNew() {
        mExcludeCells = new ArrayList<>();
        mExcludeRows = new ArrayList<>();
        mExcludeCols = new ArrayList<>();

        mOptions = new ArrayList<>();
        mOptions.add(new OptionalField("Identification"                 ));
        mOptions.add(new OptionalField("Person"                         ));
        mOptions.add(new OptionalField("Date", /* hint => */ DATE_FORMAT));

        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.template_new, new LinearLayout(this), false);

        final EditText nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        final EditText rowsEdit = (EditText) view.findViewById(R.id.rowsEdit);
        final EditText colsEdit = (EditText) view.findViewById(R.id.colsEdit);

        nameEdit.setText("");
        rowsEdit.setText(mRows <= 0 ? "" : String.valueOf(mRows));
        colsEdit.setText(mCols <= 0 ? "" : String.valueOf(mCols));

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(menuMain[1]);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                String sname = nameEdit.getText().toString().trim();
                String scols = colsEdit.getText().toString().trim();
                String srows = rowsEdit.getText().toString().trim();

                mTemplate = sname;
                mRows = Utils.getInteger(srows);
                mCols = Utils.getInteger(scols);

                if (sname.length() == 0) {
                    Toast.makeText(Main.this, getString(R.string.template_no_name), Toast.LENGTH_LONG).show();
                    inputTemplateNew();
                    return;
                }

                if (srows.length() == 0 || mRows == -1) {
                    Toast.makeText(Main.this, getString(R.string.no_rows), Toast.LENGTH_LONG).show();
                    inputTemplateNew();
                    return;
                }

                if (scols.length() == 0 || mCols == -1) {
                    Toast.makeText(Main.this, getString(R.string.no_cols), Toast.LENGTH_LONG).show();
                    inputTemplateNew();
                    return;
                }

                inputTemplateNewExtra();
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void inputTemplateNewExtra() {
        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.template_new_extra, new LinearLayout(this), false);

        final Button optionalButton = (Button) view.findViewById(R.id.optionalButton);
        final Button excludeButton = (Button) view.findViewById(R.id.excludeButton);
        final Button namingButton = (Button) view.findViewById(R.id.namingButton);

        optionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputOptional();
            }
        });

        excludeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputExclude();
            }
        });

        namingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNaming();
            }
        });

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.template_new);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                inputTemplateInput(MODE_DEFAULT);
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void inputSeed() {
        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.grid_new, new LinearLayout(this), false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.optionalLayout);

        final EditText[] edits = new EditText[mOptions.size()];

        // load options
        for (int i = 0; i < mOptions.size(); i++) {
            View item = inflater.inflate(R.layout.optional_edit, layout, false);
            TextView optionText = (TextView) item.findViewById(R.id.optionText);
            EditText optionEdit = (EditText) item.findViewById(R.id.optionEdit);

            OptionalField opt = mOptions.get(i);
            if (opt == null || !opt.checked)
                continue;

            optionText.setText(opt.field);
            optionEdit.setText(opt.hint.equals(DATE_FORMAT) ? Utils.getDateFormat(System.currentTimeMillis()) : opt.value);
            optionEdit.setHint(opt.hint);

            edits[i] = optionEdit;

            layout.addView(item);
        }

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(mTemplate);
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                for (int i = 0; i < mOptions.size(); i++) {
                    EditText edit = edits[i];
                    if (edit == null)
                        continue;

                    if (mOptions.get(i) == null)
                        continue;

                    String value = edit.getText().toString().trim();
                    if (i == 0 && value.length() == 0) {
                        Utils.toast(Main.this, mOptions.get(i).hint + getString(R.string.not_empty));
                        inputSeed();
                        return;
                    }

                    mOptions.get(i).value = value;

                    if (mOptions.get(i).field.equalsIgnoreCase("Person") || mOptions.get(i).field.equalsIgnoreCase("Name")) {
                        SharedPreferences.Editor ed = ep.edit();
                        ed.putString("Person", mOptions.get(i).value);
                        ed.apply();
                    }
                }

                dialog.cancel();
                loadTemplate(TYPE_SEED);
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dlg = dialog.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dlg.setCancelable(false);
        dlg.show();
    }

    private void tempLoad(int mode) {
        if (mode == MODE_DNA) {
            loadTemplate(TYPE_DNA);
        } else if (mode == MODE_SAVED) {
            loadTemplate(TYPE_DEFAULT);
        } else {
            newTemplate(TYPE_DEFAULT);
        }
    }

    //TODO merge this method with the one above
    private void inputTemplateInput(final int mode) {
        if (mOptions.size() == 0) {
            tempLoad(mode);
            return;
        }

        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.grid_new, new LinearLayout(this), false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.optionalLayout);

        final EditText[] edits = new EditText[mOptions.size()];

        // load options
        for (int i = 0; i < mOptions.size(); i++) {
            View item = inflater.inflate(R.layout.optional_edit, layout, false);
            TextView optionText = (TextView) item.findViewById(R.id.optionText);
            EditText optionEdit = (EditText) item.findViewById(R.id.optionEdit);

            OptionalField opt = mOptions.get(i);
            if (opt == null || !opt.checked)
                continue;

            optionText.setText(opt.field);
            optionEdit.setText(opt.hint.equals(DATE_FORMAT) ? Utils.getDateFormat(System.currentTimeMillis()) : opt.value);
            optionEdit.setHint(opt.hint);

            edits[i] = optionEdit;

            layout.addView(item);
        }

        Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(mTemplate);
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(R.string.create), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                for (int i = 0; i < mOptions.size(); i++) {
                    EditText edit = edits[i];
                    if (edit == null)
                        continue;

                    if (mOptions.get(i) == null)
                        continue;

                    if (mode == MODE_DNA) {
                        String value = edit.getText().toString().trim();
                        if (i == 0 && value.length() == 0) {
                            Utils.toast(Main.this, mOptions.get(i).hint + getString(R.string.not_empty));
                            inputTemplateInput(MODE_DNA);
                            return;
                        }
                    }

                    mOptions.get(i).value = edit.getText().toString().trim();

                    if (mOptions.get(i).field.equalsIgnoreCase("Person") || mOptions.get(i).field.equalsIgnoreCase("Name")) {
                        SharedPreferences.Editor ed = ep.edit();
                        ed.putString("Person", mOptions.get(i).value);
                        ed.apply();
                    }
                }

                dialog.cancel();
                tempLoad(mode);
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dlg = dialog.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputOptional() {
        final String[] items = new String[mOptions.size()];
        final boolean[] selections = new boolean[mOptions.size()];

        for (int i = 0; i < mOptions.size(); i++) {
            OptionalField opt = mOptions.get(i);
            if (opt == null)
                continue;

            items[i] = opt.field;
            selections[i] = opt.checked;
        }

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.optional_fields));
        dialog.setMultiChoiceItems(items, selections, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                mOptions.get(which).checked = isChecked;
            }
        });

        dialog.setNeutralButton(getString(R.string.add_new), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                inputOptionalNew("", "");
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void inputOptionalNew(final String field, final String value) {
        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.optional_new, null);

        final EditText fieldEdit = (EditText) view.findViewById(R.id.fieldEdit);
        final EditText valueEdit = (EditText) view.findViewById(R.id.valueEdit);

        fieldEdit.setText(field);
        valueEdit.setText(value);

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.new_optional_field));
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String sfield = fieldEdit.getText().toString().trim();
                String svalue = valueEdit.getText().toString().trim();

                if (sfield.length() == 0) {
                    Toast.makeText(Main.this, getString(R.string.new_optional_field_no_name), Toast.LENGTH_LONG).show();
                    inputOptionalNew(field, value);
                    return;
                }
                dialog.cancel();

                mOptions.add(new OptionalField(sfield, /* value => */ svalue, /* hint => */ ""));

                inputOptional();
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dlg = dialog.create();
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputExclude() {
        String[] items = {getString(R.string.rows), getString(R.string.cols), getString(R.string.random)};

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.exclude_title));
        dialog.setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    inputExcludeBoxes(0);
                } else if (which == 1) {
                    inputExcludeBoxes(1);
                } else if (which == 2) {
                    inputExcludeInput();
                }
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void inputExcludeBoxes(final int type) {
        final int total = type == 0 ? mRows : mCols;

        final boolean[] selections = new boolean[total];

        String[] items = new String[total];

        for (int i = 0; i < total; i++) {
            items[i] = String.format("%s %d", (type == 0 ? getString(R.string.row) : getString(R.string.col)), i + 1);
            selections[i] = (type == 0 ? mExcludeRows.contains(Integer.valueOf(i + 1)) : mExcludeCols.contains(Integer.valueOf(i + 1)));
        }

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.exclude_title) + " - " + (type == 0 ? getString(R.string.rows) : getString(R.string.cols)));
        dialog.setMultiChoiceItems(items, selections, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selections[which] = isChecked;
            }
        });

        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // get choices
                for (int i = 0; i < total; i++) {
                    if (selections[i]) {
                        if (type == 0)
                            mExcludeRows.add(i + 1);
                        else
                            mExcludeCols.add(i + 1);
                    }
                }
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void inputExcludeInput() {
        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.random, null);

        final EditText cellsEdit = (EditText) view.findViewById(R.id.cellsEdit);

        cellsEdit.setText("1");

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.random));
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String str = cellsEdit.getText().toString();

                mExcludeCells = new ArrayList<>();

                int cells = Utils.getInteger(str);
                if (cells > 0) {
                    for (int i = 0; i < cells; i++) {
                        Point point = new Point(randomBox(mCols), randomBox(mRows));
                        mExcludeCells.add(point); // FIXME check exclusivity
                    }
                } else {
                    inputExcludeInput();
                }

                dialog.cancel();
            }
        });

        AlertDialog dlg = dialog.create();
        dlg.setCancelable(false);
        dlg.show();
    }

    private void inputNaming() {
        LayoutInflater inflater = Main.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.naming, null);

        final Spinner rowSpinner = (Spinner) view.findViewById(R.id.rowSpinner);
        final Spinner colSpinner = (Spinner) view.findViewById(R.id.colSpinner);

        rowSpinner.setSelection(mRowNumbering ? 0 : 1);
        colSpinner.setSelection(mColNumbering ? 0 : 1);

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.naming));
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mRowNumbering = (rowSpinner.getSelectedItemPosition() == 0);
                mColNumbering = (colSpinner.getSelectedItemPosition() == 0);

                dialog.cancel();
            }
        });

        AlertDialog dlg = dialog.create();
        dlg.setCancelable(false);
        dlg.show();
    }

    private void importGrid() {
        String[] names = new String[1];
        long[] indexes = new long[1];

        int pos = 0;

        Grid grid = new Grid();
        Cursor gridCursor = grid.getAllGrids();
        if (gridCursor != null) {
            int size = gridCursor.getCount();

            names = new String[size];
            indexes = new long[size];

            while (gridCursor.moveToNext()) {
                Grid tmpG = new Grid();
                if (tmpG.copyAll(gridCursor)) {
                    names[pos] = String.format("Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", tmpG.title, tmpG.templateTitle, tmpG.cols, tmpG.rows, Utils.getDateFormat(tmpG.stamp));
                    indexes[pos] = tmpG.id;
                    pos++;
                }
            }
            gridCursor.close();
        }

        if (pos == 0) {
            Utils.alert(this, Coordinate.mAppName, getString(R.string.no_templates));
            return;
        }

        final long[] gridIds = indexes;

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.import_grid));
        dialog.setItems(names, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which < gridIds.length) {
                    long id = gridIds[which];

                    SharedPreferences.Editor ed = ep.edit();
                    ed.putLong("CurrentGrid", id);
                    ed.apply();

                    Grid grd = new Grid();
                    if (grd.get(id)) {
                        mTemplate = grd.templateTitle;
                        mGrid = grd.id;
                        mGridTitle = grd.title;
                        mType = grd.templateType;
                        mRows = grd.rows;
                        mCols = grd.cols;

                        Template tmp = new Template();

                        if (tmp.get(grd.templateId)) {
                            mOptions = Utils.jsonToOptions(tmp.options);

                            mRowNumbering = tmp.rnumbering == 1;
                            mColNumbering = tmp.cnumbering == 1;
                        }

                        mExcludeCells.clear();
                        mExcludeRows.clear();
                        mExcludeCols.clear();

                        populateTemplate();
                        showTemplateUI();
                    } else {
                        Utils.alert(Main.this, Coordinate.mAppName, getString(R.string.import_grid_failed));
                    }

                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }

    private void loadGrid(long id) {
        Grid grd = new Grid();
        if (grd.get(id)) {
            mTemplate = grd.templateTitle;
            mGrid = grd.id;
            mGridTitle = grd.title;
            mType = grd.templateType;
            mRows = grd.rows;
            mCols = grd.cols;

            mExcludeCells.clear();
            mExcludeRows.clear();
            mExcludeCols.clear();

            Template tmp = new Template();

            if (tmp.get(grd.templateId)) {
                mOptions = Utils.jsonToOptions(tmp.options);

                mRowNumbering = tmp.rnumbering == 1;
                mColNumbering = tmp.cnumbering == 1;
            }

            populateTemplate();
            showTemplateUI();
        } else {
            Utils.alert(Main.this, Coordinate.mAppName, getString(R.string.import_grid_failed));
        }
    }

    private boolean isExcludedCell(int r, int c) {
        for (int i = 0; i < mExcludeCells.size(); i++) {
            Point point = mExcludeCells.get(i);
            if (point.equals(c, r))
                return true;
        }

        return false;
    }

    private boolean isExcludedRow(int r) {
        return mExcludeRows.contains(Integer.valueOf(r));
    }

    private boolean isExcludedCol(int c) {
        return mExcludeCols.contains(Integer.valueOf(c));
    }

    public int randomBox(int size) {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        return rand.nextInt(size - 1) + 1;
    }

    private void saveData() {
        View view;

        String data = mEditData.getText().toString().trim();

        boolean ret;

        Entry entry = new Entry();
        if (entry.getByGrid(mGrid, mCurRow, mCurCol)) {
            entry.value = data;
            ret = entry.update();
        } else {
            entry.grid = mGrid;
            entry.row = mCurRow;
            entry.col = mCurCol;
            entry.value = data;
            ret = entry.insert() > 0;
        }

        if (!ret) {
            Toast.makeText(Main.this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        view = mTableData.findViewWithTag(Utils.getTag(mCurRow, mCurCol));

        if (view != null) {
            if (data.length() != 0)
                setCellState(view, STATE_DONE);
            else
                setCellState(view, STATE_NORMAL);
        }

        boolean endOfCell = false;

        mCurRow++;
        if (mCurRow > mRows || (mCurRow == mRows && (isExcludedRow(mRows) || isExcludedCell(mRows, mCurCol)))) {
            mCurRow = mRows;

            mCurCol++;
            if (mCurCol > mCols || (mCurCol == mCols && (isExcludedCol(mCols) || isExcludedCell(mCurRow, mCols)))) {
                mCurCol = mCols;
                mCurCol = mRows;

                endOfCell = true;
            } else {
                mCurRow = 1;
            }
        }

        if (!endOfCell) {
            if (isExcludedRow(mCurRow) || isExcludedCol(mCurCol) || isExcludedCell(mCurRow, mCurCol)) {
                if (!getNextFreeCell()) {
                    endOfCell = true;
                }
            }
        }

        data = getDataEntry(mGrid, mCurRow, mCurCol);

        if (data == null)
            data = "";

        mEditData.setSelectAllOnFocus(true);
        mEditData.setText(data);
        mEditData.selectAll();
        mEditData.requestFocus();

        view = mTableData.findViewWithTag(Utils.getTag(mCurRow, mCurCol));
        if (view != null) {
            if (!isExcludedRow(mCurRow) && !isExcludedCol(mCurCol) && !isExcludedCell(mCurRow, mCurCol)) {
                setCellState(view, STATE_ACTIVE);
            }
        }

        resetCurrentCell();
        curCell = view;

        if (endOfCell) {
            Utils.alert(this, Coordinate.mAppName, getString(R.string.grid_filled));
            completeSound();
        }
    }

    public void completeSound() {
        try {
            int resID = getResources().getIdentifier("plonk", "raw", getPackageName());
            MediaPlayer chimePlayer = MediaPlayer.create(Main.this, resID);
            chimePlayer.start();

            chimePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    private void resetCurrentCell() {
        if (curCell != null) {
            int r = -1;
            int c = -1;

            Object obj;

            obj = curCell.getTag(R.string.cell_col);
            if (obj instanceof Integer)
                c = (Integer) obj;

            obj = curCell.getTag(R.string.cell_row);
            if (obj instanceof Integer)
                r = (Integer) obj;

            if (isExcludedRow(r) || isExcludedCol(c) || isExcludedCell(r, c)) {
                setCellState(curCell, STATE_INACTIVE);
            } else {
                String data = getDataEntry(mGrid, r, c);
                if (data == null)
                    data = "";

                if (data.length() == 0)
                    setCellState(curCell, STATE_NORMAL);
                else
                    setCellState(curCell, STATE_DONE);
            }
        }
    }

    private boolean deleteTemplate(long id) {
        boolean ret;
        Template tmp = new Template();
        if (!tmp.get(id)) {
            return false;
        }

        if (tmp.type == TYPE_SEED || tmp.type == TYPE_DNA) {
            makeToast(getString(R.string.template_not_deleted_default));
            return false;
        }

        Grid grid = new Grid();
        Cursor cursor = grid.loadByTemplate(id);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Grid g = new Grid();
                if (g.copy(cursor)) {
                    deleteGrid(g.id);
                }
            }
            cursor.close();
        }

        ret = tmp.delete(id);
        return ret;
    }

    private boolean deleteGrid(long id) {
        boolean ret;

        Grid grid = new Grid();
        ret = grid.delete(id);

        Entry entry = new Entry();
        entry.deleteByGrid(id);

        return ret;
    }

    private long createGrid(long template) {
        Grid grid = new Grid();
        grid.template = template;
        grid.stamp = System.currentTimeMillis();
        grid.title = mOptions.get(0).value;
        return grid.insert();
    }

    private void newTemplate(int type) {
        mType = type;
        Template temp = new Template();
        temp.title = mTemplate;
        temp.type = type;
        temp.cols = mCols;
        temp.rows = mRows;

        temp.ecells = Utils.pointToJson(mExcludeCells);
        temp.ecols = Utils.listToJson(mExcludeCols);
        temp.erows = Utils.listToJson(mExcludeRows);

        temp.options = Utils.optionsToJson(mOptions);

        temp.cnumbering = mColNumbering ? 1 : 0;
        temp.rnumbering = mRowNumbering ? 1 : 0;

        temp.stamp = System.currentTimeMillis();

        long id = temp.insert();
        if (id > 0) {
            // deleteTemplate(mId); //TODO

            mId = id;

            long grid = createGrid(mId);
            if (grid > 0) {
                mGrid = grid;

                SharedPreferences.Editor ed = ep.edit();
                ed.putLong("CurrentGrid", grid);
                ed.apply();

                populateTemplate();
                showTemplateUI();
            } else {
                Utils.alert(this, Coordinate.mAppName, getString(R.string.create_grid_fail));
            }
        } else {
            Utils.alert(this, Coordinate.mAppName, getString(R.string.create_template_fail));
        }
    }

    private void loadTemplate(int type) {
        mType = type;

        long grid = createGrid(mId);
        if (grid > 0) {
            mGrid = grid;

            SharedPreferences.Editor ed = ep.edit();
            ed.putLong("CurrentGrid", grid);
            ed.apply();

            mLayoutOptional.setVisibility(View.VISIBLE);
            mLayoutGrid.setVisibility(View.VISIBLE);

            populateTemplate();
            showTemplateUI();
        } else {
            Utils.alert(this, Coordinate.mAppName, getString(R.string.create_grid_fail));
        }

    }

    private void showTemplateUI() {
        mTextTemplate.setText(mTemplate);

        mLayoutMain.setVisibility(View.VISIBLE);

        String data = getDataEntry(mGrid, 1, 1);
        if (data == null)
            data = "";
        mEditData.setText(data);
    }

    // first non excluded cell
    private boolean getNextFreeCell() {
        int c;
        int r = 1;

        for (c = mCurCol; c <= mCols; c++) {
            if (isExcludedCol(c))
                continue;

            for (r = mCurRow; r <= mRows; r++) {
                if (isExcludedRow(r))
                    continue;

                if (!isExcludedCell(r, c)) {
                    break;
                }
            }
            break;
        }

        mCurRow = r;
        mCurCol = c;
        return true;
    }

    private void populateTemplate() {
        mCurRow = 1;
        mCurCol = 1;

        getNextFreeCell();

        LayoutInflater inflater = getLayoutInflater();

        mLayoutOptional.removeAllViews();

        for (int i = 0; i < mOptions.size(); i++) {
            View view = inflater.inflate(R.layout.optional_line, new LinearLayout(this), false);
            TextView fieldText = (TextView) view.findViewById(R.id.fieldText);
            TextView valueText = (TextView) view.findViewById(R.id.valueText);

            OptionalField opt = mOptions.get(i);
            if (opt == null || !opt.checked)
                continue;

            if (i == 0) {
                fieldText.setText(opt.field);
                valueText.setText(mGridTitle);
            } else {
                fieldText.setText(opt.field);
                valueText.setText(opt.value);
            }

            mLayoutOptional.addView(view);
        }

        mTableData.removeAllViews();

        // header
        @SuppressLint("InflateParams")
        TableRow hrow = (TableRow) inflater.inflate(R.layout.table_row, null);
        int chcol = 0;
        for (int c = 0; c < (mCols + 1); c++) {
            @SuppressLint("InflateParams")
            LinearLayout cell_top = (LinearLayout) inflater.inflate(R.layout.table_cell_top, null);
            TextView cell_txt = (TextView) cell_top.findViewById(R.id.dataCell);

            if (c == 0) {
                cell_txt.setText("");
            } else {
                cell_txt.setText("" + (mColNumbering ? c : Character.toString((char) ('A' + chcol)))); // Row
                chcol++;
                if (chcol >= 26)
                    chcol = 0;
            }
            hrow.addView(cell_top);
        }
        mTableData.addView(hrow);

        // body
        int chrow = 0;
        for (int r = 1; r < (mRows + 1); r++) {
            @SuppressLint("InflateParams")
            TableRow brow = (TableRow) inflater.inflate(R.layout.table_row, null);

            boolean excludedRow = isExcludedRow(r);

            for (int c = 0; c < (mCols + 1); c++) {
                boolean excludedCol = isExcludedCol(c);

                @SuppressLint("InflateParams")
                LinearLayout cell_box = (LinearLayout) inflater.inflate(R.layout.table_cell_box, null);
                TextView cell_cnt = (TextView) cell_box.findViewById(R.id.dataCell);

                @SuppressLint("InflateParams")
                LinearLayout cell_left = (LinearLayout) inflater.inflate(R.layout.table_cell_left, null);
                TextView cell_num = (TextView) cell_left.findViewById(R.id.dataCell);

                if (c == 0) {
                    cell_num.setText("" + (mRowNumbering ? r : Character.toString((char) ('A' + chrow)))); // Row
                    chrow++;
                    if (chrow >= 26)
                        chrow = 0;

                    brow.addView(cell_left);
                } else {
                    String data = getDataEntry(mGrid, r, c);
                    setCellState(cell_cnt, STATE_NORMAL);

                    if (data != null && data.trim().length() != 0) {
                        setCellState(cell_cnt, STATE_DONE);
                    }

                    if (r == mCurRow && c == mCurCol) {
                        setCellState(cell_cnt, STATE_ACTIVE);
                        curCell = cell_cnt;
                    }

                    if (excludedRow || excludedCol || isExcludedCell(r, c)) {
                        setCellState(cell_cnt, STATE_INACTIVE);
                        saveExcludedCell(r, c);
                    }

                    if (data != null && data.equals("exclude")) {
                        setCellState(cell_cnt, STATE_INACTIVE);
                        mExcludeCells.add(new Point(c, r));
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

    private void saveExcludedCell(int r, int c) {

        boolean ret;

        Entry entry = new Entry();
        if (entry.getByGrid(mGrid, r, c)) {
            entry.value = "exclude";
            ret = entry.update();
        } else {
            entry.grid = mGrid;
            entry.row = r;
            entry.col = c;
            entry.value = "exclude";
            ret = entry.insert() > 0;
        }

        if (!ret) {
            Toast.makeText(Main.this, getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private String getDataEntry(long grid, int r, int c) {
        String data = null;

        Entry entry = new Entry();
        if (entry.getByGrid(grid, r, c)) {
            data = entry.value;
        }

        return data;
    }

    private void setCellState(View cell, int state) {
        if (state == STATE_DONE)
            cell.setBackgroundResource(R.drawable.table_cell_done);
        else if (state == STATE_ACTIVE)
            cell.setBackgroundResource(R.drawable.table_cell_active);
        else if (state == STATE_INACTIVE)
            cell.setBackgroundResource(R.drawable.table_cell_inactive);
        else
            cell.setBackgroundResource(R.drawable.table_cell);
    }

    private void exportData() {
        String name = mOptions.get(0).value + "_" + Utils.getDateFormat(System.currentTimeMillis()).replace(".", "_");

        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.file_input, null);

        final EditText nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        nameEdit.setText(name);

        Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.filename_set));
        dialog.setView(view);
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                String filename = nameEdit.getText().toString().trim();
                if (filename.length() == 0) {
                    Utils.alert(Main.this, Coordinate.mAppName, getString(R.string.filename_empty));
                    return;
                }

                File path = new File(Constants.EXPORT_PATH, mTemplate);
                createDir(path);

                mTask = new DataExporter(Main.this, mId, filename, path.getAbsolutePath());
                mTask.execute();
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void cancelTask() {
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public class DataExporter extends AsyncTask<Void, String, Boolean> {
        private Context mContext;
        private ProgressDialog mDlg;
        private long mTempId;

        private String mTempPath;
        private String mTempName;

        private Template mTemplate;

        private String mMsg = null;

        private File mFile;

        public DataExporter(Context c, long id, String name, String path) {
            mContext = c;
            mTempId = id;
            mTempName = name;
            mTempPath = path;
        }

        protected void onProgressUpdate(String... msg) {
            if (msg == null)
                return;

            String text = msg[0];
            if (text == null)
                return;

            mDlg.setMessage(text);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDlg = new ProgressDialog(mContext);
            mDlg.setTitle(getString(R.string.exporting_title));
            mDlg.setMessage(getString(R.string.exporting_body));
            mDlg.setCancelable(true);
            mDlg.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface d) {
                    cancelTask();
                }
            });
            mDlg.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (mDlg != null && mDlg.isShowing()) {
                mDlg.dismiss();
                mDlg = null;
            }

            //TODO when grid is reset, make a new one
            if (result != null && result) {
                mLastExportGridId = mGrid;
                Utils.alert(mContext, Coordinate.mAppName, getString(R.string.export_success), new Runnable() {
                    @Override
                    public void run() {
                        Utils.confirm(mContext, Coordinate.mAppName, getString(R.string.clear_grid),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Entry entry = new Entry();
                                        if (entry.deleteByGrid(mGrid)) {
                                            populateTemplate();
                                            showTemplateUI();
                                        } else {
                                            Utils.toast(mContext, getString(R.string.clear_fail));
                                        }

                                        share();
                                    }
                                },
                                new Runnable() {

                                    @Override
                                    public void run() {
                                        share();
                                    }

                                });
                    }
                });
            } else {
                if (mMsg == null)
                    mMsg = getString(R.string.export_no_data);

                Utils.alert(mContext, getString(R.string.export), mMsg);
            }
        }

        public void share() {
            String path = mFile.getAbsolutePath();

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, getString(R.string.share_file)));
        }

        @Override
        protected Boolean doInBackground(Void... bparams) {
            boolean ret;

            mTemplate = new Template();
            if (!mTemplate.get(mTempId)) {
                mMsg = getString(R.string.import_template_failed);
                return false;
            }

            if (mTemplate.type == TYPE_SEED) {
                ret = exportSeed();
            } else if (mTemplate.type == TYPE_DNA) {
                ret = exportDna();
            } else {
                ret = exportDefault();
            }

            return ret;
        }

        private boolean exportDefault() {
            boolean ret = false;

            String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) {
                mFile.delete();
            }

            CsvWriter csvOutput;
            FileWriter writer;

            try {

                writer = new FileWriter(mFile, false);
                csvOutput = new CsvWriter(writer, ',');

                //Titles
                csvOutput.write("Value");
                csvOutput.write("Column");
                csvOutput.write("Row");

                for (int i = 0; i < mOptions.size(); i++) {
                    OptionalField opt = mOptions.get(i);
                    csvOutput.write(opt.field);
                }

                csvOutput.endRecord();

                int row;
                int col;
                for (int c = 0; c < mTemplate.cols; c++) {
                    col = c + 1;
                    for (int r = 0; r < mTemplate.rows; r++) {
                        row = r + 1;

                        String data;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col)) {
                            data = "exclude";
                        } else {
                            data = getDataEntry(mGrid, row, col);
                            if (data == null) {
                                data = "";
                            }
                        }

                        csvOutput.write(data);
                        csvOutput.write(String.valueOf(col));
                        csvOutput.write(String.valueOf(row));

                        for (int i = 0; i < mOptions.size(); i++) {
                            OptionalField opt = mOptions.get(i);
                            csvOutput.write(opt.value);
                        }

                        csvOutput.endRecord();
                    }

                    publishProgress(getString(R.string.exporting_column_title) + col);
                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }

        private boolean exportDna() {
            boolean ret = false;
            String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) {
                mFile.delete();
            }

            CsvWriter csvOutput;
            FileWriter writer;

            String date = "";
            String plate_id = "";
            String plate_name = "";

            String dna_person = "";
            String notes = "";
            String tissue_type = "";
            String extraction = "";

            for (int i = 0; i < mOptions.size(); i++) {
                OptionalField opt = mOptions.get(i);
                if (opt == null)
                    continue;

                if (opt.field.equalsIgnoreCase("date")) {
                    date = opt.value;
                } else if (opt.field.equalsIgnoreCase("Plate")) {
                    plate_id = opt.value;
                } else if (opt.field.equalsIgnoreCase("Plate Name")) {
                    plate_name = opt.value;
                } else if (opt.field.equalsIgnoreCase("Notes")) {
                    notes = opt.value;
                } else if (opt.field.equalsIgnoreCase("tissue_type")) {
                    tissue_type = opt.value;
                } else if (opt.field.equalsIgnoreCase("extraction")) {
                    extraction = opt.value;
                } else if (opt.field.equalsIgnoreCase("person")) {
                    dna_person = opt.value;
                } else if (opt.field.equalsIgnoreCase("date")) {
                    date = opt.value;
                }
            }

            try {
                writer = new FileWriter(mFile, false);

                csvOutput = new CsvWriter(writer, ',');

                csvOutput.write("date");
                csvOutput.write("plate_id");
                csvOutput.write("plate_name");
                csvOutput.write("sample_id");
                csvOutput.write("well_A01");
                csvOutput.write("well_01A");
                csvOutput.write("tissue_id");
                csvOutput.write("dna_person");
                csvOutput.write("notes");
                csvOutput.write("tissue_type");
                csvOutput.write("extraction");

                csvOutput.endRecord();

                int row, col;
                for (int c = 0; c < mTemplate.cols; c++) {
                    col = c + 1;
                    for (int r = 0; r < mTemplate.rows; r++) {
                        row = r + 1;

                        String rowName = Character.toString((char) ('A' + r));
                        String colName = String.format("%02d", col);
                        String sample_id = String.format("%s_%s%s", plate_id, rowName, colName);

                        String tissue_id;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col)) {
                            tissue_id = "BLANK_" + sample_id;
                        } else {
                            tissue_id = getDataEntry(mGrid, row, col);
                            if (tissue_id == null || tissue_id.trim().length() == 0) {
                                tissue_id = "BLANK_" + sample_id;
                            }
                        }

                        csvOutput.write(date);
                        csvOutput.write(plate_id);
                        csvOutput.write(plate_name);
                        csvOutput.write(sample_id); // sample_id
                        csvOutput.write(String.format("%s%s", rowName, colName)); // well_A01
                        csvOutput.write(String.format("%s%s", colName, rowName)); // well_01A
                        csvOutput.write(tissue_id);
                        csvOutput.write(dna_person.replace(" ", "_"));
                        csvOutput.write(notes);
                        csvOutput.write(tissue_type);
                        csvOutput.write(extraction);

                        csvOutput.endRecord();

                    }
                    publishProgress(getString(R.string.exporting_column_title) + col);
                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }

        private boolean exportSeed() {
            boolean ret = false;

            String outputFile = mTempName + ".csv";

            mFile = new File(mTempPath, outputFile);

            if (mFile.exists()) {
                mFile.delete();
            }

            CsvWriter csvOutput;
            FileWriter writer;

            String person = "";
            String date = "";
            String trayid = "";

            for (int i = 0; i < mOptions.size(); i++) {
                OptionalField opt = mOptions.get(i);
                if (opt == null)
                    continue;

                if (opt.field.equalsIgnoreCase("Tray")) {
                    trayid = opt.value;
                } else if (opt.field.equalsIgnoreCase("Person")) {
                    person = opt.value;
                } else if (opt.field.equalsIgnoreCase("date")) {
                    date = opt.value;
                }
            }

            try {
                writer = new FileWriter(mFile, false);

                csvOutput = new CsvWriter(writer, ',');

                csvOutput.write("tray_id");
                csvOutput.write("cell_id");
                csvOutput.write("tray_num");
                csvOutput.write("tray_column");
                csvOutput.write("tray_row");
                csvOutput.write("seed_id");
                csvOutput.write("person");
                csvOutput.write("date");
                csvOutput.endRecord();

                int row;
                int col;
                for (int c = 0; c < mTemplate.cols; c++) {
                    col = c + 1;
                    for (int r = 0; r < mTemplate.rows; r++) {
                        row = r + 1;

                        String data;
                        if (isExcludedRow(row) || isExcludedCol(col) || isExcludedCell(row, col)) {
                            data = "exclude";
                        } else {
                            data = getDataEntry(mGrid, row, col);
                            if (data == null)
                                data = "BLANK_";
                        }

                        csvOutput.write(trayid); // tray id
                        csvOutput.write(String.format("%s_C%02d_R%d", mTempName, col, row)); // "cell_id"
                        csvOutput.write(""); // "tray_num"
                        csvOutput.write(String.valueOf(col)); // "tray_column"
                        csvOutput.write(String.valueOf(row)); // "tray_row"
                        csvOutput.write(data); // "seed_id"
                        csvOutput.write(person.replace(" ", "_")); // "person"
                        csvOutput.write(date); // "date"
                        csvOutput.endRecord();
                    }

                    publishProgress(getString(R.string.exporting_column_title) + col);

                }

                csvOutput.close();
                makeFileDiscoverable(mFile, Main.this);

                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
                mMsg = getString(R.string.export_failed);
            }

            return ret;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (scanResult != null) {
            String barcodeText = scanResult.getContents();
            mEditData.setText(barcodeText);
            saveData();
        }
    }

    public void makeFileDiscoverable(File file, Context context) {
        MediaScannerConnection.scanFile(context,
                new String[]{file.getPath()}, null, null);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
    }
}
