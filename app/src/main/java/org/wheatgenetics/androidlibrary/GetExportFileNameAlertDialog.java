package org.wheatgenetics.androidlibrary;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 */
@SuppressWarnings({"unused"})
public class GetExportFileNameAlertDialog extends AlertDialog
{
    @SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void handleGetFileNameDone(String fileName); }

    // region Fields
    private final Handler handler ;
    private       android.widget.EditText                                               editText;
    // endregion

    private void handleGetFileNameDone()
    {
        final String fileName =
            Utils.getText(this.editText);
        if (fileName.length() < 1)
            this.showToast(
                R.string.getExportFileNameAlertDialogToast);
        else
        {
            this.cancelAlertDialog();
            if (null != this.handler) this.handler.handleGetFileNameDone(fileName);
        }
    }

    public GetExportFileNameAlertDialog(@androidx.annotation.NonNull
    final android.app.Activity                                                  activity,
    final Handler handler )
    { super(activity); this.handler = handler; }

    @Override public void configure()
    {
        this.setTitle(R.string.getExportFileNameAlertDialogTitle);

        {
            @android.annotation.SuppressLint({"InflateParams"})
            final android.view.View view = this.layoutInflater().inflate(
                R.layout.alert_dialog_get_export_file_name,
                null);

            if (null != view) if (null == this.editText) this.editText = view.findViewById(
                R.id.editText);

            this.setView(view);
        }

        this.setOKPositiveButton(null).setCancelNegativeButton();
    }

    public void show(final String initialFileName)
    {
        if (null != this.editText)
        {
            this.editText.setText(initialFileName); this.editText.selectAll();

            this.show();
            if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
                new android.view.View.OnClickListener()
                {
                    @Override public void onClick(final android.view.View view)
                    {
                        GetExportFileNameAlertDialog
                            .this.handleGetFileNameDone();
                    }
                });
        }
    }
}