package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * android.content.Context
 * android.util.TypedValue
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * androidx.annotation.IdRes
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.preference.Utils
 *
 * org.wheatgenetics.coordinate.display.adapter.ViewHolder
 */
abstract class TopOrLeftViewHolder extends org.wheatgenetics.coordinate.display.adapter.ViewHolder
{
    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context   ;
    @androidx.annotation.IdRes   private final int                     textViewId;

    @androidx.annotation.Nullable private android.widget.TextView textViewInstance = null;     // ll
    // endregion

    // region Private Methods
    @androidx.annotation.Nullable private android.widget.TextView textView()
    {
        if (null == this.textViewInstance)
        {
            this.textViewInstance = this.itemView.findViewById(this.textViewId);
            this.textViewInstance.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX,
                this.textViewInstance.getTextSize() *
                    org.wheatgenetics.coordinate.preference.Utils.getScaling(this.context));
        }
        return this.textViewInstance;
    }

    private void setTextViewText(@androidx.annotation.Nullable
    final android.widget.TextView textView, final java.lang.String text)
    { if (null != textView) textView.setText(text); }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void bind(final java.lang.String text) { this.setTextViewText(this.textView(), text); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    TopOrLeftViewHolder(
    @androidx.annotation.NonNull final android.content.Context     context   ,
    @androidx.annotation.NonNull final android.widget.LinearLayout itemView  ,
    @androidx.annotation.IdRes   final int                         textViewId)
    { super(itemView); this.context = context; this.textViewId = textViewId; }
}