package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.ProjectModel;

public class ProjectDeleter extends CascadingDeleter {
    // region Fields
    @NonNull
    private final
    ProjectDeleter.Handler handler;
    private ProjectsTable projectsTableInstance = null;  // ll

    public ProjectDeleter(@NonNull final Context context,
                          @NonNull final ProjectDeleter.Handler
                                  handler) {
        super(context,
                R.string.ProjectDeleterConfirmationTitle,
                R.string.ProjectDeleterConfirmationMessage,
                R.string.ProjectDeleterProjectSuccessToast,
                R.string.ProjectDeleterProjectFailToast);
        this.handler = handler;
    }
    // endregion

    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.context());
        return this.projectsTableInstance;
    }

    public boolean deleteAll() {
        return projectsTable().deleteAll();
    }
    // region Overridden Methods
    // region deleteStep3() Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean deleteMasterRecord() {
        return this.projectsTable().delete(this.id());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean deleteStep3() {
        final boolean success = super.deleteStep3();
        if (success) this.handler.respondToDeletedProject(this.id());
        return success;
    }

    // region deleteStep2() Overridden Method
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @Nullable
    BaseJoinedGridModels loadDetailRecords() {
        return this.gridsTable().loadByProjectId(this.id());
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean detailRecordsExists() {
        return this.gridsTable().existsInProject(this.id());
    }
    // endregion

    public void delete(@Nullable final
                       ProjectModel projectModel) {
        super.delete(projectModel);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void respondToDeletedProject(
                @IntRange(from = 1) long projectId);
    }
}