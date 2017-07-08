package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 * android.support.annotation.NonNull
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class TemplateModel extends org.wheatgenetics.coordinate.model.PartialTemplateModel
{
    // region Fields
    private java.util.List<android.graphics.Point> excludeCells            ;
    private java.util.List<java.lang.Integer     > excludeRows, excludeCols;

    private boolean colNumbering, rowNumbering;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields;

    private long timestamp;
    // endregion

    // region Cell Private Methods
    static private int randomCoordinate(final int bound)
    { return new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(bound - 1) + 1; }

    private android.graphics.Point randomCell()
    {
        return new android.graphics.Point(
            org.wheatgenetics.coordinate.model.TemplateModel.randomCoordinate(this.getCols()),
            org.wheatgenetics.coordinate.model.TemplateModel.randomCoordinate(this.getRows()));
    }

    private boolean isAlreadyExcluded(
    @android.support.annotation.NonNull final android.graphics.Point candidateCell)
    {
        assert null != candidateCell    ;
        assert null != this.excludeCells;

        boolean result = false;

        for (final android.graphics.Point excludedCell: this.excludeCells)
        {
            assert null != excludedCell;
            if (excludedCell.equals(candidateCell.x, candidateCell.y))
            {
                result = true;
                break;
            }
        }

        return result;
    }
    // endregion

    // region Constructors
    public TemplateModel(             ) { super(  ); }  // TODO: Remove?
    public TemplateModel(final long id) { super(id); }  // TODO: Remove?

    public TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(title, type, rows, cols);

        this.colNumbering = colNumbering;
        this.rowNumbering = rowNumbering;

        this.excludeCells = new java.util.ArrayList<android.graphics.Point>();
    }

    public TemplateModel(final long id, final java.lang.String title, final int code,
    final int rows, final int cols, final java.lang.String excludeCells,
    final java.lang.String excludeRows, final java.lang.String excludeCols, final int colNumbering,
    final int rowNumbering, final java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, code, rows, cols);

        try
        {
            this.excludeCells =
                org.wheatgenetics.coordinate.utils.Utils.jsonToPointList(excludeCells);
        }
        catch (final org.json.JSONException e) { this.excludeCells = null; }

        try
        {
            this.excludeRows =
                org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(excludeRows);
        }
        catch (final org.json.JSONException e) { this.excludeRows = null; }
        try
        {
            this.excludeCols =
                org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(excludeCols);
        }
        catch (final org.json.JSONException e) { this.excludeCols = null; }

        this.colNumbering = colNumbering == 1;
        this.rowNumbering = rowNumbering == 1;

        try
        {
            this.optionalFields = new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
        }
        catch (final org.json.JSONException e) { this.optionalFields = null; }

        this.timestamp = timestamp;
    }
    // endregion

    // region toString()
    @java.lang.Override
    java.lang.String formatString()
    {
        return super.formatString() + ", excludeCells=%s, excludeRows=%s" +
            ", excludeCols=%s, options=%s, colNumbering=%b, rowNumbering=%b, stamp=%d]";
    }

    @java.lang.Override
    public java.lang.String toString()
    {
        return java.lang.String.format(this.formatString(),
            "TemplateModel"    , this.excludeCells, this.excludeRows , this.excludeCols,
            this.optionalFields, this.colNumbering, this.rowNumbering, this.timestamp  );
    }
    // endregion

    // region Public Methods
    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }

    public java.util.List<android.graphics.Point> getExcludeCells() { return this.excludeCells; }
    public void setExcludeCells(final java.util.List<android.graphics.Point> excludeCells)
    { this.excludeCells = new java.util.ArrayList<android.graphics.Point>(excludeCells); }

    // region Cell Public Methods
    public void makeOneRandomCell()
    {
        assert null != this.excludeCells;
        this.excludeCells.clear();
        this.excludeCells.add(this.randomCell());
    }

    public void makeRandomCells(int amount)
    {
        if (1 == amount)
            this.makeOneRandomCell();
        else
        {
            assert null != this.excludeCells;
            this.excludeCells.clear();
            if (amount > 1)
            {
                android.graphics.Point cell;
                do
                {
                    do
                        cell = this.randomCell();
                    while (this.isAlreadyExcluded(cell));
                    this.excludeCells.add(cell);
                }
                while (--amount > 0);
            }
        }
    }
    // endregion
    // endregion
}