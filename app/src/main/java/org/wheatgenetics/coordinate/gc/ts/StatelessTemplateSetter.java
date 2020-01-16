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
public class StatelessTemplateSetter
extends org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
{
    public StatelessTemplateSetter(                 final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.gc.ts.StatelessTemplateSetter.Handler handler)
    {
        super(activity, requestCode,
            org.wheatgenetics.coordinate.R.string.StatelessTemplateChoiceAlertDialogOldItem,
            handler                                                                        );
    }
}