package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.res.Resources
 * android.os.Bundle
 * android.view.MenuItem
 * android.view.View.OnClickListener
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 *
 * org.wheatgenetics.coordinate.GridDeleter
 * org.wheatgenetics.coordinate.GridDeleter.Handler
 * org.wheatgenetics.coordinate.ProjectDeleter
 * org.wheatgenetics.coordinate.ProjectDeleter.Handler
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Types.RequestCode
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.deleter.TemplateDeleter
 * org.wheatgenetics.coordinate.deleter.TemplateDeleter.GridHandler
 *
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 *
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
 * org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
 * org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler
 *
 * org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
 * org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler
 *
 * org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog
 * org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler
 * org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog
 * org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler
 * org.wheatgenetics.coordinate.nisl.PreferenceActivity
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class NavigationItemSelectedListener extends java.lang.Object implements
com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void    createGrid             ();
        public abstract boolean joinedGridModelIsLoaded();
        public abstract void    loadGrid  (@androidx.annotation.IntRange(from = 1) long gridId);
        public abstract long    getGridId          ();
        public abstract void    respondToDeleteGrid();
        public abstract void    exportGrid         (
            @androidx.annotation.IntRange(from = 1) long gridId, java.lang.String fileName);

        public abstract void importTemplate(java.lang.String fileName);
        public abstract void exportTemplate(
            @androidx.annotation.IntRange(from = 1) long             templateId,
                                                    java.lang.String fileName  );
        public abstract void handleGridDeleted();

        public abstract long getProjectId();
        public abstract void loadProject (@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void clearProject();
        public abstract void handleProjectDeleted(
            @androidx.annotation.IntRange(from = 1) long projectId);
        public abstract boolean projectModelIsLoaded();
        public abstract void    exportProject       (
            @androidx.annotation.IntRange(from = 1) long             projectId    ,
                                                    java.lang.String directoryName);

        public abstract void closeDrawer();
    }

    private enum TemplateOperation {       EXPORT, DELETE }
    private enum ProjectOperation  { LOAD, DELETE, EXPORT }
    // endregion

    // region Fields
    // region Constructor Fields
    @androidx.annotation.NonNull                    private final android.app.Activity activity;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int
        createTemplateRequestCode, clickUniquenessRequestCode;
                                 private final int              preprocessTemplateImportRequestCode;
                                 private final java.lang.String versionName                        ;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler handler           ;
    private final android.view.View.OnClickListener                          versionOnClickListener;
    // endregion

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.database.ProjectsTable  projectsTableInstance  = null;// ll
    // endregion

    // region Lazy Load Fields
    private org.wheatgenetics.coordinate.GridDeleter       gridDeleterInstance = null;  // lazy load
    private org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog
        manageGridAlertDialogInstance = null;                                           // lazy load
    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor
        gridExportPreprocessorInstance = null;                                          // lazy load

    private org.wheatgenetics.coordinate.tc.TemplateCreator    templateCreatorInstance = null; // ll
    private org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor
        templateImportPreprocessorInstance = null;                                      // lazy load
    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor
        templateExportPreprocessorInstance = null;                                      // lazy load
    private org.wheatgenetics.coordinate.deleter.TemplateDeleter templateDeleterInstance = null;//ll

    private org.wheatgenetics.coordinate.pc.ProjectCreator      projectCreatorInstance = null; // ll
    private org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog
        manageProjectAlertDialogInstance = null;                                        // lazy load
    private org.wheatgenetics.coordinate.ProjectDeleter  projectDeleterInstance = null; // lazy load
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor
        projectExportPreprocessorInstance = null;                                       // lazy load

    private android.content.Intent preferenceIntentInstance = null;                     // lazy load

    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialogInstance = null;   // lazy load
    // endregion
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }
    // endregion

    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    // region Grid Private Methods
    // region manageGrid() Grid Private Methods
    // region loadGrid() manageGrid() Grid Private Methods
    private void loadGridAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { if (null != joinedGridModel) this.handler.loadGrid(joinedGridModel.getId()); }

    private void loadGrid()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.gridsTable().load();
        if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectGridToLoadAlertDialog =
                new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                    new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                    {
                        @java.lang.Override public void select(final int which)
                        {
                            org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                .this.loadGridAfterSelect(baseJoinedGridModels.get(which));
                        }
                    });
            selectGridToLoadAlertDialog.show(
                org.wheatgenetics.coordinate.R.string.NavigationItemSelectedListenerLoadGridTitle,
                baseJoinedGridModels.names()                                                     );
        }
    }
    // endregion

    // region deleteGrid() manageGrid() Grid Private Methods
    private void respondToDeleteGrid() { this.handler.respondToDeleteGrid(); }

    private org.wheatgenetics.coordinate.GridDeleter gridDeleter()
    {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
            new org.wheatgenetics.coordinate.GridDeleter(this.activity,
                new org.wheatgenetics.coordinate.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.respondToDeleteGrid();
                    }
                });
        return this.gridDeleterInstance;
    }

    private void deleteGrid() { this.gridDeleter().deleteWithConfirm(this.handler.getGridId()); }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog manageGridAlertDialog()
    {
        if (null == this.manageGridAlertDialogInstance) this.manageGridAlertDialogInstance =
            new org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog.Handler()
                {
                    @java.lang.Override public void loadGrid()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.loadGrid();
                    }

                    @java.lang.Override public void deleteGrid()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.deleteGrid();
                    }
                });
        return this.manageGridAlertDialogInstance;
    }

    private void manageGrid()
    { this.manageGridAlertDialog().show(this.handler.joinedGridModelIsLoaded()); }
    // endregion

    // region exportGrid() Grid Private Methods
    private void exportGrid(
    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                            final java.lang.String fileName)
    { this.handler.exportGrid(gridId, fileName); }

    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor gridExportPreprocessor()
    {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.ge.GridExportPreprocessor(this.activity,
                new org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportGrid(
                    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                                            final java.lang.String fileName)
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.exportGrid(gridId, fileName);
                    }
                });
        return this.gridExportPreprocessorInstance;
    }

    private void exportGrid()
    { this.gridExportPreprocessor().preprocess(this.handler.getGridId()); }
    // endregion
    // endregion

    // region Template Private Methods
    // region createTemplate() Template Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator()
    {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                this.activity, this.createTemplateRequestCode,this);
        return this.templateCreatorInstance;
    }

    private void createTemplate() { this.templateCreator().create(); }
    // endregion

    // region importTemplate() Template Private Methods
    private void importTemplate(final java.lang.String fileName)
    { this.handler.importTemplate(fileName); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor templateImportPreprocessor()
    {
        if (null == this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance =
                new org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor(
                    this.activity, this.preprocessTemplateImportRequestCode,
                    new org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor.Handler()
                    {
                        @java.lang.Override
                        public void importTemplate(final java.lang.String fileName)
                        {
                            org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                .this.importTemplate(fileName);
                        }
                    });
        return this.templateImportPreprocessorInstance;
    }

    private void importTemplate() { this.templateImportPreprocessor().preprocess(); }
    // endregion

    // region Export Template Private Methods
    private void exportTemplateAfterGettingFileName(
    @androidx.annotation.IntRange(from = 1) final long             templateId,
                                            final java.lang.String fileName  )
    { this.handler.exportTemplate(templateId, fileName); }

    private org.wheatgenetics.coordinate.te.TemplateExportPreprocessor templateExportPreprocessor()
    {
        if (null == this.templateExportPreprocessorInstance)
            this.templateExportPreprocessorInstance =
                new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor(this.activity,
                    new org.wheatgenetics.coordinate.te.TemplateExportPreprocessor.Handler()
                    {
                        @java.lang.Override public void exportTemplate(
                        @androidx.annotation.IntRange(from = 1) final long             templateId,
                                                                final java.lang.String fileName  )
                        {
                            org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                .this.exportTemplateAfterGettingFileName(templateId, fileName);
                        }
                    });
        return this.templateExportPreprocessorInstance;
    }

    private void exportTemplateAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { this.templateExportPreprocessor().preprocess(templateModel); }
    // endregion

    // region Delete Template Private Methods
    private void handleGridDeleted() { this.handler.handleGridDeleted(); }

    private org.wheatgenetics.coordinate.deleter.TemplateDeleter templateDeleter()
    {
        if (null == this.templateDeleterInstance) this.templateDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.TemplateDeleter(this.activity,
                new org.wheatgenetics.coordinate.deleter.TemplateDeleter.GridHandler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.handleGridDeleted();
                    }
                });
        return this.templateDeleterInstance;
    }

    private void deleteTemplateAfterSelect(final org.wheatgenetics.coordinate.model.TemplateModel
    templateModel) { this.templateDeleter().delete(templateModel); }
    // endregion

    private void selectUserDefinedTemplate(
    final org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.TemplateOperation
        templateOperation)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().loadUserDefined();
        if (null != templateModels) if (templateModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectTemplateAlertDialog;
            {
                final org.wheatgenetics.coordinate.SelectAlertDialog.Handler handler;
                switch (templateOperation)
                {
                    case EXPORT: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.exportTemplateAfterSelect(templateModels.get(which));
                            }
                        }; break;

                    case DELETE: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.deleteTemplateAfterSelect(templateModels.get(which));
                            }
                        }; break;

                    default: handler = null; break;
                }

                selectTemplateAlertDialog =
                    new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity, handler);
            }

            @androidx.annotation.StringRes final int title;
            switch (templateOperation)
            {
                case EXPORT: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectExportTemplateTitle; break;

                case DELETE: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectDeleteTemplateTitle; break;

                default: title = 0;
            }
            selectTemplateAlertDialog.show(title, templateModels.titles());
        }
    }
    // endregion

    // region Project Private Methods
    // region createProject() Project Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pc.ProjectCreator projectCreator()
    {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
            new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity);
        return this.projectCreatorInstance;
    }

    private void createProject() { this.projectCreator().create(); }
    // endregion

    private void selectProject(
    final org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.ProjectOperation
        projectOperation)
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels;
        switch (projectOperation)
        {
            case LOAD: projectModels =
                this.projectsTable().loadExceptFor(this.getProjectId()); break;

            case DELETE: projectModels = this.projectsTable().load                 (); break;
            case EXPORT: projectModels = this.projectsTable().loadProjectsWithGrids(); break;
            default    : projectModels = null                                        ; break;
        }

        if (null != projectModels) if (projectModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectProjectAlertDialog;
            {
                final org.wheatgenetics.coordinate.SelectAlertDialog.Handler handler;
                switch (projectOperation)
                {
                    case LOAD: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.loadProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    case DELETE: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.deleteProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    case EXPORT: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.exportProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    default: handler = null; break;
                }

                selectProjectAlertDialog =
                    new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity, handler);
            }

            @androidx.annotation.StringRes final int title;
            switch (projectOperation)
            {
                case LOAD: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectLoadProjectTitle; break;

                case DELETE: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectDeleteProjectTitle; break;

                case EXPORT: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectExportProjectTitle; break;

                default: title = 0;
            }
            selectProjectAlertDialog.show(title, projectModels.titles());
        }
    }

    // region manageProject() Project Private Methods
    private long getProjectId() { return this.handler.getProjectId(); }

    // region loadProject() Private Methods
    private void loadProjectAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { if (null != projectModel) this.handler.loadProject(projectModel.getId()); }

    private void loadProject()
    {
        this.selectProject(
            org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.ProjectOperation.LOAD);
    }
    // endregion

    private void clearProject() { this.handler.clearProject(); }

    // region deleteProject() Private Methods
    private void respondToDeletedProject(@androidx.annotation.IntRange(from = 1)
    final long projectId) { this.handler.handleProjectDeleted(projectId); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ProjectDeleter projectDeleter()
    {
        if (null == this.projectDeleterInstance) this.projectDeleterInstance =
            new org.wheatgenetics.coordinate.ProjectDeleter(this.activity,
                new org.wheatgenetics.coordinate.ProjectDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedProject(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.respondToDeletedProject(projectId);
                    }
                });
        return this.projectDeleterInstance;
    }

    private void deleteProjectAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { this.projectDeleter().delete(projectModel); }

    private void deleteProject()
    {
        this.selectProject(org.wheatgenetics.coordinate.nisl
            .NavigationItemSelectedListener.ProjectOperation.DELETE);
    }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog manageProjectAlertDialog()
    {
        if (null == this.manageProjectAlertDialogInstance) this.manageProjectAlertDialogInstance =
            new org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog(this.activity,
                new org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler()
                {
                    @java.lang.Override public long getProjectId()
                    {
                        return org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.getProjectId();
                    }

                    @java.lang.Override public void loadProject()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.loadProject();
                    }

                    @java.lang.Override public void clearProject()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.clearProject();
                    }

                    @java.lang.Override public void deleteProject()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.deleteProject();
                    }
                });
        return this.manageProjectAlertDialogInstance;
    }

    private void manageProject()
    { this.manageProjectAlertDialog().show(this.handler.projectModelIsLoaded()); }
    // endregion

    // region Export Project Private Methods
    private void exportProject(
    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                            final java.lang.String directoryName)
    { this.handler.exportProject(projectId, directoryName); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor projectExportPreprocessor()
    {
        if (null == this.projectExportPreprocessorInstance) this.projectExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor(this.activity,
                new org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportProject(
                    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                                            final java.lang.String directoryName)
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.exportProject(projectId, directoryName);
                    }
                });
        return this.projectExportPreprocessorInstance;
    }

    private void exportProjectAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { this.projectExportPreprocessor().preprocess(projectModel); }
    // endregion
    // endregion

    // region PreferenceActivity Private Methods
    @androidx.annotation.NonNull private android.content.Intent preferenceIntent()
    {
        if (null == this.preferenceIntentInstance)
            this.preferenceIntentInstance = new android.content.Intent(
                this.activity, org.wheatgenetics.coordinate.nisl.PreferenceActivity.class);
        return this.preferenceIntentInstance;
    }

    private void startPreferenceActivity()
    {
        this.activity.startActivityForResult(this.preferenceIntent(),
            this.clickUniquenessRequestCode);
    }
    // endregion

    // region AboutAlertDialog Private Methods
    @androidx.annotation.Nullable
    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog()
    {
        if (null == this.aboutAlertDialogInstance)
        {
            // noinspection CStyleArrayDeclaration
            final java.lang.String title, msgs[];
            {
                final android.content.res.Resources resources = this.activity.getResources();

                if (null == resources)
                    return null;
                else
                {
                    title = resources.getString(
                        org.wheatgenetics.coordinate.R.string.AboutAlertDialogTitle);
                    msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                        org.wheatgenetics.coordinate.R.string.AboutAlertDialogMsg));
                }
            }

            this.aboutAlertDialogInstance = new org.wheatgenetics.about.AboutAlertDialog(
                /* context       => */ this.activity                                     ,
                /* title         => */ title                                             ,
                /* versionName   => */ this.versionName                                  ,
                /* msgs[]        => */ msgs                                              ,
                /* suppressIndex => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                /* versionOnClickListener => */ this.versionOnClickListener              );
        }
        return this.aboutAlertDialogInstance;
    }

    private void showAboutAlertDialog()
    {
        final org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = this.aboutAlertDialog();
        if (null != aboutAlertDialog) aboutAlertDialog.show();
    }
    // endregion
    // endregion

    public NavigationItemSelectedListener(
    @androidx.annotation.NonNull                    final android.app.Activity activity            ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int   createTemplateRequestCode          ,
                                                    final int   preprocessTemplateImportRequestCode,
    @org.wheatgenetics.coordinate.Types.RequestCode final int   clickUniquenessRequestCode         ,
                                                    final java.lang.String     versionName         ,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.activity                            = activity                           ;
        this.createTemplateRequestCode           = createTemplateRequestCode          ;
        this.preprocessTemplateImportRequestCode = preprocessTemplateImportRequestCode;
        this.clickUniquenessRequestCode          = clickUniquenessRequestCode         ;
        this.versionName                         = versionName                        ;
        this.handler                             = handler                            ;
        this.versionOnClickListener              = versionOnClickListener             ;
    }

    // region Overridden Methods
    // region com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override public boolean onNavigationItemSelected(
    @androidx.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            // The following twelve ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.coordinate.R.id.nav_create_grid:
                this.handler.createGrid(); break;

            case org.wheatgenetics.coordinate.R.id.nav_manage_grid: this.manageGrid(); break;
            case org.wheatgenetics.coordinate.R.id.nav_export_grid: this.exportGrid(); break;



            case org.wheatgenetics.coordinate.R.id.nav_create_template:
                this.createTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.nav_import_template:
                this.importTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.nav_export_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.TemplateOperation.EXPORT); break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.TemplateOperation.DELETE); break;



            case org.wheatgenetics.coordinate.R.id.nav_create_project: this.createProject(); break;
            case org.wheatgenetics.coordinate.R.id.nav_manage_project: this.manageProject(); break;

            case org.wheatgenetics.coordinate.R.id.nav_export_project:
                this.selectProject(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.ProjectOperation.EXPORT); break;



            case org.wheatgenetics.coordinate.R.id.nav_settings:
                this.startPreferenceActivity(); break;



            case org.wheatgenetics.coordinate.R.id.nav_show_about:
                this.showAboutAlertDialog(); break;
        }
        this.handler.closeDrawer(); return true;
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override public void handleTemplateCreated(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final java.lang.String not = this.templatesTable().insert(templateModel) > 0 ? "" : " not";
        this.showLongToast(templateModel.getTitle() + not + " created");
    }
    // endregion
    // endregion

    // region Public Methods
    public void setExcludedCells(final android.os.Bundle bundle)
    {
        if (null != this.templateCreatorInstance)
            this.templateCreatorInstance.setExcludedCells(bundle);
    }

    public void preprocessTemplateImport()
    {
        if (null != this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance.preprocess();
    }
    // endregion
}