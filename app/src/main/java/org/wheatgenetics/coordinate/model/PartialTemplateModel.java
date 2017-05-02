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

public class PartialTemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    private java.lang.String                                title;
    private org.wheatgenetics.coordinate.model.TemplateType type =
        org.wheatgenetics.coordinate.model.TemplateType.SEED;
    private int rows, cols;

    PartialTemplateModel() { super(); }

    PartialTemplateModel(final long id) { super(id); }

    PartialTemplateModel(final long id, final java.lang.String title,
    final int type, final int rows, final int cols)
    {
        this(id);

        this.title = title;
        this.type  = org.wheatgenetics.coordinate.model.TemplateType.get(type);

        if (rows <= 0) throw new java.lang.IndexOutOfBoundsException(); else this.rows = rows;
        if (cols <= 0) throw new java.lang.IndexOutOfBoundsException(); else this.cols = cols;
    }

    public java.lang.String getTitle() { return this.title; }
    public void setTitle(final java.lang.String title) { this.title = title; }

    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }

    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }

    public void setType(final int code)
    { this.type = org.wheatgenetics.coordinate.model.TemplateType.get(code); }
}