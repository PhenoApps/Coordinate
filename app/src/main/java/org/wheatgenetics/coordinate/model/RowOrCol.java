package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class RowOrCol extends java.lang.Object implements java.lang.Cloneable, java.lang.Comparable
{
    private final int value;

    // region Constructors
    RowOrCol(@android.support.annotation.IntRange(from = 1) final int value)
    {
        super();
        this.value = org.wheatgenetics.coordinate.Utils.valid(value,1);    // throws java
    }                                                                               //  .lang.Ille-
                                                                                    //  galArgument-
    RowOrCol(final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol)            //  Exception
    { this(rowOrCol.getValue()) /* throws java.lang.IllegalArgumentException */; }
    // endregion

    // region Overridden Methods
    @java.lang.Override public java.lang.String toString()
    { return java.lang.Integer.toString(this.getValue()); }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (null == object)
            return false;
        else
            if (object instanceof org.wheatgenetics.coordinate.model.RowOrCol)
            {
                final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
                    (org.wheatgenetics.coordinate.model.RowOrCol) object;
                return this.getValue() == rowOrCol.getValue();
            }
            else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    { return new org.wheatgenetics.coordinate.model.RowOrCol(this); }

    // region java.lang.Comparable Overridden Method
    @java.lang.Override
    public int compareTo(@android.support.annotation.NonNull final java.lang.Object object)
    {
        // noinspection UseCompareMethod
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
        if (this.getValue() > maxRowOrCol.getValue())
            throw new java.lang.IllegalArgumentException();
        return this;
    }

    static org.wheatgenetics.coordinate.model.RowOrCol makeWithRandomValue(
    @android.support.annotation.IntRange(from = 1) final int maxValue)
    {
        return new org.wheatgenetics.coordinate.model.RowOrCol(
            /* value => */new java.util.Random().nextInt(maxValue) + 1);
    }
    // endregion
}