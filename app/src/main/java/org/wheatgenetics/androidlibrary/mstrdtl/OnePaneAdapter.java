package org.wheatgenetics.androidlibrary.mstrdtl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.Intent
 * android.view.View
 * android.view.View.OnClickListener
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope

 * org.wheatgenetics.javalib.mstrdtl.Item
 * org.wheatgenetics.javalib.mstrdtl.Items
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.Adapter
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment
 * org.wheatgenetics.androidlibrary.mstrdtl.Utils
 */
public abstract class OnePaneAdapter extends Adapter
{
    static final int REQUEST_CODE = 1;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract Class concreteItemActivityClass();

    protected OnePaneAdapter(@androidx.annotation.NonNull
    final org.wheatgenetics.javalib.mstrdtl.Items items) { super(items); }

    @Override android.view.View.OnClickListener makeOnClickListener(
    @androidx.annotation.IntRange(from = org.wheatgenetics.javalib.mstrdtl.Item.MIN_POSITION)
        final int position)
    {
        return new android.view.View.OnClickListener()
            {
                @Override public void onClick(final android.view.View view)
                {
                    if (null != view)
                    {
                        final android.content.Context context = view.getContext();
                        if (context instanceof android.app.Activity)
                        {
                            final android.content.Intent intent = new android.content.Intent(
                                context, OnePaneAdapter
                                    .this.concreteItemActivityClass() /* polymorphism */);
                            intent.putExtra(
                                ItemFragment.POSITION_KEY,
                                position                                                          );
                            ((android.app.Activity) context).startActivityForResult(
                                Utils.putJsonIntoIntent(
                                    /* items => */ OnePaneAdapter.this.getItems(),
                                    /* intent => */ intent),
                                OnePaneAdapter.REQUEST_CODE);
                        }
                    }
                }
            };
    }
}