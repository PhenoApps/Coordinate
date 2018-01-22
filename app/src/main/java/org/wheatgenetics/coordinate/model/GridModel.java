package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.Utils
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private final long             templateId          ;
    private final java.lang.String person              ;
    private       int              activeRow, activeCol;
    private final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
    private final long timestamp;
    // endregion

    // region Constructors
    /** Used by first JoinedGridModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(@android.support.annotation.IntRange(from = 1) final long templateId,
    final java.lang.String person,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super();

        this.templateId = org.wheatgenetics.coordinate.model.Model.valid(templateId);
        this.person     = person                                                    ;

        this.activeRow = this.activeCol = 0;

        this.nonNullOptionalFieldsInstance = optionalFields                      ;
        this.timestamp                     = java.lang.System.currentTimeMillis();
    }

    /** Used by second JoinedGridModel constructor. */
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(
    @android.support.annotation.IntRange(from = 1) final long id                ,
    @android.support.annotation.IntRange(from = 1) final long templateId        ,
                                                   final java.lang.String person,
    @android.support.annotation.IntRange(from = 0) final int activeRow          ,
    @android.support.annotation.IntRange(from = 0) final int activeCol          ,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id);

        this.templateId = org.wheatgenetics.coordinate.model.Model.valid(templateId);
        this.person     = person                                                    ;

        this.activeRow = activeRow; this.activeCol = activeCol;

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
    { this.activeRow = org.wheatgenetics.coordinate.model.Utils.valid(activeRow, 0); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void setActiveCol(@android.support.annotation.IntRange(from = 0) final int activeCol)
    { this.activeCol = org.wheatgenetics.coordinate.model.Utils.valid(activeCol, 0); }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }
    // endregion

    // region Public Methods
    public long             getTemplateId() { return this.templateId; }
    public java.lang.String getPerson    () { return this.person    ; }
    public int              getActiveRow () { return this.activeRow ; }
    public int              getActiveCol () { return this.activeCol ; }
    public long             getTimestamp () { return this.timestamp ; }

    // region OptionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }
    // endregion
    // endregion
}