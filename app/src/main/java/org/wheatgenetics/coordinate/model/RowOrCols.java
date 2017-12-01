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
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class RowOrCols extends java.lang.Object
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.RowOrCol>
        rowOrColArrayListInstance = null;

    // region Private Methods
    @java.lang.SuppressWarnings("Convert2Diamond")
    private static java.util.ArrayList<org.wheatgenetics.coordinate.model.RowOrCol> make()
    { return new java.util.ArrayList<org.wheatgenetics.coordinate.model.RowOrCol>(); }

    private java.util.ArrayList<org.wheatgenetics.coordinate.model.RowOrCol> rowOrColArrayList()
    {
        if (null == this.rowOrColArrayListInstance)
            this.rowOrColArrayListInstance = org.wheatgenetics.coordinate.model.RowOrCols.make();
        return this.rowOrColArrayListInstance;
    }
    // endregion

    // region Constructors
    RowOrCols() { super(); }

    RowOrCols(final java.lang.String json)
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
                { return; /* Leave rowOrColArrayListInstance == null. */ }
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
    @java.lang.Override
    public java.lang.String toString()
    {
        if (null == this.rowOrColArrayListInstance)
            return "null";
        else
            if (this.rowOrColArrayListInstance.size() <= 0)
                return "empty";
            else
            {
                final java.lang.StringBuilder result = new java.lang.StringBuilder();
                {
                    boolean firstValue = true;
                    for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol:
                    this.rowOrColArrayListInstance)
                        if (null != rowOrCol)
                        {
                            if (firstValue) firstValue = false; else result.append(", ");
                            result.append(rowOrCol.toString());
                        }
                }
                return result.toString();
            }
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object object)
    {
        if (null == object)
            return false;
        else
            if (object instanceof org.wheatgenetics.coordinate.model.RowOrCols)
            {
                final org.wheatgenetics.coordinate.model.RowOrCols rowOrCols =
                    (org.wheatgenetics.coordinate.model.RowOrCols) object;

                if (null == this.rowOrColArrayListInstance
                &&  null != rowOrCols.rowOrColArrayListInstance)
                    return false;
                else
                    if (null != this.rowOrColArrayListInstance
                    &&  null == rowOrCols.rowOrColArrayListInstance) return false;

                if (null == this.rowOrColArrayListInstance)
                    return true;
                else
                    if (this.rowOrColArrayListInstance.size()
                    != rowOrCols.rowOrColArrayListInstance.size())
                        return false;
                    else
                    {
                        {
                            int i = 0;

                            for (final org.wheatgenetics.coordinate.model.RowOrCol myRowOrCol:
                            this.rowOrColArrayListInstance)
                            {
                                final org.wheatgenetics.coordinate.model.RowOrCol yourRowOrCol =
                                    rowOrCols.rowOrColArrayListInstance.get(i++);

                                if (null == myRowOrCol && null != yourRowOrCol)
                                    return false;
                                else
                                    if (null != myRowOrCol && null == yourRowOrCol)
                                        return false;
                                    else
                                        if (null != myRowOrCol)
                                            if (!myRowOrCol.equals(yourRowOrCol)) return false;
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
        final org.wheatgenetics.coordinate.model.RowOrCols result =
            new org.wheatgenetics.coordinate.model.RowOrCols();

        if (null != this.rowOrColArrayListInstance)
        {
            result.rowOrColArrayListInstance = org.wheatgenetics.coordinate.model.RowOrCols.make();
            for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol:
            this.rowOrColArrayListInstance)
                result.rowOrColArrayListInstance.add(
                    new org.wheatgenetics.coordinate.model.RowOrCol(rowOrCol));
        }

        return result;
    }
    // endregion

    // region Package Methods
    void add(@android.support.annotation.IntRange(from = 1) final int value)
    { this.rowOrColArrayList().add(new org.wheatgenetics.coordinate.model.RowOrCol(value)); }

    java.lang.String json()
    {
        if (null == this.rowOrColArrayListInstance)
            return null;
        else
        {
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.RowOrCol> rowOrColArrayList =
                this.rowOrColArrayList();

            if (rowOrColArrayList.size() <= 0)
                return null;
            else
            {
                final org.json.JSONArray jsonArray = new org.json.JSONArray();

                for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol: rowOrColArrayList)
                    if (null != rowOrCol) jsonArray.put(rowOrCol.getValue());

                return jsonArray.toString();
            }
        }
    }

    void clear()
    { if (null != this.rowOrColArrayListInstance) this.rowOrColArrayListInstance.clear(); }

    @java.lang.SuppressWarnings("UnnecessaryUnboxing")
    boolean isPresent(@android.support.annotation.IntRange(from = 1) final int candidateValue)
    {
        boolean result = false;

        if (null != this.rowOrColArrayListInstance)
            for (final org.wheatgenetics.coordinate.model.RowOrCol rowOrCol:
            this.rowOrColArrayListInstance)
                if (null != rowOrCol) if (rowOrCol.getValue() == candidateValue)
                {
                    result = true;
                    break;
                }

        return result;
    }
    // endregion
}