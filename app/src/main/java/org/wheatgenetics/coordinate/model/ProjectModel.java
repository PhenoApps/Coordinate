package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class ProjectModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
                                                   private       java.lang.String title    ;
    @android.support.annotation.IntRange(from = 0) private final long             timestamp;
    // endregion

    // region Constructors
    private ProjectModel() { super(); this.timestamp = java.lang.System.currentTimeMillis(); }

    public ProjectModel(final java.lang.String title) { this(); this.title = title; }

    public ProjectModel(
    @android.support.annotation.IntRange(from = 1) final long             id       ,
                                                   final java.lang.String title    ,
    @android.support.annotation.IntRange(from = 0) final long             timestamp)
    { super(id); this.title = title; this.timestamp = timestamp; }
    // endregion

    // region Public Methods
    public java.lang.String getTitle() { return this.title; }

    @android.support.annotation.IntRange(from = 0) public long getTimestamp()
    { return this.timestamp; }
    // endregion
}