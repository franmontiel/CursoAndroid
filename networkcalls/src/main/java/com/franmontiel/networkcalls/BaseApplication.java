package com.franmontiel.networkcalls;

import android.app.Application;

import com.franmontiel.networkcalls.okhttp.OkHttpProvider;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpProvider.initializeClient(getApplicationContext());
    }
}
