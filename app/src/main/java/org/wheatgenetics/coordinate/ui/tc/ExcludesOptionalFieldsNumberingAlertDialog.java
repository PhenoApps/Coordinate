package org.wheatgenetics.coordinate.ui.tc;

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
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog.Handler
 * org.wheatgenetics.coordinate.ui.tc.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.ui.tc.SetNumberingAlertDialog
 */
class ExcludesOptionalFieldsNumberingAlertDialog
extends org.wheatgenetics.androidlibrary.AlertDialog
implements org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog.Handler
{
    interface Handler { public abstract void handleExtraNewTemplateNext(); }

    // region Fields
    private final
        org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog.Handler
        handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog
        checkAndAddOptionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.tc.ExcludeAlertDialog excludeAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.tc.SetNumberingAlertDialog
        setNumberingAlertDialog = null;
    // endregion

    // region Private Methods
    private void checkAndAddOptionalFields()
    {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                new org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog(
                    this.activity(), this);
        this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel);
    }

    private void exclude()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.ui.tc.ExcludeAlertDialog(this.activity());
        this.excludeAlertDialog.show(this.templateModel);
    }

    private void setNumbering()
    {
        if (null == this.setNumberingAlertDialog) this.setNumberingAlertDialog =
            new org.wheatgenetics.coordinate.ui.tc.SetNumberingAlertDialog(this.activity());
        this.setNumberingAlertDialog.show(this.templateModel);
    }

    private void handleNext()
    { assert null != this.handler; this.handler.handleExtraNewTemplateNext(); }
    // endregion

    ExcludesOptionalFieldsNumberingAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.tc.ExcludesOptionalFieldsNumberingAlertDialog.Handler
        handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.template_new);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.template_new_extra);

            assert null != view;
            {
                final android.widget.Button checkAndAddOptionalFieldsButton =
                    (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
                assert null != checkAndAddOptionalFieldsButton;
                checkAndAddOptionalFieldsButton.setOnClickListener(
                    new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.ui.tc
                                .ExcludesOptionalFieldsNumberingAlertDialog.this
                                .checkAndAddOptionalFields();
                        }
                    });
            }

            {
                final android.widget.Button excludeButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton);
                assert null != excludeButton;
                excludeButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.ui.tc
                                .ExcludesOptionalFieldsNumberingAlertDialog.this.exclude();
                        }
                    });
            }

            {
                final android.widget.Button setNumberingButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton);
                assert null != setNumberingButton;
                setNumberingButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.ui.tc
                                .ExcludesOptionalFieldsNumberingAlertDialog.this.setNumbering();
                        }
                    });
            }

            this.setView(view);
        }

        this.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    org.wheatgenetics.coordinate.ui.tc
                        .ExcludesOptionalFieldsNumberingAlertDialog.this.handleNext();
                }
            }).setCancelNegativeButton();
    }

    // region org.wheatgenetics.coordinate.ui.tc.CheckAndAddOptionalFieldsAlertDialog.HandlerOverridden Method
    @java.lang.Override
    public void handleAddOptionalFieldDone()
    {
        assert null != this.checkAndAddOptionalFieldsAlertDialog;
        this.checkAndAddOptionalFieldsAlertDialog.show(this.templateModel);
    }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}