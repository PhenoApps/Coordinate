package org.wheatgenetics.coordinate.model;

/**
 * PartialTemplateModel implements part of a template.  To get a full template implementation use
 * TemplateModel.  What purpose does PartialTemplateModel serve?  What criteria was used to
 * determine which parts of a template should go in PartialTemplateModel and which parts should go
 * in TemplateModel?  Answer: PartialTemplateModel contains those parts of a template needed by
 * JoinedGridModel.
 *
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.json.JSONException
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class PartialTemplateModel extends org.wheatgenetics.coordinate.model.Model
implements java.lang.Cloneable
{
    // region Fields
    private java.lang.String                                title     ;
    private org.wheatgenetics.coordinate.model.TemplateType type      ;
    private int                                             rows, cols;

    private boolean colNumbering, rowNumbering;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields;
    // endregion

    // region Private Methods
    private static int validate(final int i)
    { if (i <= 0) throw new java.lang.IllegalArgumentException(); else return i; }

    private void setRows(final int rows)
    { this.rows = org.wheatgenetics.coordinate.model.PartialTemplateModel.validate(rows); }

    private void setCols(final int cols)
    { this.cols = org.wheatgenetics.coordinate.model.PartialTemplateModel.validate(cols); }


    private static boolean valid(final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }


    private void assign(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        this.setTitle(title); this.setType(type); this.setRows(rows); this.setCols(cols);
        this.setColNumbering(colNumbering); this.setRowNumbering(rowNumbering);
    }


    @android.annotation.SuppressLint("DefaultLocale")
    private static java.lang.String[] items(final int length, final java.lang.String label)
    {
        if (length <= 0)
            return null;
        else
        {
            final java.lang.String result[] = new java.lang.String[length];
            for (int i = 0; i < length; i++)
                result[i] = java.lang.String.format("%s %d", label, i + 1);
            return result;
        }
    }
    // endregion

    // region Method Overview
    // signature                                                   scope
    // =========================================================== =======
    // assign(title, type, rows, cols, colNumbering, rowNumbering) private
    //     PartialTemplateModel(    type, nonNullOptionalFields)   package
    //     PartialTemplateModel(id, type                       )   private
    //     PartialTemplateModel(id, code, optionalFields       )   package
    //     assign(partialTemplateModel)                            package
    // assign(title, rows, cols)                                   public
    // endregion

    // region Constructors
    PartialTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        super();

        this.assign(title, type, rows, cols, colNumbering, rowNumbering);
        this.optionalFields = nonNullOptionalFields;
    }

    private PartialTemplateModel(final long id, final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    { super(id); this.assign(title, type, rows, cols, colNumbering, rowNumbering); }

    PartialTemplateModel(final long id, final java.lang.String title, final int code,
    final int rows, final int cols, final int colNumbering, final int rowNumbering,
    final java.lang.String optionalFields)
    {
        super(id);

        this.assign(title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(rowNumbering));

        try
        {
            this.optionalFields = new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
        }
        catch (final org.json.JSONException e) { this.optionalFields = null; }
    }
    // endregion

    // region Overridden Methods
    // region toString() Overridden Methods
    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String formatString()
    {
        return "%s" + java.lang.String.format(" [%s, title=%s, type=%d, rows=%d, cols=%d, colNumb" +
            "ering=%b, rowNumbering=%b, options=%s", super.toString(), this.getTitle(),
            this.getType().getCode(), this.getRows(), this.getCols(), this.getColNumbering(),
            this.getRowNumbering(), this.optionalFields);
    }

    @java.lang.Override
    public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "PartialTemplateModel") + "]"; }
    // endregion

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
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

                if (this.getRows() != p.getRows() || this.getCols() != p.getCols()) return false;

                if (this.getColNumbering() != p.getColNumbering()) return false;
                if (this.getRowNumbering() != p.getRowNumbering()) return false;

                if (null == this.optionalFields && null != p.optionalFields)
                    return false;
                else
                    if (null != this.optionalFields && null == p.optionalFields) return false;
                return  null == this.optionalFields ? true :
                    this.optionalFields.equals(p.optionalFields);
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
        final org.wheatgenetics.coordinate.model.PartialTemplateModel result =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(this.getId(),
                new java.lang.String(this.getTitle()), this.getType(), this.getRows(),
                this.getCols(), this.getColNumbering(), this.getRowNumbering());
        if (null != this.optionalFields) result.optionalFields =
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.optionalFields.clone();
        return result;
    }
    // endregion

    // region Package Methods
    void assign(final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel)
    {
        assert null != partialTemplateModel;
        this.assign(partialTemplateModel.getTitle(), partialTemplateModel.getType(),
            partialTemplateModel.getRows(), partialTemplateModel.getCols(),
            partialTemplateModel.getColNumbering(), partialTemplateModel.getRowNumbering());
        this.optionalFields = partialTemplateModel.optionalFields;
    }

    void makeOptionalFieldsNew()
    {
        this.optionalFields =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();
    }

    java.lang.String[] optionalFieldValues()
    { return null == this.optionalFields ? null : this.optionalFields.values(); }

    java.lang.String[] optionalFieldValues(final java.lang.String names[])
    { return null == this.optionalFields ? null : this.optionalFields.values(names); }
    // endregion

    // region Public Methods
    public java.lang.String getTitle()                             { return this.title ; }
    public void             setTitle(final java.lang.String title) { this.title = title; }


    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }

    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }


    public int getRows() { return this.rows; } public int getCols() { return this.cols; }


    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }


    public java.lang.String getOptionalFields()
    {
        try { return null == this.optionalFields ? null : this.optionalFields.toJson(); } // throws
        catch (final org.json.JSONException e) { return null; }                           //  org.-
    }                                                                                     //  json.-
                                                                                          //  JSON-
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields               //  Excep-
    optionalFieldsClone()                                                                 //  tion
    {
        try
        {
            return null == this.optionalFields ? null :
                (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                    this.optionalFields.clone();
        }
        catch (final java.lang.CloneNotSupportedException e) { return null; }
    }

    public void assign(final java.lang.String title, final int rows, final int cols)
    { this.setTitle(title); this.setRows(rows); this.setCols(cols); }


    public java.lang.String[] rowItems(final java.lang.String label)
    { return org.wheatgenetics.coordinate.model.PartialTemplateModel.items(this.getRows(), label); }

    public java.lang.String[] colItems(final java.lang.String label)
    { return org.wheatgenetics.coordinate.model.PartialTemplateModel.items(this.getCols(), label); }


    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean optionalFieldsIsEmpty()
    { return null == this.optionalFields ? true : this.optionalFields.isEmpty(); }

    public java.lang.String[] optionalFieldNames()
    { return null == this.optionalFields ? null : this.optionalFields.names(); }

    public boolean[] optionalFieldChecks()
    { return null == this.optionalFields ? null : this.optionalFields.checks(); }

    public void addOptionalField(final java.lang.String name, final java.lang.String value)
    {
        assert null != this.optionalFields;
        this.optionalFields.add(/* name => */ name, /* value => */ value, /* hint => */ "");
    }

    public void setOptionalFieldChecked(final int index, final boolean checked)
    { assert null != this.optionalFields; this.optionalFields.get(index).setChecked(checked); }

    public org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
    makeCheckedOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
            this.optionalFields);
    }

    public java.lang.String getFirstOptionalFieldValue()
    { assert null != this.optionalFields; return this.optionalFields.getFirstValue(); }

    public java.lang.String getFirstOptionalFieldDatedValue()
    { assert null != this.optionalFields; return this.optionalFields.getDatedFirstValue(); }
    // endregion
}