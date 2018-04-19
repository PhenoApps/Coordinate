package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.content.res.Resources
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.annotation.StringRes
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.view.MenuItem
 * android.view.View.OnClickListener
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
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
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
 * org.wheatgenetics.coordinate.MainActivity.RequestCode
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class NavigationItemSelectedListener extends java.lang.Object implements
android.support.design.widget.NavigationView.OnNavigationItemSelectedListener,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler                      ,
org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler
    {
        public abstract void             createGrid                  ();
        public abstract void             loadGrid                    (long gridId);
        public abstract void             deleteGrid                  ();
        public abstract java.lang.String getInitialGridExportFileName();
        public abstract void             exportGrid                  (java.lang.String fileName);

        public abstract void handleGridDeleted();
        public abstract void exportTemplate   (
            org.wheatgenetics.coordinate.model.TemplateModel templateModel,
            java.lang.String                                 fileName     );

        public abstract long getProjectId        ();
        public abstract void loadProject         (long projectId);
        public abstract void clearProject        ();
        public abstract void handleProjectDeleted(long projectId);
        public abstract void exportProject       (long projectId, java.lang.String directoryName);

        public abstract void storeSoundOn(boolean soundOn);

        public abstract void closeDrawer();
    }

    private enum TemplateOperation {       DELETE, EXPORT }
    private enum ProjectOperation  { LOAD, DELETE, EXPORT }
    // endregion

    // region Fields
    private final android.app.Activity activity                                            ;
    private final int                  createTemplateRequestCode, importTemplateRequestCode;
    private final java.lang.String     versionName                                         ;
    private final org.wheatgenetics.coordinate.NavigationItemSelectedListener.Handler handler;
    private final android.view.View.OnClickListener                    versionOnClickListener;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;
    private org.wheatgenetics.coordinate.database.ProjectsTable  projectsTableInstance  = null;
    // endregion

    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getGridExportFileNameAlertDialog = null;
    private org.wheatgenetics.coordinate.tc.TemplateCreator templateCreator = null;
    private android.content.Intent                          intentInstance  = null;
    private org.wheatgenetics.coordinate.pc.ProjectCreator  projectCreator  = null;
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getProjectExportFileNameAlertDialog = null;
    private long                                     exportProjectId  =    0;
    private boolean                                  soundOn                ;
    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = null;
    // endregion

    // region Private Methods
    // region Table Private Methods
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.activity);
        return this.entriesTableInstance;
    }

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

    private void showLongToast(@android.support.annotation.StringRes final int text)
    { assert null != this.activity; this.showLongToast(this.activity.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.activity, text); }

    private void showShortToast(@android.support.annotation.StringRes final int text)
    { assert null != this.activity; this.showShortToast(this.activity.getString(text)); }
    // endregion
    // endregion

    // region Grid Private Methods
    private void loadGridAfterSelect(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        if (null != joinedGridModel)
            { assert null != this.handler; this.handler.loadGrid(joinedGridModel.getId()); }
    }

    private void deleteGrid() { assert null != this.handler; this.handler.deleteGrid(); }

    private void exportGrid(final java.lang.String fileName)
    { assert null != this.handler; this.handler.exportGrid(fileName); }
    // endregion

    // region Template Private Methods
    // region Delete Template Private Methods
    @java.lang.SuppressWarnings({"SimplifiableIfStatement"})
    private void deleteTemplateAfterConfirm(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final boolean success;
        if (null == templateModel)
            success = false;
        else
        {
            final long templateId = templateModel.getId();

            {
                final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
                    this.gridsTable().loadByTemplateId(templateId);
                if (null != joinedGridModels) joinedGridModels.processAll(this);  // delete entries
            }

            if (this.gridsTable().deleteByTemplateId(templateId))                 // delete grids
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsSuccessToast);
            else
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsFailToast);

            success = templatesTable().delete(templateId);                        // delete template
        }

        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteTemplateSuccessToast);
            assert null != this.handler; this.handler.handleGridDeleted();
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
                        org.wheatgenetics.coordinate.NavigationItemSelectedListener
                            .this.deleteTemplateAfterConfirm(templateModel);
                    }
                });
    }
    // endregion

    // region Export Template Private Methods
    private void exportTemplateAfterGettingFileName(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final java.lang.String                                 fileName     )
    { assert null != this.handler; this.handler.exportTemplate(templateModel, fileName); }

    private void exportTemplateAfterSelect(
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
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener.this
                                    .exportTemplateAfterGettingFileName(templateModel, fileName);
                            }
                        });
            getTemplateExportFileNameAlertDialog.show(templateModel.getTitle());
        }
    }
    // endregion

    private void selectUserDefinedTemplate(
    final org.wheatgenetics.coordinate.NavigationItemSelectedListener.TemplateOperation
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
                    case DELETE: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.deleteTemplateAfterSelect(templateModels.get(which));
                            }
                        }; break;

                    case EXPORT: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.exportTemplateAfterSelect(templateModels.get(which));
                            }
                        }; break;

                    default: handler = null; break;
                }

                selectTemplateAlertDialog =
                    new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity, handler);
            }

            @android.support.annotation.StringRes final int title;
            switch (templateOperation)
            {
                case DELETE: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectDeleteTemplateTitle; break;

                case EXPORT: title = org.wheatgenetics.coordinate.R.string
                    .NavigationItemSelectedListenerSelectExportTemplateTitle; break;

                default: title = 0;
            }
            selectTemplateAlertDialog.show(title, templateModels.titles());
        }
    }

    private android.content.Intent intent()
    {
        if (null == this.intentInstance)
        {
            this.intentInstance =
                new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
            this.intentInstance.setType("text/xml");
        }
        return this.intentInstance;
    }
    // endregion

    // region Project Private Methods
    private void loadProjectAfterSelect(
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        if (null != projectModel)
            { assert null != this.handler; this.handler.loadProject(projectModel.getId()); }
    }

    // region Delete Project Private Methods
    private void deleteProjectAfterConfirm(
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    {
        final boolean success  ;
        final long    projectId;
        if (null == projectModel)
            { success = false; projectId = 0; }
        else
        {
            projectId = projectModel.getId();

            {
                final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
                    this.gridsTable().loadByProjectId(projectId);
                if (null != joinedGridModels) joinedGridModels.processAll(this);   // delete entries
            }

            if (this.gridsTable().deleteByProjectId(projectId))                    // delete grids
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsSuccessToast);
            else
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsFailToast);

            success = projectsTable().delete(projectId);                           // delete project
        }

        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteProjectSuccessToast);
            assert null != this.handler; this.handler.handleProjectDeleted(projectId);
        }
        else this.showLongToast(org.wheatgenetics.coordinate
            .R.string.NavigationItemSelectedListenerDeleteProjectFailToast);
    }

    private void deleteProjectAfterSelect(
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
                    org.wheatgenetics.coordinate.NavigationItemSelectedListener
                        .this.deleteProjectAfterConfirm(projectModel);
                }
            });
    }
    // endregion

    // region Export Project Private Methods
    private void exportProjectAfterGettingDirectoryName(final java.lang.String directoryName)
    {
        assert null != this.handler;
        this.handler.exportProject(this.exportProjectId, directoryName);

        this.exportProjectId = 0;
    }

    private void exportProjectAfterSelect(
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
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.exportProjectAfterGettingDirectoryName(fileName);
                            }
                        });
            this.exportProjectId = projectModel.getId();
            this.getProjectExportFileNameAlertDialog.show(projectModel.getTitle());
        }
    }
    // endregion

    private void selectProject(
    final org.wheatgenetics.coordinate.NavigationItemSelectedListener.ProjectOperation
        projectOperation)
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels;
        switch (projectOperation)
        {
            case LOAD:
                assert null != this.handler;
                projectModels = this.projectsTable().loadExceptFor(this.handler.getProjectId());
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
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.loadProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    case DELETE: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.deleteProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    case EXPORT: handler =
                        new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                        {
                            @java.lang.Override public void select(final int which)
                            {
                                org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                    .this.exportProjectAfterSelect(projectModels.get(which));
                            }
                        }; break;

                    default: handler = null; break;
                }

                selectProjectAlertDialog =
                    new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity, handler);
            }

            @android.support.annotation.StringRes final int title;
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
    // endregion

    private void storeSoundOn()
    { assert null != this.handler; this.handler.storeSoundOn(this.soundOn); }
    // endregion

    NavigationItemSelectedListener(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.MainActivity.RequestCode final int createTemplateRequestCode,
    @org.wheatgenetics.coordinate.MainActivity.RequestCode final int importTemplateRequestCode,
    final java.lang.String versionName, final boolean soundOn,
    final org.wheatgenetics.coordinate.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener                    versionOnClickListener)
    {
        super();

        this.activity                  = activity                 ;
        this.createTemplateRequestCode = createTemplateRequestCode;
        this.importTemplateRequestCode = importTemplateRequestCode;

        this.versionName            = versionName           ; this.handler = handler;
        this.versionOnClickListener = versionOnClickListener; this.soundOn = soundOn;
    }

    // region Overridden Methods
    // region android.support.design.widget.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            // The following sixteen ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.coordinate.R.id.nav_create_grid:
                assert null != this.handler; this.handler.createGrid(); break;

            case org.wheatgenetics.coordinate.R.id.nav_load_grid:
                final org.wheatgenetics.coordinate.model.JoinedGridModels joinedGridModels =
                    this.gridsTable().load();
                if (null != joinedGridModels) if (joinedGridModels.size() > 0)
                {
                    final org.wheatgenetics.coordinate.SelectAlertDialog
                        selectGridToLoadAlertDialog =
                            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                                {
                                    @java.lang.Override public void select(final int which)
                                    {
                                        org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                            .this.loadGridAfterSelect(joinedGridModels.get(which));
                                    }
                                });
                    selectGridToLoadAlertDialog.show(
                        org.wheatgenetics.coordinate
                            .R.string.NavigationItemSelectedListenerLoadGridTitle,
                        joinedGridModels.names());
                } break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_grid:
                org.wheatgenetics.coordinate.Utils.confirm(
                    /* context => */ this.activity,
                    /* message => */ org.wheatgenetics.coordinate
                        .R.string.NavigationItemSelectedListenerDeleteGridConfirmation,
                    /* yesRunnable => */ new java.lang.Runnable()
                        {
                            @java.lang.Override public void run()
                            {
                                org.wheatgenetics.coordinate
                                    .NavigationItemSelectedListener.this.deleteGrid();
                            }
                        }); break;

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
                                    org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                        .this.exportGrid(fileName);
                                }
                            });
                assert null != this.handler;
                this.getGridExportFileNameAlertDialog.show(
                    this.handler.getInitialGridExportFileName());
                break;



            case org.wheatgenetics.coordinate.R.id.nav_create_template:
                if (null == this.templateCreator)
                    this.templateCreator = new org.wheatgenetics.coordinate.tc.TemplateCreator(
                        this.activity, this.createTemplateRequestCode, this);
                this.templateCreator.create(); break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate
                    .NavigationItemSelectedListener.TemplateOperation.DELETE);
                break;

            case org.wheatgenetics.coordinate.R.id.nav_export_template:
                this.selectUserDefinedTemplate(org.wheatgenetics.coordinate
                    .NavigationItemSelectedListener.TemplateOperation.EXPORT);
                break;

            case org.wheatgenetics.coordinate.R.id.nav_import_template:
                assert null != this.activity;
                this.activity.startActivityForResult(this.intent(), this.importTemplateRequestCode);
                break;



            case org.wheatgenetics.coordinate.R.id.nav_create_project:
                if (null == this.projectCreator) this.projectCreator =
                    new org.wheatgenetics.coordinate.pc.ProjectCreator(this.activity);
                this.projectCreator.create();
                break;

            case org.wheatgenetics.coordinate.R.id.nav_load_project:
                this.selectProject(org.wheatgenetics.coordinate
                    .NavigationItemSelectedListener.ProjectOperation.LOAD);
                break;

            case org.wheatgenetics.coordinate.R.id.nav_clear_project:
                assert null != this.handler; this.handler.clearProject(); break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_project:
                this.selectProject(org.wheatgenetics.coordinate
                    .NavigationItemSelectedListener.ProjectOperation.DELETE);
                break;

            case org.wheatgenetics.coordinate.R.id.nav_export_project:
                this.selectProject(org.wheatgenetics.coordinate
                    .NavigationItemSelectedListener.ProjectOperation.EXPORT);
                break;



            case org.wheatgenetics.coordinate.R.id.nav_turn_sound_on:
                this.soundOn = true; this.storeSoundOn(); break;

            case org.wheatgenetics.coordinate.R.id.nav_turn_sound_off:
                this.soundOn = false; this.storeSoundOn(); break;

            case org.wheatgenetics.coordinate.R.id.nav_show_about:
                if (null == this.aboutAlertDialog)
                {
                    final java.lang.String title, msgs[];
                    {
                        assert null != this.activity;
                        final android.content.res.Resources resources =
                            this.activity.getResources();

                        assert null != resources; title = resources.getString(
                            org.wheatgenetics.coordinate.R.string.AboutAlertDialogTitle);
                        msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                            org.wheatgenetics.coordinate.R.string.AboutAlertDialogMsg));
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
        assert null != this.handler; this.handler.closeDrawer(); return true;
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @java.lang.Override public void handleTemplateCreated(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            final java.lang.String not =
                this.templatesTable().insert(templateModel) > 0 ? "" : " not";
            this.showLongToast(templateModel.getTitle() + not + " created");
        }
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.JoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { assert null != joinedGridModel; this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion
    // endregion

    // region Package Methods
    void setExcludedCells(final android.os.Bundle bundle)
    { if (null != this.templateCreator) this.templateCreator.setExcludedCells(bundle); }

    boolean getSoundOn() { return this.soundOn; }
    // endregion
}