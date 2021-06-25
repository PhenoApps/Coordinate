package org.wheatgenetics.coordinate.grids;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.CollectorActivity;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.ge.GridExporter;
import org.wheatgenetics.coordinate.viewmodel.ExportingViewModel;

public class GridsActivity extends BackActivity {
    // region Constants
    private static final String
            TEMPLATE_ID_KEY = "templateId", PROJECT_ID_KEY = "projectId";
    private static final int EXPORT_GRID_REQUEST_CODE = 10;
    // endregion
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private ExportingViewModel gridsViewModel;
    private View.OnClickListener onCollectDataButtonClickListenerInstance = null,
            onDeleteButtonClickListenerInstance = null, onExportButtonClickListenerInstance = null;//lls
    private GridDeleter gridDeleterInstance = null;// lazy load
    // region exportGrid() Fields
    private GridExporter gridExporterInstance = null;// ll
    // endregion
    private GridExportPreprocessor
            gridExportPreprocessorInstance = null;                                          // lazy load
    private GridsAdapter gridsAdapter = null;
    private StatelessGridCreator
            statelessGridCreatorInstance = null;                                            // lazy load
    // endregion

    // region intent Private Methods
    @NonNull
    private static Intent INTENT(
            @NonNull final Context context) {
        return null == GridsActivity.INTENT_INSTANCE ?
                GridsActivity.INTENT_INSTANCE =
                        new Intent(context,
                                GridsActivity.class) :
                GridsActivity.INTENT_INSTANCE;
    }

    @NonNull
    private static Intent removeTEMPLATE_ID_KEY(
            @NonNull final Intent intent) {
        {
            final String TEMPLATE_ID_KEY =
                    GridsActivity.TEMPLATE_ID_KEY;
            if (intent.hasExtra(TEMPLATE_ID_KEY)) intent.removeExtra(TEMPLATE_ID_KEY);
        }
        return intent;
    }

    @NonNull
    private static Intent removePROJECT_ID_KEY(
            @NonNull final Intent intent) {
        {
            final String PROJECT_ID_KEY =
                    GridsActivity.PROJECT_ID_KEY;
            if (intent.hasExtra(PROJECT_ID_KEY)) intent.removeExtra(PROJECT_ID_KEY);
        }
        return intent;
    }

    // region Public Methods
    @NonNull
    public static Intent intent(
            @NonNull final Context context) {
        return GridsActivity.removePROJECT_ID_KEY(
                GridsActivity.removeTEMPLATE_ID_KEY(
                        GridsActivity.INTENT(context)));
    }
    // endregion

    @NonNull
    public static Intent templateIdIntent(
            @NonNull final Context context,
            @IntRange(from = 1) final long templateId) {
        return GridsActivity.removePROJECT_ID_KEY(
                GridsActivity.INTENT(context)).putExtra(
                GridsActivity.TEMPLATE_ID_KEY, templateId);
    }

    @NonNull
    public static Intent projectIdIntent(
            @NonNull final Context context,
            @IntRange(from = 1) final long projectId) {
        return GridsActivity.removeTEMPLATE_ID_KEY(
                GridsActivity.INTENT(context)).putExtra(
                GridsActivity.PROJECT_ID_KEY, projectId);
    }

    // region Private Methods
    private void startCollectorActivity(@IntRange(from = 1) final long gridId) {
        this.startActivity(
                CollectorActivity.intent(this, gridId));
    }
    // endregion

    private void notifyDataSetChanged() {
        if (null != this.gridsAdapter) this.gridsAdapter.notifyDataSetChanged();
    }

