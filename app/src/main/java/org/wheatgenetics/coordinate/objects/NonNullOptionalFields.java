package org.wheatgenetics.coordinate.objects;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONTokener
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.objects.DateOptionalField
 * org.wheatgenetics.coordinate.objects.OptionalField
 * org.wheatgenetics.coordinate.objects.OtherOptionalField
 * org.wheatgenetics.coordinate.objects.OptionalFields
 */

public class NonNullOptionalFields extends org.wheatgenetics.coordinate.objects.OptionalFields
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

        assert jsonArray      != null;
        assert this.arrayList != null;
        for (int i = 0; i < jsonArray.length(); i++)
        {
            org.wheatgenetics.coordinate.objects.OptionalField optionalField;
            {
                final org.json.JSONObject jsonObject =
                    (org.json.JSONObject) jsonArray.get(i);         // throws org.json.JSONException

                try
                {
                    optionalField =
                        new org.wheatgenetics.coordinate.objects.OtherOptionalField(jsonObject);
                }
                catch (
                final org.wheatgenetics.coordinate.objects.OtherOptionalField.WrongClass wrongClass)
                {
                    optionalField =
                        new org.wheatgenetics.coordinate.objects.DateOptionalField(jsonObject);
                }
            }
            this.arrayList.add(optionalField);
        }
    }
    // endregion


    // region Add Public Methods
    public boolean add(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OtherOptionalField(name));
    }

    public boolean add(final java.lang.String name, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OtherOptionalField(name, hint));
    }

    public boolean add(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OtherOptionalField(name, value, hint));
    }

    public boolean addDate(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(new org.wheatgenetics.coordinate.objects.DateOptionalField(name));
    }
    // endregion


    public boolean isEmpty()
    {
        final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
            this.iterator();

        assert iterator != null;
        return iterator.hasNext();
    }

    public org.wheatgenetics.coordinate.objects.OptionalField get(final int index)
    {
        int size = 0;
        {
            final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
                this.iterator();

            assert iterator != null;
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
            org.wheatgenetics.coordinate.objects.OptionalField optionalField;
            {
                final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
                    this.iterator();
                int i = 0;

                assert iterator != null;
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

        for (final org.wheatgenetics.coordinate.objects.OptionalField optionalField: this)
            jsonArray.put(optionalField.makeJSONObject());          // throws org.json.JSONException
        return jsonArray.toString();
    }
    // endregion
}