package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import org.json.JSONObject;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@VisibleForTesting(
        otherwise = VisibleForTesting.PACKAGE_PRIVATE)
public class TimestampOptionalField extends OptionalField {
    // region Constructors
    TimestampOptionalField(@NonNull final StringGetter stringGetter) {
        // noinspection ConstantConditions
        super(
                /* name => */ null == stringGetter.get(
                        R.string.TimestampOptionalFieldDateFieldName) ? "Timestamp" :
                        stringGetter.get(
                                R.string.TimestampOptionalFieldDateFieldName),
                /* hint => */ BaseOptionalField.TIMESTAMP_HINT,
                /* stringGetter => */ stringGetter);
    }

    TimestampOptionalField(final JSONObject jsonObject,
                           @NonNull final StringGetter stringGetter) {
        this(stringGetter);
        this.setChecked(OptionalField.getChecked(
                jsonObject, stringGetter));
    }
    // endregion

    @VisibleForTesting(
            otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    @NonNull
    public static String getCurrentTimestamp() {
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.getDefault());
        return timeStamp.format(Calendar.getInstance().getTime());
    }

    // region Overridden Methods
    @Override
    @NonNull
    public Object clone() {
        final TimestampOptionalField result =
                new TimestampOptionalField(this.stringGetter());
        result.setValue(this.getValue());
        result.setChecked(this.getChecked());
        return result;
    }
    // endregion

    @Override
    @NonNull
    public String getValue() {
        return TimestampOptionalField.getCurrentTimestamp();
    }
}