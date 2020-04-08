package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class RowOrCols extends java.lang.Object
{
    // region Fields
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.model.RowOrCol
        maxRowOrCol;
    private java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol>
        rowOrColTreeSetInstance = null;                                                 // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol> rowOrColTreeSet()
    {
        if (null == this.rowOrColTreeSetInstance)
            // noinspection Convert2Diamond
            this.rowOrColTreeSetInstance =
                new java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol>();
        return this.rowOrColTreeSetInstance;
    }

    private void add(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol)
    {
        this.rowOrColTreeSet().add(rowOrCol.inRange(    // throws java.lang.IllegalArgumentException
            this.maxRowOrCol));
    }
    // endregion

    // region Constructors
    /** Assigns this.maxRowOrCol. */
    private RowOrCols(@androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.RowOrCol
        maxRowOrCol)
    { super(); this.maxRowOrCol = maxRowOrCol; }

    /** Creates this.maxRowOrCol. */
    RowOrCols(@androidx.annotation.IntRange(from = 1) final int maxValue)
    { this(/* maxRowOrCol => */ new org.wheatgenetics.coordinate.model.RowOrCol(maxValue)); }

    /** Creates this.maxRowOrCol. */
    public RowOrCols(                       final java.lang.String json    ,
    @androidx.annotation.IntRange(from = 1) final int              maxValue)
    {
        this(maxValue);

        if (null != json) if (json.trim().length() > 0)
        {
            final org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try { jsonArray = (org.json.JSONArray) jsonTokener.nextValue(); }  // throws org.-
                catch (final org.json.JSONException e)                             //  json.JSONEx-
                { return /* Leave rowOrColTreeSetInstance == null. */; }           //  ception
            }

            if (null != jsonArray)
            {
                final int length = jsonArray.length();
                if (length > 0)
                {
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try { this.add(jsonArray.getInt(i) /* throws org.json.JSONException */); }
                        catch (final org.json.JSONException e) { /* Skip ith jsonObject. */ }
                }
            }
        }
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    {
        if (null == this.rowOrColTreeSetInstance)
            return "null";
        else
            if (this.rowOrColTreeSetInstance.isEmpty())
                return "empty";
            else
            {
                final java.lang.String result;
                {
                    final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
                    {
                        boolean firstValue = true;
                        for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol:
                        this.rowOrColTreeSetInstance)
                            if (null != rowOrCol)
                            {
                                if (firstValue) firstValue = false; else stringBuilder.append(", ");
                                stringBuilder.append(rowOrCol.toString());
                            }
                    }
                    result = stringBuilder.toString();
                }
                return result.length() > 0 ? result : "empty";
            }
    }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (null == object)
            return false;
        else
            if (object instanceof org.wheatgenetics.coordinate.model.RowOrCols)
            {
                final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
                    (org.wheatgenetics.coordinate.model.RowOrCols) object;

                if (null == this.rowOrColTreeSetInstance
                &&  null != rowOrCols.rowOrColTreeSetInstance)
                    return false;
                else
                    if (null != this.rowOrColTreeSetInstance
                    &&  null == rowOrCols.rowOrColTreeSetInstance) return false;

                // noinspection SimplifiableConditionalExpression
                return null == this.rowOrColTreeSetInstance ? true :
                    this.rowOrColTreeSetInstance.equals(rowOrCols.rowOrColTreeSetInstance);
            }
            else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @java.lang.Override @androidx.annotation.NonNull protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols result =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxRowOrCol => */
                new org.wheatgenetics.coordinate.model.RowOrCol(this.maxRowOrCol));

        if (null != this.rowOrColTreeSetInstance)
            // noinspection Convert2Diamond
            result.rowOrColTreeSetInstance = new java.util.TreeSet<
                org.wheatgenetics.coordinate.model.RowOrCol>(this.rowOrColTreeSetInstance);

        return result;
    }
    // endregion

    // region Package Methods
    void add(@androidx.annotation.IntRange(from = 1) final int value)
    { this.add(/* rowOrCol => */ new org.wheatgenetics.coordinate.model.RowOrCol(value)); }

    void clear() { if (null != this.rowOrColTreeSetInstance) this.rowOrColTreeSetInstance.clear(); }

    @androidx.annotation.Nullable java.lang.String json()
    {
        if (null == this.rowOrColTreeSetInstance)
            return null;
        else
        {
            if (this.rowOrColTreeSetInstance.isEmpty())
                return null;
            else
            {
                final org.json.JSONArray jsonArray = new org.json.JSONArray();
                for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol:
                this.rowOrColTreeSetInstance)
                    if (null != rowOrCol) jsonArray.put(rowOrCol.getValue());
                return jsonArray.toString();
            }
        }
    }

    boolean contains(@androidx.annotation.IntRange(from = 1) final int candidateValue)
    {
        if (null == this.rowOrColTreeSetInstance)
            return false;
        else
        {
            final org.wheatgenetics.coordinate.model.RowOrCol candidateRowOrCol;
            try
            {
                candidateRowOrCol =
                    new org.wheatgenetics.coordinate.model.RowOrCol(       // throws java.lang.Ille-
                        candidateValue);                                   //  galArgumentException
                candidateRowOrCol.inRange(this.maxRowOrCol);               // throws java.lang.Ille-
            }                                                              //  galArgumentException
            catch (final java.lang.IllegalArgumentException e) { return false; }

            return this.rowOrColTreeSetInstance.contains(candidateRowOrCol);
        }
    }
    // endregion
}