package org.wheatgenetics.about;

/**
 * Uses:
 * android.app.Activity
 * android.view.View
 * android.view.View.OnClickListener
 *
 * org.wheatgenetics.about.OtherApps.Index
 * org.wheatgenetics.about.OtherAppsAlertDialog
 */
@SuppressWarnings({"WeakerAccess", "ClassExplicitlyExtendsObject"})
public class OtherAppsOnClickListener extends Object
implements android.view.View.OnClickListener
{
    // region Fields
    private final android.app.Activity                    activity     ;
    private final OtherApps.Index suppressIndex;

    private OtherAppsAlertDialog otherAppsAlertDialog = null;   // lazy load
    // endregion

    public OtherAppsOnClickListener(
    final android.app.Activity                    activity     ,
    final OtherApps.Index suppressIndex)
    { super(); this.activity = activity; this.suppressIndex = suppressIndex; }

    @Override public void onClick(final android.view.View v)
    {
        if (null == this.otherAppsAlertDialog) this.otherAppsAlertDialog =
            new OtherAppsAlertDialog(this.activity, this.suppressIndex);
        this.otherAppsAlertDialog.show();
    }
}