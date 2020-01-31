package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.DisplayTemplateModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.RowOrCols
 *
 * org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler
 */
public class ExcludeCellsActivity extends androidx.appcompat.app.AppCompatActivity
implements org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler
{
    @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) private static class DisplayModel
    extends java.lang.Object implements org.wheatgenetics.coordinate.model.DisplayModel
    {
        // region Fields
        @androidx.annotation.IntRange(from = 1) private final int     rows        , cols        ;
                                                private final boolean colNumbering, rowNumbering;
        // endregion

        private DisplayModel(
        @androidx.annotation.IntRange(from = 1) final int               rows  ,
        @androidx.annotation.IntRange(from = 1) final int               cols  ,
        @androidx.annotation.NonNull            final android.os.Bundle bundle)
        {
            super();

            this.rows = rows; this.cols = cols;

            this.colNumbering = bundle.getBoolean(
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.COL_NUMBERING_BUNDLE_KEY);
            this.rowNumbering = bundle.getBoolean(
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROW_NUMBERING_BUNDLE_KEY);
        }

        // region Overridden Methods
        @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getRows()
        { return this.rows; }

        @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getCols()
        { return this.cols; }

        @java.lang.Override public boolean getColNumbering() { return this.colNumbering; }
        @java.lang.Override public boolean getRowNumbering() { return this.rowNumbering; }

        @java.lang.Override @androidx.annotation.Nullable
        public org.wheatgenetics.coordinate.model.ElementModel getElementModel(
        @androidx.annotation.IntRange(from = 1) final int row,
        @androidx.annotation.IntRange(from = 1) final int col)
        { return new org.wheatgenetics.coordinate.model.Cell(/* row => */ row, /* col => */ col); }
        // endregion
    }

    // region Fields
    private org.wheatgenetics.coordinate.tc.ExcludeCellsActivity.DisplayModel displayModel = null;

    private org.wheatgenetics.coordinate.model.Cells     excludedCells = null                     ;
    private org.wheatgenetics.coordinate.model.RowOrCols excludedRows  = null, excludedCols = null;
    // endregion

    // region Private Methods
    private boolean isExcludedCell(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell cell)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedCells ? false : this.excludedCells.contains(cell);
    }

    private boolean isExcludedRow(final int row)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedRows ? false : this.excludedRows.contains(row);
    }

    private boolean isExcludedCol(final int col)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedCols ? false : this.excludedCols.contains(col);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_exclude_cells);

        final android.os.Bundle bundle = this.getIntent().getExtras();
        if (null != bundle)
        {
            final java.lang.String
                ROWS_BUNDLE_KEY =
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_BUNDLE_KEY,
                COLS_BUNDLE_KEY =
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_BUNDLE_KEY;
            if (bundle.containsKey(ROWS_BUNDLE_KEY) && bundle.containsKey(COLS_BUNDLE_KEY))
            {
                @androidx.annotation.IntRange(from = 1) final int
                    rows = bundle.getInt(ROWS_BUNDLE_KEY), cols = bundle.getInt(COLS_BUNDLE_KEY);
                this.displayModel =
                    new org.wheatgenetics.coordinate.tc.ExcludeCellsActivity.DisplayModel(
                        rows, cols, bundle);
                {
                    final java.lang.String EXCLUDED_CELLS_BUNDLE_KEY = org.wheatgenetics
                        .coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY;
                    if (bundle.containsKey(EXCLUDED_CELLS_BUNDLE_KEY))
                        this.excludedCells = new org.wheatgenetics.coordinate.model.Cells(
                            /* json   => */ bundle.getString(EXCLUDED_CELLS_BUNDLE_KEY),
                            /* maxRow => */ rows                                       ,
                            /* maxCol => */ cols                                       );
                }
                {
                    final java.lang.String EXCLUDED_ROWS_BUNDLE_KEY = org.wheatgenetics
                        .coordinate.model.DisplayTemplateModel.EXCLUDED_ROWS_BUNDLE_KEY;
                    if (bundle.containsKey(EXCLUDED_ROWS_BUNDLE_KEY))
                        this.excludedRows = new org.wheatgenetics.coordinate.model.RowOrCols(
                            /* json     => */ bundle.getString(EXCLUDED_ROWS_BUNDLE_KEY),
                            /* maxValue => */ rows                                      );
                }
                final java.lang.String EXCLUDED_COLS_BUNDLE_KEY = org.wheatgenetics
                    .coordinate.model.DisplayTemplateModel.EXCLUDED_COLS_BUNDLE_KEY;
                if (bundle.containsKey(EXCLUDED_COLS_BUNDLE_KEY))
                    this.excludedCols = new org.wheatgenetics.coordinate.model.RowOrCols(
                        /* json     => */ bundle.getString(EXCLUDED_COLS_BUNDLE_KEY),
                        /* maxValue => */ cols                                      );
            }
        }
    }

    @java.lang.Override public void onBackPressed()
    {
        if (null != this.excludedCells)
        {
            final android.content.Intent intent = new android.content.Intent();
            {
                final android.os.Bundle bundle = new android.os.Bundle();
                bundle.putString(
                    org.wheatgenetics.coordinate.model
                        .DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY,
                    this.excludedCells.json());
                intent.putExtras(bundle);
            }
            this.setResult(android.app.Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    // region org.wheatgenetics.coordinate.tc.TemplateDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.displayModel; }

    @java.lang.Override public void toggle(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (null != elementModel)
        {
            final org.wheatgenetics.coordinate.model.Cell cell =
                (org.wheatgenetics.coordinate.model.Cell) elementModel;
            if (null != this.excludedCells)
                if (this.isExcludedCell(cell))
                    this.excludedCells.remove(cell);
                else
                    this.excludedCells.add(cell);
        }
    }

    @java.lang.Override
    public boolean isExcluded(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        if (null == cell)
            return false;
        else
            if (this.isExcludedRow(cell.getRowValue()))
                return true;
            else
                // noinspection SimplifiableConditionalExpression
                return this.isExcludedCol(cell.getColValue()) ? true : this.isExcludedCell(cell);
    }
    // endregion
    // endregion
}