package org.wheatgenetics.coordinate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.phenoapps.androidlibrary.ClearingEditorActionListener;
import org.wheatgenetics.coordinate.collector.Collector;
import org.wheatgenetics.coordinate.collector.DataEntryDialogFragment;
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment;
import org.wheatgenetics.coordinate.grids.GridsActivity;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.projects.ProjectsActivity;
import org.wheatgenetics.coordinate.templates.TemplatesActivity;

public class CollectorActivity extends BackActivity implements
        GridDisplayFragment.Handler,
        DataEntryDialogFragment.Handler,
        ClearingEditorActionListener.Receiver {

    public static final String COLLECTOR_LAST_GRID = "org.wheatgenetics.coordinate.keys.COLLECTOR_LAST_GRID";
    public static final String TAG = "CollectorActivity";
    private static final String GRID_ID_KEY = "gridId";
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private long mGridId = -1;
    private Collector collectorInstance = null;  // lazy load
    // endregion

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
                this.collector().loadJoinedGridModel(mGridId);
            }
        }

        @Nullable final ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(null);
        }

        EditText dataEntryEt = findViewById(R.id.act_collector_data_entry_et);
        if (dataEntryEt != null) {
            dataEntryEt.setOnEditorActionListener(
                    new ClearingEditorActionListener(dataEntryEt,
                    this, BuildConfig.DEBUG));
        }

        attachKeyboardListeners();
        setupBottomNavigationBar();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_collector, menu);
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
        this.collector().parseActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (null != this.collectorInstance) this.collectorInstance.release();
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
            final int about = R.id.action_nav_about;

            //when navigating to another tab when collecting, save the current grid id
            //to re-navigate here later
            if (mGridId != -1L) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                prefs.edit().putLong(COLLECTOR_LAST_GRID, mGridId).apply();
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
                case about:
                    startActivity(new Intent(this, AboutActivity.class));
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
    public String getProjectTitle() { return this.collector().getProjectTitle(); }

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

        final int summarizeData = R.id.action_summarize_data;
        if (item.getItemId() == summarizeData) {
            new DataEntryDialogFragment().show(getSupportFragmentManager(), TAG);
        } else if (item.getItemId() == R.id.home) {
            startActivity(GridsActivity.intent(this));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void receiveText(final String text) {
        saveEntry(text);
    }

    @Override
    public void clearText() {
        clearEntry();
    }
}