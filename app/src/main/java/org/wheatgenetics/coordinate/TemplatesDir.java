package org.wheatgenetics.coordinate;

import android.app.Activity;

import org.wheatgenetics.androidlibrary.RequestDir;
import org.wheatgenetics.javalib.Dir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TemplatesDir extends RequestDir {
    TemplatesDir(final Activity activity,
                 @SuppressWarnings({"SameParameterValue"}) final String name,
                 @SuppressWarnings({"SameParameterValue"}) final String blankHiddenFileName,
                 final int requestCode) {
        super(activity, name, blankHiddenFileName, requestCode);
    }

    @Override
    public File createIfMissing()
            throws IOException, Dir.PermissionException {
        final File blankHiddenFile = super.createIfMissing();        // throws IOException,
        {                                                                    //  PermissionException
            final File htpgFile = this.makeFile( // throws java.io.IOException, org.wheatge-
                    "HTPG.xml");                    //  netics.javalib.Dir.PermissionException
            if (!htpgFile.exists()) {
                final Activity activity = this.getActivity();
                if (null != activity) {
                    final InputStream inputStream = activity.getResources().openRawResource(
                            R.raw.htpg);
                    // noinspection TryFinallyCanBeTryWithResources
                    try {
                        final FileOutputStream outputStream =
                                new FileOutputStream(htpgFile);          // throws java.io.File-
                        // noinspection TryFinallyCanBeTryWithResources      //  NotFoundException
                        try {
                            // noinspection CStyleArrayDeclaration
                            final byte buffer[] = new byte[1024];
                            int numberOfBytesRead;
                            while ((numberOfBytesRead = inputStream.read(buffer) /* throws */) > 0)
                                outputStream.write(buffer, 0, numberOfBytesRead) /* throws */;
                        } finally {
                            outputStream.close() /* throws java.io.IOException */;
                        }
                    } finally {
                        inputStream.close() /* throws java.io.IOException */;
                    }
                }
            }
        }
        return blankHiddenFile;
    }

    // region Public Methods
    public boolean atLeastOneXmlFileExists()
            throws Dir.PermissionException {
        // noinspection CStyleArrayDeclaration
        final String fileNames[] = this.listXml();       // throws org.wheatgenetics.java-
        // noinspection SimplifiableConditionalExpression          //  lib.Dir.PermissionException
        return null == fileNames ? false : fileNames.length > 0;
    }

    public String[] listXml() throws Dir.PermissionException {
        return this.list(".+\\.xml");
    }                       // throws org.wheatgenetics.java-
    // endregion                                                   //  lib.Dir.PermissionException
}