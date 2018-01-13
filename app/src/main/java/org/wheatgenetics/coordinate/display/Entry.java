package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.R
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Entry extends java.lang.Object implements android.view.View.OnClickListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler
    { public abstract void activate(org.wheatgenetics.coordinate.display.Entry entry); }

    // region Fields
    private final org.wheatgenetics.coordinate.model.EntryModel      entryModel;
    private final android.widget.TextView                            textView  ;
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler   ;
    // endregion

    private void activate()
    {
        assert null != this.textView;
        this.textView.setBackgroundResource(org.wheatgenetics.coordinate.R.drawable.active_entry);

        assert null != this.handler; this.handler.activate(this);
    }

    Entry(
    final org.wheatgenetics.coordinate.model.EntryModel      entryModel,
    final android.widget.TextView                            textView  ,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler   ,
    final int activeRow, final int activeCol)
    {
        super();

        this.entryModel = entryModel; this.textView = textView; this.handler = handler;

        if (this.getRow() == activeRow && this.getCol() == activeCol
        ||  -1            == activeRow && -1            == activeCol)
            this.activate();
        else
            this.inactivate();

        if (entryModel instanceof org.wheatgenetics.coordinate.model.IncludedEntryModel)
            this.textView.setOnClickListener(this);
    }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v) { this.activate(); }
    // endregion

    // region Package Methods
    void inactivate()
    {
        assert null != this.entryModel; assert null != this.textView;
        this.textView.setBackgroundResource(this.entryModel.backgroundResource());
    }

    int getRow() { return null == this.entryModel ? -1 : this.entryModel.getRow() - 1; }
    int getCol() { return null == this.entryModel ? -1 : this.entryModel.getCol() - 1; }

    java.lang.String getValue()
    { return null == this.entryModel ? null : this.entryModel.getValue(); }
    // endregion
}