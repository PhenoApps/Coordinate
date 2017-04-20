package org.wheatgenetics.coordinate.objects;

public class OptionalFields extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.objects.OptionalField>
{
    // region Public Type
    static public class Iterator extends java.lang.Object
    implements java.util.Iterator<org.wheatgenetics.coordinate.objects.OptionalField>
    {
        protected java.util.ArrayList<org.wheatgenetics.coordinate.objects.OptionalField> arrayList;
        protected java.util.ListIterator<
            org.wheatgenetics.coordinate.objects.OptionalField> listIterator;


        protected Iterator(final java.util.ArrayList<
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


    // region Protected Field
    protected java.util.ArrayList<org.wheatgenetics.coordinate.objects.OptionalField> arrayList =
        new java.util.ArrayList<org.wheatgenetics.coordinate.objects.OptionalField>();
    // endregion


    // region Public Methods
    // region Public java.lang.Iterable<> Method
    @Override
    public org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator()
    {
        return new org.wheatgenetics.coordinate.objects.OptionalFields.Iterator(this.arrayList);
    }
    // endregion


    // region Public Add Methods
    public boolean add(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(new org.wheatgenetics.coordinate.objects.OptionalField(name));
    }

    public boolean add(final java.lang.String name, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OptionalField(name, hint));
    }

    public boolean add(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        assert this.arrayList != null;
        return this.arrayList.add(
            new org.wheatgenetics.coordinate.objects.OptionalField(name, value, hint));
    }

    public boolean add(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        org.wheatgenetics.coordinate.objects.OptionalField optionalField;

        try
        {
            optionalField = new org.wheatgenetics.coordinate.objects.OptionalField(jsonObject);
        }
        catch (org.wheatgenetics.coordinate.objects.OptionalField.WrongClass wrongClass)
        {
            optionalField = new org.wheatgenetics.coordinate.objects.DateOptionalField(jsonObject);
        }

        assert this.arrayList != null;
        return this.arrayList.add(optionalField);
    }

    public boolean addDate(final java.lang.String name)
    {
        assert this.arrayList != null;
        return this.arrayList.add(new org.wheatgenetics.coordinate.objects.DateOptionalField(name));
    }
    // endregion


    public int size()
    {
        final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
            this.iterator();                                                         // polymorphism
        int size = 0;

        assert iterator != null;
        while (iterator.hasNext())
        {
            size++;
            iterator.next();
        }
        return size;
    }


    public org.wheatgenetics.coordinate.objects.OptionalField get(final int index)
    {
        if (index < 0)
            throw new java.lang.IndexOutOfBoundsException();
        else
            if (index >= this.size())
                throw new java.lang.IndexOutOfBoundsException();
            else
            {
                org.wheatgenetics.coordinate.objects.OptionalField optionalField;
                {
                    final org.wheatgenetics.coordinate.objects.OptionalFields.Iterator iterator =
                        this.iterator();                                             // polymorphism
                    int i = 0;

                    assert iterator != null;
                    do
                        optionalField = iterator.next();
                    while (i++ < index);
                }
                return optionalField;
            }
    }
    // endregion
}