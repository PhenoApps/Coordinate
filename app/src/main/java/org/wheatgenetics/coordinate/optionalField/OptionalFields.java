package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */

abstract class OptionalFields extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.optionalField.OptionalField>
{
    /**
     * Iterates over non-null optional fields.
     */
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
                if (null == optionalField) this.listIterator.next(); else return true;
            }
            return false;
        }

        @java.lang.Override
        public org.wheatgenetics.coordinate.optionalField.OptionalField next()
        {
            org.wheatgenetics.coordinate.optionalField.OptionalField optionalField;

            assert null != this.listIterator;
            do optionalField = this.listIterator.next(); while (null == optionalField);
            return optionalField;
        }

        @java.lang.Override
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }

    java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.OptionalField> arrayList =
        new java.util.ArrayList<org.wheatgenetics.coordinate.optionalField.OptionalField>();

    // region Overridden Methods
    @java.lang.Override
    public boolean equals(final java.lang.Object o)
    {
        if (null == o)
            return false;
        else
            if (o instanceof org.wheatgenetics.coordinate.optionalField.OptionalFields)
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalFields f =
                    (org.wheatgenetics.coordinate.optionalField.OptionalFields) o;

                assert null != this.arrayList;
                assert null != f.arrayList   ;
                if (this.arrayList.size() != f.arrayList.size())
                    return false;
                else
                {
                    int i = 0;

                    for (final org.wheatgenetics.coordinate.optionalField.OptionalField
                    optionalField: this)
                        if (!optionalField.equals(f.arrayList.get(i++))) return false;

                    return true;
                }
            }
            else return false;
    }

    @java.lang.Override
    public int hashCode()
    {
        java.lang.String signatures = null;
        {
            boolean firstOptionalField = true;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField: this)
            {
                java.lang.String signature;
                try { signature = optionalField.makeJSONObject().toString(); }
                catch (final org.json.JSONException e) { continue; }

                if (firstOptionalField)
                    signatures = signature;
                else
                {
                    signatures += '\n' + signature;
                    firstOptionalField = false;
                }
            }
        }
        return org.wheatgenetics.javalib.Utils.makeEmptyIfNull(signatures).hashCode();
    }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override
    public org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator()
    {
        return
            new org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator(this.arrayList);
    }
    // endregion
}