package org.wheatgenetics.coordinate.gc.ts;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
 */
public class StatefulTemplateSetter
extends org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
{
    public StatefulTemplateSetter(                  final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.gc.ts.StatefulTemplateSetter.Handler handler)
    {
        super(activity, requestCode,
            org.wheatgenetics.coordinate.R.string.StatefulTemplateChoiceAlertDialogOldItem,
            handler                                                                       );
    }
}