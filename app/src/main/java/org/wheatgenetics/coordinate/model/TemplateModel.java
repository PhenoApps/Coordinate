package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.util.Xml
 *
 * org.xmlpull.v1.XmlSerializer
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.DisplayTemplateModel
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.RowOrCols
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class TemplateModel extends org.wheatgenetics.coordinate.model.DisplayTemplateModel
{
    private final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;

    // region Constructors
    /** Called by clone(). */
    private TemplateModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows,excludedCols, colNumbering, rowNumbering, timestamp);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by clone() and third constructor. */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount, excludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, timestamp);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by makeSeedDefault(), makeDNADefault(), and makeUserDefined(). */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
            null, null, null, true, rowNumbering, optionalFields, 0);
    }

    /** Called by JoinedGridModel constructor and TemplatesTable.make(). */
    public TemplateModel(
    @android.support.annotation.IntRange(from = 1        ) final long             id             ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    final java.lang.String excludedCells,
    final java.lang.String excludedRows, final java.lang.String excludedCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, code, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, timestamp);

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString()
    {
        return java.lang.String.format(super.formatString() + ", options=%s]",
            "TemplateModel", null == this.nonNullOptionalFieldsInstance ? "" :
                this.nonNullOptionalFieldsInstance.toString());
    }

    @java.lang.Override @java.lang.SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final java.lang.Object object)
    {
        if (super.equals(object))
            if (object instanceof org.wheatgenetics.coordinate.model.TemplateModel)
            {
                final org.wheatgenetics.coordinate.model.TemplateModel templateModel =
                    (org.wheatgenetics.coordinate.model.TemplateModel) object;

                if (null == this.nonNullOptionalFieldsInstance
                &&  null != templateModel.nonNullOptionalFieldsInstance)
                    return false;
                else
                    if (null != this.nonNullOptionalFieldsInstance
                    &&  null == templateModel.nonNullOptionalFieldsInstance)
                        return false;
                if (null == this.nonNullOptionalFieldsInstance)
                    return true;
                else
                    return this.nonNullOptionalFieldsInstance.equals(
                        templateModel.nonNullOptionalFieldsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings("CloneDoesntCallSuperClone")
    protected java.lang.Object clone()
    {
        final long                                     id            = this.getId             ();
        final org.wheatgenetics.coordinate.model.Cells excludedCells = this.excludedCellsClone();
        final org.wheatgenetics.coordinate.model.RowOrCols
            excludedRows = this.excludedRowsClone(),
            excludedCols = this.excludedColsClone();
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
            null == this.nonNullOptionalFieldsInstance ? null :
                (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                    this.nonNullOptionalFieldsInstance.clone();

        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            return new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* timestamp                    => */ this.getTimestamp()                   );
        else
            return new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ id                                    ,
                /* title                        => */ this.getTitle()                       ,
                /* type                         => */ this.getType()                        ,
                /* rows                         => */ this.getRows()                        ,
                /* cols                         => */ this.getCols()                        ,
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* timestamp                    => */ this.getTimestamp()                   );
    }
    // endregion

    @java.lang.SuppressWarnings("PointlessBooleanExpression")
    boolean export(final java.io.File exportFile)
    {
        final boolean success = true;
        if (null == exportFile)
            return !success;
        else
        {
            try
            {
                final java.io.FileWriter fileWriter = new java.io.FileWriter(exportFile); // throws
                try                                                                       //  java.-
                {                                                                         //  io.IO-
                    final org.xmlpull.v1.XmlSerializer xmlSerializer =                    //  Excep-
                        android.util.Xml.newSerializer();                                 //  tion
                    assert null != xmlSerializer;
                    xmlSerializer.setOutput(fileWriter);               // throws java.io.IOException

                    xmlSerializer.startDocument(                       // throws java.io.IOException
                        /* encoding   => */ "UTF-8",
                        /* standalone => */ true);

                    xmlSerializer.ignorableWhitespace("\n");
                    {
                        final java.lang.String templateTagName = "template";
                        xmlSerializer.startTag(null, templateTagName); // throws java.io.IOException

                        {
                            final java.lang.String indent = "\n    ";
                            if (!this.export(xmlSerializer, indent)) return !success;
                            if (null != this.nonNullOptionalFieldsInstance)
                                org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeTag(
                                    xmlSerializer, indent, "optionalFields",
                                    this.nonNullOptionalFieldsInstance.toJson());
                        }

                        xmlSerializer.ignorableWhitespace("\n");
                        xmlSerializer.endTag(null, templateTagName);
                    }

                    xmlSerializer.endDocument();                       // throws java.io.IOException
                }
                finally { fileWriter.close(); }
            }
            catch (final java.io.IOException e) { return !success; }
            return success;
        }
    }

    // region Public Methods
    // region optionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean optionalFieldsIsEmpty()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            true : this.nonNullOptionalFieldsInstance.isEmpty();
    }

    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsClone()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.nonNullOptionalFieldsInstance.clone();
    }

    public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }
    // endregion

    // region Make Public Methods
    static org.wheatgenetics.coordinate.model.TemplateModel makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title => */ "Seed Tray"                                         ,
                /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows  => */  6                                                  ,
                /* cols  => */ 20                                                  ,
                /* generatedExcludedCellsAmount => */ 0                            ,
                /* rowNumbering                 => */ true                         ,
                /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFields.makeSeedDefault());
        result.addExcludedRow(2); result.addExcludedRow(5);
        return result;
    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */ "DNA Plate"                                        ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows  => */  8                                                 ,
            /* cols  => */ 12                                                 ,
            /* generatedExcludedCellsAmount => */ 1                           ,
            /* rowNumbering                 => */ false                       ,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeDNADefault());
    }

    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */ ""                                                         ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */ 20                                                         ,
            /* cols  => */ 10                                                         ,
            /* generatedExcludedCellsAmount => */ 1                                   ,
            /* rowNumbering                 => */ false                               ,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeNew());
    }
    // endregion
    // endregion
}