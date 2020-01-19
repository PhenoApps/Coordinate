package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.app.Activity
 * android.os.Bundle
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.preference.Utils
 *
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter
 *
 * org.wheatgenetics.coordinate.gc.vs.Handler
 * org.wheatgenetics.coordinate.gc.vs.ValueSetter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class GridCreator extends java.lang.Object
{
    // region Types
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void handleGridCreated(@androidx.annotation.IntRange(from = 1) long gridId); }

    private enum TemplateIdStatus { UNSPECIFIED, CLEARED, SET }
    // endregion

    // region Fields
    // region Constructor Fields
                                                    private final android.app.Activity activity   ;
    @org.wheatgenetics.coordinate.Types.RequestCode private final int                  requestCode;
    @androidx.annotation.Nullable                   private final
        org.wheatgenetics.coordinate.gc.GridCreator.Handler handler;
    // endregion

    private org.wheatgenetics.coordinate.gc.GridCreator.TemplateIdStatus templateIdStatus =
        org.wheatgenetics.coordinate.gc.GridCreator.TemplateIdStatus.UNSPECIFIED;

    @androidx.annotation.IntRange(from = 0) private long             projectId, templateId;
                                            private java.lang.String person               ;

    // region Value Setter Fields
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null; //ll
    private org.wheatgenetics.coordinate.database.GridsTable     gridsTableInstance     = null; //ll
    private org.wheatgenetics.coordinate.database.EntriesTable   entriesTableInstance   = null; //ll
    private org.wheatgenetics.coordinate.gc.vs.ValueSetter       valueSetterInstance    = null; //ll
    // endregion

    private org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter
        projectTemplateSetterInstance = null;                                           // lazy load
    // endregion

    // region Private Methods
    // region Value Setter Private Methods
    private void setPerson(final java.lang.String person) { this.person = person; }

    // region handleSetValuesDone() Value Setter Private Methods
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

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.activity());
        return this.entriesTableInstance;
    }

    private void handleSetValuesDone(
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
            this.templatesTable().get(this.templateId);
        this.templateIdStatus =
            org.wheatgenetics.coordinate.gc.GridCreator.TemplateIdStatus.UNSPECIFIED;
        if (null != templateModel)
        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* projectId      => */ this.projectId,
                    /* person         => */ this.person   ,
                    /* optionalFields => */ optionalFields,
                    /* templateModel  => */ templateModel );
            this.setPerson(null);

            final org.wheatgenetics.coordinate.database.GridsTable gridsTable = this.gridsTable();
            final long gridId = gridsTable.insert(joinedGridModel);
            if (org.wheatgenetics.coordinate.model.Model.illegal(gridId))
                org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                    org.wheatgenetics.coordinate.R.string.GridCreatorGridAlertMessage);
            else
            {
                joinedGridModel.setId(gridId);

                try
                {
                    if (org.wheatgenetics.coordinate.model.Model.illegal(                 // no pro-
                    this.projectId))                                                      //  ject
                        joinedGridModel.makeEntryModels();                // throws AmountIsTooLarge
                    else
                    {
                        final org.wheatgenetics.coordinate.model.BaseJoinedGridModels
                            baseJoinedGridModels = gridsTable.loadByProjectId(this.projectId);
                        if (null == baseJoinedGridModels)
                            joinedGridModel.makeEntryModels();            // throws AmountIsTooLarge
                        else
                            if (baseJoinedGridModels.size() <= 1)    // includes the just added grid
                                joinedGridModel.makeEntryModels();        // throws AmountIsTooLarge
                            else
                            {
                                final org.wheatgenetics.coordinate.model.Cells excludedCells =
                                    baseJoinedGridModels.excludedCells(
                                        joinedGridModel.getRows(), joinedGridModel.getCols());
                                if (null == excludedCells)
                                    joinedGridModel.makeEntryModels();
                                else
                                    try
                                    {
                                        joinedGridModel.makeEntryModels(excludedCells);// throws
                                    }                                                  //  AmountIs-
                                    catch (final                                       //  TooLarge
                                    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge e)
                                    { joinedGridModel.makeEntryModels() /* throws AITL */; }
                            }
                    }
                }
                catch (final org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge e)
                {
                    gridsTable.delete(gridId);
                    org.wheatgenetics.coordinate.Utils.alert(this.activity()                ,
                        org.wheatgenetics.coordinate.R.string.GridCreatorEntriesAlertMessage,
                        e.getMessage()                                                      );
                    return;
                }

                if (joinedGridModel.activeRowAndOrActiveColWasAdjusted(
                org.wheatgenetics.coordinate.preference.Utils.getAdvancement(this.activity())))
                    gridsTable.update(joinedGridModel);              // Update activeRow, activeCol.

                this.entriesTable().insert(joinedGridModel.getEntryModels());

                if (null != this.handler) this.handler.handleGridCreated(gridId);
            }
        }
    }
    // endregion

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.vs.ValueSetter valueSetter()
    {
        if (null == this.valueSetterInstance) this.valueSetterInstance =
            new org.wheatgenetics.coordinate.gc.vs.ValueSetter(this.activity(),
                new org.wheatgenetics.coordinate.gc.vs.Handler()
                {
                    @java.lang.Override public void setPerson(final java.lang.String person)
                    { org.wheatgenetics.coordinate.gc.GridCreator.this.setPerson(person); }

                    @java.lang.Override public void handleSetValuesDone(final
                    org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
                    {
                        org.wheatgenetics.coordinate.gc.GridCreator.this.handleSetValuesDone(
                            optionalFields);
                    }
                });
        return this.valueSetterInstance;
    }

    @android.annotation.SuppressLint({"Range"}) private void setValues()
    {
        if (!org.wheatgenetics.coordinate.model.Model.illegal(this.templateId))
            this.valueSetter().set(this.templateId);
    }
    // endregion

    // region Template Setter Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter projectTemplateSetter()
    {
        if (null == this.projectTemplateSetterInstance) this.projectTemplateSetterInstance =
            new org.wheatgenetics.coordinate.gc.ts.ProjectTemplateSetter(this.activity());
        return this.projectTemplateSetterInstance;
    }

    private void setTemplate() { this.choosingTemplateSetter().set(); }
    // endregion
    // endregion

    // region Package Methods
    // region Field Access Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    android.app.Activity activity() { return this.activity; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @org.wheatgenetics.coordinate.Types.RequestCode int requestCode() { return this.requestCode; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable
    org.wheatgenetics.coordinate.gc.GridCreator.Handler handler() { return this.handler; }
    // endregion

    // region templateIdStatus Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setTemplateIdStatusToCleared()
    {
        this.templateIdStatus =
            org.wheatgenetics.coordinate.gc.GridCreator.TemplateIdStatus.CLEARED;
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setTemplateIdStatusToSet()
    { this.templateIdStatus = org.wheatgenetics.coordinate.gc.GridCreator.TemplateIdStatus.SET; }
    // endregion

    // region Template Setter Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setTemplateId(@androidx.annotation.IntRange(from = 1) long templateId)
    { this.templateId = templateId; }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void handleTemplateSet(@androidx.annotation.IntRange(from = 1) long templateId)
    { this.setTemplateId(templateId); this.setValues(); }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull
    abstract org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter choosingTemplateSetter();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract boolean choosingTemplateSetterIsNotNull();
    // endregion

    // region Project Setter Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void handleNoProjectSet()
    {
        this.projectId = 0;

        switch (this.templateIdStatus)
        {
            case UNSPECIFIED: case CLEARED: this.setTemplate(); break;
            case SET                      : this.setValues  (); break;
        }
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.annotation.SuppressLint({"Range"}) void handleProjectSet(
    @androidx.annotation.IntRange(from = 1) final long projectId, final boolean existing )
    {
        this.projectId = projectId;

        switch (this.templateIdStatus)
        {
            case UNSPECIFIED: case CLEARED:
                if (existing)
                {
                    @androidx.annotation.IntRange(from = 0) final long templateId =
                        this.projectTemplateSetter().set(projectId);
                    if (org.wheatgenetics.coordinate.model.Model.illegal(templateId))
                        this.setTemplate();
                    else
                        this.handleTemplateSet(templateId);               // SuppressLint({"Range"})
                }
                else this.setTemplate();
                break;

            case SET: this.setValues(); break;
        }
    }
    // endregion
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    GridCreator(                                    final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.Nullable                   final
        org.wheatgenetics.coordinate.gc.GridCreator.Handler handler)
    { super(); this.activity = activity; this.requestCode = requestCode; this.handler = handler; }

    public void setExcludedCells(final android.os.Bundle bundle)
    {
        if (this.choosingTemplateSetterIsNotNull())
            this.choosingTemplateSetter().setExcludedCells(bundle);
    }
}