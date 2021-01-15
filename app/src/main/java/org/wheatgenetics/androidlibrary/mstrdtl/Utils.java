package org.wheatgenetics.androidlibrary.mstrdtl;

/**
 * Uses:
 * android.content.Intent
 *
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.javalib.mstrdtl.Items
 *
 * org.wheatgenetics.androidlibrary.mstrdtl.Consts
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class Utils extends Object
{
    @androidx.annotation.NonNull public static android.content.Intent putJsonIntoIntent(
    @androidx.annotation.Nullable final String       json  ,
    @androidx.annotation.NonNull  final android.content.Intent intent)
    {
        if (null == json)
            return intent;
        else
        {
            final String trimmedJson = json.trim();
            return trimmedJson.length() <= 0 ? intent : intent.putExtra(
                Consts.JSON_KEY, trimmedJson);
        }
    }

    @androidx.annotation.NonNull public static android.content.Intent putJsonIntoIntent(
    @androidx.annotation.Nullable final org.wheatgenetics.javalib.mstrdtl.Items items ,
    @androidx.annotation.NonNull  final android.content.Intent                  intent)
    {
        if (null == items)
            return intent;
        else
            return Utils.putJsonIntoIntent(
                items.toJson(), intent);
    }
}