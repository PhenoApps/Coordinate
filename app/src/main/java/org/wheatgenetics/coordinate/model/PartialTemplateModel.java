package org.wheatgenetics.coordinate.model;

/**
 * PartialTemplateModel implements part of a template.  To get a full template implementation use
 * TemplateModel.  What purpose does PartialTemplateModel serve?  What criteria was used to
 * determine which parts of a template should go in PartialTemplateModel and which parts should go
 * in TemplateModel?  Answer: PartialTemplateModel contains those parts of a template needed by
 * GridModel.
 *
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateType
 * org.wheatgenetics.coordinate.utils.Utils
 */

class PartialTemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private java.lang.String                                title     ;
    private org.wheatgenetics.coordinate.model.TemplateType type      ;
    private int                                             rows, cols;
    // endregion

    // region Private Methods
    private static java.lang.String convert(final int integer)
    { return integer <= 0 ? "" : java.lang.String.valueOf(integer); }


    private void setRows(final int rows)
    { if (rows <= 0) throw new java.lang.IllegalArgumentException(); else this.rows = rows; }

    private void setRows(final java.lang.String rows)
    { this.setRows(org.wheatgenetics.coordinate.utils.Utils.parseInt(rows)); }

    private void setCols(final int cols)
    { if (cols <= 0) throw new java.lang.IllegalArgumentException(); else this.cols = cols; }

    private void setCols(final java.lang.String cols)
    { this.setCols(org.wheatgenetics.coordinate.utils.Utils.parseInt(cols)); }
    // endregion

    // region Constructors
    private PartialTemplateModel(final long id) { super(id); }

    PartialTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols)
    {
        super();
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
    // endregion

    // region toString()
    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String formatString()
    {
        return "%s" + java.lang.String.format(" [%s, title=%s, type=%d, rows=%d, cols=%d",
            super.toString(), this.title, this.type.getCode(), this.rows, this.cols);
    }

    @java.lang.Override
    public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "PartialTemplateModel") + "]"; }
    // endregion

    // region Public Methods
    public java.lang.String getTitle()                             { return this.title ; }
    public void             setTitle(final java.lang.String title) { this.title = title; }


    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }

    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }

    public void setType(final int code)
    { this.setType(org.wheatgenetics.coordinate.model.TemplateType.get(code)); }


    public int getRows() { return this.rows; }

    public java.lang.String getRowsAsString()
    { return org.wheatgenetics.coordinate.model.PartialTemplateModel.convert(this.getRows()); }


    public int getCols() { return this.cols; }

    public java.lang.String getColsAsString()
    { return org.wheatgenetics.coordinate.model.PartialTemplateModel.convert(this.getCols()); }


    public void assign(final java.lang.String title, final int rows, final int cols)
    {
        this.setTitle(title);

        this.setRows(rows);
        this.setCols(cols);
    }

    public void assign(final java.lang.String title,
    final java.lang.String rows, final java.lang.String cols)
    {
        this.setTitle(title);

        this.setRows(rows);
        this.setCols(cols);
    }
    // endregion
}