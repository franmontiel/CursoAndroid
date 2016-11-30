package com.franmontiel.todolist.presentation.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.franmontiel.commons.adapters.recyclerview.OnItemClickListener;
import com.franmontiel.todolist.R;
import com.franmontiel.todolist.entities.Task;
import com.franmontiel.todolist.presentation.base.MVPFragment;
import com.franmontiel.todolist.presentation.creation.TaskCreationFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Francisco J. Montiel on 28/11/16.
 */

public class TaskListFragment
        extends MVPFragment<TaskListPresenter.TaskListView, TaskListPresenter>
        implements TaskListPresenter.TaskListView {

    public interface OnTaskSelectedListener {
        public void onTaskSelected(Task task);
    }

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    TaskListFragment.OnTaskSelectedListener onTaskSelectedListener;

    @BindView(R.id.taskList)
    RecyclerView list;

    TasksAdapter adapter;


    @Override
    protected int getContentViewResource() {
        return R.layout.screen_task_recyclerview;
    }

    @Override
    public TaskListPresenter initalizePresenter() {
        return new TaskListPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        adapter = new TasksAdapter(getContext());
    }

    public void setOnTaskSelectedListener(TaskListFragment.OnTaskSelectedListener onTaskSelectedListener) {
        this.onTaskSelectedListener = onTaskSelectedListener;
    }

    @OnClick(R.id.openTaskCreationButton)
    public void openTaskCreationClick() {
        getPresenter().onCreateClick();
    }


    @Override
    public void showTasks(final List<Task> tasks) {
        adapter.setItems(tasks);
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

    @Override
    public void removeTask(List<Task> task) {
        // TODO
    }

    @Override
    public void goToCreateTask() {
        TaskCreationFragment.open(this);
    }
}
