package org.wheatgenetics.coordinate.utils;

/**
 * Uses:
 * android.os.Environment
 */

public class Constants extends java.lang.Object
{
    /**
     * EXPORT_PATH:   Exported data is saved to this folder.
     * TEMPLATE_PATH: This folder will be used in the future to transfer templates between devices.
     */
    public static final java.io.File
        MAIN_PATH     = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate"          ),
        EXPORT_PATH   = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate/Export"   ),
        TEMPLATE_PATH = new java.io.File(android.os.Environment.getExternalStorageDirectory(),
            "Coordinate/Templates");
}