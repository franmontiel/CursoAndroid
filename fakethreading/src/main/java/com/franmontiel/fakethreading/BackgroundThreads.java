package com.franmontiel.fakethreading;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class BackgroundThreads {

    public interface Callback {
        void onResult(boolean bool);
    }

    public static void executeAsyncTaskWithCallback(final Callback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.onResult(aBoolean);
            }
        }.execute();
    }

    public static void executeAsyncTaskWithWeakReferencedCallback(Callback callback) {

        final WeakReference<Callback> callbackWeakReference = new WeakReference<Callback>(callback);

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                Callback callback = callbackWeakReference.get();
                if (callback != null)
                    callback.onResult(aBoolean);
            }
        }.execute();
    }

    public static void executeAsyncTaskAndUseBusAsCommunication() {

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                BusProvider.getInstance().post(aBoolean);
            }
        }.execute();
    }

    public static void executeJavaThreadAndUseBusAsCommunication() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Cambio al UI Thread
                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                BusProvider.getInstance().post(true);
                            }
                        });
            }
        }).start();
    }
}


