package org.wheatgenetics.coordinate.nisl;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.navigation.NavigationView;

import org.wheatgenetics.androidlibrary.Utils;
import org.wheatgenetics.coordinate.AboutAlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.SelectAlertDialog;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.deleter.GridDeleter;
import org.wheatgenetics.coordinate.deleter.ProjectDeleter;
import org.wheatgenetics.coordinate.deleter.TemplateDeleter;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.ProjectModels;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.pc.ProjectCreator;
import org.wheatgenetics.coordinate.pe.ProjectExportPreprocessor;
import org.wheatgenetics.coordinate.preference.PreferenceActivity;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.coordinate.te.TemplateExportPreprocessor;
import org.wheatgenetics.coordinate.ti.TemplateImportPreprocessor;

public class NavigationItemSelectedListener implements
        TemplateCreator.Handler {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final Activity activity;
    @Types.RequestCode
    private final int
            createTemplateRequestCode, clickUniquenessRequestCode;
    private final int preprocessTemplateImportRequestCode;
    // endregion
    private final String versionName;
    @NonNull
    private final
    NavigationItemSelectedListener.Handler handler;
    private final View.OnClickListener versionOnClickListener;
    // region Table Fields
    private GridsTable gridsTableInstance = null;// ll
    private TemplatesTable templatesTableInstance = null;// ll
    private ProjectsTable projectsTableInstance = null;// ll
    // endregion
    // region Lazy Load Fields
    private GridDeleter gridDeleterInstance = null;// lazy load
    private ManageGridAlertDialog
            manageGridAlertDialogInstance = null;                                           // lazy load
    private GridExportPreprocessor
            gridExportPreprocessorInstance = null;                                          // lazy load
    // endregion
    private TemplateCreator templateCreatorInstance = null; // ll
    private TemplateImportPreprocessor
            templateImportPreprocessorInstance = null;                                      // lazy load
    private TemplateExportPreprocessor
            templateExportPreprocessorInstance = null;                                      // lazy load
    private TemplateDeleter templateDeleterInstance = null;//ll
    private ProjectCreator projectCreatorInstance = null; // ll
    private ManageProjectAlertDialog
            manageProjectAlertDialogInstance = null;                                        // lazy load
    private ProjectDeleter projectDeleterInstance = null; // ll
    private ProjectExportPreprocessor
            projectExportPreprocessorInstance = null;                                       // lazy load
    private AboutAlertDialog aboutAlertDialogInstance = null;     // ll
    public NavigationItemSelectedListener(
            @NonNull final Activity activity,
            @Types.RequestCode final int createTemplateRequestCode,
            final int preprocessTemplateImportRequestCode,
            @Types.RequestCode final int clickUniquenessRequestCode,
            final String versionName,
            @NonNull final
            NavigationItemSelectedListener.Handler handler,
            final View.OnClickListener versionOnClickListener) {
        super();

        this.activity = activity;
        this.createTemplateRequestCode = createTemplateRequestCode;
        this.preprocessTemplateImportRequestCode = preprocessTemplateImportRequestCode;
        this.clickUniquenessRequestCode = clickUniquenessRequestCode;
        this.versionName = versionName;
        this.handler = handler;
        this.versionOnClickListener = versionOnClickListener;
    }

    // region Private Methods
    // region Table Private Methods
    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }
    // endregion
    // endregion

    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    private void showLongToast(final String text) {
        Utils.showLongToast(this.activity, text);
    }

    // region Grid Private Methods
    // region manageGrid() Grid Private Methods
    // region loadGrid() manageGrid() Grid Private Methods
    private void loadGridAfterSelect(@Nullable final JoinedGridModel joinedGridModel) {
        if (null != joinedGridModel) this.handler.loadGrid(joinedGridModel.getId());
    }
    // endregion

    private void loadGrid() {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().load();
        if (null != baseJoinedGridModels) if (baseJoinedGridModels.size() > 0) {
            final SelectAlertDialog selectGridToLoadAlertDialog =
                    new SelectAlertDialog(this.activity,
                            new SelectAlertDialog.Handler() {
                                @Override
                                public void select(final int which) {
                                    NavigationItemSelectedListener
                                            .this.loadGridAfterSelect(baseJoinedGridModels.get(which));
                                }
                            });
            selectGridToLoadAlertDialog.show(
                    R.string.NavigationItemSelectedListenerLoadGridTitle,
                    baseJoinedGridModels.names());
        }
    }

    // region deleteGrid() manageGrid() Grid Private Methods
    private void respondToDeleteGrid() {
        this.handler.respondToDeleteGrid();
    }

    @NonNull
    private GridDeleter gridDeleter() {
        if (null == this.gridDeleterInstance) this.gridDeleterInstance =
                new GridDeleter(this.activity,
                        new GridDeleter.Handler() {
                            @Override
                            public void respondToDeletedGrid() {
                                NavigationItemSelectedListener
                                        .this.respondToDeleteGrid();
                            }
                        });
        return this.gridDeleterInstance;
    }
    // endregion

    private void deleteGrid() {
        this.gridDeleter().deleteWithConfirm(this.handler.getGridId());
    }

    @NonNull
    private ManageGridAlertDialog manageGridAlertDialog() {
        if (null == this.manageGridAlertDialogInstance) this.manageGridAlertDialogInstance =
                new ManageGridAlertDialog(this.activity,
                        new ManageGridAlertDialog.Handler() {
                            @Override
                            public void loadGrid() {
                                NavigationItemSelectedListener.this.loadGrid();
                            }

                            @Override
                            public void deleteGrid() {
                                NavigationItemSelectedListener.this.deleteGrid();
                            }
                        });
        return this.manageGridAlertDialogInstance;
    }

    private void manageGrid() {
        this.manageGridAlertDialog().show(this.handler.joinedGridModelIsLoaded());
    }
    // endregion

    // region exportGrid() Grid Private Methods
    private void exportGrid(@IntRange(from = 1) final long gridId,
                            final String fileName) {
        this.handler.exportGrid(gridId, fileName);
    }

    @NonNull
    private GridExportPreprocessor gridExportPreprocessor() {
        if (null == this.gridExportPreprocessorInstance) this.gridExportPreprocessorInstance =
                new GridExportPreprocessor(this.activity,
                        new GridExportPreprocessor.Handler() {
                            @Override
                            public void exportGrid(
                                    @IntRange(from = 1) final long gridId,
                                    final String fileName) {
                                NavigationItemSelectedListener
                                        .this.exportGrid(gridId, fileName);
                            }
                        });
        return this.gridExportPreprocessorInstance;
    }
    // endregion

    private void exportGrid() {
        this.gridExportPreprocessor().preprocess(this.handler.getGridId());
    }

    // region Template Private Methods
    // region createTemplate() Template Private Methods
    @NonNull
    private TemplateCreator templateCreator() {
        if (null == this.templateCreatorInstance)
            this.templateCreatorInstance = new TemplateCreator(
                    this.activity, this.createTemplateRequestCode, this);
        return this.templateCreatorInstance;
    }

    private void createTemplate() {
        this.templateCreator().create();
    }
    // endregion
    // endregion

    // region importTemplate() Template Private Methods
    private void importTemplate(final String fileName) {
        this.handler.importTemplate(fileName);
    }

    @NonNull
    private TemplateImportPreprocessor templateImportPreprocessor() {
        if (null == this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance =
                    new TemplateImportPreprocessor(
                            this.activity, this.preprocessTemplateImportRequestCode,
                            new TemplateImportPreprocessor.Handler() {
                                @Override
                                public void importTemplate(final String fileName) {
                                    NavigationItemSelectedListener
                                            .this.importTemplate(fileName);
                                }
                            });
        return this.templateImportPreprocessorInstance;
    }
    // endregion

    private void importTemplate() {
        this.templateImportPreprocessor().preprocess();
    }

    // region Export Template Private Methods
    private void exportTemplateAfterGettingFileName(
            @IntRange(from = 1) final long templateId,
            final String fileName) {
        this.handler.exportTemplate(templateId, fileName);
    }

    @NonNull
    private TemplateExportPreprocessor templateExportPreprocessor() {
        if (null == this.templateExportPreprocessorInstance)
            this.templateExportPreprocessorInstance =
                    new TemplateExportPreprocessor(this.activity,
                            new TemplateExportPreprocessor.Handler() {
                                @Override
                                public void exportTemplate(
                                        @IntRange(from = 1) final long templateId,
                                        final String fileName) {
                                    NavigationItemSelectedListener
                                            .this.exportTemplateAfterGettingFileName(templateId, fileName);
                                }
                            });
        return this.templateExportPreprocessorInstance;
    }
    // endregion

    private void exportTemplateAfterSelect(@Nullable final TemplateModel templateModel) {
        this.templateExportPreprocessor().preprocess(templateModel);
    }

    // region Delete Template Private Methods
    private void handleGridDeleted() {
        this.handler.handleGridDeleted();
    }

    @NonNull
    private TemplateDeleter templateDeleter() {
        if (null == this.templateDeleterInstance) this.templateDeleterInstance =
                new TemplateDeleter(this.activity,
                        new TemplateDeleter.GridHandler() {
                            @Override
                            public void respondToDeletedGrid() {
                                NavigationItemSelectedListener
                                        .this.handleGridDeleted();
                            }
                        });
        return this.templateDeleterInstance;
    }
    // endregion

    private void deleteTemplateAfterSelect(final TemplateModel
                                                   templateModel) {
        this.templateDeleter().delete(templateModel);
    }

    private void selectUserDefinedTemplate(
            final NavigationItemSelectedListener.TemplateOperation
                    templateOperation) {
        final TemplateModels templateModels =
                this.templatesTable().loadUserDefined();
        if (null != templateModels) if (templateModels.size() > 0) {
            final SelectAlertDialog selectTemplateAlertDialog;
            {
                final SelectAlertDialog.Handler handler;
                switch (templateOperation) {
                    case EXPORT:
                        handler =
                                new SelectAlertDialog.Handler() {
                                    @Override
                                    public void select(final int which) {
                                        NavigationItemSelectedListener
                                                .this.exportTemplateAfterSelect(templateModels.get(which));
                                    }
                                };
                        break;

                    case DELETE:
                        handler =
                                new SelectAlertDialog.Handler() {
                                    @Override
                                    public void select(final int which) {
                                        NavigationItemSelectedListener
                                                .this.deleteTemplateAfterSelect(templateModels.get(which));
                                    }
                                };
                        break;

                    default:
                        handler = null;
                        break;
                }

                selectTemplateAlertDialog =
                        new SelectAlertDialog(this.activity, handler);
            }

            @StringRes final int title;
            switch (templateOperation) {
                case EXPORT:
                    title = R.string
                            .NavigationItemSelectedListenerSelectExportTemplateTitle;
                    break;

                case DELETE:
                    title = R.string
                            .NavigationItemSelectedListenerSelectDeleteTemplateTitle;
                    break;

                default:
                    title = 0;
            }
            selectTemplateAlertDialog.show(title, templateModels.titles());
        }
    }

    // region Project Private Methods
    // region createProject() Project Private Methods
    @NonNull
    private ProjectCreator projectCreator() {
        if (null == this.projectCreatorInstance) this.projectCreatorInstance =
                new ProjectCreator(this.activity);
        return this.projectCreatorInstance;
    }
    // endregion

    private void createProject() {
        this.projectCreator().create();
    }
    // endregion

    private void selectProject(
            final NavigationItemSelectedListener.ProjectOperation
                    projectOperation) {
        final ProjectModels projectModels;
        switch (projectOperation) {
            case LOAD:
                projectModels =
                        this.projectsTable().loadExceptFor(this.getProjectId());
                break;

            case DELETE:
                projectModels = this.projectsTable().load();
                break;
            case EXPORT:
                projectModels = this.projectsTable().loadProjectsWithGrids();
                break;
            default:
                projectModels = null;
                break;
        }

        if (null != projectModels) if (projectModels.size() > 0) {
            final SelectAlertDialog selectProjectAlertDialog;
            {
                final SelectAlertDialog.Handler handler;
                switch (projectOperation) {
                    case LOAD:
                        handler =
                                new SelectAlertDialog.Handler() {
                                    @Override
                                    public void select(final int which) {
                                        NavigationItemSelectedListener
                                                .this.loadProjectAfterSelect(projectModels.get(which));
                                    }
                                };
                        break;

                    case DELETE:
                        handler =
                                new SelectAlertDialog.Handler() {
                                    @Override
                                    public void select(final int which) {
                                        NavigationItemSelectedListener
                                                .this.deleteProjectAfterSelect(projectModels.get(which));
                                    }
                                };
                        break;

                    case EXPORT:
                        handler =
                                new SelectAlertDialog.Handler() {
                                    @Override
                                    public void select(final int which) {
                                        NavigationItemSelectedListener
                                                .this.exportProjectAfterSelect(projectModels.get(which));
                                    }
                                };
                        break;

                    default:
                        handler = null;
                        break;
                }

                selectProjectAlertDialog =
                        new SelectAlertDialog(this.activity, handler);
            }

            @StringRes final int title;
            switch (projectOperation) {
                case LOAD:
                    title = R.string
                            .NavigationItemSelectedListenerSelectLoadProjectTitle;
                    break;

                case DELETE:
                    title = R.string
                            .NavigationItemSelectedListenerSelectDeleteProjectTitle;
                    break;

                case EXPORT:
                    title = R.string
                            .NavigationItemSelectedListenerSelectExportProjectTitle;
                    break;

                default:
                    title = 0;
            }
            selectProjectAlertDialog.show(title, projectModels.titles());
        }
    }

    // region manageProject() Project Private Methods
    private long getProjectId() {
        return this.handler.getProjectId();
    }
    // endregion

    // region loadProject() Private Methods
    private void loadProjectAfterSelect(@Nullable final ProjectModel projectModel) {
        if (null != projectModel) this.handler.loadProject(projectModel.getId());
    }

    private void loadProject() {
        this.selectProject(
                NavigationItemSelectedListener.ProjectOperation.LOAD);
    }

    private void clearProject() {
        this.handler.clearProject();
    }

    // region deleteProject() Private Methods
    private void respondToDeletedProject(@IntRange(from = 1) final long projectId) {
        this.handler.handleProjectDeleted(projectId);
    }
    // endregion

    @NonNull
    private ProjectDeleter projectDeleter() {
        if (null == this.projectDeleterInstance) this.projectDeleterInstance =
                new ProjectDeleter(this.activity,
                        new ProjectDeleter.Handler() {
                            @Override
                            public void respondToDeletedProject(
                                    @IntRange(from = 1) final long projectId) {
                                NavigationItemSelectedListener
                                        .this.respondToDeletedProject(projectId);
                            }
                        });
        return this.projectDeleterInstance;
    }

    private void deleteProjectAfterSelect(@Nullable final ProjectModel projectModel) {
        this.projectDeleter().delete(projectModel);
    }

    private void deleteProject() {
        this.selectProject(NavigationItemSelectedListener.ProjectOperation.DELETE);
    }

    @NonNull
    private ManageProjectAlertDialog manageProjectAlertDialog() {
        if (null == this.manageProjectAlertDialogInstance) this.manageProjectAlertDialogInstance =
                new ManageProjectAlertDialog(this.activity,
                        new ManageProjectAlertDialog.Handler() {
                            @Override
                            public long getProjectId() {
                                return NavigationItemSelectedListener.this.getProjectId();
                            }

                            @Override
                            public void loadProject() {
                                NavigationItemSelectedListener.this.loadProject();
                            }

                            @Override
                            public void clearProject() {
                                NavigationItemSelectedListener.this.clearProject();
                            }

                            @Override
                            public void deleteProject() {
                                NavigationItemSelectedListener.this.deleteProject();
                            }
                        });
        return this.manageProjectAlertDialogInstance;
    }

    private void manageProject() {
        this.manageProjectAlertDialog().show(this.handler.projectModelIsLoaded());
    }
    // endregion

    // region Export Project Private Methods
    private void exportProject(@IntRange(from = 1) final long projectId,
                               final String directoryName) {
        this.handler.exportProject(projectId, directoryName);
    }

    @NonNull
    private ProjectExportPreprocessor projectExportPreprocessor() {
        if (null == this.projectExportPreprocessorInstance) this.projectExportPreprocessorInstance =
                new ProjectExportPreprocessor(this.activity,
                        new ProjectExportPreprocessor.Handler() {
                            @Override
                            public void exportProject(
                                    @IntRange(from = 1) final long projectId,
                                    final String directoryName) {
                                NavigationItemSelectedListener
                                        .this.exportProject(projectId, directoryName);
                            }
                        });
        return this.projectExportPreprocessorInstance;
    }
    // endregion

    private void exportProjectAfterSelect(@Nullable final ProjectModel projectModel) {
        this.projectExportPreprocessor().preprocess(projectModel);
    }

    private void startPreferenceActivity() {
        this.activity.startActivityForResult(
                PreferenceActivity.intent(this.activity),
                this.clickUniquenessRequestCode);
    }

    // region AboutAlertDialog Private Methods
    @NonNull
    private AboutAlertDialog aboutAlertDialog() {
        if (null == this.aboutAlertDialogInstance) this.aboutAlertDialogInstance =
                new AboutAlertDialog(
                        this.activity, this.versionName, this.versionOnClickListener);
        return this.aboutAlertDialogInstance;
    }
    // endregion
    // endregion

    private void showAboutAlertDialog() {
        this.aboutAlertDialog().show();
    }

    // region Overridden Methods

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @Override
    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        @NonNull final String text;
        {
            @NonNull final String format;
            {
                @StringRes final int resId =
                        this.templatesTable().insert(templateModel) > 0 ?
                                R.string.TemplateCreatedToast :
                                R.string.TemplateNotCreatedToast;
                format = this.activity.getString(resId);
            }
            text = String.format(format, templateModel.getTitle());
        }
        this.showLongToast(text);
    }
    // endregion
    // endregion

    // region Public Methods
    public void continueExcluding(final Bundle bundle) {
        this.templateCreator().continueExcluding(bundle);
    }

    public void preprocessTemplateImport() {
        if (null != this.templateImportPreprocessorInstance)
            this.templateImportPreprocessorInstance.preprocess();
    }
    // endregion

    private enum TemplateOperation {EXPORT, DELETE}
    // endregion
    // endregion

    private enum ProjectOperation {LOAD, DELETE, EXPORT}

    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void createGrid();

        public abstract boolean joinedGridModelIsLoaded();

        public abstract void loadGrid(@IntRange(from = 1) long gridId);

        public abstract long getGridId();

        public abstract void respondToDeleteGrid();

        public abstract void exportGrid(
                @IntRange(from = 1) long gridId, String fileName);

        public abstract void importTemplate(String fileName);

        public abstract void exportTemplate(
                @IntRange(from = 1) long templateId,
                String fileName);

        public abstract void handleGridDeleted();

        public abstract long getProjectId();

        public abstract void loadProject(@IntRange(from = 1) long projectId);

        public abstract void clearProject();

        public abstract void handleProjectDeleted(
                @IntRange(from = 1) long projectId);

        public abstract boolean projectModelIsLoaded();

        public abstract void exportProject(
                @IntRange(from = 1) long projectId,
                String directoryName);

        public abstract void closeDrawer();
    }
    // endregion
}