    // region deleteGrid() Private Methods
    @NonNull
    private GridDeleter gridDeleter() {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
                new GridDeleter(this,
                        new GridDeleter.Handler() {
                            @Override
                            public void respondToDeletedGrid() {
                                GridsActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.gridDeleterInstance;
    }
    // endregion
    // endregion

    private void deleteGrid(@IntRange(from = 1) final long gridId) {
        this.gridDeleter().deleteWithConfirm(gridId);
    }

    // region Export Private Methods
    // region exportGrid() Private Methods
    @NonNull
    private GridExporter gridExporter() {
        if (null == this.gridExporterInstance) this.gridExporterInstance =
                new GridExporter(this,
                        GridsActivity.EXPORT_GRID_REQUEST_CODE,
                        new GridDeleter.Handler() {
                            @Override
                            public void respondToDeletedGrid() {
                                GridsActivity.this.notifyDataSetChanged();
                            }
                        });
        return this.gridExporterInstance;
    }

    private void exportGrid() {
        this.gridExporter().export(
                this.gridsViewModel.getId(), this.gridsViewModel.getExportFileName());
    }
    // endregion

    private void exportGrid(@IntRange(from = 1) final long gridId,
                            final String fileName) {
        this.gridsViewModel.setIdAndExportFileName(gridId, fileName);
        this.exportGrid();
    }

    // region preprocessGridExport() Private Methods
    @NonNull
    private GridExportPreprocessor gridExportPreprocessor() {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
                new GridExportPreprocessor(this,
                        new GridExportPreprocessor.Handler() {
                            @Override
                            public void exportGrid(
                                    @IntRange(from = 1) final long gridId,
                                    final String fileName) {
                                GridsActivity.this.exportGrid(
                                        gridId, fileName);
                            }
                        });
        return this.gridExportPreprocessorInstance;
    }

    private void preprocessGridExport(@IntRange(from = 1) final long gridId) {
        this.gridExportPreprocessor().preprocess(gridId);
    }

    // region onButtonClickListener() Private Methods
    @NonNull
    private View.OnClickListener onCollectDataButtonClickListener() {
        if (null == this.onCollectDataButtonClickListenerInstance)
            this.onCollectDataButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.startCollectorActivity((Long) view.getTag());
                }
            };
        return this.onCollectDataButtonClickListenerInstance;
    }
    // endregion

    @NonNull
    private View.OnClickListener onDeleteButtonClickListener() {
        if (null == this.onDeleteButtonClickListenerInstance)
            this.onDeleteButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.deleteGrid((Long) view.getTag());
                }
            };
        return this.onDeleteButtonClickListenerInstance;
    }

    @NonNull
    private View.OnClickListener onExportButtonClickListener() {
        if (null == this.onExportButtonClickListenerInstance)
            this.onExportButtonClickListenerInstance = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (null != view) GridsActivity
                            .this.preprocessGridExport((Long) view.getTag());
                }
            };
        return this.onExportButtonClickListenerInstance;
    }

    @NonNull
    private AllGridsAdapter makeAllGridsAdapter() {
        return new AllGridsAdapter(this,
                this.onCollectDataButtonClickListener(), this.onDeleteButtonClickListener(),
                this.onExportButtonClickListener());
    }
    // endregion
    // endregion

    // region createGrid() Private Methods
    private void handleGridCreated(@IntRange(from = 1) final long gridId) {
        this.notifyDataSetChanged();
        this.startCollectorActivity(gridId);
    }

    @NonNull
    private StatelessGridCreator statelessGridCreator() {
        if (null == this.statelessGridCreatorInstance) this.statelessGridCreatorInstance =
                new StatelessGridCreator(
                        this, Types.CREATE_GRID,
                        new StatelessGridCreator.Handler() {
                            @Override
                            public void handleGridCreated(
                                    @IntRange(from = 1) final long gridId) {
                                GridsActivity.this.handleGridCreated(
                                        gridId);
                            }
                        });
        return this.statelessGridCreatorInstance;
    }

    private void createGrid() {
        statelessGridCreator().create();
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_grids);

        this.gridsViewModel = new ViewModelProvider(this).get(
                ExportingViewModel.class);

        final ListView gridsListView = this.findViewById(
                R.id.gridsListView);
        if (null != gridsListView) {
            {
                final Intent intent = this.getIntent();
                if (null == intent)
                    this.gridsAdapter = this.makeAllGridsAdapter();
                else {
                    final String TEMPLATE_ID_KEY =
                            GridsActivity.TEMPLATE_ID_KEY;
                    if (intent.hasExtra(TEMPLATE_ID_KEY)) {
                        @IntRange(from = 1) final long templateId =
                                intent.getLongExtra(TEMPLATE_ID_KEY, -1);
                        this.gridsAdapter =
                                new TemplateGridsAdapter(
                                        this, templateId,
                                        this.onCollectDataButtonClickListener(),
                                        this.onDeleteButtonClickListener(),
                                        this.onExportButtonClickListener());
                    } else {
                        final String PROJECT_ID_KEY =
                                GridsActivity.PROJECT_ID_KEY;
                        if (intent.hasExtra(PROJECT_ID_KEY)) {
                            @IntRange(from = 1) final long projectId =
                                    intent.getLongExtra(PROJECT_ID_KEY, -1);
                            this.gridsAdapter =
                                    new ProjectGridsAdapter(
                                            this, projectId,
                                            this.onCollectDataButtonClickListener(),
                                            this.onDeleteButtonClickListener(),
                                            this.onExportButtonClickListener());
                        } else this.gridsAdapter = this.makeAllGridsAdapter();
                    }
                }
            }
            gridsListView.setAdapter(this.gridsAdapter);
        }
    }
    // endregion

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_grids, menu);
        return true;
    }
    // endregion

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final String permissions[],
                                           @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final int grantResults[]) {
        boolean permissionFound = false;
        for (final String permission : permissions)
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                permissionFound = true;
                break;
            }

        if (permissionFound) for (final int grantResult : grantResults)
            if (PackageManager.PERMISSION_GRANTED == grantResult)
                // noinspection SwitchStatementWithTooFewBranches
                switch (requestCode) {
                    case GridsActivity.EXPORT_GRID_REQUEST_CODE:
                        this.exportGrid();
                        break;
                }
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // noinspection SwitchStatementWithTooFewBranches
        switch (requestCode) {
            case Types.CREATE_GRID:
                if (Activity.RESULT_OK == resultCode && null != data)
                    this.statelessGridCreator().continueExcluding(data.getExtras());
                break;
        }
    }

    // region MenuItem Event Handler
    public void onNewGridMenuItemClick(@SuppressWarnings({"unused"}) final MenuItem menuItem) {
        this.createGrid();
    }
    // endregion
}