package org.wheatgenetics.coordinate.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
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
 */

public class Utils {
    private static final String TAG = "Utils";

    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void alert(Context context, String title, String msg) {
        Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
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

    public static void hideKeys(Context ctx, View vw) {
        if (vw == null)
            return;
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(vw.getWindowToken(), 0);
    }

    public static void sleeper(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
        }

    }

    public static Bitmap loadBitmap(Context context, String path) {
        Bitmap bmp = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            if (is != null) {
                bmp = BitmapFactory.decodeStream(is);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
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

        source = Bitmap.createScaledBitmap(source, (int) dwidth, (int) dheight,
                true);
        Log.i(TAG,
                String.format("output iw: %d ih: %d", source.getWidth(),
                        source.getHeight()));
        return source;
    }

    public static Bitmap resizeBitmap(Bitmap bm, int newHeight, int newWidth) {
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

    public static int getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        Display d = wm.getDefaultDisplay();

        return Math.min(metrics.widthPixels, metrics.heightPixels);
    }

    public static int getInteger(String input) {
        int value = -1;
        try {
            value = Integer.parseInt(input);
        } catch (Exception e) {

        }
        return value;
    }

    public static String getTag(int r, int c) {
        String tag = String.format(Locale.US, "tag_%d_%d", r, c);
        return tag;
    }

    public static String listToJson(List<Integer> list) {
        JSONArray json = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Integer intr = list.get(i);
            if (intr == null) continue;

            try {
                json.put(intr.intValue());
            } catch (Exception e) {

            }
        }

        String str = json.toString();
        return str;
    }

    public static String pointToJson(List<Point> list) {
        JSONArray json = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Point point = list.get(i);
            if (point == null) continue;

            try {
                JSONObject data = new JSONObject();
                data.put("row", point.y);
                data.put("col", point.x);

                json.put(data);
            } catch (Exception e) {

            }
        }

        String str = json.toString();
        return str;
    }

    public static List<Integer> jsonToList(String json) {
        List<Integer> list = new ArrayList<Integer>();

        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONArray jarr = (JSONArray) tokener.nextValue();

            for (int i = 0; i < jarr.length(); i++) {
                try {
                    int value = jarr.getInt(i);
                    list.add(Integer.valueOf(value));
                } catch (JSONException e) {

                }
            }
        } catch (JSONException e) {
        }

        return list;
    }

    public static List<Point> jsonToPoints(String json) {
        List<Point> points = new ArrayList<Point>();

        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONArray jarr = (JSONArray) tokener.nextValue();

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject jobj = (JSONObject) jarr.get(i);

                try {
                    int x = jobj.getInt("col");
                    int y = jobj.getInt("row");

                    points.add(new Point(x, y));
                } catch (JSONException e) {

                }
            }
        } catch (JSONException e) {
        }

        return points;
    }

    public static org.wheatgenetics.coordinate.objects.NonNullOptionalFields
    jsonToNonNullOptionalFields(final java.lang.String json) throws org.json.JSONException
    {
        final org.wheatgenetics.coordinate.objects.NonNullOptionalFields nonNullOptionalFields =
            new org.wheatgenetics.coordinate.objects.NonNullOptionalFields();

        final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
        final org.json.JSONArray   jsonArray   =
            (org.json.JSONArray) jsonTokener.nextValue();           // throws org.json.JSONException

        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length(); i++) nonNullOptionalFields.add(
            (org.json.JSONObject) jsonArray.get(i));                // throws org.json.JSONException

        return nonNullOptionalFields;
    }
}