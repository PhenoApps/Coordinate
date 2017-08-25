package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
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
 * org.wheatgenetics.coordinate.ui.ActivityAlertDialog
 */
class LoadTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.ActivityAlertDialog
{
    interface Handler { public abstract void process(java.lang.String values[]); }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler handler;
    private       java.util.ArrayList<android.widget.EditText>   editTextArrayList = null;
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
        assert null != this.handler; this.handler.process(values);
    }

    LoadTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    void show(final java.lang.CharSequence title,
    final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields)
    {
        if (null == this.builder)
        {
            this.builder = this.makeBuilder().setCancelable(false)
                .setPositiveButton(org.wheatgenetics.coordinate.R.string.create,
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @java.lang.Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.this.process();
                            assert null != dialog; dialog.cancel();
                        }
                    })
                .setNegativeButton(org.wheatgenetics.coordinate.R.string.cancel,
                    org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            assert null != this.builder;
        }

        if (null == this.editTextArrayList)
            this.editTextArrayList = new java.util.ArrayList<android.widget.EditText>();
        else
            this.editTextArrayList.clear();

        this.builder.setTitle(title);
        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.grid_new);
            {
                final android.view.LayoutInflater layoutInflater = this.layoutInflater();
                assert null != layoutInflater;

                assert null != view;
                final android.widget.LinearLayout linearLayout = (android.widget.LinearLayout)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);
                assert null != linearLayout;

                assert null != checkedOptionalFields;
                for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                checkedOptionalFields)
                {
                    final android.view.View optionalFieldView = layoutInflater.inflate(
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
        assert null != alertDialog; alertDialog.getWindow().setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.show();
    }
}