package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 */
class SetOptionalFieldValuesAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler
    {
        public abstract void setPerson(java.lang.String person);
        public abstract void handleSetValuesDone();
    }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog.Handler handler;

    private java.util.ArrayList<android.widget.EditText>                  editTextArrayList = null;
    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields;
    private boolean                                                          firstCannotBeEmpty   ;
    // endregion

    private void setValues()
    {
        assert null != this.checkedOptionalFields; assert null != this.editTextArrayList;
        assert this.checkedOptionalFields.size() == this.editTextArrayList.size();

        boolean firstWasEmptyWhenItWasNotSupposedToBe = false;
        {
            int i = 0; assert null != this.handler;
            for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
            this.checkedOptionalFields)
            {
                java.lang.String value;
                {
                    final android.widget.EditText editText = this.editTextArrayList.get(i);
                    value = null == editText ? "" :
                        org.wheatgenetics.androidlibrary.Utils.getText(editText);
                }

                if (this.firstCannotBeEmpty && 0 == i)
                    if (0 == value.length())
                    {
                        {
                            final java.lang.String optionalFieldHint = optionalField.getHint();
                            this.showToast(optionalFieldHint.length() > 0 ? optionalFieldHint :
                                optionalField.getName() + this.getString(
                                    org.wheatgenetics.coordinate.R.string.not_empty));
                        }
                        firstWasEmptyWhenItWasNotSupposedToBe = true;
                    }

                optionalField.setValue(value);
                if (optionalField.isAPerson()) this.handler.setPerson(optionalField.getValue());

                i++;
            }
        }

        if (!firstWasEmptyWhenItWasNotSupposedToBe)
            { this.cancelAlertDialog(); this.handler.handleSetValuesDone(); }
    }

    SetOptionalFieldValuesAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.SetOptionalFieldValuesAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setCancelableToFalse().setPositiveButton(org.wheatgenetics.coordinate.R.string.create)
            .setCancelNegativeButton();
    }

    void show(final java.lang.String title,
    final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields,
    final boolean firstCannotBeEmpty)
    {
        this.setTitle(title);

        if (null == this.editTextArrayList)
            this.editTextArrayList = new java.util.ArrayList<android.widget.EditText>();
        else
            this.editTextArrayList.clear();
        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.grid_new);
            {
                assert null != view;
                final android.widget.LinearLayout optionalFieldsLinearLayout =
                    (android.widget.LinearLayout)
                    view.findViewById(org.wheatgenetics.coordinate.R.id.optionalLayout);

                final android.view.LayoutInflater layoutInflater = this.layoutInflater();


                assert null != checkedOptionalFields;
                assert null != layoutInflater; assert null != optionalFieldsLinearLayout;
                for (final org.wheatgenetics.coordinate.optionalField.OptionalField optionalField:
                checkedOptionalFields)
                {
                    final android.view.View optionalFieldView = layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.optional_edit,
                        optionalFieldsLinearLayout, false);

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

                    optionalFieldsLinearLayout.addView(optionalFieldView);
                }
            }
            this.setView(view);
        }

        this.checkedOptionalFields = checkedOptionalFields;
        this.firstCannotBeEmpty    = firstCannotBeEmpty   ;
        this.createModifiyShow();

        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.view.View view)
                {
                    org.wheatgenetics.coordinate.ui
                        .SetOptionalFieldValuesAlertDialog.this.setValues();
                }
            });
    }
}