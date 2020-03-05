package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Cells extends java.lang.Object implements java.lang.Cloneable
{
    // region Types
    static class MaxRowAndOrMaxColOutOfRange extends java.lang.IllegalArgumentException
    { private MaxRowAndOrMaxColOutOfRange() { super("maxRow and/or maxCol is out of range"); } }

    public static class AmountIsTooLarge extends java.lang.IllegalArgumentException
    {
        private AmountIsTooLarge(final int maxAmount)
        {
            super(java.lang.String.format(
                /* format => */ maxAmount <= 0 ?
                    "There is no more room for entries." :
                    1 == maxAmount ?
                        "There is room for only %d more entry."  :
                        "There is room for only %d more entries.",
                /* args => */ maxAmount));
        }
    }
    // endregion

    // region Fields
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.model.Cell maxCell;
    private java.util.TreeSet<org.wheatgenetics.coordinate.model.Cell>
        cellTreeSetInstance = null;                                                     // lazy load
    // endregion

    // region Private Methods
    private void add(final java.lang.Object object)
    {
        if (null != object)
            try
            {
                this.add(new org.wheatgenetics.coordinate.model.Cell(             // throws org.json
                    (org.json.JSONObject) object));                               //  .JSONException
            }
            catch (final org.json.JSONException e) { /* Don't add(). */ }
    }

    @androidx.annotation.Nullable
    private java.util.Iterator<org.wheatgenetics.coordinate.model.Cell> iterator()
    { return null == this.cellTreeSetInstance ? null : this.cellTreeSetInstance.iterator(); }

    @androidx.annotation.NonNull
    private java.util.TreeSet<org.wheatgenetics.coordinate.model.Cell> cellTreeSet()
    {
        if (null == this.cellTreeSetInstance)
            // noinspection Convert2Diamond
            this.cellTreeSetInstance =
                new java.util.TreeSet<org.wheatgenetics.coordinate.model.Cell>();
        return this.cellTreeSetInstance;
    }
    // endregion

    // region Constructors
    /** Assigns this.maxCell. */
    private Cells(@androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell
        maxCell) { super(); this.maxCell = maxCell; }

    /** Creates this.maxCell. */
    @java.lang.SuppressWarnings({"CopyConstructorMissesField"}) private Cells(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cells cells)
    { this(/* maxCell => */ new org.wheatgenetics.coordinate.model.Cell(cells.maxCell)); }

    /** Creates this.maxCell. */
    public Cells(
    @androidx.annotation.IntRange(from = 1) final int maxRow,
    @androidx.annotation.IntRange(from = 1) final int maxCol)
    { this(/* maxCell => */ new org.wheatgenetics.coordinate.model.Cell(maxRow, maxCol)); }

    /** Creates this.maxCell. */
    public Cells(                           final java.lang.String json  ,
    @androidx.annotation.IntRange(from = 1) final int              maxRow,
    @androidx.annotation.IntRange(from = 1) final int              maxCol)
    {
        this(maxRow, maxCol);

        if (null != json) if (json.trim().length() > 0)
        {
            final org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try { jsonArray = (org.json.JSONArray) jsonTokener.nextValue(); }    // throws org.-
                catch (final org.json.JSONException e)                               //  json.JSON-
                { return /* Leave cellTreeSetInstance == null. */; }                 //  Exception
            }

            if (null != jsonArray)
            {
                final int length = jsonArray.length();
                if (length > 0)
                {
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try { this.add(jsonArray.get(i) /* throws org.json.JSONException */); }
                        catch (final org.json.JSONException e) { /* Skip ith jsonObject. */ }
                }
            }
        }
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    {
        if (null == this.cellTreeSetInstance)
            return "null";
        else
            if (this.cellTreeSetInstance.isEmpty())
                return "empty";
            else
            {
                final java.lang.String result;
                {
                    final java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
                    {
                        boolean firstCell = true;
                        for (final org.wheatgenetics.coordinate.model.Cell cell:
                        this.cellTreeSetInstance) if (null != cell)
                        {
                            if (firstCell) firstCell = false; else stringBuilder.append(", ");
                            stringBuilder.append(cell.toString());
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
            if (object instanceof org.wheatgenetics.coordinate.model.Cells)
            {
                final org.wheatgenetics.coordinate.model.Cells cells =
                    (org.wheatgenetics.coordinate.model.Cells) object;
    
                if (null == this.cellTreeSetInstance && null != cells.cellTreeSetInstance)
                    return false;
                else
                    if (null != this.cellTreeSetInstance && null == cells.cellTreeSetInstance)
                        return false;

                // noinspection SimplifiableConditionalExpression
                return null == this.cellTreeSetInstance ? true :
                    this.cellTreeSetInstance.equals(cells.cellTreeSetInstance);
            }
            else return false;
    }

    @java.lang.Override public int hashCode() { return this.toString().hashCode(); }

    @java.lang.SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
    @java.lang.Override @androidx.annotation.NonNull protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.Cells result =
            new org.wheatgenetics.coordinate.model.Cells(this);

        if (null != this.cellTreeSetInstance)
            // noinspection Convert2Diamond
            result.cellTreeSetInstance = new java.util.TreeSet<
                org.wheatgenetics.coordinate.model.Cell>(this.cellTreeSetInstance);

        return result;
    }
    // endregion

    // region Package Methods
    void accumulate(final org.wheatgenetics.coordinate.model.Cells cells)
    {
        if (null != cells)
        {
            final java.util.Iterator<org.wheatgenetics.coordinate.model.Cell> iterator =
                cells.iterator();
            if (null != iterator) while (iterator.hasNext())
            {
                final org.wheatgenetics.coordinate.model.Cell cell = iterator.next();
                if (null != cell) this.add(cell.getRowValue(), cell.getColValue());
            }
        }
    }

    @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.Cells makeRandomCells(
    @androidx.annotation.IntRange(from = 0)       int amount,
    @androidx.annotation.IntRange(from = 1) final int maxRow,
    @androidx.annotation.IntRange(from = 1) final int maxCol) throws
    org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange,
    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
    {
        if (amount < 1)
            return null;
        else
        {
            try
            {
                new org.wheatgenetics.coordinate.model.Cell(maxRow, maxCol).inRange(// throws java.-
                    this.maxCell);                                                  //  lang.Ille-
            }                                                                       //  galArgument-
            catch (final java.lang.IllegalArgumentException e)                      //  Exception
            { throw new org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange(); }

            final int maxAmount = maxRow * maxCol -
                (null == this.cellTreeSetInstance ? 0 : this.cellTreeSetInstance.size());
            if (amount > maxAmount)
                throw new org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge(maxAmount);
            else
            {
                final org.wheatgenetics.coordinate.model.Cells result =
                    new org.wheatgenetics.coordinate.model.Cells(maxRow, maxCol);
                do
                {
                    org.wheatgenetics.coordinate.model.Cell cell;
                    do
                        cell = org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
                            maxRow, maxCol);
                    while (this.contains(cell));

                    this.add(cell); result.add(cell);
                }
                while (--amount > 0);
                return result;
            }
        }
    }

    boolean contains(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    { return this.contains(new org.wheatgenetics.coordinate.model.Cell(row, col)); }

    void add(
    @androidx.annotation.IntRange(from = 1) final int row,
    @androidx.annotation.IntRange(from = 1) final int col)
    { this.add(new org.wheatgenetics.coordinate.model.Cell(row, col)); }

    int size() { return null == this.cellTreeSetInstance ? 0 : this.cellTreeSetInstance.size(); }
    // endregion

    // region Public Methods
    public boolean contains(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.Cell candidateCell)
    {
        // The following code checks to see if candidateCell is inRange().  If it isn't then we know
        // that it can't be present so contains() returns false.  (The check is actually not
        // necessary: the code that follows the following code will also return false since an
        // out-of-range candidateCell will not be found.  The purpose of the check is not to be
        // necessary but to (potentially) save time.)
        try { candidateCell.inRange(this.maxCell) /* throws java.lang.IllegalArgumentException */; }
        catch (final java.lang.IllegalArgumentException e) { return false; }

        // noinspection SimplifiableConditionalExpression
        return null == this.cellTreeSetInstance ? false :
            this.cellTreeSetInstance.contains(candidateCell);
    }

    public boolean add(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        // noinspection SimplifiableConditionalExpression
        return null == cell ? false : this.cellTreeSet().add(
            cell.inRange(this.maxCell) /* throws java.lang.IllegalArgumentException */);
    }

    public boolean remove(final org.wheatgenetics.coordinate.model.Cell cell)
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.cellTreeSetInstance ? false : this.cellTreeSetInstance.remove(cell);
    }

    @androidx.annotation.Nullable public java.lang.String json()
    {
        if (null == this.cellTreeSetInstance)
            return null;
        else
        {
            if (this.cellTreeSetInstance.isEmpty())
                return null;
            else
            {
                final java.lang.String result;
                {
                    final org.json.JSONArray jsonArray = new org.json.JSONArray();

                    for (final org.wheatgenetics.coordinate.model.Cell cell:
                    this.cellTreeSetInstance) if (null != cell)
                        try                                    { jsonArray.put(cell.json()); }
                        catch (final org.json.JSONException e) { /* Skip this JSONObject. */ }

                    result = jsonArray.toString();
                }
                return result.length() > 0 ? result : null;
            }
        }
    }
    // endregion
}