package org.wheatgenetics.coordinate.csv;

/**
 * Uses:
 * org.wheatgenetics.coordinate.csv.CsvWriter
 */

public class CoordinateCsvWriter extends org.wheatgenetics.coordinate.csv.CsvWriter
{
    public CoordinateCsvWriter(final java.io.Writer outputStream)
    { super(/* outputStream => */ outputStream, /* delimiter => */ ','); }


    public void write(                 ) throws java.io.IOException { super.write(""); }

    public void write(final int content) throws java.io.IOException
    { super.write(java.lang.String.valueOf(content));}

    public void write(final java.lang.String format, java.lang.Object... args)
    { java.lang.String.format(format, args); }
}