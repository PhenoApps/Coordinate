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
implements java.lang.Cloneable
{
    // region Fields
    private java.lang.String                                title     ;
    private org.wheatgenetics.coordinate.model.TemplateType type      ;
    private int                                             rows, cols;
    // endregion

    // region Private Methods
    private static int valid(final int i)
    { if (i <= 0) throw new java.lang.IllegalArgumentException(); else return i; }


    private void setRows(final int rows)
    { this.rows = org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(rows); }

    private void setRows(final java.lang.String rows)
    { this.setRows(org.wheatgenetics.coordinate.utils.Utils.parseInt(rows)); }

    private void setCols(final int cols)
    { this.cols = org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(cols); }

    private void setCols(final java.lang.String cols)
    { this.setCols(org.wheatgenetics.coordinate.utils.Utils.parseInt(cols)); }


    private static java.lang.String convert(final int integer)
    { return integer <= 0 ? "" : java.lang.String.valueOf(integer); }
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

    // region Overridden Methods
    // region toString() Overridden Methods
    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String formatString()
    {
        return "%s" + java.lang.String.format(" [%s, title=%s, type=%d, rows=%d, cols=%d",
            super.toString(), this.getTitle(), this.getType().getCode(), this.getRows(),
            this.getCols());
    }

    @java.lang.Override
    public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "PartialTemplateModel") + "]"; }
    // endregion

    @java.lang.Override
    public boolean equals(final java.lang.Object o)
    {
        if (super.equals(o))
            if (o instanceof org.wheatgenetics.coordinate.model.PartialTemplateModel)
            {
                final org.wheatgenetics.coordinate.model.PartialTemplateModel p =
                    (org.wheatgenetics.coordinate.model.PartialTemplateModel) o;

                {
                    final java.lang.String myTitle = this.getTitle(), yourTitle = p.getTitle();
                    if (null == myTitle && null != yourTitle)
                        return false;
                    else
                        if (null != myTitle && null == yourTitle)
                            return false;
                        else if (null != myTitle) if (!myTitle.equals(yourTitle)) return false;
                }
                {
                    final org.wheatgenetics.coordinate.model.TemplateType
                        myTemplateType = this.getType(), yourTemplateType = p.getType();
                    if (null == myTemplateType && null != yourTemplateType)
                        return false;
                    else
                        if (null != myTemplateType && null == yourTemplateType)
                            return false;
                        else
                            if (null != myTemplateType)
                                if (myTemplateType.getCode() != yourTemplateType.getCode())
                                    return false;
                }
                return this.getRows() == p.getRows() && this.getCols() == p.getCols();
            }
            else return false;
        else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override
    @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone", "RedundantStringConstructorCall"})
    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
    {
        return new org.wheatgenetics.coordinate.model.PartialTemplateModel(this.getId(),
            new java.lang.String(this.getTitle()), this.getType().getCode(), this.getRows(),
            this.getCols());
    }
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