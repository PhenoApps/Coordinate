package org.wheatgenetics.coordinate.griddisplay;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.DisplayModel
 *
 * org.wheatgenetics.coordinate.display.DisplayFragment
 * org.wheatgenetics.coordinate.display.DisplayFragment.Handler
 * org.wheatgenetics.coordinate.display.adapter.Adapter
 *
 * org.wheatgenetics.coordinate.griddisplay.adapter.Adapter
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler
 * org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler
 */
public class GridDisplayFragment extends org.wheatgenetics.coordinate.display.DisplayFragment
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.display.DisplayFragment.Handler
    {
        public abstract int getActiveRow(); public abstract int getActiveCol();
        public abstract void activate(int row, int col);

        @androidx.annotation.Nullable public abstract
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker getChecker();
    }

    private org.wheatgenetics.coordinate.griddisplay.adapter.Adapter adapter = null;

    // region Private Methods
    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
    gridDisplayFragmentHandler()
    { return (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler) this.handler; }

    private int getActiveRow()
    {
        final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
            gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        return null == gridDisplayFragmentHandler ? -1 : gridDisplayFragmentHandler.getActiveRow();
    }

    private int getActiveCol()
    {
        final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
            gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        return null == gridDisplayFragmentHandler ? -1 : gridDisplayFragmentHandler.getActiveCol();
    }

    private void activate(@androidx.annotation.NonNull final
    org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder dataViewHolder)
    {
        final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
            gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
        if (null != gridDisplayFragmentHandler)
            gridDisplayFragmentHandler.activate(dataViewHolder.getRow(), dataViewHolder.getCol());
    }
    // endregion

    public GridDisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected boolean setHandler(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        final boolean success;

        if (context instanceof org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler)
        {
            this.handler =
                (org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler) context;
            success = true;
        }
        else { this.handler = null; success = false; }

        return success;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected org.wheatgenetics.coordinate.display.adapter.Adapter makeAdapter(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel)
    {
        final android.app.Activity activity = this.getActivity();
        if (null == activity)
            throw new java.lang.NullPointerException();
        else
        {
            final org.wheatgenetics.coordinate.griddisplay.GridDisplayFragment.Handler
                gridDisplayFragmentHandler = this.gridDisplayFragmentHandler();
            if (null == gridDisplayFragmentHandler)
                throw new java.lang.NullPointerException();
            else
                return this.adapter = new org.wheatgenetics.coordinate.griddisplay.adapter.Adapter(
                    displayModel, activity, this.getActiveRow(), this.getActiveCol(),
                    new org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler()
                    {
                        @java.lang.Override @androidx.annotation.Nullable
                        public org.wheatgenetics.coordinate.model.ElementModel toggle(
                        @androidx.annotation.Nullable final
                            org.wheatgenetics.coordinate.model.ElementModel elementModel)
                        {
                            return org.wheatgenetics.coordinate.griddisplay
                                .GridDisplayFragment.this.toggle(elementModel);
                        }
                    }, new
                    org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler()
                    {
                        @java.lang.Override public void activate(@androidx.annotation.NonNull
                        final org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder
                            dataViewHolder)
                        {
                            org.wheatgenetics.coordinate.griddisplay
                                .GridDisplayFragment.this.activate(dataViewHolder);
                        }
                    }, gridDisplayFragmentHandler.getChecker());
        }
    }
    // endregion

    public void notifyDataSetChanged()
    {
        if (null != this.adapter)
            this.adapter.notifyDataSetChanged(this.getActiveRow(), this.getActiveCol());
    }
}