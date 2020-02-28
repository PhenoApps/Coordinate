package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.ViewGroup
 * android.widget.ImageButton
 * android.widget.TextView
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.adapter.NonGridsAdapter
 *
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
class TemplatesAdapter extends org.wheatgenetics.coordinate.adapter.NonGridsAdapter
{
    // region Fields
    // region Table Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    // endregion

    private org.wheatgenetics.coordinate.model.TemplateModels templateModelsInstance = null;   // ll
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.activity());
        return this.gridsTableInstance;
    }
    // endregion

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.TemplateModels templateModels()
    {
        if (null == this.templateModelsInstance)
            this.templateModelsInstance = this.templatesTable().load();
        return this.templateModelsInstance;
    }
    // endregion

    TemplatesAdapter(
    @androidx.annotation.NonNull final android.app.Activity              activity,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onCreateGridButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onShowGridsButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onExportButtonClickListener,
    @androidx.annotation.NonNull final android.view.View.OnClickListener
        onDeleteButtonClickListener)
    {
        super(activity, onCreateGridButtonClickListener, onDeleteButtonClickListener,
            onExportButtonClickListener, onShowGridsButtonClickListener);
    }

    // region Overridden Methods
    @java.lang.Override public int getCount()
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templateModels();
        return null == templateModels ? 0 : templateModels.size();
    }

    @java.lang.Override public java.lang.Object getItem(final int position)
    {
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templateModels();
        return null == templateModels ? null : templateModels.get(position);
    }

    @java.lang.Override public long getItemId(final int position)
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) this.getItem(position);
        return null == templateModel ? -1 :templateModel.getId();
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   , 
    @androidx.annotation.Nullable final android.view.View      convertView, 
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) this.getItem(position);
        if (null == templateModel)
            return this.makeEmptyTableLayout();
        else
        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.activity().getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.templates_list_item,
                    null,false);
            if (null == view)
                return this.makeEmptyTableLayout();
            else
            {
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemTitle);
                    if (null != textView) textView.setText(templateModel.getTitle());
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemRows);
                    if (null != textView)
                    {
                        @androidx.annotation.IntRange(from = 1) final int rows =
                            templateModel.getRows();
                        textView.setText(this.resources().getQuantityString(
                            org.wheatgenetics.coordinate.R.plurals.TemplatesListRows, rows, rows));
                    }
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemColumns);
                    if (null != textView)
                    {
                        @androidx.annotation.IntRange(from = 1) final int cols =
                            templateModel.getCols();
                        textView.setText(this.resources().getQuantityString(
                            org.wheatgenetics.coordinate.R.plurals.TemplatesListColumns,
                            cols, cols                                                 ));
                    }
                }
                {
                    final android.widget.TextView textView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemTimestamp);
                    if (null != textView) textView.setText(templateModel.getFormattedTimestamp());
                }

                @androidx.annotation.IntRange(from = 1) final long templateId =
                    templateModel.getId();
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemCreateGridButton);
                    if (null != imageButton)
                    {
                        imageButton.setTag            (templateId                            );
                        imageButton.setOnClickListener(this.onCreateGridButtonClickListener());
                    }
                }
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemShowGridsButton);
                    if (null != imageButton)
                        if (this.gridsTable().existsInTemplate(templateId))
                        {
                            imageButton.setTag            (templateId                           );
                            imageButton.setOnClickListener(this.onShowGridsButtonClickListener());
                        }
                        else imageButton.setEnabled(false);
                }

                final boolean isUserDefined = !templateModel.isDefaultTemplate();
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemExportButton);
                    if (null != imageButton)
                        if (isUserDefined)
                        {
                            imageButton.setTag            (templateId                        );
                            imageButton.setOnClickListener(this.onExportButtonClickListener());
                        }
                        else imageButton.setEnabled(false);
                }
                {
                    final android.widget.ImageButton imageButton = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemDeleteButton);
                    if (null != imageButton)
                        if (isUserDefined)
                        {
                            imageButton.setTag            (templateId                        );
                            imageButton.setOnClickListener(this.onDeleteButtonClickListener());
                        }
                        else imageButton.setEnabled(false);
                }

                return view;
            }
        }
    }

    @java.lang.Override public void notifyDataSetChanged()
    { this.templateModelsInstance = null; super.notifyDataSetChanged(); }
    // endregion
}