package org.wheatgenetics.coordinate.activities;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.Button
 * android.widget.LinearLayout
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 */
class ExtraNewTemplateAlertDialog extends java.lang.Object
{
    interface Handler
    {
        public abstract void addOptionalFields(); public abstract void addExcludes();
        public abstract void addNaming()        ; public abstract void handleNext() ;
    }

    // region Fields
    private final android.app.Activity activity;
    private final org.wheatgenetics.coordinate.activities.ExtraNewTemplateAlertDialog.Handler
        handler;

    private android.app.AlertDialog         alertDialog  = null;
    private android.app.AlertDialog.Builder builder      = null;
    private android.widget.Button optionalButton = null, excludeButton = null, namingButton = null;
    // endregion

    // region Private Methods
    private void addOptionalFields()
    {
        assert null != this.handler;
        this.handler.addOptionalFields();
    }

    private void addExcludes()
    {
        assert null != this.handler;
        this.handler.addExcludes();
    }

    private void addNaming()
    {
        assert null != this.handler;
        this.handler.addNaming();
    }

    private void handleNext()
    {
        assert null != this.handler;
        this.handler.handleNext();
    }
    // endregion

    ExtraNewTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.activities.ExtraNewTemplateAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.activity);
                this.builder.setTitle(org.wheatgenetics.coordinate.R.string.template_new);
                {
                    android.view.View view;
                    {
                        assert null != this.activity;
                        final android.view.LayoutInflater layoutInflater =
                            this.activity.getLayoutInflater();
                        view = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.template_new_extra,
                            new android.widget.LinearLayout(this.activity), false);
                    }

                    assert null != view;
                    if (null == this.optionalButton)
                    {
                        this.optionalButton = (android.widget.Button)
                            view.findViewById(org.wheatgenetics.coordinate.R.id.optionalButton);
                        assert null != this.optionalButton;
                        this.optionalButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate.activities.
                                        ExtraNewTemplateAlertDialog.this.addOptionalFields();
                                }
                            });
                    }

                    if (null == this.excludeButton)
                    {
                        this.excludeButton = (android.widget.Button)
                            view.findViewById(org.wheatgenetics.coordinate.R.id.excludeButton);
                        assert null != this.excludeButton;
                        this.excludeButton.setOnClickListener(
                            new android.view.View.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate.activities.
                                        ExtraNewTemplateAlertDialog.this.addExcludes();
                                }
                            });
                    }

                    if (null == this.namingButton)
                    {
                        this.namingButton = (android.widget.Button)
                            view.findViewById(org.wheatgenetics.coordinate.R.id.namingButton);
                        assert null != this.namingButton;
                        this.namingButton.setOnClickListener(new android.view.View.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.coordinate.activities.
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
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            assert null != dialog;
                            dialog.cancel();

                            org.wheatgenetics.coordinate.activities.
                                ExtraNewTemplateAlertDialog.this.handleNext();
                        }
                    }).setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}