package org.wheatgenetics.coordinate.optionalField;

/**
 * An OtherOptionalField is an optional field that is anything other than a date optional field.
 *
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

    OtherOptionalField(final java.lang.String name, final java.lang.String value,
    final java.lang.String hint) { this(name, hint); this.setValue(value); }

    OtherOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException,
    org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        super(jsonObject);                                          // throws org.json.JSONException

        if (org.wheatgenetics.coordinate.optionalField.OptionalField.DATE_HINT.equals(
        this.getHint()))
            throw new org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass();
    }
    // endregion
}