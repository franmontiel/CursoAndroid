package com.franmontiel.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franmontiel.commons.adapters.recyclerview.BaseAdapter;
import com.franmontiel.commons.adapters.recyclerview.BaseViewHolder;
import com.franmontiel.todolist.entities.Task;

import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 29/11/16.
 */

public class TaskRecyclerAdapter extends BaseAdapter<TaskRecyclerAdapter.TaskViewHolder, Task> {

    static class TaskViewHolder extends BaseViewHolder {

        private TextView title, description;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.title);
            description = ButterKnife.findById(itemView, R.id.description);
        }
    }

    public TaskRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Context context, TaskViewHolder holder, int position) {
        Task task = getItem(position);

        holder.title.setText(task.getName());
        holder.description.setText(task.getDescription());
    }


}


