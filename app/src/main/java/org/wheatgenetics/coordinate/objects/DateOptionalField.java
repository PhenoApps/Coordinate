package org.wheatgenetics.coordinate.objects;

public class DateOptionalField extends org.wheatgenetics.coordinate.objects.OptionalField
{
    public DateOptionalField(final java.lang.String name)
    {
        super(name, /* hint => */ "yyyy-mm-dd");
    }

    public java.lang.String getValue()
    {
        return org.wheatgenetics.coordinate.utils.Utils.getCurrentDate();
    }
}