package org.wheatgenetics.coordinate.ui;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.LayoutInflater
 * android.view.View
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 *
 * org.wheatgenetics.coordinate.ui.AlertDialog
 */
class LoadTemplateAlertDialog extends org.wheatgenetics.coordinate.ui.AlertDialog
{
    interface Handler
    {
        public abstract void processError (java.lang.String message);
        public abstract void processPerson(java.lang.String person );
        public abstract void createGrid   ()                        ;
    }

    // region Fields
    private final org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler handler;

    private java.util.ArrayList<android.widget.EditText>                  editTextArrayList = null;
    private org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields checkedOptionalFields;
    private boolean                                                          firstCannotBeEmpty   ;
    // endregion

    private void process()
    {
        assert null != this.checkedOptionalFields; assert null != this.editTextArrayList;
        assert this.checkedOptionalFields.size() == this.editTextArrayList.size();

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
                    if (0 == value.length()) this.handler.processError(optionalField.getHint() +
                        this.getString(org.wheatgenetics.coordinate.R.string.not_empty));

                optionalField.setValue(value);

                if (optionalField.namesAreEqual("Person") || optionalField.namesAreEqual("Name"))  // TODO: Should be done in OptionalField.
                    this.handler.processPerson(optionalField.getValue());

                i++;
            }
        }

        this.handler.createGrid();
    }

    LoadTemplateAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    void configure()
    {
        this.setCancelableToFalse().setPositiveButton(org.wheatgenetics.coordinate.R.string.create,
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                { org.wheatgenetics.coordinate.ui.LoadTemplateAlertDialog.this.process(); }
            }).setNegativeButton();
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
            this.setView(view);
        }

        this.checkedOptionalFields = checkedOptionalFields;
        this.firstCannotBeEmpty    = firstCannotBeEmpty   ;
        this.createModifiyShow();
    }
}