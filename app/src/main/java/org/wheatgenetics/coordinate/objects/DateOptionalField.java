package org.wheatgenetics.coordinate.objects;

public class DateOptionalField extends org.wheatgenetics.coordinate.objects.OptionalField
{
    public DateOptionalField(final java.lang.String name)
    {
        super(name, /* hint => */ org.wheatgenetics.coordinate.objects.OptionalField.DATE_HINT);
    }

    public DateOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        super();
        super.set(jsonObject);
    }


    @Override
    public java.lang.String getValue()
    {
        return org.wheatgenetics.coordinate.utils.Utils.getCurrentDate();
    }
}