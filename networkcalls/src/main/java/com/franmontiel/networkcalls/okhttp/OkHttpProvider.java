package com.franmontiel.networkcalls.okhttp;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class OkHttpProvider {

    private static OkHttpClient OKHTTP_CLIENT;

    private OkHttpProvider() {
        // No instances.
    }

    public static void initializeClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Especificando cache
        final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
        final File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            builder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }

        // Añadiendo el user Agent (INTERCEPTOR)
        builder.addInterceptor(new UserAgentInterceptor("Android/" + Build.VERSION.RELEASE));

        // Añadiendo logging interceptor para visualizar las llamadas en el log
        builder.addInterceptor(createLoggingInterceptor());

        // Añadiendo stetho para debugging en Chrome
        builder.addNetworkInterceptor(new StethoInterceptor());

        // Timeouts
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);

        OKHTTP_CLIENT = builder.build();
    }

    private static Interceptor createLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("OkHttp", message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }

    public static OkHttpClient getClient() {
        return OKHTTP_CLIENT;
    }
}
