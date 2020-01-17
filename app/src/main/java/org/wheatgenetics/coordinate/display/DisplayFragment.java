package org.wheatgenetics.coordinate.display;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.LinearLayout
 * android.widget.TableLayout
 * android.widget.TableRow
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.fragment.app.Fragment
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 *
 * org.wheatgenetics.coordinate.display.Element.Handler
 * org.wheatgenetics.coordinate.display.Elements
 */
public abstract class DisplayFragment extends androidx.fragment.app.Fragment
implements org.wheatgenetics.coordinate.display.Element.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) protected interface Handler
    {
        public abstract org.wheatgenetics.coordinate.model.DisplayModel         getDisplayModel();
        public abstract void toggle(org.wheatgenetics.coordinate.model.ElementModel elementModel);
    }

    // region Fields
    @androidx.annotation.Nullable protected
        org.wheatgenetics.coordinate.display.DisplayFragment.Handler handler;
    @androidx.annotation.Nullable private   android.widget.TableLayout                  tableLayout;
    @androidx.annotation.Nullable protected org.wheatgenetics.coordinate.display.Elements elements ;
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract boolean setHandler(final android.content.Context context);

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract void allocateElements(
    @androidx.annotation.IntRange(from = 1) final int lastRow,
    @androidx.annotation.IntRange(from = 1) final int lastCol);
    // endregion

    // region Overridden Methods
    @java.lang.Override public void onAttach(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        super.onAttach(context);

        if (!this.setHandler(context))
            throw new java.lang.RuntimeException(context.toString() + " must implement Handler");
    }

    @java.lang.Override public android.view.View onCreateView(
    @androidx.annotation.NonNull  final android.view.LayoutInflater inflater          ,
    @androidx.annotation.Nullable final android.view.ViewGroup      container         ,
    @androidx.annotation.Nullable final android.os.Bundle           savedInstanceState)
    {
        // Inflate the layout for this fragment:
        return inflater.inflate(org.wheatgenetics.coordinate.R.layout.fragment_display,
            container,false);
    }

    @java.lang.Override public void onActivityCreated(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final android.app.Activity activity = this.getActivity();
        if (null == activity)
            this.tableLayout = null;
        else
            this.tableLayout =
                activity.findViewById(org.wheatgenetics.coordinate.R.id.displayTableLayout);

        this.populate();
    }

    @java.lang.Override public void onDetach() { this.handler = null; super.onDetach(); }

    // region org.wheatgenetics.coordinate.display.Element.Handler Overridden Method
    public void toggle(@androidx.annotation.Nullable final
    org.wheatgenetics.coordinate.model.ElementModel elementModel)
    { if (null != this.handler) this.handler.toggle(elementModel); }
    // endregion
    // endregion

    public void populate()
    {
        if (null != this.elements) this.elements.clear();
        if (null == this.tableLayout) return; else this.tableLayout.removeAllViews();

        final org.wheatgenetics.coordinate.model.DisplayModel displayModel =
            null == this.handler ? null : this.handler.getDisplayModel();
        if (null != displayModel)
        {
            final int                         lastCol = displayModel.getCols();
            final android.view.LayoutInflater layoutInflater                  ;
            {
                final android.app.Activity activity = this.getActivity();
                if (null == activity) return; else layoutInflater = activity.getLayoutInflater();
            }

            // region Populate header row.
            {
                @java.lang.SuppressWarnings({"InflateParams"})
                final android.widget.TableRow tableRow =
                    (android.widget.TableRow) layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.display_table_row,null);
                {
                    final boolean colNumbering = displayModel.getColNumbering();
                          byte    offsetFromA  = 0                             ;
                    if (null == tableRow) return;
                    for (int col = 0; col <= lastCol; col++)
                    {
                        @java.lang.SuppressWarnings({"InflateParams"})
                        final android.widget.LinearLayout tableCell =
                            (android.widget.LinearLayout) layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.top_display_table_cell,
                                null);
                        {
                            if (null == tableCell) return;
                            final android.widget.TextView textView = tableCell.findViewById(
                                org.wheatgenetics.coordinate.R.id.topDisplayTextView);

                            if (null == textView) return;
                            textView.setText(0 == col ? "" : colNumbering ? "" + col :
                                org.wheatgenetics.coordinate.Utils.convert(offsetFromA++));
                        }
                        tableRow.addView(tableCell);
                    }
                }
                this.tableLayout.addView(tableRow);
            }
            // endregion

            // region Populate body rows.
            final int lastRow = displayModel.getRows();
            this.allocateElements(lastRow, lastCol); if (null == this.elements) return;

            final boolean rowNumbering = displayModel.getRowNumbering();
                  byte    offsetFromA  = 0                             ;
            for (int row = 1; row <= lastRow; row++)
            {
                @java.lang.SuppressWarnings({"InflateParams"})
                final android.widget.TableRow tableRow =
                    (android.widget.TableRow) layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.display_table_row,null);
                if (null == tableRow) return;
                for (int col = 0; col <= lastCol; col++)
                    if (0 == col)
                    {
                        @java.lang.SuppressWarnings({"InflateParams"})
                        final android.widget.LinearLayout tableCell =
                            (android.widget.LinearLayout) layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.left_display_table_cell,
                                null);
                        {
                            if (null == tableCell) return;
                            final android.widget.TextView textView = tableCell.findViewById(
                                org.wheatgenetics.coordinate.R.id.leftDisplayTextView);

                            if (null == textView) return;
                            textView.setText(rowNumbering ? "" + row :
                                org.wheatgenetics.coordinate.Utils.convert(offsetFromA++));
                        }
                        tableRow.addView(tableCell);
                    }
                    else
                    {
                        final org.wheatgenetics.coordinate.model.ElementModel elementModel =
                            displayModel.getElementModel(row, col);
                        if (null == elementModel)
                            break;
                        else
                            tableRow.addView(this.elements.add(elementModel));
                    }
                this.tableLayout.addView(tableRow);
            }
            // endregion
        }
    }
}