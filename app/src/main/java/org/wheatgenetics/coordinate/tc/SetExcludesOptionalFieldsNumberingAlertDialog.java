package org.wheatgenetics.coordinate.tc;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import org.phenoapps.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog;
import org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialog;

class SetExcludesOptionalFieldsNumberingAlertDialog
        extends AlertDialog
        implements CheckAndAddOptionalFieldsAlertDialog.Handler {
    // region Fields
    @Types.RequestCode
    private final int requestCode;
    @NonNull
    private final
    SetExcludesOptionalFieldsNumberingAlertDialog.Handler
            handler;
    private TemplateModel templateModel;
    private CheckAndAddOptionalFieldsAlertDialog
            checkAndAddOptionalFieldsAlertDialogInstance = null;                            // lazy load
    private ExcludeAlertDialog
            excludeAlertDialogInstance = null;                                              // lazy load
    private SetNumberingAlertDialog
            setNumberingAlertDialogInstance = null;                                         // lazy load
    SetExcludesOptionalFieldsNumberingAlertDialog(final Activity activity,
                                                  @Types.RequestCode final int requestCode,
                                                  @NonNull final
                                                  SetExcludesOptionalFieldsNumberingAlertDialog.Handler
                                                          handler) {
        super(activity);
        this.requestCode = requestCode;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    // region checkAndAddOptionalFields() Private Methods
    @NonNull
    private CheckAndAddOptionalFieldsAlertDialog
    checkAndAddOptionalFieldsAlertDialog() {
        if (null == this.checkAndAddOptionalFieldsAlertDialogInstance)
            this.checkAndAddOptionalFieldsAlertDialogInstance =
                    new CheckAndAddOptionalFieldsAlertDialog(
                            this.activity(), this);
        return this.checkAndAddOptionalFieldsAlertDialogInstance;
    }

    private void checkAndAddOptionalFields() {
        if (null != this.templateModel)
            this.checkAndAddOptionalFieldsAlertDialog().show(this.templateModel.optionalFields());
    }
    // endregion

    // region exclude() Private Methods
    @NonNull
    private ExcludeAlertDialog excludeAlertDialog() {
        if (null == this.excludeAlertDialogInstance) this.excludeAlertDialogInstance =
                new ExcludeAlertDialog(
                        this.activity(), this.requestCode);
        return this.excludeAlertDialogInstance;
    }

    private void exclude() {
        this.excludeAlertDialog().show(this.templateModel);
    }
    // endregion

    // region setNumbering() Private Methods
    @NonNull
    private SetNumberingAlertDialog setNumberingAlertDialog() {
        if (null == this.setNumberingAlertDialogInstance) this.setNumberingAlertDialogInstance =
                new SetNumberingAlertDialog(this.activity());
        return this.setNumberingAlertDialogInstance;
    }

    private void setNumbering() {
        this.setNumberingAlertDialog().show(this.templateModel);
    }
    // endregion

    private void handleSetDone() {
        this.handler.handleSetDone();
    }
    // endregion

    // region Overridden Methods
    @Override
    public void configure() {
        this.setTitle(R.string.SetExcludesOptionalFieldsNumberingAlertDialogTitle);

        {
            final View view = this.inflate(
                    R.layout.set_excludes_optional_fields_numbering);

            if (null != view) {
                {
                    final Button checkAndAddOptionalFieldsButton = view.findViewById(
                            R.id.checkAndAddOptionalFieldsButton);
                    if (null != checkAndAddOptionalFieldsButton)
                        checkAndAddOptionalFieldsButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        SetExcludesOptionalFieldsNumberingAlertDialog
                                                .this.checkAndAddOptionalFields();
                                    }
                                });
                }
                {
                    final Button excludeButton = view.findViewById(
                            R.id.excludeButton);
                    if (null != excludeButton) excludeButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    SetExcludesOptionalFieldsNumberingAlertDialog.this.exclude();
                                }
                            });
                }
                {
                    final Button setNumberingButton = view.findViewById(
                            R.id.setNumberingButton);
                    if (null != setNumberingButton) setNumberingButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    SetExcludesOptionalFieldsNumberingAlertDialog
                                            .this.setNumbering();
                                }
                            });
                }
            }
            this.setView(view);
        }

        this.setPositiveButton(R.string.SetExcludesOptionalFieldsNumberingAlertDialogPositiveButtonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        SetExcludesOptionalFieldsNumberingAlertDialog
                                .this.handleSetDone();
                    }
                }).setCancelNegativeButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler Overridden Method
    @Override
    public void showCheckAndAddOptionalFieldsAlertDialogAgain() {
        if (null != this.templateModel && null != this.checkAndAddOptionalFieldsAlertDialogInstance)
            this.checkAndAddOptionalFieldsAlertDialogInstance.show(
                    this.templateModel.optionalFields());
    }

    void show(final TemplateModel templateModel) {
        if (null != templateModel) {
            this.templateModel = templateModel;
            this.show();
        }
    }
    // endregion
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public abstract void handleSetDone();
    }
}