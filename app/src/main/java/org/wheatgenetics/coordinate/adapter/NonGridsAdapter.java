package org.wheatgenetics.coordinate.adapter;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

public abstract class NonGridsAdapter extends Adapter {
    @NonNull
    private final View.OnClickListener
            onCreateGridButtonClickListener, onShowGridsButtonClickListener;

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected NonGridsAdapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onCreateGridButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener,
            @NonNull final View.OnClickListener
                    onShowGridsButtonClickListener) {
        super(activity, onDeleteButtonClickListener, onExportButtonClickListener);

        this.onCreateGridButtonClickListener = onCreateGridButtonClickListener;
        this.onShowGridsButtonClickListener = onShowGridsButtonClickListener;
    }

    // region Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected View.OnClickListener
    onCreateGridButtonClickListener() {
        return this.onCreateGridButtonClickListener;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected View.OnClickListener
    onShowGridsButtonClickListener() {
        return this.onShowGridsButtonClickListener;
    }
}