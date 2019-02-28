package org.wheatgenetics.coordinate;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 *
 * org.wheatgenetics.coordinate.R
 */
public class TemplatesDir extends org.wheatgenetics.androidlibrary.RequestDir
{
    TemplatesDir(                                       final android.app.Activity activity,
    @java.lang.SuppressWarnings({"SameParameterValue"}) final java.lang.String     name    ,
    @java.lang.SuppressWarnings({"SameParameterValue"}) final java.lang.String blankHiddenFileName,
                                                        final int              requestCode        )
    { super(activity, name, blankHiddenFileName, requestCode); }

    @java.lang.Override public java.io.File createIfMissing()
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        final java.io.File blankHiddenFile = super.createIfMissing();        // throws IOException,
        {                                                                    //  PermissionException
            final java.io.File htpgFile = this.makeFile( // throws java.io.IOException, org.wheatge-
                "HTPG.xml");                    //  netics.javalib.Dir.PermissionException
            if (!htpgFile.exists())
            {
                final android.app.Activity activity = this.getActivity();

                assert null != activity;
                final java.io.InputStream inputStream = activity.getResources().openRawResource(
                    org.wheatgenetics.coordinate.R.raw.htpg);
                // noinspection TryFinallyCanBeTryWithResources
                try
                {
                    final java.io.FileOutputStream outputStream =
                        new java.io.FileOutputStream(htpgFile);              // throws java.io.File-
                    // noinspection TryFinallyCanBeTryWithResources          //  NotFoundException
                    try
                    {
                        final byte buffer[]          = new byte[1024];
                              int  numberOfBytesRead                 ;
                        while ((numberOfBytesRead = inputStream.read(buffer) /* throws */) > 0)
                            outputStream.write(buffer,0, numberOfBytesRead) /* throws */;
                    }
                    finally { outputStream.close() /* throws java.io.IOException */; }
                }
                finally { inputStream.close() /* throws java.io.IOException */; }
            }
        }
        return blankHiddenFile;
    }

    boolean atLeastOneXmlFileExists() throws org.wheatgenetics.javalib.Dir.PermissionException
    {
        final java.lang.String fileNames[] = this.listXml();       // throws org.wheatgenetics.java-
        // noinspection SimplifiableConditionalExpression          //  lib.Dir.PermissionException
        return null == fileNames ? false : fileNames.length > 0;
    }

    public java.lang.String[] listXml() throws org.wheatgenetics.javalib.Dir.PermissionException
    { return this.list(".+\\.xml"); }                       // throws org.wheatgenetics.java-
}                                                                  //  lib.Dir.PermissionException