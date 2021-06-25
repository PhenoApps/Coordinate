package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

import java.util.Iterator;
import java.util.TreeSet;

public class Cells implements Cloneable {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final Cell maxCell;
    @NonNull
    private final StringGetter
            stringGetter;
    // endregion
    private TreeSet<Cell>
            cellTreeSetInstance = null;                                                     // lazy load
    /**
     * Assigns this.maxCell.
     */
    private Cells(
            @NonNull final Cell maxCell,
            @NonNull final StringGetter stringGetter) {
        super();
        this.maxCell = maxCell;
        this.stringGetter = stringGetter;
    }
    // endregion

    /**
     * Creates this.maxCell.
     */
    private Cells(
            @NonNull final Cells cells,
            @NonNull final StringGetter stringGetter) {
        this(new Cell(
                cells.maxCell, stringGetter), stringGetter);
    }
    // endregion

    /**
     * Creates this.maxCell.
     */
    public Cells(
            @IntRange(from = 1) final int maxRow,
            @IntRange(from = 1) final int maxCol,
            @NonNull final StringGetter stringGetter) {
        this(new Cell(
                maxRow, maxCol, stringGetter), stringGetter);
    }

    /**
     * Creates this.maxCell.
     */
    public Cells(final String json,
                 @IntRange(from = 1) final int maxRow,
                 @IntRange(from = 1) final int maxCol,
                 @NonNull final StringGetter stringGetter) {
        this(maxRow, maxCol, stringGetter);

        if (null != json) if (json.trim().length() > 0) {
            final JSONArray jsonArray;
            {
                final JSONTokener jsonTokener = new JSONTokener(json);
                try {
                    jsonArray = (JSONArray) jsonTokener.nextValue();
                }    // throws org.-
                catch (final JSONException e)                               //  json.JSON-
                {
                    return /* Leave cellTreeSetInstance == null. */;
                }                 //  Exception
            }

            if (null != jsonArray) {
                final int length = jsonArray.length();
                if (length > 0) {
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try {
                            this.add(jsonArray.get(i) /* throws org.json.JSONException */);
                        } catch (final JSONException e) { /* Skip ith jsonObject. */ }
                }
            }
        }
    }

    // region Private Methods
    private void add(final Object object) {
        if (null != object)
            try {
                this.add(new Cell(             // throws org.json
                        (JSONObject) object, this.stringGetter));            //  .JSONException
            } catch (final JSONException e) { /* Don't add(). */ }
    }
    // endregion

    // region Constructors

    @Nullable
    private Iterator<Cell> iterator() {
        return null == this.cellTreeSetInstance ? null : this.cellTreeSetInstance.iterator();
    }

