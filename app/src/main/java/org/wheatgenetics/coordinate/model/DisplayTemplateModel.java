package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class DisplayTemplateModel extends BaseTemplateModel {
    private static final String
            TITLE_TAG_NAME = "title", ROWS_TAG_NAME = "rows", COLS_TAG_NAME = "cols",
            GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME = "generatedExcludedCellsAmount",
            COL_NUMBERING_TAG_NAME = "colNumbering", ROW_NUMBERING_TAG_NAME = "rowNumbering",
            ENTRY_LABEL_TAG_NAME = "entryLabel", EXCLUDED_CELLS_TAG_NAME = "excludedCells",
            EXCLUDED_ROWS_TAG_NAME = "excludedRows", EXCLUDED_COLS_TAG_NAME = "excludedCols";

    // region Fields
    @Nullable
    private Cells
            excludedCellsInstance = null;
    @Nullable
    private RowOrCols
            excludedRowsInstance = null, excludedColsInstance = null;
    // endregion

    // region Private Methods

    /**
     * Called by clone() and first TemplateModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(@IntRange(from = 1) final long id,
                         final String title, final TemplateType type,
                         @IntRange(from = 1) final int rows,
                         @IntRange(from = 1) final int cols,
                         @IntRange(from = 0) final int generatedExcludedCellsAmount,
                         @Nullable final Cells excludedCells,
                         @Nullable final RowOrCols excludedRows,
                         @Nullable final RowOrCols excludedCols,
                         final boolean colNumbering, final boolean rowNumbering,
                         @IntRange(from = 0) final long timestamp,
                         @NonNull final StringGetter stringGetter) {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp, stringGetter);

        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance = excludedRows;
        this.excludedColsInstance = excludedCols;
    }

    /**
     * Called by clone() and second TemplateModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(final String title,
                         final TemplateType type,
                         @IntRange(from = 1) final int rows,
                         @IntRange(from = 1) final int cols,
                         @IntRange(from = 0) final int generatedExcludedCellsAmount,
                         @Nullable final Cells excludedCells,
                         @Nullable final RowOrCols excludedRows,
                         @Nullable final RowOrCols excludedCols,
                         final boolean colNumbering, final boolean rowNumbering,
                         @IntRange(from = 0) final long timestamp,
                         @NonNull final StringGetter stringGetter) {
        super(title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp, stringGetter);

        this.excludedCellsInstance = excludedCells;
        this.excludedRowsInstance = excludedRows;
        this.excludedColsInstance = excludedCols;
    }

    /**
     * Called by fourth and fifth TemplateModel constructors.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(@NonNull final StringGetter stringGetter) {
        super(stringGetter);
    }

    /**
     * Called by sixth TemplateModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    DisplayTemplateModel(
            @IntRange(from = 1) final long id,
            final String title,
            @IntRange(from = 0, to = 2) final int code,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @IntRange(from = 0) final int generatedExcludedCellsAmount,
            @Nullable final String excludedCells,
            @Nullable final String excludedRows,
            @Nullable final String excludedCols,
            @IntRange(from = 0, to = 1) final int colNumbering,
            @IntRange(from = 0, to = 1) final int rowNumbering,
            final String entryLabel,
            @IntRange(from = 0) final long timestamp,
            @NonNull final StringGetter stringGetter) {
        super(id, title, TemplateType.get(code),
                rows, cols, generatedExcludedCellsAmount,
                DisplayTemplateModel.valid(colNumbering),
                DisplayTemplateModel.valid(rowNumbering),
                timestamp, stringGetter);

        this.setExcludedCells(excludedCells);
        this.setExcludedRows(excludedRows);
        this.setExcludedCols(excludedCols);
        this.setEntryLabel(entryLabel);
    }

    /**
     * 0 means false and 1 means true.
     */
    private static boolean valid(
            @IntRange(from = 0, to = 1) final int numbering) {
        if (numbering < 0 || numbering > 1)
            throw new IllegalArgumentException();
        else
            return 1 == numbering;
    }
    // endregion

    // region setExcluded[Row|Col]s() Private Methods
    @Nullable
    private static RowOrCols makeFromJSON(
            @Nullable String json,
            @IntRange(from = 1) final int maxValue,
            @NonNull final StringGetter stringGetter) {
        if (null == json)
            return null;
        else {
            json = json.trim();
            return json.length() <= 0 ? null : new RowOrCols(
                    /* json => */ json, /* maxValue => */ maxValue, stringGetter);
        }
    }

    // region writeElement() Private Methods
    private static void writeElement(
            @NonNull final XmlSerializer xmlSerializer,
            final String indent,
            @NonNull final String tagName,
            final int text)
            throws IOException {
        DisplayTemplateModel.writeElement(
                xmlSerializer, indent, tagName, Integer.toString(text));
    }
    // endregion

    private static void writeElement(
            @NonNull final XmlSerializer xmlSerializer,
            final String indent,
            @NonNull final String tagName,
            final boolean text)
            throws IOException {
        DisplayTemplateModel.writeElement(
                xmlSerializer, indent, tagName, String.valueOf(text));
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    static void writeElement(
            @NonNull final XmlSerializer xmlSerializer,
            final String indent,
            @NonNull final String tagName,
            final String text)
            throws IOException {
        if (null != text) if (text.length() > 0) {
            xmlSerializer.ignorableWhitespace(indent);
            xmlSerializer.startTag(null, tagName);       // throws java.io.IOException
            xmlSerializer.text(text);                   // throws java.io.IOException
            xmlSerializer.endTag(null, tagName);       // throws java.io.IOException
        }
    }
    // endregion

    // region Constructors

    private void setExcludedRows(@Nullable final String json) {
        this.excludedRowsInstance =
                DisplayTemplateModel.makeFromJSON(
                        /* json => */ json, /* maxValue => */ this.getRows(), this.stringGetter());
    }

    private void setExcludedCols(@Nullable final String json) {
        this.excludedColsInstance =
                DisplayTemplateModel.makeFromJSON(
                        /* json => */ json, /* maxValue => */ this.getCols(), this.stringGetter());
    }

    @NonNull
    private RowOrCols excludedRows() {
        if (null == this.excludedRowsInstance)
            this.excludedRowsInstance = new RowOrCols(
                    /* maxValue => */ this.getRows(), this.stringGetter());
        return this.excludedRowsInstance;
    }

    @NonNull
    private RowOrCols excludedCols() {
        if (null == this.excludedColsInstance)
            this.excludedColsInstance = new RowOrCols(
                    /* maxValue => */ this.getCols(), this.stringGetter());
        return this.excludedColsInstance;
    }
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    String formatString() {
        return String.format(
                super.formatString() + ", excludedCells=%s, excludedRows=%s, excludedCols=%s",
                "%s", this.getExcludedCells(), this.excludedRowsInstance, this.excludedColsInstance);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format(this.formatString(), "DisplayTemplateModel");
    }

    @Override
    public boolean equals(final Object object) {
        if (super.equals(object))
            if (object instanceof DisplayTemplateModel) {
                final DisplayTemplateModel displayTemplateModel =
                        (DisplayTemplateModel) object;

                if (null == this.excludedCellsInstance
                        && null != displayTemplateModel.excludedCellsInstance)
                    return false;
                else if (null != this.excludedCellsInstance
                        && null == displayTemplateModel.excludedCellsInstance)
                    return false;
                if (null != this.excludedCellsInstance)
                    if (!this.excludedCellsInstance.equals(
                            displayTemplateModel.excludedCellsInstance))
                        return false;

                if (null == this.excludedRowsInstance
                        && null != displayTemplateModel.excludedRowsInstance)
                    return false;
                else if (null != this.excludedRowsInstance
                        && null == displayTemplateModel.excludedRowsInstance)
                    return false;
                if (null != this.excludedRowsInstance)
                    if (!this.excludedRowsInstance.equals(
                            displayTemplateModel.excludedRowsInstance))
                        return false;

                if (null == this.excludedColsInstance
                        && null != displayTemplateModel.excludedColsInstance)
                    return false;
                else if (null != this.excludedColsInstance
                        && null == displayTemplateModel.excludedColsInstance)
                    return false;

                // noinspection SimplifiableConditionalExpression
                return null == this.excludedColsInstance ? true :
                        this.excludedColsInstance.equals(displayTemplateModel.excludedColsInstance);
            } else return false;
        else return false;
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    @NonNull
    protected Object clone() {
        final DisplayTemplateModel result;
        {
            final long id = this.getId();
            final Cells excludedCells =
                    this.excludedCellsClone();
            final RowOrCols
                    excludedRows = this.excludedRowsClone(), excludedCols = this.excludedColsClone();

            if (Model.illegal(id))
                result = new DisplayTemplateModel(
                        /* title                        => */ this.getTitle(),
                        /* type                         => */ this.getType(),
                        /* rows                         => */ this.getRows(),
                        /* cols                         => */ this.getCols(),
                        /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                        /* excludedCells                => */ excludedCells,
                        /* excludedRows                 => */ excludedRows,
                        /* excludedCols                 => */ excludedCols,
                        /* colNumbering                 => */ this.getColNumbering(),
                        /* rowNumbering                 => */ this.getRowNumbering(),
                        /* timestamp                    => */ this.getTimestamp(),
                        /* stringGetter                 => */ this.stringGetter());
            else
                result = new DisplayTemplateModel(
                        /* id                           => */ id,
                        /* title                        => */ this.getTitle(),
                        /* type                         => */ this.getType(),
                        /* rows                         => */ this.getRows(),
                        /* cols                         => */ this.getCols(),
                        /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                        /* excludedCells                => */ excludedCells,
                        /* excludedRows                 => */ excludedRows,
                        /* excludedCols                 => */ excludedCols,
                        /* colNumbering                 => */ this.getColNumbering(),
                        /* rowNumbering                 => */ this.getRowNumbering(),
                        /* timestamp                    => */ this.getTimestamp(),
                        /* stringGetter                 => */ this.stringGetter());
        }
        if (this.entryLabelIsNotNull()) result.setEntryLabel(this.getEntryLabel());
        return result;
    }
    // endregion

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    Cells excludedCellsClone() {
        return null == this.excludedCellsInstance ? null :
                (Cells) this.excludedCellsInstance.clone();
    }

    @Nullable
    Cells getExcludedCells() {
        return this.excludedCellsInstance;
    }

    private void setExcludedCells(@Nullable String json) {
        if (null == json)
            this.excludedCellsInstance = null;
        else {
            json = json.trim();
            this.excludedCellsInstance = json.length() <= 0 ? null :
                    new Cells(
                            /* json         => */ json,
                            /* maxRow       => */ this.getRows(),
                            /* maxCol       => */ this.getCols(),
                            /* stringGetter => */ this.stringGetter());
        }
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    RowOrCols excludedRowsClone() {
        return null == this.excludedRowsInstance ? null :
                (RowOrCols) this.excludedRowsInstance.clone();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    RowOrCols excludedColsClone() {
        return null == this.excludedColsInstance ? null :
                (RowOrCols) this.excludedColsInstance.clone();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean export(
            @Nullable final XmlSerializer xmlSerializer,
            @SuppressWarnings({"SameParameterValue"}) final String indent) {
        boolean success;

        if (null == xmlSerializer)
            success = false;
        else
            try {
                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent,
                        DisplayTemplateModel.TITLE_TAG_NAME,
                        this.getTitle());

                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent,
                        DisplayTemplateModel.ROWS_TAG_NAME,
                        this.getRows());
                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent,
                        DisplayTemplateModel.COLS_TAG_NAME,
                        this.getCols());

                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent, DisplayTemplateModel.GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME,
                        this.getGeneratedExcludedCellsAmount());

                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent,
                        DisplayTemplateModel.COL_NUMBERING_TAG_NAME,
                        this.getColNumbering());
                DisplayTemplateModel.writeElement(      // throws
                        xmlSerializer, indent,
                        DisplayTemplateModel.ROW_NUMBERING_TAG_NAME,
                        this.getRowNumbering());

                if (this.entryLabelIsNotNull())
                    DisplayTemplateModel.writeElement(  // throws
                            xmlSerializer, indent, DisplayTemplateModel.ENTRY_LABEL_TAG_NAME,
                            this.getEntryLabel());

                if (null != this.excludedCellsInstance)
                    DisplayTemplateModel.writeElement(  // throws
                            xmlSerializer, indent, DisplayTemplateModel.EXCLUDED_CELLS_TAG_NAME,
                            this.excludedCellsInstance.json());

                if (null != this.excludedRowsInstance)
                    DisplayTemplateModel.writeElement(  // throws
                            xmlSerializer, indent, DisplayTemplateModel.EXCLUDED_ROWS_TAG_NAME,
                            this.excludedRowsInstance.json());

                if (null != this.excludedColsInstance)
                    DisplayTemplateModel.writeElement(  // throws
                            xmlSerializer, indent, DisplayTemplateModel.EXCLUDED_COLS_TAG_NAME,
                            this.excludedColsInstance.json());

                success = true;
            } catch (final IOException e) {
                success = false;
            }

        return success;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void assignCharacterData(
            @Nullable final String elementName,
            final String characterData) {
        if (null != elementName) switch (elementName) {
            case DisplayTemplateModel.TITLE_TAG_NAME:
                this.setTitle(characterData);
                break;

            case DisplayTemplateModel.ROWS_TAG_NAME:
                this.setRows(characterData);
                break;

            case DisplayTemplateModel.COLS_TAG_NAME:
                this.setCols(characterData);
                break;

            case DisplayTemplateModel
                    .GENERATED_EXCLUDED_CELLS_AMOUNT_TAG_NAME:
                this.setGeneratedExcludedCellsAmount(characterData);
                break;

            case DisplayTemplateModel.COL_NUMBERING_TAG_NAME:
                this.setColNumbering(characterData);
                break;

            case DisplayTemplateModel.ROW_NUMBERING_TAG_NAME:
                this.setRowNumbering(characterData);
                break;

            case DisplayTemplateModel.ENTRY_LABEL_TAG_NAME:
                this.setEntryLabel(characterData);
                break;

            case DisplayTemplateModel.EXCLUDED_CELLS_TAG_NAME:
                this.setExcludedCells(characterData);
                break;

            case DisplayTemplateModel.EXCLUDED_ROWS_TAG_NAME:
                this.setExcludedRows(characterData);
                break;

            case DisplayTemplateModel.EXCLUDED_COLS_TAG_NAME:
                this.setExcludedCols(characterData);
                break;
        }
    }
    // endregion

    // region Public Methods
    // region excludedCells Public Methods
    @Nullable
    public String getExcludedCellsAsJson() {
        return null == this.excludedCellsInstance ? null : this.excludedCellsInstance.json();
    }

    public boolean isExcludedCell(
            @NonNull final Cell cell) {
        final Cells excludedCells = this.getExcludedCells();
        // noinspection SimplifiableConditionalExpression
        return null == excludedCells ? false : excludedCells.contains(cell);
    }

    public void toggle(@Nullable final Cell cell) {
        if (null != cell)
            if (this.isExcludedCell(cell))
                // noinspection ConstantConditions
                this.excludedCellsInstance.remove(cell);
            else {
                if (null == this.excludedCellsInstance)
                    this.excludedCellsInstance = new Cells(
                            /* maxRow       => */ this.getRows(),
                            /* maxCol       => */ this.getCols(),
                            /* stringGetter => */ this.stringGetter());
                this.excludedCellsInstance.add(cell);
            }
    }
    // endregion

    // region excludedRows Public Methods
    public void addExcludedRow(@IntRange(from = 1) final int row) {
        this.excludedRows().add(row);
    }

    public void clearExcludedRows() {
        if (null != this.excludedRowsInstance) this.excludedRowsInstance.clear();
    }

    @Nullable
    public String getExcludedRowsAsJson() {
        return null == this.excludedRowsInstance ? null : this.excludedRowsInstance.json();
    }

    public boolean isExcludedRow(@IntRange(from = 1) final int row) {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedRowsInstance ? false : this.excludedRowsInstance.contains(row);
    }
    // endregion

    // region excludedCols Public Methods
    public void addExcludedCol(@IntRange(from = 1) final int col) {
        this.excludedCols().add(col);
    }

    public void clearExcludedCols() {
        if (null != this.excludedColsInstance) this.excludedColsInstance.clear();
    }

    @Nullable
    public String getExcludedColsAsJson() {
        return null == this.excludedColsInstance ? null : this.excludedColsInstance.json();
    }

    public boolean isExcludedCol(@IntRange(from = 1) final int col) {
        // noinspection SimplifiableConditionalExpression
        return null == this.excludedColsInstance ? false : this.excludedColsInstance.contains(col);
    }
    // endregion

    // region checkedItems Public Methods
    @NonNull
    public boolean[] rowCheckedItems() {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @IntRange(from = 1) final int rows = this.getRows();
            result = new boolean[rows];
            for (int i = 0; i < rows; i++) result[i] = this.isExcludedRow(i + 1);
        }
        return result;
    }

    @NonNull
    public boolean[] colCheckedItems() {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @IntRange(from = 1) final int cols = this.getCols();
            result = new boolean[cols];
            for (int i = 0; i < cols; i++) result[i] = this.isExcludedCol(i + 1);
        }
        return result;
    }
    // endregion
    // endregion
}