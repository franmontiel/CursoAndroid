package com.franmontiel.commons.navigation;

import android.Manifest;
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
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.franmontiel.commons.R;

import java.util.ArrayList;
import java.util.List;

public class IntentStarter {
    private IntentStarter() {
    }

    public static void share(Context context, @Nullable String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (title != null) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
        }
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");

        startIntent(context, intent);
    }

    public static void dial(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        startIntent(context, intent);
    }

    public static void sendEmail(Context context, String email) {
        sendEmail(context, email, null, null);
    }

    public static void sendEmail(Context context, String email, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        if (subject != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (body != null) {
            intent.putExtra(Intent.EXTRA_TEXT, body);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startIntent(context, intent);

    }

    public static void openInBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent toBrowser = new Intent(Intent.ACTION_VIEW, Uri
                .parse(url));
        toBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(toBrowser);

        startIntent(context, toBrowser);
    }

    public static void openInMarket(Context context, String packageName) {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + packageName));

        startIntent(context, intent);
    }

    public static void openLocationSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startIntent(context, intent);
    }

    public static void navigate(Context context, double desLat, double desLon) {
        Intent intentNavigate = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + desLat + ","
                        + desLon));
        intentNavigate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(context, intentNavigate);
    }

    public static void openApplicationSettings(Context context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));

        startIntent(context, intent);

    }

    public static void startIntent(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.intent_error, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static Uri openGalleryOrCamera(Fragment fragment,
                                          String appChooserTitle,
                                          int requestCode,
                                          Uri cameraFileUri) {

        List<Intent> cameraIntents = null;
        if (cameraFileUri != null) {
            cameraIntents = getCameraIntents(fragment.getContext(), cameraFileUri);
        }

        // Filesystem.
        // Chooser of filesystem options.
        Intent galleryIntent = getGalleryIntent();
        final Intent chooserIntent = Intent.createChooser(galleryIntent, appChooserTitle);

        if (cameraIntents != null && !cameraIntents.isEmpty()) {
            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[1]));
        }

        boolean success = startIntentForResult(fragment, chooserIntent, requestCode);

        return success ? cameraFileUri : null;
    }

    private static boolean startIntentForResult(Fragment fragment, Intent intent, int requestCode) {
        try {
            fragment.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(fragment.getContext(), R.string.intent_error, Toast.LENGTH_SHORT).show();
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