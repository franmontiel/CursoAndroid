package com.franmontiel.activitynavigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class NavigationHelper {

    public static void open(Activity activity, Class<? extends AppCompatActivity> toActivityClass, Integer flags) {

        Intent intent = createIntent(activity, toActivityClass, null, flags);

        activity.startActivity(intent);
    }


    public static Intent createIntent(Context context, Class<? extends AppCompatActivity> toActivityClass, Bundle extras, Integer flags) {
        Intent intent = new Intent(context, toActivityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags != null) {
            intent.addFlags(flags);
        }
        return intent;
    }
}
