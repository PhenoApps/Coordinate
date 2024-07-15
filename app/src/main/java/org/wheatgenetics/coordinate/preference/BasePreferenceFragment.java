package org.wheatgenetics.coordinate.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import org.wheatgenetics.coordinate.R;

public class BasePreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    public void setToolbar(String title) {
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
