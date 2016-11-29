package com.franmontiel.introandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Francisco J. Montiel on 29/11/16.
 */

public class PersonPersitor {

    private static final String SP_NAME = "SHARED_PREFS";
    private static final String KEY_PERSON = "PERSON";

    private SharedPreferences sharedPreferences;

    public PersonPersitor(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private void persistPersonToGreet(String personToGreet) {
        sharedPreferences.edit().putString(KEY_PERSON, personToGreet).apply();

    }

    private String loadLastPersonToGreet() {
        return sharedPreferences.getString(KEY_PERSON, "");
    }
}
