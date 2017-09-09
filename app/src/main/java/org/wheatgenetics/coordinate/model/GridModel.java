package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private long             temp     ;
    private java.lang.String title    ;
    private long             timestamp;
    // endregion

    // region Constructors
    public GridModel(final long id) { super(id); }

    GridModel(final long id, final java.lang.String title, final long timestamp)
    { super(id); this.title = title; this.timestamp = timestamp; }
    // endregion

    // region Public Methods
    public java.lang.String getTitle    () { return this.title    ; }
    public long             getTimestamp() { return this.timestamp; }
    // endregion
}