package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.VisibleForTesting
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
 */
public class NonNullOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
implements java.lang.Cloneable
{
    private java.lang.String getFirstValue() { return this.get(0).getValue(); }

    // region Constructors
    public NonNullOptionalFields() { super(); }

    public NonNullOptionalFields(final java.lang.String json)
    {
        this();

        if (null != json)
        {
            org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try
                {
                    jsonArray = (org.json.JSONArray)         // throws java.lang.ClassCastException,
                        jsonTokener.nextValue();             //  org.json.JSONException
                }
                catch (final java.lang.ClassCastException | org.json.JSONException e)
                { jsonArray = null; }
            }

            if (null != jsonArray)
            {
                final int last = jsonArray.length() - 1;
                assert null != this.arrayList; for (int i = 0; i <= last; i++)
                {
                    org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField;
                    {
                        final org.json.JSONObject jsonObject;
                        try { jsonObject = (org.json.JSONObject) jsonArray.get(i); }  // throws org-
                        catch (final org.json.JSONException e) { continue; }          //  .json.-
                                                                                      //  JSONExcep-
                        try                                                           //  tion
                        {
                            baseOptionalField =
                                new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                                    jsonObject);
                        }
                        catch (final
                        org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass e)
                        {
                            baseOptionalField =
                                new org.wheatgenetics.coordinate.optionalField.DateOptionalField(
                                    jsonObject);
                        }
                    }
                    this.arrayList.add(baseOptionalField);
                }
            }
        }
    }
    // endregion

    @java.lang.Override @java.lang.SuppressWarnings("CloneDoesntCallSuperClone")
    public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        assert null != result.arrayList;
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                result.arrayList.add((org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                    dateOptionalField.clone());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    result.arrayList.add(
                        (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                            otherOptionalField.clone());
                }

        return result;
    }

    // region Add Methods
    private boolean add(final java.lang.String name)
    {
        assert null != this.arrayList; return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name));
    }

    private boolean add(final java.lang.String name, final java.lang.String hint)
    {
        assert null != this.arrayList; return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, hint));
    }

    public boolean add(final java.lang.String name, final java.lang.String value,
    final java.lang.String hint)
    {
        assert null != this.arrayList; return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, value, hint));
    }

    private boolean addDate()
    {
        assert null != this.arrayList; return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField());
    }
    // endregion

    // region Package Methods
    void setChecked(@android.support.annotation.IntRange(from = 0) final int index,
    final boolean checked) { this.get(index).setChecked(checked); }

    boolean[] checks()
    {
        final boolean result[];
        {
            @java.lang.SuppressWarnings("Convert2Diamond")
            final java.util.ArrayList<java.lang.Boolean> checkedArrayList =
                new java.util.ArrayList<java.lang.Boolean>();

            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: this)
                checkedArrayList.add(baseOptionalField.getChecked());

            final int checkedArrayListSize = checkedArrayList.size();
            result = new boolean[checkedArrayListSize];

            for (int i = 0; i < checkedArrayListSize; i++) result[i] = checkedArrayList.get(i);
        }
        return result;
    }
    // endregion

    // region Public Methods
    public boolean isEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
            this.iterator();
        assert null != iterator; return !iterator.hasNext();
    }

    public org.wheatgenetics.coordinate.optionalField.BaseOptionalField get(
    @android.support.annotation.IntRange(from = 0) final int index)
    {
        int size = 0;
        {
            final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                this.iterator();
            assert null != iterator; while (iterator.hasNext()) { size++; iterator.next(); }
        }

        if (index < 0 || index >= size)
            throw new java.lang.IndexOutOfBoundsException();
        else
        {
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField result;
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                    this.iterator();
                int i = 0;
                assert null != iterator; do result = iterator.next(); while (i++ < index);
            }
            return result;
        }
    }

    public java.lang.String getDatedFirstValue()
    {
        return this.getFirstValue() + "_" +
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate().replace(
                ".", "_");
    }

    public java.lang.String toJson()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                jsonArray.put(dateOptionalField.makeJSONObject());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    jsonArray.put(otherOptionalField.makeJSONObject());
                }

        return jsonArray.toString();
    }

    public java.lang.String[] names()
    {
        @java.lang.SuppressWarnings("Convert2Diamond")
        final java.util.ArrayList<java.lang.String> nameArrayList =
            new java.util.ArrayList<java.lang.String>();
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
        baseOptionalField: this)
            nameArrayList.add(baseOptionalField.getName());
        return nameArrayList.toArray(new java.lang.String[nameArrayList.size()]);
    }

    public java.lang.String[] values()
    {
        @java.lang.SuppressWarnings("Convert2Diamond")
        final java.util.ArrayList<java.lang.String> valueArrayList =
            new java.util.ArrayList<java.lang.String>();

        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                valueArrayList.add(dateOptionalField.getValue());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                            baseOptionalField;
                    valueArrayList.add(otherOptionalField.getValue());
                }

        return valueArrayList.toArray(new java.lang.String[valueArrayList.size()]);
    }

    public java.lang.String[] values(final java.lang.String names[])
    {
        if (null == names)
            return null;
        else
            if (names.length <= 0)
                return null;
            else
            {
                @java.lang.SuppressWarnings("Convert2Diamond")
                final java.util.ArrayList<java.lang.String> valueArrayList =
                    new java.util.ArrayList<java.lang.String>();

                for (final java.lang.String name: names)
                {
                    boolean nameFound = false;
                    for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                    baseOptionalField: this)
                        if (baseOptionalField.namesAreEqual(name))
                        {
                            final java.lang.String safeValue;
                            {
                                if (baseOptionalField instanceof
                                org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                                {
                                    final
                                        org.wheatgenetics.coordinate.optionalField.DateOptionalField
                                            dateOptionalField =
                                                (org.wheatgenetics.coordinate.optionalField
                                                    .DateOptionalField) baseOptionalField;
                                    safeValue = dateOptionalField.getSafeValue();
                                }
                                else
                                    if (baseOptionalField instanceof
                                    org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                    {
                                        final org.wheatgenetics.coordinate.optionalField
                                            .OtherOptionalField otherOptionalField =
                                                (org.wheatgenetics.coordinate.optionalField
                                                    .OtherOptionalField) baseOptionalField;
                                        safeValue = otherOptionalField.getSafeValue();
                                    }
                                    else safeValue = null;
                            }
                            valueArrayList.add(safeValue);
                            nameFound = true;
                            break;
                        }
                    if (!nameFound) valueArrayList.add("");
                }

                return valueArrayList.toArray(new java.lang.String[valueArrayList.size()]);
            }
    }

    // region Make Public Methods
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        result.add("Identification"); result.add("Person"); result.addDate();
        return result;
    }

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add    ("Tray"  , /* hint => */ "Tray ID"    );
        result.add    ("Person", /* hint => */ "Person name");
        result.addDate(                                     );

        return result;
    }

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add    ("Plate"                             , /* hint => */ "Plate ID");     // TODO: dna
        result.add    ("Plate Name"                                                  );
        result.add    ("Notes"                                                       );
        result.add    ("tissue_type", /* value => */ "Leaf", /* hint => */ ""        );
        result.add    ("extraction" , /* value => */ "CTAB", /* hint => */ ""        );
        result.add    ("person"                                                      );
        result.addDate(                                                              );

        return result;
    }
    // endregion
    // endregion
}