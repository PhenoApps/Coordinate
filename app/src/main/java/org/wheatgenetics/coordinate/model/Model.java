package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
abstract class Model extends java.lang.Object
{
    private long id;

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    Model() { super(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    Model(final long id) { this(); this.setId(id); }
    // endregion

    // region Overridden Methods
    @java.lang.Override @java.lang.SuppressWarnings("DefaultLocale")
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.getId()); }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final java.lang.Object o)
    {
        if (null == o)
            return false;
        else
            if (o instanceof org.wheatgenetics.coordinate.model.Model)
                return this.getId() == ((org.wheatgenetics.coordinate.model.Model) o).getId();
            else
                return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }
    // endregion

    public long getId() { return this.id; } public void setId(final long id) { this.id = id; }
}