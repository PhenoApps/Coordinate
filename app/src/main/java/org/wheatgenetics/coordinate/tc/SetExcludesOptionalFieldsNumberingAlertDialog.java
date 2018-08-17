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
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler { public abstract void handleSetDone(); }

    // region Fields
    @org.wheatgenetics.coordinate.Types.RequestCode private final int requestCode;
    private final
        org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
            handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.tc.ExcludeAlertDialog      excludeAlertDialog      = null;
    private org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog setNumberingAlertDialog = null;
    // endregion

    // region Private Methods
    private void checkAndAddOptionalFields()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(),this);
        assert null != this.templateModel;
        this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel.optionalFields());
    }

    private void exclude()
    {
        if (null == this.excludeAlertDialog)
            this.excludeAlertDialog = new org.wheatgenetics.coordinate.tc.ExcludeAlertDialog(
                this.activity(), this.requestCode);
        this.excludeAlertDialog.show(this.templateModel);
    }

    private void setNumbering()
    {
        if (null == this.setNumberingAlertDialog) this.setNumberingAlertDialog =
            new org.wheatgenetics.coordinate.tc.SetNumberingAlertDialog(this.activity());
        this.setNumberingAlertDialog.show(this.templateModel);
    }

    private void handleSetDone() { assert null != this.handler; this.handler.handleSetDone(); }
    // endregion

    SetExcludesOptionalFieldsNumberingAlertDialog(final android.app.Activity activity,
    @org.wheatgenetics.coordinate.Types.RequestCode final int requestCode,
    final org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialog.Handler
        handler) { super(activity); this.requestCode = requestCode; this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate
            .R.string.SetExcludesOptionalFieldsNumberingAlertDialogTitle);

        {
            final android.view.View view = this.inflate(
                org.wheatgenetics.coordinate.R.layout.set_excludes_optional_fields_numbering);

            assert null != view;
            {
                final android.widget.Button checkAndAddOptionalFieldsButton = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.checkAndAddOptionalFieldsButton);
                assert null != checkAndAddOptionalFieldsButton;
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
                assert null != excludeButton;
                excludeButton.setOnClickListener(new android.view.View.OnClickListener()
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
                assert null != setNumberingButton;
                setNumberingButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.tc
                                .SetExcludesOptionalFieldsNumberingAlertDialog.this.setNumbering();
                        }
                    });
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

    // region org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.HandlerOverridden Method
    @java.lang.Override public void handleAddOptionalFieldDone()
    {
        assert null != this.templateModel; assert null != this.checkAndAddOptionalFieldsAlertDialog;
        this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel.optionalFields());
    }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}