package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
{
    private org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel = null;

    private java.lang.String getTemplateTitle()
    { return null == this.partialTemplateModel ? null : this.partialTemplateModel.getTitle(); }

    public JoinedGridModel(final long id, final java.lang.String title, final long timestamp,
    final long templateId, final java.lang.String templateTitle, final int code, final int rows,
    final int cols, final int colNumbering, final int rowNumbering,
    final java.lang.String optionalFields)
    {
        super(id, title, timestamp);
        this.partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            templateId, templateTitle, code, rows, cols, colNumbering, rowNumbering,
            optionalFields);
    }

    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String name()
    {
        return java.lang.String.format("Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getTitle(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    // region Public Methods
    public long getTemplateId()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getId(); }

    public org.wheatgenetics.coordinate.model.TemplateType getType()
    { assert null != this.partialTemplateModel; return this.partialTemplateModel.getType(); }

    public int getRows()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getRows(); }

    public int getCols()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getCols(); }


    public void assign(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { assert null != templateModel; templateModel.assign(this.partialTemplateModel); }
    // endregion
}