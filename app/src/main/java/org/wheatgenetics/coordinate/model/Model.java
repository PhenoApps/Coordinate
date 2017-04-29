package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 */

abstract public class Model extends java.lang.Object
{
    long id;  // TODO: Make private later.

    public long getId() { return this.id; }
    public void setId(final long id) { this.id = id; }                 // TODO: Replace with copy().
}