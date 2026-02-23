package org.wheatgenetics.coordinate.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * Entry model for cells in imported grids. Extends IncludedEntryModel with:
 * - originalValue: the value from the imported CSV, immutable after import
 * - confirmedTimestamp: epoch ms when the cell was approved (0 = not confirmed)
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
        super(id, gridId, row, col, value, timestamp, stringGetter);
        this.originalValue = originalValue;
        this.confirmedTimestamp = confirmedTimestamp;
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
