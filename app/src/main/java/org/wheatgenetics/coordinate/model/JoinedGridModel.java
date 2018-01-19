package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.support.annotation.IntRange
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 *
 * org.wheatgenetics.coordinate.model.Cell
 * org.wheatgenetics.coordinate.model.Cells
 * org.wheatgenetics.coordinate.model.EntryModel
 * org.wheatgenetics.coordinate.model.EntryModels
 * org.wheatgenetics.coordinate.model.EntryModels.FilledGridHandler
 * org.wheatgenetics.coordinate.model.ExcludedEntryModel
 * org.wheatgenetics.coordinate.model.GridModel
 * org.wheatgenetics.coordinate.model.IncludedEntryModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class JoinedGridModel extends org.wheatgenetics.coordinate.model.GridModel
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    interface Helper { public abstract void publishProgress(int col); }

    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel templateModel = null;
    private org.wheatgenetics.coordinate.model.EntryModels   entryModels   = null;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.model.Cells excludedCells()
    {
        if (null == this.excludedCellsInstance)
            this.excludedCellsInstance = new org.wheatgenetics.coordinate.model.Cells(
                /* maxRow => */ this.getRows(), /* maxCol => */ this.getCols());
        return this.excludedCellsInstance;
    }

    // region export*() Private Methods
    private void exportSeed(final org.wheatgenetics.javalib.CsvWriter csvWriter,
    final java.lang.String                                          exportFileName,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper        )
    throws java.io.IOException
    {
        final java.lang.String tray_id, person, date;
        {
            final java.lang.String values[] = this.optionalFields().values(
                /* names[] => */ new java.lang.String[] { "Tray", "Person", "date" });
            tray_id = values[0]; person = values[1]; date = values[2];
        }

        csvWriter.writeRecord(new java.lang.String[] {"tray_id", "cell_id",
            "tray_num", "tray_column", "tray_row", "seed_id", "person", "date"});
        {
            final int cols = this.getCols(), rows = this.getRows();

            for (int col = 1; col <= cols; col++)
            {
                for (int row = 1; row <= rows; row++)
                {
                    csvWriter.write(tray_id                                 );       // tray id
                    csvWriter.write("%s_C%02d_R%d", exportFileName, col, row);       // cell_id
                    csvWriter.write(                                        );       // tray_num
                    csvWriter.write(col                                     );       // tray_column
                    csvWriter.write(row                                     );       // tray_row
                    {
                        final java.lang.String value;
                        {
                            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                                this.getEntryModel(row, col);
                            if (entryModel instanceof
                            org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                                value = "exclude";
                            else
                                value = org.wheatgenetics.javalib.Utils.replaceIfNull(
                                    entryModel.getValue(), "BLANK_");
                        }
                        csvWriter.write(value);                                      // seed_id
                    }
                    csvWriter.write(person);                                         // person
                    csvWriter.write(date  );                                         // date

                    csvWriter.endRecord();
                }
                helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }

    @java.lang.SuppressWarnings("DefaultLocale")
    private void exportDna(final org.wheatgenetics.javalib.CsvWriter csvWriter,
    final org.wheatgenetics.coordinate.model.JoinedGridModel.Helper helper)
    throws java.io.IOException
    {
        final java.lang.String date, plate_id, plate_name,
            dna_person, notes, tissue_type, extraction;
        {
            final java.lang.String values[] = this.optionalFields().values(
                /* names[] => */ new java.lang.String[] { "date", "Plate",
                    "Plate Name", "person", "Notes", "tissue_type", "extraction" });
            date       = values[0]; plate_id = values[1]; plate_name  = values[2];
            dna_person = values[3]; notes    = values[4]; tissue_type = values[5];
            extraction = values[6];
        }

        csvWriter.writeRecord(new java.lang.String[] {"date", "plate_id",
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
                        {
                            java.lang.String tissue_id;
                            {
                                final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                                    this.getEntryModel(r + 1, col);
                                if (entryModel instanceof
                                org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                                    tissue_id = "BLANK_" + sample_id;
                                else
                                {
                                    tissue_id = entryModel.getValue();
                                    if (null == tissue_id || tissue_id.length() == 0)
                                        tissue_id = "BLANK_" + sample_id;
                                }
                            }
                            csvWriter.write(tissue_id);
                        }
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
                    {
                        final java.lang.String value;
                        {
                            final org.wheatgenetics.coordinate.model.EntryModel entryModel =
                                this.getEntryModel(row, col);
                            if (entryModel instanceof
                            org.wheatgenetics.coordinate.model.ExcludedEntryModel)
                                value = "exclude";
                            else
                                value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                                    entryModel.getValue());
                        }
                        csvWriter.write(value);
                    }
                    csvWriter.write(col); csvWriter.write(row);
                    for (final java.lang.String value: values) csvWriter.write(value);

                    csvWriter.endRecord();
                }
                helper.publishProgress(col);
            }
        }
        csvWriter.close();
    }
    // endregion
    // endregion

    // region Constructors
    /** Used by GridCreator. */
    public JoinedGridModel(final java.lang.String                          person        ,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields,
    final org.wheatgenetics.coordinate.model.TemplateModel                 templateModel )
    {
        super(
            /* templateId     => */ templateModel.getId()                  ,
            /* person         => */ person                                 ,
            /* excludedCells  => */ templateModel.getInitialExcludedCells(),
            /* optionalFields => */ optionalFields                         );
        this.templateModel = templateModel;
    }

    /** Used by GridsTable. */
    public JoinedGridModel(@android.support.annotation.IntRange(from = 1) final long id,
    final java.lang.String person, final java.lang.String excludedCells,
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
        super(id, templateId, person, excludedCells, rows, cols,
            activeRow, activeCol, optionalFields, timestamp);
        this.templateModel = new org.wheatgenetics.coordinate.model.TemplateModel(templateId,
            title, code, rows, cols, generatedExcludedCellsAmount, initialExcludedCells,
            excludedRows, excludedCols, colNumbering, rowNumbering, templateOptionalFields,
            templateTimestamp);
        this.entryModels = entryModels;
    }
    // endregion

    // region Package Methods
    @android.annotation.SuppressLint("DefaultLocale")
    java.lang.String name()
    {
        return java.lang.String.format("Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n",
            this.getPerson(), this.getTemplateTitle(), this.getCols(), this.getRows(),
            this.getFormattedTimestamp());
    }

    @java.lang.SuppressWarnings("PointlessBooleanExpression")
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
                    this.exportDna(csvWriter, helper);                 // throws java.io.IOException
                else
                    this.exportUserDefined(csvWriter, helper);         // throws java.io.IOException
            return success;
        }
    }
    // endregion

    // region Public Methods
    public java.lang.String getTemplateTitle()
    { return null == this.templateModel ? null : this.templateModel.getTitle(); }

    public int getRows() { return null == this.templateModel ? 0 : this.templateModel.getRows(); }
    public int getCols() { return null == this.templateModel ? 0 : this.templateModel.getCols(); }

    public boolean getColNumbering()
    { assert null != this.templateModel; return this.templateModel.getColNumbering(); }

    public boolean getRowNumbering()
    { assert null != this.templateModel; return this.templateModel.getRowNumbering(); }

    public java.lang.String getFirstOptionalFieldDatedValue()
    {
        return null == this.templateModel ? null :
            this.templateModel.getFirstOptionalFieldDatedValue();
    }

    public void addExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { this.excludedCells().add(row, col); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedRow(final int row)
    { return null == this.templateModel ? true : this.templateModel.isExcludedRow(row); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCol(final int col)
    { return null == this.templateModel ? true : this.templateModel.isExcludedCol(col); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCell(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    {
        return null == this.excludedCellsInstance ? false :
            this.excludedCellsInstance.contains(row, col);
    }

    public org.wheatgenetics.coordinate.model.Cell nextFreeCell(
    final org.wheatgenetics.coordinate.model.Cell candidateFreeCell)
    {
        if (null == candidateFreeCell)
            return null;
        else
        {
            {
                final int lastRow = this.getRows(), lastCol = this.getCols();
                candidateFreeCell.inRange(              // throws java.lang.IllegalArgumentException
                    /* maxCell => */ new org.wheatgenetics.coordinate.model.Cell(lastRow, lastCol));

                boolean candidateCol = true;
                for (int col = candidateFreeCell.getCol().getValue(); col <= lastCol; col++)
                {
                    if (!this.isExcludedCol(col))
                        for (int row = candidateCol ? candidateFreeCell.getRow().getValue() : 1;
                        row <= lastRow; row++)
                            if (!this.isExcludedRow(row)) if (!this.isExcludedCell(row, col))
                                return new org.wheatgenetics.coordinate.model.Cell(row, col);
                    candidateCol = false;
                }
            }
            return null;
        }
    }

    public void makeEntryModels()
    {
        final int rows = this.getRows(), cols = this.getCols();
        assert null != this.templateModel;
        this.excludedCells().makeRandomCells(this.templateModel.getGeneratedExcludedCellsAmount(),
            /* maxRow => */ rows, /* maxCol => */ cols);

        this.entryModels = new org.wheatgenetics.coordinate.model.EntryModels(
            /* gridId => */ this.getId(), rows, cols);
        for (int row = 1; row <= rows; row++)
        {
            final boolean excludedRow = this.isExcludedRow(row);
            for (int col = 1; col <= cols; col++)
                if (excludedRow)
                    this.entryModels.makeExcludedEntry(row, col);
                else
                    if (this.isExcludedCol(col))
                        this.entryModels.makeExcludedEntry(row, col);
                    else
                        if (this.isExcludedCell(row, col))
                            this.entryModels.makeExcludedEntry(row, col);
                        else
                            this.entryModels.makeIncludedEntry(row, col);
        }
    }

    public org.wheatgenetics.coordinate.model.EntryModel getEntryModel(
    @android.support.annotation.IntRange(from = 1) final int row,
    @android.support.annotation.IntRange(from = 1) final int col)
    { return null == this.entryModels ? null : this.entryModels.get(row, col); }

    public org.wheatgenetics.coordinate.model.EntryModel getActiveEntryModel()
    { return this.getEntryModel(this.getActiveRow() + 1, this.getActiveCol() + 1); }

    public boolean setActiveRowAndActiveCol(
    @android.support.annotation.IntRange(from = 0) final int row,
    @android.support.annotation.IntRange(from = 0) final int col)
    {
        final boolean changed = true;
        if (this.getActiveRow() != row || this.getActiveCol() != col)
        {
            this.setActiveRow(row); this.setActiveCol(col);
            return changed;
        }
        else return !changed;
    }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean setActiveRowAndActiveCol(
    final org.wheatgenetics.coordinate.model.EntryModel nextEntryModel)
    {
        return null == nextEntryModel ? false : this.setActiveRowAndActiveCol(
            nextEntryModel.getRow() - 1, nextEntryModel.getCol() - 1);
    }

    public org.wheatgenetics.coordinate.model.EntryModels getEntryModels()
    { return this.entryModels; }

    public org.wheatgenetics.coordinate.model.IncludedEntryModel next(
    final org.wheatgenetics.coordinate.model.EntryModel activeEntryModel,
    final org.wheatgenetics.coordinate.model.EntryModels.FilledGridHandler filledGridHandler)
    {
        return null == this.entryModels ? null :
            this.entryModels.next(activeEntryModel, filledGridHandler);
    }
    // endregion
}