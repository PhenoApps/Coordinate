package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.EditorActionListener
 * org.wheatgenetics.androidlibrary.EditorActionListener.Receiver
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.BuildConfig
 * org.wheatgenetics.coordinate.R
 */
public class DataEntryFragment extends android.support.v4.app.Fragment
implements org.wheatgenetics.androidlibrary.EditorActionListener.Receiver
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    {
        public abstract java.lang.String getTemplateTitle()              ;
        public abstract void             addEntry(java.lang.String entry);
    }

    // region Fields
    private org.wheatgenetics.coordinate.DataEntryFragment.Handler handler;

    private android.widget.EditText     entryEditText        ;
    private android.widget.TextView     templateTitleTextView;
    private android.widget.LinearLayout optionalFieldsLayout ;
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.coordinate.DataEntryFragment.Handler)
            this.handler = (org.wheatgenetics.coordinate.DataEntryFragment.Handler) context;
        else
        {
            assert null != context; throw new java.lang.RuntimeException(context.toString() +
                " must implement Handler");
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.coordinate.R.layout.fragment_data_entry, container, false);
    }

    @java.lang.Override
    public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        {
            final android.app.Activity activity = this.getActivity();
            assert null != activity; this.entryEditText = (android.widget.EditText)
                activity.findViewById(org.wheatgenetics.coordinate.R.id.entryEditText);

            this.templateTitleTextView = (android.widget.TextView)
                activity.findViewById(org.wheatgenetics.coordinate.R.id.templateTitleTextView);

            this.optionalFieldsLayout = (android.widget.LinearLayout)
                activity.findViewById(org.wheatgenetics.coordinate.R.id.optionalFieldsLayout);
        }

        assert null != this.entryEditText; this.entryEditText.setOnEditorActionListener(
            new org.wheatgenetics.androidlibrary.EditorActionListener(
                this.entryEditText, this, org.wheatgenetics.coordinate.BuildConfig.DEBUG));
        this.entryEditText.setSelectAllOnFocus(true);

        assert null != this.handler; assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(this.handler.getTemplateTitle());
    }

    // region org.wheatgenetics.androidlibrary.EditorActionListener.ReceiverOverridden Method
    @Override
    public void receiveText(final java.lang.String text)
    { assert null != this.handler; this.handler.addEntry(text); }
    // endregion
    // endregion

    // region Package Methods
    void setEntry(final java.lang.String entry)
    { assert null != this.entryEditText; this.entryEditText.setText(entry); }

    void clearEntry() { this.setEntry(""); }

    void setTemplateTitle(final java.lang.String templateTitle)
    {
        assert null != this.templateTitleTextView;
        this.templateTitleTextView.setText(templateTitle);
    }

    void clearTemplateTitle() { this.setTemplateTitle(""); }

    void setOptionalFields(
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        if (null != nonNullOptionalFields) if (!nonNullOptionalFields.isEmpty())
        {
            final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
                checkedOptionalFields =
                    new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
                        nonNullOptionalFields);

            final android.app.Activity activity = this.getActivity();

            assert null != activity;
            final android.view.LayoutInflater layoutInflater = activity.getLayoutInflater();

            assert null != this.optionalFieldsLayout; this.optionalFieldsLayout.removeAllViews();

            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: checkedOptionalFields)
            {
                final android.view.View view = layoutInflater.inflate(
                    org.wheatgenetics.coordinate.R.layout.optional_field_show,
                    new android.widget.LinearLayout(activity), false);
                {
                    assert null != view;
                    final android.widget.TextView nameTextView = (android.widget.TextView)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.nameTextView);
                    assert null != nameTextView; nameTextView.setText(baseOptionalField.getName());
                }
                {
                    final android.widget.TextView valueTextView = (android.widget.TextView)
                        view.findViewById(org.wheatgenetics.coordinate.R.id.valueTextView);
                    assert null != valueTextView;
                    valueTextView.setText(baseOptionalField.getValue());
                }
                this.optionalFieldsLayout.addView(view);
            }
        }
    }
    // endregion
}