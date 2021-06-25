package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.phenoapps.androidlibrary.Utils;
import org.wheatgenetics.coordinate.StringGetter;

public class ProjectModel extends Model {
    // region Fields
    @IntRange(from = 0)
    private final long timestamp;
    private String title;
    // endregion

    // region Constructors
    private ProjectModel(@NonNull final StringGetter stringGetter) {
        super(stringGetter);
        this.timestamp = System.currentTimeMillis();
    }

    public ProjectModel(final String title, @NonNull final StringGetter stringGetter) {
        this(stringGetter);
        this.title = title;
    }

    public ProjectModel(
            @IntRange(from = 1) final long id,
            final String title,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        super(id, stringGetter);
        this.title = title;
        this.timestamp = timestamp;
    }
    // endregion

    // region Public Methods
    public String getTitle() {
        return this.title;
    }

    @IntRange(from = 0)
    public long getTimestamp() {
        return this.timestamp;
    }

    @Nullable
    public CharSequence getFormattedTimestamp() {
        final long timestamp = this.getTimestamp();
        return timestamp < 1 ? null : Utils.formatDate(timestamp);
    }
    // endregion
}