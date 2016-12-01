package com.franmontiel.imageintent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {

    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    public static final String DEFAULT_FILE_EXTENSION = ".png";
    private static final int DEFAULT_QUALITY = 90;

    private BitmapUtil() {
    }


    public static byte[] encodeToBytes(Bitmap bm, @Nullable Bitmap.CompressFormat format, @Nullable Integer quality) {
        if (format == null) {
            format = DEFAULT_COMPRESS_FORMAT;
        }
        if (quality == null) {
            quality = DEFAULT_QUALITY;
        }
        byte[] bytes = null;
        if (bm != null && !bm.isRecycled()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(format, quality, stream);
            bytes = stream.toByteArray();
            try {
                stream.close();
            } catch (IOException e) {
//                Timber.w(e, "encodeToBytes");
            }
        }
        return bytes;
    }

    public interface OnBitmapDecodedListener {
        void onBitmapDecoded(Bitmap bitmap);
    }

    public static void decodeAndScaleImage(final Context context, final Uri imageUri,
                                           final int maximumSize, final boolean lowQuality,
                                           final OnBitmapDecodedListener onBitmapDecodedListener) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return decodeAndScaleImage(context, imageUri, maximumSize, lowQuality);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    onBitmapDecodedListener.onBitmapDecoded(bitmap);
                }
            }
        }.execute();
    }

    public static Bitmap decodeAndScaleImage(Context context, Uri imageUri,
                                             int maximumSize, boolean lowQuality) {
        Context ctx = context.getApplicationContext();

        InputStream stream = null;
        try {
            // Decode image size
            BitmapFactory.Options optionsToGetSize = new BitmapFactory.Options();
            optionsToGetSize.inJustDecodeBounds = true;

            stream = ctx.getContentResolver().openInputStream(imageUri);
            BitmapFactory.decodeStream(stream, null, optionsToGetSize);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (optionsToGetSize.outWidth / scale / 2 > maximumSize
                    || optionsToGetSize.outHeight / scale / 2 > maximumSize) {
                scale *= 2;
            }

            // Why close the stream? Response here: http://stackoverflow.com/a/21912323/980387
            stream.close();

            // Decode with inSampleSize
            stream = ctx.getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options optionsToResize = new BitmapFactory.Options();
            optionsToResize.inSampleSize = scale;
            if (lowQuality) {
                optionsToResize.inPreferredConfig = Bitmap.Config.RGB_565;
                optionsToResize.inDither = true;
            }

            return BitmapFactory.decodeStream(stream, null, optionsToResize);
        } catch (Exception e) {
//            Timber.w(e, "decodeAndScaleImage");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    public interface OnSavedBitmapListener {
        void onSavedBitmap();
    }

    public static void saveBitmap(final Bitmap bitmap, final File outFile,
                                  @Nullable final Bitmap.CompressFormat format, @Nullable final Integer quality,
                                  final OnSavedBitmapListener onSavedBitmapListener) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                saveBitmap(bitmap, outFile, format, quality);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onSavedBitmapListener.onSavedBitmap();
            }
        }.execute();
    }

    public static Uri saveBitmap(Bitmap bitmap, File outFile,
                                 @Nullable Bitmap.CompressFormat format, @Nullable Integer quality) {
        if (format == null) {
            format = DEFAULT_COMPRESS_FORMAT;
        }
        if (quality == null) {
            quality = DEFAULT_QUALITY;
        }

        Uri uri = null;
        if (outFile != null) {
            // If outfile does not exist -> ok
            // If outfile exist -> it should be successfully deleted
            if (!outFile.exists() || outFile.delete()) {
                try {
                    FileOutputStream out = new FileOutputStream(outFile);
                    if (bitmap.compress(format, quality, out)) {
                        uri = Uri.fromFile(outFile);
                    }
                    out.flush();
                    out.close();
                } catch (Exception e) {
//                    Timber.w(e, "saveBitmap");
                }
            }
        }
        return uri;
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}
