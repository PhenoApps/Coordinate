package org.wheatgenetics.coordinate.tc;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
 *
 * org.wheatgenetics.coordinate.tc.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog
 */
class SetExcludesOptionalFieldsNumberingAlertDialog
extends org.wheatgenetics.androidlibrary.AlertDialog
implements org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void handleSetDone(); }

    // region Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode;
    @androidx.annotation.NonNull                    private final
        org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
            handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;                                    // lazy load
    private org.wheatgenetics.coordinate.tc.ExcludeAlertDialog
        excludeAlertDialog = null;                                                      // lazy load
    private org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog
        setNumberingAlertDialog = null;                                                 // lazy load
    // endregion

    // region Private Methods
    private void checkAndAddOptionalFields()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(),this);
        if (null != this.templateModel)
            this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel.optionalFields());
    }

    private void exclude()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.tc.ExcludeAlertDialog(
                this.activity(), this.requestCode);
        this.excludeAlertDialog.show(this.templateModel);
    }

    private void setNumbering()
    {
        if (null == this.setNumberingAlertDialog) this.setNumberingAlertDialog =
            new org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog(this.activity());
        this.setNumberingAlertDialog.show(this.templateModel);
    }

    private void handleSetDone() { this.handler.handleSetDone(); }
    // endregion

    SetExcludesOptionalFieldsNumberingAlertDialog(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
        handler) { super(activity); this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate
            .R.string.SetExcludesOptionalFieldsNumberingAlertDialogTitle);

        {
            final android.view.View view = this.inflate(
                org.wheatgenetics.coordinate.R.layout.set_excludes_optional_fields_numbering);

            if (null != view)
            {
                {
                    final android.widget.Button checkAndAddOptionalFieldsButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.checkAndAddOptionalFieldsButton);
                    if (null != checkAndAddOptionalFieldsButton)
                        checkAndAddOptionalFieldsButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate.tc
                                        .SetExcludesOptionalFieldsNumberingAlertDialog
                                        .this.checkAndAddOptionalFields();
                                }
                            });
                }
                {
                    final android.widget.Button excludeButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.excludeButton);
                    if (null != excludeButton) excludeButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            {
                                org.wheatgenetics.coordinate.tc
                                    .SetExcludesOptionalFieldsNumberingAlertDialog.this.exclude();
                            }
                        });
                }
                {
                    final android.widget.Button setNumberingButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.setNumberingButton);
                    if (null != setNumberingButton) setNumberingButton.setOnClickListener(
                        new android.view.View.OnClickListener()
                        {
                            @java.lang.Override public void onClick(final android.view.View v)
                            {
                                org.wheatgenetics.coordinate.tc
                                    .SetExcludesOptionalFieldsNumberingAlertDialog
                                    .this.setNumbering();
                            }
                        });
                }
            }
            this.setView(view);
        }

        this.setPositiveButton(org.wheatgenetics.coordinate
                .R.string.SetExcludesOptionalFieldsNumberingAlertDialogPositiveButtonText,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog
                        .this.handleSetDone();
                }
            }).setCancelNegativeButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    {
        if (null != this.templateModel && null != this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel.optionalFields());
    }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}