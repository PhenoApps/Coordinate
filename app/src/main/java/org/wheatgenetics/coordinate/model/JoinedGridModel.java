package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
{
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel = null;

    // region Private Methods
    private java.lang.String getTemplateTitle()
    { return null == this.templateModel ? null : this.templateModel.getTitle(); }

    private int getRows() { return null == this.templateModel ? 0 : this.templateModel.getRows(); }

    private int getCols() { return null == this.templateModel ? 0 : this.templateModel.getCols(); }
    // endregion

    public JoinedGridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final long timestamp,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId   ,
                                                           final java.lang.String templateTitle,
    @android.support.annotation.IntRange(from = 0, to = 2) final int code                      ,
    @android.support.annotation.IntRange(from = 1        ) final int rows                      ,
    @android.support.annotation.IntRange(from = 1        ) final int cols                      ,
    final java.lang.String excludeCells,
    final java.lang.String excludeRows, final java.lang.String excludeCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    final java.lang.String optionalFields, final long templateTimestamp)
    {
        super(id, title, timestamp);
        this.templateModel = new org.wheatgenetics.coordinate.model.TemplateModel(templateId,
            templateTitle, code, rows, cols, excludeCells, excludeRows, excludeCols, colNumbering,
            rowNumbering, optionalFields, templateTimestamp);
    }

    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String name()
    {
        return java.lang.String.format("Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getTitle(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    public void populate(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { assert null != templateModel; templateModel.assign(this.templateModel); }
}