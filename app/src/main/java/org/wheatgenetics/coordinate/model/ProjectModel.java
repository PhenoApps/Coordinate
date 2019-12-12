package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class ProjectModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
                                            private       java.lang.String title    ;
    @androidx.annotation.IntRange(from = 0) private final long             timestamp;
    // endregion

    // region Constructors
    private ProjectModel() { super(); this.timestamp = java.lang.System.currentTimeMillis(); }

    public ProjectModel(final java.lang.String title) { this(); this.title = title; }

    public ProjectModel(
    @androidx.annotation.IntRange(from = 1) final long             id       ,
                                            final java.lang.String title    ,
    @androidx.annotation.IntRange(from = 0) final long             timestamp)
    { super(id); this.title = title; this.timestamp = timestamp; }
    // endregion

    // region Public Methods
    public java.lang.String getTitle() { return this.title; }

    @androidx.annotation.IntRange(from = 0) public long getTimestamp() { return this.timestamp; }

    @androidx.annotation.Nullable public java.lang.CharSequence getFormattedTimestamp()
    {
        final long timestamp = this.getTimestamp();
        return timestamp < 1 ? null : org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp);
    }
    // endregion
}