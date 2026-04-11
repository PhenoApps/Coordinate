package org.wheatgenetics.coordinate.griddisplay;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.display.DisplayFragment;
import org.wheatgenetics.coordinate.griddisplay.adapter.Adapter;
import org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;

/**
 * Fragment code that manages click events and displays table in collect activity.
 * Fragment is included in collector_tablerow.xml with data entry fragment.
 */
public class GridDisplayFragment extends DisplayFragment {
    private Adapter adapter = null;

    public GridDisplayFragment() { /* Required empty public constructor. */ }

    // region Private Methods
    @Nullable
    private GridDisplayFragment.Handler
    gridDisplayFragmentHandler() {
        return (GridDisplayFragment.Handler) this.handler;
    }

    private int getActiveRow() {
        final GridDisplayFragment.Handler
                gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        return null == gridDisplayFragmentHandler ? -1 : gridDisplayFragmentHandler.getActiveRow();
    }

    private int getActiveCol() {
        final GridDisplayFragment.Handler
                gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        return null == gridDisplayFragmentHandler ? -1 : gridDisplayFragmentHandler.getActiveCol();
    }

    private void activate(@NonNull final
                          DataViewHolder dataViewHolder) {
        final GridDisplayFragment.Handler
                gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        if (null != gridDisplayFragmentHandler)
            gridDisplayFragmentHandler.activate(dataViewHolder.getRow(), dataViewHolder.getCol());
    }
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected boolean setHandler(
            @NonNull final Context context) {
        final boolean success;

        if (context instanceof GridDisplayFragment.Handler) {
            this.handler =
                    (GridDisplayFragment.Handler) context;
            success = true;
        } else {
            this.handler = null;
            success = false;
        }

        return success;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @NonNull
    protected org.wheatgenetics.coordinate.display.adapter.Adapter makeAdapter(
            @NonNull final DisplayModel displayModel) {
        final Activity activity = this.getActivity();
        if (null == activity)
            throw new NullPointerException();
        else {
            final GridDisplayFragment.Handler
                    gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
            if (null == gridDisplayFragmentHandler)
                throw new NullPointerException();
            else
                return this.adapter = new Adapter(
                        displayModel, activity, this.getActiveRow(), this.getActiveCol(),
                        new DataViewHolder.Handler() {
                            @Override
                            public void toggle(@Nullable final ElementModel elementModel) {
                                GridDisplayFragment.this.toggle(elementModel);
                            }
                        }, new
                        DataViewHolder.GridHandler() {
                            @Override
                            public void activate(@NonNull final DataViewHolder
                                                         dataViewHolder) {
                                GridDisplayFragment.this.activate(dataViewHolder);
                            }
                        }, gridDisplayFragmentHandler.getChecker(),
                        gridDisplayFragmentHandler.isImportedMode());
        }
    }

    public void notifyDataSetChanged() {
        if (null != this.adapter) {
            final int activeRow = this.getActiveRow();
            final int activeCol = this.getActiveCol();
            // Post to the next frame so we never call notifyDataSetChanged() while
            // RecyclerView is mid-layout (would throw IllegalStateException).
            final android.view.View root = getView();
            if (root != null) {
                root.post(() -> {
                    if (this.adapter != null) {
                        this.adapter.notifyDataSetChanged(activeRow, activeCol);
                        scrollToActiveCell(activeRow, activeCol);
                    }
                });
            } else {
                this.adapter.notifyDataSetChanged(activeRow, activeCol);
                scrollToActiveCell(activeRow, activeCol);
            }
        }
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends DisplayFragment.Handler {
        public abstract int getActiveRow();

        public abstract int getActiveCol();

        public abstract void activate(int row, int col);

        @Nullable
        public abstract CheckedIncludedEntryModel.Checker getChecker();

        public default boolean isImportedMode() { return false; }
    }
}
