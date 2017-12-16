package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class TemplateModel extends org.wheatgenetics.coordinate.model.BaseTemplateModel
implements java.lang.Cloneable
{
    // region Fields
    private org.wheatgenetics.coordinate.model.Cells     initialExcludeCellsInstance = null;
    private org.wheatgenetics.coordinate.model.RowOrCols excludeRowsInstance         = null,
        excludeColsInstance = null;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
    private long timestamp;
    // endregion

    // region Private Methods
    /** 0 means false and 1 means true. */
    private static boolean valid(final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }

    private org.wheatgenetics.coordinate.model.Cells initialExcludeCells()
    {
        if (null == this.initialExcludeCellsInstance)
            this.initialExcludeCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
        return this.initialExcludeCellsInstance;
    }

    private org.wheatgenetics.coordinate.model.RowOrCols excludeRows()
    {
        if (null == this.excludeRowsInstance) this.excludeRowsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getRows());
        return this.excludeRowsInstance;
    }

    private org.wheatgenetics.coordinate.model.RowOrCols excludeCols()
    {
        if (null == this.excludeColsInstance) this.excludeColsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getCols());
        return this.excludeColsInstance;
    }

    private void clearExcludes()
    {
        if (null != this.initialExcludeCellsInstance) this.initialExcludeCellsInstance.clear();
        if (null != this.excludeRowsInstance        ) this.excludeRowsInstance.clear        ();
        if (null != this.excludeColsInstance        ) this.excludeColsInstance.clear        ();
    }
    // endregion

    // region Constructors
    private TemplateModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super(id, title, type, rows, cols, colNumbering, rowNumbering);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super(title, type, rows, cols, colNumbering, rowNumbering);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    public TemplateModel(
    @android.support.annotation.IntRange(from = 1)         final long             id   ,
                                                           final java.lang.String title,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols ,
    java.lang.String initialExcludeCells,
    java.lang.String excludeRows, java.lang.String excludeCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            org.wheatgenetics.coordinate.model.TemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.TemplateModel.valid(rowNumbering));

        if (null != initialExcludeCells)
        {
            initialExcludeCells = initialExcludeCells.trim();
            if (initialExcludeCells.length() > 0)
                this.initialExcludeCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                    /* json   => */ initialExcludeCells,
                    /* maxRow => */ this.getRows()     ,
                    /* maxCol => */ this.getCols()     );
        }

        if (null != excludeRows)
        {
            excludeRows = excludeRows.trim();
            if (excludeRows.length() > 0)
                this.excludeRowsInstance = new org.wheatgenetics.coordinate.model.RowOrCols(
                    /* json => */ excludeRows, /* maxValue => */ this.getRows());
        }

        if (null != excludeCols)
        {
            excludeCols = excludeCols.trim();
            if (excludeCols.length() > 0)
                this.excludeColsInstance = new org.wheatgenetics.coordinate.model.RowOrCols(
                    /* json => */ excludeCols, /* maxValue => */ this.getCols());
        }

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);

        this.timestamp = timestamp;
    }
    // endregion

    // region Overridden Methods
    // region toString() Overridden Methods
    @java.lang.Override
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String formatString()
    {
        return java.lang.String.format(super.formatString() + ", options=%s", "%s",
                null == this.nonNullOptionalFieldsInstance ? "" :
                    this.nonNullOptionalFieldsInstance.toString()) +
            ", initialExcludedCells=%s, excludeRows=%s, excludeCols=%s, stamp=%d]";
    }

    @java.lang.Override
    public java.lang.String toString()
    {
        return java.lang.String.format(this.formatString(), "TemplateModel",
            this.initialExcludeCellsInstance, this.excludeRowsInstance, this.excludeColsInstance,
            this.getTimestamp());
    }
    // endregion

    @java.lang.Override
    public boolean equals(final java.lang.Object object)
    {
        if (super.equals(object))
            if (object instanceof org.wheatgenetics.coordinate.model.TemplateModel)
            {
                final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                    (org.wheatgenetics.coordinate.model.TemplateModel) object;

                if (null == this.initialExcludeCellsInstance
                &&  null != templateModel.initialExcludeCellsInstance)
                    return false;
                else
                    if (null != this.initialExcludeCellsInstance
                    &&  null == templateModel.initialExcludeCellsInstance)
                        return false;
                if (null != this.initialExcludeCellsInstance)
                    if (!this.initialExcludeCellsInstance.equals(
                    templateModel.initialExcludeCellsInstance)) return false;

                if (null == this.excludeRowsInstance && null != templateModel.excludeRowsInstance)
                    return false;
                else
                    if (null != this.excludeRowsInstance
                    &&  null == templateModel.excludeRowsInstance)
                        return false;
                if (null != this.excludeRowsInstance)
                    if (!this.excludeRowsInstance.equals(templateModel.excludeRowsInstance))
                        return false;

                if (null == this.excludeColsInstance && null != templateModel.excludeColsInstance)
                    return false;
                else
                    if (null != this.excludeColsInstance
                    &&  null == templateModel.excludeColsInstance)
                        return false;
                if (null != this.excludeColsInstance)
                    if (!this.excludeColsInstance.equals(templateModel.excludeColsInstance))
                        return false;

                if (null == this.nonNullOptionalFieldsInstance
                &&  null != templateModel.nonNullOptionalFieldsInstance)
                    return false;
                else
                    if (null != this.nonNullOptionalFieldsInstance
                    &&  null == templateModel.nonNullOptionalFieldsInstance)
                        return false;
                if (null != this.nonNullOptionalFieldsInstance)
                    if (!this.nonNullOptionalFieldsInstance.equals(
                    templateModel.nonNullOptionalFieldsInstance)) return false;

                return this.getTimestamp() == templateModel.getTimestamp();
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    {
        final long                                                             id = this.getId();
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
            null == this.nonNullOptionalFieldsInstance ? null :
                (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                    this.nonNullOptionalFieldsInstance.clone();

        final org.wheatgenetics.coordinate.model.TemplateModel result =
            org.wheatgenetics.coordinate.model.Model.illegal(id) ?
            new org.wheatgenetics.coordinate.model.TemplateModel(this.getTitle(), this.getType(),
                this.getRows(), this.getCols(), this.getColNumbering(), this.getRowNumbering(),
                optionalFields) :
            new org.wheatgenetics.coordinate.model.TemplateModel(id, this.getTitle(),
                this.getType(), this.getRows(), this.getCols(), this.getColNumbering(),
                this.getRowNumbering(), optionalFields);

        if (null != this.initialExcludeCellsInstance) result.initialExcludeCellsInstance =
            (org.wheatgenetics.coordinate.model.Cells) this.initialExcludeCellsInstance.clone();

        if (null != this.excludeRowsInstance) result.excludeRowsInstance =
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludeRowsInstance.clone();
        if (null != this.excludeColsInstance) result.excludeColsInstance =
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludeColsInstance.clone();

        result.timestamp = this.getTimestamp();

        return result;
    }
    // endregion

    void assign(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        assert null != templateModel;
        super.assign(templateModel.getTitle(), templateModel.getType(),
            templateModel.getRows(), templateModel.getCols(),
            templateModel.getColNumbering(), templateModel.getRowNumbering());

        this.nonNullOptionalFieldsInstance = templateModel.nonNullOptionalFieldsInstance; // TODO: Assign or clone?

        this.clearExcludes();
    }

    // region Public Methods
    // region initialExcludeCells Public Methods
    public void addInitialExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.initialExcludeCells().add(row, col); }

    public void makeOneRandomCell()
    {
        this.initialExcludeCells().makeOneRandomCell(
            /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
    }

    public void makeRandomCells(@android.support.annotation.IntRange(from = 1) final int amount)
    {
        this.initialExcludeCells().makeRandomCells(
            amount, /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
    }

    public java.lang.String getInitialExcludedCellsAsJson()
    {
        return null == this.initialExcludeCellsInstance ?
            null : this.initialExcludeCellsInstance.json();
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isInitialExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return null == this.initialExcludeCellsInstance ? false :
            this.initialExcludeCellsInstance.contains(row, col);
    }
    // endregion

    // region excludeRows, excludeCols Public Methods
    public void addExcludeRow(@android.support.annotation.IntRange(from = 1) final int row)
    { this.excludeRows().add(row); }

    public void addExcludeCol(@android.support.annotation.IntRange(from = 1) final int col)
    { this.excludeCols().add(col); }

    public java.lang.String getExcludeRowsAsJson()
    { return null == this.excludeRowsInstance ? null : this.excludeRowsInstance.json(); }

    public java.lang.String getExcludeColsAsJson()
    { return null == this.excludeColsInstance ? null : this.excludeColsInstance.json(); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { return null == this.excludeRowsInstance ? false : this.excludeRowsInstance.contains(row); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.excludeColsInstance ? false : this.excludeColsInstance.contains(col); }
    // endregion

    // region optionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }
    // endregion

    public long getTimestamp() { return this.timestamp; }

    public void clearExcludesAndOptionalFields()
    {
        this.clearExcludes();
        this.nonNullOptionalFieldsInstance =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();
    }

    public org.wheatgenetics.coordinate.model.Cell nextFreeCell(
    final org.wheatgenetics.coordinate.model.Cell candidateFreeCell)
    {
        if (null == candidateFreeCell)
            return null;
        else
        {
            {
                final int lastRow = this.getRows(), lastCol = this.getCols();
                candidateFreeCell.inRange(              // throws java.lang.IllegalArgumentException
                    /* maxCell => */ new org.wheatgenetics.coordinate.model.Cell(lastRow, lastCol));

                boolean candidateCol = true;
                for (int col = candidateFreeCell.getCol().getValue(); col <= lastCol; col++)
                {
                    if (!this.isExcludedCol(col))
                        for (int row = candidateCol ? candidateFreeCell.getRow().getValue() : 1;
                        row <= lastRow; row++)
                            if (!this.isExcludedRow(row)) if (!this.isInitialExcludedCell(row, col))
                                return new org.wheatgenetics.coordinate.model.Cell(row, col);
                    candidateCol = false;
                }
            }
            return null;
        }
    }

    // region checkedItems Public Methods
    public boolean[] rowCheckedItems()
    {
        final int rows = this.getRows();
        if (rows <= 0)
            return null;
        else
        {
            final boolean result[] = new boolean[rows];
            for (int i = 0; i < rows; i++) result[i] = this.isExcludedRow(i + 1);
            return result;
        }
    }

    public boolean[] colCheckedItems()
    {
        final int cols = this.getCols();
        if (cols <= 0)
            return null;
        else
        {
            final boolean result[] = new boolean[cols];
            for (int i = 0; i < cols; i++) result[i] = this.isExcludedCol(i + 1);
            return result;
        }
    }
    // endregion

    // region Make Public Methods
    public static org.wheatgenetics.coordinate.model.TemplateModel makeInitial()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title          => */ ""                                                  ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
            /* rows           => */ 20                                                  ,
            /* cols           => */ 10                                                  ,
            /* colNumbering   => */ true                                                ,
            /* rowNumbering   => */ false                                               ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField.
                NonNullOptionalFields.makeInitial());

    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title          => */ "Seed Tray"                                         ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows           => */  6                                                  ,
                /* cols           => */ 20                                                  ,
                /* colNumbering   => */ true                                                ,
                /* rowNumbering   => */ true                                                ,
                /* optionalFields => */ org.wheatgenetics.coordinate.optionalField.
                    NonNullOptionalFields.makeSeedDefault());
        {
            final org.wheatgenetics.coordinate.model.RowOrCols excludeRows = result.excludeRows();
            excludeRows.add(2); excludeRows.add(5);
        }
        return result;
    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title          => */ "DNA Plate"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */  8                                                 ,
            /* cols           => */ 12                                                 ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField.
                NonNullOptionalFields.makeDNADefault());
    }
    // endregion
    // endregion
}