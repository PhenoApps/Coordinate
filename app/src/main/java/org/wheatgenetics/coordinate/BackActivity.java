package org.wheatgenetics.coordinate;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.wheatgenetics.coordinate.utils.InsetHandler;

public abstract class BackActivity extends AppCompatActivity {
    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                try {
                    getOnBackPressedDispatcher().onBackPressed();
                } finally {
                    setEnabled(true);
                }
            }
        });
        InsetHandler.enableEdgeToEdge(this);

        @Nullable final ActionBar supportActionBar = this.getSupportActionBar();
        if (null != supportActionBar) {
            //supportActionBar.setDisplayHomeAsUpEnabled(/* showHomeAsUp => */ true);
            supportActionBar.setTitle(null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
    // endregion
}
