package org.wheatgenetics.sharedpreferences;

/**
 * Uses:
 * android.content.SharedPreferences
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
 */
public class SharedPreferences
extends org.wheatgenetics.sharedpreferences.UpdateVersionSharedPreferences
{
    private static final java.lang.String GRID_ID = "CurrentGrid", PROJECT_ID = "CurrentProject";

    @androidx.annotation.NonNull
    private final org.wheatgenetics.coordinate.StringGetter stringGetter;

    // region Private Methods
    private void uncheckedSetGridId(@androidx.annotation.IntRange(from = -1) final long gridId)
    { this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.GRID_ID, gridId); }

    private void uncheckedSetProjectId(
    @androidx.annotation.IntRange(from = -1) final long projectId)
    { this.setLong(org.wheatgenetics.sharedpreferences.SharedPreferences.PROJECT_ID, projectId); }
    // endregion

    public SharedPreferences(
    @androidx.annotation.NonNull final android.content.SharedPreferences         sharedPreferences,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter     )
    { super(sharedPreferences); this.stringGetter = stringGetter; }

    // region Public Methods
    // region GridId Public Methods
    public long getGridId()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.GRID_ID); }

    public void setGridId(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.uncheckedSetGridId(
            org.wheatgenetics.coordinate.model.Model.valid(gridId, this.stringGetter));
    }

    public void clearGridId() { this.uncheckedSetGridId(-1); }

    public boolean gridIdIsSet() { return this.getGridId() > -1; }
    // endregion

    // region ProjectId Public Methods
    public long getProjectId()
    { return this.getLong(org.wheatgenetics.sharedpreferences.SharedPreferences.PROJECT_ID); }

    public void setProjectId(@androidx.annotation.IntRange(from = 1) final long projectId)
    {
        this.uncheckedSetProjectId(
            org.wheatgenetics.coordinate.model.Model.valid(projectId, this.stringGetter));
    }

    public void clearProjectId() { this.uncheckedSetProjectId(-1); }

    public boolean projectIdIsSet() { return this.getProjectId() > -1; }
    // endregion
    // endregion
}