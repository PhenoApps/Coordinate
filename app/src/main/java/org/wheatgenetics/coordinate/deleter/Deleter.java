package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.GridsTable;

abstract class Deleter {
    // region Fields
    @NonNull
    private final Context context;

    private GridsTable gridsTableInstance = null; // lazy load
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Deleter(@NonNull final Context context) {
        super();
        this.context = context;
    }

    // region Private Methods
    private void showLongToast(final String text) {
        Utils.showLongToast(this.context(), text);
    }
    // endregion

    private void showShortToast(final String text) {
        Utils.showShortToast(this.context(), text);
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    Context context() {
        return this.context;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.context());
        return this.gridsTableInstance;
    }

    // region Toast Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void showLongToast(@StringRes final int text) {
        this.showLongToast(this.context().getString(text));
    }
    // endregion
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void showShortToast(@StringRes final int text) {
        this.showShortToast(this.context().getString(text));
    }
}