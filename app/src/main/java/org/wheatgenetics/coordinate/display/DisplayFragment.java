package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.LinearLayout
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.display.Entries
 * org.wheatgenetics.coordinate.display.Entry.Handler
 */
public class DisplayFragment extends android.support.v4.app.Fragment
implements org.wheatgenetics.coordinate.display.Entry.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    {
        public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel();
        public abstract int getActiveRow();                  public abstract int getActiveCol();

        public abstract void activate(int row, int col);
        public abstract void toggle  (org.wheatgenetics.coordinate.model.EntryModel entryModel);
    }

    // region Fields
    private org.wheatgenetics.coordinate.display.DisplayFragment.Handler handler       ;
    private android.widget.TableLayout                                   tableLayout   ;
    private org.wheatgenetics.coordinate.display.Entries                 entries = null;
    // endregion

    public DisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.coordinate.display.DisplayFragment.Handler)
            this.handler = (org.wheatgenetics.coordinate.display.DisplayFragment.Handler) context;
        else
        {
            assert null != context; throw new java.lang.RuntimeException(context.toString() +
                " must implement Handler");
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.coordinate.R.layout.fragment_display, container, false);
    }

    @java.lang.Override
    public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final android.app.Activity activity = this.getActivity();
        assert null != activity; this.tableLayout = (android.widget.TableLayout)
            activity.findViewById(org.wheatgenetics.coordinate.R.id.displayTableLayout);

        this.populate();
    }

    @java.lang.Override
    public void onDetach() { this.handler = null; super.onDetach(); }

    // region org.wheatgenetics.coordinate.display.Entry.Handler Overridden Methods
    @java.lang.Override
    public void activate(final org.wheatgenetics.coordinate.display.Entry entry)
    {
        assert null != entry; assert null != this.handler;
        this.handler.activate(entry.getRow(), entry.getCol());
    }

    @java.lang.Override
    public void toggle(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { assert null != this.handler; this.handler.toggle(entryModel); }
    // endregion
    // endregion

    public void populate()
    {
        if (null != this.entries) this.entries.clear();
        assert null != this.tableLayout; this.tableLayout.removeAllViews();

        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            null == this.handler ? null : this.handler.getJoinedGridModel();
        if (null != joinedGridModel)
        {
            final int                  lastCol  = joinedGridModel.getCols();
            final android.app.Activity activity = this.getActivity()       ;

            assert null != activity;
            final android.view.LayoutInflater layoutInflater = activity.getLayoutInflater();

            // region Populate header row.
            {
                @android.annotation.SuppressLint("InflateParams")
                final android.widget.TableRow tableRow = (android.widget.TableRow)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.display_table_row, null);
                {
                    final boolean colNumbering = joinedGridModel.getColNumbering();
                          byte    offsetFromA  = 0                                ;
                    assert null != tableRow; for (int col = 0; col <= lastCol; col++)
                    {
                        @android.annotation.SuppressLint("InflateParams")
                        final android.widget.LinearLayout tableCell = (android.widget.LinearLayout)
                            layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.top_display_table_cell, null);
                        {
                            assert null != tableCell;
                            final android.widget.TextView textView = (android.widget.TextView)
                                tableCell.findViewById(
                                    org.wheatgenetics.coordinate.R.id.topDisplayTextView);

                            final java.lang.String text;
                            if (0 == col)
                                text = "";
                            else
                                if (colNumbering)
                                    text = "" + col;
                                else
                                {
                                    text =
                                        java.lang.Character.toString((char) ('A' + offsetFromA++));
                                    if (offsetFromA >= 26) offsetFromA = 0;
                                }
                            assert null != textView; textView.setText(text);
                        }
                        tableRow.addView(tableCell);
                    }
                }
                this.tableLayout.addView(tableRow);
            }
            // endregion

            // region Populate body rows.
            final int lastRow = joinedGridModel.getRows();
            if (null == this.entries)
                this.entries = new org.wheatgenetics.coordinate.display.Entries(activity, lastRow,
                    lastCol, this.handler.getActiveRow(), this.handler.getActiveCol(), this);
            else
                this.entries.allocate(lastRow, lastCol,
                    this.handler.getActiveRow(), this.handler.getActiveCol());

            final boolean rowNumbering = joinedGridModel.getRowNumbering();
                  byte    offsetFromA  = 0                                ;
            for (int row = 1; row <= lastRow; row++)
            {
                @android.annotation.SuppressLint("InflateParams")
                final android.widget.TableRow tableRow = (android.widget.TableRow)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.display_table_row, null);
                assert null != tableRow; for (int col = 0; col <= lastCol; col++)
                    if (0 == col)
                    {
                        @android.annotation.SuppressLint("InflateParams")
                        final android.widget.LinearLayout tableCell = (android.widget.LinearLayout)
                            layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.left_display_table_cell,
                                null                                                         );
                        {
                            assert null != tableCell;
                            final android.widget.TextView textView = (android.widget.TextView)
                                tableCell.findViewById(
                                    org.wheatgenetics.coordinate.R.id.leftDisplayTextView);

                            final java.lang.String text;
                            if (rowNumbering)
                                text = "" + row;
                            else
                            {
                                text = java.lang.Character.toString((char) ('A' + offsetFromA++));
                                if (offsetFromA >= 26) offsetFromA = 0;
                            }
                            assert null != textView; textView.setText(text);
                        }
                        tableRow.addView(tableCell);
                    }
                    else
                        tableRow.addView(this.entries.add(joinedGridModel.getEntryModel(row, col)));
                this.tableLayout.addView(tableRow);
            }
            // endregion
        }
    }
}