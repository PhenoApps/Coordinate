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
    private void add(final org.json.JSONObject jsonObject)
    {
        if (null != jsonObject)
            try
            {
                final org.wheatgenetics.coordinate.model.Cell cell =
                    new org.wheatgenetics.coordinate.model.Cell(jsonObject);    // throws org.json.-
                this.cellArrayList().add(cell);                                 //  JSONException

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

        if (null != json) if (json.length() > 0)
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
                    try
                    {
                        this.add((org.json.JSONObject)
                            jsonArray.get(i));                      // throws org.json.JSONException
                    }
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
    @android.support.annotation.IntRange(from = 2) final int xBound,
    @android.support.annotation.IntRange(from = 2) final int yBound)
    {
        final org.wheatgenetics.coordinate.model.Cell cell =
            org.wheatgenetics.coordinate.model.Cell.random(xBound, yBound);

        final java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell> cellArrayList =
            this.cellArrayList();
        cellArrayList.clear(); cellArrayList.add(cell);
    }

    void makeRandomCells(
    @android.support.annotation.IntRange(from = 1)       int amount,
    @android.support.annotation.IntRange(from = 2) final int xBound,
    @android.support.annotation.IntRange(from = 2) final int yBound)
    {
        if (1 == amount)
            this.makeOneRandomCell(xBound, yBound);
        else
            if (amount > 1)
            {
                final java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell> cellArrayList =
                    this.cellArrayList();
                cellArrayList.clear();

                do
                {
                    org.wheatgenetics.coordinate.model.Cell cell;
                    do
                        cell = org.wheatgenetics.coordinate.model.Cell.random(xBound, yBound);
                    while (this.isPresent(cell));
                    cellArrayList.add(cell);
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
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.Cell> cellArrayList =
                this.cellArrayList();

            if (cellArrayList.size() <= 0)
                return null;
            else
            {
                final org.json.JSONArray jsonArray = new org.json.JSONArray();

                for (final org.wheatgenetics.coordinate.model.Cell cell: cellArrayList)
                    if (null != cell)
                        try                                    { jsonArray.put(cell.json()); }
                        catch (final org.json.JSONException e) { /* Skip this JSONObject. */ }

                return jsonArray.toString();
            }
        }
    }

    void clear() { if (null != this.cellArrayListInstance) this.cellArrayList().clear(); }

    boolean isPresent(
    @android.support.annotation.IntRange(from = 1) final int x,
    @android.support.annotation.IntRange(from = 1) final int y)
    { return this.isPresent( new org.wheatgenetics.coordinate.model.Cell(x, y)); }

    void add(
    @android.support.annotation.IntRange(from = 1) final int x,
    @android.support.annotation.IntRange(from = 1) final int y)
    { this.cellArrayList().add(new org.wheatgenetics.coordinate.model.Cell(x, y)); }
    // endregion
}