package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
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
    @android.support.annotation.Nullable
        private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFieldsInstance;

    private static javax.xml.parsers.SAXParserFactory saxParserFactoryInstance = null;  // lazy load
    private static javax.xml.parsers.SAXParser        saxParserInstance        = null;  // lazy load
    // endregion

    // region Private Methods
    private void setOptionalFields(
    @android.support.annotation.Nullable java.lang.String optionalFields)
    {
        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
            optionalFields.equals("") ? null : new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);

    }

    @android.support.annotation.NonNull
    private static javax.xml.parsers.SAXParserFactory saxParserFactory()
    {
        if (null == org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance)
            org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance =
                javax.xml.parsers.SAXParserFactory.newInstance();
        return org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance;
    }

    @android.support.annotation.Nullable private static javax.xml.parsers.SAXParser saxParser()
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
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.Cells excludedCells,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.RowOrCols excludedRows,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.RowOrCols excludedCols,
    final boolean colNumbering, final boolean rowNumbering,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, timestamp);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by clone() and third constructor. */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int rows                        ,
    @android.support.annotation.IntRange(from = 1) final int cols                        ,
    @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.Cells excludedCells,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.RowOrCols excludedRows,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.RowOrCols excludedCols,
    final boolean colNumbering, final boolean rowNumbering,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @android.support.annotation.IntRange(from = 0) final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount, excludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, timestamp);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by makeSeedDefault() and makeDNADefault(). */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @android.support.annotation.IntRange(from = 1) final int     rows                        ,
    @android.support.annotation.IntRange(from = 1) final int     cols                        ,
    @android.support.annotation.IntRange(from = 0) final int     generatedExcludedCellsAmount,
                                                   final boolean rowNumbering                ,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
            /* excludedCells => */null, /* excludedRows => */null,
            /* excludedCols  => */null, /* colNumbering  => */true,
            rowNumbering, optionalFields,
            /* timestamp => */0);
    }

    /** Called by makeUserDefined(). */
    private TemplateModel(@android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields)
    {
        super();

        this.setGeneratedExcludedCellsAmount(1);
        this.setColNumbering(true); this.setRowNumbering(false);

        this.nonNullOptionalFieldsInstance = optionalFields;
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
    @android.support.annotation.Nullable                   final java.lang.String excludedCells ,
    @android.support.annotation.Nullable                   final java.lang.String excludedRows  ,
    @android.support.annotation.Nullable                   final java.lang.String excludedCols  ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int              colNumbering  ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int              rowNumbering  ,
                                                           final java.lang.String entryLabel    ,
    @android.support.annotation.Nullable                   final java.lang.String optionalFields,
    @android.support.annotation.IntRange(from = 0)         final long             timestamp     )
    {
        super(id, title, code, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel, timestamp);
        this.setOptionalFields(optionalFields);
    }
    // endregion

    // region Overridden Methods
    @android.support.annotation.NonNull @java.lang.Override public java.lang.String toString()
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
                // noinspection SimplifiableConditionalExpression
                return null == this.nonNullOptionalFieldsInstance ? true :
                    this.nonNullOptionalFieldsInstance.equals(
                        templateModel.nonNullOptionalFieldsInstance);
            }
            else return false;
        else return false;
    }

    @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone"})
    @java.lang.Override protected java.lang.Object clone()
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
    @java.lang.Override void assignCharacterData(
    @android.support.annotation.Nullable final java.lang.String elementName  ,
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
    boolean export(@android.support.annotation.Nullable final java.io.Writer writer)
    {
        boolean success;
        if (null == writer)
            success = false;
        else
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
                    final java.lang.String indent = "\n    ";
                    if (!this.export(xmlSerializer, indent))
                        success = false;
                    else
                    {
                        if (null != this.nonNullOptionalFieldsInstance)
                            org.wheatgenetics.coordinate.model.DisplayTemplateModel.writeElement(
                                xmlSerializer, indent, org.wheatgenetics.coordinate.model
                                    .TemplateModel.OPTIONAL_FIELDS_TAG_NAME,
                                this.nonNullOptionalFieldsInstance.toJson());

                        xmlSerializer.ignorableWhitespace("\n");
                        xmlSerializer.endTag(null, templateTagName);

                        success = true;
                    }
                }
                finally { xmlSerializer.endDocument() /* throws java.io.IOException */; }
            }
            catch (final java.io.IOException e) { success = false; }
        return success;
    }

    boolean export(@android.support.annotation.Nullable final java.io.File exportFile)
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
    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public boolean optionalFieldsIsEmpty()
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.nonNullOptionalFieldsInstance ?
            true : this.nonNullOptionalFieldsInstance.isEmpty();
    }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsClone()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.nonNullOptionalFieldsInstance.clone();
    }

    @android.support.annotation.Nullable public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }
    // endregion

    // region Make Public Methods
    /** Called by TemplateModels. */
    @android.support.annotation.NonNull
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

    /** Called by TemplateModels. */
    @android.support.annotation.NonNull
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

    /** Called by TemplateCreator. */
    @android.support.annotation.NonNull
    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew());
    }

    @android.support.annotation.Nullable public static
    org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined(final java.io.File file)
    {
        if (null == file)
            return null;
        else
        {
            class DefaultHandler extends org.xml.sax.helpers.DefaultHandler
            {
                // region Fields
                @android.support.annotation.Nullable private java.lang.String elementName;

                private org.wheatgenetics.coordinate.model.TemplateModel
                    templateModelInstance = null;                                       // lazy load
                // endregion

                @android.support.annotation.NonNull
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

                @android.support.annotation.Nullable
                private org.wheatgenetics.coordinate.model.TemplateModel getTemplateModel()
                { return this.templateModelInstance; }
            }

            final DefaultHandler defaultHandler = new DefaultHandler();
            try
            {
                final java.io.FileInputStream fileInputStream =
                    new java.io.FileInputStream(file);       // throws java.io.FileNotFoundException
                // noinspection TryFinallyCanBeTryWithResources
                try
                {
                    try
                    {
                        final javax.xml.parsers.SAXParser saxParser =
                            org.wheatgenetics.coordinate.model.TemplateModel.saxParser();
                        if (null == saxParser)
                            return null;
                        else
                            saxParser.parse(                         // throws org.xml.sax.SAXExcep-
                                fileInputStream, defaultHandler);    //  tion, java.io.IOException
                    }
                    catch (final org.xml.sax.SAXException e) { return null; }
                }
                finally { fileInputStream.close() /* throws java.io.IOException */; }
            }
            catch (final java.io.IOException e) { /* Don't close fileInputStream. */ }

            return defaultHandler.getTemplateModel();
        }
    }
    // endregion
    // endregion
}