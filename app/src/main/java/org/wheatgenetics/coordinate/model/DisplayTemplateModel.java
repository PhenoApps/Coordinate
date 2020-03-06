package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.xmlpull.v1.XmlSerializer
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class DisplayTemplateModel extends org.wheatgenetics.coordinate.model.BaseTemplateModel
{
    private static final java.lang.String
        TITLE_TAG_NAME = "title", ROWS_TAG_NAME = "rows", COLS_TAG_NAME = "cols",
        GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME = "generatedExcludedCellsAmount",
        COL_NUMBERING_TAG_NAME  = "colNumbering", ROW_NUMBERING_TAG_NAME  = "rowNumbering" ,
        ENTRY_LABEL_TAG_NAME    = "entryLabel"  , EXCLUDED_CELLS_TAG_NAME = "excludedCells",
        EXCLUDED_ROWS_TAG_NAME  = "excludedRows", EXCLUDED_COLS_TAG_NAME  = "excludedCols" ;

    // region Fields
    @androidx.annotation.Nullable private org.wheatgenetics.coordinate.model.Cells
        excludedCellsInstance = null;
    @androidx.annotation.Nullable private org.wheatgenetics.coordinate.model.RowOrCols
        excludedRowsInstance = null, excludedColsInstance = null;
    // endregion

    // region Private Methods
    /** 0 means false and 1 means true. */
    private static boolean valid(
    @androidx.annotation.IntRange(from = 0, to = 1) final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }

    private void setExcludedCells(@androidx.annotation.Nullable java.lang.String json)
    {
        if (null == json)
            this.excludedCellsInstance = null;
        else
        {
            json = json.trim();
            this.excludedCellsInstance = json.length() <= 0 ? null :
                new org.wheatgenetics.coordinate.model.Cells(
                    /* json   => */ json          ,
                    /* maxRow => */ this.getRows(),
                    /* maxCol => */ this.getCols());
        }
    }

    // region setExcluded[Row|Col]s() Private Methods
    @androidx.annotation.Nullable
    private static org.wheatgenetics.coordinate.model.RowOrCols makeFromJSON(
    @androidx.annotation.Nullable                 java.lang.String json    ,
    @androidx.annotation.IntRange(from = 1) final int              maxValue)
    {
        if (null == json)
            return null;
        else
        {
            json = json.trim();
            return json.length() <= 0 ? null : new org.wheatgenetics.coordinate.model.RowOrCols(
                /* json => */ json, /* maxValue => */ maxValue);
        }
    }

    private void setExcludedRows(@androidx.annotation.Nullable final java.lang.String json)
    {
        this.excludedRowsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ json, /* maxValue => */ this.getRows());
    }

    private void setExcludedCols(@androidx.annotation.Nullable final java.lang.String json)
    {
        this.excludedColsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ json, /* maxValue => */ this.getCols());
    }
    // endregion

    // region writeElement() Private Methods
    private static void writeElement(
    @androidx.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                 final java.lang.String             indent       ,
    @androidx.annotation.NonNull final java.lang.String             tagName      ,
                                 final int                          text         )
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
            xmlSerializer, indent, tagName, java.lang.Integer.toString(text));
    }

    private static void writeElement(
    @androidx.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                 final java.lang.String             indent       ,
    @androidx.annotation.NonNull final java.lang.String             tagName      ,
                                 final boolean                      text         )
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
            xmlSerializer, indent, tagName, java.lang.String.valueOf(text));
    }
    // endregion

    @androidx.annotation.NonNull private org.wheatgenetics.coordinate.model.RowOrCols excludedRows()
    {
        if (null == this.excludedRowsInstance) this.excludedRowsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getRows());
        return this.excludedRowsInstance;
    }

    @androidx.annotation.NonNull private org.wheatgenetics.coordinate.model.RowOrCols excludedCols()
    {
        if (null == this.excludedColsInstance) this.excludedColsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getCols());
        return this.excludedColsInstance;
    }
    // endregion

    // region Constructors
    /** Called by clone() and first TemplateModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(@androidx.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance  = excludedRows ;
        this.excludedColsInstance  = excludedCols ;
    }

    /** Called by clone() and second TemplateModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance  = excludedRows ;
        this.excludedColsInstance  = excludedCols ;
    }

    /** Called by fourth and fifth TemplateModel constructors. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel() { super(); }

    /** Called by sixth TemplateModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(
    @androidx.annotation.IntRange(from = 1        ) final long             id             ,
                                                    final java.lang.String title          ,
    @androidx.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @androidx.annotation.IntRange(from = 1        ) final int              rows           ,
    @androidx.annotation.IntRange(from = 1        ) final int              cols           ,
    @androidx.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable                   final java.lang.String excludedCells,
    @androidx.annotation.Nullable                   final java.lang.String excludedRows ,
    @androidx.annotation.Nullable                   final java.lang.String excludedCols ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              colNumbering ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int              rowNumbering ,
                                                    final java.lang.String entryLabel   ,
    @androidx.annotation.IntRange(from = 0        ) final long             timestamp    )
    {
        super(id, title, org.wheatgenetics.coordinate.model.TemplateType.get(code), rows, cols,
            generatedExcludedCellsAmount,
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.valid(colNumbering),
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.valid(rowNumbering), timestamp);

        this.setExcludedCells(excludedCells); this.setExcludedRows(excludedRows);
        this.setExcludedCols (excludedCols ); this.setEntryLabel  (entryLabel  );
    }
    // endregion

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override java.lang.String formatString()
    {
        return java.lang.String.format(
            super.formatString() + ", excludedCells=%s, excludedRows=%s, excludedCols=%s",
            "%s", this.getExcludedCells(), this.excludedRowsInstance, this.excludedColsInstance);
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    { return java.lang.String.format(this.formatString(), "DisplayTemplateModel"); }

    @java.lang.Override public boolean equals(final java.lang.Object object)
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

                // noinspection SimplifiableConditionalExpression
                return null == this.excludedColsInstance ? true :
                    this.excludedColsInstance.equals(displayTemplateModel.excludedColsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @java.lang.Override @androidx.annotation.NonNull protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.DisplayTemplateModel result;
        {
            final long                                     id            = this.getId();
            final org.wheatgenetics.coordinate.model.Cells excludedCells =
                this.excludedCellsClone();
            final org.wheatgenetics.coordinate.model.RowOrCols
                excludedRows = this.excludedRowsClone(), excludedCols = this.excludedColsClone();

            if (org.wheatgenetics.coordinate.model.Model.illegal(id))
                result = new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                    /* title                        => */ this.getTitle                       (),
                    /* type                         => */ this.getType                        (),
                    /* rows                         => */ this.getRows                        (),
                    /* cols                         => */ this.getCols                        (),
                    /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                    /* excludedCells                => */ excludedCells                         ,
                    /* excludedRows                 => */ excludedRows                          ,
                    /* excludedCols                 => */ excludedCols                          ,
                    /* colNumbering                 => */ this.getColNumbering()                ,
                    /* rowNumbering                 => */ this.getRowNumbering()                ,
                    /* timestamp                    => */ this.getTimestamp   ()                );
            else
                result = new org.wheatgenetics.coordinate.model.DisplayTemplateModel(
                    /* id                           => */ id                                    ,
                    /* title                        => */ this.getTitle                       (),
                    /* type                         => */ this.getType                        (),
                    /* rows                         => */ this.getRows                        (),
                    /* cols                         => */ this.getCols                        (),
                    /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                    /* excludedCells                => */ excludedCells                         ,
                    /* excludedRows                 => */ excludedRows                          ,
                    /* excludedCols                 => */ excludedCols                          ,
                    /* colNumbering                 => */ this.getColNumbering()                ,
                    /* rowNumbering                 => */ this.getRowNumbering()                ,
                    /* timestamp                    => */ this.getTimestamp   ()                );
        }
        if (this.entryLabelIsNotNull()) result.setEntryLabel(this.getEntryLabel());
        return result;
    }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.Cells excludedCellsClone()
    {
        return null == this.excludedCellsInstance ? null :
            (org.wheatgenetics.coordinate.model.Cells) this.excludedCellsInstance.clone();
    }

    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.Cells getExcludedCells()
    { return this.excludedCellsInstance; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.RowOrCols excludedRowsClone()
    {
        return null == this.excludedRowsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedRowsInstance.clone();
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.RowOrCols excludedColsClone()
    {
        return null == this.excludedColsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedColsInstance.clone();
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    static void writeElement(
    @androidx.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                 final java.lang.String             indent       ,
    @androidx.annotation.NonNull final java.lang.String             tagName      ,
                                 final java.lang.String             text         )
    throws java.io.IOException
    {
        xmlSerializer.ignorableWhitespace(indent);
        xmlSerializer.startTag           (null, tagName);           // throws java.io.IOException
        xmlSerializer.text               (text);                       // throws java.io.IOException
        xmlSerializer.endTag             (null, tagName);           // throws java.io.IOException
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean export(
    @androidx.annotation.Nullable                  final org.xmlpull.v1.XmlSerializer xmlSerializer,
    @java.lang.SuppressWarnings({"SameParameterValue"}) final java.lang.String        indent       )
    {
        boolean success;

        if (null == xmlSerializer)
            success = false;
        else
            try
            {
                {
                    final java.lang.String title = this.getTitle();
                    if (null != title) if (title.length() > 0) org.wheatgenetics.coordinate.model
                        .DisplayTemplateModel.writeElement(xmlSerializer, indent,          // throws
                            org.wheatgenetics.coordinate.model.DisplayTemplateModel.TITLE_TAG_NAME,
                            title                                                                 );
                }

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(      // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_TAG_NAME,
                    this.getRows()                                                       );
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(      // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_TAG_NAME,
                    this.getCols()                                                       );

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(      // throws
                    xmlSerializer, indent, org.wheatgenetics.coordinate.model
                        .DisplayTemplateModel.GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME,
                    this.getGeneratedExcludedCellsAmount()                            );

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(      // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COL_NUMBERING_TAG_NAME,
                    this.getColNumbering()                                                        );
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(      // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROW_NUMBERING_TAG_NAME,
                    this.getRowNumbering()                                                        );

                if (this.entryLabelIsNotNull())
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(  // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.ENTRY_LABEL_TAG_NAME,
                        this.getEntryLabel()                          );

                if (null != this.excludedCellsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(  // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_CELLS_TAG_NAME,
                        this.excludedCellsInstance.json()                );

                if (null != this.excludedRowsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(  // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_ROWS_TAG_NAME,
                        this.excludedRowsInstance.json()                );

                if (null != this.excludedColsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(  // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_COLS_TAG_NAME,
                        this.excludedColsInstance.json()                );

                success = true;
            }
            catch (final java.io.IOException e) { success = false; }

        return success;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void assignCharacterData(
    @androidx.annotation.Nullable final java.lang.String elementName  ,
                                  final java.lang.String characterData)
    {
        if (null != elementName) switch (elementName)
        {
            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.TITLE_TAG_NAME:
                this.setTitle(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_TAG_NAME:
                this.setRows(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_TAG_NAME:
                this.setCols(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel
                .GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME:
                    this.setGeneratedExcludedCellsAmount(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.COL_NUMBERING_TAG_NAME:
                this.setColNumbering(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROW_NUMBERING_TAG_NAME:
                this.setRowNumbering(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.ENTRY_LABEL_TAG_NAME:
                this.setEntryLabel(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_TAG_NAME:
                this.setExcludedCells(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_ROWS_TAG_NAME:
                this.setExcludedRows(characterData); break;

            case org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_COLS_TAG_NAME:
                this.setExcludedCols(characterData); break;
        }
    }
    // endregion

    // region Public Methods
    // region excludedCells Public Methods
    @androidx.annotation.Nullable public java.lang.String getExcludedCellsAsJson()
    { return null == this.excludedCellsInstance ? null : this.excludedCellsInstance.json(); }

    public boolean isExcludedCell(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell cell)
    {
        final org.wheatgenetics.coordinate.model.Cells excludedCells = this.getExcludedCells();
        // noinspection SimplifiableConditionalExpression
        return null == excludedCells ? false : excludedCells.contains(cell);
    }

    public void toggle(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.Cell cell)
    {
        if (null != cell)
            if (this.isExcludedCell(cell))
                // noinspection ConstantConditions
                this.excludedCellsInstance.remove(cell);
            else
            {
                if (null == this.excludedCellsInstance)
                    this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                        /* maxRow => */ this.getRows(),
                        /* maxCol => */ this.getCols());
                this.excludedCellsInstance.add(cell);
            }
    }
    // endregion

    // region excludedRows Public Methods
    public void addExcludedRow(@androidx.annotation.IntRange(from = 1) final int row)
    { this.excludedRows().add(row); }

    public void clearExcludedRows()
    { if (null != this.excludedRowsInstance) this.excludedRowsInstance.clear(); }

    @androidx.annotation.Nullable public java.lang.String getExcludedRowsAsJson()
    { return null == this.excludedRowsInstance ? null : this.excludedRowsInstance.json(); }

    public boolean isExcludedRow(@androidx.annotation.IntRange(from = 1) final int row)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedRowsInstance ? false : this.excludedRowsInstance.contains(row);
    }
    // endregion

    // region excludedCols Public Methods
    public void addExcludedCol(@androidx.annotation.IntRange(from = 1) final int col)
    { this.excludedCols().add(col); }

    public void clearExcludedCols()
    { if (null != this.excludedColsInstance) this.excludedColsInstance.clear(); }

    @androidx.annotation.Nullable public java.lang.String getExcludedColsAsJson()
    { return null == this.excludedColsInstance ? null : this.excludedColsInstance.json(); }

    public boolean isExcludedCol(@androidx.annotation.IntRange(from = 1) final int col)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedColsInstance ? false : this.excludedColsInstance.contains(col);
    }
    // endregion

    // region checkedItems Public Methods
    @androidx.annotation.NonNull public boolean[] rowCheckedItems()
    {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @androidx.annotation.IntRange(from = 1) final int rows = this.getRows();
            result = new boolean[rows];
            for (int i = 0; i < rows; i++) result[i] = this.isExcludedRow(i + 1);
        }
        return result;
    }

    @androidx.annotation.NonNull public boolean[] colCheckedItems()
    {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @androidx.annotation.IntRange(from = 1) final int cols = this.getCols();
            result = new boolean[cols];
            for (int i = 0; i < cols; i++) result[i] = this.isExcludedCol(i + 1);
        }
        return result;
    }
    // endregion
    // endregion
}