package org.wheatgenetics.coordinate.objects;

class OtherOptionalField extends org.wheatgenetics.coordinate.objects.OptionalField
{
    static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }


    // region Constructors
    OtherOptionalField(final java.lang.String name) { super(name); }

    OtherOptionalField(final java.lang.String name, final java.lang.String hint)
    {
        super(name, hint);
    }

    OtherOptionalField(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        super(name, hint);
        this.setValue(value);
    }

    OtherOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException,
    org.wheatgenetics.coordinate.objects.OtherOptionalField.WrongClass
    {
        super(jsonObject);
        if (this.getHint().equals(org.wheatgenetics.coordinate.objects.OptionalField.DATE_HINT))
            throw new org.wheatgenetics.coordinate.objects.OtherOptionalField.WrongClass();
    }
    // endregion
}