package org.wheatgenetics.coordinate.application;

import androidx.multidex.MultiDexApplication;

import org.wheatgenetics.coordinate.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class Coordinate extends MultiDexApplication {

    public Coordinate() {
        if (BuildConfig.DEBUG) {
            //StrictMode.enableDefaults();
            //un-comment to enable strict warnings in logcat
        }
    }
}
