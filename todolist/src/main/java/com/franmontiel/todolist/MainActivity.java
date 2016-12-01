package com.franmontiel.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
        TaskRecyclerFragment taskListFragment;

        if (!isRestoringView) {
            taskListFragment = TaskRecyclerFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.masterContainer, taskListFragment).commit();
        } else {
            taskListFragment = (TaskRecyclerFragment) getSupportFragmentManager().findFragmentById(R.id.masterContainer);
        }

        if (taskListFragment != null)
            taskListFragment.setOnTaskSelectedListener(new TaskListFragment.OnTaskSelectedListener() {
                @Override
                public void onTaskSelected(Task task) {
//                Toast.makeText(getApplicationContext(), task.getName(), Toast.LENGTH_SHORT).show();
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
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.masterContainer, TaskDetailFragment.newInstance(task))
//                    .addToBackStack("DETAIL")
//                    .commit();
            TaskDetailActivity.open(this, task);

        }
    }


}
