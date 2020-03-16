package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.ImageView
 * android.widget.LinearLayout
 *
 * androidx.annotation.IntRange
 * androidx.annotation.LayoutRes
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.recyclerview.widget.RecyclerView.Adapter
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler
 * org.wheatgenetics.coordinate.display.adapter.ItemViewType
 * org.wheatgenetics.coordinate.display.adapter.LeftViewHolder
 * org.wheatgenetics.coordinate.display.adapter.TopViewHolder
 * org.wheatgenetics.coordinate.display.adapter.ViewHolder
 */
public abstract class Adapter extends androidx.recyclerview.widget.RecyclerView.Adapter<
org.wheatgenetics.coordinate.display.adapter.ViewHolder>
{
    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;

    @androidx.annotation.NonNull private org.wheatgenetics.coordinate.model.DisplayModel
        displayModel;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler handler;

    private android.view.LayoutInflater layoutInflaterInstance = null;                  // lazy load
    // endregion

    // region Private Methods
    // region adapter Private Methods
    @androidx.annotation.IntRange(from = 2) private int adapterRows()
    { return 1 + /* add top row */ this.displayModel.getRows(); }

    @androidx.annotation.IntRange(from = 2) private int adapterCols()
    { return 1 + /* add left column */ this.displayModel.getCols(); }

    @androidx.annotation.IntRange(from = 0) private int adapterRow(
    @androidx.annotation.IntRange(from = 0) final int position)
    { return position / this.adapterCols(); }

    @androidx.annotation.IntRange(from = 0) private int adapterCol(
    @androidx.annotation.IntRange(from = 0) final int position)
    { return position % this.adapterCols(); }
    // endregion

    // region inflate() Private Methods
    @androidx.annotation.NonNull private android.view.LayoutInflater layoutInflater(
    @androidx.annotation.NonNull final android.view.ViewGroup parent)
    {
        if (null == this.layoutInflaterInstance)
            this.layoutInflaterInstance = android.view.LayoutInflater.from(parent.getContext());
        return this.layoutInflaterInstance;
    }

    private android.view.View inflate(
    @androidx.annotation.NonNull   final android.view.ViewGroup parent,
    @androidx.annotation.LayoutRes final int                    layout)
    { return this.layoutInflater(parent).inflate(layout, parent,false); }

    private android.widget.LinearLayout inflateTopOrLeft(
    @androidx.annotation.NonNull   final android.view.ViewGroup parent,
    @androidx.annotation.LayoutRes final int                    layout)
    { return (android.widget.LinearLayout) this.inflate(parent, layout); }

    private android.widget.ImageView inflateData(
    @androidx.annotation.NonNull final android.view.ViewGroup parent)
    {
        return (android.widget.ImageView) this.inflate(parent,
            org.wheatgenetics.coordinate.R.layout.display_cell);
    }
    // endregion
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected abstract org.wheatgenetics.coordinate.display.adapter.DataViewHolder dataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void bind(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.display.adapter.DataViewHolder
        dataViewHolder,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.ElementModel
        elementModel);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected android.content.Context getContext() { return this.context; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler getHandler()
    { return this.handler; }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected Adapter(
    @androidx.annotation.NonNull final android.content.Context                         context     ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.display.adapter.DataViewHolder.Handler handler)
    { super(); this.context = context; this.displayModel = displayModel; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public int getItemViewType(final int position)
    {
        if (this.adapterRow(position) == 0)
            return org.wheatgenetics.coordinate.display.adapter.ItemViewType.TOP.getCode();
        else
            if (this.adapterCol(position) == 0)
                return org.wheatgenetics.coordinate.display.adapter.ItemViewType.LEFT.getCode();
            else
                return org.wheatgenetics.coordinate.display.adapter.ItemViewType.DATA.getCode();
    }

    @java.lang.Override @androidx.annotation.NonNull
    public org.wheatgenetics.coordinate.display.adapter.ViewHolder onCreateViewHolder(
    @androidx.annotation.NonNull final android.view.ViewGroup parent, final int viewType)
    {
        final org.wheatgenetics.coordinate.display.adapter.ItemViewType itemViewType =
            org.wheatgenetics.coordinate.display.adapter.ItemViewType.convert(viewType);
        switch (itemViewType)
        {
            case TOP: return new org.wheatgenetics.coordinate.display.adapter.TopViewHolder(
                /* context  => */ this.getContext(),
                /* itemView => */ this.inflateTopOrLeft(parent,
                    org.wheatgenetics.coordinate.R.layout.top_display_table_cell));

            case LEFT: return new org.wheatgenetics.coordinate.display.adapter.LeftViewHolder(
                /* context  => */ this.getContext(),
                /* itemView => */ this.inflateTopOrLeft(parent,
                    org.wheatgenetics.coordinate.R.layout.left_display_table_cell));

            case DATA: return this.dataViewHolder(/* itemView => */ this.inflateData(parent));

            default: throw new java.lang.IllegalArgumentException();
        }
    }

    @android.annotation.SuppressLint({"Range"}) @java.lang.Override public void onBindViewHolder(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.display.adapter.ViewHolder
        viewHolder,
    final int position)
    {
        final org.wheatgenetics.coordinate.display.adapter.ItemViewType itemViewType =
            org.wheatgenetics.coordinate.display.adapter.ItemViewType.convert(
                this.getItemViewType(position));
        switch (itemViewType)
        {
            case TOP:
                ((org.wheatgenetics.coordinate.display.adapter.TopViewHolder) viewHolder).bind(
                    position, this.displayModel.getColNumbering()); break;

            case LEFT: case DATA:
                @androidx.annotation.IntRange(from = 0) final int adapterRow =
                    this.adapterRow(position);
                switch (itemViewType)
                {
                    case LEFT: ((org.wheatgenetics.coordinate.display.adapter.LeftViewHolder)
                        viewHolder).bind(adapterRow, this.displayModel.getRowNumbering()); break;

                    case DATA:
                        this.bind(
                            (org.wheatgenetics.coordinate.display.adapter.DataViewHolder)
                                viewHolder,
                            this.displayModel.getElementModel(
                                adapterRow, this.adapterCol(position)));
                        break;
                } break;
        }
    }

    @java.lang.Override public int getItemCount()
    { return this.adapterRows() * this.adapterCols(); }
    // endregion

    public void initialize(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.model.DisplayModel displayModel)
    { this.displayModel = displayModel; }
}