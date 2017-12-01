package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class RowOrCol extends java.lang.Object implements java.lang.Cloneable
{
    private final int value;

    private static int valid(final int value)
    {
        if (value < 1) throw new java.lang.IllegalArgumentException("value must be > 0");
        return value;
    }

    // region Constructors
    RowOrCol(@android.support.annotation.IntRange(from = 1) final int value)
    { super(); this.value = org.wheatgenetics.coordinate.model.RowOrCol.valid(value); }

    RowOrCol(final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol) { this(rowOrCol.value); }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString() { return java.lang.Integer.toString(this.value); }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj)
    {
        if (null == obj)
            return false;
        else
            if (obj instanceof org.wheatgenetics.coordinate.model.RowOrCol)
            {
                final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
                    (org.wheatgenetics.coordinate.model.RowOrCol) obj;
                return this.value == rowOrCol.value;
            }
            else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    { return new org.wheatgenetics.coordinate.model.RowOrCol(this.value); }
    // endregion

    // Package Methods
    int getValue() { return this.value; }

    static org.wheatgenetics.coordinate.model.RowOrCol makeWithRandomValue(
    @android.support.annotation.IntRange(from = 1) final int maxValue)
    {
        return new org.wheatgenetics.coordinate.model.RowOrCol(
            new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(maxValue) + 1);
    }
    // endregion
}