package com.franmontiel.todolist.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.franmontiel.todolist.R;
import com.franmontiel.todolist.presentation.detail.TaskDetailActivity;
import com.franmontiel.todolist.presentation.detail.TaskDetailFragment;
import com.franmontiel.todolist.presentation.list.TaskListFragment;
import com.franmontiel.todolist.entities.Task;

public class MainActivity extends AppCompatActivity {

    boolean showingTwoFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Si puedo acceder al ID significa que estoy usando el layout de layout-w900dp
        showingTwoFragments = findViewById(R.id.detailContainer) != null;

        boolean isRestoringView = savedInstanceState != null;

        showMaster(isRestoringView);
    }

    private void showMaster(boolean isRestoringView) {
        TaskListFragment taskListFragment;

        if (!isRestoringView) {
            taskListFragment = TaskListFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.masterContainer, taskListFragment).commit();
        } else {
            taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.masterContainer);
        }

        if (taskListFragment != null)
            taskListFragment.setOnTaskSelectedListener(new TaskListFragment.OnTaskSelectedListener() {
                @Override
                public void onTaskSelected(Task task) {

                    showDetail(task);
                }
            });
    }

    private void showDetail(Task task) {
        if (showingTwoFragments) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailContainer, TaskDetailFragment.newInstance(task))
                    .commit();
        } else {
            TaskDetailActivity.open(this, task);

        }
    }


}
