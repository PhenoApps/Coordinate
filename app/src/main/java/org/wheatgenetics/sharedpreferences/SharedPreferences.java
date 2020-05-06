package org.wheatgenetics.sharedpreferences;

import org.wheatgenetics.coordinate.StringGetter;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.Model;

public class SharedPreferences extends UpdateVersionSharedPreferences {
    private static final String GRID_ID = "CurrentGrid", PROJECT_ID = "CurrentProject";

    @NonNull
    private final StringGetter stringGetter;

    public SharedPreferences(
            @NonNull final android.content.SharedPreferences sharedPreferences,
            @NonNull final StringGetter stringGetter) {
        super(sharedPreferences);
        this.stringGetter = stringGetter;
    }

    // region Private Methods
    private void uncheckedSetGridId(@IntRange(from = -1) final long gridId) {
        this.setLong(SharedPreferences.GRID_ID, gridId);
    }
    // endregion

    private void uncheckedSetProjectId(
            @IntRange(from = -1) final long projectId) {
        this.setLong(SharedPreferences.PROJECT_ID, projectId);
    }

    // region Public Methods
    // region GridId Public Methods
    public long getGridId() {
        return this.getLong(SharedPreferences.GRID_ID);
    }

    public void setGridId(@IntRange(from = 1) final long gridId) {
        this.uncheckedSetGridId(
                Model.valid(gridId, this.stringGetter));
    }

    public void clearGridId() {
        this.uncheckedSetGridId(-1);
    }

    public boolean gridIdIsSet() {
        return this.getGridId() > -1;
    }
    // endregion

    // region ProjectId Public Methods
    public long getProjectId() {
        return this.getLong(SharedPreferences.PROJECT_ID);
    }

    public void setProjectId(@IntRange(from = 1) final long projectId) {
        this.uncheckedSetProjectId(
                Model.valid(projectId, this.stringGetter));
    }

    public void clearProjectId() {
        this.uncheckedSetProjectId(-1);
    }

    public boolean projectIdIsSet() {
        return this.getProjectId() > -1;
    }
    // endregion
    // endregion
}