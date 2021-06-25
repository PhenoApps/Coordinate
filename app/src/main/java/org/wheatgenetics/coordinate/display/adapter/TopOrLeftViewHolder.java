package org.wheatgenetics.coordinate.display.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.preference.Utils;

abstract class TopOrLeftViewHolder extends ViewHolder {
    // region Fields
    @NonNull
    private final Context context;
    @IdRes
    private final int textViewId;

    @Nullable
    private TextView textViewInstance = null;     // ll
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    TopOrLeftViewHolder(
            @NonNull final Context context,
            @NonNull final LinearLayout itemView,
            @IdRes final int textViewId) {
        super(itemView);
        this.context = context;
        this.textViewId = textViewId;
    }

    // region Private Methods
    @Nullable
    private TextView textView() {
        if (null == this.textViewInstance) {
            this.textViewInstance = this.itemView.findViewById(this.textViewId);
            this.textViewInstance.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    this.textViewInstance.getTextSize() *
                            Utils.getScaling(this.context));
        }
        return this.textViewInstance;
    }
    // endregion

    private void setTextViewText(@Nullable final TextView textView, final String text) {
        if (null != textView) textView.setText(text);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void bind(final String text) {
        this.setTextViewText(this.textView(), text);
    }
}