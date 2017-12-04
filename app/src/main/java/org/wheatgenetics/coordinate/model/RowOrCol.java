package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class RowOrCol extends java.lang.Object implements java.lang.Cloneable, java.lang.Comparable
{
    private final int value;

    // region Constructors
    RowOrCol(@android.support.annotation.IntRange(from = 1) final int value)
    {
        super();

        if (value < 1) throw new java.lang.IllegalArgumentException("value must be > 0");
        this.value = value;
    }

    RowOrCol(final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol) { this(rowOrCol.value); }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString() { return java.lang.Integer.toString(this.value); }

    @java.lang.Override
    public boolean equals(final java.lang.Object object)
    {
        if (null == object)
            return false;
        else
            if (object instanceof org.wheatgenetics.coordinate.model.RowOrCol)
            {
                final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
                    (org.wheatgenetics.coordinate.model.RowOrCol) object;
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

    // region java.lang.Comparable Overridden Method
    @java.lang.Override
    public int compareTo(@android.support.annotation.NonNull final java.lang.Object object)
    {
        return java.lang.Integer.valueOf(this.getValue()).compareTo(
            ((org.wheatgenetics.coordinate.model.RowOrCol) object).getValue());
    }
    // endregion
    // endregion

    // region Package Methods
    int getValue() { return this.value; }

    org.wheatgenetics.coordinate.model.RowOrCol inRange(
    final org.wheatgenetics.coordinate.model.RowOrCol maxRowOrCol)
    {
        assert null != maxRowOrCol;
        if (this.value > maxRowOrCol.value) throw new java.lang.IllegalArgumentException();
        return this;
    }

    static org.wheatgenetics.coordinate.model.RowOrCol makeWithRandomValue(
    @android.support.annotation.IntRange(from = 1) final int maxValue)
    {
        return new org.wheatgenetics.coordinate.model.RowOrCol(
            new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(maxValue) + 1);
    }
    // endregion
}