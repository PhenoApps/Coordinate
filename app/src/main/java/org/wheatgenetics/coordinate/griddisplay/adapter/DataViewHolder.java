package org.wheatgenetics.coordinate.griddisplay.adapter;

/**
 * Uses:
 * android.content.Context
 * android.content.res.Resources.NotFoundException
 * android.view.View
 * android.view.View.OnLongClickListener
 * android.widget.ImageView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 *
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 */
public class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.DataViewHolder
implements org.wheatgenetics.coordinate.StringGetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface GridHandler
    {
        public abstract void activate(@androidx.annotation.NonNull
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder dataViewHolder);
    }

    // region Fields
    // region Constructor Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;
    // endregion

    private android.view.View.OnLongClickListener onLongClickListenerInstance = null;   // lazy load
    // endregion

    // region Private Methods
    private boolean elementModelIsNotNull() { return null != this.elementModel; }

    @androidx.annotation.Nullable private org.wheatgenetics.coordinate.model.EntryModel entryModel()
    { return (org.wheatgenetics.coordinate.model.EntryModel) this.elementModel; }

    private void activate()
    {
        this.setImage(org.wheatgenetics.coordinate.R.drawable.active_entry);
        this.gridHandler.activate(this);
    }

    // region onLongClickListener() Private Methods
    private void exclude()
    {
        if (this.elementModelIsNotNull())
        {
            this.elementModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                (org.wheatgenetics.coordinate.model.IncludedEntryModel) this.elementModel,
                this);
            this.clearOnClickListener(); this.toggle();
        }
    }

    private void include()
    {
        if (this.elementModelIsNotNull())
        {
            final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel =
                (org.wheatgenetics.coordinate.model.ExcludedEntryModel) this.elementModel;
            this.elementModel = null == this.checker ?
                new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                    excludedEntryModel,this) :
                new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                    excludedEntryModel, this.checker,this);
            this.setOnClickListener(); this.toggle();
        }
    }

    @java.lang.SuppressWarnings({"SameReturnValue"}) private boolean onLongClick()
    {
        if (this.elementModelIsNotNull())
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel = this.entryModel();
            if (entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
                    (org.wheatgenetics.coordinate.model.IncludedEntryModel) entryModel;
                if (includedEntryModel.valueIsEmpty())
                    this.exclude();
                else
                {
                    org.wheatgenetics.coordinate.Utils.confirm(
                        /* context => */ this.context                                             ,
                        /* title   => */ org.wheatgenetics.coordinate.R.string.ElementConfirmTitle,
                        /* message => */ java.lang.String.format(
                            this.context.getString(
                                org.wheatgenetics.coordinate.R.string.ElementConfirmMessage),
                            includedEntryModel.getValue()),
                        /* yesRunnable => */ new java.lang.Runnable()
                            {
                                @java.lang.Override public void run()
                                {
                                    org.wheatgenetics.coordinate.griddisplay
                                        .adapter.DataViewHolder.this.exclude();
                                }
                            });
                }
            }
            else this.include();
        }
        return true;
    }

    @androidx.annotation.NonNull private android.view.View.OnLongClickListener onLongClickListener()
    {
        if (null == this.onLongClickListenerInstance) this.onLongClickListenerInstance =
            new android.view.View.OnLongClickListener()
            {
                @java.lang.Override public boolean onLongClick(final android.view.View view)
                {
                    return org.wheatgenetics.coordinate.griddisplay
                        .adapter.DataViewHolder.this.onLongClick();
                }
            };
        return this.onLongClickListenerInstance;
    }
    // endregion
    // endregion

    DataViewHolder(
    @androidx.annotation.NonNull final android.widget.ImageView itemView,
    @androidx.annotation.NonNull final android.content.Context  context ,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.Handler handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.adapter.DataViewHolder.GridHandler gridHandler,
    @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    {
        super(itemView, handler); this.itemView.setOnLongClickListener(this.onLongClickListener());
        this.context = context; this.gridHandler = gridHandler; this.checker = checker;
    }

    // region Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void setImage()
    { if (this.elementModelIsNotNull()) this.setImage(this.entryModel().backgroundResource()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override protected void respondToClick() { this.activate(); }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.context.getString(resId); }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException
    { return this.context.getResources().getQuantityString(resId, quantity, formatArgs); }
    // endregion
    // endregion

    void bind(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.EntryModel entryModel, int activeRow, int activeCol)
    {
        this.elementModel = entryModel;

        activeRow = java.lang.Math.max(activeRow, -1);
        activeCol = java.lang.Math.max(activeCol, -1);
        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.setImage();

        if (this.elementModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.setOnClickListener();
        else
            this.clearOnClickListener();
    }

    // region Public Methods
    @androidx.annotation.IntRange(from = -1) public int getRow()
    { return this.elementModelIsNotNull() ? this.elementModel.getRowValue() - 1 : -1; }

    @androidx.annotation.IntRange(from = -1) public int getCol()
    { return this.elementModelIsNotNull() ? this.elementModel.getColValue() - 1 : -1; }
    // endregion
}