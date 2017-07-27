package org.wheatgenetics.coordinate.utils;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.graphics.Bitmap
 * android.graphics.BitmapFactory
 * android.graphics.Matrix
 * android.net.ConnectivityManager
 * android.net.NetworkInfo
 * android.text.format.DateFormat
 * android.util.DisplayMetrics
 * android.util.Log
 * android.view.Display
 * android.view.inputmethod.InputMethodManager
 * android.view.View
 * android.view.WindowManager
 * android.widget.Toast
 *
 * org.wheatgenetics.androidlibrary.Utils
 */
public class Utils extends java.lang.Object
{
    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public static boolean isOnline(final android.content.Context context)
    {
        android.net.NetworkInfo networkInfo;
        {
            assert null != context;
            final android.net.ConnectivityManager connectivityManager =
                (android.net.ConnectivityManager) context.getSystemService(
                    android.content.Context.CONNECTIVITY_SERVICE);
            assert null != connectivityManager;
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return null == networkInfo ? false : networkInfo.isConnectedOrConnecting();
    }

    // region AlertDialog Methods
    private static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.String positiveButtonText,
    final android.content.DialogInterface.OnClickListener positiveButtonOnClickListener,
    final android.content.DialogInterface.OnClickListener negativeButtonOnClickListener)
    {
        final android.app.AlertDialog.Builder builder =
            new android.app.AlertDialog.Builder(context);
        if (null != negativeButtonOnClickListener)
            builder.setNegativeButton("No", negativeButtonOnClickListener);
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText,
            positiveButtonOnClickListener).show();
    }

    public static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Ok",
            org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener(), null);
    }

    public static void alert(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable runnableYes)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Ok",
            new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog, final int id)
                    {
                        assert null != dialog;
                        dialog.cancel();
                        if (null != runnableYes) runnableYes.run();
                    }
                }, null);
    }

    public static void confirm(final android.content.Context context, final java.lang.String title,
    final java.lang.String message, final java.lang.Runnable runnableYes,
    final java.lang.Runnable runnableNo)
    {
        org.wheatgenetics.coordinate.utils.Utils.alert(context, title, message, "Yes",
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog;
                    dialog.cancel();
                    if (null != runnableYes) runnableYes.run();
                }
            },
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int id)
                {
                    assert null != dialog;
                    dialog.cancel();
                    if (null != runnableNo) runnableNo.run();
                }
            });
    }
    // endregion

    public static void showToast(final android.content.Context context, final java.lang.String text)
    { android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG).show(); }

    public static java.lang.String formatDate(final long date)
    {
        return (java.lang.String) android.text.format.DateFormat.format(
            "yyyy-MM-dd", new java.util.Date(date));
    }

    public static java.lang.String getCurrentDate()
    {
        return org.wheatgenetics.coordinate.utils.Utils.formatDate(
            java.lang.System.currentTimeMillis());
    }

    public static void hideKeys(final android.content.Context context, final android.view.View view)
    {
        if (null != context && null != view)
        {
            final android.view.inputmethod.InputMethodManager inputMethodManager =
                (android.view.inputmethod.InputMethodManager)
                    context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            assert null != inputMethodManager;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), /* flags => */ 0);
        }
    }

    @java.lang.SuppressWarnings("EmptyCatchBlock")
    public static void sleeper(final int i)
    { try { java.lang.Thread.sleep(i); } catch (final java.lang.InterruptedException e) {} }

    @java.lang.SuppressWarnings("EmptyCatchBlock")
    public static android.graphics.Bitmap loadBitmap(final java.lang.String path)
    {
        android.graphics.Bitmap bitmap = null;
        {
            java.io.FileInputStream fileInputStream;
            try { fileInputStream = new java.io.FileInputStream(path); }
            catch (final java.io.FileNotFoundException e) { fileInputStream = null; }

            if (fileInputStream != null)
            {
                bitmap = android.graphics.BitmapFactory.decodeStream(fileInputStream);
                try { fileInputStream.close(); } catch (final java.io.IOException e) {}
            }
        }
        return bitmap;
    }

    public static android.graphics.Bitmap scaleBitmap(android.graphics.Bitmap src,
    final int quality, final int screenWidth, final int screenHeight)
    {
        {
            {
                byte byteArray[];
                {
                    final java.io.ByteArrayOutputStream byteArrayOutputStream =
                        new java.io.ByteArrayOutputStream();

                    src.compress(
                        /* format =>  */ android.graphics.Bitmap.CompressFormat.JPEG,
                        /* quality => */ quality                                    ,
                        /* stream  => */ byteArrayOutputStream                      );
                    src.recycle();

                    byteArray = byteArrayOutputStream.toByteArray();
                }
                assert null != byteArray;
                src = android.graphics.BitmapFactory.decodeByteArray(
                    /* data => */ byteArray, /* offset => */ 0, /* length => */ byteArray.length);
            }

            int dstWidth, dstHeight;
            {
                assert null != src;
                final float srcWidth = (float) src.getWidth(),
                    srcHeight = (float) src.getHeight(), ratio;
                {
                    final float xRatio = (float) screenWidth  / srcWidth ;
                    final float yRatio = (float) screenHeight / srcHeight;
                    ratio = java.lang.Math.min(xRatio, yRatio);
                }
                dstWidth  = (int) (ratio * srcWidth );
                dstHeight = (int) (ratio * srcHeight);
            }
            src = android.graphics.Bitmap.createScaledBitmap(
                /* src       => */ src      ,
                /* dstWidth  => */ dstWidth ,
                /* dstHeight => */ dstHeight,
                /* filter    => */ true     );
        }
        assert null != src;
        android.util.Log.i("Utils", java.lang.String.format(
            "output iw: %d ih: %d", src.getWidth(), src.getHeight()));
        return src;
    }

    public static android.graphics.Bitmap resizeBitmap(final android.graphics.Bitmap bitmap,
    final int newHeight, final int newWidth)
    {
        assert null != bitmap;
        final int width = bitmap.getWidth(), height = bitmap.getHeight();

        // CREATE A MATRIX FOR THE MANIPULATION
        final android.graphics.Matrix matrix = new android.graphics.Matrix();
        {
            final float scaleWidth  =   ((float) newWidth ) / width              ;
            final float scaleHeight = /*((float) newHeight) / height*/ scaleWidth;

            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
        }

        // RECREATE THE NEW BITMAP
        return android.graphics.Bitmap.createBitmap(   /* source => */ bitmap,
            /* x      => */ 0     , /* y => */ 0     , /* width  => */ width ,
            /* height => */ height, /* m => */ matrix, /* filter => */ false );
    }

    public static int getScreenSize(final android.content.Context context)
    {
        final android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
        {
            assert null != context;
            final android.view.WindowManager windowManager = (android.view.WindowManager)
                context.getSystemService(android.content.Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            final android.view.Display display = windowManager.getDefaultDisplay(); // TODO: Appears
        }                                                                           // TODO:  unne-
        return java.lang.Math.min(metrics.widthPixels, metrics.heightPixels);       // TODO:  cessa-
    }                                                                               // TODO:  ry.

    public static int parseInt(final java.lang.String s)
    {
        try                                             { return java.lang.Integer.parseInt(s); }
        catch (final java.lang.NumberFormatException e) { return                            -1; }
    }

    public static java.lang.String getTag(final int r, final int c)
    { return java.lang.String.format(java.util.Locale.US, "tag_%d_%d", r, c); }
}