package org.wheatgenetics.coordinate.grids;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.ViewGroup
 * android.widget.ImageButton
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.adapter.Adapter
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
abstract class GridsAdapter extends org.wheatgenetics.coordinate.adapter.Adapter
{
    // region Fields
    @androidx.annotation.NonNull private final android.view.View.OnClickListener
        onCollectDataButtonClickListener;
    private org.wheatgenetics.coordinate.database.GridsTable gridsTableInstance = null; // lazy load
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable
    abstract org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }
    // endregion

    GridsAdapter(
    @androidx.annotation.NonNull final android.app.Activity              activity,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onCollectDataButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onDeleteButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onExportButtonClickListener)
    {
        super(activity, onDeleteButtonClickListener, onExportButtonClickListener);
        this.onCollectDataButtonClickListener = onCollectDataButtonClickListener;
    }

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
            return this.makeEmptyTableLayout();
        else
        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.activity().getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.grids_list_item,
                    null,false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else
            {
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemTitle);
                    if (null != textView) textView.setText(joinedGridModel.getTitle());
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemTemplateTitle);
                    if (null != textView) textView.setText(joinedGridModel.getTemplateTitle());
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemTimestamp);
                    if (null != textView) textView.setText(joinedGridModel.getFormattedTimestamp());
                }
                @androidx.annotation.IntRange(from = 1) final long gridId = joinedGridModel.getId();
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemDeleteButton);
                    if (null != imageButton)
                    {
                        imageButton.setTag            (gridId                            );
                        imageButton.setOnClickListener(this.onDeleteButtonClickListener());
                    }
                }
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemExportButton);
                    if (null != imageButton)
                    {
                        imageButton.setTag            (gridId                            );
                        imageButton.setOnClickListener(this.onExportButtonClickListener());
                    }
                }
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.gridsListItemCollectDataButton);
                    if (null != imageButton)
                    {
                        imageButton.setTag            (gridId                               );
                        imageButton.setOnClickListener(this.onCollectDataButtonClickListener);
                    }
                }
                return view;
            }
        }
    }
    // endregion
}