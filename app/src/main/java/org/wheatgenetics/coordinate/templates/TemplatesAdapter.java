package org.wheatgenetics.coordinate.templates;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.view.View
 * android.view.ViewGroup
 * android.widget.BaseAdapter
 * android.widget.ListAdapter
 * android.widget.TableLayout
 * android.widget.TextView
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
class TemplatesAdapter extends android.widget.BaseAdapter implements android.widget.ListAdapter
{
    // region Fields
    private final android.app.Activity activity;

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    private org.wheatgenetics.coordinate.model.TemplateModels    templateModelsInstance = null;// ll
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    @androidx.annotation.Nullable
    private org.wheatgenetics.coordinate.model.TemplateModels templateModels()
    {
        if (null == this.templateModelsInstance)
            this.templateModelsInstance = this.templatesTable().load();
        return this.templateModelsInstance;
    }
    // endregion

    TemplatesAdapter(@androidx.annotation.NonNull final android.app.Activity activity)
    { super(); this.activity = activity; }

    // region Overridden Methods
    @java.lang.Override public void notifyDataSetChanged()
    { this.templateModelsInstance = null; super.notifyDataSetChanged(); }

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
        final org.wheatgenetics.coordinate.model.TemplateModels templateModels =
            this.templateModels();
        if (null == templateModels)
            return -1;
        else
        {
            final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                templateModels.get(position);
            return null == templateModel ? -1 :templateModel.getId();
        }
    }

    @java.lang.Override @androidx.annotation.NonNull public android.view.View getView(
                                  final int                    position   , 
    @androidx.annotation.Nullable final android.view.View      convertView, 
    @androidx.annotation.NonNull  final android.view.ViewGroup parent     )
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            (org.wheatgenetics.coordinate.model.TemplateModel) this.getItem(position);
        if (null == templateModel)
            return new android.widget.TableLayout(this.activity);
        else
        {
            @android.annotation.SuppressLint({"InflateParams"}) final android.view.View view =
                this.activity.getLayoutInflater().inflate(
                    org.wheatgenetics.coordinate.R.layout.templates_list_item,
                    null,false);
            if (null == view)
                return new android.widget.TableLayout(this.activity);
            else
            {
                {
                    final android.widget.TextView titleTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemTitle);
                    if (null != titleTextView) titleTextView.setText(templateModel.getTitle());
                }
                {
                    final android.widget.TextView rowsTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemRows);
                    if (null != rowsTextView) rowsTextView.setText(templateModel.getRowsAsString());
                }
                {
                    final android.widget.TextView colsTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemColumns);
                    if (null != colsTextView) colsTextView.setText(templateModel.getColsAsString());
                }
                {
                    final android.widget.TextView timestampTextView = view.findViewById(
                        org.wheatgenetics.coordinate.R.id.templatesListItemTimestamp);
                    if (null != timestampTextView)
                        timestampTextView.setText(templateModel.getTimestampAsCharSequence());
                }
                return view;
            }
        }
    }
    // endregion
}