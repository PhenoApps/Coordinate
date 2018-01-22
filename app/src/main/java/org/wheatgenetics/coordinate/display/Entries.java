package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
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
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler ;
    private final android.app.Activity                               activity;

    private org.wheatgenetics.coordinate.display.Entry entryArray[][];

    private int activeRow, activeCol;
    // endregion

    Entries(final android.app.Activity activity,
    final int rows, final int cols, final int activeRow, final int activeCol,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler)
    {
        super();

        this.handler = handler; this.activity = activity;
        this.allocate(rows, cols, activeRow, activeCol);
    }

    // region org.wheatgenetics.coordinate.display.Entry.Handler Overridden Methods
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

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { assert null != this.handler; this.handler.toggle(entryModel); }
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
            assert null != this.activity;
            final android.view.LayoutInflater layoutInflater = this.activity.getLayoutInflater();

            @android.annotation.SuppressLint("InflateParams")
            final android.widget.LinearLayout result = (android.widget.LinearLayout)
                layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.entries_table_cell, null);
            {
                assert null != result;
                final org.wheatgenetics.coordinate.display.Entry entry =
                    new org.wheatgenetics.coordinate.display.Entry(
                        /* context    => */ this.activity,
                        /* entryModel => */ entryModel   ,
                        /* textView   => */ (android.widget.TextView)
                            result.findViewById(org.wheatgenetics.coordinate.R.id.entryTextView),
                        /* handler   => */ this          ,
                        /* activeRow => */ this.activeRow,
                        /* activeCol => */ this.activeCol);
                assert null != this.entryArray;
                this.entryArray[entry.getRow()][entry.getCol()] = entry;
            }
            return result;
        }
    }
    // endregion
}