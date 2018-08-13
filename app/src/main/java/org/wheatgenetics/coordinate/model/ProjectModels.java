package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectModels extends java.lang.Object
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.ProjectModel>
        arrayListInstance = null;

    // region Private Methods
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.ProjectModel> arrayList()
    {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
                new java.util.ArrayList<org.wheatgenetics.coordinate.model.ProjectModel>();
        return this.arrayListInstance;
    }

    private boolean isInRange(final int i)
    {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
            false : i < this.arrayListInstance.size();
    }
    // endregion

    // region Public Methods
    public boolean add(final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        // noinspection SimplifiableConditionalExpression
        return null == projectModel ? false : this.arrayList().add(projectModel);
    }

    public int size() { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    public org.wheatgenetics.coordinate.model.ProjectModel get(final int i)
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
    // endregion
}