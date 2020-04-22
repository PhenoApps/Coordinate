package org.wheatgenetics.coordinate.gc.ps;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.SelectAlertDialog
 * org.wheatgenetics.coordinate.SelectAlertDialog.Handler
 */
class ProjectChoiceAlertDialog extends org.wheatgenetics.coordinate.SelectAlertDialog
{
    @androidx.annotation.NonNull private java.lang.String[] items(
    @androidx.annotation.NonNull  final java.lang.String secondItem,
    @androidx.annotation.Nullable final java.lang.String thirdItem )
    {
        final java.lang.String firstItem = this.activity().getString(
            org.wheatgenetics.coordinate.R.string.ProjectSetterDontAddItem);
        if (null == thirdItem)
            return new java.lang.String[]{firstItem, secondItem};
        else
            return new java.lang.String[]{firstItem, secondItem, thirdItem};
    }

    ProjectChoiceAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull final
    org.wheatgenetics.coordinate.SelectAlertDialog.Handler handler) { super(activity, handler); }

    void show(
    @androidx.annotation.NonNull  final java.lang.String secondItem,
    @androidx.annotation.Nullable final java.lang.String thirdItem )
    {
        this.show(org.wheatgenetics.coordinate.R.string.ProjectChoiceAlertDialogTitle,
            this.items(secondItem, thirdItem));
    }
}