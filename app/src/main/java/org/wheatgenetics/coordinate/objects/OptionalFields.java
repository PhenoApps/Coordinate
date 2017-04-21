package org.wheatgenetics.coordinate.objects;

abstract class OptionalFields extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.objects.OptionalField>
{
    // region Package Type
    static class Iterator extends java.lang.Object
    implements java.util.Iterator<org.wheatgenetics.coordinate.objects.OptionalField>
    {
        java.util.ArrayList   <org.wheatgenetics.coordinate.objects.OptionalField> arrayList   ;
        java.util.ListIterator<org.wheatgenetics.coordinate.objects.OptionalField> listIterator;


        Iterator(final java.util.ArrayList<
        org.wheatgenetics.coordinate.objects.OptionalField> arrayList)
        {
            super();

            assert arrayList != null;
            this.arrayList = arrayList;

            final java.util.ListIterator<org.wheatgenetics.coordinate.objects.OptionalField>
                listIterator = this.arrayList.listIterator();
            assert listIterator != null;
            this.listIterator = listIterator;
        }


        @Override
        public boolean hasNext()
        {
            assert this.listIterator != null;
            assert this.arrayList    != null;
            while (this.listIterator.hasNext())
            {
                final org.wheatgenetics.coordinate.objects.OptionalField optionalField =
                    this.arrayList.get(this.listIterator.nextIndex());
                if (optionalField != null)
                    return true;
                else
                    this.listIterator.next();
            }
            return false;
        }

        @Override
        public org.wheatgenetics.coordinate.objects.OptionalField next()
        {
            org.wheatgenetics.coordinate.objects.OptionalField optionalField;

            assert this.listIterator != null;
            do
                optionalField = this.listIterator.next();
            while (optionalField == null);
            return optionalField;
        }

        @Override
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }
    // endregion


    // region Package Field
    java.util.ArrayList<org.wheatgenetics.coordinate.objects.OptionalField> arrayList =
        new java.util.ArrayList<org.wheatgenetics.coordinate.objects.OptionalField>();
    // endregion


    // region Public java.lang.Iterable<> Method
    @Override
    public org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator()
    {
        return new org.wheatgenetics.coordinate.objects.OptionalFields.Iterator(this.arrayList);
    }
    // endregion
}