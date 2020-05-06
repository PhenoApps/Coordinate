package org.wheatgenetics.coordinate.gc.ps;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.SelectAlertDialog;

class ProjectChoiceAlertDialog extends SelectAlertDialog {
    ProjectChoiceAlertDialog(final Activity activity, @NonNull final
    SelectAlertDialog.Handler handler) {
        super(activity, handler);
    }

    @NonNull
    private String[] items(
            @NonNull final String secondItem,
            @Nullable final String thirdItem) {
        final String firstItem = this.activity().getString(
                R.string.ProjectSetterDontAddItem);
        if (null == thirdItem)
            return new String[]{firstItem, secondItem};
        else
            return new String[]{firstItem, secondItem, thirdItem};
    }

    void show(
            @NonNull final String secondItem,
            @Nullable final String thirdItem) {
        this.show(R.string.ProjectChoiceAlertDialogTitle,
                this.items(secondItem, thirdItem));
    }
}