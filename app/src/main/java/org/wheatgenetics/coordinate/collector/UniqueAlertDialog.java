package org.wheatgenetics.coordinate.collector;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;

class UniqueAlertDialog extends AlertDialog {
    UniqueAlertDialog(final Activity activity) {
        super(activity);
    }

    @Override
    public void configure() {
        this.setTitle(R.string.UniqueAlertDialogTitle);
    }

    void show(@NonNull final String message) {
        this.setMessage(message);
        this.createShow();
    }
}