    @NonNull
    private TreeSet<Cell> cellTreeSet() {
        if (null == this.cellTreeSetInstance)
            // noinspection Convert2Diamond
            this.cellTreeSetInstance =
                    new TreeSet<Cell>();
        return this.cellTreeSetInstance;
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        if (null == this.cellTreeSetInstance)
            return "null";
        else {
            final String EMPTY_STRING = "empty";
            if (this.cellTreeSetInstance.isEmpty())
                return EMPTY_STRING;
            else {
                final String result;
                {
                    final StringBuilder stringBuilder = new StringBuilder();
                    {
                        boolean firstCell = true;
                        for (final Cell cell :
                                this.cellTreeSetInstance)
                            if (null != cell) {
                                if (firstCell) firstCell = false;
                                else stringBuilder.append(", ");
                                stringBuilder.append(cell.toString());
                            }
                    }
                    result = stringBuilder.toString();
                }
                return result.length() > 0 ? result : EMPTY_STRING;
            }
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (null == object)
            return false;
        else if (object instanceof Cells) {
            final Cells cells =
                    (Cells) object;

            if (null == this.cellTreeSetInstance && null != cells.cellTreeSetInstance)
                return false;
            else if (null != this.cellTreeSetInstance && null == cells.cellTreeSetInstance)
                return false;

            // noinspection SimplifiableConditionalExpression
            return null == this.cellTreeSetInstance ? true :
                    this.cellTreeSetInstance.equals(cells.cellTreeSetInstance);
        } else return false;
    }
    // endregion

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    @NonNull
    protected Object clone() {
        final Cells result =
                new Cells(this, this.stringGetter);

        if (null != this.cellTreeSetInstance)
            // noinspection Convert2Diamond
            result.cellTreeSetInstance = new TreeSet<
                    Cell>(this.cellTreeSetInstance);

        return result;
    }

    // region Package Methods
    void accumulate(final Cells cells) {
        if (null != cells) {
            final Iterator<Cell> iterator =
                    cells.iterator();
            if (null != iterator) while (iterator.hasNext()) {
                final Cell cell = iterator.next();
                if (null != cell) this.add(cell.getRowValue(), cell.getColValue());
            }
        }
    }

    @Nullable
    Cells makeRandomCells(
            @IntRange(from = 0) int amount,
            @IntRange(from = 1) final int maxRow,
            @IntRange(from = 1) final int maxCol) throws
            Cells.MaxRowAndOrMaxColOutOfRange,
            Cells.AmountIsTooLarge {
        if (amount < 1)
            return null;
        else {
            try {
                new Cell(
                        maxRow, maxCol, this.stringGetter).inRange(this.maxCell);  // throws java.lang
            }                                                                  //  .IllegalArgument-
            catch (final IllegalArgumentException e)                 //  Exception
            {
                throw new Cells.MaxRowAndOrMaxColOutOfRange(
                        this.stringGetter);
            }

            final int maxAmount = maxRow * maxCol -
                    (null == this.cellTreeSetInstance ? 0 : this.cellTreeSetInstance.size());
            if (amount > maxAmount)
                throw new Cells.AmountIsTooLarge(
                        maxAmount, this.stringGetter);
            else {
                final Cells result =
                        new Cells(maxRow, maxCol, this.stringGetter);
                do {
                    Cell cell;
                    do
                        cell = Cell.makeWithRandomValues(
                                maxRow, maxCol, this.stringGetter);
                    while (this.contains(cell));

                    this.add(cell);
                    result.add(cell);
                }
                while (--amount > 0);
                return result;
            }
        }
    }
    // endregion

    boolean contains(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        return this.contains(
                new Cell(row, col, this.stringGetter));
    }

    void add(
            @IntRange(from = 1) final int row,
            @IntRange(from = 1) final int col) {
        this.add(new Cell(row, col, this.stringGetter));
    }

    int size() {
        return null == this.cellTreeSetInstance ? 0 : this.cellTreeSetInstance.size();
    }

    boolean contains(@NonNull final Cell candidateCell) {
        // The following code checks to see if candidateCell is inRange().  If it isn't then we know
        // that it can't be present so contains() returns false.  (The check is actually not
        // necessary: the code that follows the following code will also return false since an
        // out-of-range candidateCell will not be found.  The purpose of the check is not to be
        // necessary but to (potentially) save time.)
        try {
            candidateCell.inRange(this.maxCell) /* throws java.lang.IllegalArgumentException */;
        } catch (final IllegalArgumentException e) {
            return false;
        }

        // noinspection SimplifiableConditionalExpression
        return null == this.cellTreeSetInstance ? false :
                this.cellTreeSetInstance.contains(candidateCell);
    }

    // region Public Methods
    public boolean add(final Cell cell) {
        // noinspection SimplifiableConditionalExpression
        return null == cell ? false : this.cellTreeSet().add(
                cell.inRange(this.maxCell) /* throws java.lang.IllegalArgumentException */);
    }

    public boolean remove(final Cell cell) {
        // noinspection SimplifiableConditionalExpression
        return null == this.cellTreeSetInstance ? false : this.cellTreeSetInstance.remove(cell);
    }
    // endregion

    @Nullable
    public String json() {
        if (null == this.cellTreeSetInstance)
            return null;
        else {
            if (this.cellTreeSetInstance.isEmpty())
                return null;
            else {
                final String result;
                {
                    final JSONArray jsonArray = new JSONArray();

                    for (final Cell cell :
                            this.cellTreeSetInstance)
                        if (null != cell)
                            try {
                                jsonArray.put(cell.json());
                            } catch (final JSONException e) { /* Skip this JSONObject. */ }

                    result = jsonArray.toString();
                }
                return result.length() > 0 ? result : null;
            }
        }
    }

    // region Types
    static class MaxRowAndOrMaxColOutOfRange extends IllegalArgumentException {
        private MaxRowAndOrMaxColOutOfRange(@NonNull final StringGetter stringGetter) {
            super(stringGetter.get(
                    R.string.CellsMaxRowAndOrMaxColOutOfRange));
        }
    }

    public static class AmountIsTooLarge extends IllegalArgumentException {
        private AmountIsTooLarge(final int maxAmount, @NonNull final StringGetter stringGetter) {
            super(stringGetter.getQuantity(
                    /* resId      => */ R.plurals.AmountIsTooLarge,
                    /* quantity   => */ Math.max(maxAmount, 0),
                    /* formatArgs => */ maxAmount));
        }
    }
    // endregion
}