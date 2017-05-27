package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.utils.Utils
 */

class DateOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    // region Constructors
    DateOptionalField(final java.lang.String name)
    {
        super(
            /* name => */ name                                                              ,
            /* hint => */ org.wheatgenetics.coordinate.optionalField.OptionalField.DATE_HINT);
    }

    DateOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException
    { super(jsonObject); }
    // endregion

    @java.lang.Override
    public java.lang.String getValue()
    { return org.wheatgenetics.coordinate.utils.Utils.getCurrentDate(); }
}