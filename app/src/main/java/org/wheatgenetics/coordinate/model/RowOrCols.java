package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.model.RowOrCol
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class RowOrCols extends java.lang.Object
{
    // region Fields
    private final org.wheatgenetics.coordinate.model.RowOrCol                    maxRowOrCol;
    private       java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol>
        rowOrColTreeSetInstance = null;
    // endregion

    // region Private Methods
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol> rowOrColTreeSet()
    {
        if (null == this.rowOrColTreeSetInstance) this.rowOrColTreeSetInstance =
            new java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol>();
        return this.rowOrColTreeSetInstance;
    }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    private boolean add(final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol)
    {
        return null == rowOrCol ? false : this.rowOrColTreeSet().add(
            rowOrCol.inRange(this.maxRowOrCol) /* throws java.lang.IllegalArgumentException */);
    }
    // endregion

    // region Constructors
    /** Assigns. */
    private RowOrCols(final org.wheatgenetics.coordinate.model.RowOrCol maxRowOrCol)
    { super(); this.maxRowOrCol = maxRowOrCol; }

    /** Creates. */
    RowOrCols(@android.support.annotation.IntRange(from = 1) final int maxValue)
    { this(/* maxRowOrCol => */ new org.wheatgenetics.coordinate.model.RowOrCol(maxValue)); }

    /** Creates. */
    public RowOrCols(                              final java.lang.String json    ,
    @android.support.annotation.IntRange(from = 1) final int              maxValue)
    {
        this(maxValue);

        if (null != json) if (json.trim().length() > 0)
        {
            final org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try { jsonArray = (org.json.JSONArray) jsonTokener.nextValue(); }  // throws org.-
                catch (final org.json.JSONException e)                             //  json.JSONEx-
                { return; /* Leave rowOrColTreeSetInstance == null. */ }           //  ception
            }

            assert null != jsonArray; final int length = jsonArray.length();
            if (length > 0)
            {
                final int first = 0, last = length - 1;
                for (int i = first; i <= last; i++)
                    try { this.add(jsonArray.getInt(i) /* throws org.json.JSONException */); }
                    catch (final org.json.JSONException e) { /* Skip ith jsonObject. */ }
            }
        }
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override public java.lang.String toString()
    {
        if (null == this.rowOrColTreeSetInstance)
            return "null";
        else
            if (this.rowOrColTreeSetInstance.isEmpty())
                return "empty";
            else
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
                return stringBuilder.toString();
            }
    }

    @java.lang.Override @java.lang.SuppressWarnings({"SimplifiableIfStatement"})
    public boolean equals(final java.lang.Object object)
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

                if (null == this.rowOrColTreeSetInstance)
                    return true;
                else
                    return this.rowOrColTreeSetInstance.equals(rowOrCols.rowOrColTreeSetInstance);
            }
            else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException", "Convert2Diamond"})
    protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.RowOrCols result =
            new org.wheatgenetics.coordinate.model.RowOrCols(/* maxRowOrCol => */
                new org.wheatgenetics.coordinate.model.RowOrCol(this.maxRowOrCol));

        if (null != this.rowOrColTreeSetInstance) result.rowOrColTreeSetInstance =
            new java.util.TreeSet<org.wheatgenetics.coordinate.model.RowOrCol>(
                this.rowOrColTreeSetInstance);

        return result;
    }
    // endregion

    // region Package Methods
    void add(@android.support.annotation.IntRange(from = 1) final int value)
    { this.add(/* rowOrCol => */ new org.wheatgenetics.coordinate.model.RowOrCol(value)); }

    void clear() { if (null != this.rowOrColTreeSetInstance) this.rowOrColTreeSetInstance.clear(); }

    java.lang.String json()
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
    // endregion

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    public boolean contains(@android.support.annotation.IntRange(from = 1) final int candidateValue)
    {
        if (null == this.rowOrColTreeSetInstance)
            return false;
        else
        {
            final org.wheatgenetics.coordinate.model.RowOrCol candidateRowOrCol;
            try
            {
                candidateRowOrCol = new org.wheatgenetics.coordinate.model.RowOrCol(       // throws
                    candidateValue);
                candidateRowOrCol.inRange(this.maxRowOrCol);                               // throws
            }
            catch (final java.lang.IllegalArgumentException e) { return false; }

            return this.rowOrColTreeSetInstance.contains(candidateRowOrCol);
        }
    }
}