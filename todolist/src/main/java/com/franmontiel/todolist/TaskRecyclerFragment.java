package com.franmontiel.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franmontiel.commons.adapters.recyclerview.OnItemClickListener;
import com.franmontiel.todolist.entities.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 28/11/16.
 */

public class TaskRecyclerFragment extends Fragment {

    public interface OnTaskSelectedListener {
        public void onTaskSelected(Task task);
    }

    public static TaskRecyclerFragment newInstance() {
        return new TaskRecyclerFragment();
    }

    TaskListFragment.OnTaskSelectedListener onTaskSelectedListener;

    @BindView(R.id.taskList)
    RecyclerView list;

    TaskRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_task_recyclerview, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
//        list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        list.setLayoutManager(new GridLayoutManager(getContext(), 2));

//        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        adapter = new TaskRecyclerAdapter(getContext());
        adapter.setItems(TaskGenerator.generateFakeTasks());
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (onTaskSelectedListener != null) {
                    onTaskSelectedListener.onTaskSelected(adapter.getItem(position));
                }
            }
        });
    }

    public void setOnTaskSelectedListener(TaskListFragment.OnTaskSelectedListener onTaskSelectedListener) {
        this.onTaskSelectedListener = onTaskSelectedListener;
    }
}
