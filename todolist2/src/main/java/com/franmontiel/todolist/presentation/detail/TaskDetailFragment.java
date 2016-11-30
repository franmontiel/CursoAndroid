package com.franmontiel.todolist.presentation.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franmontiel.todolist.R;
import com.franmontiel.todolist.entities.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 28/11/16.
 */

public class TaskDetailFragment extends Fragment {

    public static final String EXTRA_TASK = "EXTRA_TASK";

    public static TaskDetailFragment newInstance() {
        TaskDetailFragment fragment = new TaskDetailFragment();
        return fragment;
    }

    public static TaskDetailFragment newInstance(Task task) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_TASK, task);

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_task_detail, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    // Similar al onCreate en el activity
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert (getArguments() != null && getArguments().containsKey(EXTRA_TASK));

        Task task = getArguments().getParcelable(EXTRA_TASK);

        title.setText(task.getName());
        description.setText(task.getDescription());

    }
}
