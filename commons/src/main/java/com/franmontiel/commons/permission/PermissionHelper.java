package com.franmontiel.commons.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by Francisco J. Montiel on 15/03/16.
 */
public class PermissionHelper {

    public static void requestLocationPermissions(Fragment fragment, int requestCode) {
        requestPermissions(fragment, requestCode, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void requestPermissions(Fragment fragment, int requestCode, String... permissions) {
        fragment.requestPermissions(permissions, requestCode);
    }

    public static boolean hasLocationPermissions(Context context) {
        return hasAllPermissions(context, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean hasAllPermissions(Context context, String... permissions) {
        boolean hasAllPermissions = true;
        for (String permission : permissions) {
            hasAllPermissions &= ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasAllPermissions;
    }

    public static boolean shouldShowLocationRequestPermissionRationale(Activity activity) {
        return shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);

    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        boolean isAnyPermissionDenied = false;
        for (String permission : permissions) {
            isAnyPermissionDenied |= ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        }
        return isAnyPermissionDenied;
    }

    public static boolean isRequestedPermissionGranted(String requestedPermission, String[] permissions, int[] grantResults) {
        boolean permissionFound = false;
        boolean permissionGranted = false;

        for (int i = 0; i < permissions.length && !permissionFound; i++) {
            permissionFound = permissions[i].equals(requestedPermission);
            permissionGranted = permissionFound && grantResults[i] == PackageManager.PERMISSION_GRANTED;
        }

        return permissionGranted;
    }

    public static boolean areAllRequestedPermissionsGranted(int[] grantResults) {
        boolean allPermissionsGranted = true;
        for (int grantResult : grantResults) {
            allPermissionsGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }
        return allPermissionsGranted;

    }
}
