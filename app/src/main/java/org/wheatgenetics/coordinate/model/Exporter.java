package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnCancelListener
 * android.os.AsyncTask
 *
 * org.wheatgenetics.javalib.CsvWriter
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.ProgressDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
public class Exporter extends java.lang.Object
{
    // region Types
    public interface Helper
    {
        public abstract java.lang.String getValue(int row, int col);
        public abstract void             handleDone(java.lang.Boolean result,
            java.lang.String message, java.io.File exportFile);
    }

    private static class AsyncTask
    extends android.os.AsyncTask<java.lang.Void, java.lang.String, java.lang.Boolean>
    {
        // region Fields
        private final org.wheatgenetics.coordinate.model.Exporter.Helper helper        ;
        private final android.content.Context                            context       ;
        private final org.wheatgenetics.androidlibrary.ProgressDialog    progressDialog;
        private final org.wheatgenetics.coordinate.model.TemplateModel   templateModel ;
        private final java.lang.String                     exportFileName, absolutePath;

        private java.io.File     exportFile    ;
        private java.lang.String message = null;
        // endregion

        // region Private Methods
        @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
        private java.io.File makeExportFile()
        {
            final java.io.File exportFile =
                new java.io.File(this.absolutePath, this.exportFileName + ".csv");
            if (this.exportFile.exists()) this.exportFile.delete();
            return exportFile;
        }

        private boolean isExcluded(final int row, final int col)
        {
            assert null != this.templateModel; return
                this.templateModel.isExcludedRow (row     ) ||
                this.templateModel.isExcludedCol (col     ) ||
                this.templateModel.isExcludedCell(col, row);
        }


        private boolean exportSeed()
        {
            java.lang.String tray_id = "", person = "", date = "";
            {
                assert null != this.templateModel;
                final java.lang.String values[] = this.templateModel.optionalFieldValues(
                    new java.lang.String[] { "Tray", "Person", "date" });
                tray_id = values[0]; person = values[1]; date = values[2];
            }

            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.javalib.CsvWriter csvWriter =
                    new org.wheatgenetics.javalib.CsvWriter(this.exportFile);             // throws
                csvWriter.writeRecord(new java.lang.String[] {"tray_id", "cell_id",       //  java.-
                    "tray_num", "tray_column", "tray_row", "seed_id", "person", "date"}); //  io.IO-
                {                                                                         //  Excep-
                    final int                                                             //  tion
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    assert null != this.helper; assert null != this.context;
                    for (int col = 1; col <= cols; col++)
                    {
                        for (int row = 1; row <= rows; row++)
                        {
                            csvWriter.write(tray_id                                      );  // tray id
                            csvWriter.write("%s_C%02d_R%d", this.exportFileName, col, row);  // cell_id
                            csvWriter.write(                                             );  // tray_num
                            csvWriter.write(col                                          );  // tray_column
                            csvWriter.write(row                                          );  // tray_row
                            {
                                final java.lang.String value = this.isExcluded(row, col) ?
                                    "exclude" :
                                    org.wheatgenetics.javalib.Utils.replaceIfNull(
                                        this.helper.getValue(row, col), "BLANK_");
                                csvWriter.write(value);  // seed_id
                            }
                            csvWriter.write(person);  // person
                            csvWriter.write(date  );  // date

                            csvWriter.endRecord();
                        }
                        this.publishProgress(this.context.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();

                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                assert null != this.context; this.message = this.context.getString(
                    org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        @android.annotation.SuppressLint("DefaultLocale")
        private boolean exportDna()
        {
            java.lang.String date       = "", plate_id = "", plate_name  = ""                 ;
            java.lang.String dna_person = "", notes    = "", tissue_type = "", extraction = "";
            {
                assert null != this.templateModel;
                final java.lang.String values[] = this.templateModel.optionalFieldValues(
                    new java.lang.String[] { "date", "Plate", "Plate Name",
                        "person", "Notes", "tissue_type", "extraction" });
                date       = values[0]; plate_id = values[1]; plate_name  = values[2];
                dna_person = values[3]; notes    = values[4]; tissue_type = values[5];
                extraction = values[6];
            }

            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.javalib.CsvWriter csvWriter =
                    new org.wheatgenetics.javalib.CsvWriter(this.exportFile);       // throws java.-
                csvWriter.writeRecord(new java.lang.String[] {"date", "plate_id",   //  io.IOExcep-
                    "plate_name", "sample_id", "well_A01", "well_01A", "tissue_id", //  tion
                    "dna_person", "notes", "tissue_type", "extraction"});
                {
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    assert null != this.helper; assert null != this.context;
                    for (int col = 1; col <= cols; col++)
                    {
                        for (int r = 0; r < rows; r++)
                        {
                            csvWriter.write(date      );
                            csvWriter.write(plate_id  );
                            csvWriter.write(plate_name);
                            {
                                java.lang.String sample_id;
                                {
                                    final java.lang.String rowName =
                                        java.lang.Character.toString((char) ('A' + r));
                                    final java.lang.String colName =
                                        java.lang.String.format("%02d", col);

                                    sample_id = java.lang.String.format(
                                        "%s_%s%s", plate_id, rowName, colName);
                                    csvWriter.write(sample_id               );  // sample_id
                                    csvWriter.write("%s%s", rowName, colName);  // well_A01
                                    csvWriter.write("%s%s", colName, rowName);  // well_01A
                                }
                                {
                                    java.lang.String tissue_id;
                                    {
                                        final int row = r + 1;
                                        if (this.isExcluded(row, col))
                                            tissue_id = "BLANK_" + sample_id;
                                        else
                                        {
                                            tissue_id = this.helper.getValue(row, col);
                                            if (null == tissue_id || tissue_id.trim().length() == 0)
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
                        this.publishProgress(this.context.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();

                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                assert null != this.context; this.message = this.context.getString(
                org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }

        private boolean exportDefault()
        {
            this.exportFile = this.makeExportFile();
            boolean success = false;
            try
            {
                final org.wheatgenetics.javalib.CsvWriter csvWriter =
                    new org.wheatgenetics.javalib.CsvWriter(this.exportFile);    // throws java.io.-
                                                                                 //  IOException
                // Titles
                csvWriter.write("Value"); csvWriter.write("Column"); csvWriter.write("Row");

                {
                    assert null != this.templateModel;
                    final java.lang.String names[] = this.templateModel.optionalFieldNames();

                    assert null != names;
                    for (final java.lang.String name: names) csvWriter.write(name);
                }
                csvWriter.endRecord();

                {
                    final int
                        cols = this.templateModel.getCols(), rows = this.templateModel.getRows();

                    assert null != this.helper; assert null != this.context;
                    for (int col = 1; col <= cols; col++)
                    {
                        for (int row = 1; row <= rows; row++)
                        {
                            {
                                final java.lang.String value = this.isExcluded(row, col) ?
                                    "exclude" :
                                    org.wheatgenetics.javalib.Utils.makeEmptyIfNull(
                                        this.helper.getValue(row, col));
                                csvWriter.write(value);
                            }
                            csvWriter.write(col); csvWriter.write(row);

                            {
                                final java.lang.String values[] =
                                    this.templateModel.optionalFieldValues();

                                assert null != values;
                                for (final java.lang.String value: values) csvWriter.write(value);
                            }
                            csvWriter.endRecord();
                        }
                        this.publishProgress(this.context.getString(
                            org.wheatgenetics.coordinate.R.string.exporting_column_title) + col);
                    }
                }
                csvWriter.close();

                org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(
                    this.context, this.exportFile);
                success = true;
            }
            catch (final java.io.IOException e)
            {
                e.printStackTrace();
                assert null != this.context; this.message = this.context.getString(
                org.wheatgenetics.coordinate.R.string.export_failed);
            }

            return success;
        }
        // endregion

        private AsyncTask(final org.wheatgenetics.coordinate.model.Exporter.Helper helper,
        final android.content.Context context, final int progressDialogTitle,
        final int progressDialogMessage,
        final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
        final java.lang.String exportFileName, final java.lang.String absolutePath)
        {
            super();

            this.helper = helper; this.context = context;
            this.progressDialog = new org.wheatgenetics.androidlibrary.ProgressDialog(this.context,
                progressDialogTitle, progressDialogMessage,
                new android.content.DialogInterface.OnCancelListener()
                {
                    @java.lang.Override
                    public void onCancel(final android.content.DialogInterface dialog)
                    {
                        org.wheatgenetics.coordinate.model.Exporter.AsyncTask.this.cancel(
                            /* mayInterruptIfRunning => */ true);
                    }
                });

            this.templateModel  = templateModel ;
            this.exportFileName = exportFileName;
            this.absolutePath   = absolutePath  ;
        }

        // region Overridden Methods
        @java.lang.Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            assert null != this.progressDialog; this.progressDialog.show();
        }

        @java.lang.Override
        protected java.lang.Boolean doInBackground(final java.lang.Void... bparams)
        {
            assert null != this.templateModel;
            final org.wheatgenetics.coordinate.model.TemplateType templateType =
                this.templateModel.getType();
            if (org.wheatgenetics.coordinate.model.TemplateType.SEED == templateType)
                return this.exportSeed();
            else
            if (org.wheatgenetics.coordinate.model.TemplateType.DNA == templateType)
                return this.exportDna();
            else
                return this.exportDefault();
        }

        @java.lang.Override
        protected void onProgressUpdate(final java.lang.String... msg)
        {
            if (null != msg)
            {
                final java.lang.String message = msg[0];
                if (null != message)
                {
                    assert null != this.progressDialog;
                    this.progressDialog.setMessage(message);
                }
            }
        }

        @java.lang.Override
        protected void onPostExecute(final java.lang.Boolean result)
        {
            assert null != this.progressDialog; this.progressDialog.dismiss();
            assert null != this.helper        ; this.helper.handleDone(
                result, this.message, this.exportFile);
        }
        // endregion
    }
    // endregion

    private final org.wheatgenetics.coordinate.model.Exporter.AsyncTask asyncTask;

    public Exporter(final org.wheatgenetics.coordinate.model.Exporter.Helper helper,
    final android.content.Context context,
    final org.wheatgenetics.coordinate.model.TemplateModel templateModel,
    final java.lang.String exportFileName, final java.lang.String absolutePath)
    {
        super();
        this.asyncTask = new org.wheatgenetics.coordinate.model.Exporter.AsyncTask(helper, context,
            /* progressDialogTitle   => */ org.wheatgenetics.coordinate.R.string.exporting_title,
            /* progressDialogMessage => */ org.wheatgenetics.coordinate.R.string.exporting_body ,
            templateModel, exportFileName, absolutePath);
    }

    // region Public Methods
    public void execute() { this.asyncTask.execute()                                   ; }
    public void cancel () { this.asyncTask.cancel (/* mayInterruptIfRunning => */ true); }
    // endregion
}