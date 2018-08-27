package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateModels extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.TemplateModel>
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>
        arrayListInstance = null;

    // region Private Methods
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList()
    {
        if (null == this.arrayListInstance) this.arrayListInstance =
            new java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>();
        return this.arrayListInstance;
    }

    private boolean isInRange(final int i)
    {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @java.lang.Override @android.support.annotation.NonNull
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

            // region Overridden Methods
            @java.lang.Override public boolean hasNext() { return this.listIterator.hasNext(); }

            @java.lang.Override public org.wheatgenetics.coordinate.model.TemplateModel next()
            { return this.listIterator.next(); }

            @java.lang.Override public void remove()
            { throw new java.lang.UnsupportedOperationException(); }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    // region Public Methods
    public void add(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) this.arrayList().add(templateModel); }

    public int size() { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    public org.wheatgenetics.coordinate.model.TemplateModel get(final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

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
                for (int i = first; i <= last; i++) result[i] = this.get(i).getTitle();
            }
            return result;
        }
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