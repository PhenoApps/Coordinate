package org.wheatgenetics.coordinate.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Utils;

public abstract class EntryModel extends Model
        implements ElementModel {
    // region Fields
    @IntRange(from = 1)
    private final long gridId;
    @IntRange(from = 1)
    private final int row, col;
    @IntRange(from = 0)
    private final long timestamp;
    // endregion

    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntryModel(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final StringGetter stringGetter) {
        super(stringGetter);

        this.gridId = Model.valid(
                gridId, this.stringGetter());
        this.row = Utils.valid(row, 1, this.stringGetter());
        this.col = Utils.valid(col, 1, this.stringGetter());
        this.timestamp = System.currentTimeMillis();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        super(id, stringGetter);

        this.gridId =
                Model.valid(gridId, this.stringGetter());
        this.row = Utils.valid(row, 1, this.stringGetter());
        this.col = Utils.valid(col, 1, this.stringGetter());
        this.timestamp = timestamp;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    EntryModel(
            @NonNull final EntryModel entryModel,
            @NonNull final StringGetter stringGetter) {
        this(entryModel.getId(), entryModel.getGridId(), entryModel.getRow(), entryModel.getCol(),
                entryModel.getTimestamp(), stringGetter);
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @Override
    @IntRange(from = 1)
    public int getRowValue() {
        return this.getRow();
    }

    @Override
    @IntRange(from = 1)
    public int getColValue() {
        return this.getCol();
    }
    // endregion

    // region Package Methods
    abstract String getSeedExportValue();

    @NonNull
    String getDNAExportValue(
            final String sample_id) {
        return "BLANK_" + sample_id;
    }

    abstract String getUserDefinedExportValue();
    // endregion

    // region Public Methods
    @IntRange(from = 1)
    public long getGridId() {
        return this.gridId;
    }

    @IntRange(from = 1)
    public int getRow() {
        return this.row;
    }

    @IntRange(from = 1)
    public int getCol() {
        return this.col;
    }

    @IntRange(from = 0)
    public long getTimestamp() {
        return this.timestamp;
    }

    public abstract String getValue();

    public abstract String getDatabaseValue();

    @DrawableRes
    public abstract int backgroundResource();
    // endregion
}