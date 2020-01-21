package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.content.pm.PackageManager
 * android.content.Intent
 * android.Manifest.permission
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.widget.AdapterView<?>
 * android.widget.AdapterView.OnItemClickListener
 * android.widget.ListView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.appcompat.app.AppCompatActivity
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types
 *
 * org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
 *
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator
 * org.wheatgenetics.coordinate.gc.StatelessGridCreator.Handler
 *
 * org.wheatgenetics.coordinate.ge.GridExporter
 *
 * org.wheatgenetics.coordinate.grids.AllGridsAdapter
 * org.wheatgenetics.coordinate.grids.CollectorActivity
 * org.wheatgenetics.coordinate.grids.GridClickAlertDialog
 * org.wheatgenetics.coordinate.grids.GridClickAlertDialog.Handler
 * org.wheatgenetics.coordinate.grids.GridsAdapter
 * org.wheatgenetics.coordinate.grids.ProjectGridsAdapter
 * org.wheatgenetics.coordinate.grids.TemplateGridsAdapter
 */
public class GridsActivity extends androidx.appcompat.app.AppCompatActivity
{
    // region Consts
    private static final java.lang.String
        TEMPLATE_ID_KEY = "templateId", PROJECT_ID_KEY = "projectId";
    private static final int EXPORT_GRID = 10;
    // endregion

    // region Fields
    private org.wheatgenetics.coordinate.grids.GridsAdapter gridsAdapter = null;

    private android.content.Intent                       collectorIntentInstance = null;// lazy load
    private org.wheatgenetics.coordinate.ge.GridExporter gridExporterInstance    = null;// lazy load
    private org.wheatgenetics.coordinate.grids.GridClickAlertDialog
        gridClickAlertDialogInstance = null;                                            // lazy load

    private org.wheatgenetics.coordinate.gc.StatelessGridCreator
        statelessGridCreatorInstance = null;                                            // lazy load

    private static android.content.Intent INTENT_INSTANCE = null;                       // lazy load
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.grids.AllGridsAdapter makeAllGridsAdapter()
    { return new org.wheatgenetics.coordinate.grids.AllGridsAdapter(this); }

    // region gridClickAlertDialog() Private Methods
    // region collectData() gridClickAlertDialog() Private Methods
    @androidx.annotation.NonNull private android.content.Intent collectorIntent()
    {
        if (null == this.collectorIntentInstance)
            this.collectorIntentInstance = new android.content.Intent(this,
                org.wheatgenetics.coordinate.grids.CollectorActivity.class);
        return this.collectorIntentInstance;
    }

    private void collectData(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        this.startActivity(this.collectorIntent().putExtra(
            org.wheatgenetics.coordinate.grids.CollectorActivity.GRID_ID_KEY, gridId));
    }
    // endregion

    private void notifyDataSetChanged()
    { if (null != this.gridsAdapter) this.gridsAdapter.notifyDataSetChanged(); }

    // region exportGrid() gridClickAlertDialog() Private Methods
    private org.wheatgenetics.coordinate.ge.GridExporter gridExporter()
    {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
            new org.wheatgenetics.coordinate.ge.GridExporter(this,
                org.wheatgenetics.coordinate.grids.GridsActivity.EXPORT_GRID,
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

    private void exportGrid(@androidx.annotation.IntRange(from = 1) final long gridId,
    final java.lang.String fileName) { this.gridExporter().export(gridId, fileName); }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.grids.GridClickAlertDialog gridClickAlertDialog()
    {
        if (null == this.gridClickAlertDialogInstance) this.gridClickAlertDialogInstance =
            new org.wheatgenetics.coordinate.grids.GridClickAlertDialog(this,
                new org.wheatgenetics.coordinate.grids.GridClickAlertDialog.Handler()
                {
                    @java.lang.Override public void collectData(
                    @androidx.annotation.IntRange(from = 1) final long gridId)
                    { org.wheatgenetics.coordinate.grids.GridsActivity.this.collectData(gridId); }

                    @java.lang.Override public void exportGrid(
                    @androidx.annotation.IntRange(from = 1) final long             gridId  ,
                                                            final java.lang.String fileName)
                    {
                        org.wheatgenetics.coordinate.grids.GridsActivity
                            .this.exportGrid(gridId, fileName);
                    }

                    @java.lang.Override public void respondToDeletedGrid()
                    {
                        org.wheatgenetics.coordinate.grids.GridsActivity
                            .this.notifyDataSetChanged();
                    }
                });
        return this.gridClickAlertDialogInstance;
    }

    private void showGridClickAlertDialog(@androidx.annotation.IntRange(from = 1) final long gridId)
    { this.gridClickAlertDialog().show(gridId); }
    // endregion

    // region createGrid() Private Methods
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
                        org.wheatgenetics.coordinate.grids
                            .GridsActivity.this.notifyDataSetChanged();
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
    @androidx.annotation.Nullable android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.coordinate.R.layout.activity_grids);

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
                                this, templateId);
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
                                    this, projectId);
                        }
                        else this.gridsAdapter = this.makeAllGridsAdapter();
                    }
                }
            }
            gridsListView.setAdapter(this.gridsAdapter);
            gridsListView.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener()
                {
                    @java.lang.Override public void onItemClick(
                    final android.widget.AdapterView<?> parent, final android.view.View view,
                    final int position, final long id)
                    {
                        org.wheatgenetics.coordinate.grids.GridsActivity
                            .this.showGridClickAlertDialog(id);
                    }
                });
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
                    case org.wheatgenetics.coordinate.grids.GridsActivity.EXPORT_GRID:
                        if (null != this.gridExporterInstance) this.gridExporterInstance.export();
                        break;
                }
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (android.app.Activity.RESULT_OK == resultCode && null != data)
            // noinspection SwitchStatementWithTooFewBranches
            switch (requestCode)
            {
                case org.wheatgenetics.coordinate.Types.CREATE_GRID:
                    if (null != this.statelessGridCreatorInstance)
                        this.statelessGridCreatorInstance.setExcludedCells(data.getExtras());
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