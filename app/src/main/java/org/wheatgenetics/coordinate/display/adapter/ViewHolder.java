package org.wheatgenetics.coordinate.display.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

abstract class ViewHolder extends RecyclerView.ViewHolder {
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    ViewHolder(@NonNull final View itemView) {
        super(itemView);
    }
}