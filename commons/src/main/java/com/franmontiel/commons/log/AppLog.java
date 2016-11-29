package com.franmontiel.commons.log;

import android.util.Log;

public class AppLog {
    private static final int LEVEL_DISABLED = 100;

    //    private static int LEVEL = BuildConfig.DEBUG ? Log.VERBOSE : LEVEL_DISABLED;

    private static int LEVEL = Log.VERBOSE;

    private static final boolean ENABLE_LOGGING = true;

    public static void setLevel(int level) {
        LEVEL = level;
    }

    public static void disableLog() {
        LEVEL = LEVEL_DISABLED;
    }

    public static void v(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.VERBOSE)
            Log.v(TAG, message);
    }

    public static void d(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.DEBUG)
            Log.d(TAG, message);
    }

    public static void d(String TAG, String message, Throwable tr) {
        if (ENABLE_LOGGING && LEVEL <= Log.DEBUG)
            Log.d(TAG, message, tr);
    }

    public static void i(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.INFO)
            Log.i(TAG, message);
    }

    public static void w(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.WARN)
            Log.w(TAG, message);
    }

    public static void w(String TAG, Throwable tr) {
        if (ENABLE_LOGGING && LEVEL <= Log.WARN)
            Log.w(TAG, tr);
    }

    public static void e(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.ERROR)
            Log.e(TAG, message);
    }

    public static void wtf(String TAG, String message) {
        if (ENABLE_LOGGING && LEVEL <= Log.ERROR)
            Log.wtf(TAG, message);
    }

    public static void wtf(String TAG, Throwable tr) {
        if (ENABLE_LOGGING && LEVEL <= Log.ERROR)
            Log.wtf(TAG, tr);
    }

}
