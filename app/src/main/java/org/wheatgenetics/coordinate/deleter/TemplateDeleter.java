package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class TemplateDeleter extends CascadingDeleter {
    // region Fields
    @Nullable
    private final
    TemplateDeleter.GridHandler gridHandler;
    @Nullable
    private final
    TemplateDeleter.TemplateHandler templateHandler;
    // endregion
    private TemplatesTable templatesTableInstance = null;// ll
    // region Constructors
    private TemplateDeleter(@NonNull final Context context,
                            @Nullable final
                            TemplateDeleter.GridHandler gridHandler,
                            @Nullable final
                            TemplateDeleter.TemplateHandler templateHandler) {
        super(context,
                R.string.TemplateDeleterConfirmationTitle,
                R.string.TemplateDeleterConfirmationMessage,
                R.string.TemplateDeleterSuccessToast,
                R.string.TemplateDeleterFailToast);
        this.gridHandler = gridHandler;
        this.templateHandler = templateHandler;
    }

    public TemplateDeleter(@NonNull final Context context,
                           @Nullable final
                           TemplateDeleter.GridHandler gridHandler) {
        this(context, gridHandler, null);
    }
    // endregion

    public TemplateDeleter(@NonNull final Context context,
                           @Nullable final
                           TemplateDeleter.TemplateHandler templateHandler) {
        this(context, null, templateHandler);
    }

    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.context());
        return this.templatesTableInstance;
    }

    // region Overridden Methods
    // region deleteStep3() Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean deleteMasterRecord() {
        return this.templatesTable().delete(this.id());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean deleteStep3() {
        final boolean success = super.deleteStep3();
        if (success && null != this.templateHandler)
            this.templateHandler.respondToDeletedTemplate(this.id());
        return success;
    }
    // endregion

    // region deleteStep2() Overridden Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    @Nullable
    BaseJoinedGridModels loadDetailRecords() {
        return this.gridsTable().loadByTemplateId(this.id());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean deleteStep2() {
        final boolean atLeastOneGridWasDeleted = super.deleteStep2();
        if (atLeastOneGridWasDeleted && null != this.gridHandler)
            this.gridHandler.respondToDeletedGrid();
        return atLeastOneGridWasDeleted;
    }
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    boolean detailRecordsExists() {
        return this.gridsTable().existsInTemplate(this.id());
    }

    public void delete(@Nullable final
                       TemplateModel templateModel) {
        super.delete(templateModel);
    }
    // endregion

    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface GridHandler {
        public abstract void respondToDeletedGrid();
    }
    // endregion

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface TemplateHandler {
        public abstract void respondToDeletedTemplate(
                @IntRange(from = 1) long templateId);
    }
}