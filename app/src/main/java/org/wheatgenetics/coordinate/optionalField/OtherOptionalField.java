package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import org.json.JSONObject;
import org.wheatgenetics.coordinate.StringGetter;

class OtherOptionalField extends OptionalField {
    // region Constructors
    private OtherOptionalField(@NonNull @Size(min = 1) final String name, final String hint, @NonNull final
    StringGetter stringGetter) {
        super(name, hint, stringGetter);
    }

    OtherOptionalField(@NonNull @Size(min = 1) final String name, @NonNull final StringGetter stringGetter) {
        super(name, stringGetter);
    }

    OtherOptionalField(@NonNull @Size(min = 1) final String name, final String value, final String hint,
                       @NonNull final StringGetter stringGetter) {
        this(name, hint, stringGetter);
        this.setValue(value);
    }

    OtherOptionalField(@NonNull final JSONObject jsonObject,
                       @NonNull final StringGetter stringGetter)
            throws OtherOptionalField.WrongClass {
        super(jsonObject, stringGetter);

        if (BaseOptionalField.TIMESTAMP_HINT.equals(
                this.getHint()))
            throw new OtherOptionalField.WrongClass();
    }

    @Override
    @NonNull
    public Object clone() {
        final OtherOptionalField result =
                new OtherOptionalField(
                        this.getName(), this.getValue(), this.getHint(), this.stringGetter());
        result.setChecked(this.getChecked());
        return result;
    }
    // endregion

    static class WrongClass extends Exception {
        WrongClass() {
            super();
        }
    }
}