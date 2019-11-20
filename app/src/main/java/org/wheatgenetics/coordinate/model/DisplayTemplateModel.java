package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.os.Bundle
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.xmlpull.v1.XmlSerializer
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class DisplayTemplateModel extends org.wheatgenetics.coordinate.model.BaseTemplateModel
{
    // region Constants
    private static final java.lang.String
        TITLE_TAG_NAME = "title", ROWS_TAG_NAME = "rows", COLS_TAG_NAME = "cols",
        GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME = "generatedExcludedCellsAmount",
        COL_NUMBERING_TAG_NAME  = "colNumbering", ROW_NUMBERING_TAG_NAME  = "rowNumbering" ,
        ENTRY_LABEL_TAG_NAME    = "entryLabel"  , EXCLUDED_CELLS_TAG_NAME = "excludedCells",
        EXCLUDED_ROWS_TAG_NAME  = "excludedRows", EXCLUDED_COLS_TAG_NAME  = "excludedCols" ;
    public static final java.lang.String
        ROWS_BUNDLE_KEY           = "rows"         , COLS_BUNDLE_KEY          = "cols"        ,
        EXCLUDED_CELLS_BUNDLE_KEY = "excludedCells",
        EXCLUDED_ROWS_BUNDLE_KEY  = "excludedRows" , EXCLUDED_COLS_BUNDLE_KEY = "excludedCols",
        COL_NUMBERING_BUNDLE_KEY  = "colNumbering" , ROW_NUMBERING_BUNDLE_KEY = "rowNumbering";
    // endregion

    // region Fields
    @android.support.annotation.Nullable private org.wheatgenetics.coordinate.model.Cells
        excludedCellsInstance = null;
    @android.support.annotation.Nullable private org.wheatgenetics.coordinate.model.RowOrCols
        excludedRowsInstance = null, excludedColsInstance = null;
    // endregion

    // region Private Methods
    /** 0 means false and 1 means true. */
    private static boolean valid(
    @android.support.annotation.IntRange(from = 0, to = 1) final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }

    private void setExcludedCells(@android.support.annotation.Nullable java.lang.String json)
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

    @android.support.annotation.Nullable
    private static org.wheatgenetics.coordinate.model.RowOrCols makeFromJSON(
    @android.support.annotation.Nullable                 java.lang.String json    ,
    @android.support.annotation.IntRange(from = 1) final int              maxValue)
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

    private void setExcludedRows(@android.support.annotation.Nullable final java.lang.String json)
    {
        this.excludedRowsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ json, /* maxValue => */ this.getRows());
    }

    private void setExcludedCols(@android.support.annotation.Nullable final java.lang.String json)
    {
        this.excludedColsInstance =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.makeFromJSON(
                /* json => */ json, /* maxValue => */ this.getCols());
    }

    private static void writeElement(
    @android.support.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                        final java.lang.String             indent       ,
    @android.support.annotation.NonNull final java.lang.String             tagName      ,
                                        final int                          text         )
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
            xmlSerializer, indent, tagName, java.lang.Integer.toString(text));
    }

    private static void writeElement(
    @android.support.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                        final java.lang.String             indent       ,
    @android.support.annotation.NonNull final java.lang.String             tagName      ,
                                        final boolean                      text         )
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
            xmlSerializer, indent, tagName, java.lang.String.valueOf(text));
    }

    @android.support.annotation.NonNull
    private org.wheatgenetics.coordinate.model.RowOrCols excludedRows()
    {
        if (null == this.excludedRowsInstance) this.excludedRowsInstance =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxValue => */ this.getRows());
        return this.excludedRowsInstance;
    }

    @android.support.annotation.NonNull
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
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells
        excludedCells,
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols
        excludedRows,
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols
        excludedCols,
    final boolean colNumbering, final boolean rowNumbering,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
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
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells
        excludedCells,
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols
        excludedRows,
    @android.support.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols
        excludedCols,
    final boolean colNumbering, final boolean rowNumbering,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount,
            colNumbering, rowNumbering, timestamp);
        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance  = excludedRows ;
        this.excludedColsInstance  = excludedCols ;
    }

    /** Called by fourth and fifth TemplateModel constructors. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel() { super(); }

    /** Called by sixth TemplateModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(
    @android.support.annotation.IntRange(from = 1        ) final long             id             ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable                   final java.lang.String excludedCells,
    @android.support.annotation.Nullable                   final java.lang.String excludedRows ,
    @android.support.annotation.Nullable                   final java.lang.String excludedCols ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int              colNumbering ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int              rowNumbering ,
                                                           final java.lang.String entryLabel   ,
    @android.support.annotation.IntRange(from = 0        ) final long             timestamp    )
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
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override java.lang.String formatString()
    {
        return java.lang.String.format(
            super.formatString() + ", excludedCells=%s, excludedRows=%s, excludedCols=%s",
            "%s", this.getExcludedCells(), this.excludedRowsInstance, this.excludedColsInstance);
    }

    @java.lang.Override @android.support.annotation.NonNull public java.lang.String toString()
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
    @java.lang.Override @android.support.annotation.NonNull protected java.lang.Object clone()
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
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.Nullable
    org.wheatgenetics.coordinate.model.Cells excludedCellsClone()
    {
        return null == this.excludedCellsInstance ? null :
            (org.wheatgenetics.coordinate.model.Cells) this.excludedCellsInstance.clone();
    }

    @android.support.annotation.Nullable org.wheatgenetics.coordinate.model.Cells getExcludedCells()
    { return this.excludedCellsInstance; }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.Nullable
    org.wheatgenetics.coordinate.model.RowOrCols excludedRowsClone()
    {
        return null == this.excludedRowsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedRowsInstance.clone();
    }

    boolean isExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedRowsInstance ? false : this.excludedRowsInstance.contains(row);
    }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.Nullable
    org.wheatgenetics.coordinate.model.RowOrCols excludedColsClone()
    {
        return null == this.excludedColsInstance ? null :
            (org.wheatgenetics.coordinate.model.RowOrCols) this.excludedColsInstance.clone();
    }

    boolean isExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedColsInstance ? false : this.excludedColsInstance.contains(col);
    }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    static void writeElement(
    @android.support.annotation.NonNull final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                        final java.lang.String             indent       ,
    @android.support.annotation.NonNull final java.lang.String             tagName      ,
                                        final java.lang.String             text         )
    throws java.io.IOException
    {
        xmlSerializer.ignorableWhitespace(indent);
        xmlSerializer.startTag           (null, tagName);           // throws java.io.IOException
        xmlSerializer.text               (text);                       // throws java.io.IOException
        xmlSerializer.endTag             (null, tagName);           // throws java.io.IOException
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean export(
    @android.support.annotation.Nullable final org.xmlpull.v1.XmlSerializer xmlSerializer,
                                         final java.lang.String             indent       )
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


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void assignCharacterData(
    @android.support.annotation.Nullable final java.lang.String elementName  ,
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
    @android.support.annotation.Nullable public java.lang.String getExcludedCellsAsJson()
    { return null == this.excludedCellsInstance ? null : this.excludedCellsInstance.json(); }


    public void addExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { this.excludedRows().add(row); }

    public void clearExcludedRows()
    { if (null != this.excludedRowsInstance) this.excludedRowsInstance.clear(); }

    @android.support.annotation.Nullable public java.lang.String getExcludedRowsAsJson()
    { return null == this.excludedRowsInstance ? null : this.excludedRowsInstance.json(); }


    public void addExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCols().add(col); }

    public void clearExcludedCols()
    { if (null != this.excludedColsInstance) this.excludedColsInstance.clear(); }

    @android.support.annotation.Nullable public java.lang.String getExcludedColsAsJson()
    { return null == this.excludedColsInstance ? null : this.excludedColsInstance.json(); }


    // region checkedItems Public Methods
    @android.support.annotation.NonNull public boolean[] rowCheckedItems()
    {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @android.support.annotation.IntRange(from = 1) final int rows = this.getRows();
            result = new boolean[rows];
            for (int i = 0; i < rows; i++) result[i] = this.isExcludedRow(i + 1);
        }
        return result;
    }

    @android.support.annotation.NonNull public boolean[] colCheckedItems()
    {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @android.support.annotation.IntRange(from = 1) final int cols = this.getCols();
            result = new boolean[cols];
            for (int i = 0; i < cols; i++) result[i] = this.isExcludedCol(i + 1);
        }
        return result;
    }
    // endregion


    @android.support.annotation.NonNull public android.os.Bundle getState()
    {
        final android.os.Bundle result = new android.os.Bundle();

        result.putInt(org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_BUNDLE_KEY,
            this.getRows());
        result.putInt(org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_BUNDLE_KEY,
            this.getCols());

        result.putString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY,
            this.getExcludedCellsAsJson()                                                    );

        result.putString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_ROWS_BUNDLE_KEY,
            this.getExcludedRowsAsJson()                                                    );
        result.putString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_COLS_BUNDLE_KEY,
            this.getExcludedColsAsJson()                                                    );

        result.putBoolean(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.COL_NUMBERING_BUNDLE_KEY,
            this.getColNumbering()                                                          );
        result.putBoolean(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROW_NUMBERING_BUNDLE_KEY,
            this.getRowNumbering()                                                          );

        return result;
    }

    public void setExcludedCells(@android.support.annotation.NonNull final android.os.Bundle bundle)
    {
        final java.lang.String EXCLUDED_CELLS_BUNDLE_KEY =
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY;
        this.setExcludedCells(bundle.containsKey(EXCLUDED_CELLS_BUNDLE_KEY) ?
            bundle.getString(EXCLUDED_CELLS_BUNDLE_KEY) : null);
    }
    // endregion
}