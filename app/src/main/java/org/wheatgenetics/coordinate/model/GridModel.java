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
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Model
 */
public class GridModel extends org.wheatgenetics.coordinate.model.Model
{
    // region Fields
    private       long                                     templateId           ;
    private final java.lang.String                         person               ;
                  org.wheatgenetics.coordinate.model.Cells excludedCellsInstance;
    private final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;
    private final long timestamp;
    // endregion

    // region Constructors
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(@android.support.annotation.IntRange(from = 1) final long templateId,
    final java.lang.String                                                 person        ,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super();

        this.templateId = org.wheatgenetics.coordinate.model.Model.valid(templateId);

        this.person                        = person                              ;
        this.nonNullOptionalFieldsInstance = optionalFields                      ;
        this.timestamp                     = java.lang.System.currentTimeMillis();
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    GridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String person, java.lang.String excludedCells,
    @android.support.annotation.IntRange(from = 1) final int maxRow,
    @android.support.annotation.IntRange(from = 1) final int maxCol,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id);

        this.person = person;

        if (null == excludedCells)
            this.excludedCellsInstance = null;
        else
        {
            excludedCells = excludedCells.trim();
            if (excludedCells.length() <= 0)
                this.excludedCellsInstance = null;
            else
                this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                    /* json   => */ excludedCells,
                    /* maxRow => */ maxRow       ,
                    /* maxCol => */ maxCol       );
        }

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);

        this.timestamp = timestamp;
    }
    // endregion

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    java.lang.CharSequence getFormattedTimestamp()
    { return org.wheatgenetics.androidlibrary.Utils.formatDate(this.getTimestamp()); }

    // region Public Methods
    public long             getTemplateId() { return this.templateId; }
    public java.lang.String getPerson    () { return this.person    ; }
    public long             getTimestamp () { return this.timestamp ; }

    public java.lang.String excludedCellsAsJson()
    { return null == this.excludedCellsInstance ? null : this.excludedCellsInstance.json(); }

    // region OptionalFields Public Methods
    public java.lang.String getTitle()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            this.nonNullOptionalFieldsInstance.getFirstValue();
    }

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