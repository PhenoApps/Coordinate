package org.wheatgenetics.coordinate.optionalField;

/**
 * An OtherOptionalField is an optional field that is anything other than a date optional field.
 *
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.Size
 *
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
class OtherOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }

    // region Constructors
    private OtherOptionalField(@androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    final java.lang.String name, final java.lang.String hint, @androidx.annotation.NonNull final
    org.wheatgenetics.coordinate.StringGetter stringGetter) { super(name, hint, stringGetter); }

    OtherOptionalField(@androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    final java.lang.String name, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.StringGetter stringGetter) { super(name, stringGetter); }

    OtherOptionalField(@androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    final java.lang.String name, final java.lang.String value, final java.lang.String hint,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { this(name, hint, stringGetter); this.setValue(value); }

    OtherOptionalField(@androidx.annotation.NonNull final org.json.JSONObject jsonObject,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    throws org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
    {
        super(jsonObject, stringGetter);

        if (org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT.equals(
        this.getHint()))
            throw new org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass();
    }
    // endregion

    @java.lang.Override @androidx.annotation.NonNull public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField result =
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                this.getName(), this.getValue(), this.getHint(), this.stringGetter());
        result.setChecked(this.getChecked());
        return result;
    }
}