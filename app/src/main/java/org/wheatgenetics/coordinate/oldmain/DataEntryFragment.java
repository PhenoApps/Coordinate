package org.wheatgenetics.coordinate.oldmain;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.EditText
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.fragment.app.Fragment
 *
 * org.wheatgenetics.androidlibrary.ClearingEditorActionListener
 * org.wheatgenetics.androidlibrary.ClearingEditorActionListener.Receiver
 *
 * org.wheatgenetics.coordinate.BuildConfig
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
public class DataEntryFragment extends androidx.fragment.app.Fragment
implements org.wheatgenetics.androidlibrary.ClearingEditorActionListener.Receiver
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract java.lang.String getEntryValue   ();
        public abstract java.lang.String getProjectTitle ();
        public abstract java.lang.String getTemplateTitle();
        public abstract org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            getOptionalFields();

        public abstract void saveEntry (java.lang.String entryValue);
        public abstract void clearEntry();
    }

    // region Fields
    private org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler handler;

    private android.widget.EditText     entryEditText                              ;
    private android.widget.TextView     projectTitleTextView, templateTitleTextView;
    private android.widget.LinearLayout optionalFieldsLayout                       ;
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override public void onAttach(
    @androidx.annotation.NonNull final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler)
            this.handler = (org.wheatgenetics.coordinate.oldmain.DataEntryFragment.Handler) context;
        else
            throw new java.lang.RuntimeException(context.toString() + " must implement Handler");
    }

    @java.lang.Override public android.view.View onCreateView(
    @androidx.annotation.NonNull  final android.view.LayoutInflater inflater          ,
    @androidx.annotation.Nullable final android.view.ViewGroup      container         ,
    @androidx.annotation.Nullable final android.os.Bundle           savedInstanceState)
    {
        // Inflate the layout for this fragment:
        return inflater.inflate(org.wheatgenetics.coordinate.R.layout.fragment_data_entry,
            container,false);
    }

    @java.lang.Override public void onActivityCreated(
    @androidx.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final android.app.Activity activity = this.getActivity();
        if (null != activity)
        {
            this.entryEditText = activity.findViewById(
                org.wheatgenetics.coordinate.R.id.entryEditText);
            if (null != this.entryEditText) this.entryEditText.setOnEditorActionListener(
                new org.wheatgenetics.androidlibrary.ClearingEditorActionListener(
                    this.entryEditText,this,
                    org.wheatgenetics.coordinate.BuildConfig.DEBUG));

            this.projectTitleTextView = activity.findViewById(
                org.wheatgenetics.coordinate.R.id.projectTitleTextView);

            this.templateTitleTextView = activity.findViewById(
                org.wheatgenetics.coordinate.R.id.templateTitleTextView);

            this.optionalFieldsLayout = activity.findViewById(
                org.wheatgenetics.coordinate.R.id.optionalFieldsLayout);
        }

        this.populate();
    }

    @java.lang.Override public void onDetach() { this.handler = null; super.onDetach(); }

    // region org.wheatgenetics.androidlibrary.ClearingEditorActionListener.Receiver Overridden Methods
    @java.lang.Override public void receiveText(final java.lang.String text)
    { if (null != this.handler) this.handler.saveEntry(text); }

    @java.lang.Override public void clearText()
    { if (null != this.handler) this.handler.clearEntry(); }
    // endregion
    // endregion

    // region Package Methods
    void populate()
    {
        if (null != this.handler)
        {
            this.setEntry(this.handler.getEntryValue());

            if (null != this.projectTitleTextView)
                this.projectTitleTextView.setText(this.handler.getProjectTitle());

            if (null != this.templateTitleTextView)
                this.templateTitleTextView.setText(this.handler.getTemplateTitle());


            if (null != this.optionalFieldsLayout)
            {
                this.optionalFieldsLayout.removeAllViews();
                final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
                    nonNullOptionalFields = this.handler.getOptionalFields();
                if (null != nonNullOptionalFields) if (!nonNullOptionalFields.isEmpty())
                {
                    final org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields
                        checkedOptionalFields =
                            new org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields(
                                nonNullOptionalFields);

                    final android.app.Activity        activity       = this.getActivity();
                    final android.view.LayoutInflater layoutInflater =
                        null == activity ? null : activity.getLayoutInflater();

                    if (null != layoutInflater)
                        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                        baseOptionalField: checkedOptionalFields)
                        {
                            final android.view.View view = layoutInflater.inflate(
                                org.wheatgenetics.coordinate.R.layout.optional_field_show,
                                new android.widget.LinearLayout(activity),false);
                            if (null != view)
                            {
                                {
                                    final android.widget.TextView nameTextView = view.findViewById(
                                        org.wheatgenetics.coordinate.R.id.nameTextView);
                                    if (null != nameTextView)
                                        nameTextView.setText(baseOptionalField.getName());
                                }
                                {
                                    final android.widget.TextView valueTextView = view.findViewById(
                                        org.wheatgenetics.coordinate.R.id.valueTextView);
                                    if (null != valueTextView)
                                        valueTextView.setText(baseOptionalField.getValue());
                                }
                            }
                            this.optionalFieldsLayout.addView(view);
                        }
                }
            }
        }
    }

    void setEntry(final java.lang.String entry)
    { if (null != this.entryEditText) this.entryEditText.setText(entry); }
    // endregion
}