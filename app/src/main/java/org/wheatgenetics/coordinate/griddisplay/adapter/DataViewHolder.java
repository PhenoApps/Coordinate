package org.wheatgenetics.coordinate.griddisplay.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel;
import org.wheatgenetics.coordinate.model.EntryModel;
import org.wheatgenetics.coordinate.model.ExcludedEntryModel;
import org.wheatgenetics.coordinate.model.IncludedEntryModel;

/**
 * Uses:
 * android.content.Context
 * android.content.res.Resources.NotFoundException
 * android.view.View
 * android.view.View.OnLongClickListener
 * android.widget.ImageView
 * <p>
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 * <p>
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Utils
 * <p>
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * <p>
 * org.wheatgenetics.coordinate.display.adapter.DataViewHolder
 */
public class DataViewHolder extends org.wheatgenetics.coordinate.display.adapter.DataViewHolder
        implements StringGetter {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final Context context;
    @NonNull
    private final
    DataViewHolder.GridHandler gridHandler;
    @Nullable
    private final
    CheckedIncludedEntryModel.Checker checker;
    private View.OnLongClickListener onLongClickListenerInstance = null;   // lazy load
    // endregion

    DataViewHolder(
            @NonNull final ImageView itemView,
            @NonNull final Context context,
            @NonNull final
            DataViewHolder.Handler handler,
            @NonNull final
            DataViewHolder.GridHandler gridHandler,
            @Nullable final
            CheckedIncludedEntryModel.Checker checker) {
        super(itemView, handler);
        this.itemView.setOnLongClickListener(this.onLongClickListener());
        this.context = context;
        this.gridHandler = gridHandler;
        this.checker = checker;
    }
    // endregion

    // region Private Methods
    private boolean elementModelIsNotNull() {
        return null != this.elementModel;
    }

    @Nullable
    private EntryModel entryModel() {
        return (EntryModel) this.elementModel;
    }

    private void activate() {
        this.setImage(R.drawable.active_entry);
        this.gridHandler.activate(this);
    }

    // region onLongClickListener() Private Methods
    private void exclude() {
        if (this.elementModelIsNotNull()) {
            this.elementModel = new ExcludedEntryModel(
                    (IncludedEntryModel) this.elementModel,
                    this);
            this.clearOnClickListener();
            this.toggle();
        }
    }

    private void include() {
        if (this.elementModelIsNotNull()) {
            final ExcludedEntryModel excludedEntryModel =
                    (ExcludedEntryModel) this.elementModel;
            this.elementModel = null == this.checker ?
                    new IncludedEntryModel(
                            excludedEntryModel, this) :
                    new CheckedIncludedEntryModel(
                            excludedEntryModel, this.checker, this);
            this.setOnClickListener();
            this.toggle();
        }
    }

    @SuppressWarnings({"SameReturnValue"})
    private boolean onLongClick() {
        if (this.elementModelIsNotNull()) {
            final EntryModel entryModel = this.entryModel();
            if (entryModel instanceof IncludedEntryModel) {
                final IncludedEntryModel includedEntryModel =
                        (IncludedEntryModel) entryModel;
                if (includedEntryModel.valueIsEmpty())
                    this.exclude();
                else {
                    Utils.confirm(
                            /* context => */ this.context,
                            /* title   => */ R.string.ElementConfirmTitle,
                            /* message => */ String.format(
                                    this.context.getString(
                                            R.string.ElementConfirmMessage),
                                    includedEntryModel.getValue()),
                            /* yesRunnable => */ new Runnable() {
                                @Override
                                public void run() {
                                    DataViewHolder.this.exclude();
                                }
                            });
                }
            } else this.include();
        }
        return true;
    }

    @NonNull
    private View.OnLongClickListener onLongClickListener() {
        if (null == this.onLongClickListenerInstance) this.onLongClickListenerInstance =
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {
                        return DataViewHolder.this.onLongClick();
                    }
                };
        return this.onLongClickListenerInstance;
    }
    // endregion
    // endregion

    // region Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void setImage() {
        if (this.elementModelIsNotNull()) this.setImage(this.entryModel().backgroundResource());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    protected void respondToClick() {
        this.activate();
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.context.getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.context.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    void bind(@Nullable final EntryModel entryModel, int activeRow, int activeCol) {
        this.elementModel = entryModel;

        activeRow = Math.max(activeRow, -1);
        activeCol = Math.max(activeCol, -1);
        if (this.getRow() == activeRow && this.getCol() == activeCol
                || -1 == activeRow && -1 == activeCol)
            this.activate();
        else
            this.setImage();

        if (this.elementModel instanceof IncludedEntryModel)
            this.setOnClickListener();
        else
            this.clearOnClickListener();
    }
    // endregion
    // endregion

    // region Public Methods
    @IntRange(from = -1)
    public int getRow() {
        return this.elementModelIsNotNull() ? this.elementModel.getRowValue() - 1 : -1;
    }

    @IntRange(from = -1)
    public int getCol() {
        return this.elementModelIsNotNull() ? this.elementModel.getColValue() - 1 : -1;
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface GridHandler {
        public abstract void activate(@NonNull
                                              DataViewHolder dataViewHolder);
    }
    // endregion
}