package org.wheatgenetics.coordinate.display.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.R;

class TopViewHolder extends LeftViewHolder {
    TopViewHolder(
            @NonNull final Context context,
            @NonNull final LinearLayout itemView) {
        super(context, itemView, R.id.topDisplayTextView);
    }

    @SuppressLint({"Range"})
    @Override
    void bind(@IntRange(from = 0) final int column, final boolean numbering) {
        if (0 == column) this.bind("");
        else super.bind(column, numbering);
    }
}