package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONTokener
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 */
public class NonNullOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
implements java.lang.Cloneable
{
    // region Constructors
    public NonNullOptionalFields() { super(); }

    public NonNullOptionalFields(final java.lang.String json) throws org.json.JSONException
    {
        this();

        org.json.JSONArray jsonArray;
        {
            final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
            jsonArray = (org.json.JSONArray) jsonTokener.nextValue();           // throws org.json.-
        }                                                                       //  JSONException
        assert null != jsonArray     ;
        assert null != this.arrayList;
        for (int i = 0; i < jsonArray.length(); i++)
        {
            org.wheatgenetics.coordinate.optionalField.OptionalField optionalField;
            {
                final org.json.JSONObject jsonObject =
                    (org.json.JSONObject) jsonArray.get(i);         // throws org.json.JSONException
                try
                {
                    optionalField = new
                        org.wheatgenetics.coordinate.optionalField.OtherOptionalField(jsonObject);
                }
                catch (final
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass wrongClass)
                {
                    optionalField = new
                        org.wheatgenetics.coordinate.optionalField.DateOptionalField(jsonObject);
                }
            }
            this.arrayList.add(optionalField);
        }
    }
    // endregion

    @java.lang.Override @java.lang.SuppressWarnings("CloneDoesntCallSuperClone")
    public java.lang.Object clone() throws java.lang.CloneNotSupportedException
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
            result.arrayList.add(
                (org.wheatgenetics.coordinate.optionalField.OptionalField) optionalField.clone());
        return result;
    }

    // region Add Methods
    public boolean add(final java.lang.String name)
    {
        assert null != this.arrayList;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name));
    }

    public boolean add(final java.lang.String name, final java.lang.String hint)
    {
        assert null != this.arrayList;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, hint));
    }

    public boolean add(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        assert null != this.arrayList;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, value, hint));
    }

    public boolean addDate(final java.lang.String name)
    {
        assert null != this.arrayList;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField(name));
    }
    // endregion

    public boolean isEmpty()
    {
        final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
            this.iterator();

        assert null != iterator;
        return !iterator.hasNext();
    }

    public org.wheatgenetics.coordinate.optionalField.OptionalField get(final int index)
    {
        int size = 0;
        {
            final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                this.iterator();

            assert null != iterator;
            while (iterator.hasNext())
            {
                size++;
                iterator.next();
            }
        }

        if (index < 0 || index >= size)
            throw new java.lang.IndexOutOfBoundsException();
        else
        {
            org.wheatgenetics.coordinate.optionalField.OptionalField optionalField;
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                    this.iterator();
                int i = 0;

                assert null != iterator;
                do optionalField = iterator.next(); while (i++ < index);
            }
            return optionalField;
        }
    }

    public java.lang.String getDatedFirstValue()
    {
        return this.get(0).getValue() + "_" +
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate().replace(
                ".", "_");
    }

    public java.lang.String toJson() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
            jsonArray.put(optionalField.makeJSONObject());          // throws org.json.JSONException
        return jsonArray.toString();
    }

    public java.lang.String[] names()
    {
        final java.util.ArrayList<java.lang.String > nameArrayList =
            new java.util.ArrayList<java.lang.String >();

        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
            nameArrayList.add(optionalField.getName());

        return nameArrayList.toArray(new java.lang.String[nameArrayList.size()]);
    }

    public boolean[] checks()
    {
        boolean result[];
        {
            final java.util.ArrayList<java.lang.Boolean> checkedArrayList =
                new java.util.ArrayList<java.lang.Boolean>();

            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
                checkedArrayList.add(optionalField.getChecked());

            final int checkedArrayListSize = checkedArrayList.size();
            result = new boolean[checkedArrayListSize];

            for (int i = 0; i < checkedArrayListSize; i++) result[i] = checkedArrayList.get(i);
        }
        return result;
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
                final java.util.ArrayList<java.lang.String> valueArrayList =
                    new java.util.ArrayList<java.lang.String>();

                for (final java.lang.String name: names)
                {
                    boolean nameFound = false;
                    for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                    optionalField: this)
                    {
                        if (optionalField.namesAreEqual(name))
                        {
                            valueArrayList.add(name.equals("Person") || name.equals("person")?
                                optionalField.getSafeValue() : optionalField.getValue());
                            nameFound = true;
                            break;
                        }
                    }
                    if (!nameFound) valueArrayList.add("");
                }

                return valueArrayList.toArray(new java.lang.String[valueArrayList.size()]);
            }
    }

    // region Make Methods
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeInitial()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add    ("Plate Id");
        result.addDate("Date"    );

        return result;
    }

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add    ("Identification");
        result.add    ("Person"        );
        result.addDate("Date"          );

        return result;
    }

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add    ("Tray"  , /* hint => */ "Tray ID"    );
        result.add    ("Person", /* hint => */ "Person name");
        result.addDate("Date"                               );

        return result;
    }

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        result.add("Plate"                             , /* hint => */ "Plate ID"); // TODO dna
        result.add("Plate Name"                                                  );
        result.add("Notes"                                                       );
        result.add("tissue_type", /* value => */ "Leaf", /* hint => */ ""        );
        result.add("extraction" , /* value => */ "CTAB", /* hint => */ ""        );
        result.add("person"                                                      );
        result.add("date"                                                        );

        return result;
    }
    // endregion
}