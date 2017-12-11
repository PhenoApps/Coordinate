package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
          private long             templateId;
    private final java.lang.String title     ;
    private final long             timestamp ;
    // endregion

    // region Constructors
    public GridModel(final long templateId, final java.lang.String title)
    {
        super();

        this.templateId = templateId                          ;
        this.title      = title                               ;
        this.timestamp  = java.lang.System.currentTimeMillis();
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(final long id, final java.lang.String title, final long timestamp)
    { super(id); this.title = title; this.timestamp = timestamp; }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }

    // region Public Methods
    public long             getTemplateId() { return this.templateId; }
    public java.lang.String getTitle     () { return this.title     ; }
    public long             getTimestamp () { return this.timestamp ; }
    // endregion
}