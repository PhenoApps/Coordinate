package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.utils.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
class DateOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    // region Constructors
    DateOptionalField()
    {
        super(
            /* name => */ "Date"                                                            ,
            /* hint => */ org.wheatgenetics.coordinate.optionalField.OptionalField.DATE_HINT);
    }

    DateOptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException
    { super(jsonObject); /* throws org.json.JSONException */ }
    // endregion

    static java.lang.String getCurrentDate()
    {
        return org.wheatgenetics.coordinate.utils.Utils.formatDate(
            java.lang.System.currentTimeMillis()).toString();
    }

    @java.lang.Override
    public java.lang.String getValue()
    { return org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate(); }
}