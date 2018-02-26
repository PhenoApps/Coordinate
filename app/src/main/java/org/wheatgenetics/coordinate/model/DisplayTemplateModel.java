package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.os.Bundle
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.RestrictTo.Scope.SUBCLASSES
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
    public static final java.lang.String
        ROWS_BUNDLE_KEY           = "rows"         , COLS_BUNDLE_KEY          = "cols"        ,
        EXCLUDED_CELLS_BUNDLE_KEY = "excludedCells",
        EXCLUDED_ROWS_BUNDLE_KEY  = "excludedRows" , EXCLUDED_COLS_BUNDLE_KEY = "excludedCols",
        COL_NUMBERING_BUNDLE_KEY  = "colNumbering" , ROW_NUMBERING_BUNDLE_KEY = "rowNumbering";
    private static final java.lang.String ID_TAG_NAME = "id", TITLE_TAG_NAME = "title",
        ROWS_TAG_NAME = "rows", COLS_TAG_NAME = "cols",
        GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME = "generatedExcludedCellsAmount",
        COL_NUMBERING_TAG_NAME = "colNumbering", ROW_NUMBERING_TAG_NAME  = "rowNumbering" ,
        TIMESTAMP_TAG_NAME     = "timestamp"   , EXCLUDED_CELLS_TAG_NAME = "excludedCells",
        EXCLUDED_ROWS_TAG_NAME = "excludedRows", EXCLUDED_COLS_TAG_NAME  = "excludedCols" ;
    // endregion

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

    private static void writeTag(final org.xmlpull.v1.XmlSerializer xmlSerializer,
    final java.lang.String indent, final java.lang.String tagName, final long text)
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(
            xmlSerializer, indent, tagName, java.lang.Long.toString(text));
    }

    private static void writeTag(final org.xmlpull.v1.XmlSerializer xmlSerializer,
    final java.lang.String indent, final java.lang.String tagName, final int text)
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(
            xmlSerializer, indent, tagName, java.lang.Integer.toString(text));
    }

    private static void writeTag(final org.xmlpull.v1.XmlSerializer xmlSerializer,
    final java.lang.String indent, final java.lang.String tagName, final boolean text)
    throws java.io.IOException
    {
        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(
            xmlSerializer, indent, tagName, java.lang.String.valueOf(text));
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
    DisplayTemplateModel() { super(); }

    /** Called by fifth TemplateModel constructor. */
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
            "%s", this.getExcludedCells(), this.excludedRowsInstance, this.excludedColsInstance);
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


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    static void writeTag(final org.xmlpull.v1.XmlSerializer xmlSerializer,
    final java.lang.String indent, final java.lang.String tagName, final java.lang.String text)
    throws java.io.IOException
    {
        assert null != xmlSerializer; xmlSerializer.ignorableWhitespace(indent);
        xmlSerializer.startTag(null, tagName);                         // throws java.io.IOException
        xmlSerializer.text    (text         );                         // throws java.io.IOException
        xmlSerializer.endTag  (null, tagName);                         // throws java.io.IOException
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.SuppressWarnings("PointlessBooleanExpression")
    boolean export(final org.xmlpull.v1.XmlSerializer xmlSerializer, final java.lang.String indent)
    {
        final boolean success = true;
        if (null == xmlSerializer)
            return !success;
        else
        {
            try
            {
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ID_TAG_NAME,
                    this.getId());

                {
                    final java.lang.String title = this.getTitle();
                    if (null != title) if (title.length() > 0)
                        org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(  // throws
                            xmlSerializer, indent,
                            org.wheatgenetics.coordinate.model.DisplayTemplateModel.TITLE_TAG_NAME,
                            title);
                }

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_TAG_NAME,
                    this.getRows());
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_TAG_NAME,
                    this.getCols());

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent, org.wheatgenetics.coordinate.model
                        .DisplayTemplateModel.GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME,
                    this.getGeneratedExcludedCellsAmount());

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COL_NUMBERING_TAG_NAME,
                    this.getColNumbering());
                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROW_NUMBERING_TAG_NAME,
                    this.getRowNumbering());

                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(          // throws
                    xmlSerializer, indent,
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.TIMESTAMP_TAG_NAME,
                    this.getTimestamp());

                if (null != this.excludedCellsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(      // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_CELLS_TAG_NAME,
                        this.excludedCellsInstance.json());

                if (null != this.excludedRowsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(      // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_ROWS_TAG_NAME,
                        this.excludedRowsInstance.json());

                if (null != this.excludedColsInstance)
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(      // throws
                        xmlSerializer, indent, org.wheatgenetics.coordinate.model
                            .DisplayTemplateModel.EXCLUDED_COLS_TAG_NAME,
                        this.excludedColsInstance.json());
            }
            catch (final java.io.IOException e) { return !success; }
            return success;
        }
    }


    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void assignCharacterData(final java.lang.String elementName,
    final java.lang.String characterData)
    {
        if (org.wheatgenetics.coordinate.model.DisplayTemplateModel.ID_TAG_NAME.equals(
        elementName))
            this.setId(characterData);
        else
            if (org.wheatgenetics.coordinate.model.DisplayTemplateModel.TITLE_TAG_NAME.equals(
            elementName))
                this.setTitle(characterData);
            else
                if (org.wheatgenetics.coordinate.model.DisplayTemplateModel.ROWS_TAG_NAME.equals(
                elementName))
                    this.setRows(characterData);
                else
                    if (
                    org.wheatgenetics.coordinate.model.DisplayTemplateModel.COLS_TAG_NAME.equals(
                    elementName))
                        this.setCols(characterData);
                    else
                        if (org.wheatgenetics.coordinate.model.DisplayTemplateModel
                        .GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME.equals(elementName))
                            this.setGeneratedExcludedCellsAmount(characterData);
                        else
                            if (org.wheatgenetics.coordinate.model.DisplayTemplateModel
                            .COL_NUMBERING_TAG_NAME.equals(elementName))
                                this.setColNumbering(characterData);
                            else
                                if (org.wheatgenetics.coordinate.model.DisplayTemplateModel
                                .ROW_NUMBERING_TAG_NAME.equals(elementName))
                                    this.setRowNumbering(characterData);
                                else
                                    if (org.wheatgenetics.coordinate.model.DisplayTemplateModel
                                    .TIMESTAMP_TAG_NAME.equals(elementName))
                                        this.setTimestamp(characterData);
    }
    // endregion

    // region Public Methods
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

    public void setExcludedCells(final android.os.Bundle bundle)
    {
        assert null != bundle;
        this.setExcludedCells(bundle.getString(
            org.wheatgenetics.coordinate.model.DisplayTemplateModel.EXCLUDED_CELLS_BUNDLE_KEY));
    }
    // endregion
}