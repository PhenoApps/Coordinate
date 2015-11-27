package org.wheatgenetics.coordinate.utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File MAIN_PATH = new File(Environment.getExternalStorageDirectory(), "/Coordinate");

    public static final File EXPORT_PATH = new File(Environment.getExternalStorageDirectory(), "/Coordinate/Export");

    public static final File TEMPLATE_PATH = new File(Environment.getExternalStorageDirectory(), "/Coordinate/Templates");
}
