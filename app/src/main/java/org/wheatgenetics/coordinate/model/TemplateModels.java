package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class TemplateModels extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.TemplateModel>
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>
        arrayListInstance = null;

    // region Private Methods
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList()
    {
        if (null == this.arrayListInstance) this.arrayListInstance =
            new java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>();
        return this.arrayListInstance;
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    private boolean isInRange(final int i)
    { return i < 0 ? false : null == this.arrayListInstance ? false : i < this.arrayList().size(); }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override
    public java.util.Iterator<org.wheatgenetics.coordinate.model.TemplateModel> iterator()
    {
        class Iterator extends java.lang.Object
        implements java.util.Iterator<org.wheatgenetics.coordinate.model.TemplateModel>
        {
            private final java.util.ListIterator<org.wheatgenetics.coordinate.model.TemplateModel>
                listIterator;

            private Iterator(@android.support.annotation.NonNull
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList)
            { super(); assert null != arrayList; this.listIterator = arrayList.listIterator(); }

            @java.lang.Override
            public boolean hasNext()
            { assert null != this.listIterator; return this.listIterator.hasNext(); }

            @java.lang.Override
            public org.wheatgenetics.coordinate.model.TemplateModel next()
            { assert null != this.listIterator; return this.listIterator.next(); }

            @java.lang.Override
            public void remove() { throw new java.lang.UnsupportedOperationException(); }
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    // region Public Methods
    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean add(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { return null == templateModel ? false : this.arrayList().add(templateModel); }

    public int size() { return null == this.arrayListInstance ? 0 : this.arrayList().size(); }

    public java.lang.String[] titles()
    {
        if (null == this.arrayListInstance)
            return null;
        else
        {
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList =
                this.arrayList();
            final int size = arrayList.size();

            if (size <= 0)
                return null;
            else
            {
                final java.lang.String result[] = new java.lang.String[size];
                {
                    final int first = 0, last = size - 1;
                    for (int i = first; i <= last; i++) result[i] = arrayList.get(i).getTitle();
                }
                return result;
            }
        }
    }

    public org.wheatgenetics.coordinate.model.TemplateModel get(final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayList().get(i) : null;
    }

    public static org.wheatgenetics.coordinate.model.TemplateModels makeDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels result =
            new org.wheatgenetics.coordinate.model.TemplateModels();

        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault());
        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault ());

        return result;
    }
    // endregion
}