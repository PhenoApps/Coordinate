package org.wheatgenetics.coordinate.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

public abstract class Adapter extends BaseAdapter {
    // region Fields
    // region Constructor Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final View.OnClickListener
            onDeleteButtonClickListener, onExportButtonClickListener;
    // endregion

    private Resources resourcesInstance = null;                     // lazy load
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    protected Adapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener) {
        super();

        this.activity = activity;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
        this.onExportButtonClickListener = onExportButtonClickListener;
    }

    // region Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected Activity activity() {
        return this.activity;
    }

    // region onButtonClickListener() Protected Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected View.OnClickListener
    onDeleteButtonClickListener() {
        return this.onDeleteButtonClickListener;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected View.OnClickListener
    onExportButtonClickListener() {
        return this.onExportButtonClickListener;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected TableLayout makeEmptyTableLayout() {
        return new TableLayout(this.activity());
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    protected Resources resources() {
        if (null == this.resourcesInstance) this.resourcesInstance = this.activity().getResources();
        return this.resourcesInstance;
    }
}