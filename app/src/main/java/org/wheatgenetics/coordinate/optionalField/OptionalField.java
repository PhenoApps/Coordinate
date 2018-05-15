package org.wheatgenetics.coordinate.optionalField;

/**
 * BaseOptionalField and OptionalField used to be one class that used org.json.JSONException and
 * org.json.JSONObject.  The one class was split into two in order to do as much local unit testing
 * as possible (BaseOptionalFieldTest) and as little instrumented testing as possible
 * (OptionalFieldTest).  (BaseOptionalField does not use org.json.JSONException or
 * org.json.JSONObject while OptionalField does.)
 *
 * Uses:
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.VisibleForTesting
 *
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 */
abstract class OptionalField extends org.wheatgenetics.coordinate.optionalField.BaseOptionalField
{
    // region Constants
    private static final java.lang.String NAME_JSON_NAME = "field",
        HINT_JSON_NAME = "hint", VALUE_JSON_NAME = "value";
    private static final java.lang.String CHECKED_JSON_NAME = "checked";
    // endregion

    private static void put(final org.json.JSONObject jsonObject,
    final java.lang.String name, java.lang.String value)
    {
        if (null != jsonObject) if (null != value)
        {
            value = value.trim();
            if (value.length() > 0)
                try { jsonObject.put(name, value); /* throws org.json.JSONException */ }
                catch (final org.json.JSONException e) { /* Don't put value. */ }
        }
    }

    // region Constructors
    OptionalField(final java.lang.String name, final java.lang.String hint) { super(name, hint); }
    OptionalField(final java.lang.String name                             ) { super(name      ); }
    OptionalField(final org.json.JSONObject jsonObject                    )
    {
        this(
            /* name => */ jsonObject.optString(
                org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME),
            /* hint => */ jsonObject.optString(
                org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME));

        this.setValue(jsonObject.optString(
            org.wheatgenetics.coordinate.optionalField.OptionalField.VALUE_JSON_NAME));
        this.setChecked(org.wheatgenetics.coordinate.optionalField.OptionalField.getChecked(
            jsonObject));
    }
    // endregion

    // region Package Methods
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    static org.json.JSONObject makeJSONObject(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME, name);
        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.VALUE_JSON_NAME, value);
        org.wheatgenetics.coordinate.optionalField.OptionalField.put(result,
            org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME, hint);

        return result;
    }

    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    static org.json.JSONObject makeJSONObject(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint, final boolean checked)
    {
        final org.json.JSONObject result =
            org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
                name, value, hint);
        org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(result, checked);
        return result;
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    static boolean getChecked(final org.json.JSONObject jsonObject)
    {
        if (null == jsonObject)
            return true;
        else
        {
            try
            {
                return jsonObject.getBoolean(                       // throws org.json.JSONException
                    org.wheatgenetics.coordinate.optionalField.OptionalField.CHECKED_JSON_NAME);
            }
            catch (final org.json.JSONException e) { return true; }
        }
    }

    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    static void putChecked(final org.json.JSONObject jsonObject, final boolean value)
    {
        if (null != jsonObject)
            try
            {
                jsonObject.put(                                     // throws org.json.JSONException
                    org.wheatgenetics.coordinate.optionalField.OptionalField.CHECKED_JSON_NAME,
                    value                                                                     );
            }
            catch (final org.json.JSONException e) { /* Don't put value. */ }
    }

    org.json.JSONObject makeJSONObject()
    {
        return  org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
            this.getName(), this.getValue(), this.getHint(), this.getChecked());
    }
    // endregion
}