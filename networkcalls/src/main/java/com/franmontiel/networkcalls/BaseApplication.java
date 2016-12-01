package com.franmontiel.networkcalls;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.franmontiel.networkcalls.okhttp.OkHttpProvider;
import com.franmontiel.networkcalls.retrofit.RetrofitProvider;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpProvider.initialize(getApplicationContext());

        RetrofitProvider.initialize(OkHttpProvider.getClient(), "https://jsonplaceholder.typicode.com/");

        Stetho.initializeWithDefaults(getApplicationContext());
    }
}
