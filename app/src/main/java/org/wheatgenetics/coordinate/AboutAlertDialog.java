package org.wheatgenetics.coordinate;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.about.OtherApps;

public class AboutAlertDialog {
    // region Fields
    @NonNull
    private final Activity activity;
    @NonNull
    private final String versionName;
    @NonNull
    private final View.OnClickListener
            versionOnClickListener;

    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialogInstance = null;   // lazy load
    // endregion

    public AboutAlertDialog(
            @NonNull final Activity activity,
            @NonNull final String versionName,
            @NonNull final View.OnClickListener versionOnClickListener) {
        super();

        this.activity = activity;
        this.versionName = versionName;
        this.versionOnClickListener = versionOnClickListener;
    }

    @Nullable
    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog() {
        if (null == this.aboutAlertDialogInstance) {
            // noinspection CStyleArrayDeclaration
            final String title, msgs[];
            {
                final Resources resources = this.activity.getResources();

                if (null == resources) {
                    title = null;
                    msgs = null;
                } else {
                    title = resources.getString(
                            R.string.AboutAlertDialogTitle);
                    msgs = org.wheatgenetics.javalib.Utils.stringArray(resources.getString(
                            R.string.AboutAlertDialogMsg));
                }
            }

            // noinspection ConstantConditions
            if (null != title && null != msgs) this.aboutAlertDialogInstance =
                    new org.wheatgenetics.about.AboutAlertDialog(
                            /* context       => */ this.activity,
                            /* title         => */ title,
                            /* versionName   => */ this.versionName,
                            /* msgs[]        => */ msgs,
                            /* suppressIndex => */ OtherApps.Index.COORDINATE,
                            /* versionOnClickListener => */ this.versionOnClickListener);
        }
        return this.aboutAlertDialogInstance;
    }

    public void show() {
        final org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = this.aboutAlertDialog();
        if (null != aboutAlertDialog) aboutAlertDialog.show();
    }
}