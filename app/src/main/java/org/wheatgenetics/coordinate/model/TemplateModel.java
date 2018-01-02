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
    private org.wheatgenetics.coordinate.model.Cells     initialExcludedCellsInstance = null;
    private org.wheatgenetics.coordinate.model.RowOrCols excludedRowsInstance         = null,
        excludedColsInstance = null;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
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

    private org.wheatgenetics.coordinate.model.Cells initialExcludedCells()
    {
        if (null == this.initialExcludedCellsInstance)
            this.initialExcludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
        return this.initialExcludedCellsInstance;
    }

    private org.wheatgenetics.coordinate.model.RowOrCols excludedRows()
    {
        if (null == this.excludedRowsInstance) this.excludedRowsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getRows());
        return this.excludedRowsInstance;
    }

    private org.wheatgenetics.coordinate.model.RowOrCols excludedCols()
    {
        if (null == this.excludedColsInstance) this.excludedColsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getCols());
        return this.excludedColsInstance;
    }

    private void clearExcludeds()
    {
        if (null != this.initialExcludedCellsInstance) this.initialExcludedCellsInstance.clear();
        if (null != this.excludedRowsInstance        ) this.excludedRowsInstance.clear        ();
        if (null != this.excludedColsInstance        ) this.excludedColsInstance.clear        ();
    }
    // endregion

    // region Constructors
    /** Called by clone(). */
    private TemplateModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     initialExcludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows        ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols        ,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.initialExcludedCellsInstance  = initialExcludedCells;
        this.excludedRowsInstance          = excludedRows        ;
        this.excludedColsInstance          = excludedCols        ;
        this.nonNullOptionalFieldsInstance = optionalFields      ;
    }

    /** Called by clone() and third constructor. */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     initialExcludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows        ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols        ,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.initialExcludedCellsInstance  = initialExcludedCells;
        this.excludedRowsInstance          = excludedRows        ;
        this.excludedColsInstance          = excludedCols        ;
        this.nonNullOptionalFieldsInstance = optionalFields      ;
    }

    /** Called by makeSeedDefault(), makeDNADefault(), and makeUserDefined(). */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
            null, null, null, true, rowNumbering, optionalFields, 0);
    }

    /** Called by JoinedGridModel constructor and TemplatesTable.make(). */
    public TemplateModel(
    @android.support.annotation.IntRange(from = 1)         final long             id             ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    java.lang.String initialExcludedCells,
    java.lang.String excludedRows, java.lang.String excludedCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            generatedExcludedCellsAmount,
            org.wheatgenetics.coordinate.model.TemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.TemplateModel.valid(rowNumbering), timestamp);

        if (null != initialExcludedCells)
        {
            initialExcludedCells = initialExcludedCells.trim();
            if (initialExcludedCells.length() > 0)
                this.initialExcludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                    /* json   => */ initialExcludedCells,
                    /* maxRow => */ this.getRows()      ,
                    /* maxCol => */ this.getCols()      );
        }

        if (null != excludedRows)
        {
            excludedRows = excludedRows.trim();
            if (excludedRows.length() > 0)
                this.excludedRowsInstance = new org.wheatgenetics.coordinate.model.RowOrCols(
                    /* json => */ excludedRows, /* maxValue => */ this.getRows());
        }

        if (null != excludedCols)
        {
            excludedCols = excludedCols.trim();
            if (excludedCols.length() > 0)
                this.excludedColsInstance = new org.wheatgenetics.coordinate.model.RowOrCols(
                    /* json => */ excludedCols, /* maxValue => */ this.getCols());
        }

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
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
            ", initialExcludedCells=%s, excludedRows=%s, excludedCols=%s]";
    }

    @java.lang.Override
    public java.lang.String toString()
    {
        return java.lang.String.format(this.formatString(), "TemplateModel",
            this.initialExcludedCellsInstance, this.excludedRowsInstance,
            this.excludedColsInstance);
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

                if (null == this.initialExcludedCellsInstance
                &&  null != templateModel.initialExcludedCellsInstance)
                    return false;
                else
                    if (null != this.initialExcludedCellsInstance
                    &&  null == templateModel.initialExcludedCellsInstance)
                        return false;
                if (null != this.initialExcludedCellsInstance)
                    if (!this.initialExcludedCellsInstance.equals(
                    templateModel.initialExcludedCellsInstance)) return false;

                if (null == this.excludedRowsInstance && null != templateModel.excludedRowsInstance)
                    return false;
                else
                    if (null != this.excludedRowsInstance
                    &&  null == templateModel.excludedRowsInstance)
                        return false;
                if (null != this.excludedRowsInstance)
                    if (!this.excludedRowsInstance.equals(templateModel.excludedRowsInstance))
                        return false;

                if (null == this.excludedColsInstance && null != templateModel.excludedColsInstance)
                    return false;
                else
                    if (null != this.excludedColsInstance
                    &&  null == templateModel.excludedColsInstance)
                        return false;
                if (null != this.excludedColsInstance)
                    if (!this.excludedColsInstance.equals(templateModel.excludedColsInstance))
                        return false;

                if (null == this.nonNullOptionalFieldsInstance
                &&  null != templateModel.nonNullOptionalFieldsInstance)
                    return false;
                else
                    if (null != this.nonNullOptionalFieldsInstance
                    &&  null == templateModel.nonNullOptionalFieldsInstance)
                        return false;
                if (null == this.nonNullOptionalFieldsInstance)
                    return true;
                else
                    return this.nonNullOptionalFieldsInstance.equals(
                        templateModel.nonNullOptionalFieldsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    {
        final long                                     id                   = this.getId();
        final org.wheatgenetics.coordinate.model.Cells initialExcludedCells =
            null == this.initialExcludedCellsInstance ? null :
                (org.wheatgenetics.coordinate.model.Cells)
                    this.initialExcludedCellsInstance.clone();
        final org.wheatgenetics.coordinate.model.RowOrCols
            excludedRows = null == this.excludedRowsInstance ? null :
                (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedRowsInstance.clone(),
            excludedCols = null == this.excludedColsInstance ? null :
                (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedColsInstance.clone();
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
            null == this.nonNullOptionalFieldsInstance ? null :
                (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                    this.nonNullOptionalFieldsInstance.clone();

        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            return  new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* initialExcludedCells         => */ initialExcludedCells                  ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* timestamp                    => */ this.getTimestamp()                   );
        else
            return new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ id,
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* initialExcludedCells         => */ initialExcludedCells                  ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* timestamp                    => */ this.getTimestamp()                   );
    }
    // endregion

    /** Called by JoinedGridModel.populate(). */
    void assign(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        assert null != templateModel;
        super.assign(templateModel.getTitle(), templateModel.getType(), templateModel.getRows(),
            templateModel.getCols(), templateModel.getGeneratedExcludedCellsAmount(),
            templateModel.getColNumbering(), templateModel.getRowNumbering());
        this.nonNullOptionalFieldsInstance = templateModel.nonNullOptionalFieldsInstance; // TODO: Assign or clone?

        this.clearExcludeds();                                             // TODO: Still necessary?
    }

    // region Public Methods
    // region initialExcludedCells Public Methods
    public void addInitialExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.initialExcludedCells().add(row, col); }

    public void makeOneRandomCell()                                           // TODO: Remove later.
    {
        this.initialExcludedCells().makeOneRandomCell(
            /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
    }

    public void makeRandomCells(@android.support.annotation.IntRange(from = 1) final int amount)  // TODO: Remove later.
    {
        this.initialExcludedCells().makeRandomCells(
            amount, /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
    }

    public java.lang.String getInitialExcludedCellsAsJson()
    {
        return null == this.initialExcludedCellsInstance ?
            null : this.initialExcludedCellsInstance.json();
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isInitialExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return null == this.initialExcludedCellsInstance ? false :
            this.initialExcludedCellsInstance.contains(row, col);
    }
    // endregion

    // region excludedRows, excludedCols Public Methods
    public void addExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { this.excludedRows().add(row); }

    public void addExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCols().add(col); }

    public java.lang.String getExcludedRowsAsJson()
    { return null == this.excludedRowsInstance ? null : this.excludedRowsInstance.json(); }

    public java.lang.String getExcludedColsAsJson()
    { return null == this.excludedColsInstance ? null : this.excludedColsInstance.json(); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { return null == this.excludedRowsInstance ? false : this.excludedRowsInstance.contains(row); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.excludedColsInstance ? false : this.excludedColsInstance.contains(col); }
    // endregion

    // region optionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean optionalFieldsIsEmpty()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            true : this.nonNullOptionalFieldsInstance.isEmpty();
    }

    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsClone()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.nonNullOptionalFieldsInstance.clone();
    }

    public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }

    public java.lang.String getFirstOptionalFieldValue()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            this.nonNullOptionalFieldsInstance.getFirstValue();
    }
    // endregion

    public org.wheatgenetics.coordinate.model.Cell nextFreeCell(              // TODO: Remove later.
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
    static org.wheatgenetics.coordinate.model.TemplateModel makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title => */ "Seed Tray"                                         ,
                /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows  => */  6                                                  ,
                /* cols  => */ 20                                                  ,
                /* generatedExcludedCellsAmount => */ 0                            ,
                /* rowNumbering                 => */ true                         ,
                /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFields.makeSeedDefault());
        {
            final org.wheatgenetics.coordinate.model.RowOrCols excludedRows = result.excludedRows();
            excludedRows.add(2); excludedRows.add(5);
        }
        return result;
    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */ "DNA Plate"                                        ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows  => */  8                                                 ,
            /* cols  => */ 12                                                 ,
            /* generatedExcludedCellsAmount => */ 1                           ,
            /* rowNumbering                 => */ false                       ,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeDNADefault());
    }

    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */ ""                                                         ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */ 20                                                         ,
            /* cols  => */ 10                                                         ,
            /* generatedExcludedCellsAmount => */ 1                                   ,
            /* rowNumbering                 => */ false                               ,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeNew());
    }
    // endregion
    // endregion
}