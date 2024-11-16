package org.wheatgenetics.coordinate.model;

import android.os.Bundle;
import android.util.Xml;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class TemplateModel extends DisplayTemplateModel {
    // region Constants
    private static final String OPTIONAL_FIELDS_TAG_NAME = "optionalFields";
    private static final String TEMPLATE_MODEL_EXTRA_NAME = "templateModel";
    // endregion
    private static SAXParserFactory saxParserFactoryInstance = null;  // lazy load
    private static SAXParser saxParserInstance = null;  // lazy load
    // region Fields
    @Nullable
    private
    NonNullOptionalFields
            nonNullOptionalFieldsInstance;
    // endregion

    /**
     * Called by clone().
     */
    private TemplateModel(@IntRange(from = 1) final long id,
                          final String title, final TemplateType type,
                          @IntRange(from = 1) final int rows,
                          @IntRange(from = 1) final int cols,
                          @IntRange(from = 0) final int generatedExcludedCellsAmount,
                          @Nullable final Cells excludedCells,
                          @Nullable final RowOrCols excludedRows,
                          @Nullable final RowOrCols excludedCols,
                          final boolean colNumbering, final boolean rowNumbering, @Nullable final
                          NonNullOptionalFields optionalFields,
                          @NonNull final StringGetter stringGetter,
                          @IntRange(from = 0) final long timestamp) {
        super(id, title, type, rows, cols, generatedExcludedCellsAmount, excludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, timestamp, stringGetter);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /**
     * Called by clone() and third constructor.
     */
    private TemplateModel(final String title,
                          final TemplateType type,
                          @IntRange(from = 1) final int rows,
                          @IntRange(from = 1) final int cols,
                          @IntRange(from = 0) final int generatedExcludedCellsAmount,
                          @Nullable final Cells excludedCells,
                          @Nullable final RowOrCols excludedRows,
                          @Nullable final RowOrCols excludedCols,
                          final boolean colNumbering, final boolean rowNumbering, @Nullable final
                          NonNullOptionalFields optionalFields,
                          @NonNull final StringGetter stringGetter,
                          @IntRange(from = 0) final long timestamp) {
        super(title, type, rows, cols, generatedExcludedCellsAmount, excludedCells, excludedRows,
                excludedCols, colNumbering, rowNumbering, timestamp, stringGetter);
        this.nonNullOptionalFieldsInstance = optionalFields;
    }

    /**
     * Called by makeSeedDefault() and makeDNADefault().
     */
    private TemplateModel(final String title,
                          final TemplateType type,
                          @IntRange(from = 1) final int rows,
                          @IntRange(from = 1) final int cols,
                          @IntRange(from = 0) final int generatedExcludedCellsAmount,
                          final boolean rowNumbering,
                          @Nullable final
                          NonNullOptionalFields optionalFields,
                          @NonNull final StringGetter stringGetter) {
        this(title, type, rows, cols, generatedExcludedCellsAmount,
                /* excludedCells => */null, /* excludedRows => */null,
                /* excludedCols  => */null, /* colNumbering  => */true,
                rowNumbering, optionalFields, stringGetter, /* timestamp => */0);
    }

    /**
     * Called by makeUserDefined().
     */
    private TemplateModel(@Nullable final
                          NonNullOptionalFields optionalFields,
                          @NonNull final StringGetter stringGetter) {
        super(stringGetter);

        this.setGeneratedExcludedCellsAmount(0);
        this.setColNumbering(true);
        this.setRowNumbering(false);

        this.nonNullOptionalFieldsInstance = optionalFields;
    }
    // endregion

    // region Constructors

    /**
     * Called by DefaultHandler class.
     */
    private TemplateModel(@NonNull final StringGetter stringGetter) {
        super(stringGetter);
    }

    /**
     * Called by JoinedGridModel constructor and TemplatesTable.make().
     */
    public TemplateModel(
            @IntRange(from = 1) final long id,
            final String title,
            @IntRange(from = 0, to = 2) final int code,
            @IntRange(from = 1) final int rows,
            @IntRange(from = 1) final int cols,
            @IntRange(from = 0) final int generatedExcludedCellsAmount,
            @Nullable final String excludedCells,
            @Nullable final String excludedRows,
            @Nullable final String excludedCols,
            @IntRange(from = 0, to = 1) final int colNumbering,
            @IntRange(from = 0, to = 1) final int rowNumbering,
            final String entryLabel,
            @Nullable final String optionalFields,
            @NonNull final StringGetter stringGetter,
            @IntRange(from = 0) final long timestamp) {
        super(id, title, code, rows, cols, generatedExcludedCellsAmount, excludedCells,
                excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel, timestamp,
                stringGetter);
        this.setOptionalFields(optionalFields);
    }

    @NonNull
    private static SAXParserFactory saxParserFactory() {
        if (null == TemplateModel.saxParserFactoryInstance)
            TemplateModel.saxParserFactoryInstance =
                    SAXParserFactory.newInstance();
        return TemplateModel.saxParserFactoryInstance;
    }

    @Nullable
    private static SAXParser saxParser() {
        if (null == TemplateModel.saxParserInstance)
            try {
                TemplateModel.saxParserInstance =
                        TemplateModel.saxParserFactory()
                                .newSAXParser();   // throws javax.xml.parsers.ParserConfigurationException,
            }                              //   org.xml.sax.SAXException
            catch (
                    final ParserConfigurationException | SAXException e) {
                TemplateModel.saxParserInstance = null;
            }
        return TemplateModel.saxParserInstance;
    }

    @Nullable
    private static TemplateModel
    makeUserDefined(@NonNull final InputSource inputSource,
                    @NonNull final StringGetter stringGetter) {
        class DefaultHandler extends org.xml.sax.helpers.DefaultHandler {
            // region Fields
            @NonNull
            private final StringGetter
                    stringGetter;

            @Nullable
            private String elementName;

            private TemplateModel
                    templateModelInstance = null;                                       // lazy load
            // endregion

            private DefaultHandler(@NonNull final
                                   StringGetter stringGetter) {
                super();
                this.stringGetter = stringGetter;
            }

            @NonNull
            private TemplateModel templateModel() {
                if (null == this.templateModelInstance) this.templateModelInstance =
                        new TemplateModel(this.stringGetter);
                return templateModelInstance;
            }

            // region Overridden Methods
            @Override
            public void startElement(final String uri,
                                     final String localName, final String qName,
                                     final Attributes attributes) {
                this.elementName = qName;
            }

            @Override
            public void characters(
                    @SuppressWarnings({"CStyleArrayDeclaration"}) final char ch[],
                    final int start, final int length) {
                this.templateModel().assignCharacterData(this.elementName,
                        /* characterData => */ new String(ch, start, length));
            }

            @Override
            public void endElement(final String uri,
                                   final String localName, final String qName) {
                this.elementName = null;
            }
            // endregion

            @Nullable
            private TemplateModel getTemplateModel() {
                return this.templateModelInstance;
            }
        }

        final DefaultHandler defaultHandler = new DefaultHandler(stringGetter);
        {
            final SAXParser saxParser =
                    TemplateModel.saxParser();
            if (null == saxParser)
                return null;
            else
                try {
                    saxParser.parse(inputSource,                     // throws org.xml.sax.SAXExcep-
                            defaultHandler);                             //  tion, java.io.IOException
                } catch (final SAXException | IOException e) {
                    return null;
                }
        }

        return defaultHandler.getTemplateModel();
    }

    /**
     * Called by TemplateModels.
     */
    @NonNull
    static TemplateModel makeSeedDefault(
            @NonNull final StringGetter stringGetter) {
        final TemplateModel result =
                new TemplateModel(
                        /* title => */stringGetter.get(
                        R.string.SeedDefaultTemplateTitle),
                        /* type => */ TemplateType.SEED,
                        /* rows => */6,
                        /* cols => */20,
                        /* generatedExcludedCellsAmount => */0,
                        /* rowNumbering                 => */true,
                        /* optionalFields               => */ NonNullOptionalFields.makeSeedDefault(stringGetter),
                        /* stringGetter => */ stringGetter);
        result.addExcludedRow(2);
        result.addExcludedRow(5);
        return result;
    }
    // endregion

    /**
     * Called by TemplateModels.
     */
    @NonNull
    static TemplateModel makeDNADefault(
            @NonNull final StringGetter stringGetter) {
        return new TemplateModel(
                /* title => */ stringGetter.get(
                R.string.DNADefaultTemplateTitle),
                /* type => */ TemplateType.DNA,
                /* rows => */8,
                /* cols => */12,
                /* generatedExcludedCellsAmount => */1,
                /* rowNumbering                 => */false,
                /* optionalFields               => */ NonNullOptionalFields.makeDNADefault(stringGetter),
                /* stringGetter => */ stringGetter);
    }

    /**
     * Called by TemplateCreator.
     */
    @NonNull
    public static TemplateModel makeUserDefined(
            @NonNull final StringGetter stringGetter) {
        return new TemplateModel(
                NonNullOptionalFields.makeNew(stringGetter),
                stringGetter);
    }

    public void setId(int id) {
        super.setId(id);
    }

    @Nullable
    public static TemplateModel makeUserDefined(final File file,
                                                @NonNull final StringGetter stringGetter) {
        if (null == file)
            return null;
        else
            try {
                final FileInputStream fileInputStream =
                        new FileInputStream(file);       // throws java.io.FileNotFoundException

                // noinspection TryFinallyCanBeTryWithResources
                try {
                    return TemplateModel.makeUserDefined(
                            new InputSource(fileInputStream), stringGetter);
                } finally {
                    fileInputStream.close() /* throws java.io.IOException */;
                }
            } catch (final IOException e) {
                return null;
            }
    }

    public static TemplateModel makeUserDefined(final InputStream input, StringGetter stringGetter) {

        if (input == null) {

            return null;

        } else {

            TemplateModel model = TemplateModel.makeUserDefined(new InputSource(input), stringGetter);

            try {

                input.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return model;
        }
    }

    @Nullable
    public static TemplateModel
    makeUserDefined(@Nullable final Bundle bundle,
                    @NonNull final StringGetter stringGetter) {
        if (null == bundle)
            return null;
        else {
            final String TEMPLATE_MODEL_EXTRA_NAME =
                    TemplateModel.TEMPLATE_MODEL_EXTRA_NAME;
            if (bundle.containsKey(TEMPLATE_MODEL_EXTRA_NAME)) {
                @Nullable
                String string = bundle.getString(TEMPLATE_MODEL_EXTRA_NAME);
                if (null == string)
                    return null;
                {
                    string = string.trim();
                    if (string.length() < 1)
                        return null;
                    else
                        return TemplateModel.makeUserDefined(
                                new InputSource(new StringReader(string)),
                                stringGetter);
                }
            } else return null;
        }
    }
    // endregion

    // region Private Methods
    private void setOptionalFields(@Nullable String json) {
        if (null != json) json = json.trim();
        this.nonNullOptionalFieldsInstance = null == json ? null : json.equals("") ? null :
                new NonNullOptionalFields(
                        json, this.stringGetter(), false);
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return String.format(super.formatString() + ", options=%s]",
                "TemplateModel", null == this.nonNullOptionalFieldsInstance ? "" :
                        this.nonNullOptionalFieldsInstance.toString());
    }

    @Override
    public boolean equals(final Object object) {
        if (super.equals(object))
            if (object instanceof TemplateModel) {
                final TemplateModel templateModel =
                        (TemplateModel) object;

                if (null == this.nonNullOptionalFieldsInstance
                        && null != templateModel.nonNullOptionalFieldsInstance)
                    return false;
                else if (null != this.nonNullOptionalFieldsInstance
                        && null == templateModel.nonNullOptionalFieldsInstance)
                    return false;

                // noinspection SimplifiableConditionalExpression
                return null == this.nonNullOptionalFieldsInstance ? true :
                        this.nonNullOptionalFieldsInstance.equals(
                                templateModel.nonNullOptionalFieldsInstance);
            } else return false;
        else return false;
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone"})
    @Override
    @NonNull
    protected Object clone() {
        final long id = this.getId();
        final Cells excludedCells = this.excludedCellsClone();
        final RowOrCols
                excludedRows = this.excludedRowsClone(), excludedCols = this.excludedColsClone();
        final NonNullOptionalFields optionalFields =
                null == this.nonNullOptionalFieldsInstance ? null :
                        (NonNullOptionalFields)
                                this.nonNullOptionalFieldsInstance.clone();

        if (Model.illegal(id))
            return new TemplateModel(
                    /* title                        => */ this.getTitle(),
                    /* type                         => */ this.getType(),
                    /* rows                         => */ this.getRows(),
                    /* cols                         => */ this.getCols(),
                    /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                    /* excludedCells                => */ excludedCells,
                    /* excludedRows                 => */ excludedRows,
                    /* excludedCols                 => */ excludedCols,
                    /* colNumbering                 => */ this.getColNumbering(),
                    /* rowNumbering                 => */ this.getRowNumbering(),
                    /* optionalFields               => */ optionalFields,
                    /* stringGetter                 => */ this.stringGetter(),
                    /* timestamp                    => */ this.getTimestamp());
        else
            return new TemplateModel(
                    /* id                           => */ id,
                    /* title                        => */ this.getTitle(),
                    /* type                         => */ this.getType(),
                    /* rows                         => */ this.getRows(),
                    /* cols                         => */ this.getCols(),
                    /* generatedExcludedCellsAmount => */ this.getGeneratedExcludedCellsAmount(),
                    /* excludedCells                => */ excludedCells,
                    /* excludedRows                 => */ excludedRows,
                    /* excludedCols                 => */ excludedCols,
                    /* colNumbering                 => */ this.getColNumbering(),
                    /* rowNumbering                 => */ this.getRowNumbering(),
                    /* optionalFields               => */ optionalFields,
                    /* stringGetter                 => */ this.stringGetter(),
                    /* timestamp                    => */ this.getTimestamp());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Override
    void assignCharacterData(
            @Nullable final String elementName,
            final String characterData) {
        if (TemplateModel.OPTIONAL_FIELDS_TAG_NAME.equals(
                elementName))
            this.setOptionalFields(characterData);
        else
            super.assignCharacterData(elementName, characterData);
    }
    // endregion

    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    boolean export(@Nullable final Writer writer) {
        boolean success;

        if (null == writer)
            success = false;
        else
            try {
                final XmlSerializer xmlSerializer = Xml.newSerializer();
                if (null == xmlSerializer)
                    success = false;
                else {
                    xmlSerializer.setOutput(writer);                   // throws java.io.IOException

                    xmlSerializer.startDocument("UTF-8", true);       // throws java.io-
                    try                                                           //  .IOException
                    {
                        xmlSerializer.ignorableWhitespace("\n");

                        final String templateTagName = "template";
                        xmlSerializer.startTag(null, templateTagName);         // throws java.io-
                        //  .IOException
                        final String indent = "\n    ";
                        if (!this.export(xmlSerializer, indent))
                            success = false;
                        else {
                            if (null != this.nonNullOptionalFieldsInstance)
                                DisplayTemplateModel.writeElement(
                                        xmlSerializer, indent, TemplateModel.OPTIONAL_FIELDS_TAG_NAME,
                                        this.nonNullOptionalFieldsInstance.toJson());

                            xmlSerializer.ignorableWhitespace("\n");
                            xmlSerializer.endTag(null, templateTagName);

                            success = true;
                        }
                    } finally {
                        xmlSerializer.endDocument() /* throws java.io.IOException */;
                    }
                }
            } catch (final IOException e) {
                success = false;
            }

        return success;
    }

    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    boolean exportStream(@Nullable final OutputStream output) {
        boolean success;

        if (null == output)
            success = false;
        else
            try {
                final XmlSerializer xmlSerializer = Xml.newSerializer();
                if (null == xmlSerializer)
                    success = false;
                else {
                    xmlSerializer.setOutput(output, "UTF-8");                   // throws java.io.IOException

                    xmlSerializer.startDocument("UTF-8", true);       // throws java.io-
                    try                                                           //  .IOException
                    {
                        xmlSerializer.ignorableWhitespace("\n");

                        final String templateTagName = "template";
                        xmlSerializer.startTag(null, templateTagName);         // throws java.io-
                        //  .IOException
                        final String indent = "\n    ";
                        if (!this.export(xmlSerializer, indent))
                            success = false;
                        else {
                            if (null != this.nonNullOptionalFieldsInstance)
                                DisplayTemplateModel.writeElement(
                                        xmlSerializer, indent, TemplateModel.OPTIONAL_FIELDS_TAG_NAME,
                                        this.nonNullOptionalFieldsInstance.toJson());

                            xmlSerializer.ignorableWhitespace("\n");
                            xmlSerializer.endTag(null, templateTagName);

                            success = true;
                        }
                    } finally {
                        xmlSerializer.endDocument() /* throws java.io.IOException */;
                    }
                }
            } catch (final IOException e) {
                success = false;
            }

        return success;
    }

    // region Public Methods
    // region optionalFields Public Methods
    @Nullable
    public NonNullOptionalFields optionalFields() {
        return this.nonNullOptionalFieldsInstance;
    }
    // endregion

    // region Make Public Methods

    public boolean optionalFieldsIsEmpty() {
        // noinspection SimplifiableConditionalExpression
        return null == this.nonNullOptionalFieldsInstance ?
                true : this.nonNullOptionalFieldsInstance.isEmpty();
    }

    @Nullable
    public NonNullOptionalFields optionalFieldsClone() {
        return null == this.nonNullOptionalFieldsInstance ? null :
                (NonNullOptionalFields)
                        this.nonNullOptionalFieldsInstance.clone();
    }

    @Nullable
    public String optionalFieldsAsJson() {
        return null == this.nonNullOptionalFieldsInstance ?
                null : this.nonNullOptionalFieldsInstance.toJson();
    }

    // region export() Public Methods
    public boolean export(@Nullable final File exportFile) {
        final boolean failure = false;
        if (null == exportFile)
            return failure;
        else
            try {
                return this.export(new FileWriter(exportFile) /* throws */);
            } catch (final IOException e) {
                return failure;
            }
    }

    public boolean export(@Nullable final OutputStream output) {
        final boolean failure = false;
        if (null == output)
            return failure;
        else return this.exportStream(output);
    }

    public void export(@NonNull final Bundle bundle) {
        final boolean remove;
        @Nullable final String string;
        {
            final StringWriter stringWriter = new StringWriter();
            if (this.export(stringWriter)) {
                string = stringWriter.toString().trim();
                remove = string.length() < 1;
            } else {
                remove = true;
                string = null;
            }
        }

        final String TEMPLATE_MODEL_EXTRA_NAME =
                TemplateModel.TEMPLATE_MODEL_EXTRA_NAME;
        if (remove)
            bundle.remove(TEMPLATE_MODEL_EXTRA_NAME);
        else
            bundle.putString(TEMPLATE_MODEL_EXTRA_NAME, string);
    }
    // endregion
    // endregion
}