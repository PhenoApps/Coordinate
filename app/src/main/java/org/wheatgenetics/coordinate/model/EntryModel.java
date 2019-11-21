package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.DrawableRes
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
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
    @android.support.annotation.IntRange(from = 1) private final long gridId   ;
    @android.support.annotation.IntRange(from = 1) private final int  row, col ;
    @android.support.annotation.IntRange(from = 0) private final long timestamp;
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
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row,1);
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col,1);
        this.timestamp = java.lang.System.currentTimeMillis();
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @android.support.annotation.IntRange(from = 1) final long id       ,
    @android.support.annotation.IntRange(from = 1) final long gridId   ,
    @android.support.annotation.IntRange(from = 1) final int  row      ,
    @android.support.annotation.IntRange(from = 1) final int  col      ,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id);

        this.gridId    = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row,1);
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col,1);
        this.timestamp = timestamp;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(@android.support.annotation.NonNull final
        org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        this(entryModel.getId(), entryModel.getGridId(), entryModel.getRow(), entryModel.getCol(),
            entryModel.getTimestamp());
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @java.lang.Override @android.support.annotation.IntRange(from = 1) public int getRowValue()
    { return this.getRow(); }

    @java.lang.Override @android.support.annotation.IntRange(from = 1) public int getColValue()
    { return this.getCol(); }
    // endregion

    // region Package Methods
    abstract java.lang.String getSeedExportValue();

    @android.support.annotation.NonNull java.lang.String getDNAExportValue(
    final java.lang.String sample_id) { return "BLANK_" + sample_id; }

    abstract java.lang.String getUserDefinedExportValue();
    // endregion

    // region Public Methods
    @android.support.annotation.IntRange(from = 1) public long getGridId() { return this.gridId; }
    @android.support.annotation.IntRange(from = 1) public int  getRow   () { return this.row   ; }
    @android.support.annotation.IntRange(from = 1) public int  getCol   () { return this.col   ; }

    @android.support.annotation.IntRange(from = 0) public long getTimestamp()
    { return this.timestamp; }

    public abstract java.lang.String getValue        ();
    public abstract java.lang.String getDatabaseValue();

    @android.support.annotation.DrawableRes public abstract int backgroundResource();
    // endregion
}