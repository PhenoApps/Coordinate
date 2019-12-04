package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.DrawableRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
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
    @androidx.annotation.IntRange(from = 1) private final long gridId   ;
    @androidx.annotation.IntRange(from = 1) private final int  row, col ;
    @androidx.annotation.IntRange(from = 0) private final long timestamp;
    // endregion

    // region Constructors
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @androidx.annotation.IntRange(from = 1) final long gridId,
    @androidx.annotation.IntRange(from = 1) final int  row   ,
    @androidx.annotation.IntRange(from = 1) final int  col   )
    {
        super();

        this.gridId    = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row,1);
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col,1);
        this.timestamp = java.lang.System.currentTimeMillis();
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @androidx.annotation.IntRange(from = 1) final long id       ,
    @androidx.annotation.IntRange(from = 1) final long gridId   ,
    @androidx.annotation.IntRange(from = 1) final int  row      ,
    @androidx.annotation.IntRange(from = 1) final int  col      ,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id);

        this.gridId    = org.wheatgenetics.coordinate.model.Model.valid(gridId);
        this.row       = org.wheatgenetics.coordinate.Utils.valid(row,1);
        this.col       = org.wheatgenetics.coordinate.Utils.valid(col,1);
        this.timestamp = timestamp;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    EntryModel(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        this(entryModel.getId(), entryModel.getGridId(), entryModel.getRow(), entryModel.getCol(),
            entryModel.getTimestamp());
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getRowValue()
    { return this.getRow(); }

    @java.lang.Override @androidx.annotation.IntRange(from = 1) public int getColValue()
    { return this.getCol(); }
    // endregion

    // region Package Methods
    abstract java.lang.String getSeedExportValue();

    @androidx.annotation.NonNull java.lang.String getDNAExportValue(
    final java.lang.String sample_id) { return "BLANK_" + sample_id; }

    abstract java.lang.String getUserDefinedExportValue();
    // endregion

    // region Public Methods
    @androidx.annotation.IntRange(from = 1) public long getGridId   () { return this.gridId   ; }
    @androidx.annotation.IntRange(from = 1) public int  getRow      () { return this.row      ; }
    @androidx.annotation.IntRange(from = 1) public int  getCol      () { return this.col      ; }
    @androidx.annotation.IntRange(from = 0) public long getTimestamp() { return this.timestamp; }

    public abstract java.lang.String getValue        ();
    public abstract java.lang.String getDatabaseValue();

    @androidx.annotation.DrawableRes public abstract int backgroundResource();
    // endregion
}