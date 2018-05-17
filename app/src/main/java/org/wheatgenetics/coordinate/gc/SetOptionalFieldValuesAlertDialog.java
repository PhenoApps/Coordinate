package org.wheatgenetics.coordinate.gc;

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
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 */
class SetOptionalFieldValuesAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler
    {
        public abstract void setPerson(java.lang.String person);
        public abstract void handleSetValuesDone();
    }

    // region Fields
    private final org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler handler;

    private java.util.ArrayList<android.widget.EditText>                  editTextArrayList = null;
    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields;
    private boolean                                                          firstCannotBeEmpty   ;
    // endregion

    private void setValues()
    {
        assert null != this.checkedOptionalFields; assert null != this.editTextArrayList;
        if (this.checkedOptionalFields.size() != this.editTextArrayList.size())
            throw new java.lang.AssertionError();

        boolean firstWasEmptyWhenItWasNotSupposedToBe = false;
        {
            int i = 0; assert null != this.handler;
            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: this.checkedOptionalFields)
            {
                final java.lang.String value;
                {
                    final android.widget.EditText editText = this.editTextArrayList.get(i);
                    value = null == editText ? "" :
                        org.wheatgenetics.androidlibrary.Utils.getText(editText);
                }

                if (this.firstCannotBeEmpty && 0 == i)
                    if (0 == value.length())
                    {
                        {
                            final java.lang.String optionalFieldHint = baseOptionalField.getHint();
                            this.showToast(optionalFieldHint.length() > 0 ? optionalFieldHint :
                                baseOptionalField.getName() + this.getString(org.wheatgenetics
                                    .coordinate.R.string.SetOptionalFieldValuesAlertDialogToast));
                        }
                        firstWasEmptyWhenItWasNotSupposedToBe = true;
                    }

                baseOptionalField.setValue(value);
                if (baseOptionalField.isAPerson())
                    this.handler.setPerson(baseOptionalField.getValue());

                i++;
            }
        }

        if (!firstWasEmptyWhenItWasNotSupposedToBe)
            { this.cancelAlertDialog(); this.handler.handleSetValuesDone(); }
    }

    SetOptionalFieldValuesAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.gc.SetOptionalFieldValuesAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setCancelableToFalse()
            .setPositiveButton(org.wheatgenetics.coordinate
                .R.string.SetOptionalFieldValuesAlertDialogPositiveButtonText)
            .setCancelNegativeButton();
    }

    @java.lang.SuppressWarnings({"Convert2Diamond"})
    void show(final java.lang.String title,
    final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields,
    final boolean                                                          firstCannotBeEmpty   )
    {
        this.setTitle(title);

        if (null == this.editTextArrayList)
            this.editTextArrayList = new java.util.ArrayList<android.widget.EditText>();
        else
            this.editTextArrayList.clear();
        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.set_optional_field_values);
            {
                assert null != view;
                final android.widget.LinearLayout linearLayout =
                    (android.widget.LinearLayout) view.findViewById(
                        org.wheatgenetics.coordinate.R.id.setOptionalFieldValuesLinearLayout);

                final android.view.LayoutInflater layoutInflater = this.layoutInflater();


                assert null != checkedOptionalFields;
                assert null != layoutInflater       ; assert null != linearLayout;
                for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                baseOptionalField: checkedOptionalFields)
                {
                    final android.view.View optionalFieldView = layoutInflater.inflate(
                        org.wheatgenetics.coordinate.R.layout.optional_field_value,
                        linearLayout, false);

                    assert null != optionalFieldView;
                    {
                        final android.widget.TextView optionalFieldValueTextView =
                            (android.widget.TextView) optionalFieldView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionalFieldValueTextView);

                        assert null != optionalFieldValueTextView;
                        optionalFieldValueTextView.setText(baseOptionalField.getName());
                    }

                    {
                        final android.widget.EditText optionalFieldValueEditText =
                            (android.widget.EditText) optionalFieldView.findViewById(
                                org.wheatgenetics.coordinate.R.id.optionalFieldValueEditText);

                        assert null != optionalFieldValueEditText;
                        optionalFieldValueEditText.setText(baseOptionalField.getValue());
                        optionalFieldValueEditText.setHint(baseOptionalField.getHint ());

                        this.editTextArrayList.add(optionalFieldValueEditText);
                    }

                    linearLayout.addView(optionalFieldView);
                }
            }
            this.setView(view);
        }

        this.checkedOptionalFields = checkedOptionalFields;
        this.firstCannotBeEmpty    = firstCannotBeEmpty   ;
        this.createModifyShow();

        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                {
                    org.wheatgenetics.coordinate.gc
                        .SetOptionalFieldValuesAlertDialog.this.setValues();
                }
            });
    }
}