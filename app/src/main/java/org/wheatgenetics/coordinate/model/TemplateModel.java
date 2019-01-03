package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 * android.support.annotation.VisibleForTesting
 * android.util.Xml
 *
 * org.xmlpull.v1.XmlSerializer
 * org.xml.sax.Attributes
 * org.xml.sax.SAXException
 * org.xml.sax.helpers.DefaultHandler
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
    private static final java.lang.String OPTIONAL_FIELDS_TAG_NAME = "optionalFields";

    // region Fields
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;

    private static javax.xml.parsers.SAXParserFactory saxParserFactoryInstance = null;
    private static javax.xml.parsers.SAXParser        saxParserInstance        = null;
    // endregion

    // region Private Methods
    private void setOptionalFields(java.lang.String optionalFields)
    {
        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);

    }

    private static javax.xml.parsers.SAXParserFactory saxParserFactory()
    {
        if (null == org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance)
            org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance =
                javax.xml.parsers.SAXParserFactory.newInstance();
        return org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance;
    }

    private static javax.xml.parsers.SAXParser saxParser()
    {
        if (null == org.wheatgenetics.coordinate.model.TemplateModel.saxParserInstance)
            try
            {
                org.wheatgenetics.coordinate.model.TemplateModel.saxParserInstance =
                    org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactory()
                        .newSAXParser();   // throws javax.xml.parsers.ParserConfigurationException,
            }                              //   org.xml.sax.SAXException
            catch (
            final javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e)
            { org.wheatgenetics.coordinate.model.TemplateModel.saxParserInstance = null; }
        return org.wheatgenetics.coordinate.model.TemplateModel.saxParserInstance;
    }
    // endregion

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
    final long                                                             timestamp     )
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
    final long                                                             timestamp     )
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
    final boolean                                                          rowNumbering  ,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
            /* excludedCells => */null, /* excludedRows => */null,
            /* excludedCols  => */null, /* colNumbering  => */true,
            rowNumbering, optionalFields,
            /* timestamp => */ type.isDefaultTemplate() ? 0 : java.lang.System.currentTimeMillis());
    }

    /** Called by DefaultHandler class. */ private TemplateModel() { super(); }

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
    final java.lang.String entryLabel, final java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, code, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel, timestamp);
        this.setOptionalFields(optionalFields);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override public java.lang.String toString()
    {
        return java.lang.String.format(super.formatString() + ", options=%s]",
            "TemplateModel", null == this.nonNullOptionalFieldsInstance ? "" :
                this.nonNullOptionalFieldsInstance.toString());
    }

    @java.lang.Override public boolean equals(final java.lang.Object object)
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
                // noinspection SimplifiableIfStatement
                if (null == this.nonNullOptionalFieldsInstance)
                    return true;
                else
                    return this.nonNullOptionalFieldsInstance.equals(
                        templateModel.nonNullOptionalFieldsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone"})
    protected java.lang.Object clone()
    {
        final long                                     id            = this.getId             ();
        final org.wheatgenetics.coordinate.model.Cells excludedCells = this.excludedCellsClone();
        final org.wheatgenetics.coordinate.model.RowOrCols
            excludedRows = this.excludedRowsClone(), excludedCols = this.excludedColsClone();
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

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void assignCharacterData(final java.lang.String elementName,
    final java.lang.String characterData)
    {
        if (org.wheatgenetics.coordinate.model.TemplateModel.OPTIONAL_FIELDS_TAG_NAME.equals(
        elementName))
            this.setOptionalFields(characterData);
        else
            super.assignCharacterData(elementName, characterData);
    }
    // endregion

    // region Package Methods
    @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    boolean export(final java.io.Writer writer)
    {
        final boolean success = true;
        if (null == writer)
            // noinspection PointlessBooleanExpression
            return !success;
        else
        {
            try
            {
                final org.xmlpull.v1.XmlSerializer xmlSerializer = android.util.Xml.newSerializer();
                assert null != xmlSerializer; xmlSerializer.setOutput(writer);    // throws java.io-
                                                                                  //  .IOException
                xmlSerializer.startDocument("UTF-8",true);    // throws java.io-
                try                                                              //  .IOException
                {
                    xmlSerializer.ignorableWhitespace("\n");

                    final java.lang.String templateTagName = "template";
                    xmlSerializer.startTag(null, templateTagName);     // throws java.io-
                                                                                  //  .IOException
                    {
                        final java.lang.String indent = "\n    ";
                        if (!this.export(xmlSerializer, indent))
                            // noinspection PointlessBooleanExpression
                            return !success;
                        if (null != this.nonNullOptionalFieldsInstance)
                            org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
                                xmlSerializer, indent, org.wheatgenetics.coordinate.model
                                    .TemplateModel.OPTIONAL_FIELDS_TAG_NAME,
                                this.nonNullOptionalFieldsInstance.toJson());
                    }

                    xmlSerializer.ignorableWhitespace("\n");
                    xmlSerializer.endTag(null, templateTagName);
                }
                finally { xmlSerializer.endDocument() /* throws java.io.IOException */; }
            }
            catch (final java.io.IOException e)
            {
                // noinspection PointlessBooleanExpression
                return !success;
            }
            return success;
        }
    }

    boolean export(final java.io.File exportFile)
    {
        final boolean failure = false;
        if (null == exportFile)
            return failure;
        else
            try { return this.export(new java.io.FileWriter(exportFile) /* throws */); }
            catch (final java.io.IOException e) { return failure; }
    }
    // endregion

    // region Public Methods
    // region optionalFields Public Methods
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public boolean optionalFieldsIsEmpty()
    {
        // noinspection SimplifiableConditionalExpression
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
                /* title => */"Seed Tray",
                /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows  => */6,
                /* cols  => */20,
                /* generatedExcludedCellsAmount => */0,
                /* rowNumbering                 => */true,
                /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFields.makeSeedDefault());
        result.addExcludedRow(2); result.addExcludedRow(5);
        return result;
    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */"DNA Plate",
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows  => */8,
            /* cols  => */12,
            /* generatedExcludedCellsAmount => */1,
            /* rowNumbering                 => */false,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeDNADefault());
    }

    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */"",
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */20,
            /* cols  => */10,
            /* generatedExcludedCellsAmount => */1,
            /* rowNumbering                 => */false,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeNew());
    }

    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined(
    final java.io.File file)
    {
        if (null == file)
            return null;
        else
        {
            class DefaultHandler extends org.xml.sax.helpers.DefaultHandler
            {
                // region Fields
                private java.lang.String                                 elementName;
                private org.wheatgenetics.coordinate.model.TemplateModel
                    templateModelInstance = null;
                // endregion

                private org.wheatgenetics.coordinate.model.TemplateModel templateModel()
                {
                    if (null == this.templateModelInstance) this.templateModelInstance =
                        new org.wheatgenetics.coordinate.model.TemplateModel();
                    return templateModelInstance;
                }

                // region Overridden Methods
                @java.lang.Override public void startElement(final java.lang.String uri,
                final java.lang.String localName, final java.lang.String qName,
                final org.xml.sax.Attributes attributes) { this.elementName = qName; }

                @java.lang.Override
                public void characters(final char ch[], final int start, final int length)
                {
                    this.templateModel().assignCharacterData(this.elementName,
                        /* characterData => */ new java.lang.String(ch, start, length));
                }

                @java.lang.Override public void endElement(final java.lang.String uri,
                final java.lang.String localName, final java.lang.String qName)
                { this.elementName = null; }
                // endregion

                private org.wheatgenetics.coordinate.model.TemplateModel getTemplateModel()
                { return this.templateModelInstance; }
            }

            final DefaultHandler defaultHandler = new DefaultHandler();
            try
            {
                final java.io.FileInputStream fileInputStream =
                    new java.io.FileInputStream(file);
                // noinspection TryFinallyCanBeTryWithResources
                try
                {
                    try
                    {
                        org.wheatgenetics.coordinate.model.TemplateModel.saxParser()
                            .parse(fileInputStream, defaultHandler);           // throws org.xml-
                    }                                                          //  .sax.SAXException
                    catch (final org.xml.sax.SAXException e) { return null; }
                }
                finally { fileInputStream.close(); /* throws java.io.IOException */ }
            }
            catch (final java.io.IOException e) { /* Don't close fileInputStream. */ }

            return defaultHandler.getTemplateModel();
        }
    }
    // endregion
    // endregion
}