package org.wheatgenetics.coordinate.griddisplay;

/**
 * Uses:
 * android.content.Context
 * android.view.View
 * android.view.View.OnLongClickListener
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.display.Element
 *
 * org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 */
class GridElement extends org.wheatgenetics.coordinate.display.Element
implements android.view.View.OnLongClickListener
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface GridHandler
    {
        public abstract void activate(@androidx.annotation.NonNull
        org.wheatgenetics.coordinate.griddisplay.GridElement gridElement);
    }

    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker;
    // endregion

    // region Private Methods
    private void activate()
    {
        this.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.active_entry);
        this.gridHandler.activate(this);
    }

    private void exclude()
    {
        if (this.elementModelIsNotNull())
            // noinspection ConstantConditions
            this.elementModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
                (org.wheatgenetics.coordinate.model.IncludedEntryModel) this.elementModel);
        this.clearOnClickListener(); this.toggle();
    }
    // endregion

    GridElement(
    @androidx.annotation.NonNull final android.content.Context                       context   ,
                                 final org.wheatgenetics.coordinate.model.EntryModel entryModel,
    @androidx.annotation.NonNull final android.widget.TextView                       textView  ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.griddisplay.GridElement.Handler
        handler,
    @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.griddisplay.GridElement.GridHandler gridHandler,
    int activeRow, int activeCol, @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel.Checker checker)
    {
        super(entryModel, textView, handler);
        this.context = context; this.gridHandler = gridHandler; this.checker = checker;

        activeRow = java.lang.Math.max(activeRow, -1);
        activeCol = java.lang.Math.max(activeCol, -1);
        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.inactivate();

        if (this.elementModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.setOnClickListener();
        this.setOnLongClickListener(this);
    }

    // region Overridden Methods
    @java.lang.Override protected void respondToClick() { this.activate(); }

    @java.lang.Override protected void setBackgroundResource()
    {
        if (this.elementModelIsNotNull())
            // noinspection ConstantConditions
            this.setBackgroundResource(((org.wheatgenetics.coordinate.model.EntryModel)
                this.elementModel).backgroundResource());
    }

    // region android.view.View.OnLongClickListener Overridden Method
    @java.lang.Override public boolean onLongClick(final android.view.View v)
    {
        if (this.elementModelIsNotNull())
        {
            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                (org.wheatgenetics.coordinate.model.EntryModel) this.elementModel;
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
                                        .GridElement.this.exclude();
                                }
                            });
                }
            }
            else
            {
                final org.wheatgenetics.coordinate.model.ExcludedEntryModel excludedEntryModel =
                    (org.wheatgenetics.coordinate.model.ExcludedEntryModel) entryModel;
                if (null != excludedEntryModel)
                {
                    this.elementModel = null == this.checker ?
                        new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                            excludedEntryModel) :
                        new org.wheatgenetics.coordinate.model.CheckedIncludedEntryModel(
                            excludedEntryModel, this.checker);
                    this.setOnClickListener(); this.toggle();
                }
            }
        }
        return true;
    }
    // endregion
    // endregion

    void inactivate() { this.setBackgroundResource(); }
}