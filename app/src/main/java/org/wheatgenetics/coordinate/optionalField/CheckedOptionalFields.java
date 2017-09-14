package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 */
public class CheckedOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
{
    public CheckedOptionalFields(
    final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields)
    {
        super();

        assert null != optionalFields; assert null != this.arrayList;
        this.arrayList.addAll(optionalFields.arrayList);
    }

    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        // Iterates over checked optional fields.
        class Iterator extends org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
        {
            private Iterator(final java.util.ArrayList<
            org.wheatgenetics.coordinate.optionalField.OptionalField> arrayList)
            { super(arrayList); }

            @java.lang.Override
            public boolean hasNext()
            {
                assert null != this.listIterator; assert null != this.arrayList;
                while (super.hasNext())
                {
                    final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField =
                        this.arrayList.get(this.listIterator.nextIndex());

                    assert null != optionalField;
                    if (optionalField.getChecked()) return true; else super.next();
                }
                return false;
            }

            @java.lang.Override
            public org.wheatgenetics.coordinate.optionalField.OptionalField next()
            {
                org.wheatgenetics.coordinate.optionalField.OptionalField result;
                do result = super.next(); while (!result.getChecked());
                return result;
            }
        }
        return new Iterator(this.arrayList);
    }

    public int size() { return null == this.arrayList ? 0 : this.arrayList.size(); }
}