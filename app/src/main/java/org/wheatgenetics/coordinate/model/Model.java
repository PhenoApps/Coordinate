package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 */
public abstract class Model extends java.lang.Object
{
    private long id;

    Model() { super(); } Model(final long id) { this(); this.setId(id); }

    // region Overridden Methods
    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.id); }

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
    // endregion

    public long getId() { return this.id; } public void setId(final long id) { this.id = id; }
}