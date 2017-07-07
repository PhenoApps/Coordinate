package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */

public class TemplateModels extends java.lang.Object
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

    // region Public Methods
    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean add(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { return null == templateModel ? false : this.arrayList().add(templateModel); }

    public java.lang.String[] titles()
    {
        if (null == this.arrayListInstance)
            return null;
        else
        {
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel> arrayList =
                this.arrayList();
            final int size = arrayList.size();

            if (0 >= size)
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
    // endregion
}