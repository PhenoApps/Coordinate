package org.wheatgenetics.coordinate.model;

/**
 * BaseTemplateModel and TemplateModel used to be one class that used
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.  The one class was split into
 * two in order to do as much local unit testing as possible (BaseTemplateModelTest) and as little
 * instrumented testing as possible (TemplateModelTest).  (BaseTemplateModel does not use
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields while TemplateModel does.)
 *
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateType
 */
abstract class BaseTemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private java.lang.String                                title                       ;
    private org.wheatgenetics.coordinate.model.TemplateType type                        ;
    private int                                             rows, cols                  ;
    private int                                             generatedExcludedCellsAmount;
    private boolean                                         colNumbering, rowNumbering  ;
    // endregion

    // region Private Methods
    private static int validate(final int value, final int minValue)
    { if (value < minValue) throw new java.lang.IllegalArgumentException(); else return value; }


    private void setRows(@android.support.annotation.IntRange(from = 1) final int rows)
    { this.rows = org.wheatgenetics.coordinate.model.BaseTemplateModel.validate(rows, 1); }

    private void setCols(@android.support.annotation.IntRange(from = 1) final int cols)
    { this.cols = org.wheatgenetics.coordinate.model.BaseTemplateModel.validate(cols, 1); }


    @java.lang.SuppressWarnings("DefaultLocale")
    private static java.lang.String[] items(
    @android.support.annotation.IntRange(from = 1) final int length, final java.lang.String label)
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

    // region Constructors
    /** Called by first and fourth TemplateModel constructors. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(id);
        this.assign(title, type, rows, cols,
            generatedExcludedCellsAmount, colNumbering, rowNumbering);
    }

    /** Called by second TemplateModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super();
        this.assign(title, type, rows, cols,
            generatedExcludedCellsAmount, colNumbering, rowNumbering);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "BaseTemplateModel") + "]"; }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final java.lang.Object object)
    {
        if (super.equals(object))
            if (object instanceof org.wheatgenetics.coordinate.model.BaseTemplateModel)
            {
                final org.wheatgenetics.coordinate.model.BaseTemplateModel baseTemplateModel =
                    (org.wheatgenetics.coordinate.model.BaseTemplateModel) object;

                {
                    final java.lang.String myTitle = this.getTitle(),
                        yourTitle = baseTemplateModel.getTitle();
                    if (null == myTitle && null != yourTitle)
                        return false;
                    else
                        if (null != myTitle && null == yourTitle)
                            return false;
                        else if (null != myTitle) if (!myTitle.equals(yourTitle)) return false;
                }

                {
                    final org.wheatgenetics.coordinate.model.TemplateType
                        myTemplateType   = this.getType()             ,
                        yourTemplateType = baseTemplateModel.getType();
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

                if (this.getRows() != baseTemplateModel.getRows()
                ||  this.getCols() != baseTemplateModel.getCols()) return false;

                if (this.generatedExcludedCellsAmount
                !=  baseTemplateModel.generatedExcludedCellsAmount)
                    return false;

                if (this.getColNumbering() != baseTemplateModel.getColNumbering()) return false;
                return this.getRowNumbering() == baseTemplateModel.getRowNumbering();
            }
            else return false;
        else return false;
    }
    // endregion

    // region Package Methods
    /** Called by first constructor, second constructor, and TemplateModel.assign(). */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void assign(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering)
    {
        this.setTitle(title); this.setType(type); this.setRows(rows); this.setCols(cols);
        this.setGeneratedExcludedCellsAmount(generatedExcludedCellsAmount);
        this.setColNumbering(colNumbering); this.setRowNumbering(rowNumbering);
    }

    @java.lang.SuppressWarnings("DefaultLocale")
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String formatString()
    {
        return "%s" + java.lang.String.format(" [%s, title=%s, type=%d, rows=%d, cols=%d, " +
            "generatedExcludedCellsAmount=%d, colNumbering=%b, rowNumbering=%b", super.toString(),
            this.getTitle(), this.getType().getCode(), this.getRows(), this.getCols(),
            this.getGeneratedExcludedCellsAmount(), this.getColNumbering(), this.getRowNumbering());
    }
    // endregion

    // region Public Methods
    public java.lang.String getTitle()                             { return this.title ; }
    public void             setTitle(final java.lang.String title) { this.title = title; }


    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }

    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }


    public int getRows() { return this.rows; } public int getCols() { return this.cols; }


    public int getGeneratedExcludedCellsAmount() { return this.generatedExcludedCellsAmount; }

    public void setGeneratedExcludedCellsAmount(
    @android.support.annotation.IntRange(from = 0) final int amount)
    {
        this.generatedExcludedCellsAmount =
            org.wheatgenetics.coordinate.model.BaseTemplateModel.validate(amount, 0);
    }


    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }


    public void assign(final java.lang.String title, final int rows, final int cols)
    {
        this.setTitle(title);       this.setRows(rows);       this.setCols(cols);
        this.setType(org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED);
    }


    public java.lang.String[] rowItems(final java.lang.String label)
    {
        return org.wheatgenetics.coordinate.model.BaseTemplateModel.items(
            this.getRows(), label);
    }

    public java.lang.String[] colItems(final java.lang.String label)
    {
        return org.wheatgenetics.coordinate.model.BaseTemplateModel.items(
            this.getCols(), label);
    }
    // endregion
}