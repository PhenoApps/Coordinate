package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.content.Context
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 *
 * org.wheatgenetics.coordinate.Element
 * org.wheatgenetics.coordinate.Element.Handler
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
class GridElement extends org.wheatgenetics.coordinate.Element
implements android.view.View.OnClickListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler extends org.wheatgenetics.coordinate.Element.Handler
    { public abstract void activate(org.wheatgenetics.coordinate.display.GridElement gridElement); }

    private final android.content.Context context;

    // region Private Methods
    private void activate()
    {
        this.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.active_entry);
        ((org.wheatgenetics.coordinate.display.GridElement.Handler) this.getHandler()).activate(
            this);
    }

    private void setBackgroundResource()
    {
        this.setBackgroundResource(((org.wheatgenetics.coordinate.model.EntryModel)
            this.elementModel).backgroundResource());
    }

    private void setOnClickListener(final android.view.View.OnClickListener onClickListener)
    { this.getTextView().setOnClickListener(onClickListener); }

    private void setOnClickListener() { this.setOnClickListener(this); }

    private void toggle()
    { this.setBackgroundResource(); this.getHandler().toggle(this.elementModel); }

    private void exclude()
    {
        this.elementModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
            (org.wheatgenetics.coordinate.model.IncludedEntryModel) this.elementModel);
        this.setOnClickListener(null); this.toggle();
    }
    // endregion

    GridElement(
    final android.content.Context                                  context   ,
    final org.wheatgenetics.coordinate.model.EntryModel            entryModel,
    final android.widget.TextView                                  textView  ,
    final org.wheatgenetics.coordinate.display.GridElement.Handler handler   ,
    final int activeRow, final int activeCol)
    {
        super(entryModel, textView, handler);

        this.context = context;

        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.inactivate();

        if (this.elementModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.setOnClickListener();
    }

    // region Overridden Methods
    @java.lang.Override
    protected void respondToLongClick()
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
                assert null != this.context;
                org.wheatgenetics.coordinate.Utils.confirm(
                    /* context => */ this.context                                             ,
                    /* title   => */ org.wheatgenetics.coordinate.R.string.ElementConfirmTitle,
                    /* message => */ java.lang.String.format(
                        this.context.getString(
                            org.wheatgenetics.coordinate.R.string.ElementConfirmMessage),
                        includedEntryModel.getValue()),
                    /* yesRunnable => */ new java.lang.Runnable()
                        {
                            @java.lang.Override
                            public void run()
                            { org.wheatgenetics.coordinate.display.GridElement.this.exclude(); }
                        });
            }
        }
        else
        {
            this.elementModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                (org.wheatgenetics.coordinate.model.ExcludedEntryModel) this.elementModel);
            this.setOnClickListener(); this.toggle();
        }
    }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v) { this.activate(); }
    // endregion
    // endregion

    void inactivate() { this.setBackgroundResource(); }
}