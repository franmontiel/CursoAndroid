package com.franmontiel.todolist.presentation.list;

import com.franmontiel.todolist.datasources.TaskDatabaseDataSource;
import com.franmontiel.todolist.datasources.events.TasksEvent;
import com.franmontiel.todolist.entities.Task;
import com.franmontiel.todolist.presentation.base.MVPPresenter;
import com.franmontiel.todolist.presentation.base.MVPView;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TaskListPresenter extends MVPPresenter<TaskListPresenter.TaskListView> {

    private static final String TAG = TaskListPresenter.class.getSimpleName();

    public interface TaskListView extends MVPView {
        void showTasks(List<Task> tasks);

        void removeTask(List<Task> task);

        void goToCreateTask();
    }

    List<Task> tasks;

    private TaskDatabaseDataSource taskDatabaseDataSource;

    public TaskListPresenter() {
        taskDatabaseDataSource = new TaskDatabaseDataSource(TAG);
    }

    @Override
    protected void onResume(TaskListView view) {
        super.onResume(view);
        if (tasks == null) {
//            tasks = TaskGenerator.generateFakeTasks();
//            getBoundView().showTasks(tasks);
            taskDatabaseDataSource.getAllTask();
        }
    }

    @Subscribe
    public void onTasksLoaded(TasksEvent event) {
//        if (event.getTag()....)
        getBoundView().showTasks(event.getData());
    }

    public void onCreateClick() {
        getBoundView().goToCreateTask();
    }

    public void onTaskClick() {
        // TODO...
    }


}
