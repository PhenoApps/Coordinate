package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.javalib.CsvWriter
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
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Helper { public abstract void publishProgress(int col); }

    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel = null;
    private org.wheatgenetics.coordinate.model.EntryModels   entryModels   = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.model.EntryModel getEntryModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.entryModels ? null : this.entryModels.get(row, col); }

    // region export*() Private Methods
    private void exportSeed(final org.wheatgenetics.javalib.CsvWriter csvWriter,
    final java.lang.String                                          exportFileName,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper        )
    throws java.io.IOException
    {
        final java.lang.String tray_id, person, date;
        {
            final java.lang.String values[] = this.optionalFields().values(
                /* names[] => */ new java.lang.String[]{"Tray", "Person", "date"});
            tray_id = values[0]; person = values[1]; date = values[2];
        }

        csvWriter.writeRecord(new java.lang.String[]{"tray_id", "cell_id",
            "tray_num", "tray_column", "tray_row", "seed_id", "person", "date"});
        {
            final int cols = this.getCols(), rows = this.getRows();

            for (int col = 1; col <= cols; col++)
            {
                for (int row = 1; row <= rows; row++)
                {
                    csvWriter.write(tray_id                                 );        // tray id
                    csvWriter.write("%s_C%02d_R%d", exportFileName, col, row);        // cell_id
                    csvWriter.write(                                        );        // tray_num
                    csvWriter.write(col                                     );        // tray_column
                    csvWriter.write(row                                     );        // tray_row
                    csvWriter.write(
                        this.getEntryModel(row, col).getSeedExportValue());           // seed_id
                    csvWriter.write(person);                                          // person
                    csvWriter.write(date  );                                          // date

                    csvWriter.endRecord();
                }
                helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    @java.lang.SuppressWarnings({"DefaultLocale"})
    private void exportDNA(final org.wheatgenetics.javalib.CsvWriter csvWriter,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        final java.lang.String date, plate_id, plate_name,
            dna_person, notes, tissue_type, extraction;
        {
            final java.lang.String values[] = this.optionalFields().values(
                /* names[] => */ new java.lang.String[]{"date", "Plate",
                    "Plate Name", "person", "Notes", "tissue_type", "extraction"});
            date       = values[0]; plate_id = values[1]; plate_name  = values[2];
            dna_person = values[3]; notes    = values[4]; tissue_type = values[5];
            extraction = values[6];
        }

        csvWriter.writeRecord(new java.lang.String[]{"date", "plate_id",
            "plate_name", "sample_id", "well_A01", "well_01A", "tissue_id",
            "dna_person", "notes", "tissue_type", "extraction"});
        {
            final int cols = this.getCols(), rows = this.getRows();

            for (int col = 1; col <= cols; col++)
            {
                for (int r = 0; r < rows; r++)
                {
                    csvWriter.write(date      );
                    csvWriter.write(plate_id  );
                    csvWriter.write(plate_name);
                    {
                        final java.lang.String sample_id;
                        {
                            final java.lang.String rowName =
                                java.lang.Character.toString((char) ('A' + r));
                            final java.lang.String colName =
                                java.lang.String.format("%02d", col);

                            sample_id = java.lang.String.format(
                                "%s_%s%s", plate_id, rowName, colName);
                            csvWriter.write(sample_id               );                  // sample_id
                            csvWriter.write("%s%s", rowName, colName);                  // well_A01
                            csvWriter.write("%s%s", colName, rowName);                  // well_01A
                        }
                        csvWriter.write(this.getEntryModel(r + 1, col)
                            .getDNAExportValue(sample_id));                             // tissue_id
                    }
                    csvWriter.write(dna_person );
                    csvWriter.write(notes      );
                    csvWriter.write(tissue_type);
                    csvWriter.write(extraction );

                    csvWriter.endRecord();
                }
                helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    private void exportUserDefined(final org.wheatgenetics.javalib.CsvWriter csvWriter,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        csvWriter.write("Value"); csvWriter.write("Column"); csvWriter.write("Row");

        {
            final java.lang.String names[] = this.optionalFields().names();
            assert null != names; for (final java.lang.String name: names) csvWriter.write(name);
        }
        csvWriter.endRecord();

        {
            final int              cols     = this.getCols(), rows = this.getRows();
            final java.lang.String values[] = this.optionalFields().values()       ;

            assert null != values; for (int col = 1; col <= cols; col++)
            {
                for (int row = 1; row <= rows; row++)
                {
                    csvWriter.write(this.getEntryModel(row, col).getUserDefinedExportValue());
                    csvWriter.write(col);                                csvWriter.write(row);
                    for (final java.lang.String value: values) csvWriter.write(value);

                    csvWriter.endRecord();
                }
                helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }
    // endregion

    private org.wheatgenetics.coordinate.model.Cells initialExcludedCells()
    { return null == this.templateModel ? null : this.templateModel.getExcludedCells(); }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    private boolean isExcludedRow(final int row)
    { return null == this.templateModel ? true : this.templateModel.isExcludedRow(row); }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    private boolean isExcludedCol(final int col)
    { return null == this.templateModel ? true : this.templateModel.isExcludedCol(col); }

    private org.wheatgenetics.coordinate.model.Cells excludedCellsFromTemplate()//TODO: Do in Cells?
    {
        final int rows = this.getRows(), cols = this.getCols();

        final org.wheatgenetics.coordinate.model.Cells result;
        {
            final org.wheatgenetics.coordinate.model.Cells initialExcludedCells =
                this.initialExcludedCells();
            result = null == initialExcludedCells ?
                new org.wheatgenetics.coordinate.model.Cells(rows, cols)               :
                (org.wheatgenetics.coordinate.model.Cells) initialExcludedCells.clone();
        }

        for (int row = 1; row <= rows; row++) if (this.isExcludedRow(row))
            for (int col = 1; col <= cols; col++) result.add(row, col);

        for (int col = 1; col <= cols; col++) if (this.isExcludedCol(col))
            for (int row = 1; row <= rows; row++) result.add(row, col);

        return result;
    }

    private void makeEntryModelsFromExcludedCells(
    final org.wheatgenetics.coordinate.model.Cells excludedCells)
    {
        final int rows = this.getRows(), cols = this.getCols();
        this.entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
            /* gridId => */ this.getId(), /* rows => */ rows, /* cols => */ cols);
        for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
            if (null == excludedCells)
                this.entryModels.makeIncludedEntry(row, col);
            else
                if (excludedCells.contains(row, col))
                    this.entryModels.makeExcludedEntry(row, col);
                else
                    this.entryModels.makeIncludedEntry(row, col);
    }

    private org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel                activeEntryModel,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler   )
    {
        return null == this.entryModels ? null :
            this.entryModels.next(activeEntryModel, filledHandler);
    }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    private boolean setActiveRowAndActiveCol(
    final org.wheatgenetics.coordinate.model.EntryModel nextEntryModel)
    {
        return null == nextEntryModel ? false : this.setActiveRowAndActiveCol(
            nextEntryModel.getRow() - 1, nextEntryModel.getCol() - 1);
    }
    // endregion

    // region Constructors
    /** Used by GridCreator. */
    public JoinedGridModel(final long projectId, final java.lang.String person,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final org.wheatgenetics.coordinate.model.TemplateModel                 templateModel )
    {
        super(
            /* templateId     => */ templateModel.getId(),
            /* projectId      => */ projectId            ,
            /* person         => */ person               ,
            /* optionalFields => */ optionalFields       );
        this.templateModel = templateModel;
    }

    /** Used by GridsTable. */
    public JoinedGridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final long projectId, final java.lang.String person,
    @android.support.annotation.IntRange(from = 0) final int activeRow,
    @android.support.annotation.IntRange(from = 0) final int activeCol,
    final java.lang.String optionalFields, final long timestamp,

    @android.support.annotation.IntRange(from = 1        ) final long             templateId     ,
                                                           final java.lang.String title          ,
    @android.support.annotation.IntRange(from = 0, to = 2) final int              code           ,
    @android.support.annotation.IntRange(from = 1        ) final int              rows           ,
    @android.support.annotation.IntRange(from = 1        ) final int              cols           ,
    @android.support.annotation.IntRange(from = 0        ) final int generatedExcludedCellsAmount,
    final java.lang.String initialExcludedCells,
    final java.lang.String excludedRows, final java.lang.String excludedCols,
    @android.support.annotation.IntRange(from = 0, to = 1) final int colNumbering,
    @android.support.annotation.IntRange(from = 0, to = 1) final int rowNumbering,
    final java.lang.String templateOptionalFields, final long templateTimestamp,

    final org.wheatgenetics.coordinate.model.EntryModels entryModels)
    {
        super(id, templateId, projectId, person, activeRow, activeCol, optionalFields, timestamp);
        this.templateModel = new org.wheatgenetics.coordinate.model.TemplateModel(templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, templateOptionalFields,
            templateTimestamp);
        this.entryModels = entryModels;
    }
    // endregion

    // region org.wheatgenetics.coordinate.model.DisplayModel Overridden Methods
    @java.lang.Override public int getRows()
    { return null == this.templateModel ? 0 : this.templateModel.getRows(); }

    @java.lang.Override public int getCols()
    { return null == this.templateModel ? 0 : this.templateModel.getCols(); }

    @java.lang.Override public boolean getColNumbering()
    { assert null != this.templateModel; return this.templateModel.getColNumbering(); }

    @java.lang.Override public boolean getRowNumbering()
    { assert null != this.templateModel; return this.templateModel.getRowNumbering(); }

    @java.lang.Override
    public org.wheatgenetics.coordinate.model.ElementModel getElementModel(
    @android.support.annotation.IntRange(from = 1) int row,
    @android.support.annotation.IntRange(from = 1) int col) { return this.getEntryModel(row, col); }
    // endregion

    // region Package Methods
    @java.lang.SuppressWarnings({"DefaultLocale"}) java.lang.String name()
    {
        return java.lang.String.format("Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getPerson(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    org.wheatgenetics.coordinate.model.Cells excludedCellsFromEntries()
    {
        final int rows = this.getRows(), cols = this.getCols();
        final org.wheatgenetics.coordinate.model.Cells result =
            new org.wheatgenetics.coordinate.model.Cells(rows, cols);

        if (null != this.entryModels)                                    // TODO: Do in EntryModels?
            for (int row = 1; row <= rows; row++) for (int col = 1; col <= cols; col++)
                if (this.getEntryModel(row, col) instanceof
                org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                    result.add(row, col);

        for (int row = 1; row <= rows; row++) if (this.isExcludedRow(row))
            for (int col = 1; col <= cols; col++) result.add(row, col);

        for (int col = 1; col <= cols; col++) if (this.isExcludedCol(col))
            for (int row = 1; row <= rows; row++) result.add(row, col);

        return result;
    }

    @java.lang.SuppressWarnings({"PointlessBooleanExpression"})
    boolean export(final java.io.File exportFile, final java.lang.String exportFileName,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        final boolean success = true;
        if (null == exportFile || null == helper || null == this.templateModel)
            return !success;
        else
        {
            final org.wheatgenetics.coordinate.model.TemplateType templateType =
                this.templateModel.getType();
            final org.wheatgenetics.javalib.CsvWriter csvWriter =
                new org.wheatgenetics.javalib.CsvWriter(exportFile);   // throws java.io.IOException
            if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
                this.exportSeed(csvWriter, exportFileName, helper);    // throws java.io.IOException
            else
                if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                    this.exportDNA(csvWriter, helper);                 // throws java.io.IOException
                else
                    this.exportUserDefined(csvWriter, helper);         // throws java.io.IOException
            return success;
        }
    }
    // endregion

    // region Public Methods
    public java.lang.String getTemplateTitle()
    { return null == this.templateModel ? null : this.templateModel.getTitle(); }

    public void makeEntryModels() throws
    org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange,
    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
    {
        final org.wheatgenetics.coordinate.model.Cells excludedCells =
            this.excludedCellsFromTemplate();

        assert null != this.templateModel; assert null != excludedCells;
        excludedCells.makeRandomCells(       // throws MaxRowAndOrMaxColOutOfRange, AmountIsTooLarge
            /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
            /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void makeEntryModels(final org.wheatgenetics.coordinate.model.Cells projectExcludedCells)
    throws org.wheatgenetics.coordinate.model.Cells.MaxRowAndOrMaxColOutOfRange,
    org.wheatgenetics.coordinate.model.Cells.AmountIsTooLarge
    {
        org.wheatgenetics.coordinate.model.Cells excludedCells = this.excludedCellsFromTemplate();

        {
            assert null != this.templateModel; assert null != projectExcludedCells;
            final org.wheatgenetics.coordinate.model.Cells randomCells =
                projectExcludedCells.makeRandomCells(                                      // throws
                    /* amount => */ this.templateModel.getGeneratedExcludedCellsAmount(),
                    /* maxRow => */ this.getRows(),       /* maxCol => */ this.getCols());
            if (null != randomCells)
                if (null == excludedCells)
                    excludedCells = randomCells;
                else
                    excludedCells.accumulate(randomCells);
        }

        this.makeEntryModelsFromExcludedCells(excludedCells);
    }

    public void setEntryModel(final org.wheatgenetics.coordinate.model.EntryModel entryModel)
    { if (null != this.entryModels) this.entryModels.set(entryModel); }

    public org.wheatgenetics.coordinate.model.EntryModel getActiveEntryModel()
    { return this.getEntryModel(this.getActiveRow() + 1, this.getActiveCol() + 1); }

    @java.lang.SuppressWarnings({"PointlessBooleanExpression"})
    public boolean setActiveRowAndActiveCol(
    @android.support.annotation.IntRange(from = 0) final int row,
    @android.support.annotation.IntRange(from = 0) final int col)
    {
        final boolean changed;

        if (this.getActiveRow() != row || this.getActiveCol() != col)
        {
            this.setActiveRow(row); this.setActiveCol(col);
            changed = true;
        }
        else changed = false;

        return changed;
    }

    public org.wheatgenetics.coordinate.model.EntryModels getEntryModels()
    { return this.entryModels; }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    public boolean goToNext(final org.wheatgenetics.coordinate.model.EntryModel entryModel,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledHandler filledHandler)
    {
        final org.wheatgenetics.coordinate.model.IncludedEntryModel nextIncludedEntryModel =
            this.next(entryModel, filledHandler);
        return null == nextIncludedEntryModel ? false :
            this.setActiveRowAndActiveCol(nextIncludedEntryModel);
    }

    @java.lang.SuppressWarnings({"SimplifiableConditionalExpression"})
    public boolean activeRowAndOrActiveColWasAdjusted()
    {
        final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel =
            this.getActiveEntryModel();
        return activeEntryModel instanceof org.wheatgenetics.coordinate.model.ExcludedEntryModel ?
            this.goToNext(activeEntryModel, null) : false;
    }
    // endregion
}