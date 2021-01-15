package org.wheatgenetics.androidlibrary.mstrdtl;

import org.wheatgenetics.coordinate.R;

/**
 * An activity representing item content.  This activity is only used in one-pane mode.  In two-pane
 * mode item content is presented side-by-side with a list of items in a {@link ListActivity}.
 *
 * Uses:
 * android.content.Intent
 * android.os.Bundle
 * android.R
 * android.view.MenuItem
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.appcompat.app.ActionBar
 *
 * com.google.android.material.appbar.CollapsingToolbarLayout
 *
 * org.wheatgenetics.androidlibrary.R
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.Activity
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment
 */
public abstract class ItemActivity extends Activity
{
    private com.google.android.material.appbar.CollapsingToolbarLayout
        collapsingToolbarLayout = null;

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected abstract Class listActivityClass();

    // region Overridden Methods
    @Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mstrdtl_item_activity);

        {
            // Show the Up button in the action bar.
            @androidx.annotation.Nullable
            final androidx.appcompat.app.ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.collapsingToolbarLayout = this.findViewById(
            R.id.masterDetailItemCollapsingToolbarLayout);

        // savedInstanceState is non-null when there is fragment state saved from previous
        // configurations of this activity (e.g., when rotating the screen from portrait to
        // landscape).  In this case, the fragment will automatically be re-added to its container
        // so we don't need to manually add it.  If savedInstanceState is null then there is no
        // state saved from previous configurations of this activity.  In this case, the fragment
        // will not be automatically re-added to its container so we do need to manually add it.
        // For more information, see the Fragments API guide at
        // http://developer.android.com/guide/components/fragments.html.
        if (null == savedInstanceState)
        {
            // Create the fragment and add it to the activity using a fragment transaction.
            final ItemFragment itemFragment =
                new ItemFragment();
            {
                final android.content.Intent intent = this.getIntent();
                if (null != intent)
                {
                    final String POSITION_KEY =
                        ItemFragment.POSITION_KEY;
                    if (intent.hasExtra(POSITION_KEY))
                    {
                        final android.os.Bundle arguments = new android.os.Bundle();
                        arguments.putInt(POSITION_KEY, intent.getIntExtra(
                            POSITION_KEY, /* defaultValue => */-1));
                        itemFragment.setArguments(arguments);
                    }
                }
            }
            this.setAndAddItemFragment(
                R.id.masterDetailNestedScrollView, itemFragment);
        }
    }

    @Override public boolean onOptionsItemSelected(
    @androidx.annotation.NonNull final android.view.MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home)
        {
            // This id represents the Home or Up button.  In the case of this activity, the Up
            // button is shown.  For more details, see the Navigation pattern on Android Design
            // (http://developer.android.com/design/patterns/navigation.html#up-vs-back).
            this.setResultFromJson();
            this.navigateUpTo(new android.content.Intent(
                this, this.listActivityClass()));
            return true;
        }
        else return super.onOptionsItemSelected(menuItem);
    }

    @Override public void setToolbarTitle(final CharSequence title)
    { if (null != this.collapsingToolbarLayout) this.collapsingToolbarLayout.setTitle(title); }

    @Override public void clearToolbarTitle() { this.setToolbarTitle(null); }
    // endregion
}