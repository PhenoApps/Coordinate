package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 */

abstract public class Model extends java.lang.Object
{
    private long id;

    public long getId()              { return this.id; }
    public void setId(final long id) { this.id = id  ; }               // TODO: Replace with copy().

    Model() { super(); }

    Model(final long id)
    {
        this();
        this.setId(id);
    }
}