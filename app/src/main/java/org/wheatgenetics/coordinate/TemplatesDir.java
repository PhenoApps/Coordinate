package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.content.Context
 *
 * org.wheatgenetics.androidlibrary.Dir
 *
 * org.wheatgenetics.coordinate.R
 */
public class TemplatesDir extends org.wheatgenetics.androidlibrary.Dir
{
    TemplatesDir(final android.content.Context context, final java.lang.String name,
    final java.lang.String blankHiddenFileName) { super(context, name, blankHiddenFileName); }

    @java.lang.Override public java.io.File createIfMissing() throws java.io.IOException
    {
        {
            final java.io.File htpgFile = this.makeFile("HTPG.xml");
            if (!htpgFile.exists())
            {
                final android.content.Context context = this.getContext();

                assert null != context;
                final java.io.InputStream inputStream =
                    context.getResources().openRawResource(org.wheatgenetics.coordinate.R.raw.htpg);
                if (null != inputStream)
                    try
                    {
                        final java.io.FileOutputStream outputStream =
                            new java.io.FileOutputStream(htpgFile);
                        try
                        {
                            final byte buffer[]          = new byte[1024];
                                  int  numberOfBytesRead = 0             ;
                            while ((numberOfBytesRead = inputStream.read(buffer)) > 0)
                                outputStream.write(buffer, 0, numberOfBytesRead);
                        }
                        finally { outputStream.close(); }
                    }
                    finally { inputStream.close(); }
            }
        }
        return super.createIfMissing();
    }

    @java.lang.SuppressWarnings({"SimplifiableIfStatement"}) boolean atLeastOneXmlFileExists()
    {
        final java.lang.String fileNames[] = this.listXml();
        if (null == fileNames)
            return false;
        else
            return fileNames.length > 0;
    }

    public java.lang.String[] listXml() { return this.list(".+\\.xml"); }
}