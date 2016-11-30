package com.franmontiel.todolist.datasources;

import android.os.AsyncTask;

import com.franmontiel.todolist.bus.BusProvider;
import com.franmontiel.todolist.database.DatabaseHelper;
import com.franmontiel.todolist.datasources.events.TaskCreatedEvent;
import com.franmontiel.todolist.datasources.events.TasksEvent;
import com.franmontiel.todolist.entities.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TaskDatabaseDataSource {

    private String tag;

    public TaskDatabaseDataSource(String tag) {
        this.tag = tag;
    }

    public void createTask(final Task task) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    DatabaseHelper.getInstance().getTaskDao().create(task);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {

                BusProvider.getInstance().post(new TaskCreatedEvent(task, tag));
            }
        }.execute();
    }

    public void removeTask(Task task) {
        // TODO
    }

    public void getAllTask() {
        new AsyncTask<Void, Void, List<Task>>() {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = new ArrayList<Task>();
                try {
                    tasks = DatabaseHelper.getInstance().getTaskDao().queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                BusProvider.getInstance().post(new TasksEvent(tasks, tag));
            }
        }.execute();

    }

}
