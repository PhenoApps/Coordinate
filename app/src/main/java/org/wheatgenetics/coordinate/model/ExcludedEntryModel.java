package org.wheatgenetics.coordinate.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

public class ExcludedEntryModel extends EntryModel {
    public static final String DATABASE_VALUE = "excluded";

    // region Constructors
    ExcludedEntryModel(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final StringGetter stringGetter) {
        super(gridId, row, col, stringGetter);
    }

    public ExcludedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        super(id, gridId, row, col, timestamp, stringGetter);
    }

    public ExcludedEntryModel(
            @NonNull final IncludedEntryModel
                    includedEntryModel,
            @NonNull final StringGetter stringGetter) {
        super(includedEntryModel, stringGetter);
    }
    // endregion

    // region Overridden Methods
    @Override
    String getSeedExportValue() {
        return "exclude";
    }

    @Override
    String getUserDefinedExportValue() {
        return "exclude";
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getDatabaseValue() {
        return ExcludedEntryModel.DATABASE_VALUE;
    }

    @Override
    @DrawableRes
    public int backgroundResource() {
        return R.drawable.excluded_entry;
    }
    // endregion
}