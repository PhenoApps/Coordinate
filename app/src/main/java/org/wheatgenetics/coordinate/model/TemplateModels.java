package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.util.Log
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
    @java.lang.SuppressWarnings("Convert2Diamond")
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList()
    {
        if (null == this.arrayListInstance) this.arrayListInstance =
            new java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>();
        return this.arrayListInstance;
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    private boolean isInRange(final int i)
    {
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }

    private static int logInfo(final java.lang.String msg)
    { return android.util.Log.i("TemplateModels", msg); }
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

            private Iterator(
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
    public boolean add(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { return null == templateModel ? false : this.arrayList().add(templateModel); }

    public int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    public java.lang.String[] titles()
    {
        final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            final java.lang.String result[] = new java.lang.String[size];
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++)
                    result[i] = this.arrayListInstance.get(i).getTitle();
            }
            return result;
        }
    }

    public org.wheatgenetics.coordinate.model.TemplateModel get(final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    public void logInfo()
    {
        if (null == this.arrayListInstance)
            org.wheatgenetics.coordinate.model.TemplateModels.logInfo(
                "null == this.arrayListInstance");
        else
            if (this.size() <= 0)
                org.wheatgenetics.coordinate.model.TemplateModels.logInfo("this.size() <= 0");
            else
                for (final org.wheatgenetics.coordinate.model.TemplateModel templateModel: this)
                    org.wheatgenetics.coordinate.model.TemplateModels.logInfo(
                        templateModel.toString());
    }

    public static org.wheatgenetics.coordinate.model.TemplateModels makeDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels result =
            new org.wheatgenetics.coordinate.model.TemplateModels();

        // It matters that the seed tray default template is first and the DNA plate default
        // template is second.  Client code assumes the templates are present in the order just
        // described.  If these two default templates are not present (and not present in the order
        // just described), client code will break.
        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault());
        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault ());

        return result;
    }
    // endregion
}