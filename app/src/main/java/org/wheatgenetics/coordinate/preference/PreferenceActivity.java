package org.wheatgenetics.coordinate.preference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

import org.wheatgenetics.coordinate.BackActivity;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.R;

public class PreferenceActivity extends BackActivity
        implements Preference.OnPreferenceClickListener {
    private static Intent INTENT_INSTANCE = null;                       // lazy load
    // region Fields
    private boolean uniquenessPreferenceWasClicked;
    // endregion

    @NonNull
    public static Intent intent(
            @NonNull final Context context) {
        return null == PreferenceActivity.INTENT_INSTANCE ?
                PreferenceActivity.INTENT_INSTANCE =
                        new Intent(context,
                                PreferenceActivity.class) :
                PreferenceActivity.INTENT_INSTANCE;
    }

    private void setResult() {
        final Intent intent = new Intent();
        {
            final Bundle bundle = new Bundle();
            bundle.putBoolean(
                    Types.UNIQUENESS_BUNDLE_KEY,
                    this.uniquenessPreferenceWasClicked);
            intent.putExtras(bundle);
        }
        this.setResult(Activity.RESULT_OK, intent);
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // noinspection SimplifiableConditionalExpression
        this.uniquenessPreferenceWasClicked =
                null == savedInstanceState ? false : savedInstanceState.getBoolean(
                        Types.UNIQUENESS_BUNDLE_KEY, false);

        // Display PreferenceFragment as the main content.
        this.getSupportFragmentManager().beginTransaction().replace(R.id.content,
                new PreferenceFragment()).commit();
    }

    @Override
    protected void onSaveInstanceState(
            @NonNull final Bundle outState) {
        outState.putBoolean(
                Types.UNIQUENESS_BUNDLE_KEY,
                this.uniquenessPreferenceWasClicked);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(
            @NonNull final MenuItem item) {
        this.setResult();
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.setResult();
        super.onBackPressed();
    }
    // endregion
    // endregion

    // region androidx.preference.Preference.OnPreferenceClickListener Overridden Method
    @Override
    public boolean onPreferenceClick(final Preference preference) {
        this.uniquenessPreferenceWasClicked = true;
        return true;
    }
}