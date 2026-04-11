package org.wheatgenetics.coordinate.display.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

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
    private float mBaseTextSizePx = -1f;
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
            mBaseTextSizePx = this.textViewInstance.getTextSize() * Utils.getScaling(this.context);
            this.textViewInstance.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBaseTextSizePx);
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

    /** Called from Adapter to display a custom label string directly (bypasses subclass restriction). */
    void bindLabel(final String text, final boolean compact, final float scaleFactor) {
        bind(text, compact, scaleFactor);
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void bind(final String text, final boolean compact, final float scaleFactor) {
        TextView tv = this.textView();
        if (null != tv) {
            int hPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    compact ? 2 : 10, this.context.getResources().getDisplayMetrics());
            int vPx = compact ? 0 : (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    10, this.context.getResources().getDisplayMetrics());
            ViewCompat.setPaddingRelative(tv, hPx, vPx, hPx, vPx);
            if (mBaseTextSizePx > 0) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        compact ? mBaseTextSizePx * scaleFactor : mBaseTextSizePx);
            }
            tv.setText(text);
        }
    }
}