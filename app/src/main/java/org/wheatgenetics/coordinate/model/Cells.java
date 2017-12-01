package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.model.Cell
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Cells extends java.lang.Object implements java.lang.Cloneable
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell>
        cellArrayListInstance = null;

    // region Private Methods
    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    private boolean add(final org.wheatgenetics.coordinate.model.Cell cell)
    { return null == cell ? false : this.cellArrayList().add(cell); }

    private void add(final java.lang.Object object)
    {
        if (null != object)
            try
            {
                final org.wheatgenetics.coordinate.model.Cell cell =
                    new org.wheatgenetics.coordinate.model.Cell(    // throws org.json.JSONException
                        (org.json.JSONObject) object);
                this.add(cell);
            }
            catch (final org.json.JSONException e) { /* Don't add(). */ }
    }

    @java.lang.SuppressWarnings("Convert2Diamond")
    private static java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell> make()
    { return new java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell>(); }

    private java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell> cellArrayList()
    {
        if (null == this.cellArrayListInstance)
            this.cellArrayListInstance = org.wheatgenetics.coordinate.model.Cells.make();
        return this.cellArrayListInstance;
    }

    private boolean isPresent(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.Cell candidateCell)
    {
        boolean result = false;

        if (null != this.cellArrayListInstance)
            for (final org.wheatgenetics.coordinate.model.Cell cell: this.cellArrayListInstance)
                if (null != cell) if (cell.equals(candidateCell)) { result = true; break; }

        return result;
    }
    // endregion

    // region Constructors
    Cells() { super(); }

    Cells(final java.lang.String json)
    {
        this();

        if (null != json) if (json.trim().length() > 0)
        {
            org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try
                {
                    jsonArray = (org.json.JSONArray) jsonTokener.nextValue();   // throws org.json.-
                }                                                               //  JSONException
                catch (final org.json.JSONException e)
                { return; /* Leave cellArrayListInstance == null. */ }
            }

            assert null != jsonArray; final int length = jsonArray.length();
            if (length > 0)
            {
                final int first = 0, last = length - 1;
                for (int i = first; i <= last; i++)
                    try { this.add(jsonArray.get(i) /* throws org.json.JSONException */); }
                    catch (final org.json.JSONException e) { /* Skip ith jsonObject. */ }
            }
        }
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString()
    {
        if (null == this.cellArrayListInstance)
            return "null";
        else
            if (this.cellArrayListInstance.size() <= 0)
                return "empty";
            else
            {
                final java.lang.StringBuilder result = new java.lang.StringBuilder();
                {
                    boolean firstCell = true;
                    for (final org.wheatgenetics.coordinate.model.Cell cell:
                    this.cellArrayListInstance) if (null != cell)
                    {
                        if (firstCell) firstCell = false; else result.append(", ");
                        result.append(cell.toString());
                    }
                }
                return result.toString();
            }
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o)
    {
        if (null == o)
            return false;
        else
            if (o instanceof org.wheatgenetics.coordinate.model.Cells)
            {
                final org.wheatgenetics.coordinate.model.Cells cs =
                    (org.wheatgenetics.coordinate.model.Cells) o;
    
                if (null == this.cellArrayListInstance && null != cs.cellArrayListInstance)
                    return false;
                else
                    if (null != this.cellArrayListInstance && null == cs.cellArrayListInstance)
                        return false;
    
                if (null == this.cellArrayListInstance)
                    return true;
                else
                    if (this.cellArrayListInstance.size() != cs.cellArrayListInstance.size())
                        return false;
                    else
                    {
                        {
                            int i = 0;

                            for (final org.wheatgenetics.coordinate.model.Cell cell:
                            this.cellArrayListInstance)
                            {
                                final org.wheatgenetics.coordinate.model.Cell c =
                                    cs.cellArrayListInstance.get(i++);

                                if (null == cell && null != c)
                                    return false;
                                else
                                    if (null != cell && null == c)
                                        return false;
                                    else
                                        if (null != cell) if (!cell.equals(c)) return false;
                            }
                        }
                        return true;
                    }
            }
            else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone",
        "CloneDoesntDeclareCloneNotSupportedException"})
    protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.Cells result =
            new org.wheatgenetics.coordinate.model.Cells();

        if (null != this.cellArrayListInstance)
        {
            result.cellArrayListInstance = org.wheatgenetics.coordinate.model.Cells.make();
            for (final org.wheatgenetics.coordinate.model.Cell cell: this.cellArrayListInstance)
                if (null != cell) result.cellArrayListInstance.add(
                    (org.wheatgenetics.coordinate.model.Cell) cell.clone());
        }

        return result;
    }
    // endregion

    // region Package Methods
    void makeOneRandomCell(
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        // Creating the cell before clearing cellArrayList is intentional.  This is done so that if
        // an exception is thrown when attempting to create the cell execution will leave this
        // method before the method can clear the cellArrayList.  (It is better to do all of the
        // work or none of the work than to do half of the work.)
        final org.wheatgenetics.coordinate.model.Cell cell =
            org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(maxRow, maxCol);

        this.clear(); this.add(cell);
    }

    void makeRandomCells(
    @android.support.annotation.IntRange(from = 1)       int amount,
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol)
    {
        if (1 == amount)
            this.makeOneRandomCell(maxRow, maxCol);
        else
            if (amount > 1)
            {
                this.clear();

                do
                {
                    org.wheatgenetics.coordinate.model.Cell cell;
                    do
                        cell = org.wheatgenetics.coordinate.model.Cell.makeWithRandomValues(
                            maxRow, maxCol);
                    while (this.isPresent(cell));

                    this.add(cell);
                }
                while (--amount > 0);
            }
    }

    java.lang.String json()
    {
        if (null == this.cellArrayListInstance)
            return null;
        else
        {
            if (this.cellArrayListInstance.size() <= 0)
                return null;
            else
            {
                final org.json.JSONArray jsonArray = new org.json.JSONArray();

                for (final org.wheatgenetics.coordinate.model.Cell cell: this.cellArrayListInstance)
                    if (null != cell)
                        try                                    { jsonArray.put(cell.json()); }
                        catch (final org.json.JSONException e) { /* Skip this JSONObject. */ }

                return jsonArray.toString();
            }
        }
    }

    void clear() { if (null != this.cellArrayListInstance) this.cellArrayListInstance.clear(); }

    boolean isPresent(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return this.isPresent(new org.wheatgenetics.coordinate.model.Cell(row, col)); }

    void add(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.add(new org.wheatgenetics.coordinate.model.Cell(row, col)); }
    // endregion
}