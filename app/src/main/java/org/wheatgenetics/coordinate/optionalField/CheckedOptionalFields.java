package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
 */
public class CheckedOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
{
    public CheckedOptionalFields(@android.support.annotation.Nullable
    final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields)
    { super(); if (null != optionalFields) this.arrayList.addAll(optionalFields.arrayList); }

    @android.support.annotation.NonNull @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        // Only iterates over checked optional fields.
        class Iterator extends org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
        {
            private Iterator(@android.support.annotation.NonNull final java.util.ArrayList<
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField> arrayList)
            { super(arrayList); }

            // region Overridden Methods
            @java.lang.Override public boolean hasNext()
            {
                while (super.hasNext())
                {
                    final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                        baseOptionalField = this.arrayList.get(this.listIterator.nextIndex());

                    assert null != baseOptionalField;
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

    @android.support.annotation.IntRange(from = 0) public int size()
    {
        @android.support.annotation.IntRange(from = 0) int result = 0;

        // noinspection UnusedParameters
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            result++;

        return result;
    }
}