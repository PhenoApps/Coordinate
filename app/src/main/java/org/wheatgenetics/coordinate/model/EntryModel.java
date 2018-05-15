package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.Model
 */
public abstract class EntryModel extends org.wheatgenetics.coordinate.model.Model
implements org.wheatgenetics.coordinate.model.ElementModel
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
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row, 1)      ;
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col, 1)      ;
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
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row, 1)      ;
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col, 1)      ;
        this.timestamp = timestamp                                             ;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        this(entryModel.getId(), entryModel.getGridId(), entryModel.getRow(), entryModel.getCol(),
            entryModel.getTimestamp());
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @java.lang.Override public int getRowValue() { return this.getRow(); }
    @java.lang.Override public int getColValue() { return this.getCol(); }
    // endregion

    // region Package Methods
    abstract java.lang.String getSeedExportValue();

    java.lang.String getDNAExportValue(final java.lang.String sample_id)
    { return "BLANK_" + sample_id; }

    abstract java.lang.String getUserDefinedExportValue();
    // endregion

    // region Public Methods
    public long getGridId   () { return this.gridId   ; }
    public int  getRow      () { return this.row      ; }
    public int  getCol      () { return this.col      ; }
    public long getTimestamp() { return this.timestamp; }

    public abstract java.lang.String getValue        ();
    public abstract java.lang.String getDatabaseValue();

    public abstract int backgroundResource();
    // endregion
}