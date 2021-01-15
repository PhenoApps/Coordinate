package org.wheatgenetics.androidlibrary;

/**
 * Uses:
 * android.app.Activity
 * android.content.pm.PackageManager
 * android.Manifest.permission
 * android.net.Uri
 * android.os.Environment
 * android.util.Log
 *
 * androidx.annotation.NonNull
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.core.content.ContextCompat
 *
 * org.wheatgenetics.javalib.Dir
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.PermissionDir
 *
 * org.wheatgenetics.androidlibrary.Utils
 */
public class PermissionDir extends org.wheatgenetics.javalib.PermissionDir
{
    @androidx.annotation.NonNull private final android.app.Activity activity;

    @androidx.annotation.NonNull
    private static android.app.Activity nonNullActivity(final android.app.Activity activity)
    {
        if (null == activity)
            throw new IllegalArgumentException("activity must not be null");
        else
            return activity;
    }

    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull protected android.app.Activity getActivity()
    { return this.activity; }

    // region Constructors
    public PermissionDir(final android.app.Activity activity, final String name,
    final String blankHiddenFileName)
    {
        super(
            /* parent              => */ android.os.Environment.getExternalStorageDirectory(),
            /* child               => */ name                                                ,
            /* blankHiddenFileName => */ blankHiddenFileName                                 );
        this.activity = PermissionDir.nonNullActivity(activity);
    }

    @SuppressWarnings({"WeakerAccess"})
    public PermissionDir(final android.app.Activity activity,
    final org.wheatgenetics.javalib.Dir parent, final String name)
    {
        super(/* parent => */ parent, /* child => */ name);
        this.activity = PermissionDir.nonNullActivity(activity);
    }
    // endregion

    // region Overridden Methods
    @Override protected void log(final String msg)
    { android.util.Log.d("Dir",this.label() + msg); }

    @Override protected boolean permissionGranted()
    {
        return androidx.core.content.ContextCompat.checkSelfPermission(
                /* context    => */ this.getActivity()                                ,
                /* permission => */ android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }

    @Override public java.io.File createIfMissing()
    throws java.io.IOException, PermissionException
    {
        final java.io.File blankHiddenFile = super.createIfMissing(); // throws java.io.IOException,
        Utils.makeFileDiscoverable(  //  org.wheatgenetics.javalib-
            this.getActivity(), blankHiddenFile);                     //  .Dir.PermissionException
        return blankHiddenFile;
    }
    // endregion

    // region Public Methods
    @SuppressWarnings({"WeakerAccess"})
    public android.net.Uri parse(final String fileName)
    {
        final java.io.File path = this.getPath();
        return null == path ? null : android.net.Uri.parse(path.toString() + "/" + fileName);
    }

    @SuppressWarnings({"unused"}) public android.net.Uri parse(final java.io.File file)
    { return null == file ? null : this.parse(file.getName()); }
    // endregion
}