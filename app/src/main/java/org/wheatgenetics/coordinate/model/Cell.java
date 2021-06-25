package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.wheatgenetics.coordinate.StringGetter;

public class Cell implements Cloneable, Comparable,
        ElementModel {
    private static final String ROW_JSON_NAME = "row", COL_JSON_NAME = "col";

    // region Fields
    @NonNull
    private final RowOrCol row, col;
    @NonNull
    private final StringGetter
            stringGetter;
    // endregion

    // region Constructors

    /**
     * Assigns this.row and this.col.
     */
    private Cell(
            @NonNull final RowOrCol row,
            @NonNull final RowOrCol col,
            @NonNull final StringGetter stringGetter) {
        super();
        this.row = row;
        this.col = col;
        this.stringGetter = stringGetter;
    }

    /**
     * Creates this.row and this.col.
     */
    Cell(
            @NonNull final Cell cell,
            @NonNull final StringGetter stringGetter) {
        this(
                new RowOrCol(cell.getRow(), stringGetter),
                new RowOrCol(cell.getCol(), stringGetter),
                stringGetter);
    }

    /**
     * Creates this.row and this.col.
     */
    public Cell(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col,
            @NonNull final StringGetter stringGetter) {
        this(
                new RowOrCol(/* value => */ row, stringGetter),
                new RowOrCol(/* value => */ col, stringGetter),
                stringGetter);
    }

    /**
     * Creates this.row and this.col.
     */
    Cell(
            @NonNull final JSONObject jsonObject,
            @NonNull final StringGetter stringGetter)
            throws JSONException {
        this(
                jsonObject.getInt(Cell.ROW_JSON_NAME),      // throws
                jsonObject.getInt(Cell.COL_JSON_NAME),      // throws
                stringGetter);
    }
    // endregion

    @NonNull
    static Cell makeWithRandomValues(
            @IntRange(from = 1) final int maxRow,
            @IntRange(from = 1) final int maxCol,
            @NonNull final StringGetter stringGetter) {
        return new Cell(
                RowOrCol.makeWithRandomValue(maxRow, stringGetter),
                RowOrCol.makeWithRandomValue(maxCol, stringGetter),
                stringGetter);
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return String.format("Cell(%s, %s)",
                this.getRow().toString(), this.getCol().toString());
    }

    @Override
    public boolean equals(final Object object) {
        if (null == object)
            return false;
        else if (object instanceof Cell) {
            final Cell cell =
                    (Cell) object;
            return this.getRow().equals(cell.getRow()) && this.getCol().equals(cell.getCol());
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    @NonNull
    protected Object clone() {
        return new Cell(this, this.stringGetter);
    }
    // endregion

    // region java.lang.Comparable Overridden Method
    @Override
    public int compareTo(@NonNull final Object object) {
        final Cell cell =
                (Cell) object;
        final int rowCompareResult = this.getRow().compareTo(cell.getRow());
        return 0 == rowCompareResult ? this.getCol().compareTo(cell.getCol()) : rowCompareResult;
    }

    // region org.wheatgenetics.coordinate.model.ElementModel Overridden Methods
    @Override
    @IntRange(from = 1)
    public int getRowValue() {
        return this.getRow().getValue();
    }
    // endregion
    // endregion

    @Override
    @IntRange(from = 1)
    public int getColValue() {
        return this.getCol().getValue();
    }

    // region Package Methods
    @NonNull
    RowOrCol getRow() {
        return this.row;
    }

    @NonNull
    RowOrCol getCol() {
        return this.col;
    }

    @NonNull
    Cell inRange(
            @NonNull final Cell maxCell) {
        this.getRow().inRange(maxCell.getRow());        // throws java.lang.IllegalArgumentException
        this.getCol().inRange(maxCell.getCol());        // throws java.lang.IllegalArgumentException

        return this;
    }

    @NonNull
    JSONObject json() throws JSONException {
        final JSONObject result = new JSONObject();

        result.put(                                                 // throws org.json.JSONException
                Cell.ROW_JSON_NAME, this.getRowValue());
        result.put(                                                 // throws org.json.JSONException
                Cell.COL_JSON_NAME, this.getColValue());

        return result;
    }
    // endregion
}