package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources
 * android.support.annotation.NonNull
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
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.JoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 *
 * org.wheatgenetics.coordinate.tc.TemplateCreator
 * org.wheatgenetics.coordinate.tc.TemplateCreator.Handler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class NavigationItemSelectedListener extends java.lang.Object implements
android.support.design.widget.NavigationView.OnNavigationItemSelectedListener,
org.wheatgenetics.coordinate.tc.TemplateCreator.Handler                      ,
org.wheatgenetics.coordinate.model.JoinedGridModels.Processor                ,
org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler
    {
        public abstract void createGrid();
        public abstract void loadGrid  (long gridId);
        public abstract void deleteGrid();

        public abstract void handleTemplateDeleted();

        public abstract java.lang.String initialExportFileName();
        public abstract void             exportGrid(java.lang.String fileName);

        public abstract void storeSoundOn(boolean soundOn);

        public abstract void closeDrawer();
    }

    // region Fields
    private final android.app.Activity                                                activity   ;
    private final java.lang.String                                                    versionName;
    private final org.wheatgenetics.coordinate.NavigationItemSelectedListener.Handler handler    ;
    private final android.view.View.OnClickListener                        versionOnClickListener;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;
    // endregion

    private org.wheatgenetics.coordinate.tc.TemplateCreator               templateCreator = null;
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialog = null;
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
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    private void showLongToast(final int text)
    { assert null != this.activity; this.showLongToast(this.activity.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.activity, text); }

    private void showShortToast(final int text)
    { assert null != this.activity; this.showShortToast(this.activity.getString(text)); }
    // endregion
    // endregion

    private void loadGridAfterSelect(
    final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        if (null != joinedGridModel)
            { assert null != this.handler; this.handler.loadGrid(joinedGridModel.getId()); }
    }

    private void deleteGrid() { assert null != this.handler; this.handler.deleteGrid(); }

    // region deleteTemplateAfterSelect() Private Methods
    @java.lang.SuppressWarnings("SimplifiableIfStatement")
    private void deleteTemplateAfterConfirm(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        final boolean success;
        if (null == templateModel)
            success = false;
        else
        {
            final long templateId = templateModel.getId();

            this.gridsTable().loadByTemplateId(templateId).processAll(this);      // delete entries

            if (this.gridsTable().deleteByTemplateId(templateId))                 // delete grids
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsOfTemplateSuccessToast);
            else
                this.showShortToast(org.wheatgenetics.coordinate
                    .R.string.NavigationItemSelectedListenerDeleteGridsOfTemplateFailToast);

            success = templatesTable().delete(templateId);                        // delete template
        }

        if (success)
        {
            this.showLongToast(org.wheatgenetics.coordinate
                .R.string.NavigationItemSelectedListenerDeleteTemplateSuccessToast);
            assert null != this.handler; this.handler.handleTemplateDeleted();
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
                    @java.lang.Override
                    public void run()
                    {
                        org.wheatgenetics.coordinate.NavigationItemSelectedListener
                            .this.deleteTemplateAfterConfirm(templateModel);
                    }
                });
    }
    // endregion

    private void storeSoundOn()
    { assert null != this.handler; this.handler.storeSoundOn(this.soundOn); }
    // endregion

    NavigationItemSelectedListener(final android.app.Activity activity,
    final java.lang.String versionName, final boolean soundOn,
    final org.wheatgenetics.coordinate.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener                     versionOnClickListener)
    {
        super();

        this.activity = activity; this.versionName            = versionName           ;
        this.handler  = handler ; this.versionOnClickListener = versionOnClickListener;

        this.soundOn = soundOn;
    }

    // region Overridden Methods
    // region android.support.design.widget.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            // The following ten ids that have names that start with "nav_" come from
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
                                    @java.lang.Override
                                    public void select(final int which)
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
                            @java.lang.Override
                            public void run()
                            {
                                org.wheatgenetics.coordinate
                                    .NavigationItemSelectedListener.this.deleteGrid();
                            }
                        }); break;

            case org.wheatgenetics.coordinate.R.id.nav_create_template:
                if (null == this.templateCreator) this.templateCreator =
                    new org.wheatgenetics.coordinate.tc.TemplateCreator(this.activity, this);
                this.templateCreator.create(); break;

            case org.wheatgenetics.coordinate.R.id.nav_delete_template:
                final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
                    this.templatesTable().loadUserDefined();
                if (null != templateModels) if (templateModels.size() > 0)
                {
                    final org.wheatgenetics.coordinate.SelectAlertDialog
                        selectTemplateToDeleteAlertDialog =
                            new org.wheatgenetics.coordinate.SelectAlertDialog(this.activity,
                                new org.wheatgenetics.coordinate.SelectAlertDialog.Handler()
                                {
                                    @java.lang.Override
                                    public void select(final int which)
                                    {
                                        org.wheatgenetics.coordinate.NavigationItemSelectedListener
                                            .this.deleteTemplateAfterSelect(
                                                templateModels.get(which));
                                    }
                                });
                    selectTemplateToDeleteAlertDialog.show(
                        org.wheatgenetics.coordinate
                            .R.string.NavigationItemSelectedListenerSelectDeleteTemplateTitle,
                        templateModels.titles());
                } break;

            case org.wheatgenetics.coordinate.R.id.nav_import_template: break;

            case org.wheatgenetics.coordinate.R.id.nav_export_grid:
                if (null == this.getExportFileNameAlertDialog) this.getExportFileNameAlertDialog =
                    new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(
                        this.activity, this);
                assert null != this.handler;
                this.getExportFileNameAlertDialog.show(this.handler.initialExportFileName());
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
    @java.lang.Override
    public void handleTemplateCreated(
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel) if (this.templatesTable().insert(templateModel) > 0)
            this.showLongToast(templateModel.getTitle() + " created");
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.JoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { assert null != joinedGridModel; this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion

    // region org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void handleGetFileNameDone(final java.lang.String fileName)
    { assert null != this.handler; this.handler.exportGrid(fileName); }
    // endregion
    // endregion

    boolean getSoundOn() { return this.soundOn; }
}