package org.wheatgenetics.coordinate.gc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.database.EntriesTable;
import org.wheatgenetics.coordinate.database.GridsTable;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter;
import org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter;
import org.wheatgenetics.coordinate.gc.vs.Handler;
import org.wheatgenetics.coordinate.gc.vs.ValueSetter;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.Cells;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.preference.Utils;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.content.res.Resources.NotFoundException
 * android.os.Bundle
 * <p>
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 * <p>
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Types.RequestCode
 * org.wheatgenetics.coordinate.Utils
 * <p>
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 * <p>
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.Model
 * <p>
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * <p>
 * org.wheatgenetics.coordinate.preference.Utils
 * <p>
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter.Handler
 * org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter
 * <p>
 * org.wheatgenetics.coordinate.gc.vs.Handler
 * org.wheatgenetics.coordinate.gc.vs.ValueSetter
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class GridCreator
        extends Object implements StringGetter {
    // region Types
    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleGridCreated(@IntRange(from = 1) long gridId);
    }

    private enum TemplateIdStatus {UNSPECIFIED, CLEARED, SET}
    // endregion

    // region Fields
    // region Constructor Fields
    private final Activity activity;
    @Types.RequestCode
    private final int requestCode;
    @Nullable
    private final
    GridCreator.Handler handler;
    // endregion

    private GridCreator.TemplateIdStatus templateIdStatus =
            GridCreator.TemplateIdStatus.UNSPECIFIED;

    @IntRange(from = 0)
    private long projectId, templateId;
    private String person;

    // region Value Setter Fields
    private TemplatesTable templatesTableInstance = null; //ll
    private GridsTable gridsTableInstance = null; //ll
    private EntriesTable entriesTableInstance = null; //ll
    private ValueSetter valueSetterInstance = null; //ll
    // endregion

    private ProjectTemplateSetter
            projectTemplateSetterInstance = null;                                           // lazy load
    private ChoosingTemplateSetter
            choosingTemplateSetterInstance = null;                                          // lazy load
    // endregion

    // region Private Methods
    // region Field Access Private Method
    @Types.RequestCode
    private int requestCode() {
        return this.requestCode;
    }
    // endregion

    // region Value Setter Private Methods
    private void setPerson(final String person) {
        this.person = person;
    }

    // region handleSetValuesDone() Value Setter Private Methods
    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }

    @NonNull
    private GridsTable gridsTable() {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
                new GridsTable(this.activity());
        return this.gridsTableInstance;
    }

    @NonNull
    private EntriesTable entriesTable() {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
                new EntriesTable(this.activity());
        return this.entriesTableInstance;
    }

    private void handleSetValuesDone(
            final NonNullOptionalFields optionalFields) {
        final TemplateModel templateModel =
                this.templatesTable().get(this.templateId);
        this.templateIdStatus =
                GridCreator.TemplateIdStatus.UNSPECIFIED;
        if (null != templateModel) {
            final JoinedGridModel joinedGridModel =
                    new JoinedGridModel(
                            /* projectId      => */ this.projectId,
                            /* person         => */ this.person,
                            /* optionalFields => */ optionalFields,
                            /* stringGetter   => */this,
                            /* templateModel  => */ templateModel);
            this.setPerson(null);

            final GridsTable gridsTable = this.gridsTable();
            final long gridId = gridsTable.insert(joinedGridModel);
            if (Model.illegal(gridId))
                org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                        R.string.GridCreatorGridAlertMessage);
            else {
                joinedGridModel.setId(gridId);

                try {
                    if (Model.illegal(                 // no pro-
                            this.projectId))                                                      //  ject
                        joinedGridModel.makeEntryModels();                // throws AmountIsTooLarge
                    else {
                        final BaseJoinedGridModels
                                baseJoinedGridModels = gridsTable.loadByProjectId(this.projectId);
                        if (null == baseJoinedGridModels)
                            joinedGridModel.makeEntryModels();            // throws AmountIsTooLarge
                        else if (baseJoinedGridModels.size() <= 1)    // includes the just added grid
                            joinedGridModel.makeEntryModels();        // throws AmountIsTooLarge
                        else {
                            final Cells excludedCells =
                                    baseJoinedGridModels.excludedCells(
                                            joinedGridModel.getRows(), joinedGridModel.getCols());
                            if (null == excludedCells)
                                joinedGridModel.makeEntryModels();
                            else
                                try {
                                    joinedGridModel.makeEntryModels(excludedCells);// throws
                                }                                                  //  AmountIs-
                                catch (final                                       //  TooLarge
                                        Cells.AmountIsTooLarge e) {
                                    joinedGridModel.makeEntryModels() /* throws AITL */;
                                }
                        }
                    }
                } catch (final Cells.AmountIsTooLarge e) {
                    gridsTable.delete(gridId);
                    org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                            R.string.GridCreatorEntriesAlertMessage,
                            e.getMessage());
                    return;
                }

                if (joinedGridModel.activeRowAndOrActiveColWasAdjusted(
                        Utils.getDirection(this.activity())))
                    gridsTable.update(joinedGridModel);              // Update activeRow, activeCol.

                this.entriesTable().insert(joinedGridModel.getEntryModels());

                if (null != this.handler) this.handler.handleGridCreated(gridId);
            }
        }
    }
    // endregion

    @NonNull
    private ValueSetter valueSetter() {
        if (null == this.valueSetterInstance) this.valueSetterInstance =
                new ValueSetter(this.activity(),
                        new org.wheatgenetics.coordinate.gc.vs.Handler() {
                            @Override
                            public void setPerson(final String person) {
                                GridCreator.this.setPerson(person);
                            }

                            @Override
                            public void handleSetValuesDone(final
                                                            NonNullOptionalFields optionalFields) {
                                GridCreator.this.handleSetValuesDone(
                                        optionalFields);
                            }
                        });
        return this.valueSetterInstance;
    }

    @SuppressLint({"Range"})
    private void setValues() {
        if (!Model.illegal(this.templateId))
            this.valueSetter().set(this.templateId);
    }
    // endregion

    // region TemplateSetter Private Methods
    @NonNull
    private ProjectTemplateSetter projectTemplateSetter() {
        if (null == this.projectTemplateSetterInstance) this.projectTemplateSetterInstance =
                new ProjectTemplateSetter(this.activity());
        return this.projectTemplateSetterInstance;
    }

    // region ChoosingTemplateSetter Private Methods
    private void handleTemplateSet(@IntRange(from = 1) long templateId) {
        this.setTemplateId(templateId);
        this.setValues();
    }

    @NonNull
    private ChoosingTemplateSetter choosingTemplateSetter() {
        if (null == this.choosingTemplateSetterInstance) this.choosingTemplateSetterInstance =
                new ChoosingTemplateSetter(
                        this.activity(), this.requestCode(),
                        new ChoosingTemplateSetter.Handler() {
                            @Override
                            public void handleTemplateSet(
                                    @IntRange(from = 1) final long templateId) {
                                GridCreator.this.handleTemplateSet(
                                        templateId);
                            }
                        });
        return this.choosingTemplateSetterInstance;
    }

    private void setTemplate() {
        this.choosingTemplateSetter().set();
    }
    // endregion
    // endregion
    // endregion

    // region Package Methods
    // region Field Access Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    Activity activity() {
        return this.activity;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    GridCreator.Handler handler() {
        return this.handler;
    }
    // endregion

    // region templateIdStatus Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setTemplateIdStatusToCleared() {
        this.templateIdStatus =
                GridCreator.TemplateIdStatus.CLEARED;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setTemplateIdStatusToSet() {
        this.templateIdStatus = GridCreator.TemplateIdStatus.SET;
    }
    // endregion

    // region Template Setter Package Method
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setTemplateId(@IntRange(from = 1) long templateId) {
        this.templateId = templateId;
    }
    // endregion

    // region Project Setter Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void handleNoProjectSet() {
        this.projectId = 0;

        switch (this.templateIdStatus) {
            case UNSPECIFIED:
            case CLEARED:
                this.setTemplate();
                break;
            case SET:
                this.setValues();
                break;
        }
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @SuppressLint({"Range"})
    void handleProjectSet(
            @IntRange(from = 1) final long projectId, final boolean existing) {
        this.projectId = projectId;

        switch (this.templateIdStatus) {
            case UNSPECIFIED:
            case CLEARED:
                if (existing) {
                    @IntRange(from = 0) final long templateId =
                            this.projectTemplateSetter().set(projectId);
                    if (Model.illegal(templateId))
                        this.setTemplate();
                    else
                        this.handleTemplateSet(templateId);               // SuppressLint({"Range"})
                } else this.setTemplate();
                break;

            case SET:
                this.setValues();
                break;
        }
    }
    // endregion
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    GridCreator(final Activity activity,
                @Types.RequestCode final int requestCode,
                @Nullable final
                GridCreator.Handler handler) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
        this.handler = handler;
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.activity().getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.activity().getResources().getQuantityString(resId, quantity, formatArgs);
    }
    // endregion

    public void continueExcluding(final Bundle bundle) {
        this.choosingTemplateSetter().continueExcluding(bundle);
    }
}