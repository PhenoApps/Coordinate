package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class BasePreferenceFragment extends PreferenceFragmentCompat {

    private String mRootKey;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        mRootKey = rootKey;
    }

    protected void setToolbar(String title) {
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            AppCompatActivity act = (AppCompatActivity) getActivity();
            if (act.getSupportActionBar() != null) {
                ActionBar bar = act.getSupportActionBar();
                bar.setTitle(title);
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    // for toolbar with no action or title
    protected void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(null);
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getContext() != null) {
                Context ctx = getContext();
                Intent intent = PreferenceActivity.intent(ctx);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
