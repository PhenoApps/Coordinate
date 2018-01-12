package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public abstract class EntryModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private final long gridId   ;
    private final int  row, col ;
    private final long timestamp;
    // endregion

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @android.support.annotation.IntRange(from = 1) final long gridId,
    @android.support.annotation.IntRange(from = 1) final int  row   ,
    @android.support.annotation.IntRange(from = 1) final int  col   )
    {
        super();

        this.gridId    = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.row       = org.wheatgenetics.coordinate.model.Model.valid(row, 1);
        this.col       = org.wheatgenetics.coordinate.model.Model.valid(col, 1);
        this.timestamp = java.lang.System.currentTimeMillis()                  ;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @android.support.annotation.IntRange(from = 1) final long id       ,
    @android.support.annotation.IntRange(from = 1) final long gridId   ,
    @android.support.annotation.IntRange(from = 1) final int  row      ,
    @android.support.annotation.IntRange(from = 1) final int  col      ,
                                                   final long timestamp)
    {
        super(id);

        this.gridId    = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.row       = org.wheatgenetics.coordinate.model.Model.valid(row, 1);
        this.col       = org.wheatgenetics.coordinate.model.Model.valid(col, 1);
        this.timestamp = timestamp                                             ;
    }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String getDebugValue() { return this.getRow() + "," + this.getCol(); }

    // region Public Methods
    public long getGridId   () { return this.gridId   ; }
    public int  getRow      () { return this.row      ; }
    public int  getCol      () { return this.col      ; }
    public long getTimestamp() { return this.timestamp; }

    public abstract java.lang.String getValue(); public abstract int backgroundResource();
    // endregion
}