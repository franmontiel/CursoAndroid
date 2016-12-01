package com.franmontiel.networkcalls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.franmontiel.networkcalls.bus.BusProvider;
import com.franmontiel.networkcalls.datasources.RetrofitUserDataSource;
import com.franmontiel.networkcalls.entities.User;
import com.franmontiel.networkcalls.events.UsersEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class UsersFragment extends Fragment {

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    private ListView list;
    private ArrayAdapter<User> adapter;

    RetrofitUserDataSource retrofitUserDataSource;
    private List<User> data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = (ListView) getView().findViewById(R.id.list);
        adapter = new ArrayAdapter<User>(getContext(), android.R.layout.simple_list_item_1);

        list.setAdapter(adapter);

        retrofitUserDataSource = new RetrofitUserDataSource();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) list.getItemAtPosition(i);
                WebViewActivity.open(UsersFragment.this, user.getWebsite(), user.getName());
            }
        });

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
        retrofitUserDataSource.loadUsers();
    }

    @Subscribe
    public void onUsersLoaded(UsersEvent event) {
        data = event.getData();
        showUsers();
    }

    private void showUsers() {
        adapter.addAll(data);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
