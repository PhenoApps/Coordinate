package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Cell extends java.lang.Object implements java.lang.Cloneable, java.lang.Comparable,
org.wheatgenetics.coordinate.model.ElementModel
{
    private static final java.lang.String ROW_JSON_NAME = "row", COL_JSON_NAME = "col";

    @android.support.annotation.NonNull
    private final org.wheatgenetics.coordinate.model.RowOrCol row, col;

    // region Constructors
    /** Assigns. */
    private Cell(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol row,
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol col)
    { super(); this.row = row; this.col = col; }

    /** Creates. */
    @java.lang.SuppressWarnings({"CopyConstructorMissesField"})
    Cell(@android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell cell)
    {
        this(
            new org.wheatgenetics.coordinate.model.RowOrCol(/* rowOrCol => */ cell.getRow()),
            new org.wheatgenetics.coordinate.model.RowOrCol(/* rowOrCol => */ cell.getCol()));
    }

    /** Creates. */
    public Cell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        this(
            new org.wheatgenetics.coordinate.model.RowOrCol(/* value => */ row),
            new org.wheatgenetics.coordinate.model.RowOrCol(/* value => */ col));
    }

    /** Creates. */
    Cell(@android.support.annotation.NonNull final org.json.JSONObject jsonObject)
    throws org.json.JSONException
    {
        this(
            jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.ROW_JSON_NAME),      // throws
            jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.COL_JSON_NAME));     // throws
    }
    // endregion

    // region Overridden Methods
    @android.support.annotation.NonNull @java.lang.Override public java.lang.String toString()
    {
        return java.lang.String.format("Cell(%s, %s)",
            this.getRow().toString(), this.getCol().toString());
    }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (null == object)
            return false;
        else
            if (object instanceof org.wheatgenetics.coordinate.model.Cell)
            {
                final org.wheatgenetics.coordinate.model.Cell cell =
                    (org.wheatgenetics.coordinate.model.Cell) object;
                return this.getRow().equals(cell.getRow()) && this.getCol().equals(cell.getCol());
            }
            else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    @java.lang.Override protected java.lang.Object clone()
    { return new org.wheatgenetics.coordinate.model.Cell(this); }

    // region java.lang.Comparable Overridden Method
    @java.lang.Override
    public int compareTo(@android.support.annotation.NonNull final java.lang.Object object)
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            (org.wheatgenetics.coordinate.model.Cell) object;
        final int rowCompareResult = this.getRow().compareTo(cell.getRow());
        return 0 == rowCompareResult ? this.getCol().compareTo(cell.getCol()) : rowCompareResult;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @java.lang.Override public int getRowValue() { return this.getRow().getValue(); }
    @java.lang.Override public int getColValue() { return this.getCol().getValue(); }
    // endregion
    // endregion

    // region Package Methods
    @android.support.annotation.NonNull org.wheatgenetics.coordinate.model.RowOrCol getRow()
    { return this.row; }

    @android.support.annotation.NonNull org.wheatgenetics.coordinate.model.RowOrCol getCol()
    { return this.col; }

    @android.support.annotation.NonNull org.wheatgenetics.coordinate.model.Cell inRange(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell maxCell)
    {
        this.getRow().inRange(maxCell.getRow());        // throws java.lang.IllegalArgumentException
        this.getCol().inRange(maxCell.getCol());        // throws java.lang.IllegalArgumentException

        return this;
    }

    @android.support.annotation.NonNull org.json.JSONObject json() throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.ROW_JSON_NAME, this.getRowValue());
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.COL_JSON_NAME, this.getColValue());

        return result;
    }

    @android.support.annotation.NonNull
    static org.wheatgenetics.coordinate.model.Cell makeWithRandomValues(
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        return new org.wheatgenetics.coordinate.model.Cell(
            /* row => */ org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(maxRow),
            /* col => */ org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(maxCol));
    }
    // endregion
}