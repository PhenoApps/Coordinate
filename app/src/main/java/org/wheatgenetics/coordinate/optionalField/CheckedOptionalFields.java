package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
 */
public class CheckedOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
{
    public CheckedOptionalFields(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields)
    { super(); if (null != optionalFields) this.arrayList.addAll(optionalFields.arrayList); }

    @java.lang.Override @androidx.annotation.NonNull
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        // Only iterates over checked optional fields.
        class Iterator extends org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
        {
            private Iterator(@androidx.annotation.NonNull final java.util.ArrayList<
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField> arrayList)
            { super(arrayList); }

            // region Overridden Methods
            @java.lang.Override public boolean hasNext()
            {
                while (super.hasNext())
                {
                    final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                        baseOptionalField = this.arrayList.get(this.listIterator.nextIndex());
                    if (baseOptionalField.getChecked()) return true; else super.next();
                }
                return false;
            }

            @java.lang.Override
            public org.wheatgenetics.coordinate.optionalField.BaseOptionalField next()
            {
                org.wheatgenetics.coordinate.optionalField.BaseOptionalField result;
                do result = super.next(); while (!result.getChecked());
                return result;
            }
            // endregion
        }
        return new Iterator(this.arrayList);
    }

    @androidx.annotation.IntRange(from = 0) public int size()
    {
        @androidx.annotation.IntRange(from = 0) int result = 0;

        // noinspection UnusedParameters
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            result++;

        return result;
    }
}