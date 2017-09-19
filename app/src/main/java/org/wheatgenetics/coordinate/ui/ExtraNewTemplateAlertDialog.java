package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog.Builder
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
 * org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class ExtraNewTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler
    {
        public abstract void addExcludes               ();
        public abstract void addNaming                 ();
        public abstract void handleExtraNewTemplateNext();
    }

    // region Fields
    private final android.app.Activity                                                activity;
    private final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler ;

    private org.wheatgenetics.coordinate.model.TemplateModel          templateModel;
    private org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog
        optionalFieldsAlertDialog = null;
    // endregion

    // region Private Methods
    private void addOptionalFields()
    {
        if (null == this.optionalFieldsAlertDialog) this.optionalFieldsAlertDialog =
            new org.wheatgenetics.coordinate.ui.OptionalFieldsAlertDialog(this.activity);
        this.optionalFieldsAlertDialog.show(this.templateModel);
    }

    private void addExcludes() { assert null != this.handler; this.handler.addExcludes(); }
    private void addNaming  () { assert null != this.handler; this.handler.addNaming  (); }

    private void handleNext()
    { assert null != this.handler; this.handler.handleExtraNewTemplateNext(); }
    // endregion

    ExtraNewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler)
    { super(activity); this.activity = activity; this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder makeBuilder(final int titleId)
    {
        super.makeBuilder(titleId);

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

        assert null != this.builder;
        this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
//                    assert null != dialog; dialog.cancel();  // TODO: Remove?
                    org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.this.handleNext();
                }
            });

        return this.setNegativeButton();
    }

    void show(final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        if (null != templateModel)
        {
            this.configure(org.wheatgenetics.coordinate.R.string.template_new);
            this.templateModel = templateModel; this.show();
        }
    }
}