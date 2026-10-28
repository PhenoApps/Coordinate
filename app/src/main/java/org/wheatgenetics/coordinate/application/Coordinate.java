package org.wheatgenetics.coordinate.application;

import android.app.Application;

import org.wheatgenetics.coordinate.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class Coordinate extends Application {

    public Coordinate() {
        if (BuildConfig.DEBUG) {
            //StrictMode.enableDefaults();
            //un-comment to enable strict warnings in logcat
        }
    }
}
