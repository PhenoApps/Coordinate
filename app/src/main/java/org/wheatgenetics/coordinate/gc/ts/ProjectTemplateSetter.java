package org.wheatgenetics.coordinate.gc.ts;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

public class ProjectTemplateSetter extends TemplateSetter {
    private GridsTable gridsTableInstance = null; // lazy load

    public ProjectTemplateSetter(final Activity activity) {
        super(activity);
    }

    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    @IntRange(from = 0)
    public long set(
            @IntRange(from = 1) final long projectId) {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(projectId);
        if (null != baseJoinedGridModels && baseJoinedGridModels.size() > 0) {
            // All grids will have the same templateId.  Using the first one is an arbitrary choice.
            final JoinedGridModel joinedGridModel =
                    baseJoinedGridModels.get(0);
            return null == joinedGridModel ? 0 : joinedGridModel.getTemplateId();
        } else return 0;
    }
}