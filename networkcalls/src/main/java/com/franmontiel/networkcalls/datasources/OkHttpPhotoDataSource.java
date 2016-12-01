package com.franmontiel.networkcalls.datasources;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.franmontiel.networkcalls.bus.BusProvider;
import com.franmontiel.networkcalls.entities.Photo;
import com.franmontiel.networkcalls.events.PhotosEvent;
import com.franmontiel.networkcalls.okhttp.OkHttpProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class OkHttpPhotoDataSource {

    public void loadPhotos() {
        OkHttpClient client = OkHttpProvider.getClient();

        client.newCall(
                new Request.Builder().get().url("https://jsonplaceholder.typicode.com/photos").build()
        )
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // La petición no se ha podidio ejecutar (problemas de conectividad)
                        // Avisar del error al usuario
                        Log.d("Okhttp", "connectivity failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("Okhttp", "response received");

                        // La petición se ha podido ejecutar pero su HTTP STATUS CODE puede no ser 200
                        int status = response.code();
                        if (status == 200) {
                            final List<Photo> photos = parsePhotos(response.body().string());


                            // TENED EN CUENTA QUE NO ESTAMOS EN EL UI THREAD!!!
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    BusProvider.getInstance().post(new PhotosEvent(photos));
                                }
                            });
                        } else {
                            // Avisar del error al usuario
                        }
                    }
                });
    }

    private List<Photo> parsePhotos(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, new TypeToken<ArrayList<Photo>>() {
        }.getType());
    }

    private void runOnUiThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }
}
