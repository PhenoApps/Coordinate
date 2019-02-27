package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * androidx.annotation.RecentlyNonNull
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class Model extends java.lang.Object
{
    @android.support.annotation.IntRange(from = 1) private long id;

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    Model() { super(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    Model(@android.support.annotation.IntRange(from = 1) final long id) { this(); this.setId(id); }
    // endregion

    // region Overridden Methods
    @java.lang.SuppressWarnings({"DefaultLocale", "NullableProblems"}) @java.lang.Override
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.getId()); }

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

    public static long valid(final long id)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            throw new java.lang.IllegalArgumentException("id must be > 0");
        else
            return id;
    }

    @android.support.annotation.IntRange(from = 1) public long getId() { return this.id; }

    public void setId(@android.support.annotation.IntRange(from = 1) final long id)
    { this.id = org.wheatgenetics.coordinate.model.Model.valid(id) /* throws */; }
    // endregion
}