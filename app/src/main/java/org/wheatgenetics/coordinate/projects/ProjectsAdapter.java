package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.res.Resources
 * android.view.View
 * android.view.ViewGroup
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.Adapter
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 */
class ProjectsAdapter extends org.wheatgenetics.coordinate.Adapter
{
    // region Fields
    // region Table Fields
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null;  // ll
    private org.wheatgenetics.coordinate.database.GridsTable    gridsTableInstance    = null;  // ll
    // endregion

    private org.wheatgenetics.coordinate.model.ProjectModels projectModelsInstance = null;     // ll
    private android.content.res.Resources                    resourcesInstance     = null;     // ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }
    // endregion

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.ProjectModels projectModels()
    {
        if (null == this.projectModelsInstance)
            this.projectModelsInstance = this.projectsTable().load();
        return this.projectModelsInstance;
    }

    @androidx.annotation.NonNull private android.content.res.Resources resources()
    {
        if (null == this.resourcesInstance) this.resourcesInstance = this.activity().getResources();
        return this.resourcesInstance;
    }
    // endregion

    ProjectsAdapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(activity); }

    // region Overridden Methods
    @java.lang.Override public int getCount()
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels = this.projectModels();
        return null == projectModels ? 0 : projectModels.size();
    }

    @java.lang.Override public java.lang.Object getItem(final int position)
    {
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels = this.projectModels();
        return null == projectModels ? null : projectModels.get(position);
    }

    @java.lang.Override public long getItemId(final int position)
    {
        final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
            (org.wheatgenetics.coordinate.model.ProjectModel) this.getItem(position);
        return null == projectModel ? -1 : projectModel.getId();
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   , 
    @androidx.annotation.Nullable final android.view.View      convertView, 
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
            (org.wheatgenetics.coordinate.model.ProjectModel) this.getItem(position);
        if (null == projectModel)
            return this.makeEmptyTableLayout();
        else
        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.activity().getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.projects_list_item,
                    null,false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else
            {
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.projectsListItemTitle);
                    if (null != textView) textView.setText(projectModel.getTitle());
                }
                {
                    final android.widget.TextView valueTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.projectsListItemNumberOfGrids);
                    if (null != valueTextView)
                    {
                        @androidx.annotation.IntRange(from = 0) final int numberOfGrids =
                            this.gridsTable().numberInProject(projectModel.getId());
                        valueTextView.setText(this.resources().getQuantityString(
                            org.wheatgenetics.coordinate.R.plurals.ProjectsListNumberOfGrids,
                            numberOfGrids, numberOfGrids                                    ));
                    }
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.projectsListItemTimestamp);
                    if (null != textView) textView.setText(projectModel.getFormattedTimestamp());
                }
                return view;
            }
        }
    }

    @java.lang.Override public void notifyDataSetChanged()
    { this.projectModelsInstance = null; super.notifyDataSetChanged(); }
    // endregion
}