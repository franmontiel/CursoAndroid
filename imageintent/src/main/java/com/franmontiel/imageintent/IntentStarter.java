package com.franmontiel.imageintent;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IntentStarter {
    private IntentStarter() {
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static Uri openGalleryOrCamera(Activity activity,
                                          String appChooserTitle,
                                          int requestCode,
                                          Uri cameraFileUri) {

        List<Intent> cameraIntents = null;
        if (cameraFileUri != null) {
            cameraIntents = getCameraIntents(activity, cameraFileUri);
        }

        // Filesystem.
        // Chooser of filesystem options.
        Intent galleryIntent = getGalleryIntent();
        final Intent chooserIntent = Intent.createChooser(galleryIntent, appChooserTitle);

        if (cameraIntents != null && !cameraIntents.isEmpty()) {
            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[1]));
        }

        boolean success = startIntentForResult(activity, chooserIntent, requestCode);

        return success ? cameraFileUri : null;
    }

    private static boolean startIntentForResult(Activity activity, Intent intent, int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No hay ninguna app disponbible", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private static List<Intent> getCameraIntents(Context ctx, Uri outputFileUri) {

        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = ctx.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
                captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName,
                    res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            cameraIntents.add(intent);
        }
        return cameraIntents;
    }

    private static Intent getGalleryIntent() {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        galleryIntent.putExtra("return-data", false);

        return galleryIntent;
    }
}