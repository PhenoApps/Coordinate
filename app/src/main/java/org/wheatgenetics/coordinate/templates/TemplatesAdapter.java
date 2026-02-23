package org.wheatgenetics.coordinate.templates;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.PreferenceManager;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.adapter.NonGridsAdapter;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.preference.GeneralKeys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

class TemplatesAdapter extends NonGridsAdapter {
    // region Fields
    // region Table Fields
    private TemplatesTable templatesTableInstance = null;// ll
    private GridsTable gridsTableInstance = null;// ll
    // endregion

    private TemplateModels templateModelsInstance = null;   // ll
    // endregion

    // region Sort fields
    static final int SORT_DEFAULT = 0;
    static final int SORT_NAME = 1;
    static final int SORT_DATE = 2;
    private int sortOrder = SORT_DEFAULT;
    // endregion

    private View.OnClickListener editTemplateButtonListener = null;

    TemplatesAdapter(
            @NonNull final Activity activity,
            @NonNull final View.OnClickListener
                    onCreateGridButtonClickListener,
            @NonNull final View.OnClickListener
                    onDeleteButtonClickListener,
            @NonNull final View.OnClickListener
                    onExportButtonClickListener,
            @NonNull final View.OnClickListener
                    onShowGridsButtonClickListener,
            @NonNull final View.OnClickListener
                    onEditButtonClickListener) {
        super(activity, onCreateGridButtonClickListener, onDeleteButtonClickListener,
                onExportButtonClickListener, onShowGridsButtonClickListener);
        this.editTemplateButtonListener = onEditButtonClickListener;
    }

