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
 * org.wheatgenetics.coordinate.ui.ShowingAlertDialog
 */
class ExtraNewTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ShowingAlertDialog
{
    interface Handler
    {
        public abstract void addOptionalFields(); public abstract void addExcludes();
        public abstract void addNaming        (); public abstract void handleNext ();
    }

    private final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler;

    // region Private Methods
    private void addOptionalFields()
    { assert null != this.handler; this.handler.addOptionalFields(); }

    private void addExcludes() { assert null != this.handler; this.handler.addExcludes(); }
    private void addNaming  () { assert null != this.handler; this.handler.addNaming  (); }
    private void handleNext () { assert null != this.handler; this.handler.handleNext (); }
    // endregion

    ExtraNewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    android.app.AlertDialog.Builder configure(final int titleId)
    {
        super.configure(titleId);

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

            this.builder.setView(view);
        }

        this.builder.setPositiveButton(org.wheatgenetics.coordinate.R.string.next,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert null != dialog; dialog.cancel();
                    org.wheatgenetics.coordinate.ui.ExtraNewTemplateAlertDialog.this.handleNext();
                }
            });

        return this.setNegativeButton();
    }

    void show()
    {
        this.configure(org.wheatgenetics.coordinate.R.string.template_new);
        super.show();
    }
}