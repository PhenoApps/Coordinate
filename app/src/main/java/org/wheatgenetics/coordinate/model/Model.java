package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Model extends java.lang.Object implements java.lang.Cloneable
{
    // region Fields
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.StringGetter
        stringGetter;
    @androidx.annotation.IntRange(from = 1) private long id;
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.StringGetter stringGetter()
    { return this.stringGetter; }

    // region Constructors
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    Model(@androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(); this.stringGetter = stringGetter; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES) Model(
    @androidx.annotation.IntRange(from = 1) final long                                 id          ,
    @androidx.annotation.NonNull       final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { this(stringGetter); this.setId(id); }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    {
        return java.lang.String.format(java.util.Locale.getDefault(),
            "id: %02d", this.getId());
    }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (object instanceof org.wheatgenetics.coordinate.model.Model)
            return this.getId() == ((org.wheatgenetics.coordinate.model.Model) object).getId();
        else
            return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }
    // endregion

    // region Public Methods
    public static boolean illegal(final long id) { return id < 1; }

    public static long valid(final long id,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            throw new java.lang.IllegalArgumentException(stringGetter.get(
                org.wheatgenetics.coordinate.R.string.ModelIdMustBeGreaterThanZero));
        else
            return id;
    }

    @androidx.annotation.IntRange(from = 1) public long getId() { return this.id; }

    public void setId(@androidx.annotation.IntRange(from = 1) final long id)
    {
        this.id = org.wheatgenetics.coordinate.model.Model.valid(                          // throws
            id, this.stringGetter());
    }
    // endregion
}