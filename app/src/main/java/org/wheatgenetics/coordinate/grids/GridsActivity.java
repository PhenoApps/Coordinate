package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.content.pm.PackageManager
 * android.content.Intent
 * android.Manifest.permission
 * android.os.Bundle
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.lifecycle.ViewModelProvider
 *
 * org.wheatgenetics.coordinate.BackActivity
 * org.wheatgenetics.coordinate.CollectorActivity
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor
 * org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler
 * org.wheatgenetics.coordinate.ge.GridExporter
 *
 * org.wheatgenetics.coordinate.viewmodel.ExportingViewModel
 *
 * org.wheatgenetics.coordinate.grids.AllGridsAdapter
 * org.wheatgenetics.coordinate.grids.GridsAdapter
 * org.wheatgenetics.coordinate.grids.ProjectGridsAdapter
 * org.wheatgenetics.coordinate.grids.TemplateGridsAdapter
 */
public class GridsActivity extends org.wheatgenetics.coordinate.BackActivity
{
    // region Constants
    private static final java.lang.String
        TEMPLATE_ID_KEY = "templateId", PROJECT_ID_KEY = "projectId";
    private static final int EXPORT_GRID_REQUEST_CODE = 10;
    // endregion

    // region Fields
    private org.wheatgenetics.coordinate.viewmodel.ExportingViewModel gridsViewModel;

    private android.view.View.OnClickListener  onCollectDataButtonClickListenerInstance = null,
        onDeleteButtonClickListenerInstance = null, onExportButtonClickListenerInstance = null;//lls

    private org.wheatgenetics.coordinate.deleter.GridDeleter gridDeleterInstance = null;// lazy load

    // region exportGrid() Fields
    private org.wheatgenetics.coordinate.ge.GridExporter           gridExporterInstance = null;// ll
    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor
        gridExportPreprocessorInstance = null;                                          // lazy load
    // endregion

    private org.wheatgenetics.coordinate.grids.GridsAdapter gridsAdapter = null;

    private org.wheatgenetics.coordinate.gc.StatelessGridCreator
        statelessGridCreatorInstance = null;                                            // lazy load

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    // region Private Methods
    private void startCollectorActivity(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.startActivity(
            org.wheatgenetics.coordinate.CollectorActivity.intent(this, gridId));
    }

    private void notifyDataSetChanged()
    { if (null != this.gridsAdapter) this.gridsAdapter.notifyDataSetChanged(); }

