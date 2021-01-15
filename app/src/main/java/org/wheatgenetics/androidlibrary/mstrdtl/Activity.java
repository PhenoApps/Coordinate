package org.wheatgenetics.androidlibrary.mstrdtl;

/**
 * Uses:
 * android.app.Activity
 * android.content.Intent
 * android.os.Bundle
 * android.util.Log
 *
 * androidx.annotation.IdRes
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.appcompat.app.AppCompatActivity
 * androidx.fragment.app.Fragment
 * androidx.fragment.app.FragmentManager
 *
 * org.wheatgenetics.javalib.mstrdtl.Item
 * org.wheatgenetics.javalib.mstrdtl.Items
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.Consts
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment
 * org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment.Helper
 * org.wheatgenetics.androidlibrary.mstrdtl.Utils
 */
abstract class Activity extends androidx.appcompat.app.AppCompatActivity
implements ItemFragment.Helper
{
    private static final String ITEM_FRAGMENT_TAG = "itemFragmentTag";

    // region Fields
    private String                                      json = null ;
    private ItemFragment itemFragment;
    // endregion

    // region Private Methods
    private void log(@androidx.annotation.NonNull final String msg)
    { android.util.Log.d(this.getClass().getName(), msg); }

    @androidx.annotation.Nullable private String jsonFromItems()
    {
        final String trimmedJson;
        {
            final String json = this.items().toJson();
            if (null == json)
                return null;
            else
                trimmedJson = json.trim();
        }
        return trimmedJson.length() <= 0 ? null : trimmedJson;
    }

    @androidx.annotation.NonNull private android.content.Intent putJsonIntoIntent(
    @androidx.annotation.NonNull final android.content.Intent intent)
    {
        @androidx.annotation.Nullable final String json = this.jsonFromItems();
        this.log("setResultFromJson(): putJsonIntoIntent(): " + json);
        return Utils.putJsonIntoIntent(json, intent);
    }

    private static void removeFragment(
    @androidx.annotation.NonNull final androidx.fragment.app.FragmentManager fragmentManager,
    @androidx.annotation.NonNull final androidx.fragment.app.Fragment        fragment       )
    { fragmentManager.beginTransaction().remove(fragment).commit(); }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setJsonFromIntent(
    @androidx.annotation.NonNull final String       source,
    @androidx.annotation.NonNull final android.content.Intent intent)
    {
        {
            final String JSON_KEY =
                Consts.JSON_KEY;
            this.json = intent.hasExtra(JSON_KEY) ? intent.getStringExtra(JSON_KEY) : null;
        }
        this.log(source + ": setJsonFromIntent(): " + this.getJson());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setResultFromJson(final int resultCode)
    { this.setResult(resultCode, this.putJsonIntoIntent(new android.content.Intent())); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setResultFromJson() { this.setResultFromJson(android.app.Activity.RESULT_OK); }

    /** Called by ItemActivity.onCreate(). */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setAndAddItemFragment(@androidx.annotation.IdRes final int containerViewId,
    @androidx.annotation.NonNull final ItemFragment
        itemFragment)
    {
        this.getSupportFragmentManager().beginTransaction().add(
            containerViewId, this.itemFragment = itemFragment).commit();
    }

    /** Called by ListActivity.setAndReplaceItemFragment(). */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setAndReplaceItemFragment(@androidx.annotation.IdRes final int containerViewId,
    @androidx.annotation.NonNull
        final ItemFragment itemFragment)
    {
        this.getSupportFragmentManager().beginTransaction().replace(containerViewId,
            this.itemFragment = itemFragment                                   ,
            Activity.ITEM_FRAGMENT_TAG).commit();
    }

    /** Called by ListActivity.onActivityResult(). */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void removeAndClearItemFragmentByTag()
    {
        final androidx.fragment.app.FragmentManager fragmentManager =
            this.getSupportFragmentManager();
        final androidx.fragment.app.Fragment fragment = fragmentManager.findFragmentByTag(
            Activity.ITEM_FRAGMENT_TAG);
        if (null != fragment) Activity.removeFragment(
            fragmentManager, fragment);
    }

    /** Called by ChangeableListActivity.delete(). */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void removeAndClearItemFragment()
    {
        if (null != this.itemFragment)
        {
            Activity.removeFragment(
                this.getSupportFragmentManager(), this.itemFragment);
            this.itemFragment = null;
        }
    }

    /**
     * Called by ListActivity.refreshSinceItemsHaveChanged() and
     * ChangeableItemActivity.refreshSinceItemsHaveChanged().
     */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void refreshSinceItemsHaveChanged()
    { if (null != this.itemFragment) this.itemFragment.refreshSinceItemsHaveChanged(); }
    // endregion

    // region Protected Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    protected abstract org.wheatgenetics.javalib.mstrdtl.Items items();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected String getJson() { return this.json; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    protected void refreshSinceItemHasChanged()
    { if (null != this.itemFragment) this.itemFragment.refreshSinceItemHasChanged(); }
    // endregion

    // region Overridden Methods
    @Override protected void onCreate(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState)
            this.setJsonFromIntent("onCreate()", this.getIntent());
        else
        {
            final String JSON_KEY =
                Consts.JSON_KEY;
            this.json = savedInstanceState.containsKey(JSON_KEY) ?
                savedInstanceState.getString(JSON_KEY) : null;
        }
    }

    @Override protected void onSaveInstanceState(
    @androidx.annotation.NonNull final android.os.Bundle outState)
    {
        {
            @androidx.annotation.Nullable final String json = this.jsonFromItems();
            if (null != json) outState.putString(
                Consts.JSON_KEY, json);
        }
        super.onSaveInstanceState(outState);
    }

    @Override public void onBackPressed()
    { this.setResultFromJson(); super.onBackPressed(); }

    // region org.wheatgenetics.androidlibrary.mstrdtl.ItemFragment.Helper Overridden Method
    @Override public org.wheatgenetics.javalib.mstrdtl.Item get(
    @androidx.annotation.IntRange(from = org.wheatgenetics.javalib.mstrdtl.Item.MIN_POSITION)
    final int position) { return this.items().get(position); }
    // endregion
    // endregion
}