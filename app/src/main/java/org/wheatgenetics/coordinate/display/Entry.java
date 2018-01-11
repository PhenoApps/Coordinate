package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Entry extends java.lang.Object
{
    // region Fields
    private final org.wheatgenetics.coordinate.model.EntryModel entryModel;
    private final android.widget.TextView                       textView  ;
    // endregion

    Entry(final org.wheatgenetics.coordinate.model.EntryModel entryModel,
    final android.widget.TextView textView)
    {
        super();

        this.entryModel = entryModel; this.textView = textView;

        assert null != this.entryModel; assert null != this.textView;
        this.textView.setBackgroundResource(this.entryModel.backgroundResource());
    }

    // region Package Methods
    int getRow() { return null == this.entryModel ? -1 : this.entryModel.getRow() - 1; }
    int getCol() { return null == this.entryModel ? -1 : this.entryModel.getCol() - 1; }
    // endregion
}