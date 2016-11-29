package com.franmontiel.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.franmontiel.todolist.entities.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 28/11/16.
 */

public class TaskListFragment extends Fragment {

    public interface OnTaskSelectedListener {
        public void onTaskSelected(Task task);
    }

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    OnTaskSelectedListener onTaskSelectedListener;

    @BindView(R.id.taskList)
    ListView list;

    TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_task_list, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new TaskAdapter(generateFakeTasks());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onTaskSelectedListener != null) {
                    onTaskSelectedListener.onTaskSelected((Task) list.getItemAtPosition(i));
                }
            }
        });
    }

    public void setOnTaskSelectedListener(OnTaskSelectedListener onTaskSelectedListener) {
        this.onTaskSelectedListener = onTaskSelectedListener;
    }

    private static List<Task> generateFakeTasks() {
        List<Task> tasks = new ArrayList<>();

        int i = 1;
        tasks.add(new Task("Sacar al perro", "", true));
        tasks.add(new Task("Comprar", "Que no se me olvide comprar Sal", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Lavar los platos", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Lavar la ropa", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Sacar la basura", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));

        return tasks;
    }

}
