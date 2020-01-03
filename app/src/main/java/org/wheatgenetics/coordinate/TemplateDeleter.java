package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateDeleter extends java.lang.Object
implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface GridHandler
    { public abstract void respondToDeletedGrid(); }

    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface TemplateHandler
    {
        public abstract void respondToDeletedTemplate(
        @androidx.annotation.IntRange(from = 1) long templateId);
    }
    // endregion

    // region Fields
    @androidx.annotation.NonNull  private final android.content.Context context;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.TemplateDeleter.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.TemplateDeleter.TemplateHandler templateHandler;

    @androidx.annotation.IntRange(from = 1) private long templateId;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null;// ll
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null;// ll
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    // endregion
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.context);
        return this.gridsTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.context);
        return this.entriesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.context);
        return this.templatesTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.context, text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.context.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.context, text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.context.getString(text)); }
    // endregion
    // endregion

    private void deleteTemplateStep3()
    {
        final boolean success = this.templatesTable().delete(this.templateId);
        {
            @androidx.annotation.StringRes final int text = success ?
                org.wheatgenetics.coordinate.R.string.TemplateDeleterSuccessToast :
                org.wheatgenetics.coordinate.R.string.TemplateDeleterFailToast    ;
            this.showLongToast(text);
        }
        if (success && null != this.templateHandler)
            this.templateHandler.respondToDeletedTemplate(this.templateId);
    }

    private void deleteTemplateStep2()
    {
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.gridsTable().loadByTemplateId(this.templateId);
            if (null != baseJoinedGridModels)
                baseJoinedGridModels.processAll(this);                   // delete entries
        }
        {
            final boolean success = this.gridsTable().deleteByTemplateId(this.templateId);
            @androidx.annotation.StringRes final int text = success ?              // delete grids
                    org.wheatgenetics.coordinate.R.string.DeleterGridsSuccessToast :
                    org.wheatgenetics.coordinate.R.string.DeleterGridsFailToast    ;
            this.showShortToast(text);
            if (success && null != this.gridHandler) this.gridHandler.respondToDeletedGrid();
        }
        this.deleteTemplateStep3();
    }
    // endregion

    // region Constructors
    private TemplateDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.TemplateDeleter.GridHandler
        gridHandler,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.TemplateDeleter.TemplateHandler
        templateHandler)
    {
        super();

        this.context         = context        ;
        this.gridHandler     = gridHandler    ;
        this.templateHandler = templateHandler;
    }

    public TemplateDeleter(@androidx.annotation.NonNull  final android.content.Context context,
                           @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.TemplateDeleter.GridHandler gridHandler)
    { this(context, gridHandler,null); }

    public TemplateDeleter(@androidx.annotation.NonNull  final android.content.Context context,
                           @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.TemplateDeleter.TemplateHandler templateHandler)
    { this(context,null, templateHandler); }
    // endregion

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    { if (null != joinedGridModel) this.entriesTable().deleteByGridId(joinedGridModel.getId()); }
    // endregion

    // region Public Methods
    public void delete(@androidx.annotation.IntRange(from = 1) final long templateId)
    {
        this.templateId = templateId;
        if (this.gridsTable().existsInTemplate(this.templateId))
            org.wheatgenetics.coordinate.Utils.confirm(
                /* context => */ this.context,
                /* title   => */
                    org.wheatgenetics.coordinate.R.string.TemplateDeleterConfirmationTitle,
                /* message => */
                    org.wheatgenetics.coordinate.R.string.TemplateDeleterConfirmationMessage,
                /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override public void run()
                        { org.wheatgenetics.coordinate.TemplateDeleter.this.deleteTemplateStep2(); }
                    });
        else this.deleteTemplateStep3();
    }

    public void delete(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    { if (null != templateModel) this.delete(templateModel.getId()); }
    // endregion
}