package org.wheatgenetics.coordinate.projects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.adapter.NonGridsAdapter;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.database.ProjectsTable;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.ProjectModels;

class ProjectsAdapter extends NonGridsAdapter {
    // region Fields
    // region Table Fields
    private ProjectsTable projectsTableInstance = null;  // ll
    private GridsTable gridsTableInstance = null;  // ll
    // endregion

    private ProjectModels projectModelsInstance = null;  // lazy
    // endregion                                                                            //  load

    ProjectsAdapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onCreateGridButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener,
            @NonNull final View.OnClickListener
                    onShowGridsButtonClickListener) {
        super(activity, onCreateGridButtonClickListener, onDeleteButtonClickListener,
                onExportButtonClickListener, onShowGridsButtonClickListener);
    }

    // region Private Methods
    // region Table Private Methods
    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }
    // endregion

    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity());
        return this.gridsTableInstance;
    }
    // endregion

    @Nullable
    private ProjectModels projectModels() {
        if (null == this.projectModelsInstance)
            this.projectModelsInstance = this.projectsTable().load();
        return this.projectModelsInstance;
    }

    // region Overridden Methods
    @Override
    public int getCount() {
        final ProjectModels projectModels = this.projectModels();
        return null == projectModels ? 0 : projectModels.size();
    }

    @Override
    public Object getItem(final int position) {
        final ProjectModels projectModels = this.projectModels();
        return null == projectModels ? null : projectModels.get(position);
    }

    @Override
    public long getItemId(final int position) {
        final ProjectModel projectModel =
                (ProjectModel) this.getItem(position);
        return null == projectModel ? -1 : projectModel.getId();
    }

    @Override
    @NonNull
    public View getView(
            final int position,
            @Nullable final View convertView,
            @NonNull final ViewGroup parent) {
        final ProjectModel projectModel =
                (ProjectModel) this.getItem(position);
        if (null == projectModel)
            return this.makeEmptyTableLayout();
        else {
            @SuppressLint({"InflateParams"}) final View view =
                    this.activity().getLayoutInflater().inflate(
                            R.layout.projects_list_item,
                            null, false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else {
                {
                    final TextView textView = view.findViewById(
                            R.id.projectsListItemTitle);
                    if (null != textView) {
                        textView.setText(projectModel.getTitle());

                        //added click to view entire project title
                        textView.setOnClickListener((v) -> {
                            Toast.makeText(this.activity(), projectModel.getTitle(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.projectsListItemTimestamp);
                    if (null != textView) textView.setText(projectModel.getFormattedTimestamp());
                }

                @IntRange(from = 1) final long projectId = projectModel.getId();
                final boolean projectHasGrids;
                {
                    @IntRange(from = 0) final int numberOfGrids =
                            this.gridsTable().numberInProject(projectId);
                    projectHasGrids = numberOfGrids > 0;
                    final TextView textView = view.findViewById(
                            R.id.projectsListItemNumberOfGrids);
                    if (null != textView) textView.setText(this.resources().getQuantityString(
                            R.plurals.ProjectsListNumberOfGrids,
                            numberOfGrids, numberOfGrids));
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.projectsListItemDeleteButton);
                    if (null != imageButton) {
                        imageButton.setTag(projectId);
                        imageButton.setOnClickListener(this.onDeleteButtonClickListener());
                    }
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.projectsListItemExportButton);
                    if (null != imageButton)
                        if (projectHasGrids) {
                            imageButton.setTag(projectId);
                            imageButton.setOnClickListener(this.onExportButtonClickListener());
                        } else imageButton.setEnabled(false);
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.projectsListItemShowGridsButton);
                    if (null != imageButton)
                        if (projectHasGrids) {
                            imageButton.setTag(projectId);
                            imageButton.setOnClickListener(this.onShowGridsButtonClickListener());
                        } else imageButton.setEnabled(false);
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.projectsListItemCreateGridButton);
                    if (null != imageButton) {
                        imageButton.setTag(projectId);
                        imageButton.setOnClickListener(this.onCreateGridButtonClickListener());
                    }
                }

                return view;
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        this.projectModelsInstance = null;
        super.notifyDataSetChanged();
    }
    // endregion
}