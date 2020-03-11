package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Context
 * android.content.Intent
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
 *
 * org.wheatgenetics.coordinate.collector.Collector
 * org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.BackActivity
 * org.wheatgenetics.coordinate.R
 */
public class CollectorActivity extends org.wheatgenetics.coordinate.BackActivity implements
org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
{
    private static final java.lang.String GRID_ID_KEY = "gridId";

    // region Fields
    private org.wheatgenetics.coordinate.collector.Collector collectorInstance = null;  // lazy load

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.collector.Collector collector()
    {
        if (null == this.collectorInstance) this.collectorInstance =
            new org.wheatgenetics.coordinate.collector.Collector(this);
        return this.collectorInstance;
    }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_collector);

        @androidx.annotation.Nullable final android.content.Intent intent = this.getIntent();
        if (null != intent)
        {
            final java.lang.String GRID_ID_KEY =
                org.wheatgenetics.coordinate.CollectorActivity.GRID_ID_KEY;
            if (intent.hasExtra(GRID_ID_KEY)) this.collector().loadJoinedGridModel(
                intent.getLongExtra(GRID_ID_KEY,-1));
        }
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_collector, menu);
        return true;
    }

    @java.lang.Override protected void onStart()
    { super.onStart(); this.collector().populateFragments(); }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        this.collector().parseActivityResult(requestCode, resultCode, data);
    }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.collectorInstance) this.collectorInstance.release();
        super.onDestroy();
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.collector().getDisplayModel(); }

    @androidx.annotation.Nullable @java.lang.Override
    public org.wheatgenetics.coordinate.model.ElementModel toggle(
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { return this.collector().toggle(elementModel); }

    @java.lang.Override public int getActiveRow() { return this.collector().getActiveRow(); }
    @java.lang.Override public int getActiveCol() { return this.collector().getActiveCol(); }

    @java.lang.Override public void activate(final int row, final int col)
    { this.collector().activate(row, col); }

    @java.lang.Override @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker()
    { return this.collector().getChecker(); }
    // endregion

    // region org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public java.lang.String getEntryValue()
    { return this.collector().getEntryValue(); }

    @java.lang.Override public java.lang.String getProjectTitle()
    { return this.collector().getProjectTitle(); }

    @java.lang.Override public java.lang.String getTemplateTitle()
    { return this.collector().getTemplateTitle(); }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return this.collector().getOptionalFields(); }

    @java.lang.Override public void saveEntry(final java.lang.String entryValue)
    { this.collector().saveEntry(entryValue); }

    @java.lang.Override public void clearEntry() { this.collector().clearEntry(); }
    // endregion
    // endregion

    // region MenuItem Event Handler
    public void onCameraMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.collector().scanBarcode(); }
    // endregion

    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.NonNull            final android.content.Context context,
    @androidx.annotation.IntRange(from = 1) final long                    gridId )
    {
        @androidx.annotation.NonNull final android.content.Intent result =
            null == org.wheatgenetics.coordinate.CollectorActivity.INTENT_INSTANCE ?
                org.wheatgenetics.coordinate.CollectorActivity.INTENT_INSTANCE =
                    new android.content.Intent(context,
                        org.wheatgenetics.coordinate.CollectorActivity.class) :
                org.wheatgenetics.coordinate.CollectorActivity.INTENT_INSTANCE;
        return result.putExtra(org.wheatgenetics.coordinate.CollectorActivity.GRID_ID_KEY, gridId);
    }
}