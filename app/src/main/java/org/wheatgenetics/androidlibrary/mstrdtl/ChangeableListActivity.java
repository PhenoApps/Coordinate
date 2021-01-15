package org.wheatgenetics.androidlibrary.mstrdtl;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * android.os.Bundle
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.javalib.mstrdtl.Item
 *
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment.HelperChanger
 * org.wheatgenetics.androidlibrary.mstrdtl.ListActivity
 */
public abstract class ChangeableListActivity
extends ListActivity
implements ItemFragment.HelperChanger
{
    private void append() { this.items().append(); this.refreshSinceItemsHaveChanged(); }

    // region Overridden Methods
    @Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final android.widget.Button appendItemButton = this.findViewById(
            R.id.appendItemButton);
        if (null != appendItemButton)
        {
            appendItemButton.setEnabled(true);
            appendItemButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @Override public void onClick(final android.view.View view)
                    {
                        ChangeableListActivity.this.append();
                    }
                });
        }
    }

    // region org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment.HelperChanger Overridden Methods
    @Override public void moveUp(@androidx.annotation.IntRange(
    from = org.wheatgenetics.javalib.mstrdtl.Item.MIN_POSITION) final int position)
    { this.items().moveUp(position); this.refreshSinceItemsHaveChanged(); }

    @Override public void moveDown(@androidx.annotation.IntRange(
    from = org.wheatgenetics.javalib.mstrdtl.Item.MIN_POSITION) final int position)
    { this.items().moveDown(position); this.refreshSinceItemsHaveChanged(); }

    @Override public void delete(@androidx.annotation.IntRange(
    from = org.wheatgenetics.javalib.mstrdtl.Item.MIN_POSITION) final int position)
    {
        this.items().delete(position);
        this.removeAndClearItemFragment();
        this.notifyDataSetChanged      ();
    }
    // endregion
    // endregion
}