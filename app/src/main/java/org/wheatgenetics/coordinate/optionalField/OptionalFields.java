package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */

abstract class OptionalFields extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.optionalField.OptionalField>
{
    // region Package Type
    static class Iterator extends java.lang.Object
    implements java.util.Iterator<org.wheatgenetics.coordinate.optionalField.OptionalField>
    {
        final java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.OptionalField>
            arrayList;
        final java.util.ListIterator<org.wheatgenetics.coordinate.optionalField.OptionalField>
            listIterator;

        Iterator(final java.util.ArrayList<
        org.wheatgenetics.coordinate.optionalField.OptionalField> arrayList)
        {
            super();

            assert null != arrayList;
            this.arrayList = arrayList;

            final java.util.ListIterator<org.wheatgenetics.coordinate.optionalField.OptionalField>
                listIterator = this.arrayList.listIterator();
            assert null != listIterator;
            this.listIterator = listIterator;
        }

        @java.lang.Override
        public boolean hasNext()
        {
            assert null != this.listIterator;
            assert null != this.arrayList   ;
            while (this.listIterator.hasNext())
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField =
                    this.arrayList.get(this.listIterator.nextIndex());
                if (null != optionalField) return true; else this.listIterator.next();
            }
            return false;
        }

        @java.lang.Override
        public org.wheatgenetics.coordinate.optionalField.OptionalField next()
        {
            org.wheatgenetics.coordinate.optionalField.OptionalField optionalField;

            assert null != this.listIterator;
            do
                optionalField = this.listIterator.next();
            while (null == optionalField);
            return optionalField;
        }

        @java.lang.Override
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }
    // endregion

    // region Package Field
    java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.OptionalField> arrayList =
        new java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.OptionalField>();
    // endregion

    // region Public java.lang.Iterable<> Method
    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        return
            new org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator(this.arrayList);
    }
    // endregion
}