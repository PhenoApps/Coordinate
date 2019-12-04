package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.VisibleForTesting
 *
 * org.json.JSONObject
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
@androidx.annotation.VisibleForTesting(
    otherwise = androidx.annotation.VisibleForTesting.PACKAGE_PRIVATE)
public class DateOptionalField extends org.wheatgenetics.coordinate.optionalField.OptionalField
{
    // region Constructors
    DateOptionalField()
    {
        super(
            /* name => */"Date",
            /* hint => */ org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT);
    }

    DateOptionalField(final org.json.JSONObject jsonObject)
    {
        this(); this.setChecked(org.wheatgenetics.coordinate.optionalField.OptionalField.getChecked(
            jsonObject));
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.DateOptionalField result =
            new org.wheatgenetics.coordinate.optionalField.DateOptionalField();
        result.setValue(this.getValue()); result.setChecked(this.getChecked());
        return result;
    }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getValue()
    { return org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate(); }
    // endregion

    @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PACKAGE_PRIVATE)
    @androidx.annotation.NonNull public static java.lang.String getCurrentDate()
    {
        return org.wheatgenetics.androidlibrary.Utils.formatDate(
            java.lang.System.currentTimeMillis()).toString();
    }
}