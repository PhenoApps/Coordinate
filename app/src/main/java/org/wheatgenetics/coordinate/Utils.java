package org.wheatgenetics.coordinate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.RequestDir;
import org.wheatgenetics.javalib.Dir;

import java.io.IOException;

public class Utils {
    // region AlertDialog Methods
    // private alert(String title, String message, positiveText, positiveListener, negativeListener)
    //     public alert(int title, String message)
    //         public alert(int message)
    //     public alert(int message, yesRunnable)
    //     private confirm(int title, String message, yesRunnable, noRunnable)
    //         public confirm(int title, String message, yesRunnable)
    //         private confirm(int title, int message, yesRunnable, noRunnable)
    //             confirm(int title, int message, yesRunnable)
    //                 confirm(int message, yesRunnable)
    //             public confirm(int message, yesRunnable, noRunnable)
    // region alert() AlertDialog Methods
    private static void alert(final Context context, final String title,
                              final String message, final String positiveButtonText,
                              final DialogInterface.OnClickListener positiveButtonOnClickListener,
                              @Nullable final DialogInterface.OnClickListener
                                      negativeButtonOnClickListener) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        if (null != negativeButtonOnClickListener) builder.setNegativeButton(
                context.getString(R.string.UtilsNegativeButtonText),
                negativeButtonOnClickListener);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText,
                positiveButtonOnClickListener).show();
    }

    // region alert(title, message) AlertDialog Methods
    public static void alert(
            @NonNull final Context context,
            @StringRes final int title, final String message) {
        Utils.alert(context, context.getString(title), message,
                context.getString(R.string.UtilsPositiveButtonText),
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener(),
                null);
    }

    public static void alert(
            @NonNull final Context context,
            @StringRes final int message) {
        Utils.alert(context,
                R.string.app_name, context.getString(message));
    }
    // endregion

    // region alert(title, message, yesRunnable) AlertDialog Method
    public static void alert(
            @NonNull final Context context,
            @StringRes final int message, final Runnable yesRunnable) {
        Utils.alert(context,
                context.getString(R.string.app_name),
                context.getString(message),
                context.getString(R.string.UtilsPositiveButtonText),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        if (null != dialog) dialog.cancel();
                        if (null != yesRunnable) yesRunnable.run();
                    }
                }, null);
    }
    // endregion
    // endregion

    // region confirm() AlertDialog Methods
    private static void confirm(
            @NonNull final Context context,
            @StringRes final int title, final String message,
            final Runnable yesRunnable,
            @Nullable final Runnable noRunnable) {
        Utils.alert(context, context.getString(title), message,
                context.getString(R.string.UtilsPositiveButtonText),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        if (null != dialog) dialog.cancel();
                        if (null != yesRunnable) yesRunnable.run();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        if (null != dialog) dialog.cancel();
                        if (null != noRunnable) noRunnable.run();
                    }
                });
    }

    // region confirm(title, String message, yesRunnable) AlertDialog Method
    public static void confirm(
            @NonNull final Context context,
            @StringRes final int title, final String message,
            final Runnable yesRunnable) {
        Utils.confirm(
                context, title, message, yesRunnable, null);
    }
    // endregion

    // region confirm(title, int message, yesRunnable) AlertDialog Methods
    private static void confirm(
            @NonNull final Context context,
            @StringRes final int title,
            @StringRes final int message,
            final Runnable yesRunnable,
            @Nullable final Runnable noRunnable) {
        Utils.confirm(context, title,
                context.getString(message), yesRunnable, noRunnable);
    }

    public static void confirm(
            @NonNull final Context context,
            @StringRes final int title,
            @StringRes final int message, final Runnable yesRunnable) {
        Utils.confirm(
                context, title, message, yesRunnable, null);
    }

    public static void confirm(
            @NonNull final Context context,
            @StringRes final int message, final Runnable yesRunnable) {
        Utils.confirm(context,
                R.string.app_name, message, yesRunnable);
    }
    // endregion

    // region confirm(title, message, yesRunnable, noRunnable) AlertDialog Method
    public static void confirm(
            @NonNull final Context context,
            @StringRes final int message, final Runnable yesRunnable,
            final Runnable noRunnable) {
        Utils.confirm(context,
                R.string.app_name, message, yesRunnable, noRunnable);
    }
    // endregion
    // endregion
    // endregion

    public static int valid(final int value, final int minValue,
                            @NonNull final StringGetter stringGetter) {
        if (value < minValue) {
            @NonNull final String format;
            {
                @Nullable final String string =
                        stringGetter.get(R.string.UtilsInvalidValue);
                format = null == string ? "value must be >= %d" : string;
            }
            throw new IllegalArgumentException(String.format(format, minValue));
        } else return value;
    }

    public static String convert(
            @IntRange(from = 0) final int offsetFromA) {
        if (offsetFromA < 0)
            throw new IllegalArgumentException();
        else {
            @IntRange(from = 0) final int modulas;
            final String rightPortion;
            {
                final int numberOfUppercaseLetters = 26;
                modulas = offsetFromA / numberOfUppercaseLetters;
                rightPortion = Character.toString(
                        (char) ('A' + offsetFromA % numberOfUppercaseLetters));
            }

            if (0 == modulas)
                return rightPortion;
            else
                return Utils.convert(                      // recursion
                        modulas - 1) + rightPortion;
        }
    }

    // region RequestDir Methods
    @NonNull
    public static RequestDir makeRequestDir(
            final Activity activity, final String name, final int requestCode) {
        return new RequestDir(
                /* activity            => */ activity,
                /* name                => */ name,
                /* blankHiddenFileName => */ Consts.BLANK_HIDDEN_FILE_NAME,
                /* requestCode         => */ requestCode);
    }

    public static void createCoordinateDirIfMissing(
            final Activity activity, final int requestCode)
            throws IOException, Dir.PermissionException {
        final RequestDir coordinateDir =
                Utils.makeRequestDir(activity,
                        Consts.COORDINATE_DIR_NAME, requestCode);
        coordinateDir.createIfMissing();                 // throws java.io.IOException, org.wheatge-
    }                                                    //  netics.javalib.Dir.PermissionException

    /**
     * This directory is used to transfer templates between devices.
     */
    @NonNull
    public static TemplatesDir templatesDir(
            final Activity activity, final int requestCode)
            throws IOException, Dir.PermissionException {
        Utils.createCoordinateDirIfMissing(// throws java.io.IOExcep-
                activity, requestCode);                                     //  tion, org.wheatgenetics-
        //  .javalib.Dir.Permission-
        final TemplatesDir result =        //  Exception
                new TemplatesDir(
                        /* activity => */ activity,
                        /* name     => */Consts.COORDINATE_DIR_NAME +
                        "/Templates",
                        /* blankHiddenFileName => */
                        Consts.BLANK_HIDDEN_FILE_NAME,
                        /* requestCode => */ requestCode);
        result.createIfMissing();                        // throws java.io.IOException, org.wheatge-
        return result;                                   //  netics.javalib.Dir.PermissionException
    }
    // endregion
}