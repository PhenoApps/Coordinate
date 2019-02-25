package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */
public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String GRID_ID = "CurrentGrid", PROJECT_ID = "CurrentProject";

    // region Private Methods
    private void uncheckedSetGridId(
    @android.support.annotation.IntRange(from = -1) final long gridId)
    { this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.GRID_ID, gridId); }

    private void uncheckedSetProjectId(
    @android.support.annotation.IntRange(from = -1) final long projectId)
    { this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.PROJECT_ID, projectId); }
    // endregion

    public SharedPreferences(@android.support.annotation.NonNull
    final android.content.SharedPreferences sharedPreferences) { super(sharedPreferences); }

    // region Public Methods
    // region GridId Public Methods
    public long getGridId()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.GRID_ID); }

    public void setGridId(@android.support.annotation.IntRange(from = 1) final long gridId)
    { this.uncheckedSetGridId(org.wheatgenetics.coordinate.model.Model.valid(gridId)); }

    public void clearGridId() { this.uncheckedSetGridId(-1); }

    public boolean gridIdIsSet() { return this.getGridId() > -1; }
    // endregion

    // region ProjectId Public Methods
    public long getProjectId()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.PROJECT_ID); }

    public void setProjectId(@android.support.annotation.IntRange(from = 1) final long projectId)
    { this.uncheckedSetProjectId(org.wheatgenetics.coordinate.model.Model.valid(projectId)); }

    public void clearProjectId() { this.uncheckedSetProjectId(-1); }

    public boolean projectIdIsSet() { return this.getProjectId() > -1; }
    // endregion
    // endregion
}