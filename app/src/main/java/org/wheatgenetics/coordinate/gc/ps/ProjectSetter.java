package org.wheatgenetics.coordinate.gc.ps;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.pc.ProjectCreator;

abstract class ProjectSetter {
    // region Fields
    // region Constructor Fields
    private final Activity activity;
    @NonNull
    private final
    ProjectSetter.Handler handler;
    /**
     * 0 means no projectId.
     */
    @IntRange(from = 0)
    private long projectId;
    // endregion
    private ProjectCreator projectCreatorInstance = null;      // ll
    private ProjectChoiceAlertDialog
            projectChoiceAlertDialogInstance = null;                                        // lazy load
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    ProjectSetter(final Activity activity, @NonNull final ProjectSetter.Handler handler) {
        super();
        this.activity = activity;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private ProjectChoiceAlertDialog projectChoiceAlertDialog() {
        if (null == this.projectChoiceAlertDialogInstance) this.projectChoiceAlertDialogInstance =
                new ProjectChoiceAlertDialog(this.activity(),
                        new ProjectChoiceAlertDialog.Handler() {
                            @Override
                            public void select(final int which) {
                                ProjectSetter.this.handleProjectChoice(
                                        which);
                            }
                        });
        return this.projectChoiceAlertDialogInstance;
    }

    @SuppressLint({"Range"})
    private void handleProjectSet(final boolean existing) {
        this.handler().handleProjectSet(this.projectId, existing);
    }
    // endregion

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Activity activity() {
        return this.activity;
    }

    // region projectId Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setProjectId(@IntRange(from = 0) final long projectId) {
        this.projectId = projectId;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void clearProjectId() {
        this.setProjectId(0);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean projectIdIsCleared() {
        return Model.illegal(this.projectId);
    }
    // endregion

    // region projectCreator Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    abstract void handleCreateProjectDone(
            @IntRange(from = 1) final long projectId);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    ProjectCreator projectCreator() {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
                new ProjectCreator(this.activity(),
                        new ProjectCreator.Handler() {
                            @Override
                            public void handleCreateProjectDone(
                                    @IntRange(from = 1) final long projectId) {
                                ProjectSetter
                                        .this.handleCreateProjectDone(projectId);
                            }
                        });
        return this.projectCreatorInstance;
    }
    // endregion

    // region projectChoiceAlertDialog Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    abstract void handleProjectChoice(
            @IntRange(from = 0, to = 2) final int which);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void showProjectChoiceAlertDialog(
            @NonNull final String secondItem,
            @Nullable final String thirdItem) {
        this.projectChoiceAlertDialog().show(secondItem, thirdItem);
    }
    // endregion

    // region handler Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    ProjectSetter.Handler handler() {
        return this.handler;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void handleNoProjectSet() {
        this.handler().handleNoProjectSet();
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void handleNewProjectSet() {
        this.handleProjectSet(false);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void handleExistingProjectSet() {
        this.handleProjectSet(true);
    }
    // endregion
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleNoProjectSet();

        public abstract void handleProjectSet(
                @IntRange(from = 1) long projectId, boolean existing);
    }
}