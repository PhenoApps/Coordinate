package org.wheatgenetics.coordinate.exporter;

/**
 * Uses:
 * com.csvreader.CsvWriter
 */
@java.lang.SuppressWarnings({"unused"}) public class CsvWriter extends org.phenoapps.csv.CsvWriter
{
    // region Constructors
    @java.lang.SuppressWarnings({"WeakerAccess", "unused"})
    public CsvWriter(final java.io.Writer writer)
    { super(/* outputStream => */ writer, /* delimiter => */','); }

    @java.lang.SuppressWarnings({"unused"})
    public CsvWriter(final java.io.File file) throws java.io.IOException
    {
        this(
                /* writer => */ new java.io.FileWriter(file,false) /* throws java.io.IOException */);
    }
    // endregion

    // region Public Methods
    @java.lang.SuppressWarnings({"unused"}) public void write() throws java.io.IOException
    { this.write("") /* throws java.io.IOException */; }

    @java.lang.SuppressWarnings({"unused"})
    public void write(final int content) throws java.io.IOException
    { this.write(java.lang.String.valueOf(content)) /* throws java.io.IOException */; }

    @java.lang.SuppressWarnings({"unused"}) public void write(final java.lang.String format,
                                                              final java.lang.Object... args) throws java.io.IOException
    { this.write(java.lang.String.format(format, args)) /* throws java.io.IOException */; }
    // endregion
}