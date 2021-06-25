package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.wheatgenetics.coordinate.StringGetter;

import java.util.TreeSet;

class RowOrCols {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final RowOrCol
            maxRowOrCol;
    @NonNull
    private final StringGetter
            stringGetter;
    // endregion

    private TreeSet<RowOrCol>
            rowOrColTreeSetInstance = null;                                                 // lazy load
    // endregion

    /**
     * Assigns this.maxRowOrCol.
     */
    private RowOrCols(@NonNull final RowOrCol
                              maxRowOrCol,
                      @NonNull final StringGetter stringGetter) {
        super();
        this.maxRowOrCol = maxRowOrCol;
        this.stringGetter = stringGetter;
    }

    /**
     * Creates this.maxRowOrCol.
     */
    RowOrCols(@IntRange(from = 1) final int maxValue,
              @NonNull final StringGetter stringGetter) {
        this(/* maxRowOrCol => */ new RowOrCol(
                maxValue, stringGetter), stringGetter);
    }
    // endregion

    // region Constructors

    /**
     * Creates this.maxRowOrCol.
     */
    public RowOrCols(final String json,
                     @IntRange(from = 1) final int maxValue,
                     @NonNull final StringGetter stringGetter) {
        this(maxValue, stringGetter);

        if (null != json) if (json.trim().length() > 0) {
            final JSONArray jsonArray;
            {
                final JSONTokener jsonTokener = new JSONTokener(json);
                try {
                    jsonArray = (JSONArray) jsonTokener.nextValue();
                }  // throws org.-
                catch (final JSONException e)                             //  json.JSONEx-
                {
                    return /* Leave rowOrColTreeSetInstance == null. */;
                }           //  ception
            }

            if (null != jsonArray) {
                final int length = jsonArray.length();
                if (length > 0) {
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try {
                            this.add(jsonArray.getInt(i) /* throws org.json.JSONException */);
                        } catch (final JSONException e) { /* Skip ith jsonObject. */ }
                }
            }
        }
    }

    // region Private Methods
    @NonNull
    private TreeSet<RowOrCol> rowOrColTreeSet() {
        if (null == this.rowOrColTreeSetInstance)
            // noinspection Convert2Diamond
            this.rowOrColTreeSetInstance =
                    new TreeSet<RowOrCol>();
        return this.rowOrColTreeSetInstance;
    }

    private void add(
            @NonNull final RowOrCol rowOrCol) {
        this.rowOrColTreeSet().add(rowOrCol.inRange(    // throws java.lang.IllegalArgumentException
                this.maxRowOrCol));
    }
    // endregion

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        if (null == this.rowOrColTreeSetInstance)
            return "null";
        else {
            final String EMPTY_STRING = "empty";
            if (this.rowOrColTreeSetInstance.isEmpty())
                return EMPTY_STRING;
            else {
                final String result;
                {
                    final StringBuilder stringBuilder = new StringBuilder();
                    {
                        boolean firstValue = true;
                        for (final RowOrCol rowOrCol :
                                this.rowOrColTreeSetInstance)
                            if (null != rowOrCol) {
                                if (firstValue) firstValue = false;
                                else stringBuilder.append(", ");
                                stringBuilder.append(rowOrCol.toString());
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
        else if (object instanceof RowOrCols) {
            final RowOrCols rowOrCols =
                    (RowOrCols) object;

            if (null == this.rowOrColTreeSetInstance
                    && null != rowOrCols.rowOrColTreeSetInstance)
                return false;
            else if (null != this.rowOrColTreeSetInstance
                    && null == rowOrCols.rowOrColTreeSetInstance) return false;

            // noinspection SimplifiableConditionalExpression
            return null == this.rowOrColTreeSetInstance ? true :
                    this.rowOrColTreeSetInstance.equals(rowOrCols.rowOrColTreeSetInstance);
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
        final RowOrCols result =
                new RowOrCols(
                        /* maxRowOrCol => */ new RowOrCol(
                        this.maxRowOrCol, this.stringGetter),
                        this.stringGetter);

        if (null != this.rowOrColTreeSetInstance)
            // noinspection Convert2Diamond
            result.rowOrColTreeSetInstance = new TreeSet<
                    RowOrCol>(this.rowOrColTreeSetInstance);

        return result;
    }
    // endregion

    // region Package Methods
    void add(@IntRange(from = 1) final int value) {
        this.add(/* rowOrCol => */ new RowOrCol(
                value, this.stringGetter));
    }

    void clear() {
        if (null != this.rowOrColTreeSetInstance) this.rowOrColTreeSetInstance.clear();
    }

    @Nullable
    String json() {
        if (null == this.rowOrColTreeSetInstance)
            return null;
        else {
            if (this.rowOrColTreeSetInstance.isEmpty())
                return null;
            else {
                final JSONArray jsonArray = new JSONArray();
                for (final RowOrCol rowOrCol :
                        this.rowOrColTreeSetInstance)
                    if (null != rowOrCol) jsonArray.put(rowOrCol.getValue());
                return jsonArray.toString();
            }
        }
    }

    boolean contains(@IntRange(from = 1) final int candidateValue) {
        if (null == this.rowOrColTreeSetInstance)
            return false;
        else {
            final RowOrCol candidateRowOrCol;
            try {
                candidateRowOrCol =
                        new RowOrCol(       // throws java.lang.Ille-
                                candidateValue, this.stringGetter);                //  galArgumentException
                candidateRowOrCol.inRange(this.maxRowOrCol);               // throws java.lang.Ille-
            }                                                              //  galArgumentException
            catch (final IllegalArgumentException e) {
                return false;
            }

            return this.rowOrColTreeSetInstance.contains(candidateRowOrCol);
        }
    }
    // endregion
}