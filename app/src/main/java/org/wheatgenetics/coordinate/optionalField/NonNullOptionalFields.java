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
{
    // region Public Methods
    // region Constructor Public Methods
    public NonNullOptionalFields() { super(); }

    public NonNullOptionalFields(final java.lang.String json) throws org.json.JSONException
    {
        this();

        final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
        final org.json.JSONArray   jsonArray   =
            (org.json.JSONArray) jsonTokener.nextValue();           // throws org.json.JSONException

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

    // region Add Public Methods
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
                do
                    optionalField = iterator.next();
                while (i++ < index);
            }
            return optionalField;
        }
    }

    public java.lang.String toJson() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
            jsonArray.put(optionalField.makeJSONObject());          // throws org.json.JSONException
        return jsonArray.toString();
    }
    // endregion
}