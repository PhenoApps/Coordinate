package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 * org.wheatgenetics.coordinate.ui.ExcludeAlertDialog
 * org.wheatgenetics.coordinate.ui.NamingAlertDialog
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler
 */
class ExtraNewTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
implements org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.Handler
{
    interface Handler { public abstract void handleExtraNewTemplateNext(); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler;

    private org.wheatgenetics.coordinate.model.TemplateModel templateModel;

    private org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
        optionalFieldsAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.ExcludeAlertDialog excludeAlertDialog = null;
    private org.wheatgenetics.coordinate.ui.NamingAlertDialog  namingAlertDialog  = null;
    // endregion

    // region Private Methods
    private void addOptionalFields()
    {
        if (null == this.optionalFieldsAlertDialog) this.optionalFieldsAlertDialog =
            new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog(this.activity(), this);
        this.optionalFieldsAlertDialog.show(this.templateModel);
    }

    private void addExcludes()
    {
        if (null == this.excludeAlertDialog) this.excludeAlertDialog =
            new org.wheatgenetics.coordinate.ui.ExcludeAlertDialog(this.activity());
        this.excludeAlertDialog.show(this.templateModel);
    }

    private void addNaming()
    {
        if (null == this.namingAlertDialog) this.namingAlertDialog =
            new org.wheatgenetics.coordinate.ui.NamingAlertDialog(this.activity());
        assert null != this.templateModel; this.namingAlertDialog.show(this.templateModel);
    }

    private void handleNext()
    { assert null != this.handler; this.handler.handleExtraNewTemplateNext(); }
    // endregion

    ExtraNewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    // region Overridden Methods
    @java.lang.Override
    void configure()
    {
        this.setTitle(org.wheatgenetics.coordinate.R.string.template_new);

        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.template_new_extra);

            assert null != view;
            {
                final android.widget.Button optionalButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
                assert null != optionalButton;
                optionalButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.ui.
                                ExtraNewTemplateAlertDialog.this.addOptionalFields();
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
                            org.wheatgenetics.coordinate.ui.
                                ExtraNewTemplateAlertDialog.this.addExcludes();
                        }
                    });
            }

            {
                final android.widget.Button namingButton = (android.widget.Button)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton);
                assert null != namingButton;
                namingButton.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.view.View v)
                        {
                            org.wheatgenetics.coordinate.ui.
                                ExtraNewTemplateAlertDialog.this.addNaming();
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
                { org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.this.handleNext(); }
            }).setNegativeButton();
    }

    // region org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog.HandlerOverridden Method
    @java.lang.Override
    public void showOptionalFieldsAlertDialog()
    { this.optionalFieldsAlertDialog.show(this.templateModel); }
    // endregion
    // endregion

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) { this.templateModel = templateModel; this.show(); } }
}