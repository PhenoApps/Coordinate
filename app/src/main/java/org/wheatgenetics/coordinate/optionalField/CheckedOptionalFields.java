package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
 */
public class CheckedOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
{
    public CheckedOptionalFields(
    final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields)
    {
        super();

        if (null != optionalFields)
        { assert null != this.arrayList; this.arrayList.addAll(optionalFields.arrayList); }
    }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        // Only iterates over checked optional fields.
        class Iterator extends org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
        {
            private Iterator(final java.util.ArrayList<
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField> arrayList)
            { super(arrayList); }

            @java.lang.Override
            public boolean hasNext()
            {
                assert null != this.listIterator; assert null != this.arrayList;
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
        }
        return new Iterator(this.arrayList);
    }

    public int size()
    {
        if (null == this.arrayList)
            return 0;
        else
        {
            int result = 0;
            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: this)
                result++;
            return result;
        }
    }
}