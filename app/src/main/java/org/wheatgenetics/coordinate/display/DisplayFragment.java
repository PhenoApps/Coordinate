package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.fragment.app.Fragment
 * androidx.recyclerview.widget.GridLayoutManager
 * androidx.recyclerview.widget.RecyclerView
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.adapter.Adapter
 */
public abstract class DisplayFragment extends androidx.fragment.app.Fragment
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) protected interface Handler
    {
        public abstract org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel();

        public abstract void toggle(
        @androidx.annotation.Nullable org.wheatgenetics.coordinate.model.ElementModel elementModel);
    }

    // region Fields
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable protected
        org.wheatgenetics.coordinate.display.DisplayFragment.Handler handler;

    @androidx.annotation.Nullable private androidx.recyclerview.widget.RecyclerView recyclerView;

    private androidx.recyclerview.widget.GridLayoutManager     gridLayoutManagerInstance = null;//ll
    private org.wheatgenetics.coordinate.display.adapter.Adapter adapterInstance         = null;//ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private androidx.recyclerview.widget.GridLayoutManager gridLayoutManager(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel)
    {
        @androidx.annotation.IntRange(from = 2) final int spanCount =
            1 + /* add left column */ displayModel.getCols();
        if (null == this.gridLayoutManagerInstance)
            this.gridLayoutManagerInstance =
                new androidx.recyclerview.widget.GridLayoutManager(this.getContext(), spanCount);
        else
            this.gridLayoutManagerInstance.setSpanCount(spanCount);
        return this.gridLayoutManagerInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.display.adapter.Adapter adapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel)
    {
        if (null == this.adapterInstance)
            this.adapterInstance = this.makeAdapter(displayModel);
        else
            this.adapterInstance.initialize(displayModel);
        return this.adapterInstance;
    }
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract boolean setHandler(
    @androidx.annotation.NonNull final android.content.Context context);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected abstract org.wheatgenetics.coordinate.display.adapter.Adapter makeAdapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel
        displayModel);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void toggle(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { if (null != this.handler) this.handler.toggle(elementModel); }
    // endregion

    // region Overridden Methods
    @java.lang.Override public void onAttach(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        super.onAttach(context);

        if (!this.setHandler(context))
            throw new java.lang.RuntimeException(context.toString() + " must implement Handler");
    }

    @java.lang.Override @androidx.annotation.Nullable public android.view.View onCreateView(
    @androidx.annotation.NonNull  final android.view.LayoutInflater inflater          ,
    @androidx.annotation.Nullable final android.view.ViewGroup      container         ,
    @androidx.annotation.Nullable final android.os.Bundle           savedInstanceState)
    {
        // Inflate the layout for this fragment:
        return inflater.inflate(org.wheatgenetics.coordinate.R.layout.fragment_display,
            container,false);
    }

    @java.lang.Override public void onActivityCreated(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final android.app.Activity activity = this.getActivity();
        if (null == activity)
            this.recyclerView = null;
        else
        {
            this.recyclerView = activity.findViewById(
                org.wheatgenetics.coordinate.R.id.displayRecyclerView);
            if (null != this.recyclerView)
                { this.recyclerView.setHasFixedSize(true); this.populate(); }
        }
    }

    @java.lang.Override public void onDetach() { this.handler = null; super.onDetach(); }
    // endregion

    public void populate()
    {
        if (null != this.recyclerView)
        {
            final org.wheatgenetics.coordinate.model.DisplayModel displayModel =
                null == this.handler ? null : this.handler.getDisplayModel();
            if (null != displayModel)
            {
                this.recyclerView.setLayoutManager(this.gridLayoutManager(displayModel));
                this.recyclerView.setAdapter      (this.adapter          (displayModel));
            }
        }
    }
}