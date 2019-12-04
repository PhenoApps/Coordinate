package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class ProjectModels extends java.lang.Object
{
    private java.util.ArrayList<org.wheatgenetics.coordinate.model.ProjectModel>
        arrayListInstance = null;                                                       // lazy load

    // region Private Methods
    @androidx.annotation.NonNull
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

    @androidx.annotation.IntRange(from = 0) public int size()
    { return null == this.arrayListInstance ? 0 : this.arrayListInstance.size(); }

    @androidx.annotation.Nullable public org.wheatgenetics.coordinate.model.ProjectModel get(
    @androidx.annotation.IntRange(from = 0) final int i)
    {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @androidx.annotation.Nullable public java.lang.String[] titles()
    {
        @androidx.annotation.IntRange(from = 0) final int size = this.size();

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