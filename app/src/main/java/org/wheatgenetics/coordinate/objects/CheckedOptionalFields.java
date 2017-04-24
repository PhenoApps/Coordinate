package org.wheatgenetics.coordinate.objects;

/**
 * Uses:
 * org.wheatgenetics.coordinate.objects.OptionalField
 * org.wheatgenetics.coordinate.objects.OptionalFields
 */

public class CheckedOptionalFields extends org.wheatgenetics.coordinate.objects.OptionalFields
{
    // region Package Type
    static class Iterator extends org.wheatgenetics.coordinate.objects.OptionalFields.Iterator
    {
        Iterator(final java.util.ArrayList<
        org.wheatgenetics.coordinate.objects.OptionalField> arrayList) { super(arrayList); }


        @Override
        public boolean hasNext()
        {
            assert this.listIterator != null;
            assert this.arrayList    != null;
            while (super.hasNext())
            {
                final org.wheatgenetics.coordinate.objects.OptionalField optionalField =
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
        public org.wheatgenetics.coordinate.objects.OptionalField next()
        {
            org.wheatgenetics.coordinate.objects.OptionalField optionalField;

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
    final org.wheatgenetics.coordinate.objects.OptionalFields optionalFields)
    {
        super();

        assert optionalFields != null;
        this.arrayList.addAll(optionalFields.arrayList);
    }
    // endregion


    // region Public Overridden Method
    @Override
    public org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator()
    {
        return
            new org.wheatgenetics.coordinate.objects.CheckedOptionalFields.Iterator(this.arrayList);
    }
    // endregion
    // endregion
}