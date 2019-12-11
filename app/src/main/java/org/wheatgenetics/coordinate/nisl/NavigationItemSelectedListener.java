package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.annotation.SuppressLint
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
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.pc.ProjectCreator
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Types.RequestCode
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Handler
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
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler                               ,
org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void    createGrid             ();
        public abstract boolean joinedGridModelIsLoaded();
        public abstract void    loadGrid(@androidx.annotation.IntRange(from = 1) long gridId);
        public abstract void    deleteGrid();
        public abstract java.lang.String getInitialGridExportFileName();
        public abstract void             exportGrid                  (java.lang.String fileName);

        public abstract void importTemplate(java.lang.String fileName);
        public abstract void exportTemplate(@androidx.annotation.NonNull
            org.wheatgenetics.coordinate.model.TemplateModel templateModel,
            java.lang.String                                 fileName     );
        public abstract void handleGridDeleted();

        public abstract long    getProjectId        ();
        public abstract boolean projectModelIsLoaded();
        public abstract void    loadProject(@androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void    clearProject        ();
        public abstract void    handleProjectDeleted(
            @androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void exportProject(
            @androidx.annotation.IntRange(from = 1) long             projectId    ,
                                                    java.lang.String directoryName);

        public abstract void closeDrawer();
    }

    private enum TemplateOperation {       EXPORT, DELETE }
    private enum ProjectOperation  { LOAD, DELETE, EXPORT }
    // endregion

    // region Fields
    @androidx.annotation.NonNull                    private final android.app.Activity activity;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int
        createTemplateRequestCode, clickUniquenessRequestCode;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.TemplatesDir
        templatesDir;
                                 private final java.lang.String versionName;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler handler;
    private final android.view.View.OnClickListener               versionOnClickListener;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;// ll
    private org.wheatgenetics.coordinate.database.ProjectsTable  projectsTableInstance  = null;// ll
    // endregion

    private org.wheatgenetics.coordinate.nisl.ManageGridAlertDialog
        manageGridAlertDialog = null;                                                   // lazy load
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getGridExportFileNameAlertDialog = null;                                        // lazy load
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator = null;     // lazy load
    private org.wheatgenetics.coordinate.pc.ProjectCreator  projectCreator  = null;     // lazy load
    private org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog
        manageProjectAlertDialog = null;                                                // lazy load
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getProjectExportFileNameAlertDialog = null;                                     // lazy load
    @androidx.annotation.IntRange(from = 0) private long exportProjectId = 0;
    private android.content.Intent                   preferenceIntentInstance = null;   // lazy load
    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog         = null;   // lazy load
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
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.activity);
        return this.entriesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.activity.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.activity, text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.activity.getString(text)); }
    // endregion
    // endregion

    // region Grid Private Methods
    // region loadGrid() Grid Private Methods
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

    // region deleteGrid() Grid Private Methods
    private void deleteGridAfterConfirm() { this.handler.deleteGrid(); }

    private void deleteGrid()
    {
        org.wheatgenetics.coordinate.Utils.confirm(
            /* context => */ this.activity,
            /* message => */ org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridConfirmation,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override public void run()
                    {
                        org.wheatgenetics.coordinate.nisl
                            .NavigationItemSelectedListener.this.deleteGridAfterConfirm();
                    }
                });
    }
    // endregion

    private void exportGrid(final java.lang.String fileName) { this.handler.exportGrid(fileName); }
    // endregion

    // region Template Private Methods
    // region Import Template Private Methods
    private void importTemplateAfterGettingFileName(final java.lang.String fileName)
    { this.handler.importTemplate(fileName); }

    private void selectExportedTemplate()
    {
        org.wheatgenetics.coordinate.Utils.selectExportedTemplate(this.templatesDir, this.activity,
            new org.wheatgenetics.coordinate.Utils.Handler()
            {
                @java.lang.Override public void importTemplate(final java.lang.String fileName)
                {
                    org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                        .this.importTemplateAfterGettingFileName(fileName);
                }
            });
    }
    // endregion

    // region Export Template Private Methods
    private void exportTemplateAfterGettingFileName(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final java.lang.String                                 fileName     )
    { this.handler.exportTemplate(templateModel, fileName); }

    private void exportTemplateAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            final org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
                getTemplateExportFileNameAlertDialog =
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity,
                        new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void handleGetFileNameDone(final java.lang.String fileName)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.exportTemplateAfterGettingFileName(
                                        templateModel, fileName);
                            }
                        });
            getTemplateExportFileNameAlertDialog.show(templateModel.getTitle());
        }
    }
    // endregion

    // region Delete Template Private Methods
    private void deleteTemplateAfterConfirm(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
                                                final boolean success                           ;
        @androidx.annotation.IntRange(from = 1) final long    templateId = templateModel.getId();

        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByTemplateId(templateId);
            if (null != baseJoinedGridModels)
                baseJoinedGridModels.processAll(this);                  // delete entries
        }

        if (this.gridsTable().deleteByTemplateId(templateId))                     // delete grids
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsSuccessToast);
        else
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsFailToast);

        success = templatesTable().delete(templateId);                            // delete template

        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteTemplateSuccessToast);
            this.handler.handleGridDeleted();
        }
        else this.showLongToast(org.wheatgenetics.coordinate
            .R.string.NavigationItemSelectedListenerDeleteTemplateFailToast);
    }

    private void deleteTemplateAfterSelect(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel) org.wheatgenetics.coordinate.Utils.confirm(
            /* context => */ this.activity,
            /* title   => */ org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteTemplateConfirmationTitle,
            /* message => */ org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteTemplateConfirmationMessage,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override public void run()
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.deleteTemplateAfterConfirm(templateModel);
                    }
                });
    }
    // endregion

    private void selectUserDefinedTemplate(
    final org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.TemplateOperation
        templateOperation)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templatesTable().loadUserDefined();
        if (null != templateModels) if (templateModels.size() > 0)
        {
            final org.wheatgenetics.coordinate.SelectAlertDialog selectAlertDialog;
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

                selectAlertDialog =
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
            selectAlertDialog.show(title, templateModels.titles());
        }
    }
    // endregion

    // region Project Private Methods
    private long getProjectId() { return this.handler.getProjectId(); }

    private void selectProject(
    final org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.ProjectOperation
        projectOperation)
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels;
        switch (projectOperation)
        {
            case LOAD:
                projectModels = this.projectsTable().loadExceptFor(this.getProjectId());
                break;

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

    // region Load Project Private Methods
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

    // region Delete Project Private Methods
    private void deleteProjectAfterConfirm(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
                                                final boolean success                         ;
        @androidx.annotation.IntRange(from = 1) final long    projectId = projectModel.getId();

        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByProjectId(projectId);
            if (null != baseJoinedGridModels)
                baseJoinedGridModels.processAll(this);                   // delete entries
        }

        if (this.gridsTable().deleteByProjectId(projectId))                        // delete grids
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsSuccessToast);
        else
            this.showShortToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteGridsFailToast);

        success = projectsTable().delete(projectId);                               // delete project

        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteProjectSuccessToast);
            this.handler.handleProjectDeleted(projectId);
        }
        else this.showLongToast(org.wheatgenetics.coordinate
            .R.string.NavigationItemSelectedListenerDeleteProjectFailToast);
    }

    private void deleteProjectAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        if (null != projectModel) org.wheatgenetics.coordinate.Utils.confirm(
            /* context => */ this.activity,
            /* title   => */ org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteProjectConfirmationTitle,
            /* message => */ org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteProjectConfirmationMessage,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override public void run()
                    {
                        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                            .this.deleteProjectAfterConfirm(projectModel);
                    }
                });
    }

    private void deleteProject()
    {
        this.selectProject(org.wheatgenetics.coordinate.nisl
            .NavigationItemSelectedListener.ProjectOperation.DELETE);
    }
    // endregion

    // region Export Project Private Methods
    @android.annotation.SuppressLint({"Range"})
    private void exportProjectAfterGettingDirectoryName(final java.lang.String directoryName)
    { this.handler.exportProject(this.exportProjectId, directoryName); this.exportProjectId = 0; }

    @android.annotation.SuppressLint({"Range"})
    private void exportProjectAfterSelect(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        if (null != projectModel)
        {
            if (null == this.getProjectExportFileNameAlertDialog)
                this.getProjectExportFileNameAlertDialog =
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(this.activity,
                        new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                        {
                            @java.lang.Override
                            public void handleGetFileNameDone(final java.lang.String fileName)
                            {
                                org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                    .this.exportProjectAfterGettingDirectoryName(fileName);
                            }
                        });
            this.exportProjectId = projectModel.getId();
            this.getProjectExportFileNameAlertDialog.show(projectModel.getTitle());
        }
    }
    // endregion
    // endregion

    @androidx.annotation.NonNull private android.content.Intent preferenceIntent()
    {
        if (null == this.preferenceIntentInstance)
            this.preferenceIntentInstance = new android.content.Intent(
                this.activity, org.wheatgenetics.coordinate.nisl.PreferenceActivity.class);
        return this.preferenceIntentInstance;
    }
    // endregion

    public NavigationItemSelectedListener(
    @androidx.annotation.NonNull                    final android.app.Activity             activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int            createTemplateRequestCode ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int            clickUniquenessRequestCode,
    @androidx.annotation.NonNull                    final org.wheatgenetics.coordinate.TemplatesDir
        templatesDir,                               final java.lang.String versionName,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.activity                   = activity                  ;
        this.createTemplateRequestCode  = createTemplateRequestCode ;
        this.clickUniquenessRequestCode = clickUniquenessRequestCode;
        this.templatesDir               = templatesDir              ;
        this.versionName                = versionName               ;
        this.handler                    = handler                   ;
        this.versionOnClickListener     = versionOnClickListener    ;
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

            case org.wheatgenetics.coordinate.R.id.nav_manage_grid:
                if (null == this.manageGridAlertDialog) this.manageGridAlertDialog =
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
                this.manageGridAlertDialog.show(this.handler.joinedGridModelIsLoaded()); break;

            case org.wheatgenetics.coordinate.R.id.nav_export_grid:
                if (null == this.getGridExportFileNameAlertDialog)
                    this.getGridExportFileNameAlertDialog =
                        new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(
                            this.activity, new
                            org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler()
                            {
                                @java.lang.Override
                                public void handleGetFileNameDone(final java.lang.String fileName)
                                {
                                    org.wheatgenetics.coordinate.nisl.NavigationItemSelectedListener
                                        .this.exportGrid(fileName);
                                }
                            });
                this.getGridExportFileNameAlertDialog.show(
                    this.handler.getInitialGridExportFileName());
                break;



            case org.wheatgenetics.coordinate.R.id.nav_create_template:
                if (null == this.templateCreator)
                    this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                        this.activity, this.createTemplateRequestCode,this);
                this.templateCreator.create(); break;

            case org.wheatgenetics.coordinate.R.id.nav_import_template:
                this.selectExportedTemplate(); break;

            case org.wheatgenetics.coordinate.R.id.nav_export_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.TemplateOperation.EXPORT);
                break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.TemplateOperation.DELETE);
                break;



            case org.wheatgenetics.coordinate.R.id.nav_create_project:
                if (null == this.projectCreator) this.projectCreator =
                    new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity);
                this.projectCreator.create(); break;

            case org.wheatgenetics.coordinate.R.id.nav_manage_project:
                if (null == this.manageProjectAlertDialog) this.manageProjectAlertDialog =
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
                this.manageProjectAlertDialog.show(this.handler.projectModelIsLoaded()); break;

            case org.wheatgenetics.coordinate.R.id.nav_export_project:
                this.selectProject(org.wheatgenetics.coordinate.nisl
                    .NavigationItemSelectedListener.ProjectOperation.EXPORT);
                break;



            case org.wheatgenetics.coordinate.R.id.nav_settings:
                this.activity.startActivityForResult(this.preferenceIntent(),
                    this.clickUniquenessRequestCode); break;



            case org.wheatgenetics.coordinate.R.id.nav_show_about:
                if (null == this.aboutAlertDialog)
                {
                    // noinspection CStyleArrayDeclaration
                    final java.lang.String title, msgs[];
                    {
                        final android.content.res.Resources resources =
                            this.activity.getResources();

                        if (null == resources)
                            break;
                        else
                        {
                            title = resources.getString(
                                org.wheatgenetics.coordinate.R.string.AboutAlertDialogTitle);
                            msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                                org.wheatgenetics.coordinate.R.string.AboutAlertDialogMsg));
                        }
                    }

                    this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                        /* context       => */ this.activity                                     ,
                        /* title         => */ title                                             ,
                        /* versionName   => */ this.versionName                                  ,
                        /* msgs[]        => */ msgs                                              ,
                        /* suppressIndex => */ org.wheatgenetics.about.OtherApps.Index.COORDINATE,
                        /* versionOnClickListener => */ this.versionOnClickListener              );
                }
                this.aboutAlertDialog.show(); break;
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

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { if (null != joinedGridModel) this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion
    // endregion

    public void setExcludedCells(final android.os.Bundle bundle)
    { if (null != this.templateCreator) this.templateCreator.setExcludedCells(bundle); }
}