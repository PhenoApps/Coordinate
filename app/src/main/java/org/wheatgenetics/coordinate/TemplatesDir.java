package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Context
 *
 * org.wheatgenetics.androidlibrary.Dir
 */
public class TemplatesDir extends org.wheatgenetics.androidlibrary.Dir
{
    TemplatesDir(final android.content.Context context, final java.lang.String name,
    final java.lang.String blankHiddenFileName) { super(context, name, blankHiddenFileName); }

    public java.lang.String[] listXml() { return this.list(".+\\.xml"); }
}