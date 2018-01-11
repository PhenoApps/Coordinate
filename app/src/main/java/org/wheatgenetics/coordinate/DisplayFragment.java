package org.wheatgenetics.coordinate;

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
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 *
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 *
 * org.wheatgenetics.coordinate.R
 */
public class DisplayFragment extends android.support.v4.app.Fragment
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Handler
    { public abstract org.wheatgenetics.coordinate.model.JoinedGridModel getJoinedGridModel(); }

    // region Fields
    private org.wheatgenetics.coordinate.DisplayFragment.Handler handler           ;
    private android.widget.TableLayout                           entriesTableLayout;
    // endregion

    public DisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.coordinate.DisplayFragment.Handler)
            this.handler = (org.wheatgenetics.coordinate.DisplayFragment.Handler) context;
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
        assert null != activity; this.entriesTableLayout = (android.widget.TableLayout)
            activity.findViewById(org.wheatgenetics.coordinate.R.id.entriesTableLayout);

        this.populate();
    }

    @java.lang.Override
    public void onDetach() { this.handler = null; super.onDetach(); }
    // endregion

    void populate()
    {
        assert null != this.entriesTableLayout; this.entriesTableLayout.removeAllViews();

        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            null == this.handler ? null : this.handler.getJoinedGridModel();
        if (null != joinedGridModel)
        {
            final int                         lastCol = joinedGridModel.getCols();
            final android.view.LayoutInflater layoutInflater;
            {
                final android.app.Activity activity = this.getActivity();
                assert null != activity; layoutInflater = activity.getLayoutInflater();
            }

            // region Populate header row.
            {
                @android.annotation.SuppressLint("InflateParams")
                final android.widget.TableRow tableRow = (android.widget.TableRow)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.entries_table_row, null);
                {
                    final boolean colNumbering = joinedGridModel.getColNumbering();
                          byte    offsetFromA  = 0                                ;
                    assert null != tableRow; for (int col = 0; col <= lastCol; col++)
                    {
                        @android.annotation.SuppressLint("InflateParams")
                        final android.widget.LinearLayout tableCell = (android.widget.LinearLayout)
                            layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.top_entries_table_cell, null);
                        {
                            assert null != tableCell;
                            final android.widget.TextView textView = (android.widget.TextView)
                                tableCell.findViewById(
                                    org.wheatgenetics.coordinate.R.id.topEntryTextView);

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
                this.entriesTableLayout.addView(tableRow);
            }
            // endregion

            // region Populate body rows.
            final int     lastRow      = joinedGridModel.getRows        ();
            final boolean rowNumbering = joinedGridModel.getRowNumbering();
                  byte    offsetFromA  = 0                                ;
            for (int row = 1; row <= lastRow; row++)
            {
                @android.annotation.SuppressLint("InflateParams")
                final android.widget.TableRow tableRow = (android.widget.TableRow)
                    layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.entries_table_row, null);
                {
                    assert null != tableRow; for (int col = 0; col <= lastCol; col++)
                        if (0 == col)
                        {
                            @android.annotation.SuppressLint("InflateParams")
                            final android.widget.LinearLayout tableCell =
                                (android.widget.LinearLayout) layoutInflater.inflate(
                                    org.wheatgenetics.coordinate.R.layout.left_entries_table_cell,
                                    null                                                         );
                            {
                                assert null != tableCell;
                                final android.widget.TextView textView = (android.widget.TextView)
                                    tableCell.findViewById(
                                        org.wheatgenetics.coordinate.R.id.leftEntryTextView);

                                final java.lang.String text;
                                if (rowNumbering)
                                    text = "" + row;
                                else
                                {
                                    text =
                                        java.lang.Character.toString((char) ('A' + offsetFromA++));
                                    if (offsetFromA >= 26) offsetFromA = 0;
                                }
                                assert null != textView;
                                textView.setText(text);
                            }
                            tableRow.addView(tableCell);
                        }
                        else
                        {
                            @android.annotation.SuppressLint("InflateParams")
                            final android.widget.LinearLayout tableCell =
                                (android.widget.LinearLayout) layoutInflater.inflate(
                                    org.wheatgenetics.coordinate.R.layout.entries_table_cell,
                                    null                                                    );
                            {
                                assert null != tableCell; final android.widget.TextView textView =
                                    (android.widget.TextView) tableCell.findViewById(
                                        org.wheatgenetics.coordinate.R.id.entryTextView);

                                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                                    joinedGridModel.getEntryModel(row, col);

                                assert null != entryModel; assert null != textView;
                                textView.setBackgroundResource(entryModel.backgroundResource());
                            }
                            tableRow.addView(tableCell);
                        }
                }
                this.entriesTableLayout.addView(tableRow);
            }
            // endregion
        }
    }
}