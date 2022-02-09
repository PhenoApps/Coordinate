package org.wheatgenetics.coordinate;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BackActivity extends AppCompatActivity {
    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        @Nullable final ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) {
            //supportActionBar.setDisplayHomeAsUpEnabled(/* showHomeAsUp => */ true);
            supportActionBar.setTitle(null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }
    // endregion
}