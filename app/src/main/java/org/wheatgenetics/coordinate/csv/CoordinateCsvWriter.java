package org.wheatgenetics.coordinate.csv;

/**
 * Uses:
 * android.annotation.SuppressLint
 *
 * com.csvreader.CsvWriter
 */
public class CoordinateCsvWriter extends com.csvreader.CsvWriter
{
    public CoordinateCsvWriter(final java.io.Writer outputStream)
    { super(/* outputStream => */ outputStream, /* delimiter => */ ','); }


    public void write(                 ) throws java.io.IOException { super.write(""); }

    public void write(final int content) throws java.io.IOException
    { super.write(java.lang.String.valueOf(content));}

    @android.annotation.SuppressLint("DefaultLocale")
    public void write(final java.lang.String format, java.lang.Object... args)
    throws java.io.IOException
    { this.write(java.lang.String.format(format, args)); }
}