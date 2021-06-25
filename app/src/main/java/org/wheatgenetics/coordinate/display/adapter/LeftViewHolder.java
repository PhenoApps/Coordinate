package org.wheatgenetics.coordinate.display.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;

class LeftViewHolder extends TopOrLeftViewHolder {
    // region Constructors
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    LeftViewHolder(
            @NonNull final Context context,
            @NonNull final LinearLayout itemView,
            @IdRes final int textViewId) {
        super(context, itemView, textViewId);
    }

    LeftViewHolder(
            @NonNull final Context context,
            @NonNull final LinearLayout itemView) {
        this(context, itemView, R.id.leftDisplayTextView);
    }
    // endregion

    void bind(@IntRange(from = 1) final int rowOrCol, final boolean numbering) {
        this.bind(numbering ? Integer.toString(rowOrCol) :
                Utils.convert(rowOrCol - 1));
    }
}