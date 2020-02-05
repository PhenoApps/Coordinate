package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.ViewGroup
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.Adapter
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
abstract class GridsAdapter extends org.wheatgenetics.coordinate.Adapter
{
    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load

    @androidx.annotation.NonNull private android.widget.TextView makeEmptyTextView()
    { return new android.widget.TextView(this.activity()); }

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable
    abstract org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels();
    // endregion

    GridsAdapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(activity); }

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
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            (org.wheatgenetics.coordinate.model.JoinedGridModel) this.getItem(position);
        return null == joinedGridModel ? -1 : joinedGridModel.getId();
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   ,
    @androidx.annotation.Nullable final android.view.View      convertView,
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            (org.wheatgenetics.coordinate.model.JoinedGridModel) this.getItem(position);
        if (null == joinedGridModel)
            return this.makeEmptyTextView();
        else
        {
            @android.annotation.SuppressLint({"InflateParams"})
            final android.widget.TextView textView =
                (android.widget.TextView) this.activity().getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.grids_list_item,
                    null,false);
            if (null == textView)
                return this.makeEmptyTextView();
            else
            {
                textView.setText(joinedGridModel.name());
                return textView;
            }
        }
    }
    // endregion
}