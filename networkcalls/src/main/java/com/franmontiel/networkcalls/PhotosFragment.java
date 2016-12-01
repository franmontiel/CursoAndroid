package com.franmontiel.networkcalls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.franmontiel.networkcalls.bus.BusProvider;
import com.franmontiel.networkcalls.datasources.OkHttpPhotoDataSource;
import com.franmontiel.networkcalls.entities.Photo;
import com.franmontiel.networkcalls.events.PhotosEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class PhotosFragment extends Fragment {

    public static PhotosFragment newInstance() {
        PhotosFragment fragment = new PhotosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    private ListView list;
    private PhotoAdapter adapter;

    OkHttpPhotoDataSource okHttpPhotoDataSource;
    private List<Photo> data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = (ListView) getView().findViewById(R.id.list);
        adapter = new PhotoAdapter(getContext());

        list.setAdapter(adapter);

        okHttpPhotoDataSource = new OkHttpPhotoDataSource();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

        if (data == null) {
            loadPhotos();
        }
    }

    private void loadPhotos() {
        okHttpPhotoDataSource.loadPhotos();
    }

    @Subscribe
    public void onPhotosLoaded(PhotosEvent event) {
        data = event.getData();
        showPhotos();
    }

    private void showPhotos() {
        adapter.setItems(data);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
