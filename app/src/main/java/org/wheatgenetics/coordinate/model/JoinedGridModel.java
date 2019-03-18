package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.RestrictTo
 * android.support.annotation.RestrictTo.Scope
 *
 * org.wheatgenetics.javalib.CsvWriter
 *
 * org.wheatgenetics.coordinate.Utils
 * org.wheatgenetics.coordinate.Utils.Advancement
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
 * org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange
 * org.wheatgenetics.coordinate.model.DisplayModel
 * org.wheatgenetics.coordinate.model.ElementModel
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.FilledHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
implements org.wheatgenetics.coordinate.model.DisplayModel
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Helper
    {
        public abstract void publishProgress(
            @android.support.annotation.IntRange(from = 1) int col);
    }

    // region Fields
    @android.support.annotation.NonNull
        private final org.wheatgenetics.coordinate.model.TemplateModel templateModel;
    private org.wheatgenetics.coordinate.model.EntryModels entryModels = null;
    // endregion

    // region Private Methods
    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.model.EntryModel getEntryModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.entryModels ? null : this.entryModels.get(row, col); }

    // region export*() Private Methods
    private void exportSeed(
    @android.support.annotation.NonNull  final org.wheatgenetics.javalib.CsvWriter csvWriter     ,
                                         final java.lang.String                    exportFileName,
    @android.support.annotation.Nullable final
        org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper,
    final boolean includeHeader) throws java.io.IOException
    {
        final java.lang.String tray_id, person, date;
        {
            final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
                this.optionalFields();
            if (null == optionalFields)
                tray_id = person = date = null;
            else
            {
                final java.lang.String values[] = optionalFields.values(
                    /* names[] => */ new java.lang.String[]{"Tray", "Person", "date"});
                if (null == values)
                    tray_id = person = date = null;
                else
                    { tray_id = values[0]; person = values[1]; date = values[2]; }
            }
        }

        if (includeHeader) csvWriter.writeRecord(new java.lang.String[]{"tray_id", "cell_id",
            "tray_num", "tray_column", "tray_row", "seed_id", "person", "date"});
        {
            @android.support.annotation.IntRange(from = 1) final int
                cols = this.getCols(), rows = this.getRows();

            for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
            {
                for (@android.support.annotation.IntRange(from = 1) int row = 1; row <= rows; row++)
                {
                    final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                        this.getEntryModel(row, col);
                    if (null != entryModel)
                    {
                        csvWriter.write(tray_id                                 );    // tray id
                        csvWriter.write("%s_C%02d_R%d", exportFileName, col, row);    // cell_id
                        csvWriter.write(                                        );    // tray_num
                        csvWriter.write(col                                     );    // tray_column
                        csvWriter.write(row                                     );    // tray_row
                        csvWriter.write(entryModel.getSeedExportValue()         );    // seed_id
                        csvWriter.write(person                                  );    // person
                        csvWriter.write(date                                    );    // date

                        csvWriter.endRecord();
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    @java.lang.SuppressWarnings({"DefaultLocale"}) private void exportDNA(
    @android.support.annotation.NonNull  final org.wheatgenetics.javalib.CsvWriter csvWriter,
    @android.support.annotation.Nullable final
        org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper,
    final boolean includeHeader) throws java.io.IOException
    {
        final java.lang.String date, plate_id, plate_name,
            dna_person, notes, tissue_type, extraction;
        {
            final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
                this.optionalFields();
            if (null == optionalFields)
                date = plate_id = plate_name = dna_person = notes = tissue_type = extraction = null;
            else
            {
                final java.lang.String values[] = optionalFields.values(
                    /* names[] => */ new java.lang.String[]{"date", "Plate",
                        "Plate Name", "person", "Notes", "tissue_type", "extraction"});
                if (null == values)
                    date = plate_id = plate_name = dna_person =
                        notes = tissue_type = extraction = null;
                else
                {
                    date       = values[0]; plate_id = values[1]; plate_name  = values[2];
                    dna_person = values[3]; notes    = values[4]; tissue_type = values[5];
                    extraction = values[6];
                }
            }
        }

        if (includeHeader) csvWriter.writeRecord(new java.lang.String[]{
            "date", "plate_id", "plate_name", "sample_id", "well_A01", "well_01A",
            "tissue_id", "dna_person", "notes", "tissue_type", "extraction"});
        {
            @android.support.annotation.IntRange(from = 1) final int
                cols = this.getCols(), rows = this.getRows();

            for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
            {
                for (@android.support.annotation.IntRange(from = 0) int r = 0; r < rows; r++)
                {
                    @android.support.annotation.IntRange(from = 1) final int row = r + 1;
                    final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                        this.getEntryModel(row, col);
                    if (null != entryModel)
                    {
                        csvWriter.write(date      );
                        csvWriter.write(plate_id  );
                        csvWriter.write(plate_name);
                        {
                            final java.lang.String sample_id;
                            {
                                final java.lang.String rowName =
                                    org.wheatgenetics.coordinate.Utils.convert(r);
                                final java.lang.String colName =
                                    java.lang.String.format("%02d", col);

                                sample_id = java.lang.String.format(
                                    "%s_%s%s", plate_id, rowName, colName);
                                csvWriter.write(sample_id               );              // sample_id
                                csvWriter.write("%s%s", rowName, colName);              // well_A01
                                csvWriter.write("%s%s", colName, rowName);              // well_01A
                            }
                            csvWriter.write(entryModel.getDNAExportValue(sample_id));   // tissue_id
                        }
                        csvWriter.write(dna_person ); csvWriter.write(notes     );
                        csvWriter.write(tissue_type); csvWriter.write(extraction);

                        csvWriter.endRecord();
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    private void exportUserDefined(
    @android.support.annotation.NonNull  final org.wheatgenetics.javalib.CsvWriter csvWriter,
    @android.support.annotation.Nullable final
        org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper,
    final boolean includeHeader) throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields =
            this.optionalFields();
        if (includeHeader)
        {
            csvWriter.write(this.templateModel.entryLabelIsNotNull() ?
                this.templateModel.getEntryLabel() : "Value");
            csvWriter.write("Column"); csvWriter.write("Row");

            if (null != optionalFields)
            {
                final java.lang.String names[] = optionalFields.names();
                for (final java.lang.String name: names) csvWriter.write(name);
            }
            csvWriter.endRecord();
        }

        {
            @android.support.annotation.IntRange(from = 1) final int
                cols = this.getCols(), rows = this.getRows();
            final java.lang.String values[] =
                null == optionalFields ? null : optionalFields.values();

            final boolean
                colNumbering = this.templateModel.getColNumbering(),
                rowNumbering = this.templateModel.getRowNumbering();
            for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
            {
                for (@android.support.annotation.IntRange(from = 1) int row = 1; row <= rows; row++)
                {
                    final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                        this.getEntryModel(row, col);
                    if (null != entryModel)
                    {
                        csvWriter.write(entryModel.getUserDefinedExportValue());

                        if (colNumbering)
                            csvWriter.write(col);
                        else
                            csvWriter.write(
                                org.wheatgenetics.coordinate.Utils.convert(col - 1));

                        if (rowNumbering)
                            csvWriter.write(row);
                        else
                            csvWriter.write(
                                org.wheatgenetics.coordinate.Utils.convert(row - 1));

                        if (null != values)
                            for (final java.lang.String value: values) csvWriter.write(value);

                        csvWriter.endRecord();
                    }
                }
                if (null != helper) helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }
    // endregion

    private boolean isExcludedRow(@android.support.annotation.IntRange(from = 1) final int row)
    { return this.templateModel.isExcludedRow(row); }

    private boolean isExcludedCol(@android.support.annotation.IntRange(from = 1) final int col)
    { return this.templateModel.isExcludedCol(col); }

    private void excludedCellsFromExcludedRowsAndCols(
    @android.support.annotation.NonNull final org.wheatgenetics.coordinate.model.Cells result)
    {
        @android.support.annotation.IntRange(from = 1) final int
            rows = this.getRows(), cols = this.getCols();

        for (@android.support.annotation.IntRange(from = 1) int row = 1; row <= rows; row++)
            if (this.isExcludedRow(row))
                for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
                    result.add(row, col);

        for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
            if (this.isExcludedCol(col))
                for (@android.support.annotation.IntRange(from = 1) int row = 1; row <= rows; row++)
                    result.add(row, col);
    }

    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.model.Cells initialExcludedCells()
    { return this.templateModel.getExcludedCells(); }

    @android.support.annotation.NonNull
    private org.wheatgenetics.coordinate.model.Cells excludedCellsFromTemplate()
    {
        final org.wheatgenetics.coordinate.model.Cells result;

        {
            final org.wheatgenetics.coordinate.model.Cells initialExcludedCells =
                this.initialExcludedCells();
            result = null == initialExcludedCells ?
                new org.wheatgenetics.coordinate.model.Cells(this.getRows(), this.getCols()) :
                (org.wheatgenetics.coordinate.model.Cells) initialExcludedCells.clone()      ;
        }
        this.excludedCellsFromExcludedRowsAndCols(result);

        return result;
    }

    private void makeEntryModelsFromExcludedCells(
    final org.wheatgenetics.coordinate.model.Cells excludedCells)
    {
        @android.support.annotation.IntRange(from = 1) final int
            rows = this.getRows(), cols = this.getCols();
        this.entryModels = this.makeEntryModels(rows, cols);
        for (@android.support.annotation.IntRange(from = 1) int row = 1; row <= rows; row++)
            for (@android.support.annotation.IntRange(from = 1) int col = 1; col <= cols; col++)
                if (null == excludedCells)
                    this.entryModels.makeIncludedEntry(row, col);
                else
                    if (excludedCells.contains(row, col))
                        this.entryModels.makeExcludedEntry(row, col);
                    else
                        this.entryModels.makeIncludedEntry(row, col);
    }

    @android.support.annotation.Nullable
    private org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel                activeEntryModel,
    final org.wheatgenetics.coordinate.Utils.Advancement               advancement     ,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler   )
    {
        return null == this.entryModels ? null : this.entryModels.next(
            activeEntryModel, advancement, filledHandler);
    }

    private boolean setActiveRowAndActiveCol(@android.support.annotation.NonNull
    final org.wheatgenetics.coordinate.model.EntryModel nextEntryModel)
    {
        return this.setActiveRowAndActiveCol(
            nextEntryModel.getRow() - 1,nextEntryModel.getCol() - 1);
    }
    // endregion

    // region Package Methods
    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.EntryModels makeEntryModels(
    @android.support.annotation.IntRange(from = 1) final int rows,
    @android.support.annotation.IntRange(from = 1) final int cols)
    {
        return new org.wheatgenetics.coordinate.model.EntryModels(
            /* gridId => */ this.getId(), /* rows => */ rows, /* cols => */ cols);
    }

    @android.support.annotation.RestrictTo(android.support.annotation.RestrictTo.Scope.SUBCLASSES)
    void uncheckedSetEntryModel(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { if (null != this.entryModels) this.entryModels.set(entryModel); }
    // endregion

    // region Constructors
    /** Used by GridCreator. */
    public JoinedGridModel(
    @android.support.annotation.IntRange(from = 0) final long             projectId,
                                                   final java.lang.String person   ,
    @android.support.annotation.Nullable           final
        org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    @android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel)
    {
        super(
            /* templateId     => */ templateModel.getId(),
            /* projectId      => */ projectId            ,
            /* person         => */ person               ,
            /* optionalFields => */ optionalFields       );
        this.templateModel = templateModel;
    }

    /** Used by GridsTable. */
    public JoinedGridModel(
    @android.support.annotation.IntRange(from = 1) final long             id            ,
    @android.support.annotation.IntRange(from = 0) final long             projectId     ,
                                                   final java.lang.String person        ,
    @android.support.annotation.IntRange(from = 0) final int              activeRow     ,
    @android.support.annotation.IntRange(from = 0) final int              activeCol     ,
    @android.support.annotation.Nullable           final java.lang.String optionalFields,
    @android.support.annotation.IntRange(from = 0) final long             timestamp     ,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    @android.support.annotation.Nullable final java.lang.String initialExcludedCells,
    @android.support.annotation.Nullable final java.lang.String excludedRows        ,
    @android.support.annotation.Nullable final java.lang.String excludedCols        ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      colNumbering          ,
    @android.support.annotation.IntRange(from = 0, to = 1) final int      rowNumbering          ,
                                                   final java.lang.String entryLabel            ,
    @android.support.annotation.Nullable           final java.lang.String templateOptionalFields,
    @android.support.annotation.IntRange(from = 0) final long             templateTimestamp     ,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        super(id, templateId, projectId, person, activeRow, activeCol, optionalFields, timestamp);
        this.templateModel = new org.wheatgenetics.coordinate.model.TemplateModel(templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, entryLabel,
            templateOptionalFields, templateTimestamp);
        this.entryModels = entryModels;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.DisplayModel Overridden Methods
    @android.support.annotation.IntRange(from = 1) @java.lang.Override public int getRows()
    { return this.templateModel.getRows(); }

    @android.support.annotation.IntRange(from = 1) @java.lang.Override public int getCols()
    { return this.templateModel.getCols(); }

    @java.lang.Override public boolean getColNumbering()
    { return this.templateModel.getColNumbering(); }

    @java.lang.Override public boolean getRowNumbering()
    { return this.templateModel.getRowNumbering(); }

    @java.lang.Override public org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @android.support.annotation.IntRange(from = 1) int row,
    @android.support.annotation.IntRange(from = 1) int col) { return this.getEntryModel(row, col); }
    // endregion

    // region Package Methods
    @android.support.annotation.NonNull @java.lang.SuppressWarnings({"DefaultLocale"})
    java.lang.String name()
    {
        return java.lang.String.format("Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getPerson(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    @android.support.annotation.NonNull
    org.wheatgenetics.coordinate.model.Cells excludedCellsFromEntries()
    {
        final org.wheatgenetics.coordinate.model.Cells result = null == this.entryModels ?
            new org.wheatgenetics.coordinate.model.Cells(this.getRows(), this.getCols()) :
            this.entryModels.excludedCells()                                             ;
        this.excludedCellsFromExcludedRowsAndCols(result);
        return result;
    }

    void export(final java.io.Writer writer, final java.lang.String exportFileName,
    @android.support.annotation.Nullable
        final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper,
    final boolean includeHeader) throws java.io.IOException
    {
        final org.wheatgenetics.coordinate.model.TemplateType templateType =
            this.templateModel.getType();
        final org.wheatgenetics.javalib.CsvWriter csvWriter =
            new org.wheatgenetics.javalib.CsvWriter(writer);
        if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
            this.exportSeed(csvWriter, exportFileName, helper, includeHeader);    // throws java.io-
        else                                                                      //  .IOException
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                this.exportDNA(csvWriter, helper, includeHeader);                 // throws java.io-
            else                                                                  //  .IOException
                this.exportUserDefined(csvWriter, helper, includeHeader);         // throws java.io-
    }                                                                             //  .IOException

    boolean export(final java.io.File exportFile, final java.lang.String exportFileName,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        final boolean success;
        if (null == exportFile || null == helper)
            success = false;
        else
        {
            this.export(                                               // throws java.io.IOException
                new java.io.FileWriter(exportFile) /* throws java.io.IOException */,
                exportFileName, helper, /* includeHeader => */true);
            success = true;
        }
        return success;
    }
    // endregion

    // region Public Methods
    public java.lang.String getTemplateTitle() { return this.templateModel.getTitle(); }

    public void makeEntryModels() throws
    org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange,
    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
    {
        final org.wheatgenetics.coordinate.model.Cells excludedCells =
            this.excludedCellsFromTemplate();

        excludedCells.makeRandomCells(       // throws MaxRowAndOrMaxColOutOfRange, AmountIsTooLarge
            /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
            /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void makeEntryModels(@android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.Cells projectExcludedCells) throws
    org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange,
    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
    {
        org.wheatgenetics.coordinate.model.Cells excludedCells = this.excludedCellsFromTemplate();

        {
            final org.wheatgenetics.coordinate.model.Cells randomCells =
                projectExcludedCells.makeRandomCells(                                      // throws
                    /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
                    /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());
            if (null != randomCells)
                if (excludedCells.size() <= 0)
                    excludedCells = randomCells;
                else
                    excludedCells.accumulate(randomCells);
        }

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void setEntryModel(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { this.uncheckedSetEntryModel(entryModel); }

    @android.support.annotation.Nullable
    public org.wheatgenetics.coordinate.model.EntryModel getActiveEntryModel()
    { return this.getEntryModel(this.getActiveRow() + 1,this.getActiveCol() + 1); }

    public boolean setActiveRowAndActiveCol(
    @android.support.annotation.IntRange(from = 0) final int row,
    @android.support.annotation.IntRange(from = 0) final int col)
    {
        final boolean changed;

        if (this.getActiveRow() != row || this.getActiveCol() != col)
            { this.setActiveRow(row); this.setActiveCol(col); changed = true; }
        else
            changed = false;

        return changed;
    }

    @android.support.annotation.Nullable public org.wheatgenetics.coordinate.model.EntryModels
    getEntryModels() { return this.entryModels; }

    public boolean goToNext(
    final org.wheatgenetics.coordinate.model.EntryModel                entryModel   ,
    final org.wheatgenetics.coordinate.Utils.Advancement               advancement  ,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel nextIncludedEntryModel =
            this.next(entryModel, advancement, filledHandler);
        // noinspection SimplifiableConditionalExpression
        return null == nextIncludedEntryModel ? false :
            this.setActiveRowAndActiveCol(nextIncludedEntryModel);
    }

    public boolean activeRowAndOrActiveColWasAdjusted(
    final org.wheatgenetics.coordinate.Utils.Advancement advancement)
    {
        final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
            this.getActiveEntryModel();
        // noinspection SimplifiableConditionalExpression
        return activeEntryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel ?
            this.goToNext(activeEntryModel, advancement,null) : false;
    }
    // endregion
}