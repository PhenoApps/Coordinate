package org.wheatgenetics.coordinate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.phenoapps.androidlibrary.ClearingEditorActionListener;
import org.wheatgenetics.coordinate.activity.GridCreatorActivity;
import org.wheatgenetics.coordinate.collector.Collector;
import org.wheatgenetics.coordinate.collector.DataEntryDialogFragment;
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.GeneralKeys;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;
import org.wheatgenetics.coordinate.utils.Keys;
import org.wheatgenetics.coordinate.utils.TapTargetUtil;

public class CollectorActivity extends BackActivity implements
        GridDisplayFragment.Handler,
        DataEntryDialogFragment.Handler,
        ClearingEditorActionListener.Receiver {

    public static final int PROJECT_UPDATE_REQUEST_CODE = 12;

    public static final String TAG = "CollectorActivity";
    private static final String GRID_ID_KEY = "gridId";
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private long mGridId = -1;
    private Collector collectorInstance = null;  // lazy load
    // endregion

    private Menu systemMenu;

    @NonNull
    public static Intent intent(
            @NonNull final Context context,
            @IntRange(from = 1) final long gridId) {
        @NonNull final Intent result =
                null == CollectorActivity.INTENT_INSTANCE ?
                        CollectorActivity.INTENT_INSTANCE =
                                new Intent(context,
                                        CollectorActivity.class) :
                        CollectorActivity.INTENT_INSTANCE;
        return result.putExtra(CollectorActivity.GRID_ID_KEY, gridId);
    }

    @NonNull
    private Collector collector() {
        if (null == this.collectorInstance) this.collectorInstance =
                new Collector(this);
        return this.collectorInstance;
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_collector);

        @Nullable final Intent intent = this.getIntent();
        if (null != intent) {
            final String GRID_ID_KEY =
                    CollectorActivity.GRID_ID_KEY;
            if (intent.hasExtra(GRID_ID_KEY)) {
                mGridId = intent.getLongExtra(GRID_ID_KEY, -1L);
                if (mGridId != -1L) {
                    this.collector().loadJoinedGridModel(mGridId);
                    saveGridIdToPreferences(mGridId);
                }
            }
        }

        @Nullable final ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(null);
        }

        EditText dataEntryEt = findViewById(R.id.act_collector_data_entry_et);
        if (dataEntryEt != null) {
            dataEntryEt.setOnEditorActionListener((TextView v, int actionId, KeyEvent key) -> {
                // handle both IME_ACTION_DONE (soft keyboard) and external barcode scanner
                if (actionId == EditorInfo.IME_ACTION_DONE) {  // Only handle DONE, not ENTER
                    String text = v.getText().toString();
                    saveEntry(text);
                    return true;
                }
                return false;
            });

            keepCollectorETinFocus(dataEntryEt);
        }

        attachKeyboardListeners();
        setupBottomNavigationBar();
        setupBarcodeButton();
    }

    // handle external barcode scanning
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            EditText dataEntryEt = findViewById(R.id.act_collector_data_entry_et);
            if (!dataEntryEt.hasFocus()) dataEntryEt.requestFocus();

            String barcode = dataEntryEt.getText().toString();
            if (!barcode.isEmpty()) {
                saveEntry(barcode);
                dataEntryEt.requestFocus();
            }
            return true;
        }
        return false;
    }

    private void keepCollectorETinFocus(EditText dataEntryEt) {
        GridDisplayFragment gridDisplayFragment =
                (GridDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.gridDisplayFragment);

        RecyclerView recyclerView = findViewById(R.id.displayRecyclerView);
        if (recyclerView != null && gridDisplayFragment != null && gridDisplayFragment.getView() != null) {
            NestedScrollView nestedScrollView = (NestedScrollView) recyclerView.getParent().getParent();
            HorizontalScrollView horizontalScrollView = (HorizontalScrollView) recyclerView.getParent();

            View.OnTouchListener touchListener = (v, event) -> {
                if (dataEntryEt.hasFocus()) {
                    dataEntryEt.post(() -> {
                        // make sure the editText is in focus and keyboard stays visible
                        dataEntryEt.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(dataEntryEt, InputMethodManager.SHOW_IMPLICIT);
                    });
                }
                // Don't consume the event
                return false;
            };

            nestedScrollView.setOnTouchListener(touchListener);
            horizontalScrollView.setOnTouchListener(touchListener);
        }
    }

    private void setupBarcodeButton() {

        try {
            ImageButton barcodeButton = findViewById(R.id.act_collector_barcode_scan_button);
            barcodeButton.setOnClickListener((view) -> {
                try {
                    this.collector().scanBarcode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //whenever collector activity is opened save the grid id to preferences
    //on next app-open, navigate to this grid
    private void saveGridIdToPreferences(long gridId) {

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putLong(Keys.COLLECTOR_LAST_GRID, gridId)
                .apply();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_collector, menu);

        systemMenu = menu;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        systemMenu.findItem(R.id.help).setVisible(preferences.getBoolean(GeneralKeys.TIPS, false));

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.collector().populateFragments();
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROJECT_UPDATE_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                final String GRID_ID_KEY =
                        CollectorActivity.GRID_ID_KEY;
                final Intent intent = getIntent();

                if (intent.hasExtra(GRID_ID_KEY)) {

                    long gridId = intent.getLongExtra(GRID_ID_KEY, -1);

                    collectorInstance.loadJoinedGridModelThenPopulate(gridId);

                }
            }

        } else this.collector().parseActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_collector_bnv);
        bottomNavigationView.getMenu().getItem(0).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.action_nav_grids);
        bottomNavigationView.getMenu().getItem(0).setEnabled(true);
    }

    private void setupBottomNavigationBar() {

        final BottomNavigationView bottomNavigationView = findViewById(R.id.act_collector_bnv);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener((item -> {

            final int grids = R.id.action_nav_grids;
            final int templates = R.id.action_nav_templates;
            final int projects = R.id.action_nav_projects;
            final int settings = R.id.action_nav_settings;

            //when navigating to another tab when collecting, save the current grid id
            //to re-navigate here later
            if (mGridId != -1L) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                prefs.edit().putLong(Keys.COLLECTOR_LAST_GRID, mGridId).apply();
            }

            switch(item.getItemId()) {
                case templates:
                    startActivity(TemplatesActivity.intent(this));
                    break;
                case projects:
                    startActivity(ProjectsActivity.intent(this));
                    break;
                case settings:
                    startActivity(PreferenceActivity.intent(this));
                    break;
                case grids:
                    startActivity(GridsActivity.intent(this));
                    break;
                default:
                    break;
            }

            return true;
        }));
    }

    //https://stackoverflow.com/questions/25216749/soft-keyboard-open-and-close-listener-in-an-activity-in-android/25681196#25681196
    private final ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

            if(heightDiff <= contentViewTop){
                onHideKeyboard();

                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);

                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
        if (keyboardHeight > 256) {
            findViewById(R.id.act_collector_bnv).setVisibility(View.GONE);
        } else {
            onHideKeyboard();
        }
    }
    protected void onHideKeyboard() {
        findViewById(R.id.act_collector_bnv).setVisibility(View.VISIBLE);
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = (ViewGroup) findViewById(R.id.act_collector_cl);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @Override
    public DisplayModel getDisplayModel() {
        return this.collector().getDisplayModel();
    }

    @Override
    public void toggle(@Nullable final ElementModel elementModel) {
        this.collector().toggle(elementModel);
    }

    @Override
    public int getActiveRow() {
        return this.collector().getActiveRow();
    }

    @Override
    public int getActiveCol() {
        return this.collector().getActiveCol();
    }

    @Override
    public void activate(final int row, final int col) {
        this.collector().activate(row, col);
    }
    // endregion

    @Override
    @Nullable
    public CheckedIncludedEntryModel.Checker getChecker() {
        return this.collector().getChecker();
    }

    // region org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler Overridden Methods
    @Override
    public String getEntryValue() {
        return this.collector().getEntryValue();
    }

    @Override
    public String getProjectTitle() {
        return this.collector().getProjectTitle();
    }

    @Override
    public String getTemplateTitle() {
        return this.collector().getTemplateTitle();
    }

    @Override
    public NonNullOptionalFields getOptionalFields() {
        return this.collector().getOptionalFields();
    }

    @Override
    public void saveEntry(final String entryValue) {
        this.collector().saveEntry(entryValue);
    }
    // endregion
    // endregion

    @Override
    public void clearEntry() {
        this.collector().clearEntry();
    }
    // endregion

    // region MenuItem Event Handler
    public void onCameraMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.collector().scanBarcode();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_summarize_data) {
            new DataEntryDialogFragment().show(getSupportFragmentManager(), TAG);
        } else if (item.getItemId() == R.id.help) {
            TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(collectorActivityTapTargetView(R.id.export_grid, getString(R.string.tutorial_grid_export_title), getString(R.string.tutorial_grid_export_summary), 40),
                            collectorActivityTapTargetView(R.id.action_edit_grid, getString(R.string.tutorial_collector_edit_title), getString(R.string.tutorial_collector_edit_summary), 40),
                            collectorActivityTapTargetView(R.id.action_summarize_data, getString(R.string.tutorial_collector_summarize_title), getString(R.string.tutorial_collector_summarize_summary), 40),
                            collectorActivityTapTargetView(R.id.act_collector_data_entry_et, getString(R.string.tutorial_collector_collect_data_title), getString(R.string.tutorial_collector_collect_data_summary), 180),
                            collectorActivityTapTargetView(R.id.act_collector_barcode_scan_button, getString(R.string.tutorial_collector_barcode_title), getString(R.string.tutorial_collector_barcode_summary), 80)
                        );
            sequence.start();
        }else if (item.getItemId() == android.R.id.home) {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putLong(Keys.COLLECTOR_LAST_GRID, -1L).apply();

            startActivity(GridsActivity.intent(this));
        }
        return super.onOptionsItemSelected(item);
    }

    private TapTarget collectorActivityTapTargetView(int id, String title, String desc, int targetRadius) {
        return TapTargetUtil.Companion.getTapTargetSettingsView(this, findViewById(id), title, desc, targetRadius);
    }

    @Override
    public void receiveText(final String text) {
        saveEntry(text);
    }

    @Override
    public void clearText() {
        clearEntry();
    }

    public void onProjectEditMenuItemClick(MenuItem item) {

        final String GRID_ID_KEY =
                CollectorActivity.GRID_ID_KEY;
        final Intent intent = getIntent();

        if (intent.hasExtra(GRID_ID_KEY)) {

            long gridId = intent.getLongExtra(GRID_ID_KEY, -1);

            if (gridId != -1L) {

                Intent projectEditor = new Intent(this, GridCreatorActivity.class);
                projectEditor.putExtra("projectEdit", true);
                projectEditor.putExtra("gridId", gridId);
                startActivityForResult(projectEditor, PROJECT_UPDATE_REQUEST_CODE);
            }
        }
    }

    public void onExportGridMenuItemClick(MenuItem item) {

        collectorInstance.gridExportPreprocessor().preprocess(mGridId);

    }
}