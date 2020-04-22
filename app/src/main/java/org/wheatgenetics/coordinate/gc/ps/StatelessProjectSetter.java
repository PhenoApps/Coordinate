package org.wheatgenetics.coordinate.gc.ps;

/**
 * The StatelessProjectSetter is for use with the version of Coordinate that does not maintain
 * state.  There is no currently-loaded-project mechanism.  The StatelessProjectSetter will either
 * 1) ask the user if the grid is supposed to be in a project (and if it is then which project) or
 * 2) not be used because the GridCreator will be told what project the grid is supposed to be in.
 *
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 *
 * org.wheatgenetics.coordinate.gc.ps.ProjectSetter
 */
public class StatelessProjectSetter extends org.wheatgenetics.coordinate.gc.ps.ProjectSetter
{
    // region Fields
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance  = null; // ll
    private boolean                                             projectsExist                ;
    private org.wheatgenetics.coordinate.model.ProjectModels    projectModels          = null;
    private org.wheatgenetics.coordinate.SelectAlertDialog
        secondProjectChoiceAlertDialogInstance = null;                                  // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    private void createProject() { this.projectCreator().createAndReturn(); }

    // region SelectAlertDialog Private Methods
    private void chooseExistingAfterSelect(@androidx.annotation.IntRange(from = 0) final int which)
    {
        if (null != this.projectModels)
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                this.projectModels.get(which);
            if (null != projectModel)
                { this.setProjectId(projectModel.getId()); this.handleExistingProjectSet(); }
            this.projectModels = null;
        }
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.SelectAlertDialog secondProjectChoiceAlertDialog()
    {
        if (null == this.secondProjectChoiceAlertDialogInstance)
            this.secondProjectChoiceAlertDialogInstance =
                new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity(),
                    new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                    {
                        @java.lang.Override public void select(final int which)
                        {
                            if (which < 0)
                                throw new java.lang.IllegalArgumentException();
                            else
                                if (which == 0)
                                    org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
                                        .this.createProject();
                                else
                                    org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter
                                        .this.chooseExistingAfterSelect(which - 1);
                        }
                    });
        return this.secondProjectChoiceAlertDialogInstance;
    }

    private void showSecondProjectChoiceAlertDialog()
    {
        @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final java.lang.String items[];
        {
            @androidx.annotation.NonNull final java.lang.String firstItem =
                this.activity().getString(
                    org.wheatgenetics.coordinate.R.string.ProjectSetterCreateProjectItem);

            this.projectModels = this.projectsTable().load();
            if (null == this.projectModels)
                items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
            else
            {
                @androidx.annotation.Nullable final java.lang.String[] titles =
                    this.projectModels.titles();
                if (null == titles)
                    items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
                else
                    if (titles.length <= 0)
                        items = org.wheatgenetics.javalib.Utils.stringArray(firstItem);
                    else
                    {
                        // noinspection Convert2Diamond
                        final java.util.ArrayList<java.lang.String> arrayList =
                            new java.util.ArrayList<java.lang.String>(
                                1 + titles.length);
                        arrayList.add(firstItem); arrayList.addAll(java.util.Arrays.asList(titles));
                        arrayList.toArray(items = new java.lang.String[arrayList.size()]);
                    }
            }
        }
        this.secondProjectChoiceAlertDialog().show(
            org.wheatgenetics.coordinate.R.string.SecondProjectChoiceAlertDialogTitle, items);
    }
    // endregion
    // endregion

    public StatelessProjectSetter(final android.app.Activity activity, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.gc.ps.StatelessProjectSetter.Handler handler)
    { super(activity, handler); }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleCreateProjectDone(
    @androidx.annotation.IntRange(from = 1) final long projectId)
    { this.setProjectId(projectId); this.handleNewProjectSet(); }

    /**
     * which         first project choice
     * ===== =====================================
     *   0   Don't add this grid to a project.
     *   1   If projects exist, add this grid to a project (newly created or existing).  If no pro-
     *       jects exist, add this grid to a newly created project.
     */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void handleProjectChoice(
    @androidx.annotation.IntRange(from = 0, to = 2) final int which)
    {
        switch (which)
        {
            case 0: this.clearProjectId(); this.handleNoProjectSet(); break;

            case 1: if (this.projectsExist)
                    this.showSecondProjectChoiceAlertDialog();
                else
                    this.createProject();
                break;

            default: throw new java.lang.IllegalArgumentException();
        }
    }
    // endregion

    public void set()
    {
        this.projectsExist = this.projectsTable().exists();
        @androidx.annotation.StringRes final int resId = this.projectsExist ?
            org.wheatgenetics.coordinate.R.string.StatelessProjectSetterAddItem :
            org.wheatgenetics.coordinate.R.string.ProjectSetterCreateProjectItem;
        this.showProjectChoiceAlertDialog(this.activity().getString(resId),null);
    }
}