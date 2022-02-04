package org.wheatgenetics.coordinate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import org.wheatgenetics.coordinate.collector.Collector;
import org.wheatgenetics.coordinate.collector.DataEntryFragment;
import org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.utils.Keys;

public class CollectorActivity extends BackActivity implements
        GridDisplayFragment.Handler,
        DataEntryFragment.Handler {
    private static final String GRID_ID_KEY = "gridId";
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
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
            long gridId = intent.getLongExtra(GRID_ID_KEY, -1);
            if (intent.hasExtra(GRID_ID_KEY)) this.collector().loadJoinedGridModel(gridId);

            saveGridIdToPreferences(gridId);
        }
    }

    //whenever collector activity is opened save the grid id to preferences
    //on next app-open, navigate to this grid
    private void saveGridIdToPreferences(long gridId) {

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putLong(Keys.Companion.getLAST_GRID_KEY(), gridId)
                .apply();
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
        super.onDestroy();
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
}