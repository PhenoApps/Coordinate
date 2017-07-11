package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 */

public class EntryModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private long             gridId   ;
    private int              row, col ;
    private java.lang.String value    ;
    private long             timestamp;
    // endregion

    public EntryModel(final long id, final long gridId, final int row, final int col,
    final java.lang.String value, final long timestamp)
    {
        super(id);

        this.gridId    = gridId   ;
        this.row       = row      ;
        this.col       = col      ;
        this.value     = value    ;
        this.timestamp = timestamp;
    }

    public long             getGridId   () { return this.gridId   ; }
    public int              getRow      () { return this.row      ; }
    public int              getCol      () { return this.col      ; }
    public java.lang.String getValue    () { return this.value    ; }
    public long             getTimestamp() { return this.timestamp; }
}