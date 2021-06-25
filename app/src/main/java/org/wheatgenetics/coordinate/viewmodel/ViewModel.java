package org.wheatgenetics.coordinate.viewmodel;

import androidx.annotation.IntRange;
import androidx.annotation.RestrictTo;

public abstract class ViewModel extends androidx.lifecycle.ViewModel {
    @IntRange(from = 1)
    private long id;

    @IntRange(from = 1)
    public long getId() {
        return this.id;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected void setId(@IntRange(from = 1) final long id) {
        this.id = id;
    }
}