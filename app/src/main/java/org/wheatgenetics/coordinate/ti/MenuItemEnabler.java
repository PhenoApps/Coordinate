package org.wheatgenetics.coordinate.ti;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.TemplatesDir;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.javalib.Dir;


import java.io.IOException;

public class MenuItemEnabler {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;
    // endregion

    public MenuItemEnabler(@NonNull final Activity activity,
                           final int requestCode) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
    }

    private void showLongToast(final String text) {
        org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text);
    }

    public boolean shouldBeEnabled() {
        try {
            final TemplatesDir templatesDir =
                    Utils.templatesDir(  // throws java.io.IOException,
                            this.activity,                             //  org.wheatgenetics.javalib-
                            this.requestCode);                            //  .Dir.PermissionException
            return templatesDir.atLeastOneXmlFileExists();
        } catch (final IOException | Dir.PermissionException e) {
            if (!(e instanceof Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            return false;
        }
    }
}