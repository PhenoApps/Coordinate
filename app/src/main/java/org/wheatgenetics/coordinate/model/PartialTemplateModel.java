package org.wheatgenetics.coordinate.model;

/**
 * PartialTemplateModel implements part of a template.  To get a full template implementation use
 * TemplateModel.  What purpose does PartialTemplateModel serve?  What criteria was used to
 * determine which parts of a template should go in PartialTemplateModel and which parts should go
 * in TemplateModel?  Answer: PartialTemplateModel contains those parts of a template need by
 * GridModel.
 *
 * Uses:
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateType
 */

class PartialTemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    private java.lang.String                                title     ;
    private org.wheatgenetics.coordinate.model.TemplateType type      ;
    private int                                             rows, cols;

    private void assign(final java.lang.String title, final int rows, final int cols)
    {
        this.title = title;

        if (rows <= 0) throw new java.lang.IndexOutOfBoundsException(); else this.rows = rows;
        if (cols <= 0) throw new java.lang.IndexOutOfBoundsException(); else this.cols = cols;
    }

    PartialTemplateModel() { super(); }

    PartialTemplateModel(final long id) { super(id); }

    PartialTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols)
    {
        this();
        this.assign(title, rows, cols);
        this.setType(type);
    }

    PartialTemplateModel(final long id, final java.lang.String title,
    final int code, final int rows, final int cols)
    {
        this(id);
        this.assign(title, rows, cols);
        this.setType(code);
    }

    public java.lang.String getTitle()                             { return this.title ; }
    public void             setTitle(final java.lang.String title) { this.title = title; }

    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }
    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }
    public void setType(final int code)
    { this.type = org.wheatgenetics.coordinate.model.TemplateType.get(code); }

    public int  getRows()               { return this.rows; }
    public void setRows(final int rows) { this.rows = rows; }

    public int  getCols()               { return this.cols; }
    public void setCols(final int cols) { this.cols = cols; }
}