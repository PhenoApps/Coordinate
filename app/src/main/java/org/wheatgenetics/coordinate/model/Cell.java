package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.json.JSONException
 * org.json.JSONObject
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Cell extends java.lang.Object implements java.lang.Cloneable
{
    private static final java.lang.String ROW_NAME = "row", COL_NAME = "col";

    private final org.wheatgenetics.coordinate.model.RowOrCol row, col;

    // region Constructors
    private Cell(
    final org.wheatgenetics.coordinate.model.RowOrCol row,
    final org.wheatgenetics.coordinate.model.RowOrCol col)
    { super(); this.row = row; this.col = col; }

    Cell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        this(
            new org.wheatgenetics.coordinate.model.RowOrCol(row),
            new org.wheatgenetics.coordinate.model.RowOrCol(col));
    }

    Cell(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        this(
            jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.ROW_NAME),           // throws
            jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.COL_NAME));          // throws
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @java.lang.SuppressWarnings("DefaultLocale")
    public java.lang.String toString()
    { return java.lang.String.format("Cell(%s, %s)", this.row.toString(), this.col.toString()); }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean equals(final java.lang.Object obj)
    {
        if (null == obj)
            return false;
        else
            if (obj instanceof org.wheatgenetics.coordinate.model.Cell)
            {
                final org.wheatgenetics.coordinate.model.Cell cell =
                    (org.wheatgenetics.coordinate.model.Cell) obj;
                return this.row.equals(cell.row) && this.col.equals(cell.col);
            }
            else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    {
        return new org.wheatgenetics.coordinate.model.Cell(
            new org.wheatgenetics.coordinate.model.RowOrCol(this.row),
            new org.wheatgenetics.coordinate.model.RowOrCol(this.col));
    }
    // endregion

    // region Package Methods
    static org.wheatgenetics.coordinate.model.Cell makeWithRandomValues(
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        return new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(maxRow),
            org.wheatgenetics.coordinate.model.RowOrCol.makeWithRandomValue(maxCol));
    }

    org.json.JSONObject json() throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.ROW_NAME, this.row.getValue());
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.COL_NAME, this.col.getValue());

        return result;
    }
    // endregion
}