package com.franmontiel.todolist;

import android.app.Application;

import com.franmontiel.todolist.database.DatabaseHelper;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper.initialize(getApplicationContext());
    }
}
