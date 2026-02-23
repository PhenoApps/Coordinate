package org.wheatgenetics.coordinate.display.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.model.DisplayModel;
import org.wheatgenetics.coordinate.model.ElementModel;

public abstract class Adapter extends RecyclerView.Adapter<
        ViewHolder> {
    // region Fields
    @NonNull
    private final Context context;
    @NonNull
    private final
    DataViewHolder.Handler handler;
    @NonNull
    private DisplayModel
            displayModel;
    private LayoutInflater layoutInflaterInstance = null;                  // lazy load
    private boolean mCompact = false;
    private float mScaleFactor = 1.0f;
    private int mCellSize = ViewGroup.LayoutParams.WRAP_CONTENT;
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected Adapter(
            @NonNull final Context context,
            @NonNull final DisplayModel displayModel,
            @NonNull final
            DataViewHolder.Handler handler) {
        super();
        this.context = context;
        this.displayModel = displayModel;
        this.handler = handler;
    }

    // region Private Methods
    // region adapter Private Methods
    @IntRange(from = 2)
    private int adapterRows() {
        return 1 + /* add top row */ this.displayModel.getRows();
    }

    @IntRange(from = 2)
    private int adapterCols() {
        return 1 + /* add left column */ this.displayModel.getCols();
    }

    @IntRange(from = 0)
    private int adapterRow(
            @IntRange(from = 0) final int position) {
        return position / this.adapterCols();
    }
    // endregion

    @IntRange(from = 0)
    private int adapterCol(
            @IntRange(from = 0) final int position) {
        return position % this.adapterCols();
    }

    // region inflate() Private Methods
    @NonNull
    private LayoutInflater layoutInflater(
            @NonNull final ViewGroup parent) {
        if (null == this.layoutInflaterInstance)
            this.layoutInflaterInstance = LayoutInflater.from(parent.getContext());
        return this.layoutInflaterInstance;
    }

    private View inflate(
            @NonNull final ViewGroup parent,
            @LayoutRes final int layout) {
        return this.layoutInflater(parent).inflate(layout, parent, false);
    }

    private LinearLayout inflateTopOrLeft(
            @NonNull final ViewGroup parent,
            @LayoutRes final int layout) {
        return (LinearLayout) this.inflate(parent, layout);
    }
    // endregion
    // endregion

    private ImageView inflateData(
            @NonNull final ViewGroup parent) {
        return (ImageView) this.inflate(parent,
                R.layout.display_cell);
    }

    // region Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected abstract DataViewHolder dataViewHolder(
            @NonNull final ImageView itemView);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected abstract void bind(
            @NonNull final DataViewHolder
                    dataViewHolder,
            @Nullable final ElementModel
                    elementModel);

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected Context getContext() {
        return this.context;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected DataViewHolder.Handler getHandler() {
        return this.handler;
    }

    // region Overridden Methods
    @Override
    public int getItemViewType(final int position) {
        if (this.adapterRow(position) == 0)
            return ItemViewType.TOP.getCode();
        else if (this.adapterCol(position) == 0)
            return ItemViewType.LEFT.getCode();
        else
            return ItemViewType.DATA.getCode();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent, final int viewType) {
        final ItemViewType itemViewType =
                ItemViewType.convert(viewType);
        switch (itemViewType) {
            case TOP:
                return new TopViewHolder(
                        /* context  => */ this.getContext(),
                        /* itemView => */ this.inflateTopOrLeft(parent,
                        R.layout.top_display_table_cell));

            case LEFT:
                return new LeftViewHolder(
                        /* context  => */ this.getContext(),
                        /* itemView => */ this.inflateTopOrLeft(parent,
                        R.layout.left_display_table_cell));

            case DATA:
                return this.dataViewHolder(/* itemView => */ this.inflateData(parent));

            default:
                throw new IllegalArgumentException();
        }
    }

    @SuppressLint({"Range"})
    @Override
    public void onBindViewHolder(
            @NonNull final ViewHolder
                    viewHolder,
            final int position) {
        final ItemViewType itemViewType =
                ItemViewType.convert(
                        this.getItemViewType(position));
        switch (itemViewType) {
            case TOP:
                ((TopViewHolder) viewHolder).bind(
                        position, this.displayModel.getColNumbering(), this.mCompact, this.mScaleFactor);
                {
                    ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
                    if (lp != null) {
                        lp.width = mCellSize;
                        lp.height = mCellSize;
                        viewHolder.itemView.setLayoutParams(lp);
                    }
                }
                break;

            case LEFT:
            case DATA:
                @IntRange(from = 0) final int adapterRow =
                        this.adapterRow(position);
                switch (itemViewType) {
                    case LEFT:
                        ((LeftViewHolder)
                                viewHolder).bind(adapterRow, this.displayModel.getRowNumbering(), this.mCompact, this.mScaleFactor);
                        {
                            ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
                            if (lp != null) {
                                lp.width = mCellSize;
                                lp.height = mCellSize;
                                viewHolder.itemView.setLayoutParams(lp);
                            }
                        }
                        break;

                    case DATA:
                        ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
                        if (lp != null) {
                            lp.width = mCellSize;
                            lp.height = mCellSize;
                            viewHolder.itemView.setLayoutParams(lp);
                        }
                        this.bind(
                                (DataViewHolder)
                                        viewHolder,
                                this.displayModel.getElementModel(
                                        adapterRow, this.adapterCol(position)));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.adapterRows() * this.adapterCols();
    }
    // endregion

    public void initialize(
            @NonNull final DisplayModel displayModel) {
        this.displayModel = displayModel;
    }

    public void setCompact(final boolean compact, final float scaleFactor, final int cellSize) {
        this.mCompact = compact;
        this.mScaleFactor = scaleFactor;
        this.mCellSize = cellSize;
        this.notifyDataSetChanged();
    }
}