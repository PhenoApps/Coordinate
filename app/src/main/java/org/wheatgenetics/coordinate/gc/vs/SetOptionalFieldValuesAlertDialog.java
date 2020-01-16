package org.wheatgenetics.coordinate.gc.vs;

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
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.gc.vs.Handler
 */
class SetOptionalFieldValuesAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    // region Fields
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.gc.vs.Handler handler;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields       ;
    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields;
    private boolean                                                          firstCannotBeEmpty   ;
    @androidx.annotation.Nullable private java.util.ArrayList<android.widget.EditText>
        editTextArrayList = null;
    // endregion

    private void setValues()
    {
        if (null != this.checkedOptionalFields && null != this.editTextArrayList)
        {
            if (this.checkedOptionalFields.size() != this.editTextArrayList.size())
                throw new java.lang.AssertionError();

            boolean firstWasEmptyWhenItWasNotSupposedToBe = false;
            {
                final int first = 0    ;
                      int i     = first;
                for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                baseOptionalField: this.checkedOptionalFields)
                {
                    final java.lang.String value;
                    {
                        final android.widget.EditText editText = this.editTextArrayList.get(i);
                        value = null == editText ? "" :
                            org.wheatgenetics.androidlibrary.Utils.getText(editText);
                    }

                    if (this.firstCannotBeEmpty && first == i)
                    {
                        final int emptyLength = 0;
                        if (emptyLength == value.length())
                        {
                            {
                                final java.lang.String optionalFieldHint =
                                    baseOptionalField.getHint();
                                this.showToast(optionalFieldHint.length() > emptyLength ?
                                    optionalFieldHint : baseOptionalField.getName() +
                                    this.getString(org.wheatgenetics.coordinate
                                        .R.string.SetOptionalFieldValuesAlertDialogToast));
                            }
                            firstWasEmptyWhenItWasNotSupposedToBe = true;
                        }
                    }

                    baseOptionalField.setValue(value);
                    if (baseOptionalField.isAPerson())
                        this.handler.setPerson(baseOptionalField.getValue());

                    i++;
                }
            }

            if (!firstWasEmptyWhenItWasNotSupposedToBe)
                { this.cancelAlertDialog(); this.handler.handleSetValuesDone(this.optionalFields); }
        }
    }

    SetOptionalFieldValuesAlertDialog(final android.app.Activity                       activity,
    @androidx.annotation.NonNull      final org.wheatgenetics.coordinate.gc.vs.Handler handler )
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setCancelableToFalse()
            .setPositiveButton(org.wheatgenetics.coordinate
                .R.string.SetOptionalFieldValuesAlertDialogPositiveButtonText)
            .setCancelNegativeButton();
    }

    void show(final java.lang.String title, @androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields    ,
    final boolean                                                          firstCannotBeEmpty)
    {
        this.setTitle(title);

        this.optionalFields        = optionalFields;
        this.checkedOptionalFields = new
            org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(this.optionalFields);
        this.firstCannotBeEmpty = firstCannotBeEmpty;

        if (null == this.editTextArrayList)
            // noinspection Convert2Diamond
            this.editTextArrayList = new java.util.ArrayList<android.widget.EditText>();
        else
            this.editTextArrayList.clear();
        {
            final android.view.View view =
                this.inflate(org.wheatgenetics.coordinate.R.layout.set_optional_field_values);
            if (null != view)
            {
                final android.widget.LinearLayout linearLayout = view.findViewById(
                    org.wheatgenetics.coordinate.R.id.setOptionalFieldValuesLinearLayout);
                final android.view.LayoutInflater layoutInflater = this.layoutInflater();
                if (null != layoutInflater && null != linearLayout)
                    for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                    baseOptionalField: this.checkedOptionalFields)
                    {
                        final android.view.View optionalFieldView = layoutInflater.inflate(
                            org.wheatgenetics.coordinate.R.layout.optional_field_value,
                            linearLayout,false);
                        if (null != optionalFieldView)
                        {
                            {
                                final android.widget.TextView optionalFieldValueTextView =
                                    optionalFieldView.findViewById(org.wheatgenetics
                                        .coordinate.R.id.optionalFieldValueTextView);
                                if (null != optionalFieldValueTextView)
                                    optionalFieldValueTextView.setText(baseOptionalField.getName());
                            }
                            {
                                final android.widget.EditText optionalFieldValueEditText =
                                    optionalFieldView.findViewById(org.wheatgenetics
                                        .coordinate.R.id.optionalFieldValueEditText);
                                if (null != optionalFieldValueEditText)
                                {
                                    optionalFieldValueEditText.setText(
                                        baseOptionalField.getValue());
                                    optionalFieldValueEditText.setHint(baseOptionalField.getHint());
                                }
                                this.editTextArrayList.add(optionalFieldValueEditText);
                            }
                            linearLayout.addView(optionalFieldView);
                        }
                    }
            }
            this.setView(view);
        }

        this.createModifyShow();
        if (!this.positiveOnClickListenerHasBeenReplaced()) this.replacePositiveOnClickListener(
            new android.view.View.OnClickListener()
            {
                @java.lang.Override public void onClick(final android.view.View view)
                {
                    org.wheatgenetics.coordinate.gc.vs
                        .SetOptionalFieldValuesAlertDialog.this.setValues();
                }
            });
    }
}