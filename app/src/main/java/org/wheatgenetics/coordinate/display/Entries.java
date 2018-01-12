package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.view.LayoutInflater
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.display.Entry
 * org.wheatgenetics.coordinate.display.Entry.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
class Entries extends java.lang.Object implements org.wheatgenetics.coordinate.display.Entry.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler { public abstract void displayValue(java.lang.String value); }

    // region Fields
    private final org.wheatgenetics.coordinate.display.Entries.Handler handler;

    private android.view.LayoutInflater                layoutInflater;
    private org.wheatgenetics.coordinate.display.Entry entryArray[][];

    private int activeRow = 0, activeCol = 0;
    // endregion

    Entries(final android.view.LayoutInflater layoutInflater, final int rows, final int cols,
    final org.wheatgenetics.coordinate.display.Entries.Handler handler)
    { super(); this.handler = handler; this.allocate(layoutInflater, rows, cols); }

    // region org.wheatgenetics.coordinate.display.Entry.Handler Overridden Method
    @java.lang.Override
    public void activate(final org.wheatgenetics.coordinate.display.Entry entry)
    {
        if (null != entry)
        {
            final int newActiveRow = entry.getRow(), newActiveCol = entry.getCol();
            if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
            {
                assert null != this.entryArray;
                this.entryArray[this.activeRow][this.activeCol].inactivate();

                this.activeRow = newActiveRow; this.activeCol = newActiveCol;

                assert null != this.handler; this.handler.displayValue(entry.getValue());
            }
        }
    }
    // endregion

    // region Package Methods
    void clear() { this.layoutInflater = null; this.entryArray = null; }

    void allocate(final android.view.LayoutInflater layoutInflater, final int rows, final int cols)
    {
        this.layoutInflater = layoutInflater                                            ;
        this.entryArray     = new org.wheatgenetics.coordinate.display.Entry[rows][cols];
    }

    android.widget.LinearLayout add(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    {
        if (null == entryModel)
            return null;
        else
        {
            assert null != this.layoutInflater;
            @android.annotation.SuppressLint("InflateParams")
            final android.widget.LinearLayout result = (android.widget.LinearLayout)
                this.layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.entries_table_cell, null);
            {
                assert null != result;
                final org.wheatgenetics.coordinate.display.Entry entry =
                    new org.wheatgenetics.coordinate.display.Entry(entryModel,
                        (android.widget.TextView)
                            result.findViewById(org.wheatgenetics.coordinate.R.id.entryTextView),
                        this, this.activeRow, this.activeCol);
                this.entryArray[entry.getRow()][entry.getCol()] = entry;
            }
            return result;
        }
    }
    // endregion
}