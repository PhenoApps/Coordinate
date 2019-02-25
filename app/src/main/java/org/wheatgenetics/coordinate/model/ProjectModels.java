package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectModels extends java.lang.Object
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.ProjectModel>
        arrayListInstance = null;                                                       // lazy load

    // region Private Methods
    @android.support.annotation.NonNull
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
    public void add(final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { if (null != projectModel) this.arrayList().add(projectModel); }

    @android.support.annotation.IntRange(from = 0)
    public int size() { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    @android.support.annotation.Nullable public org.wheatgenetics.coordinate.model.ProjectModel get(
    @android.support.annotation.IntRange(from = 0) final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @android.support.annotation.Nullable public java.lang.String[] titles()
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
                {
                    final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                        this.get(i);
                    if (null != projectModel) result[i] = projectModel.getTitle();
                }
            }
            return result;
        }
    }
    // endregion
}