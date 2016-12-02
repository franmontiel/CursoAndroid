package com.franmontiel.networkcalls.datasources;

import android.util.Log;

import com.franmontiel.networkcalls.bus.BusProvider;
import com.franmontiel.networkcalls.entities.User;
import com.franmontiel.networkcalls.events.UsersEvent;
import com.franmontiel.networkcalls.retrofit.RetrofitProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class RetrofitUserDataSource {

    private UserService userService;

    public RetrofitUserDataSource() {
        userService = RetrofitProvider.getRetrofit().create(UserService.class);
    }

    public void loadUsers() {
        userService.getUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        Log.d("Retrofit", "response received");
                        if (response.code() == 200) {
                            // ESTAMOS EN EL UI THREAD
                            BusProvider.getInstance().post(new UsersEvent(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d("Retrofit", "connectivity failure");
                        // Avisar del error al usuario
                    }
                });
    }


    interface UserService {
        @GET("users")
        Call<List<User>> getUsers();

    }
}