    // region Private Methods
    // region Table Private Methods
    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }
    // endregion

    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity());
        return this.gridsTableInstance;
    }
    // endregion

    @Nullable
    private Set<String> hiddenBuiltinTypes() {
        final SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this.activity());
        final String raw = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "");
        if (raw == null || raw.isEmpty()) return new HashSet<>();
        return new HashSet<>(Arrays.asList(raw.split(",")));
    }

    @Nullable
    private TemplateModels templateModels() {
        if (null == this.templateModelsInstance) {
            final TemplateModels all = this.templatesTable().load();
            if (all != null) {
                final Set<String> hidden = hiddenBuiltinTypes();
                this.templateModelsInstance = new TemplateModels();
                for (final TemplateModel tm : all) {
                    final String typeCode = String.valueOf(tm.getType().getCode());
                    if (!tm.isDefaultTemplate() || !hidden.contains(typeCode)) {
                        this.templateModelsInstance.add(tm);
                    }
                }
            }
            if (null != this.templateModelsInstance && sortOrder != SORT_DEFAULT) {
                if (sortOrder == SORT_NAME) {
                    this.templateModelsInstance.sort(new Comparator<TemplateModel>() {
                        @Override
                        public int compare(final TemplateModel a, final TemplateModel b) {
                            final String titleA = a.getTitle() != null ? a.getTitle() : "";
                            final String titleB = b.getTitle() != null ? b.getTitle() : "";
                            return titleA.compareToIgnoreCase(titleB);
                        }
                    });
                } else if (sortOrder == SORT_DATE) {
                    this.templateModelsInstance.sort(new Comparator<TemplateModel>() {
                        @Override
                        public int compare(final TemplateModel a, final TemplateModel b) {
                            return Long.compare(b.getTimestamp(), a.getTimestamp());
                        }
                    });
                }
            }
        }
        return this.templateModelsInstance;
    }

    void setSortOrder(final int sortOrder) {
        this.sortOrder = sortOrder;
        notifyDataSetChanged();
    }

    int getSortOrder() {
        return sortOrder;
    }

    // region Overridden Methods
    @Override
    public int getCount() {
        final TemplateModels templateModels =
                this.templateModels();
        return null == templateModels ? 0 : templateModels.size();
    }

    @Override
    public Object getItem(final int position) {
        final TemplateModels templateModels =
                this.templateModels();
        return null == templateModels ? null : templateModels.get(position);
    }

    @Override
    public long getItemId(final int position) {
        final TemplateModel templateModel =
                (TemplateModel) this.getItem(position);
        return null == templateModel ? -1 : templateModel.getId();
    }

    @Override
    @NonNull
    public View getView(
            final int position,
            @Nullable final View convertView,
            @NonNull final ViewGroup parent) {
        final TemplateModel templateModel =
                (TemplateModel) this.getItem(position);
        if (null == templateModel)
            return this.makeEmptyTableLayout();
        else {
            @SuppressLint({"InflateParams"}) final View view =
                    this.activity().getLayoutInflater().inflate(
                            R.layout.templates_list_item,
                            null, false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else {
                {
                    final TextView textView = view.findViewById(
                            R.id.templatesListItemTitle);
                    if (null != textView) {
                        textView.setText(templateModel.getTitle());

                        //added click to view entire template title
                        textView.setOnClickListener((v) -> {
                            Toast.makeText(this.activity(), templateModel.getTitle(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.templatesListItemRows);
                    if (null != textView) {
                        @IntRange(from = 1) final int rows =
                                templateModel.getRows();
                        textView.setText(this.resources().getQuantityString(
                                R.plurals.TemplatesListRows, rows, rows));
                    }
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.templatesListItemColumns);
                    if (null != textView) {
                        @IntRange(from = 1) final int cols =
                                templateModel.getCols();
                        textView.setText(this.resources().getQuantityString(
                                R.plurals.TemplatesListColumns,
                                cols, cols));
                    }
                }
                {
                    final TextView textView = view.findViewById(
                            R.id.templatesListItemTimestamp);
                    if (null != textView) textView.setText(templateModel.getFormattedTimestamp());
                }

                @IntRange(from = 1) final long templateId =
                        templateModel.getId();
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.templatesListItemShowGridsButton);
                    if (null != imageButton)
                        if (this.gridsTable().existsInTemplate(templateId)) {
                            imageButton.setTag(templateId);
                            imageButton.setOnClickListener(this.onShowGridsButtonClickListener());
                        } else imageButton.setEnabled(false);
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.templatesListItemCreateGridButton);
                    if (null != imageButton) {
                        imageButton.setTag(templateId);
                        imageButton.setOnClickListener(this.onCreateGridButtonClickListener());
                    }
                }

                final boolean isUserDefined = !templateModel.isDefaultTemplate();
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.templatesListItemDeleteButton);
                    if (null != imageButton) {
                        imageButton.setTag(templateId);
                        imageButton.setOnClickListener(this.onDeleteButtonClickListener());
                    }
                }
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.templatesListItemExportButton);
                    if (null != imageButton)
                        if (isUserDefined) {
                            imageButton.setTag(templateId);
                            imageButton.setOnClickListener(this.onExportButtonClickListener());
                        } else imageButton.setEnabled(false);
                }
                //issue 27 template editor
                //disable template editing if a grid is already created
                boolean hasGrid = gridsTableInstance.existsInTemplate(templateId);
                {
                    final ImageButton imageButton = view.findViewById(
                            R.id.templatesListItemEditButton);
                    if (null != imageButton)
                        if (isUserDefined && !hasGrid) {
                            imageButton.setTag(templateId);
                            imageButton.setOnClickListener(this.editTemplateButtonListener);
                        } else {
                            imageButton.setImageDrawable(AppCompatResources
                                    .getDrawable(activity(), R.drawable.ic_pencil_gray));
                            imageButton.setOnClickListener((v) -> {
                                Toast.makeText(activity(),
                                        R.string.templates_with_grids_cant_be_edited,
                                        Toast.LENGTH_LONG).show();
                            });
                        }
                }

                return view;
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        this.templateModelsInstance = null;
        super.notifyDataSetChanged();
    }
    // endregion
}