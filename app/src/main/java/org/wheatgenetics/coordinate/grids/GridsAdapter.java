package org.wheatgenetics.coordinate.grids;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
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
import org.wheatgenetics.coordinate.model.JoinedGridModels;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

abstract class GridsAdapter extends Adapter {
    // region Fields
    @NonNull
    private final View.OnClickListener
            onCollectDataButtonClickListener;
    private GridsTable gridsTableInstance = null; // lazy load

    // region Action mode / selection fields
    private final HashSet<Long> selectedIds = new HashSet<>();
    private boolean inActionMode = false;
    @Nullable
    private View.OnLongClickListener rowLongClickListener = null;
    @Nullable
    private OnSelectionChangedListener selectionChangedListener = null;

    interface OnSelectionChangedListener {
        void onSelectionChanged(int count);
    }
    // endregion

    // region Sort constants and field
    static final int SORT_DEFAULT = 0;
    static final int SORT_NAME = 1;
    static final int SORT_DATE = 2;
    private int sortOrder = SORT_DEFAULT;
    // endregion
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

    // region Action mode methods
    void enterActionMode(@IntRange(from = 1) final long firstId) {
        inActionMode = true;
        selectedIds.add(firstId);
    }

    void exitActionMode() {
        inActionMode = false;
        selectedIds.clear();
    }

    void toggleSelection(@IntRange(from = 1) final long id) {
        if (!selectedIds.remove(id)) selectedIds.add(id);
    }

    boolean isInActionMode() { return inActionMode; }

    @NonNull
    Set<Long> getSelectedIds() { return Collections.unmodifiableSet(selectedIds); }

    int getSelectedCount() { return selectedIds.size(); }

    void setRowLongClickListener(@Nullable final View.OnLongClickListener listener) {
        this.rowLongClickListener = listener;
    }

    void setSelectionChangedListener(@Nullable final OnSelectionChangedListener listener) {
        this.selectionChangedListener = listener;
    }
    // endregion

    // region Sort Methods
    void setSortOrder(final int sortOrder) {
        this.sortOrder = sortOrder;
        notifyDataSetChanged();
    }

    int getSortOrder() {
        return sortOrder;
    }

    void applySort(@Nullable final BaseJoinedGridModels models) {
        if (!(models instanceof JoinedGridModels) || sortOrder == SORT_DEFAULT) return;
        final JoinedGridModels jgm = (JoinedGridModels) models;
        if (sortOrder == SORT_NAME) {
            jgm.sort(new Comparator<JoinedGridModel>() {
                @Override
                public int compare(final JoinedGridModel a, final JoinedGridModel b) {
                    final String titleA = a.getTitle() != null ? a.getTitle() : "";
                    final String titleB = b.getTitle() != null ? b.getTitle() : "";
                    return titleA.compareToIgnoreCase(titleB);
                }
            });
        } else if (sortOrder == SORT_DATE) {
            jgm.sort(new Comparator<JoinedGridModel>() {
                @Override
                public int compare(final JoinedGridModel a, final JoinedGridModel b) {
                    return Long.compare(b.getTimestamp(), a.getTimestamp());
                }
            });
        }
    }
    // endregion

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
                    if (null != textView) textView.setText(joinedGridModel.isImported()
                            ? view.getContext().getString(R.string.ImportedGridLabel)
                            : joinedGridModel.getTemplateTitle());
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.gridsListItemTimestamp);
                    if (null != textView) textView.setText(joinedGridModel.getFormattedTimestamp());
                }
                @IntRange(from = 1) final long gridId = joinedGridModel.getId();
                if (inActionMode) {
                    // Hide per-item buttons; show selection highlight
                    final ImageButton deleteBtn = view.findViewById(R.id.gridsListItemDeleteButton);
                    final ImageButton exportBtn = view.findViewById(R.id.gridsListItemExportButton);
                    final ImageButton collectBtn = view.findViewById(R.id.gridsListItemCollectDataButton);
                    if (deleteBtn != null) deleteBtn.setVisibility(View.GONE);
                    if (exportBtn != null) exportBtn.setVisibility(View.GONE);
                    if (collectBtn != null) collectBtn.setVisibility(View.GONE);
                    view.setBackgroundColor(selectedIds.contains(gridId)
                            ? Color.argb(60, 0, 120, 215)
                            : Color.TRANSPARENT);
                    view.setOnClickListener(v -> {
                        toggleSelection(gridId);
                        if (selectionChangedListener != null) {
                            selectionChangedListener.onSelectionChanged(getSelectedCount());
                        }
                        notifyDataSetChanged();
                    });
                } else {
                    // Normal mode: ensure buttons are visible
                    final ImageButton deleteBtn = view.findViewById(R.id.gridsListItemDeleteButton);
                    if (deleteBtn != null) {
                        deleteBtn.setVisibility(View.VISIBLE);
                        deleteBtn.setTag(gridId);
                        deleteBtn.setOnClickListener(this.onDeleteButtonClickListener());
                    }
                    final ImageButton exportBtn = view.findViewById(R.id.gridsListItemExportButton);
                    if (exportBtn != null) {
                        exportBtn.setVisibility(View.VISIBLE);
                        exportBtn.setTag(gridId);
                        exportBtn.setOnClickListener(this.onExportButtonClickListener());
                    }
                    final ImageButton collectBtn = view.findViewById(R.id.gridsListItemCollectDataButton);
                    if (collectBtn != null) {
                        collectBtn.setVisibility(View.VISIBLE);
                        collectBtn.setTag(gridId);
                        collectBtn.setOnClickListener(this.onCollectDataButtonClickListener);
                    }
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                view.setTag(gridId);
                if (rowLongClickListener != null) {
                    view.setOnLongClickListener(rowLongClickListener);
                }
                return view;
            }
        }
    }
    // endregion
}