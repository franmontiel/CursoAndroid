package com.franmontiel.commons.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Helper class to assist in in the navigation between Activities/Fragments
 */
public class NavigationHelper {

    private NavigationHelper() {
    }

    public static void open(Fragment fromFragment, Class<? extends AppCompatActivity> toActivityClass, Bundle extras, Integer flags) {
        Intent intent = createIntent(fromFragment.getActivity(), toActivityClass, extras, flags);
        fromFragment.startActivity(intent);
    }

    public static void open(Activity activity, Class<? extends AppCompatActivity> toActivityClass, Bundle extras, Integer flags) {
        Intent intent = createIntent(activity, toActivityClass, extras, flags);
        activity.startActivity(intent);
    }

    public static void openForResult(Fragment fromFragment, Class<? extends AppCompatActivity> toActivityClass, Bundle extras, Integer flags, int requestCode) {
        Intent intent = createIntent(fromFragment.getActivity(), toActivityClass, extras, flags);
        fromFragment.startActivityForResult(intent, requestCode);
    }

    public static void openForResult(Activity activity, Class<? extends AppCompatActivity> toActivityClass, Bundle extras, Integer flags, int requestCode) {
        Intent intent = createIntent(activity, toActivityClass, extras, flags);
        activity.startActivityForResult(intent, requestCode);
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