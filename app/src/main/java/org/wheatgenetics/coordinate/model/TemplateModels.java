package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateModels extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.coordinate.model.TemplateModel>
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel>
        arrayListInstance = null;                                                       // lazy load

    // region Private Methods
    @androidx.annotation.NonNull
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList()
    {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
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
    @java.lang.Override @androidx.annotation.NonNull
    public java.util.Iterator<org.wheatgenetics.coordinate.model.TemplateModel> iterator()
    {
        class Iterator extends java.lang.Object
        implements java.util.Iterator<org.wheatgenetics.coordinate.model.TemplateModel>
        {
            @androidx.annotation.NonNull private final
                java.util.ListIterator<org.wheatgenetics.coordinate.model.TemplateModel>
                listIterator;

            private Iterator(@androidx.annotation.NonNull
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList)
            { super(); this.listIterator = arrayList.listIterator(); }

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

    @androidx.annotation.IntRange(from = 0) public int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    @androidx.annotation.Nullable public org.wheatgenetics.coordinate.model.TemplateModel get(
    @androidx.annotation.IntRange(from = 0) final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @androidx.annotation.Nullable public java.lang.String[] titles()
    {
        final int size = this.size();

        if (size <= 0)
            return null;
        else
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String result[] = new java.lang.String[size];
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++)
                {
                    final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                        this.get(i);
                    if (null != templateModel) result[i] = templateModel.getTitle();
                }
            }
            return result;
        }
    }

    @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.model.TemplateModels makeDefault(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels result =
            new org.wheatgenetics.coordinate.model.TemplateModels();

        // It matters that the seed tray default template is first and the DNA plate default
        // template is second.  Client code assumes the templates are present in the order just
        // described.  If these two default templates are not present (and not present in the order
        // just described), client code will break.
        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeSeedDefault(stringGetter));
        result.add(org.wheatgenetics.coordinate.model.TemplateModel.makeDNADefault (stringGetter));

        return result;
    }
    // endregion
}