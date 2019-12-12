package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class RowOrCol extends java.lang.Object implements java.lang.Cloneable, java.lang.Comparable
{
    @androidx.annotation.IntRange(from = 1) private final int value;

    // region Constructors
    RowOrCol(@androidx.annotation.IntRange(from = 1) final int value)
    {
        super();
        this.value = org.wheatgenetics.coordinate.Utils.valid(value,1);    // throws java
    }                                                                               //  .lang.Ille-
                                                                                    //  galArgument-
    RowOrCol(                                                                       //  Exception
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol)
    { this(rowOrCol.getValue()) /* throws java.lang.IllegalArgumentException */; }
    // endregion

    @androidx.annotation.NonNull private java.lang.String getValueAsString()
    { return java.lang.String.valueOf(this.getValue()); }

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    { return this.getValueAsString(); }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (object instanceof org.wheatgenetics.coordinate.model.RowOrCol)
        {
            final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol =
                (org.wheatgenetics.coordinate.model.RowOrCol) object;
            return this.getValue() == rowOrCol.getValue();
        }
        else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @java.lang.Override @androidx.annotation.NonNull protected java.lang.Object clone()
    { return new org.wheatgenetics.coordinate.model.RowOrCol(this); }

    // region java.lang.Comparable Overridden Method
    @java.lang.Override
    public int compareTo(@androidx.annotation.NonNull final java.lang.Object object)
    {
        // noinspection UseCompareMethod
        return java.lang.Integer.valueOf(this.getValue()).compareTo(
            ((org.wheatgenetics.coordinate.model.RowOrCol) object).getValue());
    }
    // endregion
    // endregion

    // region Package Methods
    @androidx.annotation.IntRange(from = 1) int getValue() { return this.value; }

    @androidx.annotation.NonNull org.wheatgenetics.coordinate.model.RowOrCol inRange(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol
        maxRowOrCol)
    {
        if (this.getValue() > maxRowOrCol.getValue())
            throw new java.lang.IllegalArgumentException();
        else
            return this;
    }

    @androidx.annotation.NonNull static org.wheatgenetics.coordinate.model.RowOrCol
    makeWithRandomValue(@androidx.annotation.IntRange(from = 1) final int maxValue)
    {
        return new org.wheatgenetics.coordinate.model.RowOrCol(
            /* value => */new java.util.Random().nextInt(maxValue) + 1);
    }
    // endregion
}