package org.wheatgenetics.coordinate.objects;

public class NonNullOptionalFields extends org.wheatgenetics.coordinate.objects.OptionalFields
{
    // region Protected Methods
    protected boolean add(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        org.wheatgenetics.coordinate.objects.OptionalField optionalField;

        try
        {
            optionalField = new org.wheatgenetics.coordinate.objects.OptionalField(jsonObject);
        }
        catch (org.wheatgenetics.coordinate.objects.OptionalField.WrongClass wrongClass)
        {
            optionalField = new org.wheatgenetics.coordinate.objects.DateOptionalField(jsonObject);
        }

        assert this.arrayList != null;
        return this.arrayList.add(optionalField);
    }

    protected int size()
    {
        final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
            this.iterator();
        int size = 0;

        assert iterator != null;
        while (iterator.hasNext())
        {
            size++;
            iterator.next();
        }
        return size;
    }
    // endregion


    // region Public Methods
    // region Add Public Methods
    public boolean add(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(new org.wheatgenetics.coordinate.objects.OptionalField(name));
    }

    public boolean add(final java.lang.String name, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OptionalField(name, hint));
    }

    public boolean add(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OptionalField(name, value, hint));
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
        if (index < 0 || index >= this.size())
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


    // region JSON Public Methods
    public java.lang.String toJson() throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (final org.wheatgenetics.coordinate.objects.OptionalField optionalField: this)
            jsonArray.put(optionalField.makeJSONObject());          // throws org.json.JSONException
        return jsonArray.toString();
    }

    public static org.wheatgenetics.coordinate.objects.NonNullOptionalFields fromJson(
    final java.lang.String json) throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.objects.NonNullOptionalFields nonNullOptionalFields =
            new org.wheatgenetics.coordinate.objects.NonNullOptionalFields();

        final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
        final org.json.JSONArray   jsonArray   =
            (org.json.JSONArray) jsonTokener.nextValue();           // throws org.json.JSONException

        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length(); i++) nonNullOptionalFields.add(
            (org.json.JSONObject) jsonArray.get(i));                // throws org.json.JSONException

        return nonNullOptionalFields;
    }
    // endregion
    // endregion
}