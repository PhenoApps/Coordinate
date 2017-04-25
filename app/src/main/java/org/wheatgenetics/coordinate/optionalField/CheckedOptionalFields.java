package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 */

public class CheckedOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
{
    // region Package Type
    static class Iterator extends org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
    {
        Iterator(final java.util.ArrayList<
        org.wheatgenetics.coordinate.optionalField.OptionalField> arrayList) { super(arrayList); }


        @Override
        public boolean hasNext()
        {
            assert this.listIterator != null;
            assert this.arrayList    != null;
            while (super.hasNext())
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField =
                    this.arrayList.get(this.listIterator.nextIndex());
                assert optionalField != null;
                if (optionalField.getChecked())
                    return true;
                else
                    super.next();
            }
            return false;
        }

        @Override
        public org.wheatgenetics.coordinate.optionalField.OptionalField next()
        {
            org.wheatgenetics.coordinate.optionalField.OptionalField optionalField;

            do
                optionalField = super.next();
            while (!optionalField.getChecked());
            return optionalField;
        }
    }
    // endregion


    // region Public Methods
    // region Public Constructor Method
    public CheckedOptionalFields(
    final org.wheatgenetics.coordinate.optionalField.OptionalFields optionalFields)
    {
        super();

        assert optionalFields != null;
        this.arrayList.addAll(optionalFields.arrayList);
    }
    // endregion


    // region Public Overridden Method
    @Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        return new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields.Iterator(
            this.arrayList);
    }
    // endregion
    // endregion
}