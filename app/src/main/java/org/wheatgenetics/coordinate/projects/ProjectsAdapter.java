package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.ViewGroup
 * android.widget.BaseAdapter
 * android.widget.ListAdapter
 * android.widget.TableLayout
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 */
class ProjectsAdapter extends android.widget.BaseAdapter implements android.widget.ListAdapter
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance = null; // ll
    private org.wheatgenetics.coordinate.model.ProjectModels    projectModelsInstance = null; // ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity);
        return this.projectsTableInstance;
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.ProjectModels projectModels()
    {
        if (null == this.projectModelsInstance)
            this.projectModelsInstance = this.projectsTable().load();
        return this.projectModelsInstance;
    }
    // endregion

    ProjectsAdapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }

    // region Overridden Methods
    @java.lang.Override public void notifyDataSetChanged()
    { this.projectModelsInstance = null; super.notifyDataSetChanged(); }

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
        final org.wheatgenetics.coordinate.model.ProjectModels projectModels = this.projectModels();
        if (null == projectModels)
            return -1;
        else
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                projectModels.get(position);
            return null == projectModel ? -1 : projectModel.getId();
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   , 
    @androidx.annotation.Nullable final android.view.View      convertView, 
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
            (org.wheatgenetics.coordinate.model.ProjectModel) this.getItem(position);
        if (null == projectModel)
            return new android.widget.TableLayout(this.activity);
        else
        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.activity.getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.projects_list_item,
                    null,false);
            if (null == view)
                return new android.widget.TableLayout(this.activity);
            else
            {
                {
                    final android.widget.TextView titleTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.projectsListItemTitle);
                    if (null != titleTextView) titleTextView.setText(projectModel.getTitle());
                }
                {
                    final android.widget.TextView timestampTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.projectsListItemTimestamp);
                    if (null != timestampTextView)
                        timestampTextView.setText(projectModel.getTimestampAsCharSequence());
                }
                return view;
            }
        }
    }
    // endregion
}