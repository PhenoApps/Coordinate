package org.wheatgenetics.coordinate.collector;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import org.phenoapps.androidlibrary.ClearingEditorActionListener;
import org.wheatgenetics.coordinate.BuildConfig;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField;
import org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

public class DataEntryDialogFragment extends DialogFragment {
    // region Fields
    private DataEntryDialogFragment.Handler handler;
    private TextView projectTitleTextView, templateTitleTextView;
    private LinearLayout optionalFieldsLayout;
    public DataEntryDialogFragment() { /* Required empty public constructor. */ }
    // endregion

    private static void setText(
            @Nullable final TextView textView,
            @Nullable final String text) {
        if (null != textView) textView.setText(text);
    }

    // region Overridden Methods
    @Override
    public void onAttach(
            @NonNull final Context context) {
        super.onAttach(context);

        if (context instanceof DataEntryDialogFragment.Handler)
            this.handler =
                    (DataEntryDialogFragment.Handler) context;
        else
            throw new RuntimeException(context.toString() + " must implement Handler");
    }

    @Override
    @Nullable
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        // Inflate the layout for this fragment:
        return inflater.inflate(R.layout.fragment_data_entry,
                container, false);
    }

    @Override
    public void onActivityCreated(
            @Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = this.getActivity();
        if (null != activity) {

            View fragView = getView();
            if (fragView != null) {

                this.projectTitleTextView = fragView.findViewById(R.id.projectTitleTextView);
                this.templateTitleTextView = fragView.findViewById(R.id.templateTitleTextView);
                this.optionalFieldsLayout = fragView.findViewById(R.id.optionalFieldsLayout);
            }
        }

        this.populate();
    }

    @Override
    public void onDetach() {
        this.handler = null;
        super.onDetach();
    }

    // region Package Methods
    void populate() {
        if (null != this.handler) {

            DataEntryDialogFragment.setText(
                    this.projectTitleTextView, this.handler.getProjectTitle());
            DataEntryDialogFragment.setText(
                    this.templateTitleTextView, this.handler.getTemplateTitle());

            if (null != this.optionalFieldsLayout) {
                this.optionalFieldsLayout.removeAllViews();
                final NonNullOptionalFields
                        nonNullOptionalFields = this.handler.getOptionalFields();
                if (null != nonNullOptionalFields) if (!nonNullOptionalFields.isEmpty()) {
                    final CheckedOptionalFields
                            checkedOptionalFields =
                            new CheckedOptionalFields(
                                    nonNullOptionalFields);

                    final Activity activity = this.getActivity();
                    final LayoutInflater layoutInflater =
                            null == activity ? null : activity.getLayoutInflater();

                    if (null != layoutInflater)
                        for (final BaseOptionalField
                                baseOptionalField : checkedOptionalFields) {
                            final View view = layoutInflater.inflate(
                                    R.layout.optional_field_show,
                                    new LinearLayout(activity), false);
                            if (null != view) {
                                {
                                    final TextView nameTextView = view.findViewById(
                                            R.id.nameTextView);
                                    DataEntryDialogFragment
                                            .setText(nameTextView, baseOptionalField.getName());
                                }
                                {
                                    final TextView valueTextView = view.findViewById(
                                            R.id.valueTextView);
                                    DataEntryDialogFragment
                                            .setText(valueTextView, baseOptionalField.getValue());
                                }
                            }
                            this.optionalFieldsLayout.addView(view);
                        }
                }
            }
        }
    }
    // endregion
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract String getEntryValue();

        public abstract String getProjectTitle();

        public abstract String getTemplateTitle();

        public abstract NonNullOptionalFields
        getOptionalFields();

        public abstract void saveEntry(String entryValue);

        public abstract void clearEntry();
    }
    // endregion
}