    // region deleteGrid() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.GridDeleter gridDeleter()
    {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.GridDeleter(this,
                new org.wheatgenetics.coordinate.deleter.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.grids
                            .GridsActivity.this.notifyDataSetChanged();
                    }
                });
        return this.gridDeleterInstance;
    }

    private void deleteGrid(@androidx.annotation.IntRange(from = 1) final long gridId)
    { this.gridDeleter().deleteWithConfirm(gridId); }
    // endregion

    // region Export Private Methods
    // region exportGrid() Private Methods
    @androidx.annotation.NonNull private org.wheatgenetics.coordinate.ge.GridExporter gridExporter()
    {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
            new org.wheatgenetics.coordinate.ge.GridExporter(this,
                org.wheatgenetics.coordinate.grids.GridsActivity.EXPORT_GRID_REQUEST_CODE,
                new org.wheatgenetics.coordinate.deleter.GridDeleter.Handler()
                {
                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.grids
                            .GridsActivity.this.notifyDataSetChanged();
                    }
                });
        return this.gridExporterInstance;
    }

    private void exportGrid()
    {
        this.gridExporter().export(
            this.gridsViewModel.getId(), this.gridsViewModel.getExportFileName());
    }

    private void exportGrid(@androidx.annotation.IntRange(from = 1) final long gridId,
    final java.lang.String fileName)
    { this.gridsViewModel.setIdAndExportFileName(gridId, fileName); this.exportGrid(); }
    // endregion

    // region preprocessGridExport() Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.ge.GridExportPreprocessor gridExportPreprocessor()
    {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
            new org.wheatgenetics.coordinate.ge.GridExportPreprocessor(this,
                new org.wheatgenetics.coordinate.ge.GridExportPreprocessor.Handler()
                {
                    @java.lang.Override public void exportGrid(
                    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                                            final java.lang.String fileName)
                    {
                        org.wheatgenetics.coordinate.grids.GridsActivity.this.exportGrid(
                            gridId, fileName);
                    }
                });
        return this.gridExportPreprocessorInstance;
    }

    private void preprocessGridExport(@androidx.annotation.IntRange(from = 1) final long gridId)
    { this.gridExportPreprocessor().preprocess(gridId); }
    // endregion
    // endregion

    // region onButtonClickListener() Private Methods
    @androidx.annotation.NonNull
    private android.view.View.OnClickListener onCollectDataButtonClickListener()
    {
        if (null == this.onCollectDataButtonClickListenerInstance)
            this.onCollectDataButtonClickListenerInstance = new android.view.View.OnClickListener()
                {
                    @java.lang.Override public void onClick(final android.view.View view)
                    {
                        if (null != view) org.wheatgenetics.coordinate.grids.GridsActivity
                            .this.startCollectorActivity((java.lang.Long) view.getTag());
                    }
                };
        return this.onCollectDataButtonClickListenerInstance;
    }

    @androidx.annotation.NonNull
    private android.view.View.OnClickListener onDeleteButtonClickListener()
    {
        if (null == this.onDeleteButtonClickListenerInstance)
            this.onDeleteButtonClickListenerInstance = new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                {
                    if (null != view) org.wheatgenetics.coordinate.grids.GridsActivity
                        .this.deleteGrid((java.lang.Long) view.getTag());
                }
            };
        return this.onDeleteButtonClickListenerInstance;
    }

    @androidx.annotation.NonNull
    private android.view.View.OnClickListener onExportButtonClickListener()
    {
        if (null == this.onExportButtonClickListenerInstance)
            this.onExportButtonClickListenerInstance = new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                {
                    if (null != view) org.wheatgenetics.coordinate.grids.GridsActivity
                        .this.preprocessGridExport((java.lang.Long) view.getTag());
                }
            };
        return this.onExportButtonClickListenerInstance;
    }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.grids.AllGridsAdapter makeAllGridsAdapter()
    {
        return new org.wheatgenetics.coordinate.grids.AllGridsAdapter(this,
            this.onCollectDataButtonClickListener(), this.onDeleteButtonClickListener(),
            this.onExportButtonClickListener     ());
    }

    // region createGrid() Private Methods
    private void handleGridCreated(@androidx.annotation.IntRange(from = 1) final long gridId)
    { this.notifyDataSetChanged(); this.startCollectorActivity(gridId); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatelessGridCreator statelessGridCreator()
    {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
            new org.wheatgenetics.coordinate.gc.StatelessGridCreator(
                this, org.wheatgenetics.coordinate.Types.CREATE_GRID,
                new org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler()
                {
                    @java.lang.Override public void handleGridCreated(
                    @androidx.annotation.IntRange(from = 1) final long gridId)
                    {
                        org.wheatgenetics.coordinate.grids.GridsActivity.this.handleGridCreated(
                            gridId);
                    }
                });
        return this.statelessGridCreatorInstance;
    }

    private void createGrid() { statelessGridCreator().create(); }
    // endregion

    // region intent Private Methods
    @androidx.annotation.NonNull private static android.content.Intent INTENT(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        return null == org.wheatgenetics.coordinate.grids.GridsActivity.INTENT_INSTANCE ?
            org.wheatgenetics.coordinate.grids.GridsActivity.INTENT_INSTANCE =
                new android.content.Intent(context,
                    org.wheatgenetics.coordinate.grids.GridsActivity.class) :
            org.wheatgenetics.coordinate.grids.GridsActivity.INTENT_INSTANCE;
    }

    @androidx.annotation.NonNull private static android.content.Intent removeTEMPLATE_ID_KEY(
    @androidx.annotation.NonNull final android.content.Intent intent)
    {
        {
            final java.lang.String TEMPLATE_ID_KEY =
                org.wheatgenetics.coordinate.grids.GridsActivity.TEMPLATE_ID_KEY;
            if (intent.hasExtra(TEMPLATE_ID_KEY)) intent.removeExtra(TEMPLATE_ID_KEY);
        }
        return intent;
    }

    @androidx.annotation.NonNull private static android.content.Intent removePROJECT_ID_KEY(
    @androidx.annotation.NonNull final android.content.Intent intent)
    {
        {
            final java.lang.String PROJECT_ID_KEY =
                org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY;
            if (intent.hasExtra(PROJECT_ID_KEY)) intent.removeExtra(PROJECT_ID_KEY);
        }
        return intent;
    }
    // endregion
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_grids);

        this.gridsViewModel = new androidx.lifecycle.ViewModelProvider(this).get(
            org.wheatgenetics.coordinate.viewmodel.ExportingViewModel.class);

        final android.widget.ListView gridsListView = this.findViewById(
            org.wheatgenetics.coordinate.R.id.gridsListView);
        if (null != gridsListView)
        {
            {
                final android.content.Intent intent = this.getIntent();
                if (null == intent)
                    this.gridsAdapter = this.makeAllGridsAdapter();
                else
                {
                    final java.lang.String TEMPLATE_ID_KEY =
                        org.wheatgenetics.coordinate.grids.GridsActivity.TEMPLATE_ID_KEY;
                    if (intent.hasExtra(TEMPLATE_ID_KEY))
                    {
                        @androidx.annotation.IntRange(from = 1) final long templateId =
                            intent.getLongExtra(TEMPLATE_ID_KEY,-1);
                        this.gridsAdapter =
                            new org.wheatgenetics.coordinate.grids.TemplateGridsAdapter(
                                this, templateId,
                                this.onCollectDataButtonClickListener(),
                                this.onDeleteButtonClickListener     (),
                                this.onExportButtonClickListener     ());
                    }
                    else
                    {
                        final java.lang.String PROJECT_ID_KEY =
                            org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY;
                        if (intent.hasExtra(PROJECT_ID_KEY))
                        {
                            @androidx.annotation.IntRange(from = 1) final long projectId =
                                intent.getLongExtra(PROJECT_ID_KEY,-1);
                            this.gridsAdapter =
                                new org.wheatgenetics.coordinate.grids.ProjectGridsAdapter(
                                    this, projectId,
                                    this.onCollectDataButtonClickListener(),
                                    this.onDeleteButtonClickListener     (),
                                    this.onExportButtonClickListener     ());
                        }
                        else this.gridsAdapter = this.makeAllGridsAdapter();
                    }
                }
            }
            gridsListView.setAdapter(this.gridsAdapter);
        }
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        this.getMenuInflater().inflate(org.wheatgenetics.coordinate.R.menu.menu_grids, menu);
        return true;
    }

    @java.lang.Override public void onRequestPermissionsResult(final int requestCode,
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final java.lang.String permissions[],
    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.NonNull
        final int grantResults[])
    {
        boolean permissionFound = false;
        for (final java.lang.String permission: permissions)
            if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
                { permissionFound = true; break; }

        if (permissionFound) for (final int grantResult: grantResults)
            if (android.content.pm.PackageManager.PERMISSION_GRANTED == grantResult)
                // noinspection SwitchStatementWithTooFewBranches
                switch (requestCode)
                {
                    case org.wheatgenetics.coordinate.grids.GridsActivity.EXPORT_GRID_REQUEST_CODE:
                        this.exportGrid(); break;
                }
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // noinspection SwitchStatementWithTooFewBranches
        switch (requestCode)
        {
            case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                if (android.app.Activity.RESULT_OK == resultCode && null != data)
                    this.statelessGridCreator().continueExcluding(data.getExtras());
                break;
        }
    }
    // endregion

    // region MenuItem Event Handler
    public void onNewGridMenuItemClick(@java.lang.SuppressWarnings({"unused"})
    final android.view.MenuItem menuItem) { this.createGrid(); }
    // endregion

    // region Public Methods
    @androidx.annotation.NonNull public static android.content.Intent intent(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        return org.wheatgenetics.coordinate.grids.GridsActivity.removePROJECT_ID_KEY(
            org.wheatgenetics.coordinate.grids.GridsActivity.removeTEMPLATE_ID_KEY(
                org.wheatgenetics.coordinate.grids.GridsActivity.INTENT(context)));
    }

    @androidx.annotation.NonNull public static android.content.Intent templateIdIntent(
    @androidx.annotation.NonNull            final android.content.Context context   ,
    @androidx.annotation.IntRange(from = 1) final long                    templateId)
    {
        return org.wheatgenetics.coordinate.grids.GridsActivity.removePROJECT_ID_KEY(
            org.wheatgenetics.coordinate.grids.GridsActivity.INTENT(context)).putExtra(
                org.wheatgenetics.coordinate.grids.GridsActivity.TEMPLATE_ID_KEY, templateId);
    }

    @androidx.annotation.NonNull public static android.content.Intent projectIdIntent(
    @androidx.annotation.NonNull            final android.content.Context context  ,
    @androidx.annotation.IntRange(from = 1) final long                    projectId)
    {
        return org.wheatgenetics.coordinate.grids.GridsActivity.removeTEMPLATE_ID_KEY(
            org.wheatgenetics.coordinate.grids.GridsActivity.INTENT(context)).putExtra(
                org.wheatgenetics.coordinate.grids.GridsActivity.PROJECT_ID_KEY, projectId);
    }
    // endregion
}