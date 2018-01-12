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
    // region Fields
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler       ;
    private final android.view.LayoutInflater                        layoutInflater;

    private org.wheatgenetics.coordinate.display.Entry entryArray[][];

    private int activeRow, activeCol;
    // endregion

    Entries(final android.view.LayoutInflater layoutInflater,
    final int rows, final int cols, final int activeRow, final int activeCol,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler)
    {
        super();

        this.handler = handler; this.layoutInflater = layoutInflater;
        this.allocate(rows, cols, activeRow, activeCol);
    }

    // region org.wheatgenetics.coordinate.display.Entry.Handler Overridden Method
    @java.lang.Override
    public void activate(final org.wheatgenetics.coordinate.display.Entry entry)
    {
        if (null != entry)
        {
            final int newActiveRow = entry.getRow(), newActiveCol = entry.getCol();
            if (newActiveRow != this.activeRow || newActiveCol != this.activeCol)
            {
                if (this.activeRow > -1 && this.activeCol > -1)
                {
                    assert null != this.entryArray;
                    this.entryArray[this.activeRow][this.activeCol].inactivate();
                }

                this.activeRow = newActiveRow; this.activeCol = newActiveCol;

                assert null != this.handler; this.handler.activate(entry);
            }
        }
    }
    // endregion

    // region Package Methods
    void clear() { this.entryArray = null; this.activeRow = this.activeCol = -1; }

    void allocate(final int rows, final int cols, final int activeRow, final int activeCol)
    {
        this.entryArray = new org.wheatgenetics.coordinate.display.Entry[rows][cols];
        this.activeRow  = activeRow;                      this.activeCol = activeCol;

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