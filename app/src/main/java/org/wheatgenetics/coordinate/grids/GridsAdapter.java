package org.wheatgenetics.coordinate.grids;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.adapter.Adapter;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;

abstract class GridsAdapter extends Adapter {
    // region Fields
    @NonNull
    private final View.OnClickListener
            onCollectDataButtonClickListener;
    private GridsTable gridsTableInstance = null; // lazy load
    // endregion

    GridsAdapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onCollectDataButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener) {
        super(activity, onDeleteButtonClickListener, onExportButtonClickListener);
        this.onCollectDataButtonClickListener = onCollectDataButtonClickListener;
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    abstract BaseJoinedGridModels baseJoinedGridModels();
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    // region Overridden Methods
    @Override
    public int getCount() {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.baseJoinedGridModels();
        return null == baseJoinedGridModels ? 0 : baseJoinedGridModels.size();
    }

    @Override
    public Object getItem(final int position) {
        final BaseJoinedGridModels baseJoinedGridModels =
                this.baseJoinedGridModels();
        return null == baseJoinedGridModels ? null : baseJoinedGridModels.get(position);
    }

    @Override
    public long getItemId(final int position) {
        final JoinedGridModel joinedGridModel =
                (JoinedGridModel) this.getItem(position);
        return null == joinedGridModel ? -1 : joinedGridModel.getId();
    }

    @Override
    @NonNull
    public View getView(
            final int position,
            @Nullable final View convertView,
            @NonNull final ViewGroup parent) {
        final JoinedGridModel joinedGridModel =
                (JoinedGridModel) this.getItem(position);
        if (null == joinedGridModel)
            return this.makeEmptyTableLayout();
        else {
            @SuppressLint({"InflateParams"}) final View view =
                    this.activity().getLayoutInflater().inflate(
                            R.layout.grids_list_item,
                            null, false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else {
                {
                    final TextView textView = view.findViewById(
                            R.id.gridsListItemTitle);
                    if (null != textView) textView.setText(joinedGridModel.getTitle());
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.gridsListItemTemplateTitle);
                    if (null != textView) textView.setText(joinedGridModel.getTemplateTitle());
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.gridsListItemTimestamp);
                    if (null != textView) textView.setText(joinedGridModel.getFormattedTimestamp());
                }
                @IntRange(from = 1) final long gridId = joinedGridModel.getId();
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.gridsListItemDeleteButton);
                    if (null != imageButton) {
                        imageButton.setTag(gridId);
                        imageButton.setOnClickListener(this.onDeleteButtonClickListener());
                    }
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.gridsListItemExportButton);
                    if (null != imageButton) {
                        imageButton.setTag(gridId);
                        imageButton.setOnClickListener(this.onExportButtonClickListener());
                    }
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.gridsListItemCollectDataButton);
                    if (null != imageButton) {
                        imageButton.setTag(gridId);
                        imageButton.setOnClickListener(this.onCollectDataButtonClickListener);
                    }
                }
                return view;
            }
        }
    }
    // endregion
}