package com.franmontiel.todolist.presentation.creation;

import com.franmontiel.todolist.datasources.TaskDatabaseDataSource;
import com.franmontiel.todolist.datasources.events.TaskCreatedEvent;
import com.franmontiel.todolist.entities.Task;
import com.franmontiel.todolist.presentation.base.MVPPresenter;
import com.franmontiel.todolist.presentation.base.MVPView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */
public class TaskCreationPresenter extends MVPPresenter<TaskCreationPresenter.TaskCreationView> {

    private static final String TAG = TaskCreationPresenter.class.getSimpleName();

    public interface TaskCreationView extends MVPView {
        void showSuccessMessage();

        void closeScreen();
    }

    private TaskDatabaseDataSource taskDatabaseDataSource;

    public TaskCreationPresenter() {
        this.taskDatabaseDataSource = new TaskDatabaseDataSource(TAG);
    }

    public void onTaskCreationClick(String name, String description, boolean isImportant) {
        Task task = new Task(name, description, isImportant);
        taskDatabaseDataSource.createTask(task);
    }

    @Subscribe
    public void onCreateTask(TaskCreatedEvent event) {
        if (event.getTag().equals(TAG)) {
            getBoundView().showSuccessMessage();
            getBoundView().closeScreen();
        }
    }
}
