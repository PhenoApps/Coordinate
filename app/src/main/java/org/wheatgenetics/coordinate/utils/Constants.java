package org.wheatgenetics.coordinate.utils;

/**
 * Uses:
 * android.os.Environment
 */

public class Constants extends java.lang.Object
{
    public static final java.io.File
        MAIN_PATH     = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate"          ),
        EXPORT_PATH   = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate/Export"   ),
        TEMPLATE_PATH = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate/Templates");
}