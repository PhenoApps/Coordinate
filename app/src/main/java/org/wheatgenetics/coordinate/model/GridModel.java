package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private final @android.support.annotation.IntRange(from = 1) long templateId           ;
    private final @android.support.annotation.IntRange(from = 0) long projectId            ;
    private final java.lang.String                                    person               ;
    private       @android.support.annotation.IntRange(from = 0) int  activeRow, activeCol ;
    private final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
    private final long timestamp;
    // endregion

    // region Constructors
    /** Used by first JoinedGridModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(@android.support.annotation.IntRange(from = 1) final long templateId,
    final long projectId, final java.lang.String person,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super();

        this.templateId = org.wheatgenetics.coordinate.model.Model.valid(templateId);
        this.projectId  =
            org.wheatgenetics.coordinate.model.Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.activeRow = this.activeCol = 0;

        this.nonNullOptionalFieldsInstance = optionalFields                      ;
        this.timestamp                     = java.lang.System.currentTimeMillis();
    }

    /** Used by second JoinedGridModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(
    @android.support.annotation.IntRange(from = 1) final long id                ,
    @android.support.annotation.IntRange(from = 1) final long templateId        ,
                                                   final long projectId         ,
                                                   final java.lang.String person,
    @android.support.annotation.IntRange(from = 0) final int activeRow          ,
    @android.support.annotation.IntRange(from = 0) final int activeCol          ,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id);

        this.templateId = org.wheatgenetics.coordinate.model.Model.valid(templateId);
        this.projectId  =
            org.wheatgenetics.coordinate.model.Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.setActiveRow(activeRow); this.setActiveCol(activeCol);

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);

        this.timestamp = timestamp;
    }
    // endregion

    // region Package Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void setActiveRow(@android.support.annotation.IntRange(from = 0) final int activeRow)
    { this.activeRow = org.wheatgenetics.coordinate.Utils.valid(activeRow,0); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void setActiveCol(@android.support.annotation.IntRange(from = 0) final int activeCol)
    { this.activeCol = org.wheatgenetics.coordinate.Utils.valid(activeCol,0); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }
    // endregion

    // region Public Methods
    public @android.support.annotation.IntRange(from = 1) long getTemplateId()
    { return this.templateId; }

    public @android.support.annotation.IntRange(from = 0) long getProjectId()
    { return this.projectId; }

    public java.lang.String getPerson() { return this.person; }

    public @android.support.annotation.IntRange(from = 0) int getActiveRow()
    { return this.activeRow; }

    public @android.support.annotation.IntRange(from = 0) int getActiveCol()
    { return this.activeCol; }

    public long getTimestamp() { return this.timestamp; }

    // region optionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }

    public java.lang.String getFirstOptionalFieldDatedValue()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            this.nonNullOptionalFieldsInstance.getDatedFirstValue();
    }
    // endregion
    // endregion
}