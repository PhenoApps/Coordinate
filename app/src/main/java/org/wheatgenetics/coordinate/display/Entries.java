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
class Entries extends java.lang.Object
{
    // region Fields
    private final org.wheatgenetics.coordinate.display.Entry.Handler handler;

    private android.view.LayoutInflater                layoutInflater;
    private org.wheatgenetics.coordinate.display.Entry entryArray[][];
    // endregion

    Entries(final android.view.LayoutInflater layoutInflater, final int rows, final int cols,
    final org.wheatgenetics.coordinate.display.Entry.Handler handler)
    { super(); this.handler = handler; this.allocate(layoutInflater, rows, cols); }

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
                        this.handler);
                this.entryArray[entry.getRow()][entry.getCol()] = entry;
            }
            return result;
        }
    }
    // endregion
}