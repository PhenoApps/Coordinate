package org.wheatgenetics.coordinate.objects;

public class NonNullOptionalFields extends org.wheatgenetics.coordinate.objects.OptionalFields
{
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

    public boolean add(final org.json.JSONObject jsonObject) throws org.json.JSONException
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

    public boolean addDate(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(new org.wheatgenetics.coordinate.objects.DateOptionalField(name));
    }
}