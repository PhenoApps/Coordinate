package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import org.json.JSONObject;
import org.wheatgenetics.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

@VisibleForTesting(
        otherwise = VisibleForTesting.PACKAGE_PRIVATE)
public class DateOptionalField extends OptionalField {
    // region Constructors
    DateOptionalField(@NonNull final StringGetter stringGetter) {
        // noinspection ConstantConditions
        super(
                /* name => */ null == stringGetter.get(
                        R.string.DateOptionalFieldDateFieldName) ? "Date" :
                        stringGetter.get(
                                R.string.DateOptionalFieldDateFieldName),
                /* hint => */ BaseOptionalField.DATE_HINT,
                /* stringGetter => */ stringGetter);
    }

    DateOptionalField(final JSONObject jsonObject,
                      @NonNull final StringGetter stringGetter) {
        this(stringGetter);
        this.setChecked(OptionalField.getChecked(
                jsonObject, stringGetter));
    }
    // endregion

    @VisibleForTesting(
            otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    @NonNull
    public static String getCurrentDate() {
        return Utils.formatDate(
                System.currentTimeMillis()).toString();
    }

    // region Overridden Methods
    @Override
    @NonNull
    public Object clone() {
        final DateOptionalField result =
                new DateOptionalField(this.stringGetter());
        result.setValue(this.getValue());
        result.setChecked(this.getChecked());
        return result;
    }
    // endregion

    @Override
    @NonNull
    public String getValue() {
        return DateOptionalField.getCurrentDate();
    }
}