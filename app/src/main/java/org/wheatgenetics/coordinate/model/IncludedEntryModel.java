package org.wheatgenetics.coordinate.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.javalib.Utils;

public class IncludedEntryModel extends EntryModel {
    private String value;

    // region Constructors
    IncludedEntryModel(
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final StringGetter stringGetter) {
        super(gridId, row, col, stringGetter);
    }

    public IncludedEntryModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long gridId,
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            final String value,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        super(id, gridId, row, col, timestamp, stringGetter);
        this.uncheckedSetValue(value);
    }

    public IncludedEntryModel(
            @NonNull final ExcludedEntryModel
                    excludedEntryModel,
            @NonNull final StringGetter stringGetter) {
        super(excludedEntryModel, stringGetter);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void uncheckedSetValue(@Nullable final String value) {
        this.value = null == value ? null : value.trim();
    }
    // endregion

    // region Overridden Methods
    @Override
    String getSeedExportValue() {
        return Utils.replaceIfNull(this.getValue(), "BLANK_");
    }

    @Override
    @NonNull
    String getDNAExportValue(
            final String sample_id) {
        final String value = this.getValue();
        if (null == value || value.length() < 1)
            return super.getDNAExportValue(sample_id);
        else
            return value;
    }

    @Override
    String getUserDefinedExportValue() {
        return Utils.makeEmptyIfNull(this.getValue());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    // region Public Methods
    public void setValue(@Nullable final String value) {
        this.uncheckedSetValue(value);
    }

    @Override
    public String getDatabaseValue() {
        return this.getValue();
    }
    // endregion

    @Override
    @DrawableRes
    public int backgroundResource() {
        return this.valueIsEmpty() ?
                R.drawable.empty_included_entry :
                R.drawable.full_included_entry;
    }

    public boolean valueIsEmpty() {
        final String value = this.getValue();

        // noinspection SimplifiableConditionalExpression
        return null == value ? true : value.length() <= 0;
    }
    // endregion
}