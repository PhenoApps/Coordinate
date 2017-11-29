package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONTokener
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Coordinates extends java.lang.Object implements java.lang.Cloneable
{
    private java.util.ArrayList<java.lang.Integer> integerArrayListInstance = null;

    // region Private Methods
    private static int valid(final int i)
    {
        if (i < 1) throw new java.lang.IllegalArgumentException("value must be > 0");
        return i;
    }

    @java.lang.SuppressWarnings("Convert2Diamond")
    private static java.util.ArrayList<java.lang.Integer> make()
    { return new java.util.ArrayList<java.lang.Integer>(); }

    private java.util.ArrayList<java.lang.Integer> integerArrayList()
    {
        if (null == this.integerArrayListInstance) this.integerArrayListInstance =
            org.wheatgenetics.coordinate.model.Coordinates.make();
        return this.integerArrayListInstance;
    }
    // endregion

    // region Constructors
    Coordinates() { super(); }

    Coordinates(final java.lang.String json)
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
                { return; /* Leave integerArrayListInstance == null. */ }
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
        if (null == this.integerArrayListInstance)
            return "null";
        else
            if (this.integerArrayList().size() <= 0)
                return "empty";
            else
            {
                final java.lang.StringBuilder result = new java.lang.StringBuilder();
                {
                    boolean firstInteger = true;
                    for (final java.lang.Integer integer: this.integerArrayListInstance)
                        if (null != integer)
                        {
                            if (firstInteger) firstInteger = false; else result.append(", ");
                            result.append(integer.toString());
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
            if (o instanceof org.wheatgenetics.coordinate.model.Coordinates)
            {
                final org.wheatgenetics.coordinate.model.Coordinates cs =
                    (org.wheatgenetics.coordinate.model.Coordinates) o;
    
                if (null == this.integerArrayListInstance && null != cs.integerArrayListInstance)
                    return false;
                else
                    if (null != this.integerArrayListInstance
                    &&  null == cs.integerArrayListInstance  ) return false;
    
                if (null == this.integerArrayListInstance)
                    return true;
                else
                    if (this.integerArrayListInstance.size() != cs.integerArrayListInstance.size())
                        return false;
                    else
                    {
                        {
                            int i = 0;

                            for (final java.lang.Integer integer: this.integerArrayListInstance)
                            {
                                final java.lang.Integer c = cs.integerArrayListInstance.get(i++);

                                if (null == integer && null != c)
                                    return false;
                                else
                                    if (null != integer && null == c)
                                        return false;
                                    else
                                        if (null != integer) if (!integer.equals(c)) return false;
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
        "CloneDoesntDeclareCloneNotSupportedException", "UnnecessaryBoxing", "UnnecessaryUnboxing"})
    protected java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.model.Coordinates result =
            new org.wheatgenetics.coordinate.model.Coordinates();

        if (null != this.integerArrayListInstance)
        {
            result.integerArrayListInstance = org.wheatgenetics.coordinate.model.Coordinates.make();
            for (final java.lang.Integer integer: this.integerArrayListInstance)
                result.integerArrayListInstance.add(java.lang.Integer.valueOf(integer.intValue()));
        }

        return result;
    }
    // endregion

    // region Package Methods
    void add(@android.support.annotation.IntRange(from = 1) final int integer)
    { this.integerArrayList().add(org.wheatgenetics.coordinate.model.Coordinates.valid(integer)); }

    java.lang.String json()
    {
        if (null == this.integerArrayListInstance)
            return null;
        else
        {
            final java.util.ArrayList<java.lang.Integer> integerArrayList =
                this.integerArrayList();

            if (integerArrayList.size() <= 0)
                return null;
            else
            {
                final org.json.JSONArray jsonArray = new org.json.JSONArray();

                for (final java.lang.Integer integer: integerArrayList)
                    if (null != integer) jsonArray.put(integer.intValue());

                return jsonArray.toString();
            }
        }
    }

    void clear()
    { if (null != this.integerArrayListInstance) this.integerArrayListInstance.clear(); }

    @java.lang.SuppressWarnings("UnnecessaryUnboxing")
    boolean isPresent(@android.support.annotation.IntRange(from = 1) final int candidateInteger)
    {
        boolean result = false;

        if (null != this.integerArrayListInstance)
            for (final java.lang.Integer integer: this.integerArrayListInstance)
                if (null != integer) if (integer.intValue() == candidateInteger)
                {
                    result = true;
                    break;
                }

        return result;
    }
    // endregion
}