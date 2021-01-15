package org.wheatgenetics.androidlibrary.mstrdtl;

import org.wheatgenetics.coordinate.R;

/**
 * An activity representing a list of items.  This activity has different presentations for one-pane
 * and two-pane mode.  In one-pane mode, the activity presents a list of items which, when tapped,
 * opens an {@link ItemActivity} that presents item content.  In two-pane mode, the activity
 * presents the list of items and item content side-by-side using two vertical panes.
 *
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 *
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.recyclerview.widget.RecyclerView
 *
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.Activity
 * org.wheatgenetics.androidlibrary.mstrdtl.Adapter
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment
 * org.wheatgenetics.androidlibrary.mstrdtl.OnePaneAdapter
 * org.wheatgenetics.androidlibrary.mstrdtl.TwoPaneAdapter
 * org.wheatgenetics.androidlibrary.mstrdtl.TwoPaneAdapter.Helper
 */
public abstract class ListActivity extends Activity
{
    private Adapter adapter;

    // region Private Methods
    private void setAndReplaceItemFragment(
    final ItemFragment itemFragment)
    {
        this.setAndReplaceItemFragment(
            R.id.content_container, itemFragment);
    }

    private void initializeItems() { this.items().fromJson(this.getJson()); }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract OnePaneAdapter makeOnePaneAdapter();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void notifyDataSetChanged() { if (null != this.adapter) this.adapter.notifyDataSetChanged(); }
    // endregion

    // region Overridden Methods
    @Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mstrdtl_list_activity);

        {
            // The item content container view will be present only in the wide-screen layouts
            // (res/values-w900dp).  If this view is present then the activity should be in two-pane
            // mode.
            final boolean twoPane = null != this.findViewById(
                R.id.content_container);    // From layout-w900dp-
            this.adapter = twoPane ?                                         //  list_container.xml.
                new TwoPaneAdapter(this.items(),
                    new TwoPaneAdapter.Helper()
                    {
                        @Override public void replace(final
                        ItemFragment itemFragment)
                        {
                            ListActivity
                                .this.setAndReplaceItemFragment(itemFragment);
                        }
                    }) :
                this.makeOnePaneAdapter();
        }
        {
            final androidx.recyclerview.widget.RecyclerView recyclerView = this.findViewById(
                R.id.masterDetailRecyclerView);
            if (null != recyclerView) recyclerView.setAdapter(this.adapter);
        }
    }

    @Override protected void onStart()
    { super.onStart(); this.refreshSinceItemsHaveChanged(); }

    @Override protected void onActivityResult(final int requestCode, final int resultCode,
                                              @androidx.annotation.Nullable final android.content.Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (OnePaneAdapter.REQUEST_CODE == requestCode
        &&  null                                                                 != data       )
        {
            this.setJsonFromIntent("onActivityResult()", data); this.initializeItems();
            switch (resultCode)
            {
                case android.app.Activity.RESULT_OK: this.refreshSinceItemsHaveChanged(); break;

                case android.app.Activity.RESULT_FIRST_USER:
                    this.removeAndClearItemFragmentByTag(); break;
            }
        }
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @Override void refreshSinceItemsHaveChanged()
    { super.refreshSinceItemsHaveChanged(); this.notifyDataSetChanged(); }

    @Override public void setToolbarTitle  (final CharSequence title) {}
    @Override public void clearToolbarTitle()                                   {}
    // endregion
}