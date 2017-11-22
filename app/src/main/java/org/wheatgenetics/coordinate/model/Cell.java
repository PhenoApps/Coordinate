package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 *
 * org.json.JSONException
 * org.json.JSONObject
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Cell extends java.lang.Object implements java.lang.Cloneable
{
    private final android.graphics.Point point;

    private static int random(final int bound)
    { return new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(bound - 1) + 1; }

    Cell(final int x, final int y) { super(); this.point = new android.graphics.Point(x, y); }

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
    static org.wheatgenetics.coordinate.model.Cell random(final int xBound, final int yBound)
    {
        return new org.wheatgenetics.coordinate.model.Cell(
            org.wheatgenetics.coordinate.model.Cell.random(xBound),
            org.wheatgenetics.coordinate.model.Cell.random(yBound));
    }

    org.json.JSONObject json() throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put("row", this.point.y);                            // throws org.json.JSONException
        result.put("col", this.point.x);                            // throws org.json.JSONException

        return result;
    }
    // endregion
}