package org.wheatgenetics.androidlibrary;

/**
 * Uses:
 * android.app.Activity
 * android.Manifest.permission
 *
 * androidx.core.app.ActivityCompat
 *
 * org.wheatgenetics.javalib.Dir
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.PermissionDir
 */
public class RequestDir extends PermissionDir
{
    private final int requestCode;

    // region Constructors
    public RequestDir(final android.app.Activity activity, final String name,
    final String blankHiddenFileName, final int requestCode)
    { super(activity, name, blankHiddenFileName); this.requestCode = requestCode; }

    @SuppressWarnings({"unused"}) public RequestDir(final android.app.Activity activity,
                                                    final org.wheatgenetics.javalib.Dir parent, final String name, final int requestCode)
    { super(activity, parent, name); this.requestCode = requestCode; }
    // endregion

    @Override protected boolean requestPermission()
    {
        if (!this.permissionGranted()) androidx.core.app.ActivityCompat.requestPermissions(
            /* activity    => */ this.getActivity(),
            /* permissions => */ org.wheatgenetics.javalib.Utils.stringArray(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            /* requestCode => */ this.requestCode);

        return true;
    }
}