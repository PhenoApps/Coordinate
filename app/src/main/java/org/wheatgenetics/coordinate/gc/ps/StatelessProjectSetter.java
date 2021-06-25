package org.wheatgenetics.coordinate.gc.ps;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.SelectAlertDialog;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.ProjectModels;
import org.phenoapps.androidlibrary.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The StatelessProjectSetter is for use with the version of Coordinate that does not maintain
 * state.  There is no currently-loaded-project mechanism.  The StatelessProjectSetter will either
 * 1) ask the user if the grid is supposed to be in a project (and if it is then which project) or
 * 2) not be used because the GridCreator will be told what project the grid is supposed to be in.
 */
public class StatelessProjectSetter extends ProjectSetter {
    // region Fields
    private ProjectsTable projectsTableInstance = null; // ll
    private boolean projectsExist;
    private ProjectModels projectModels = null;
    private SelectAlertDialog
            secondProjectChoiceAlertDialogInstance = null;                                  // lazy load
    // endregion

    public StatelessProjectSetter(final Activity activity, @NonNull final StatelessProjectSetter.Handler handler) {
        super(activity, handler);
    }

    // region Private Methods
    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    // region secondProjectChoiceAlertDialog Private Methods
    private void createProject() {
        this.projectCreator().createAndReturn();
    }

    private void chooseExistingAfterSelect(@IntRange(from = 0) final int which) {
        if (null != this.projectModels) {
            final ProjectModel projectModel =
                    this.projectModels.get(which);
            if (null != projectModel) {
                this.setProjectId(projectModel.getId());
                this.handleExistingProjectSet();
            }
            this.projectModels = null;
        }
    }

    @NonNull
    private SelectAlertDialog secondProjectChoiceAlertDialog() {
        if (null == this.secondProjectChoiceAlertDialogInstance)
            this.secondProjectChoiceAlertDialogInstance =
                    new SelectAlertDialog(this.activity(),
                            new SelectAlertDialog.Handler() {
                                @Override
                                public void select(final int which) {
                                    if (which < 0)
                                        throw new IllegalArgumentException();
                                    else if (which == 0)
                                        StatelessProjectSetter
                                                .this.createProject();
                                    else
                                        StatelessProjectSetter
                                                .this.chooseExistingAfterSelect(which - 1);
                                }
                            });
        return this.secondProjectChoiceAlertDialogInstance;
    }
    // endregion
    // endregion

    private void showSecondProjectChoiceAlertDialog() {
        @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final String items[];
        {
            @NonNull final String firstItem =
                    this.activity().getString(
                            R.string.ProjectSetterCreateProjectItem);

            this.projectModels = this.projectsTable().load();
            if (null == this.projectModels)
                items = Utils.stringArray(firstItem);
            else {
                @Nullable final String[] titles =
                        this.projectModels.titles();
                if (null == titles)
                    items = Utils.stringArray(firstItem);
                else if (titles.length <= 0)
                    items = Utils.stringArray(firstItem);
                else {
                    // noinspection Convert2Diamond
                    final ArrayList<String> arrayList =
                            new ArrayList<String>(
                                    1 + titles.length);
                    arrayList.add(firstItem);
                    arrayList.addAll(Arrays.asList(titles));
                    arrayList.toArray(items = new String[arrayList.size()]);
                }
            }
        }
        this.secondProjectChoiceAlertDialog().show(
                R.string.SecondProjectChoiceAlertDialogTitle, items);
    }

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    void handleCreateProjectDone(
            @IntRange(from = 1) final long projectId) {
        this.setProjectId(projectId);
        this.handleNewProjectSet();
    }

    /**
     * which         first project choice
     * ===== =====================================
     * 0   Don't add this grid to a project.
     * 1   If projects exist, add this grid to a project (newly created or existing).  If no pro-
     * jects exist, add this grid to a newly created project.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    void handleProjectChoice(
            @IntRange(from = 0, to = 2) final int which) {
        switch (which) {
            case 0:
                this.clearProjectId();
                this.handleNoProjectSet();
                break;

            case 1:
                if (this.projectsExist)
                    this.showSecondProjectChoiceAlertDialog();
                else
                    this.createProject();
                break;

            default:
                throw new IllegalArgumentException();
        }
    }
    // endregion

    public void set() {
        this.projectsExist = this.projectsTable().exists();
        @StringRes final int resId = this.projectsExist ?
                R.string.StatelessProjectSetterAddItem :
                R.string.ProjectSetterCreateProjectItem;
        this.showProjectChoiceAlertDialog(this.activity().getString(resId), null);
    }
}