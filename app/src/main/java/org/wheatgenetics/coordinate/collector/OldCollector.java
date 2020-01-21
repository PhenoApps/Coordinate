package org.wheatgenetics.coordinate.collector;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.collector.BaseCollector
 */
public class OldCollector extends org.wheatgenetics.coordinate.collector.BaseCollector
{
    @androidx.annotation.NonNull
    private final org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences;

    public OldCollector(
    @androidx.annotation.NonNull final androidx.appcompat.app.AppCompatActivity            activity,
    @androidx.annotation.NonNull final org.wheatgenetics.sharedpreferences.SharedPreferences
        sharedPreferences) { super(activity); this.sharedPreferences = sharedPreferences; }

    @java.lang.Override
    public void loadJoinedGridModel(@androidx.annotation.IntRange(from = 0) final long gridId)
    {
        super.loadJoinedGridModel(gridId);

        if (this.joinedGridModelIsLoaded())
            sharedPreferences.setGridId(this.joinedGridModel.getId());
        else
            sharedPreferences.clearGridId();
    }

    // region Public Methods
    // region joinedGridModel() Public Methods
    @androidx.annotation.NonNull public java.lang.String getPerson()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getPerson() : ""; }

    public long getGridId()
    { return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getId() : 0; }
    // endregion

    // region menuItemShouldBeEnabled() Public Methods
    public boolean manageGridMenuItemShouldBeEnabled()
    {
        final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
        // noinspection SimplifiableConditionalExpression
        return null == gridsTable ? false : gridsTable.exists();
    }

    public boolean manageProjectMenuItemShouldBeEnabled() { return this.projectsTable().exists(); }

    public boolean exportProjectMenuItemShouldBeEnabled()
    {
        if (this.projectsTable().exists())
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable =
                this.gridsTable();
            // noinspection SimplifiableConditionalExpression
            return null == gridsTable ? false : gridsTable.existsInProject();
        }
        else return false;
    }
    // endregion

    public void clearJoinedGridModelThenPopulate()
    { this.loadJoinedGridModelThenPopulate(0); }

    // region Project Public Methods
    @androidx.annotation.Nullable public org.wheatgenetics.coordinate.model.ProjectModel
    getProjectModel(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        return org.wheatgenetics.coordinate.model.Model.illegal(projectId) ?
            null : this.projectsTable().get(projectId);
    }

    public boolean projectExists(@androidx.annotation.IntRange(from = 1) final long projectId)
    { return this.projectsTable().exists(projectId); }
    // endregion

    public void handleGridDeleted()
    {
        if (this.joinedGridModelIsLoaded())
        {
            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            if (null != gridsTable) if (!gridsTable.exists(this.joinedGridModel.getId()))
                this.clearJoinedGridModelThenPopulate();
        }
    }
    // endregion
}