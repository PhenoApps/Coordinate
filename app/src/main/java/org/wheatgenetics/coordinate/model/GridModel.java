package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 */

public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private java.lang.String title    ;
    private long             timestamp;

    private org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel;
    // endregion

    // region Constructors
    public GridModel() { super(); }  // TODO: Remove?

    public GridModel(final long id)  // TODO: Remove?
    {
        this();
        this.setId(id);
    }

    public GridModel(final long id, final java.lang.String title, final long timestamp,
    final long templateId, final java.lang.String templateTitle, final int templateType,
    final int templateRows, final int templateCols)
    {
        this(id);

        this.title     = title    ;
        this.timestamp = timestamp;

        this.partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            templateId, templateTitle, templateType, templateRows, templateCols);
    }
    // endregion

    public java.lang.String getTitle    () { return this.title    ; }
    public long             getTimestamp() { return this.timestamp; }
}