package org.wheatgenetics.coordinate.utils;

/**
 * Uses:
 * android.text.format.DateFormat
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class Utils extends java.lang.Object
{
    public static java.lang.CharSequence formatDate(final long date)
    { return android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date(date)); }

    public static int convert(final java.lang.String s)
    {
        try                                             { return java.lang.Integer.parseInt(s); }
        catch (final java.lang.NumberFormatException e) { return                            -1; }
    }
}