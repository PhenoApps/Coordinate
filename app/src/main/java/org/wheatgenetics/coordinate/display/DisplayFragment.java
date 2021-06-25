package org.wheatgenetics.coordinate.display;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
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
                this.recyclerView.setHasFixedSize(true);
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
                this.recyclerView.setAdapter(this.adapter(displayModel));
            }
        }
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    protected interface Handler {
        public abstract DisplayModel getDisplayModel();

        public abstract void toggle(
                @Nullable ElementModel elementModel);
    }
}