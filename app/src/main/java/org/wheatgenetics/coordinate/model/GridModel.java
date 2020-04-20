package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    @androidx.annotation.IntRange(from = 1) private final long             templateId          ;
    @androidx.annotation.IntRange(from = 0) private final long             projectId           ;
                                            private final java.lang.String person              ;
    @androidx.annotation.IntRange(from = 0) private       int              activeRow, activeCol;
    @androidx.annotation.Nullable           private final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
    @androidx.annotation.IntRange(from = 0) private final long timestamp;
    // endregion

    // region Constructors
    /** Used by first JoinedGridModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(
    @androidx.annotation.IntRange(from = 1) final long             templateId,
    @androidx.annotation.IntRange(from = 0) final long             projectId ,
                                            final java.lang.String person    ,
    @androidx.annotation.Nullable           final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        super(stringGetter);

        this.templateId =
            org.wheatgenetics.coordinate.model.Model.valid(templateId, this.stringGetter());
        this.projectId  =
            org.wheatgenetics.coordinate.model.Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.activeRow = this.activeCol = 0;

        this.nonNullOptionalFieldsInstance = optionalFields                      ;
        this.timestamp                     = java.lang.System.currentTimeMillis();
    }

    /** Used by second JoinedGridModel constructor. */
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(
    @androidx.annotation.IntRange(from = 1) final long                           id                ,
    @androidx.annotation.IntRange(from = 1) final long                           templateId        ,
    @androidx.annotation.IntRange(from = 0) final long                           projectId         ,
                                            final java.lang.String               person            ,
    @androidx.annotation.IntRange(from = 0) final int                            activeRow         ,
    @androidx.annotation.IntRange(from = 0) final int                            activeCol         ,
    @androidx.annotation.Nullable                 java.lang.String               optionalFields    ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter      ,
    @androidx.annotation.IntRange(from = 0) final long                           timestamp         )
    {
        super(id, stringGetter);

        this.templateId =
            org.wheatgenetics.coordinate.model.Model.valid(templateId, this.stringGetter());
        this.projectId  =
            org.wheatgenetics.coordinate.model.Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.setActiveRow(activeRow); this.setActiveCol(activeCol);

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(
                    optionalFields, stringGetter);
        this.timestamp = timestamp;
    }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setActiveRow(@androidx.annotation.IntRange(from = 0) final int activeRow)
    {
        this.activeRow = org.wheatgenetics.coordinate.Utils.valid(
            activeRow,0, this.stringGetter());
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void setActiveCol(@androidx.annotation.IntRange(from = 0) final int activeCol)
    {
        this.activeCol = org.wheatgenetics.coordinate.Utils.valid(
            activeCol,0, this.stringGetter());
    }
    // endregion

    // region Public Methods
    @androidx.annotation.IntRange(from = 1) public long getTemplateId() { return this.templateId; }
    @androidx.annotation.IntRange(from = 0) public long getProjectId () { return this.projectId ; }

    public java.lang.String getPerson() { return this.person; }

    @androidx.annotation.IntRange(from = 0) public int  getActiveRow() { return this.activeRow; }
    @androidx.annotation.IntRange(from = 0) public int  getActiveCol() { return this.activeCol; }
    @androidx.annotation.IntRange(from = 0) public long getTimestamp() { return this.timestamp; }

    public java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }

    // region optionalFields Public Methods
    @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    @androidx.annotation.Nullable public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }

    @androidx.annotation.Nullable public java.lang.String getFirstOptionalFieldDatedValue()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            this.nonNullOptionalFieldsInstance.getDatedFirstValue();
    }
    // endregion
    // endregion
}