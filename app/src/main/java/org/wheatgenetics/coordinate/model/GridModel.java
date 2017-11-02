package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
          private long             temp     ;
    final private java.lang.String title    ;
    final private long             timestamp;
    // endregion

    // region Constructors
    public GridModel(final long temp, final java.lang.String title)
    {
        super();
        this.temp = temp; this.title = title; this.timestamp = java.lang.System.currentTimeMillis();
    }

    GridModel(final long id, final java.lang.String title, final long timestamp)
    { super(id); this.title = title; this.timestamp = timestamp; }
    // endregion

    java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }

    // region Public Methods
    public long             getTemp     () { return this.temp     ; }
    public java.lang.String getTitle    () { return this.title    ; }
    public long             getTimestamp() { return this.timestamp; }
    // endregion
}