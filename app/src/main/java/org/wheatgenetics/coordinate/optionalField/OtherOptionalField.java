package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */

class OtherOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }

    // region Constructors
    OtherOptionalField(final java.lang.String name) { super(name); }

    OtherOptionalField(final java.lang.String name, final java.lang.String hint)
    { super(name, hint); }

    OtherOptionalField(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        super(name, hint);
        this.setValue(value);
    }

    OtherOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException,
    org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        super(jsonObject);

        final java.lang.String hint = this.getHint();
        assert null != hint;
        if (hint.equals(org.wheatgenetics.coordinate.optionalField.OptionalField.DATE_HINT))
            throw new org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass();
    }
    // endregion
}