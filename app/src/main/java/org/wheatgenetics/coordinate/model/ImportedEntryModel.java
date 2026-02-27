package org.wheatgenetics.coordinate.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Entry model for cells in imported grids. Extends IncludedEntryModel with:
 * - originalValue: the sample name as imported, used for replacement detection
 * - confirmedTimestamp: epoch ms when the cell was approved (0 = not confirmed)
 * - brapiData: JSON with sampleDbId/germplasmDbId for BrAPI sync (null for non-BrAPI)
 * - takenBy: who confirmed this sample (set at confirmation time for BrAPI grids)
 *
 * Cell state is determined by:
 *   confirmed  — confirmedTimestamp > 0 (blue + checkmark)
 *   missing    — value equals MISSING_VALUE (gray)
 *   replaced   — value differs from originalValue and not missing (red)
 *   pending    — default unconfirmed state (light blue)
 */
public class ImportedEntryModel extends IncludedEntryModel {

    public static final String MISSING_VALUE = "MISSING";

    @Nullable
    private final String originalValue;
    private long confirmedTimestamp;

    @Nullable
    private final String brapiData;
    @Nullable
    private String takenBy;

    public ImportedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @Nullable final String value,
            @Nullable final String originalValue,
            final long confirmedTimestamp,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        this(id, gridId, row, col, value, originalValue, confirmedTimestamp, timestamp,
                null, null, stringGetter);
    }

    public ImportedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @Nullable final String value,
            @Nullable final String originalValue,
            final long confirmedTimestamp,
            @IntRange(from = 0) final long timestamp,
            @Nullable final String brapiData,
            @Nullable final String takenBy,
            @NonNull final StringGetter stringGetter) {
        super(id, gridId, row, col, value, timestamp, stringGetter);
        this.originalValue = originalValue;
        this.confirmedTimestamp = confirmedTimestamp;
        this.brapiData = brapiData;
        this.takenBy = takenBy;
    }

    @Nullable
    public String getOriginalValue() {
        return originalValue;
    }

    public long getConfirmedTimestamp() {
        return confirmedTimestamp;
    }

    public void setConfirmedTimestamp(final long confirmedTimestamp) {
        this.confirmedTimestamp = confirmedTimestamp;
    }

    @Nullable
    public String getBrapiData() {
        return brapiData;
    }

    @Nullable
    public String getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(@Nullable final String takenBy) {
        this.takenBy = takenBy;
    }

    public boolean isConfirmed() {
        return confirmedTimestamp > 0;
    }

    public boolean isMissing() {
        return MISSING_VALUE.equals(getValue());
    }

    public boolean isReplaced() {
        return !isMissing()
                && originalValue != null
                && !originalValue.equals(getValue());
    }

    @Override
    @DrawableRes
    public int backgroundResource() {
        if (isConfirmed()) {
            if (isMissing())  return R.drawable.imported_confirmed_missing_entry;
            if (isReplaced()) return R.drawable.imported_confirmed_replaced_entry;
            return R.drawable.imported_confirmed_entry;
        }
        if (isMissing())   return R.drawable.imported_missing_entry;
        if (isReplaced())  return R.drawable.imported_replaced_entry;
        return R.drawable.imported_pending_entry;
    }
}
