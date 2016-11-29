package com.franmontiel.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.franmontiel.todolist.entities.Task;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Francisco J. Montiel on 29/11/16.
 */

public class TaskAdapter extends BaseAdapter {

    private List<Task> items;

    public TaskAdapter(List<Task> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

            holder = new ViewHolder();

            holder.title = ButterKnife.findById(convertView, R.id.title);
            holder.description = ButterKnife.findById(convertView, R.id.description);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = (Task) getItem(position);

        holder.title.setText(task.getName());
        holder.description.setText(task.getDescription());

        if (task.getDescription().isEmpty()) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
    }
}
