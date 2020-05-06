package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Utils;

import java.util.Random;

class RowOrCol implements Cloneable, Comparable {
    // region Fields
    @IntRange(from = 1)
    private final int value;
    @NonNull
    private final StringGetter
            stringGetter;
    // endregion

    // region Constructors
    RowOrCol(@IntRange(from = 1) final int value,
             @NonNull final StringGetter stringGetter) {
        super();
        this.value = Utils.valid(             // throws java.lang.Ille-
                value, 1, stringGetter);                               //  galArgumentException
        this.stringGetter = stringGetter;
    }

    RowOrCol(
            @NonNull final RowOrCol rowOrCol,
            @NonNull final StringGetter stringGetter) {
        this(rowOrCol.getValue(), stringGetter) /* throws java.lang.IllegalArgumentException */;
    }
    // endregion

    @NonNull
    static RowOrCol
    makeWithRandomValue(@IntRange(from = 1) final int maxValue,
                        @NonNull final StringGetter stringGetter) {
        return new RowOrCol(
                new Random().nextInt(maxValue) + 1, stringGetter);
    }

    @NonNull
    private String getValueAsString() {
        return String.valueOf(this.getValue());
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return this.getValueAsString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof RowOrCol) {
            final RowOrCol rowOrCol =
                    (RowOrCol) object;
            return this.getValue() == rowOrCol.getValue();
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    @NonNull
    protected Object clone() {
        return new RowOrCol(this, this.stringGetter);
    }
    // endregion
    // endregion

    // region java.lang.Comparable Overridden Method
    @Override
    public int compareTo(@NonNull final Object object) {
        // noinspection UseCompareMethod
        return Integer.valueOf(this.getValue()).compareTo(
                ((RowOrCol) object).getValue());
    }

    // region Package Methods
    @IntRange(from = 1)
    int getValue() {
        return this.value;
    }

    @NonNull
    RowOrCol inRange(
            @NonNull final RowOrCol
                    maxRowOrCol) {
        if (this.getValue() > maxRowOrCol.getValue())
            throw new IllegalArgumentException();
        else
            return this;
    }
    // endregion
}