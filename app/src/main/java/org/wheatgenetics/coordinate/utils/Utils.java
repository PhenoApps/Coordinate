package org.wheatgenetics.coordinate.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Uses:
 * android.text.format.DateFormat
 *
 * org.json.JSONArray
 */

public class Utils extends java.lang.Object
{
    private static final String TAG = "Utils";

    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) return true;
        return false;
    }

    public static void alert(Context context, String title, String msg) {
        Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }});
        dialog.show();
    }

    public static void alert(Context context, String title, String msg, final Runnable runnableYes) {
        Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (runnableYes != null) {
                    runnableYes.run();
                }
            }
        });
        dialog.show();
    }

    public static void confirm(Context context, String title, String msg, final Runnable runnableYes, final Runnable runnableNo) {
        Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (runnableYes != null) {
                    runnableYes.run();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (runnableNo != null) {
                    runnableNo.run();
                }
            }
        });
        dialog.show();
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

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

    public static void hideKeys(final Context ctx, final View vw)
    {
        if (vw == null) return;
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(vw.getWindowToken(), 0);
    }

    public static void sleeper(final int i)
    {
        try { Thread.sleep(i); } catch (InterruptedException e) {}
    }

    public static Bitmap loadBitmap(final Context context, final String path)
    {
        Bitmap bmp = null;
        FileInputStream is = null;
        try
        {
            is = new FileInputStream(path);
            if (is != null) bmp = BitmapFactory.decodeStream(is);
        }
        catch (Exception e) {}
        finally { try { if (is != null) is.close(); } catch (Exception e) {} }
        return bmp;
    }

    public static Bitmap scaleBitmap(Bitmap source, int quality,
                                     int screenWidth, int screenHeight) {
        float ratioX = (float) screenWidth / (float) source.getWidth();
        float ratioY = (float) screenHeight / (float) source.getHeight();
        float ratio = Math.min(ratioX, ratioY);
        float dwidth = ratio * source.getWidth();
        float dheight = ratio * source.getHeight();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        source.compress(Bitmap.CompressFormat.JPEG, quality, os);
        source.recycle();

        byte[] array = os.toByteArray();

        source = BitmapFactory.decodeByteArray(array, 0, array.length);

        source = Bitmap.createScaledBitmap(source, (int) dwidth, (int) dheight, true);
        assert source != null;
        Log.i(TAG, String.format("output iw: %d ih: %d", source.getWidth(), source.getHeight()));
        return source;
    }

    public static Bitmap resizeBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = scaleWidth; //((float) newHeight) / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();

        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static int getScreenSize(final Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        Display d = wm.getDefaultDisplay();

        return Math.min(metrics.widthPixels, metrics.heightPixels);
    }

    public static int parseInt(final java.lang.String s)
    {
        int i;
        try                                             { i = java.lang.Integer.parseInt(s); }
        catch (final java.lang.NumberFormatException e) { i =                            -1; }
        return i;
    }

    public static String getTag(final int r, final int c)
    {
        return String.format(Locale.US, "tag_%d_%d", r, c);
    }

    public static java.lang.String integerListToJson(
    final java.util.List<java.lang.Integer> integerList)
    {
        if (null == integerList)
            return null;
        else if (integerList.size() <= 0)
            return null;
        else
        {
            final org.json.JSONArray jsonArray = new org.json.JSONArray();
            for (final java.lang.Integer integer: integerList)
                if (null != integer) jsonArray.put(integer.intValue());
            return jsonArray.toString();
        }
    }
}