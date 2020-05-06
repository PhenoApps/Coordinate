package org.wheatgenetics.coordinate;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.ItemsAlertDialog;

public class SelectAlertDialog extends ItemsAlertDialog {
    @NonNull
    private final
    SelectAlertDialog.Handler handler;

    public SelectAlertDialog(final Activity activity, @NonNull final
    SelectAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }

    private void select(final int which) {
        this.handler.select(which);
    }

    @Override
    public void configure() {
        this.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                SelectAlertDialog.this.select(which);
            }
        });
    }

    public void show(@StringRes final int title,
                     @SuppressWarnings({"CStyleArrayDeclaration"})
                     @Size(min = 1) final String items[]) {
        this.setTitle(title);
        this.show(items);
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void select(int which);
    }
}