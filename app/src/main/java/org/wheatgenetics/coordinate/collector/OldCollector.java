package org.wheatgenetics.coordinate.collector;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.sharedpreferences.SharedPreferences;

public class OldCollector extends BaseCollector {
    @NonNull
    private final SharedPreferences sharedPreferences;

    public OldCollector(
            @NonNull final AppCompatActivity activity,
            @NonNull final SharedPreferences
                    sharedPreferences) {
        super(activity);
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void loadJoinedGridModel(@IntRange(from = 0) final long gridId) {
        super.loadJoinedGridModel(gridId);

        if (this.joinedGridModelIsLoaded())
            sharedPreferences.setGridId(this.joinedGridModel.getId());
        else
            sharedPreferences.clearGridId();
    }

    // region Public Methods
    // region joinedGridModel() Public Methods
    @NonNull
    public String getPerson() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getPerson() : "";
    }

    public long getGridId() {
        return this.joinedGridModelIsLoaded() ? this.joinedGridModel.getId() : 0;
    }
    // endregion

    // region menuItemShouldBeEnabled() Public Methods
    public boolean manageGridMenuItemShouldBeEnabled() {
        final GridsTable gridsTable = this.gridsTable();
        // noinspection SimplifiableConditionalExpression
        return null == gridsTable ? false : gridsTable.exists();
    }

    public boolean manageProjectMenuItemShouldBeEnabled() {
        return this.projectsTable().exists();
    }

    public boolean exportProjectMenuItemShouldBeEnabled() {
        if (this.projectsTable().exists()) {
            final GridsTable gridsTable =
                    this.gridsTable();
            // noinspection SimplifiableConditionalExpression
            return null == gridsTable ? false : gridsTable.existsInProject();
        } else return false;
    }
    // endregion

    public void clearJoinedGridModelThenPopulate() {
        this.loadJoinedGridModelThenPopulate(0);
    }

    // region Project Public Methods
    @Nullable
    public ProjectModel
    getProjectModel(@IntRange(from = 1) final long projectId) {
        return Model.illegal(projectId) ?
                null : this.projectsTable().get(projectId);
    }

    public boolean projectExists(@IntRange(from = 1) final long projectId) {
        return this.projectsTable().exists(projectId);
    }
    // endregion

    public void handleGridDeleted() {
        if (this.joinedGridModelIsLoaded()) {
            final GridsTable gridsTable = this.gridsTable();
            if (null != gridsTable) if (!gridsTable.exists(this.joinedGridModel.getId()))
                this.clearJoinedGridModelThenPopulate();
        }
    }
    // endregion
}