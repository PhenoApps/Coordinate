package org.wheatgenetics.coordinate.model;

abstract public class Model extends java.lang.Object
{
    private long id;

    Model() { super(); }
    Model(final long id)
    {
        this();
        this.setId(id);
    }

    @java.lang.Override @android.annotation.SuppressLint("DefaultLocale")
    public java.lang.String toString() { return java.lang.String.format("id: %02d", this.id); }

    public long getId()              { return this.id; }
    public void setId(final long id) { this.id = id  ; }               // TODO: Replace with copy().
}