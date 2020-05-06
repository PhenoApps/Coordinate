package org.wheatgenetics.coordinate.gc.ps;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.ProjectModel;

/**
 * The StatefulProjectSetter is for use with the version of Coordinate that maintains state.  The
 * state that is maintained is the currently loaded project, if any.
 */
public class StatefulProjectSetter extends ProjectSetter {
    public StatefulProjectSetter(final Activity activity, @NonNull final StatefulProjectSetter.Handler handler) {
        super(activity, handler);
    }

    @NonNull
    private StatefulProjectSetter.Handler statefulHandler() {
        return (StatefulProjectSetter.Handler) this.handler();
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    void handleCreateProjectDone(
            @IntRange(from = 1) final long projectId) {
        this.setProjectId(projectId);
        this.statefulHandler().loadProjectModel(projectId);
        this.handleNewProjectSet();
    }

    /**
     * which                          item
     * ===== =======================================================
     * 0   Don't add this grid to a project.
     * 1   Create a project for this grid then select new project.
     * 2   Add this grid to "X" project.
     * <p>
     * projectId which             side effect
     * ========= ===== ===================================
     * cleared    0   none
     * cleared    1   load new project
     * set      0   clear old project
     * set      1   clear old project, load new project
     * set      2   none
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    void handleProjectChoice(
            @IntRange(from = 0, to = 2) final int which) {
        if (this.projectIdIsCleared())
            switch (which) {
                case 0:
                    this.handleNoProjectSet();
                    break;
                case 1:
                    this.projectCreator().createAndReturn();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        else
            switch (which) {
                case 0:
                    this.clearProjectId();
                    this.statefulHandler().clearProjectModel();
                    this.handleNoProjectSet();
                    break;

                case 1:
                    this.projectCreator().createAndReturn();
                    break;
                case 2:
                    this.handleExistingProjectSet();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
    }

    public void set(@Nullable final ProjectModel projectModel) {
        @NonNull final String secondItem;
        @Nullable final String thirdItem;
        {
            final Activity activity = this.activity();

            secondItem = activity.getString(
                    R.string.ProjectSetterCreateProjectItem);

            if (null == projectModel) {
                thirdItem = null;
                this.clearProjectId();
            } else {
                thirdItem = String.format(activity.getString(
                        R.string.StatefulProjectSetterSelectProjectItem),
                        projectModel.getTitle());
                this.setProjectId(projectModel.getId());
            }
        }
        this.showProjectChoiceAlertDialog(secondItem, thirdItem);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends ProjectSetter.Handler {
        public abstract void clearProjectModel();

        public abstract void loadProjectModel(
                @IntRange(from = 1) long projectId);
    }
}