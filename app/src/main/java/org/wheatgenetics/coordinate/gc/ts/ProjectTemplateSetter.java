package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.gc.ts.TemplateSetter
 */
public class ProjectTemplateSetter extends org.wheatgenetics.coordinate.gc.ts.TemplateSetter
{
    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    public ProjectTemplateSetter(final android.app.Activity activity) { super(activity); }

    @androidx.annotation.IntRange(from = 0) public long set(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.gridsTable().loadByProjectId(projectId);
        if (null != baseJoinedGridModels && baseJoinedGridModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                baseJoinedGridModels.get(0);
            return null == joinedGridModel ? 0 :joinedGridModel.getTemplateId();
        }
        else return 0;
    }
}