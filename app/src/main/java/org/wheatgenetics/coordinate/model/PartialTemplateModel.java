package org.wheatgenetics.coordinate.model;

/**
 * BasePartialTemplateModel and PartialTemplateModel used to be one class that used
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.  The one class was split into
 * two in order to do as much local unit testing as possible (BasePartialTemplateModelTest) and as
 * little instrumented testing as possible (PartialTemplateModelTest).  (BasePartialTemplateModel
 * does not use org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields while
 * PartialTemplateModel does.)
 *
 * PartialTemplateModel implements part of a template.  To get a full template implementation  use
 * TemplateModel.  What purpose does PartialTemplateModel serve?  What criteria was used to
 * determine which parts of a template should go in PartialTemplateModel and which parts should go
 * in TemplateModel?  Answer: PartialTemplateModel contains those parts of a template needed by
 * JoinedGridModel.
 *
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.BasePartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class PartialTemplateModel
extends org.wheatgenetics.coordinate.model.BasePartialTemplateModel implements java.lang.Cloneable
{
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsInstance;

    // region Private Methods
    /** 0 means false and 1 means true. */
    private static boolean valid(final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    {
        if (null == this.optionalFieldsInstance) this.optionalFieldsInstance =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        return this.optionalFieldsInstance;
    }
    // endregion

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    PartialTemplateModel(final long id, final java.lang.String title,
    @android.support.annotation.IntRange(from = 0, to = 2) final int code        ,
    @android.support.annotation.IntRange(from = 1        ) final int rows        ,
    @android.support.annotation.IntRange(from = 1        ) final int cols        ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    java.lang.String optionalFields)
    {
        super(id, title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.PartialTemplateModel.valid(rowNumbering));

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.optionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    PartialTemplateModel(final long id, final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols, final boolean colNumbering,
    final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super(id, title, type, rows, cols, colNumbering, rowNumbering);
        this.optionalFieldsInstance = optionalFields;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    PartialTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols, final boolean colNumbering,
    final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super(title, type, rows, cols, colNumbering, rowNumbering);
        this.optionalFieldsInstance = optionalFields;
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String formatString()
    {
        return java.lang.String.format(super.formatString() + ", options=%s", "%s",
            null == this.optionalFieldsInstance ? "" : this.optionalFieldsInstance.toString());
    }

    @java.lang.Override
    public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "PartialTemplateModel") + "]"; }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean equals(final java.lang.Object o)
    {
        if (super.equals(o))
            if (o instanceof org.wheatgenetics.coordinate.model.PartialTemplateModel)
            {
                final org.wheatgenetics.coordinate.model.PartialTemplateModel p =
                    (org.wheatgenetics.coordinate.model.PartialTemplateModel) o;

                if (null == this.optionalFieldsInstance && null != p.optionalFieldsInstance)
                    return false;
                else
                    if (null != this.optionalFieldsInstance && null == p.optionalFieldsInstance)
                        return false;
                return  null == this.optionalFieldsInstance ? true :
                    this.optionalFieldsInstance.equals(p.optionalFieldsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException", "RedundantStringConstructorCall"})
    protected java.lang.Object clone()
    {
        return new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ this.getId()                         ,
            /* title          => */ new java.lang.String(this.getTitle()),
            /* type           => */ this.getType()                       ,
            /* rows           => */ this.getRows()                       ,
            /* cols           => */ this.getCols()                       ,
            /* colNumbering   => */ this.getColNumbering()               ,
            /* rowNumbering   => */ this.getRowNumbering()               ,
            /* optionalFields => */ this.optionalFieldsClone()           );
    }
    // endregion

    // region Package Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void assign(final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel)
    {
        assert null != partialTemplateModel;
        super.assign(partialTemplateModel.getTitle(), partialTemplateModel.getType(),
            partialTemplateModel.getRows(), partialTemplateModel.getCols(),
            partialTemplateModel.getColNumbering(), partialTemplateModel.getRowNumbering());
        this.optionalFieldsInstance = partialTemplateModel.optionalFieldsInstance; // TODO: Assign or clone?
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void makeOptionalFieldsNew()
    {
        this.optionalFieldsInstance =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String[] optionalFieldValues()
    { return null == this.optionalFieldsInstance ? null : this.optionalFieldsInstance.values(); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String[] optionalFieldValues(final java.lang.String names[])
    {
        return null == this.optionalFieldsInstance ? null :
            this.optionalFieldsInstance.values(names);
    }
    // endregion

    // region Public Methods
    public java.lang.String getOptionalFields()
    { return null == this.optionalFieldsInstance ? null : this.optionalFieldsInstance.toJson(); }

    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsClone()
    {
        return null == this.optionalFieldsInstance ? null :
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.optionalFieldsInstance.clone();
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean optionalFieldsIsEmpty()
    { return null == this.optionalFieldsInstance ? true : this.optionalFieldsInstance.isEmpty(); }

    public java.lang.String[] optionalFieldNames()
    { return null == this.optionalFieldsInstance ? null : this.optionalFieldsInstance.names(); }

    public boolean[] optionalFieldChecks()
    { return null == this.optionalFieldsInstance ? null : this.optionalFieldsInstance.checks(); }

    public void addOptionalField(final java.lang.String name, final java.lang.String value)
    { this.optionalFields().add(/* name => */ name, /* value => */ value, /* hint => */ ""); }

    public void setOptionalFieldChecked(
    @android.support.annotation.IntRange(from = 0) final int index, final boolean checked)
    {
        assert null != this.optionalFieldsInstance;
        this.optionalFieldsInstance.get(index).setChecked(checked);
    }

    public java.lang.String getFirstOptionalFieldValue()
    {
        assert null != this.optionalFieldsInstance;
        return this.optionalFieldsInstance.getFirstValue();
    }

    public java.lang.String getFirstOptionalFieldDatedValue()
    {
        assert null != this.optionalFieldsInstance;
        return this.optionalFieldsInstance.getDatedFirstValue();
    }
    // endregion
}