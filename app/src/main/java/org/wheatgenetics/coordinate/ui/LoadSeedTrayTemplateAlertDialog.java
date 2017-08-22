package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.view.WindowManager.LayoutParams
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.R
 */
class LoadSeedTrayTemplateAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void process(java.lang.String values[]); }

    // region Fields
    private final android.app.Activity                                                    activity;
    private final org.wheatgenetics.coordinate.ui.LoadSeedTrayTemplateAlertDialog.Handler handler ;

    private android.app.AlertDialog.Builder              builder           = null;
    private java.util.ArrayList<android.widget.EditText> editTextArrayList = null;
    private android.view.LayoutInflater                  layoutInflater    = null;
    // endregion

    private void process()
    {
        assert null != this.editTextArrayList;
        final java.lang.String values[] = new java.lang.String[this.editTextArrayList.size()];
        {
            int i = 0;
            for (final android.widget.EditText editText: this.editTextArrayList)
                if (null != editText) values[i] = editText.getText().toString().trim();
        }

        assert null != this.handler;
        this.handler.process(values);
    }

    LoadSeedTrayTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.LoadSeedTrayTemplateAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    void show(final java.lang.CharSequence title,
    final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields)
    {
        if (null == this.builder)
        {
            this.builder = new android.app.AlertDialog.Builder(this.activity);
            this.builder.setCancelable(false)
                .setPositiveButton(org.wheatgenetics.coordinate.R.string.create,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.ui.
                                LoadSeedTrayTemplateAlertDialog.this.process();
                        }
                    })
                .setNegativeButton(
                    org.wheatgenetics.coordinate.R.string.cancel                      ,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
        }

        if (null == this.editTextArrayList)
            this.editTextArrayList = new java.util.ArrayList<android.widget.EditText>();

        {
            android.view.View view;
            {
                if (null == this.layoutInflater)
                {
                    assert null != this.activity;
                    this.layoutInflater = this.activity.getLayoutInflater();
                }
                view = this.layoutInflater.inflate(org.wheatgenetics.coordinate.R.layout.grid_new,
                    new android.widget.LinearLayout(this.activity), false);

                assert null != view;
                final android.widget.LinearLayout linearLayout = (android.widget.LinearLayout)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

                assert null != checkedOptionalFields; assert null != linearLayout;
                for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                checkedOptionalFields)
                {
                    final android.view.View optionalFieldView = this.layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.optional_edit, linearLayout, false);

                    assert null != optionalFieldView;
                    {
                        final android.widget.TextView optionalFieldTextView =
                            (android.widget.TextView) optionalFieldView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionText);

                        assert null != optionalFieldTextView;
                        optionalFieldTextView.setText(optionalField.getName());
                    }

                    {
                        final android.widget.EditText optionalFieldEditText =
                            (android.widget.EditText) optionalFieldView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionEdit);

                        assert null != optionalFieldEditText;
                        optionalFieldEditText.setText(optionalField.getValue());
                        optionalFieldEditText.setHint(optionalField.getHint ());

                        this.editTextArrayList.add(optionalFieldEditText);
                    }

                    linearLayout.addView(optionalFieldView);
                }
            }
            this.builder.setView(view);
        }

        final android.app.AlertDialog alertDialog = this.builder.create();
        assert null != alertDialog;
        alertDialog.setTitle(title);
        alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.show();
    }
}