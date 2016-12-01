package com.franmontiel.imageintent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int GET_IMAGE_REQUEST_CODE = 1;
    private static final int IMAGE_MAX_SIZE_PX = 600;

    private Uri imageFileUri;
    private Bitmap loadedImageBitmap;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionHelper.hasAllPermissions(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    attachImage();
                } else {
                    PermissionHelper.requestPermissions(MainActivity.this, IMAGE_PERMISSION_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == IMAGE_PERMISSION_REQUEST_CODE) {
            if (PermissionHelper.areAllRequestedPermissionsGranted(grantResults)) {
                attachImage();
            } else {
                if (!PermissionHelper.shouldShowRequestPermissionRationale(this, permissions)) {
                    // The permission has been denied forever so we should show some message indicating how the user can enable it
                }
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private void attachImage() {
        imageFileUri = IntentStarter.openGalleryOrCamera(MainActivity.this,
                "ELIGE FOTO",
                GET_IMAGE_REQUEST_CODE,
                getTmpImageUri());
    }

    private Uri getTmpImageUri() {
        return getImageFileUri(getApplicationContext(), "tmp.jpg");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Clear ImageView & Recycle Bitmap
            clearImageBitmap();

            boolean resultFromCamera = data == null
                    || (data.getAction() != null && data.getAction().equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE))
                    || data.getData() == null;

            if (!resultFromCamera) {
                imageFileUri = data.getData();
            }
        } else {
            imageFileUri = null;
        }

        if (imageFileUri != null) {
            BitmapUtil.decodeAndScaleImage(getApplicationContext(),
                    imageFileUri, IMAGE_MAX_SIZE_PX, false,

                    new BitmapUtil.OnBitmapDecodedListener() {
                        @Override
                        public void onBitmapDecoded(final Bitmap bitmap) {
                            BitmapUtil.saveBitmap(bitmap,
                                    new File(imageFileUri.getPath()),
                                    Bitmap.CompressFormat.JPEG, 90,
                                    new BitmapUtil.OnSavedBitmapListener() {
                                        @Override
                                        public void onSavedBitmap() {
                                            Bitmap resizedBitmap = BitmapUtil.resize(bitmap, IMAGE_MAX_SIZE_PX, IMAGE_MAX_SIZE_PX);
                                            if (resizedBitmap != null) {
                                                setImageBitmap(resizedBitmap);
                                            }
                                        }
                                    });
                        }
                    });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private static Uri getImageFileUri(Context context, String filename) {
        File dir = context.getExternalFilesDir(null);
        if (dir != null) {
            return Uri.fromFile(new File(dir, filename));
        } else return null;
    }

    private void clearImageBitmap() {
        image.setImageDrawable(null);
        if (loadedImageBitmap != null) {
            loadedImageBitmap.recycle();
            loadedImageBitmap = null;
        }
    }

    private void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            loadedImageBitmap = bitmap;
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                    new ColorDrawable(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent)),
                    new BitmapDrawable(getResources(), bitmap)});
            image.setImageDrawable(td);
            td.startTransition(200);
        }
    }

}
