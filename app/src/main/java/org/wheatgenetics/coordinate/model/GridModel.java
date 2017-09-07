package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private java.lang.String title    ;
    private long             timestamp;

    private org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel = null;
    // endregion

    // region Constructors
    public GridModel(final long id) { super(id); }

    public GridModel(final long id, final java.lang.String title, final long timestamp,
    final long templateId, final java.lang.String templateTitle, final int templateType,
    final int templateRows, final int templateCols)
    {
        this(id);

        this.title = title; this.timestamp = timestamp;
        this.partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            templateId, templateTitle, templateType, templateRows, templateCols);
    }
    // endregion

    // region Public Methods
    public java.lang.String getTitle    () { return this.title    ; }
    public long             getTimestamp() { return this.timestamp; }

    public long getTemplateId()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getId(); }

    public java.lang.String getTemplateTitle()
    { return null == this.partialTemplateModel ? null : this.partialTemplateModel.getTitle(); }

    public org.wheatgenetics.coordinate.model.TemplateType getType()
    { assert null != this.partialTemplateModel; return this.partialTemplateModel.getType(); }

    public int getRows()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getRows(); }

    public int getCols()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getCols(); }
    // endregion
}