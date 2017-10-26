package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 */
public class EntryModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    final private long             gridId   ;
    final private int              row, col ;
          private java.lang.String value    ;
    final private long             timestamp;
    // endregion

    // region Constructors
    public EntryModel(final long gridId, final int row, final int col, final java.lang.String value)
    {
        super();

        this.gridId = gridId; this.row = row; this.col = col; this.value = value;
        this.timestamp = java.lang.System.currentTimeMillis();
    }

    public EntryModel(final long id, final long gridId, final int row, final int col,
    final java.lang.String value, final long timestamp)
    {
        super(id);

        this.gridId = gridId; this.row = row; this.col = col;
        this.value = value;       this.timestamp = timestamp;
    }
    // endregion

    // region Public Methods
    public long getGridId() { return this.gridId; }
    public int  getRow   () { return this.row   ; }
    public int  getCol   () { return this.col   ; }

    public java.lang.String getValue()                             { return this.value ; }
    public void             setValue(final java.lang.String value) { this.value = value; }

    public long getTimestamp() { return this.timestamp; }
    // endregion
}