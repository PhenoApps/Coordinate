package org.wheatgenetics.coordinate.optionalField;

/**
 * BaseOptionalField and OptionalField used to be one class that used org.json.JSONException and
 * org.json.JSONObject.  The one class was split into two in order to do as much local unit testing
 * as possible (BaseOptionalFieldTest) and as little instrumented testing as possible
 * (OptionalFieldTest).  (BaseOptionalField does not use org.json.JSONException or
 * org.json.JSONObject while OptionalField does.)
 *
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.VisibleForTesting
 *
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 */
abstract class OptionalField extends org.wheatgenetics.coordinate.optionalField.BaseOptionalField
{
    private static final java.lang.String NAME_JSON_NAME = "field", HINT_JSON_NAME = "hint";

    @java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
    private static class JSONObject extends java.lang.Object
    {
        private static final java.lang.String VALUE_JSON_NAME = "value",
            CHECKED_JSON_NAME = "checked";

        @androidx.annotation.NonNull private final org.json.JSONObject jsonObject;

        // region Private Methods
        private void put(final java.lang.String name, java.lang.String value)
        {
            if (null != value)
            {
                value = value.trim();
                if (value.length() > 0)
                    try { this.jsonObject.put(name, value) /* throws org.json.JSONException */; }
                    catch (final org.json.JSONException e) { /* Don't put value. */ }
            }
        }

        void putChecked(final boolean checked)
        {
            try
            {
                this.jsonObject.put(org.wheatgenetics               // throws org.json.JSONException
                        .coordinate.optionalField.OptionalField.JSONObject.CHECKED_JSON_NAME,
                    checked                                                                 );
            }
            catch (final org.json.JSONException e) { /* Don't put checked. */ }
        }
        // endregion

        // region Constructors
        private JSONObject(@androidx.annotation.NonNull final org.json.JSONObject jsonObject)
        { super(); this.jsonObject = jsonObject; }

        private JSONObject(final java.lang.String name, final java.lang.String value,
        final java.lang.String hint)
        {
            super(); this.jsonObject = new org.json.JSONObject();

            this.put(org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME, name);
            this.put(
                org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject.VALUE_JSON_NAME,
                value);
            this.put(org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME, hint);
        }


        private JSONObject(final java.lang.String name, final java.lang.String value,
        final java.lang.String hint, final boolean checked)
        { this(name, value, hint); this.putChecked(checked); }

        private JSONObject(@androidx.annotation.NonNull final org.json.JSONObject jsonObject,
        final boolean checked) { this(jsonObject); this.putChecked(checked); }
        // endregion

        private java.lang.String getValue()
        {
            return this.jsonObject.optString(org.wheatgenetics.coordinate
                .optionalField.OptionalField.JSONObject.VALUE_JSON_NAME);
        }

        private boolean getChecked()
        {
            try
            {
                return this.jsonObject.getBoolean(org.wheatgenetics // throws org.json.JSONException
                    .coordinate.optionalField.OptionalField.JSONObject.CHECKED_JSON_NAME);
            }
            catch (final org.json.JSONException e) { return true; }
        }

        @androidx.annotation.NonNull private org.json.JSONObject jsonObject()
        { return this.jsonObject; }
    }

    // region Constructors
    OptionalField(@androidx.annotation.NonNull final java.lang.String name,
    final java.lang.String hint) { super(name, hint); }

    OptionalField(@androidx.annotation.NonNull final java.lang.String name) { super(name); }

    OptionalField(@androidx.annotation.NonNull final org.json.JSONObject jsonObject)
    {
        this(
            /* name => */ jsonObject.optString(
                org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME),
            /* hint => */ jsonObject.optString(
                org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME));

        final org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject customJSONObject =
            new org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject(jsonObject);
        this.setValue(customJSONObject.getValue()); this.setChecked(customJSONObject.getChecked());
    }
    // endregion

    // region Package Methods
    @java.lang.SuppressWarnings({"DefaultAnnotationParam"}) @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE) @androidx.annotation.NonNull
    static org.json.JSONObject makeJSONObject(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        return new org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject(
            name, value, hint).jsonObject();
    }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"}) @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE) @androidx.annotation.NonNull
    static org.json.JSONObject makeJSONObject(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint, final boolean checked)
    {
        return new org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject(
            name, value, hint, checked).jsonObject();
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    static boolean getChecked(final org.json.JSONObject jsonObject)
    {
        if (null == jsonObject)
            return true;
        else
            return new org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject(
                jsonObject).getChecked();
    }

    @java.lang.SuppressWarnings({"DefaultAnnotationParam", "SameParameterValue"})
    @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    static void putChecked(final org.json.JSONObject jsonObject, final boolean checked)
    {
        if (null != jsonObject)
            new org.wheatgenetics.coordinate.optionalField.OptionalField.JSONObject(
                jsonObject, checked);
    }

    @androidx.annotation.NonNull org.json.JSONObject makeJSONObject()
    {
        return org.wheatgenetics.coordinate.optionalField.OptionalField.makeJSONObject(
            this.getName(), this.getValue(), this.getHint(), this.getChecked());
    }
    // endregion
}