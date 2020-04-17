package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.os.Bundle
 * android.util.Xml
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.VisibleForTesting
 *
 * org.xmlpull.v1.XmlSerializer
 * org.xml.sax.Attributes
 * org.xml.sax.InputSource
 * org.xml.sax.SAXException
 * org.xml.sax.helpers.DefaultHandler
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
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
    // region Constants
    private static final java.lang.String OPTIONAL_FIELDS_TAG_NAME  = "optionalFields";
    private static final java.lang.String TEMPLATE_MODEL_EXTRA_NAME = "templateModel" ;
    // endregion

    // region Fields
    @androidx.annotation.Nullable private
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
        nonNullOptionalFieldsInstance;

    private static javax.xml.parsers.SAXParserFactory saxParserFactoryInstance = null;  // lazy load
    private static javax.xml.parsers.SAXParser        saxParserInstance        = null;  // lazy load
    // endregion

    // region Private Methods
    private void setOptionalFields(@androidx.annotation.Nullable java.lang.String json)
    {
        if (null != json) json = json.trim();
        this.nonNullOptionalFieldsInstance = null == json ? null : json.equals("") ? null :
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(
                json, this.stringGetter());
    }

    @androidx.annotation.NonNull
    private static javax.xml.parsers.SAXParserFactory saxParserFactory()
    {
        if (null == org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance)
            org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance =
                javax.xml.parsers.SAXParserFactory.newInstance();
        return org.wheatgenetics.coordinate.model.TemplateModel.saxParserFactoryInstance;
    }

    @androidx.annotation.Nullable private static javax.xml.parsers.SAXParser saxParser()
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

    @androidx.annotation.Nullable private static org.wheatgenetics.coordinate.model.TemplateModel
    makeUserDefined(@androidx.annotation.NonNull final org.xml.sax.InputSource inputSource,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        class DefaultHandler extends org.xml.sax.helpers.DefaultHandler
        {
            // region Fields
            @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.StringGetter
                stringGetter;

            @androidx.annotation.Nullable private java.lang.String elementName;

            private org.wheatgenetics.coordinate.model.TemplateModel
                templateModelInstance = null;                                       // lazy load
            // endregion

            @androidx.annotation.NonNull
            private org.wheatgenetics.coordinate.model.TemplateModel templateModel()
            {
                if (null == this.templateModelInstance) this.templateModelInstance =
                    new org.wheatgenetics.coordinate.model.TemplateModel(this.stringGetter);
                return templateModelInstance;
            }

            private DefaultHandler(@androidx.annotation.NonNull final
            org.wheatgenetics.coordinate.StringGetter stringGetter)
            { super(); this.stringGetter = stringGetter; }

            // region Overridden Methods
            @java.lang.Override
            public void startElement(final java.lang.String uri,
                final java.lang.String localName, final java.lang.String qName,
                final org.xml.sax.Attributes attributes)
            { this.elementName = qName; }

            @java.lang.Override
            public void characters(
                @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) final char ch[],
                final int start, final int length)
            {
                this.templateModel().assignCharacterData(this.elementName,
                    /* characterData => */ new java.lang.String(ch, start, length));
            }

            @java.lang.Override
            public void endElement(final java.lang.String uri,
                final java.lang.String localName, final java.lang.String qName)
            { this.elementName = null; }
            // endregion

            @androidx.annotation.Nullable
            private org.wheatgenetics.coordinate.model.TemplateModel getTemplateModel()
            { return this.templateModelInstance; }
        }

        final DefaultHandler defaultHandler = new DefaultHandler(stringGetter);
        {
            final javax.xml.parsers.SAXParser saxParser =
                org.wheatgenetics.coordinate.model.TemplateModel.saxParser();
            if (null == saxParser)
                return null;
            else
                try
                {
                    saxParser.parse(inputSource,                     // throws org.xml.sax.SAXExcep-
                        defaultHandler);                             //  tion, java.io.IOException
                }
                catch (final org.xml.sax.SAXException | java.io.IOException e) { return null; }
        }

        return defaultHandler.getTemplateModel();
    }
    // endregion

    // region Constructors
    /** Called by clone(). */
    private TemplateModel(@androidx.annotation.IntRange(from = 1) final long id,
    final java.lang.String title, final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering, @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, timestamp, stringGetter);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by clone() and third constructor. */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int rows                        ,
    @androidx.annotation.IntRange(from = 1) final int cols                        ,
    @androidx.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Cells     excludedCells,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedRows ,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.RowOrCols excludedCols ,
    final boolean colNumbering, final boolean rowNumbering, @androidx.annotation.Nullable final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter,
    @androidx.annotation.IntRange(from = 0) final long timestamp)
    {
        super(title, type, rows, cols, generatedExcludedCellsAmount, excludedCells, excludedRows,
            excludedCols, colNumbering, rowNumbering, timestamp, stringGetter);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by makeSeedDefault() and makeDNADefault(). */
    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type,
    @androidx.annotation.IntRange(from = 1) final int     rows                        ,
    @androidx.annotation.IntRange(from = 1) final int     cols                        ,
    @androidx.annotation.IntRange(from = 0) final int     generatedExcludedCellsAmount,
                                            final boolean rowNumbering                ,
    @androidx.annotation.Nullable           final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
            /* excludedCells => */null, /* excludedRows => */null,
            /* excludedCols  => */null, /* colNumbering  => */true,
            rowNumbering, optionalFields, stringGetter, /* timestamp => */0);
    }

    /** Called by makeUserDefined(). */
    private TemplateModel(@androidx.annotation.Nullable final
    org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        super(stringGetter);

        this.setGeneratedExcludedCellsAmount(1);
        this.setColNumbering(true); this.setRowNumbering(false);

        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /** Called by DefaultHandler class. */ private TemplateModel(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.StringGetter stringGetter) { super(stringGetter); }

    /** Called by JoinedGridModel constructor and TemplatesTable.make(). */
    public TemplateModel(
    @androidx.annotation.IntRange(from = 1        ) final long             id                   ,
                                                    final java.lang.String title                ,
    @androidx.annotation.IntRange(from = 0, to = 2) final int              code                 ,
    @androidx.annotation.IntRange(from = 1        ) final int              rows                 ,
    @androidx.annotation.IntRange(from = 1        ) final int              cols                 ,
    @androidx.annotation.IntRange(from = 0        ) final int       generatedExcludedCellsAmount,
    @androidx.annotation.Nullable                   final java.lang.String       excludedCells  ,
    @androidx.annotation.Nullable                   final java.lang.String       excludedRows   ,
    @androidx.annotation.Nullable                   final java.lang.String       excludedCols   ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int                    colNumbering   ,
    @androidx.annotation.IntRange(from = 0, to = 1) final int                    rowNumbering   ,
                                                    final java.lang.String       entryLabel     ,
    @androidx.annotation.Nullable                   final java.lang.String       optionalFields ,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter   ,
    @androidx.annotation.IntRange(from = 0        ) final long                   timestamp      )
    {
        super(id, title, code, rows, cols, generatedExcludedCellsAmount, excludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel, timestamp,
            stringGetter);
        this.setOptionalFields(optionalFields);
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
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
    @java.lang.Override @androidx.annotation.NonNull protected java.lang.Object clone()
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
                /* title                        => */ this.getTitle                       (),
                /* type                         => */ this.getType                        (),
                /* rows                         => */ this.getRows                        (),
                /* cols                         => */ this.getCols                        (),
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* stringGetter                 => */ this.stringGetter()                   ,
                /* timestamp                    => */ this.getTimestamp()                   );
        else
            return new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id                           => */ id                                    ,
                /* title                        => */ this.getTitle                       (),
                /* type                         => */ this.getType                        (),
                /* rows                         => */ this.getRows                        (),
                /* cols                         => */ this.getCols                        (),
                /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                /* excludedCells                => */ excludedCells                         ,
                /* excludedRows                 => */ excludedRows                          ,
                /* excludedCols                 => */ excludedCols                          ,
                /* colNumbering                 => */ this.getColNumbering()                ,
                /* rowNumbering                 => */ this.getRowNumbering()                ,
                /* optionalFields               => */ optionalFields                        ,
                /* stringGetter                 => */ this.stringGetter()                   ,
                /* timestamp                    => */ this.getTimestamp()                   );
    }

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @java.lang.Override void assignCharacterData(
    @androidx.annotation.Nullable final java.lang.String elementName  ,
                                  final java.lang.String characterData)
    {
        if (org.wheatgenetics.coordinate.model.TemplateModel.OPTIONAL_FIELDS_TAG_NAME.equals(
        elementName))
            this.setOptionalFields(characterData);
        else
            super.assignCharacterData(elementName, characterData);
    }
    // endregion

    @java.lang.SuppressWarnings({"DefaultAnnotationParam"}) @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    boolean export(@androidx.annotation.Nullable final java.io.Writer writer)
    {
        boolean success;

        if (null == writer)
            success = false;
        else
            try
            {
                final org.xmlpull.v1.XmlSerializer xmlSerializer = android.util.Xml.newSerializer();
                if (null == xmlSerializer)
                    success = false;
                else
                {
                    xmlSerializer.setOutput(writer);                   // throws java.io.IOException

                    xmlSerializer.startDocument("UTF-8",true);       // throws java.io-
                    try                                                           //  .IOException
                    {
                        xmlSerializer.ignorableWhitespace("\n");

                        final java.lang.String templateTagName = "template";
                        xmlSerializer.startTag(null, templateTagName);         // throws java.io-
                                                                                  //  .IOException
                        final java.lang.String indent = "\n    ";
                        if (!this.export(xmlSerializer, indent))
                            success = false;
                        else
                        {
                            if (null != this.nonNullOptionalFieldsInstance) org.wheatgenetics
                                .coordinate.model.DisplayTemplateModel.writeElement(
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
            }
            catch (final java.io.IOException e) { success = false; }

        return success;
    }

    // region Public Methods
    // region optionalFields Public Methods
    @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields()
    { return this.nonNullOptionalFieldsInstance; }

    public boolean optionalFieldsIsEmpty()
    {
        // noinspection SimplifiableConditionalExpression
        return null == this.nonNullOptionalFieldsInstance ?
            true : this.nonNullOptionalFieldsInstance.isEmpty();
    }

    @androidx.annotation.Nullable
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFieldsClone()
    {
        return null == this.nonNullOptionalFieldsInstance ? null :
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.nonNullOptionalFieldsInstance.clone();
    }

    @androidx.annotation.Nullable public java.lang.String optionalFieldsAsJson()
    {
        return null == this.nonNullOptionalFieldsInstance ?
            null : this.nonNullOptionalFieldsInstance.toJson();
    }
    // endregion

    // region export() Public Methods
    public boolean export(@androidx.annotation.Nullable final java.io.File exportFile)
    {
        final boolean failure = false;
        if (null == exportFile)
            return failure;
        else
            try { return this.export(new java.io.FileWriter(exportFile) /* throws */); }
            catch (final java.io.IOException e) { return failure; }
    }

    public void export(@androidx.annotation.NonNull final android.os.Bundle bundle)
    {
                                      final boolean          remove;
        @androidx.annotation.Nullable final java.lang.String string;
        {
            final java.io.StringWriter stringWriter = new java.io.StringWriter();
            if (this.export(stringWriter))
            {
                string = stringWriter.toString().trim();
                remove = string.length() < 1;
            }
            else
                { remove = true; string = null; }
        }

        final java.lang.String TEMPLATE_MODEL_EXTRA_NAME =
            org.wheatgenetics.coordinate.model.TemplateModel.TEMPLATE_MODEL_EXTRA_NAME;
        if (remove)
            bundle.remove(TEMPLATE_MODEL_EXTRA_NAME);
        else
            bundle.putString(TEMPLATE_MODEL_EXTRA_NAME, string);
    }
    // endregion

    // region Make Public Methods
    /** Called by TemplateModels. */ @androidx.annotation.NonNull
    static org.wheatgenetics.coordinate.model.TemplateModel makeSeedDefault(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title => */stringGetter.get(
                    org.wheatgenetics.coordinate.R.string.SeedDefaultTemplateTitle),
                /* type => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows => */6,
                /* cols => */20,
                /* generatedExcludedCellsAmount => */0,
                /* rowNumbering                 => */true,
                /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFields.makeSeedDefault(stringGetter),
                /* stringGetter => */ stringGetter);
        result.addExcludedRow(2); result.addExcludedRow(5);
        return result;
    }

    /** Called by TemplateModels. */ @androidx.annotation.NonNull
    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title => */ stringGetter.get(
                org.wheatgenetics.coordinate.R.string.DNADefaultTemplateTitle),
            /* type => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows => */8,
            /* cols => */12,
            /* generatedExcludedCellsAmount => */1,
            /* rowNumbering                 => */false,
            /* optionalFields               => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFields.makeDNADefault(stringGetter),
            /* stringGetter => */ stringGetter);
    }

    /** Called by TemplateCreator. */ @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew(stringGetter),
            stringGetter                                                                          );
    }

    @androidx.annotation.Nullable public static
    org.wheatgenetics.coordinate.model.TemplateModel makeUserDefined(final java.io.File file,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        if (null == file)
            return null;
        else
            try
            {
                final java.io.FileInputStream fileInputStream =
                    new java.io.FileInputStream(file);       // throws java.io.FileNotFoundException

                // noinspection TryFinallyCanBeTryWithResources
                try
                {
                    return org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(
                        new org.xml.sax.InputSource(fileInputStream), stringGetter);
                }
                finally { fileInputStream.close() /* throws java.io.IOException */; }
            }
            catch (final java.io.IOException e) { return null; }
    }

    @androidx.annotation.Nullable public static org.wheatgenetics.coordinate.model.TemplateModel
    makeUserDefined(@androidx.annotation.Nullable final android.os.Bundle bundle,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        if (null == bundle)
            return null;
        else
        {
            final java.lang.String TEMPLATE_MODEL_EXTRA_NAME =
                org.wheatgenetics.coordinate.model.TemplateModel.TEMPLATE_MODEL_EXTRA_NAME;
            if (bundle.containsKey(TEMPLATE_MODEL_EXTRA_NAME))
            {
                @androidx.annotation.Nullable
                java.lang.String string = bundle.getString(TEMPLATE_MODEL_EXTRA_NAME);
                if (null == string)
                    return null;
                {
                    string = string.trim();
                    if (string.length() < 1)
                        return null;
                    else
                        return org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(
                            new org.xml.sax.InputSource(new java.io.StringReader(string)),
                            stringGetter                                                 );
                }
            }
            else return null;
        }
    }
    // endregion
    // endregion
}