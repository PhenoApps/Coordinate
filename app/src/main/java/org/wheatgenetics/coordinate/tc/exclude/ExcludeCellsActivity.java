package org.wheatgenetics.coordinate.tc.exclude;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.res.Resources.NotFoundException
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.tc.exclude.TemplateDisplayFragment.Handler
 */
public class ExcludeCellsActivity extends androidx.appcompat.app.AppCompatActivity implements
org.wheatgenetics.coordinate.tc.exclude.TemplateDisplayFragment.Handler,
org.wheatgenetics.coordinate.StringGetter
{
    @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) private static class DisplayModel
    extends java.lang.Object implements org.wheatgenetics.coordinate.model.DisplayModel
    {
        @androidx.annotation.NonNull
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;

        private DisplayModel(@androidx.annotation.NonNull
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
        { super(); this.templateModel = templateModel; }

        // region Overridden Methods
        @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getRows()
        { return this.templateModel.getRows(); }

        @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getCols()
        { return this.templateModel.getCols(); }

        @java.lang.Override public boolean getColNumbering()
        { return this.templateModel.getColNumbering(); }

        @java.lang.Override public boolean getRowNumbering()
        { return this.templateModel.getRowNumbering(); }

        @java.lang.Override @androidx.annotation.Nullable
        public org.wheatgenetics.coordinate.model.ElementModel getElementModel(
        @androidx.annotation.IntRange(from = 1) final int row,
        @androidx.annotation.IntRange(from = 1) final int col)
        { return new org.wheatgenetics.coordinate.model.Cell(/* row => */ row, /* col => */ col); }
        // endregion
    }

    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel                   templateModel = null;
    private org.wheatgenetics.coordinate.tc.exclude.ExcludeCellsActivity.DisplayModel
        displayModel = null;
    // endregion

    // region Private Methods
    private boolean isExcludedRow(final int row)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedRow(row);
    }

    private boolean isExcludedCol(final int col)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedCol(col);
    }

    private boolean isExcludedCell(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell cell)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedCell(cell);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_exclude_cells);

        this.templateModel = org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(
            null == savedInstanceState ? this.getIntent().getExtras() : savedInstanceState,
            this                                                                );
        if (null != this.templateModel) this.displayModel =
            new org.wheatgenetics.coordinate.tc.exclude.ExcludeCellsActivity.DisplayModel(
                this.templateModel);
    }

    @java.lang.Override protected void onSaveInstanceState(
    @androidx.annotation.NonNull final android.os.Bundle outState)
    {
        if (null != this.templateModel) this.templateModel.export(outState);
        super.onSaveInstanceState(outState);
    }

    @java.lang.Override public void onBackPressed()
    {
        if (null != this.templateModel)
        {
            final android.content.Intent intent = new android.content.Intent();
            {
                final android.os.Bundle bundle = new android.os.Bundle();
                this.templateModel.export(bundle);
                intent.putExtras(bundle);
            }
            this.setResult(android.app.Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    // region org.wheatgenetics.coordinate.tc.exclude.TemplateDisplayFragment.Handler Overridden Methods
    @java.lang.Override public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel()
    { return this.displayModel; }

    @java.lang.Override public void toggle(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    {
        if (null != this.templateModel)
            this.templateModel.toggle((org.wheatgenetics.coordinate.model.Cell) elementModel);
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

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.getString(resId); }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException
    { return this.getResources().getQuantityString(resId, quantity, formatArgs); }
    // endregion
    // endregion
}