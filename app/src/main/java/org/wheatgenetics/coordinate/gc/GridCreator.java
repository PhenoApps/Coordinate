package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 * org.wheatgenetics.coordinate.pc.ProjectCreator.Handler
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog
 * org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler
 * org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog
 * org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class GridCreator extends java.lang.Object implements
org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler             ,
org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    {
        public abstract void handleGridCreated(long gridId);

        public abstract void loadProjectModel (long projectId);
        public abstract void clearProjectModel(              );
    }

    // region Fields
    private final android.app.Activity                                activity   ;
    private final int                                                 requestCode;
    private final org.wheatgenetics.coordinate.gc.GridCreator.Handler handler    ;

    private org.wheatgenetics.coordinate.database.GridsTable   gridsTableInstance   = null;
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTableInstance = null;

    private java.lang.String                                                  person        ;
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields  optionalFields;
    private org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog
        setOptionalFieldValuesAlertDialog = null;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.SelectAlertDialog       chooseOldAlertDialog   = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.model.TemplateModels    templateModels         = null;

    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator = null;

    private org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog
        getTemplateChoiceAlertDialog = null;

    private org.wheatgenetics.coordinate.model.ProjectModel projectModel                = null;
    private org.wheatgenetics.coordinate.SelectAlertDialog  getProjectChoiceAlertDialog = null;
    private org.wheatgenetics.coordinate.pc.ProjectCreator
        clearedProjectCreator = null, loadedProjectCreator = null;
    private long projectId;
    // endregion

    // region Private Methods
    private void getTemplateChoice()
    {
        if (null == this.getTemplateChoiceAlertDialog) this.getTemplateChoiceAlertDialog =
            new org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog(this.activity, this);
        this.getTemplateChoiceAlertDialog.show();
    }

    private void handleCreateClearedProjectDone(final long projectId)
    {
        this.projectId = projectId;
        assert null != this.handler; this.handler.loadProjectModel(this.projectId);
        this.getTemplateChoice();
    }

    private boolean setTemplateFromOtherGrids()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
            this.gridsTable().loadByProjectId(this.projectId);
        if (null != joinedGridModels && joinedGridModels.size() > 0)
        {
            this.templateModel = this.templatesTable().get(
                joinedGridModels.get(0).getTemplateId());
            this.setValues();
            return true;
        }
        return false;
    }

    private void handleCreateLoadedProjectDone(final long projectId)
    {
        this.projectId = projectId;
        assert null != this.handler; this.handler.loadProjectModel(this.projectId);
        if (!this.setTemplateFromOtherGrids()) this.getTemplateChoice();
    }

    /**
     * projectModel     which
     *              no project (0)
     * == null (0) (create)use (1)
     * != null (1)     use (2)      side effect
     * =========== =============== =============
     *      0             0        none
     *      0             1        load project
     *      1             0        clear project
     *      1             1        load project
     *      1             2        none
     */
    private void handleProjectChoice(final int which)
    {
        if (null == this.projectModel)
            switch (which)
            {
                case 0: this.projectId = 0; break;

                case 1:
                    if (null == this.clearedProjectCreator) this.clearedProjectCreator =
                        new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity,
                            new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                            {
                                @java.lang.Override
                                public void handleCreateProjectDone(final long projectId)
                                {
                                    org.wheatgenetics.coordinate.gc.GridCreator
                                        .this.handleCreateClearedProjectDone(projectId);
                                }
                            } );
                    this.clearedProjectCreator.createAndReturn(); return;

                default: throw new java.lang.IllegalArgumentException();
            }
        else
            switch (which)
            {
                case 0:
                    this.projectId = 0;
                    assert null != this.handler; this.handler.clearProjectModel(); break;

                case 1:
                    if (null == this.loadedProjectCreator) this.loadedProjectCreator =
                        new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity,
                            new org.wheatgenetics.coordinate.pc.ProjectCreator.Handler()
                            {
                                @java.lang.Override
                                public void handleCreateProjectDone(final long projectId)
                                {
                                    org.wheatgenetics.coordinate.gc.GridCreator
                                        .this.handleCreateLoadedProjectDone(projectId);
                                }
                            });
                    this.loadedProjectCreator.createAndReturn(); return;

                case 2:
                    this.projectId = this.projectModel.getId();
                    if (this.setTemplateFromOtherGrids()) return; else break;

                default: throw new java.lang.IllegalArgumentException();
            }

        this.getTemplateChoice();
    }

    private void chooseOldAfterSelect(final int which)
    {
        assert null != this.templateModels; this.templateModel = this.templateModels.get(which);
        this.templateModels = null;
        this.setValues();
    }

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void setValues()
    {
        this.person = null;

        assert null != this.templateModel;
        if (this.templateModel.optionalFieldsIsEmpty())
        {
            this.optionalFields = null;
            this.handleSetValuesDone();
        }
        else
        {
            this.optionalFields = this.templateModel.optionalFieldsClone();
            if (null == this.setOptionalFieldValuesAlertDialog)
                this.setOptionalFieldValuesAlertDialog =
                    new org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog(
                        this.activity, this);
            this.setOptionalFieldValuesAlertDialog.show(this.templateModel.getTitle(),
                new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
                    this.optionalFields),
                /* firstCannotBeEmpty => */ this.templateModel.isDefaultTemplate());
        }
    }

    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.activity);
        return this.entriesTableInstance;
    }
    // endregion

    public GridCreator(final android.app.Activity activity, final int requestCode,
    final org.wheatgenetics.coordinate.gc.GridCreator.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    // region org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void setPerson(final java.lang.String person) { this.person = person; }

    @java.lang.Override
    public void handleSetValuesDone()
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* projectId      => */ this.projectId     ,
                /* person         => */ this.person        ,
                /* optionalFields => */ this.optionalFields,
                /* templateModel  => */ this.templateModel );
        this.person = null; this.optionalFields = null; this.templateModel = null;

        final long gridId = this.gridsTable().insert(joinedGridModel);
        if (gridId <= 0)
            org.wheatgenetics.coordinate.Utils.alert(this.activity,
                org.wheatgenetics.coordinate.R.string.GridCreatorGridAlertMessage);
        else
        {
            joinedGridModel.setId(gridId);

            try
            {
                if (org.wheatgenetics.coordinate.model.Model.illegal(projectId))
                    joinedGridModel.makeEntryModels();                    // throws AmountIsTooLarge
                else
                {
                    final org.wheatgenetics.coordinate.model.JoinedGridModels
                        projectJoinedGridModels = this.gridsTable().loadByProjectId(this.projectId);
                    if (null == projectJoinedGridModels)
                        joinedGridModel.makeEntryModels();                // throws AmountIsTooLarge
                    else
                        if (projectJoinedGridModels.size() <= 1)
                            joinedGridModel.makeEntryModels();            // throws AmountIsTooLarge
                        else
                            try
                            {
                                joinedGridModel.makeEntryModels(          // throws AmountIsTooLarge
                                    projectJoinedGridModels.excludedCells(
                                        joinedGridModel.getRows(), joinedGridModel.getCols()));
                            }
                            catch (
                            final org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge e)
                            { joinedGridModel.makeEntryModels(); /* throws AmountIsTooLarge */ }
                }
            }
            catch (final org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge e)
            {
                this.gridsTable().delete(gridId);
                org.wheatgenetics.coordinate.Utils.alert(this.activity                  ,
                    org.wheatgenetics.coordinate.R.string.GridCreatorEntriesAlertMessage,
                    e.getMessage()                                                      );
                return;
            }

            if (joinedGridModel.activeRowAndOrActiveColWasAdjusted())
                this.gridsTable().update(joinedGridModel);           // Update activeRow, activeCol.

            this.entriesTable().insert(joinedGridModel.getEntryModels());

            assert null != this.handler; this.handler.handleGridCreated(gridId);
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override
    public void handleTemplateCreated(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        this.templateModel = templateModel;

        final boolean inserted;
        {
            final long templateId = this.templatesTable().insert(this.templateModel);
            if (templateId > 0)
            {
                assert null != this.templateModel; this.templateModel.setId(templateId);
                inserted = true;
            }
            else
            {
                org.wheatgenetics.coordinate.Utils.alert(this.activity,
                    org.wheatgenetics.coordinate.R.string.GridCreatorTemplateAlertMessage);
                inserted = false;
            }
        }
        if (inserted) this.setValues();
    }
    // endregion

    // region org.wheatgenetics.coordinate.gc.GetTemplateChoiceAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void chooseOld()
    {
        if (null == this.chooseOldAlertDialog) this.chooseOldAlertDialog =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void select(final int which)
                    {
                        org.wheatgenetics.coordinate.gc.GridCreator.this.chooseOldAfterSelect(
                            which);
                    }
                });
        this.templateModels = this.templatesTable().load();
        assert null != this.templateModels; this.chooseOldAlertDialog.show(
            org.wheatgenetics.coordinate.R.string.GridCreatorChooseOldAlertDialogTitle,
            this.templateModels.titles()                                              );
    }

    @java.lang.Override
    public void chooseNew()
    {
        if (null == this.templateCreator)
            this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this.activity, this.requestCode, this);
        this.templateCreator.create();
    }
    // endregion
    // endregion

    // region Public Methods
    public void create(final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        this.projectModel = projectModel;

        if (null == this.getProjectChoiceAlertDialog) this.getProjectChoiceAlertDialog =
            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void select(final int which)
                    { org.wheatgenetics.coordinate.gc.GridCreator.this.handleProjectChoice(which); }
                });

        final java.lang.String items[];
        {
            final java.lang.String
                firstItem  = "Don't put this grid in a project.",
                secondItem = "Create then select new project."  ;
            if (null == this.projectModel)
                items = new java.lang.String[] {firstItem, secondItem};
            else
                items = new java.lang.String[] {firstItem, secondItem,
                    "Put this grid in \"" + this.projectModel.getTitle() + "\"."};
        }
        this.getProjectChoiceAlertDialog.show(
            org.wheatgenetics.coordinate.R.string.GridCreatorGetProjectChoiceAlertDialogTitle,
            items                                                                            );
    }

    public void setExcludedCells(final android.os.Bundle bundle)
    { if (null != this.templateCreator) this.templateCreator.setExcludedCells(bundle); }
    // endregion
}