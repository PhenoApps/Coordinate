package org.wheatgenetics.coordinate.pc;

import android.app.Activity;
import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.ProjectModel;

public class ProjectCreator implements StringGetter {
    // region Fields
    // region Constructor Fields
    private final Activity activity;
    @Nullable
    private final
    ProjectCreator.Handler handler;
    private CreateProjectAlertDialog                   // lazy loads
            createProjectAlertDialogInstance = null, createAndReturnProjectAlertDialogInstance = null;
    // endregion
    private ProjectsTable
            projectsTableInstance = null;                                                   // lazy load
    // region Constructors
    public ProjectCreator(final Activity activity, @Nullable final ProjectCreator.Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    public ProjectCreator(@NonNull final Activity activity) {
        this(activity, null);
    }

    // region Private Methods
    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private long sharedInsert(final String projectTitle) {
        return this.projectsTable().insert(
                new ProjectModel(projectTitle, this));
    }

    // region create() Private Methods
    private boolean insert(final String projectTitle) {
        return this.sharedInsert(projectTitle) > 0;
    }
    // endregion

    @NonNull
    private CreateProjectAlertDialog createProjectAlertDialog() {
        if (null == this.createProjectAlertDialogInstance) this.createProjectAlertDialogInstance =
                new CreateProjectAlertDialog(this.activity,
                        new CreateProjectAlertDialog.Handler() {
                            @Override
                            public boolean handleCreateProjectDone(final String projectTitle) {
                                return ProjectCreator.this.insert(
                                        projectTitle);
                            }
                        });
        return this.createProjectAlertDialogInstance;
    }

    // region createAndReturn() Private Methods
    private boolean insertAndReturn(final String projectTitle) {
        final long projectId = this.sharedInsert(projectTitle);
        if (projectId > 0) {
            if (null != this.handler) this.handler.handleCreateProjectDone(projectId);
            return true;
        } else return false;
    }
    // endregion
    // endregion

    @NonNull
    private CreateProjectAlertDialog createAndReturnProjectAlertDialog() {
        if (null == this.createAndReturnProjectAlertDialogInstance)
            this.createAndReturnProjectAlertDialogInstance =
                    new CreateProjectAlertDialog(this.activity,
                            new CreateProjectAlertDialog.Handler() {
                                @Override
                                public boolean handleCreateProjectDone(
                                        final String projectTitle) {
                                    return
                                            ProjectCreator.this.insertAndReturn(
                                                    projectTitle);
                                }
                            });
        return this.createAndReturnProjectAlertDialogInstance;
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.activity.getString(resId);
    }
    // endregion

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.activity.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    // region Public Methods
    public void create() {
        this.createProjectAlertDialog().show();
    }
    // endregion

    public void createAndReturn() {
        this.createAndReturnProjectAlertDialog().show();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleCreateProjectDone(
                @IntRange(from = 1) long projectId);
    }
    // endregion
}