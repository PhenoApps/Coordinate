package org.wheatgenetics.coordinate.display;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.display.adapter.Adapter;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;

public abstract class DisplayFragment extends Fragment {
    // region Fields
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    protected
    DisplayFragment.Handler handler;
    @Nullable
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManagerInstance = null;//ll
    private Adapter adapterInstance = null;//ll
    private int mCellWidth = 0;

    // region Private Methods
    @NonNull
    private GridLayoutManager gridLayoutManager(
            @NonNull final DisplayModel displayModel) {
        @IntRange(from = 2) final int spanCount =
                1 + /* add left column */ displayModel.getCols();
        if (null == this.gridLayoutManagerInstance)
            this.gridLayoutManagerInstance =
                    new GridLayoutManager(this.getContext(), spanCount);
        else
            this.gridLayoutManagerInstance.setSpanCount(spanCount);
        return this.gridLayoutManagerInstance;
    }
    // endregion

    @NonNull
    private Adapter adapter(
            @NonNull final DisplayModel displayModel) {
        if (null == this.adapterInstance)
            this.adapterInstance = this.makeAdapter(displayModel);
        else
            this.adapterInstance.initialize(displayModel);
        return this.adapterInstance;
    }

    // region Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected abstract boolean setHandler(
            @NonNull final Context context);
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected abstract Adapter makeAdapter(
            @NonNull final DisplayModel
                    displayModel);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void toggle(@Nullable final ElementModel elementModel) {
        if (null != this.handler) this.handler.toggle(elementModel);
    }

    // region Overridden Methods
    @Override
    public void onAttach(
            @NonNull final Context context) {
        super.onAttach(context);

        if (!this.setHandler(context))
            throw new RuntimeException(context.toString() + " must implement Handler");
    }
    // endregion

    @Override
    @Nullable
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        // Inflate the layout for this fragment:
        return inflater.inflate(R.layout.fragment_display,
                container, false);
    }

    @Override
    public void onActivityCreated(
            @Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = this.getActivity();
        if (null == activity)
            this.recyclerView = null;
        else {
            this.recyclerView = activity.findViewById(
                    R.id.displayRecyclerView);
            if (null != this.recyclerView) {
                //this.recyclerView.setHasFixedSize(true);
                this.populate();
            }
        }
    }

    @Override
    public void onDetach() {
        this.handler = null;
        super.onDetach();
    }

    public void populate() {
        if (null != this.recyclerView) {
            final DisplayModel displayModel =
                    null == this.handler ? null : this.handler.getDisplayModel();
            if (null != displayModel) {
                this.recyclerView.setLayoutManager(this.gridLayoutManager(displayModel));
                Adapter adapter = adapter(displayModel);
                this.recyclerView.setAdapter(this.adapter(displayModel));
            }
        }
    }


    public void scrollToActiveCell(int row, int col) {
        if (recyclerView == null || gridLayoutManagerInstance == null) return;

        // Calculate the position in the adapter from row and column
        DisplayModel displayModel = (handler != null) ? handler.getDisplayModel() : null;
        if (displayModel == null) return;

        int totalCols = displayModel.getCols();
        int position = (row * totalCols) + col;

        recyclerView.scrollToPosition(position);

        recyclerView.post(() -> {
            View itemView = gridLayoutManagerInstance.findViewByPosition(position);
            if (itemView != null) {
                NestedScrollView nestedScrollView = (NestedScrollView) recyclerView.getParent().getParent();
                HorizontalScrollView horizontalScrollView = (HorizontalScrollView) recyclerView.getParent();

                // sometimes itemView.getWidth() returns anomalous width for certain cells
                // therefore, we store the value
                if (mCellWidth <= 0) {
                    // stores the first measured item width as reference
                    mCellWidth = itemView.getWidth();
                }

                int itemWidth = mCellWidth;
                int itemHeight = itemView.getHeight();
                int verticalOffset = itemView.getTop();

                // calculate distance from left logically
                // (col + 1) as we have the first column as "row header" in the grid
                int horizontalOffset = (col + 1) * itemWidth;

                int horizontalScrollViewWidth = horizontalScrollView.getWidth();
                int nestedScrollViewHeight = nestedScrollView.getHeight();

                // calculate scroll in a way to position the item in the center of the screen where possible
                int horizontalScroll = Math.max(0, horizontalOffset - ((horizontalScrollViewWidth - itemWidth) / 2));
                int verticalScroll = Math.max(0, verticalOffset - ((nestedScrollViewHeight - itemHeight) / 2));

                // maximum scrollable distance by finding how much content extends beyond the viewport
                int maxHorizontalScroll = horizontalScrollView.getChildAt(0).getWidth() - horizontalScrollViewWidth;
                int maxVerticalScroll = nestedScrollView.getChildAt(0).getHeight() - nestedScrollViewHeight;

                horizontalScroll = Math.min(horizontalScroll, maxHorizontalScroll);
                verticalScroll = Math.min(verticalScroll, maxVerticalScroll);

                nestedScrollView.smoothScrollTo(0, verticalScroll);
                horizontalScrollView.smoothScrollTo(horizontalScroll, 0);
            }
        });
    }


    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract DisplayModel getDisplayModel();

        public abstract void toggle(
                @Nullable ElementModel elementModel);
    }
}