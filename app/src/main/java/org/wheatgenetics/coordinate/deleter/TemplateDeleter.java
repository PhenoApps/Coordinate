package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.TemplateModel
 *
 * org.wheatgenetics.coordinate.deleter.CascadingDeleter
 */
public class TemplateDeleter extends org.wheatgenetics.coordinate.deleter.CascadingDeleter
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
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.GridHandler gridHandler;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.TemplateHandler templateHandler;

    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.context());
        return this.templatesTableInstance;
    }

    // region Constructors
    private TemplateDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.GridHandler gridHandler,
    @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.TemplateHandler templateHandler)
    {
        super(context,
            org.wheatgenetics.coordinate.R.string.TemplateDeleterConfirmationTitle  ,
            org.wheatgenetics.coordinate.R.string.TemplateDeleterConfirmationMessage,
            org.wheatgenetics.coordinate.R.string.TemplateDeleterSuccessToast       ,
            org.wheatgenetics.coordinate.R.string.TemplateDeleterFailToast          );
        this.gridHandler = gridHandler; this.templateHandler = templateHandler;
    }

    public TemplateDeleter(@androidx.annotation.NonNull  final android.content.Context context,
                           @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.GridHandler gridHandler)
    { this(context, gridHandler,null); }

    public TemplateDeleter(@androidx.annotation.NonNull  final android.content.Context context,
                           @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.deleter.TemplateDeleter.TemplateHandler templateHandler)
    { this(context,null, templateHandler); }
    // endregion

    // region Overridden Methods
    // region deleteStep3() Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean deleteMasterRecord()
    { return this.templatesTable().delete(this.id()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean deleteStep3()
    {
        final boolean success = super.deleteStep3();
        if (success && null != this.templateHandler)
            this.templateHandler.respondToDeletedTemplate(this.id());
        return success;
    }
    // endregion

    // region deleteStep2() Overridden Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.model.BaseJoinedGridModels loadDetailRecords()
    { return this.gridsTable().loadByTemplateId(this.id()); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean deleteStep2()
    {
        final boolean atLeastOneGridWasDeleted = super.deleteStep2();
        if (atLeastOneGridWasDeleted && null != this.gridHandler)
            this.gridHandler.respondToDeletedGrid();
        return atLeastOneGridWasDeleted;
    }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override boolean detailRecordsExists()
    { return this.gridsTable().existsInTemplate(this.id()); }
    // endregion

    public void delete(@androidx.annotation.Nullable final
    org.wheatgenetics.coordinate.model.TemplateModel templateModel) { super.delete(templateModel); }
}