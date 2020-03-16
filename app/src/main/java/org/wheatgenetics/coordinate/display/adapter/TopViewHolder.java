package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.widget.LinearLayout
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.display.adapter.LeftViewHolder
 */
class TopViewHolder extends org.wheatgenetics.coordinate.display.adapter.LeftViewHolder
{
    TopViewHolder(
    @androidx.annotation.NonNull final android.content.Context     context ,
    @androidx.annotation.NonNull final android.widget.LinearLayout itemView)
    { super(context, itemView, org.wheatgenetics.coordinate.R.id.topDisplayTextView); }

    @android.annotation.SuppressLint({"Range"}) @java.lang.Override
    void bind(@androidx.annotation.IntRange(from = 0) final int column, final boolean numbering)
    { if (0 == column) this.bind(""); else super.bind(column, numbering); }
}