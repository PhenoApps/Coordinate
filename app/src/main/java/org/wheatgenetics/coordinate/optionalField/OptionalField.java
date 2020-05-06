package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;
import androidx.annotation.VisibleForTesting;

import org.json.JSONException;
import org.wheatgenetics.coordinate.StringGetter;

/**
 * BaseOptionalField and OptionalField used to be one class that used org.json.JSONException and
 * org.json.JSONObject.  The one class was split into two in order to do as much local unit testing
 * as possible (BaseOptionalFieldTest) and as little instrumented testing as possible
 * (OptionalFieldTest).  (BaseOptionalField does not use org.json.JSONException or
 * org.json.JSONObject while OptionalField does.)
 */
abstract class OptionalField extends BaseOptionalField {
    private static final String NAME_JSON_NAME = "field", HINT_JSON_NAME = "hint";

    // region Constructors
    OptionalField(@NonNull @Size(min = 1) final String name, final String hint, @NonNull final
    StringGetter stringGetter) {
        super(name, hint, stringGetter);
    }

    OptionalField(@NonNull @Size(min = 1) final String name, @NonNull final StringGetter stringGetter) {
        super(name, stringGetter);
    }

    OptionalField(@NonNull final org.json.JSONObject jsonObject,
                  @NonNull final StringGetter stringGetter) {
        this(
                /* name => */ jsonObject.optString(
                        OptionalField.NAME_JSON_NAME),
                /* hint => */ jsonObject.optString(
                        OptionalField.HINT_JSON_NAME),
                /* stringGetter => */ stringGetter);

        final OptionalField.JSONObject customJSONObject =
                new OptionalField.JSONObject(
                        jsonObject, stringGetter);
        this.setValue(customJSONObject.getValue());
        this.setChecked(customJSONObject.getChecked());
    }

    // region Package Methods
    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    @NonNull
    static org.json.JSONObject makeJSONObject(@NonNull
                                              @Size(min = 1) final String name,
                                              final String value, final String hint,
                                              @NonNull final StringGetter stringGetter) {
        return new OptionalField.JSONObject(
                name, value, hint, stringGetter).jsonObject();
    }
    // endregion

    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    @NonNull
    static org.json.JSONObject makeJSONObject(@NonNull
                                              @Size(min = 1) final String name, final String value,
                                              final String hint, final boolean checked,
                                              @NonNull final StringGetter stringGetter) {
        return new OptionalField.JSONObject(
                name, value, hint, checked, stringGetter).jsonObject();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    static boolean getChecked(final org.json.JSONObject jsonObject,
                              @NonNull final StringGetter stringGetter) {
        if (null == jsonObject)
            return true;
        else
            return new OptionalField.JSONObject(
                    jsonObject, stringGetter).getChecked();
    }

    @SuppressWarnings({"DefaultAnnotationParam", "SameParameterValue"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    static void putChecked(final org.json.JSONObject jsonObject, final boolean checked,
                           @NonNull final StringGetter stringGetter) {
        if (null != jsonObject)
            new OptionalField.JSONObject(
                    jsonObject, checked, stringGetter);
    }

    @NonNull
    org.json.JSONObject makeJSONObject(
            @NonNull final StringGetter stringGetter) {
        return OptionalField.makeJSONObject(
                this.getName(), this.getValue(), this.getHint(), this.getChecked(), stringGetter);
    }

    @SuppressWarnings({"ClassExplicitlyExtendsObject"})
    private static class JSONObject extends Object {
        private static final String VALUE_JSON_NAME = "value",
                CHECKED_JSON_NAME = "checked";

        // region Fields
        @NonNull
        private final org.json.JSONObject jsonObject;
        private final boolean nameIsIdentification;
        // endregion

        // region Constructors
        private JSONObject(@NonNull final org.json.JSONObject jsonObject,
                           @NonNull final StringGetter stringGetter) {
            super();

            this.jsonObject = jsonObject;
            this.nameIsIdentification = OptionalField.JSONObject.nameIsIdentification(this.jsonObject.optString(
                    OptionalField.NAME_JSON_NAME),
                    stringGetter);
        }

        private JSONObject(@NonNull @Size(min = 1) final String name, final String value, final String hint,
                           @NonNull final StringGetter stringGetter) {
            super();

            // noinspection ConstantConditions
            if (null == name)
                throw new AssertionError();
            else {
                this.jsonObject = new org.json.JSONObject();
                this.nameIsIdentification = OptionalField.JSONObject.nameIsIdentification(name, stringGetter);

                this.put(OptionalField.NAME_JSON_NAME,
                        name);
                this.put(OptionalField.JSONObject.VALUE_JSON_NAME,
                        value);
                this.put(OptionalField.HINT_JSON_NAME,
                        hint);
            }
        }

        private JSONObject(@NonNull @Size(min = 1) final String name, final String value, final String hint,
                           final boolean checked,
                           @NonNull final StringGetter stringGetter) {
            this(name, value, hint, stringGetter);
            this.putChecked(checked);
        }
        // endregion

        private JSONObject(@NonNull final org.json.JSONObject jsonObject,
                           final boolean checked,
                           @NonNull final StringGetter stringGetter) {
            this(jsonObject, stringGetter);
            this.putChecked(checked);
        }

        // region Private Methods
        private static boolean nameIsIdentification(@NonNull
                                                    @Size(min = 1) final String name,
                                                    @NonNull final StringGetter stringGetter) {
            return name.equals(BaseOptionalField.identificationFieldName(stringGetter));
        }

        private void put(@Size(min = 1)
                         @NonNull final String name,
                         @Nullable String value) {
            if (null != value) {
                value = value.trim();
                if (value.length() > 0)
                    try {
                        this.jsonObject.put(name, value) /* throws org.json.JSONException */;
                    } catch (final JSONException e) { /* Don't put value. */ }
            }
        }

        private void putChecked(final boolean checked) {
            try {
                this.jsonObject.put(// throws org.json.JSONException
                        OptionalField.JSONObject.CHECKED_JSON_NAME,
                        this.nameIsIdentification || checked);
            } catch (final JSONException e) { /* Don't put checked. */ }
        }
        // endregion

        private String getValue() {
            return this.jsonObject.optString(OptionalField.JSONObject.VALUE_JSON_NAME);
        }

        private boolean getChecked() {
            try {
                return this.jsonObject.getBoolean(// throws org.json.JSONException
                        OptionalField.JSONObject.CHECKED_JSON_NAME);
            } catch (final JSONException e) {
                return true;
            }
        }

        @NonNull
        private org.json.JSONObject jsonObject() {
            return this.jsonObject;
        }
    }
    // endregion
}