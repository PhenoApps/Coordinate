package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.content.Intent
 * android.os.Bundle
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
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
 */
public class CollectorActivity extends androidx.appcompat.app.AppCompatActivity implements
org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler,
org.wheatgenetics.coordinate.collector.DataEntryFragment.Handler
{
    static final java.lang.String GRID_ID_KEY = "gridId";

    private org.wheatgenetics.coordinate.collector.Collector collectorInstance = null;  // lazy load

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.collector.Collector collector()
    {
        if (null == this.collectorInstance) this.collectorInstance =
            new org.wheatgenetics.coordinate.collector.Collector(this);
        return this.collectorInstance;
    }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_collector);

        @androidx.annotation.Nullable final android.content.Intent intent = this.getIntent();
        if (null != intent)
        {
            final java.lang.String GRID_ID_KEY =
                org.wheatgenetics.coordinate.grids.CollectorActivity.GRID_ID_KEY;
            if (intent.hasExtra(GRID_ID_KEY)) this.collector().loadJoinedGridModel(
                intent.getLongExtra(GRID_ID_KEY,-1));
        }
    }

    @java.lang.Override protected void onStart()
    { super.onStart(); this.collector().populateFragments(); }

    @java.lang.Override protected void onDestroy()
    {
        if (null != this.collectorInstance) this.collectorInstance.release();
        super.onDestroy();
    }

    // region org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.collector().getDisplayModel(); }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { this.collector().toggle(elementModel); }

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
}