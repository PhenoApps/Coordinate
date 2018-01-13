package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
{
    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel = null;
    private org.wheatgenetics.coordinate.model.EntryModels   entryModels   = null;
    // endregion

    private org.wheatgenetics.coordinate.model.Cells excludedCells()
    {
        if (null == this.excludedCellsInstance)
            this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
        return this.excludedCellsInstance;
    }

    // region Constructors
    /** Used by GridCreator. */
    public JoinedGridModel(final java.lang.String                          person        ,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final org.wheatgenetics.coordinate.model.TemplateModel                 templateModel )
    {
        super(
            /* templateId     => */ templateModel.getId()                  ,
            /* person         => */ person                                 ,
            /* excludedCells  => */ templateModel.getInitialExcludedCells(),
            /* optionalFields => */ optionalFields                         );
        this.templateModel = templateModel;
    }

    /** Used by GridsTable. */
    public JoinedGridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String person, final java.lang.String excludedCells,
    final java.lang.String optionalFields, final long timestamp,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    final java.lang.String initialExcludedCells,
    final java.lang.String excludedRows, final java.lang.String excludedCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    final java.lang.String templateOptionalFields, final long templateTimestamp,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        super(id, person, excludedCells, rows, cols, optionalFields, timestamp);
        this.templateModel = new org.wheatgenetics.coordinate.model.TemplateModel(templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, templateOptionalFields,
            templateTimestamp);
        this.entryModels = entryModels;
    }
    // endregion

    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String name()
    {
        return java.lang.String.format("Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getPerson(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    // region Public Methods
    public java.lang.String getTemplateTitle()
    { return null == this.templateModel ? null : this.templateModel.getTitle(); }

    public int getRows() { return null == this.templateModel ? 0 : this.templateModel.getRows(); }
    public int getCols() { return null == this.templateModel ? 0 : this.templateModel.getCols(); }

    public boolean getColNumbering()
    { assert null != this.templateModel; return this.templateModel.getColNumbering(); }

    public boolean getRowNumbering()
    { assert null != this.templateModel; return this.templateModel.getRowNumbering(); }

    public void populate(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { assert null != templateModel; templateModel.assign(this.templateModel); }

    public void addExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCells().add(row, col); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedRow(final int row)
    { return null == this.templateModel ? true : this.templateModel.isExcludedRow(row); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCol(final int col)
    { return null == this.templateModel ? true : this.templateModel.isExcludedCol(col); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return null == this.excludedCellsInstance ? false :
            this.excludedCellsInstance.contains(row, col);
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
                            if (!this.isExcludedRow(row)) if (!this.isExcludedCell(row, col))
                                return new org.wheatgenetics.coordinate.model.Cell(row, col);
                    candidateCol = false;
                }
            }
            return null;
        }
    }

    public void makeEntryModels()
    {
        final int rows = this.getRows(), cols = this.getCols();
        assert null != this.templateModel;                                  // TODO: clear()s first!
        this.excludedCells().makeRandomCells(this.templateModel.getGeneratedExcludedCellsAmount(),
            /* maxRow => */ rows, /* maxCol => */ cols);

        this.entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
            /* gridId => */ this.getId(), rows, cols);
        for (int row = 1; row <= rows; row++)
        {
            final boolean excludedRow = this.isExcludedRow(row);
            for (int col = 1; col <= cols; col++)
                if (excludedRow)
                    this.entryModels.makeExcludedEntry(row, col);
                else
                    if (this.isExcludedCol(col))
                        this.entryModels.makeExcludedEntry(row, col);
                    else
                        if (this.isExcludedCell(row, col))
                            this.entryModels.makeExcludedEntry(row, col);
                        else
                            this.entryModels.makeIncludedEntry(row, col);
        }
    }

    public org.wheatgenetics.coordinate.model.EntryModel getEntryModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.entryModels ? null : this.entryModels.get(row, col); }

    public org.wheatgenetics.coordinate.model.EntryModels getEntryModels()
    { return this.entryModels; }

    public org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel)
    { return null == this.entryModels ? null : this.entryModels.next(activeEntryModel); }
    // endregion
}