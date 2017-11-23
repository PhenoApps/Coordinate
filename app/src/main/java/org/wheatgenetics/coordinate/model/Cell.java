package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 * android.support.annotation.IntRange
 *
 * org.json.JSONException
 * org.json.JSONObject
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Cell extends java.lang.Object implements java.lang.Cloneable
{
    private static final java.lang.String COL_NAME = "col", ROW_NAME = "row";

    private final android.graphics.Point point;

    // region Private Methods
    private static int valid(final int i)
    {
        if (i < 1) throw new java.lang.IllegalArgumentException("value must be > 0");
        return i;
    }

    private static int random(final int bound)
    { return new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(bound - 1) + 1; }
    // endregion

    // region Constructors
    Cell(
    @android.support.annotation.IntRange(from = 1) final int x,
    @android.support.annotation.IntRange(from = 1) final int y)
    {
        super();
        this.point = new android.graphics.Point(
            org.wheatgenetics.coordinate.model.Cell.valid(x),
            org.wheatgenetics.coordinate.model.Cell.valid(y));
    }

    Cell(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        this(
            /* x => */ jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.COL_NAME),
            /* y => */ jsonObject.getInt(org.wheatgenetics.coordinate.model.Cell.ROW_NAME));
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString() { return this.point.toString(); }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean equals(final java.lang.Object o)
    {
        if (null == o)
            return false;
        else
            if  (o instanceof org.wheatgenetics.coordinate.model.Cell)
            {
                final org.wheatgenetics.coordinate.model.Cell c =
                    (org.wheatgenetics.coordinate.model.Cell) o;
                return this.point.equals(c.point.x, c.point.y);
            }
            else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    { return new org.wheatgenetics.coordinate.model.Cell(this.point.x, this.point.y); }
    // endregion

    // region Package Methods
    static org.wheatgenetics.coordinate.model.Cell random(
    @android.support.annotation.IntRange(from = 2) final int xBound,
    @android.support.annotation.IntRange(from = 2) final int yBound)
    {
        return new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.Cell.random(xBound),
            org.wheatgenetics.coordinate.model.Cell.random(yBound));
    }

    org.json.JSONObject json() throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.ROW_NAME, this.point.y);
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.model.Cell.COL_NAME, this.point.x);

        return result;
    }
    // endregion
}