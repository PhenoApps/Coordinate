package org.wheatgenetics.coordinate.adapter;

/**
 * Uses:
 * android.app.Activity
 * android.view.View.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.adapter.Adapter
 */
public abstract class NonGridsAdapter extends org.wheatgenetics.coordinate.adapter.Adapter
{
    @androidx.annotation.NonNull private final android.view.View.OnClickListener
        onCreateGridButtonClickListener, onShowGridsButtonClickListener;

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.view.View.OnClickListener
    onCreateGridButtonClickListener() { return this.onCreateGridButtonClickListener; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.view.View.OnClickListener
    onShowGridsButtonClickListener() { return this.onShowGridsButtonClickListener; }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected NonGridsAdapter(
    @androidx.annotation.NonNull final android.app.Activity              activity,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onCreateGridButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onDeleteButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onExportButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onShowGridsButtonClickListener)
    {
        super(activity, onDeleteButtonClickListener, onExportButtonClickListener);

        this.onCreateGridButtonClickListener = onCreateGridButtonClickListener;
        this.onShowGridsButtonClickListener  = onShowGridsButtonClickListener ;
    }
}