package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Entry extends java.lang.Object implements android.view.View.OnClickListener
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void displayValue(java.lang.String value); }

    // region Fields
    private final org.wheatgenetics.coordinate.model.EntryModel      entryModel;
    private final android.widget.TextView                            textView  ;
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler   ;
    // endregion

    Entry(
    final org.wheatgenetics.coordinate.model.EntryModel      entryModel,
    final android.widget.TextView                            textView  ,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler   )
    {
        super();

        this.entryModel = entryModel; this.textView = textView; this.handler = handler;

        assert null != this.entryModel; assert null != this.textView;
        this.textView.setBackgroundResource(this.entryModel.backgroundResource());
        this.textView.setOnClickListener(this);
    }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v)
    {
        assert null != this.entryModel; assert null != this.handler;
        this.handler.displayValue(this.entryModel.getValue());
    }
    // endregion

    // region Package Methods
    int getRow() { return null == this.entryModel ? -1 : this.entryModel.getRow() - 1; }
    int getCol() { return null == this.entryModel ? -1 : this.entryModel.getCol() - 1; }
    // endregion
}