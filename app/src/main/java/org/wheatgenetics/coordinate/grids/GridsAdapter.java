package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.ViewGroup
 * android.widget.BaseAdapter
 * android.widget.ListAdapter
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
class GridsAdapter extends android.widget.BaseAdapter implements android.widget.ListAdapter
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels 
        baseJoinedGridModelsInstance = null;                                            // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity);
        return this.gridsTableInstance;
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels()
    {
        if (null == this.baseJoinedGridModelsInstance)
            this.baseJoinedGridModelsInstance = this.gridsTable().load();
        return this.baseJoinedGridModelsInstance;
    }

    @androidx.annotation.NonNull private android.widget.TextView makeTextView()
    { return new android.widget.TextView(this.activity); }
    // endregion

    GridsAdapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }

    // region Overridden Methods
    @java.lang.Override public int getCount()
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.baseJoinedGridModels();
        return null == baseJoinedGridModels ? 0 : baseJoinedGridModels.size();
    }

    @java.lang.Override public java.lang.Object getItem(final int position)
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.baseJoinedGridModels();
        return null == baseJoinedGridModels ? null : baseJoinedGridModels.get(position);
    }

    @java.lang.Override public long getItemId(final int position)
    {
        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
            this.baseJoinedGridModels();
        if (null == baseJoinedGridModels)
            return -1;
        else
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                baseJoinedGridModels.get(position);
            return null == joinedGridModel ? -1 :joinedGridModel.getId();
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   ,
    @androidx.annotation.Nullable final android.view.View      convertView,
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            (org.wheatgenetics.coordinate.model.JoinedGridModel) this.getItem(position);
        if (null == joinedGridModel)
            return this.makeTextView();
        else
        {
            @android.annotation.SuppressLint({"InflateParams"})
            final android.widget.TextView textView =
                (android.widget.TextView) this.activity.getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.grids_list_item,
                    null,false);
            if (null == textView)
                return this.makeTextView();
            else
            {
                textView.setText(joinedGridModel.name());
                return textView;
            }
        }
    }
    // endregion
}