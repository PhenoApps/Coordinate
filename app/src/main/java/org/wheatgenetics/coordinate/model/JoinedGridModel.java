package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
{
    private org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel = null;

    // region Private Methods
    private java.lang.String getTemplateTitle()
    { return null == this.partialTemplateModel ? null : this.partialTemplateModel.getTitle(); }

    private int getRows()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getRows(); }

    private int getCols()
    { return null == this.partialTemplateModel ? 0 : this.partialTemplateModel.getCols(); }
    // endregion

    public JoinedGridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final long timestamp,
    @android.support.annotation.IntRange(from = 1        ) final long             templateId    ,
                                                           final java.lang.String templateTitle ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int code                       ,
    @android.support.annotation.IntRange(from = 1        ) final int rows                       ,
    @android.support.annotation.IntRange(from = 1        ) final int cols                       ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering               ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering               ,
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

    public void populate(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { assert null != templateModel; templateModel.assign(this.partialTemplateModel); }
}