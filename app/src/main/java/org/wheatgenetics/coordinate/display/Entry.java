package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.content.Context
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.View.OnLongClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Entry extends java.lang.Object
implements android.view.View.OnClickListener, android.view.View.OnLongClickListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler
    {
        public abstract void activate(org.wheatgenetics.coordinate.display.Entry    entry     );
        public abstract void toggle  (org.wheatgenetics.coordinate.model.EntryModel entryModel);
    }

    // region Fields
    private final android.content.Context                            context   ;
    private       org.wheatgenetics.coordinate.model.EntryModel      entryModel;
    private final android.widget.TextView                            textView  ;
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler   ;
    // endregion

    // region Private Methods
    private void activate()
    {
        assert null != this.textView;
        this.textView.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.active_entry);

        assert null != this.handler; this.handler.activate(this);
    }

    private void setOnClickListener(final android.view.View.OnClickListener onClickListener)
    { assert null != this.textView; this.textView.setOnClickListener(onClickListener); }

    // region onClickListener() Private Methods
    private void setOnClickListener() { this.setOnClickListener(this); }

    private void clearOnClickListener() { this.setOnClickListener(null); }

    private void setBackgroundResource()
    {
        assert null != this.entryModel; assert null != this.textView;
        this.textView.setBackgroundResource(this.entryModel.backgroundResource());
    }
    // endregion

    private void toggle()
    {
        this.setBackgroundResource();
        assert null != this.handler; this.handler.toggle(this.entryModel);
    }

    private void exclude()
    {
        this.entryModel = new org.wheatgenetics.coordinate.model.ExcludedEntryModel(
            (org.wheatgenetics.coordinate.model.IncludedEntryModel) this.entryModel);
        this.clearOnClickListener(); this.toggle();
    }
    // endregion

    Entry(
    final android.content.Context                            context   ,
    final org.wheatgenetics.coordinate.model.EntryModel      entryModel,
    final android.widget.TextView                            textView  ,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler   ,
    final int activeRow, final int activeCol)
    {
        super();

        this.context  = context ; this.entryModel = entryModel;
        this.textView = textView; this.handler    = handler   ;

        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.inactivate();

        if (this.entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.setOnClickListener();

        assert null != this.textView; this.textView.setOnLongClickListener(this);
    }

    // region Overridden Methods
    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v) { this.activate(); }
    // endregion

    // region android.view.View.OnLongClickListener Overridden Method
    @java.lang.Override
    public boolean onLongClick(final android.view.View v)
    {
        if (null != this.entryModel)
        {
            if (this.entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            {
                final org.wheatgenetics.coordinate.model.IncludedEntryModel includedEntryModel =
                    (org.wheatgenetics.coordinate.model.IncludedEntryModel) this.entryModel;
                if (includedEntryModel.valueIsEmpty())
                    this.exclude();
                else
                {
                    assert null != this.context;
                    org.wheatgenetics.coordinate.Utils.confirm(
                        /* context => */ this.context                                           ,
                        /* title   => */ org.wheatgenetics.coordinate.R.string.EntryConfirmTitle,
                        /* message => */ java.lang.String.format(
                            this.context.getString(
                                org.wheatgenetics.coordinate.R.string.EntryConfirmMessage),
                            includedEntryModel.getValue()),
                        /* yesRunnable => */ new java.lang.Runnable()
                            {
                                @java.lang.Override
                                public void run()
                                { org.wheatgenetics.coordinate.display.Entry.this.exclude(); }
                            });
                }
            }
            else
            {
                this.entryModel = new org.wheatgenetics.coordinate.model.IncludedEntryModel(
                    (org.wheatgenetics.coordinate.model.ExcludedEntryModel) this.entryModel);
                this.setOnClickListener(); this.toggle();
            }
        }
        return true;
    }
    // endregion
    // endregion

    // region Package Methods
    void inactivate() { this.setBackgroundResource(); }

    int getRow() { return null == this.entryModel ? -1 : this.entryModel.getRow() - 1; }
    int getCol() { return null == this.entryModel ? -1 : this.entryModel.getCol() - 1; }
    // endregion
}