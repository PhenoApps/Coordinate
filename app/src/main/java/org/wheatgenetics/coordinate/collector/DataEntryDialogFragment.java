package org.wheatgenetics.coordinate.collector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField;
import org.wheatgenetics.coordinate.optionalField.CheckedOptionalFields;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

public class DataEntryDialogFragment extends DialogFragment {
    // region Fields
    private DataEntryDialogFragment.Handler handler;
    private LinearLayout fieldsContainer;
    private Context themedContext;

    public DataEntryDialogFragment() { /* Required empty public constructor. */ }
    // endregion

    // region Overridden Methods
    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        if (context instanceof DataEntryDialogFragment.Handler)
            this.handler = (DataEntryDialogFragment.Handler) context;
        else
            throw new RuntimeException(context.toString() + " must implement Handler");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Activity activity = requireActivity();
        this.themedContext = new ContextThemeWrapper(activity, R.style.MaterialAppTheme);
        final LayoutInflater inflater = LayoutInflater.from(this.themedContext);
        final View view = inflater.inflate(R.layout.fragment_data_entry, null, false);

        this.fieldsContainer = view.findViewById(R.id.fieldsContainer);

        populate();

        return new MaterialAlertDialogBuilder(themedContext)
                .setTitle(R.string.fragment_data_entryGridInformationHeaderText)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    @Override
    public void onDetach() {
        this.handler = null;
        super.onDetach();
    }

    // region Package Methods
    void populate() {
        if (null == this.handler || null == this.fieldsContainer) return;

        final Context ctx = themedContext != null ? themedContext : requireContext();
        final LayoutInflater inflater = LayoutInflater.from(ctx);

        this.fieldsContainer.removeAllViews();

        addInfoRow(inflater, ctx,
                ctx.getString(R.string.fragment_data_entryProjectLabelText),
                this.handler.getProjectTitle());

        addInfoRow(inflater, ctx,
                ctx.getString(R.string.fragment_data_entryTemplateLabelText),
                this.handler.getTemplateTitle());

        final NonNullOptionalFields optionalFields = this.handler.getOptionalFields();
        if (null != optionalFields && !optionalFields.isEmpty()) {
            final CheckedOptionalFields checked = new CheckedOptionalFields(optionalFields);
            for (final BaseOptionalField field : checked) {
                addInfoRow(inflater, ctx, field.getName(), field.getValue());
            }
        }
    }

    /** Inflates a label/value row and adds it to fieldsContainer. Skips rows with blank values. */
    private void addInfoRow(
            @NonNull final LayoutInflater inflater,
            @NonNull final Context ctx,
            @Nullable final String label,
            @Nullable final String value) {
        if (value == null || value.trim().isEmpty()) return;
        final View row = inflater.inflate(
                R.layout.optional_field_show, new LinearLayout(ctx), false);
        if (null == row) return;
        final TextView nameView = row.findViewById(R.id.nameTextView);
        if (nameView != null) nameView.setText(label);
        final TextView valueView = row.findViewById(R.id.valueTextView);
        if (valueView != null) valueView.setText(value);
        this.fieldsContainer.addView(row);
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract String getEntryValue();

        public abstract String getProjectTitle();

        public abstract String getTemplateTitle();

        public abstract NonNullOptionalFields getOptionalFields();

        public abstract void saveEntry(String entryValue);

        public abstract void clearEntry();
    }
}