package org.wheatgenetics.coordinate.gc.ts;

import android.app.Activity;

import androidx.annotation.RestrictTo;

abstract class TemplateSetter {
    private final Activity activity;

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    TemplateSetter(final Activity activity) {
        super();
        this.activity = activity;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Activity activity() {
        return this.activity;
    }
}