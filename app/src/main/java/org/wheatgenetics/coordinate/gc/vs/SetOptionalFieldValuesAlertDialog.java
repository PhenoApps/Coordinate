package org.wheatgenetics.coordinate.gc.vs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.androidlibrary.Utils;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField;
import org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

import java.util.ArrayList;

class SetOptionalFieldValuesAlertDialog extends AlertDialog {
    // region Fields
    @NonNull
    private final Handler handler;

    private NonNullOptionalFields optionalFields;
    private CheckedOptionalFields checkedOptionalFields;
    private boolean firstCannotBeEmpty;
    @Nullable
    private ArrayList<EditText>
            editTextArrayList = null;
    // endregion

    SetOptionalFieldValuesAlertDialog(final Activity activity,
                                      @NonNull final Handler handler) {
        super(activity);
        this.handler = handler;
    }

    private void setValues() {
        if (null != this.checkedOptionalFields && null != this.editTextArrayList) {
            if (this.checkedOptionalFields.size() != this.editTextArrayList.size())
                throw new AssertionError();

            boolean firstWasEmptyWhenItWasNotSupposedToBe = false;
            {
                final int first = 0;
                int i = first;
                for (final BaseOptionalField
                        baseOptionalField : this.checkedOptionalFields) {
                    final String value;
                    {
                        final EditText editText = this.editTextArrayList.get(i);
                        value = null == editText ? "" :
                                Utils.getText(editText);
                    }

                    if (this.firstCannotBeEmpty && first == i) {
                        final int emptyLength = 0;
                        if (emptyLength == value.length()) {
                            {
                                final String optionalFieldHint =
                                        baseOptionalField.getHint();
                                this.showToast(optionalFieldHint.length() > emptyLength ?
                                        optionalFieldHint : baseOptionalField.getName() +
                                        this.getString(R.string.SetOptionalFieldValuesAlertDialogToast));
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

            if (!firstWasEmptyWhenItWasNotSupposedToBe) {
                this.cancelAlertDialog();
                this.handler.handleSetValuesDone(this.optionalFields);
            }
        }
    }

    @Override
    public void configure() {
        this.setCancelableToFalse()
                .setPositiveButton(R.string.SetOptionalFieldValuesAlertDialogPositiveButtonText)
                .setCancelNegativeButton();
    }

    void show(final String title, @NonNull final NonNullOptionalFields optionalFields,
              final boolean firstCannotBeEmpty) {
        this.setTitle(title);

        this.optionalFields = optionalFields;
        this.checkedOptionalFields = new
                CheckedOptionalFields(this.optionalFields);
        this.firstCannotBeEmpty = firstCannotBeEmpty;

        if (null == this.editTextArrayList)
            // noinspection Convert2Diamond
            this.editTextArrayList = new ArrayList<EditText>();
        else
            this.editTextArrayList.clear();
        {
            final View view =
                    this.inflate(R.layout.set_optional_field_values);
            if (null != view) {
                final LinearLayout linearLayout = view.findViewById(
                        R.id.setOptionalFieldValuesLinearLayout);
                final LayoutInflater layoutInflater = this.layoutInflater();
                if (null != layoutInflater && null != linearLayout)
                    for (final BaseOptionalField
                            baseOptionalField : this.checkedOptionalFields) {
                        final View optionalFieldView = layoutInflater.inflate(
                                R.layout.optional_field_value,
                                linearLayout, false);
                        if (null != optionalFieldView) {
                            {
                                final TextView optionalFieldValueTextView =
                                        optionalFieldView.findViewById(R.id.optionalFieldValueTextView);
                                if (null != optionalFieldValueTextView)
                                    optionalFieldValueTextView.setText(baseOptionalField.getName());
                            }
                            {
                                final EditText optionalFieldValueEditText =
                                        optionalFieldView.findViewById(R.id.optionalFieldValueEditText);
                                if (null != optionalFieldValueEditText) {
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
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        SetOptionalFieldValuesAlertDialog.this.setValues();
                    }
                });
    }
}