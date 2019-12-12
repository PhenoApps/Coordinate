package org.wheatgenetics.coordinate.model;

/**
 * BaseTemplateModel, DisplayTemplateModel, and TemplateModel used to be one class that used
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.  The one class was split into
 * three partly to do as much local unit testing as possible (BaseTemplateModelTest) and as little
 * instrumented testing as possible (DisplayTemplateModelTest and TemplateModelTest).
 * (BaseTemplateModel does not use org.json.* while DisplayTemplateModel and TemplateModel do.)
 *
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.TemplateType
 */
abstract class BaseTemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    @androidx.annotation.IntRange(from = 0) private final long timestamp;

    private java.lang.String                                title;
    private org.wheatgenetics.coordinate.model.TemplateType type ;

    @androidx.annotation.IntRange(from = 1) private int rows, cols                  ;
    @androidx.annotation.IntRange(from = 0) private int generatedExcludedCellsAmount;

    private boolean          colNumbering, rowNumbering;
    private java.lang.String entryLabel                ;
    // endregion

    // region Private Methods
    private void setRows(@androidx.annotation.IntRange(from = 1) final int rows)
    { this.rows = org.wheatgenetics.coordinate.Utils.valid(rows,1); }

    private void setCols(@androidx.annotation.IntRange(from = 1) final int cols)
    { this.cols = org.wheatgenetics.coordinate.Utils.valid(cols,1); }


    /** Called by first and second constructor. */
    private void assign(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering)
    {
        this.setTitle(title); this.setType(type); this.setRows(rows); this.setCols(cols);
        this.setGeneratedExcludedCellsAmount(generatedExcludedCellsAmount);
        this.setColNumbering(colNumbering); this.setRowNumbering(rowNumbering);
    }


    @java.lang.SuppressWarnings({"DefaultLocale"}) @androidx.annotation.Nullable
    private static java.lang.String[] items(
    @androidx.annotation.IntRange(from = 1) final int length, final java.lang.String label)
    {
        if (length <= 0)
            return null;
        else
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String result[] = new java.lang.String[length];
            for (int i = 0; i < length; i++)
                result[i] = java.lang.String.format("%s %d", label, i + 1);
            return result;
        }
    }
    // endregion

    // region Constructors
    /** Called by first and fourth DisplayTemplateModel constructors. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(@androidx.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id);
        this.assign(title, type, rows, cols,
            generatedExcludedCellsAmount, colNumbering, rowNumbering);
        this.timestamp = timestamp;
    }

    /** Called by second DisplayTemplateModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean colNumbering, final boolean rowNumbering,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super();
        this.assign(title, type, rows, cols,
            generatedExcludedCellsAmount, colNumbering, rowNumbering);
        this.timestamp = timestamp;
    }

    /** Called by third DisplayTemplateModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel()
    {
        super();

        this.type      = org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        this.timestamp = java.lang.System.currentTimeMillis()                       ;
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "BaseTemplateModel") + "]"; }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (super.equals(object))
            if (object instanceof org.wheatgenetics.coordinate.model.BaseTemplateModel)
            {
                final org.wheatgenetics.coordinate.model.BaseTemplateModel baseTemplateModel =
                    (org.wheatgenetics.coordinate.model.BaseTemplateModel) object;

                {
                    final java.lang.String
                        myTitle = this.getTitle(), yourTitle = baseTemplateModel.getTitle();
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

                if (this.getGeneratedExcludedCellsAmount()
                !=  baseTemplateModel.getGeneratedExcludedCellsAmount())
                    return false;

                if (this.getColNumbering() != baseTemplateModel.getColNumbering()) return false;
                if (this.getRowNumbering() != baseTemplateModel.getRowNumbering()) return false;

                if (this.getTimestamp() != baseTemplateModel.getTimestamp()) return false;

                {
                    final java.lang.String
                        myEntryLabel   = this.getEntryLabel()             ,
                        yourEntryLabel = baseTemplateModel.getEntryLabel();
                    if (null == myEntryLabel && null != yourEntryLabel)
                        return false;
                    else
                        if (null != myEntryLabel && null == yourEntryLabel)
                            return false;
                        else
                            // noinspection SimplifiableConditionalExpression
                            return null == myEntryLabel ? true :
                                myEntryLabel.equals(yourEntryLabel);
                }
            }
            else return false;
        else return false;
    }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.SuppressWarnings({"DefaultLocale"}) java.lang.String formatString()
    {
        return "%s" + java.lang.String.format(" [%s, title=%s, type=%d, rows=%d, cols=%d, genera" +
            "tedExcludedCellsAmount=%d, colNumbering=%b, rowNumbering=%b, entryLabel=%s, stamp=%d",
            super.toString(), this.getTitle(), this.getType().getCode(), this.getRows(),
            this.getCols(), this.getGeneratedExcludedCellsAmount(), this.getColNumbering(),
            this.getRowNumbering(), this.getEntryLabel(), this.getTimestamp());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setRows(final java.lang.String rows) { this.setRows(java.lang.Integer.valueOf(rows)); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setCols(final java.lang.String cols) { this.setCols(java.lang.Integer.valueOf(cols)); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setGeneratedExcludedCellsAmount(final java.lang.String generatedExcludedCellsAmount)
    {
        this.setGeneratedExcludedCellsAmount(
            java.lang.Integer.valueOf(generatedExcludedCellsAmount));
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setColNumbering(final java.lang.String colNumbering)
    { this.setColNumbering(java.lang.Boolean.valueOf(colNumbering)); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setRowNumbering(final java.lang.String rowNumbering)
    { this.setRowNumbering(java.lang.Boolean.valueOf(rowNumbering)); }

    boolean entryLabelIsNotNull() { return null != this.getEntryLabel(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setEntryLabel(final java.lang.String entryLabel) { this.entryLabel = entryLabel; }
    // endregion

    // region Public Methods
    public java.lang.String getTitle()                             { return this.title ; }
    public void             setTitle(final java.lang.String title) { this.title = title; }


    public org.wheatgenetics.coordinate.model.TemplateType getType() { return this.type; }

    public void setType(final org.wheatgenetics.coordinate.model.TemplateType templateType)
    { this.type = templateType; }


    @androidx.annotation.IntRange(from = 1) public int getRows() { return this.rows; }
    @androidx.annotation.IntRange(from = 1) public int getCols() { return this.cols; }

    @androidx.annotation.NonNull public java.lang.String getRowsAsString()
    { return java.lang.String.valueOf(this.getRows()); }

    @androidx.annotation.NonNull public java.lang.String getColsAsString()
    { return java.lang.String.valueOf(this.getCols()); }


    @androidx.annotation.IntRange(from = 0) public int getGeneratedExcludedCellsAmount()
    { return this.generatedExcludedCellsAmount; }

    public void setGeneratedExcludedCellsAmount(
    @androidx.annotation.IntRange(from = 0) final int amount)
    {
        this.generatedExcludedCellsAmount =
            org.wheatgenetics.coordinate.Utils.valid(amount,0);
    }


    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }


    public java.lang.String getEntryLabel() { return this.entryLabel; }


    @androidx.annotation.IntRange(from = 0) public long getTimestamp() { return this.timestamp; }

    @androidx.annotation.Nullable public java.lang.CharSequence getFormattedTimestamp()
    {
        final long timestamp = this.getTimestamp();
        return timestamp < 1 ? null : org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp);
    }


    public void assign(final java.lang.String title,
    @androidx.annotation.IntRange(from = 1) final int rows,
    @androidx.annotation.IntRange(from = 1) final int cols)
    {
        this.setTitle(title);       this.setRows(rows);       this.setCols(cols);
        this.setType(org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED);
    }


    public boolean isDefaultTemplate() { return this.getType().isDefaultTemplate(); }


    @androidx.annotation.Nullable public java.lang.String[] rowItems(final java.lang.String label)
    { return org.wheatgenetics.coordinate.model.BaseTemplateModel.items(this.getRows(), label); }

    @androidx.annotation.Nullable public java.lang.String[] colItems(final java.lang.String label)
    { return org.wheatgenetics.coordinate.model.BaseTemplateModel.items(this.getCols(), label); }
    // endregion
}