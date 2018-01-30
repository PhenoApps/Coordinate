package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.os.Bundle
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.RestrictTo.Scope.SUBCLASSES
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class DisplayTemplateModel extends org.wheatgenetics.coordinate.model.BaseTemplateModel
implements org.wheatgenetics.coordinate.model.DisplayModel
{
    private static final java.lang.String EXCLUDED_CELLS_BUNDLE_KEY = "excludedCells";

    // region Fields
    private org.wheatgenetics.coordinate.model.Cells     excludedCellsInstance = null;
    private org.wheatgenetics.coordinate.model.RowOrCols excludedRowsInstance  = null,
        excludedColsInstance = null;
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

    private void setExcludedCells(java.lang.String json)
    {
        if (null == json)
            this.excludedCellsInstance = null;
        else
        {
            json = json.trim();
            if (json.length() <= 0)
                this.excludedCellsInstance = null;
            else
                this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                    /* json   => */ json          ,
                    /* maxRow => */ this.getRows(),
                    /* maxCol => */ this.getCols());
        }
    }

    private static org.wheatgenetics.coordinate.model.RowOrCols makeFromJSON(java.lang.String json,
    final int maxValue)
    {
        if (null == json)
            return null;
        else
        {
            json = json.trim();
            if (json.length() <= 0)
                return null;
            else
                return new org.wheatgenetics.coordinate.model.RowOrCols(
                    /* json => */ json, /* maxValue => */ maxValue);
        }
    }

    private org.wheatgenetics.coordinate.model.Cells excludedCells()
    {
        if (null == this.excludedCellsInstance)
            this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
        return this.excludedCellsInstance;
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
    // endregion

    // region Constructors
    /** Called by clone() and first TemplateModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering, final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance  = excludedRows ;
        this.excludedColsInstance  = excludedCols ;
    }

    /** Called by clone() and second TemplateModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering, final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance  = excludedRows ;
        this.excludedColsInstance  = excludedCols ;
    }

    /** Called by fourth TemplateModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(
    @android.support.annotation.IntRange(from = 1        ) final long             id             ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    final java.lang.String excludedCells,
    final java.lang.String excludedRows, final java.lang.String excludedCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int  colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int  rowNumbering,
                                                           final long timestamp   )
    {
        super(id, title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            generatedExcludedCellsAmount,
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.valid(rowNumbering), timestamp);

        this.setExcludedCells(excludedCells);

        this.excludedRowsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ excludedRows, /* maxValue => */ this.getRows());
        this.excludedColsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ excludedCols, /* maxValue => */ this.getCols());
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.String formatString()
    {
        return java.lang.String.format(
            super.formatString() + ", excludedCells=%s, excludedRows=%s, excludedCols=%s",
            "%s", this.excludedCellsInstance, this.excludedRowsInstance, this.excludedColsInstance);
    }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final java.lang.Object object)
    {
        if (super.equals(object))
            if (object instanceof org.wheatgenetics.coordinate.model.DisplayTemplateModel)
            {
                final org.wheatgenetics.coordinate.model.DisplayTemplateModel displayTemplateModel =
                    (org.wheatgenetics.coordinate.model.DisplayTemplateModel) object;

                if (null == this.excludedCellsInstance
                &&  null != displayTemplateModel.excludedCellsInstance)
                    return false;
                else
                    if (null != this.excludedCellsInstance
                    &&  null == displayTemplateModel.excludedCellsInstance)
                        return false;
                if (null != this.excludedCellsInstance)
                    if (!this.excludedCellsInstance.equals(
                    displayTemplateModel.excludedCellsInstance))
                        return false;

                if (null == this.excludedRowsInstance
                &&  null != displayTemplateModel.excludedRowsInstance)
                    return false;
                else
                    if (null != this.excludedRowsInstance
                    &&  null == displayTemplateModel.excludedRowsInstance)
                        return false;
                if (null != this.excludedRowsInstance)
                    if (!this.excludedRowsInstance.equals(
                    displayTemplateModel.excludedRowsInstance))
                        return false;

                if (null == this.excludedColsInstance
                &&  null != displayTemplateModel.excludedColsInstance)
                    return false;
                else
                    if (null != this.excludedColsInstance
                    &&  null == displayTemplateModel.excludedColsInstance)
                        return false;
                if (null == this.excludedColsInstance)
                    return true;
                else
                    return
                        this.excludedColsInstance.equals(displayTemplateModel.excludedColsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    {
        final long                                     id            = this.getId             ();
        final org.wheatgenetics.coordinate.model.Cells excludedCells = this.excludedCellsClone();
        final org.wheatgenetics.coordinate.model.RowOrCols
            excludedRows = this.excludedRowsClone(),
            excludedCols = this.excludedColsClone();

        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            return new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* timestamp                    => */ this.getTimestamp()                   );
        else
            return new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                /* id                           => */ id                                    ,
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* timestamp                    => */ this.getTimestamp()                   );
    }

    // region org.wheatgenetics.coordinate.model.DisplayModel Overridden Method
    @java.lang.Override
    public org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @android.support.annotation.IntRange(from = 1) int row,
    @android.support.annotation.IntRange(from = 1) int col)
    { return new org.wheatgenetics.coordinate.model.Cell(/* row => */ row, /* col => */ col); }
    // endregion
    // endregion

    // region Package Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.Cells excludedCellsClone()
    {
        return null == this.excludedCellsInstance ? null :
            (org.wheatgenetics.coordinate.model.Cells) this.excludedCellsInstance.clone();
    }

    org.wheatgenetics.coordinate.model.Cells getExcludedCells()
    { return this.excludedCellsInstance; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.RowOrCols excludedRowsClone()
    {
        return null == this.excludedRowsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedRowsInstance.clone();
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    boolean isExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { return null == this.excludedRowsInstance ? false : this.excludedRowsInstance.contains(row); }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    org.wheatgenetics.coordinate.model.RowOrCols excludedColsClone()
    {
        return null == this.excludedColsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedColsInstance.clone();
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    boolean isExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.excludedColsInstance ? false : this.excludedColsInstance.contains(col); }
    // endregion

    // region Public Methods
    public void addExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCells().add(row, col); }

    public java.lang.String getExcludedCellsAsJson()
    { return null == this.excludedCellsInstance ? null : this.excludedCellsInstance.json(); }


    public void addExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { this.excludedRows().add(row); }

    public java.lang.String getExcludedRowsAsJson()
    { return null == this.excludedRowsInstance ? null : this.excludedRowsInstance.json(); }


    public void addExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCols().add(col); }

    public java.lang.String getExcludedColsAsJson()
    { return null == this.excludedColsInstance ? null : this.excludedColsInstance.json(); }


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


    public android.os.Bundle getState()
    {
        final android.os.Bundle result = new android.os.Bundle();

        result.putInt("rows", this.getRows()); result.putInt("cols", this.getCols());
        result.putString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY,
            this.getExcludedCellsAsJson()                                                    );
        result.putString ("excludedRows", this.getExcludedRowsAsJson());
        result.putString ("excludedCols", this.getExcludedColsAsJson());
        result.putBoolean("colNumbering", this.getColNumbering      ());
        result.putBoolean("rowNumbering", this.getRowNumbering      ());

        return result;
    }

    public void putState(final android.os.Bundle bundle)
    {
        assert null != bundle;
        this.setExcludedCells(bundle.getString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY));
    }
    // endregion
}