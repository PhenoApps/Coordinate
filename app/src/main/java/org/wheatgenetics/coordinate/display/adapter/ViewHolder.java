package org.wheatgenetics.coordinate.display.adapter;

/**
 * Uses:
 * android.view.View
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.recyclerview.widget.RecyclerView.ViewHolder
 */
abstract class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder
{
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    ViewHolder(@androidx.annotation.NonNull final android.view.View itemView) { super(itemView); }
}