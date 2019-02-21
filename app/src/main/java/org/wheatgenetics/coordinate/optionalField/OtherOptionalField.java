package org.wheatgenetics.coordinate.optionalField;

/**
 * An OtherOptionalField is an optional field that is anything other than a date optional field.
 *
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
class OtherOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }

    // region Constructors
    OtherOptionalField(@android.support.annotation.NonNull final java.lang.String name,
    final java.lang.String hint) { super(name, hint); }

    OtherOptionalField(@android.support.annotation.NonNull final java.lang.String name)
    { super(name); }

    OtherOptionalField(@android.support.annotation.NonNull final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    { this(name, hint); this.setValue(value); }

    OtherOptionalField(@android.support.annotation.NonNull final org.json.JSONObject jsonObject)
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        super(jsonObject);

        if (org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT.equals(
        this.getHint()))
            throw new org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass();
    }
    // endregion

    @java.lang.Override public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField result =
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                this.getName(), this.getValue(), this.getHint());
        result.setChecked(this.getChecked());
        return result;
    }
}