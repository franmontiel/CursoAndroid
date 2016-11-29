package com.franmontiel.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.franmontiel.commons.navigation.NavigationHelper;
import com.franmontiel.commons.screens.FragmentContainerActivity;
import com.franmontiel.todolist.entities.Task;

/**
 * Created by Francisco J. Montiel on 29/11/16.
 */

public class TaskDetailActivity extends FragmentContainerActivity {

    public static void open(Activity from, Task task) {
        Bundle extras = new Bundle();
        extras.putParcelable(TaskDetailFragment.EXTRA_TASK, task);

        NavigationHelper.open(from, TaskDetailActivity.class, extras, null);
    }

    @Override
    protected Fragment instantiateFragment() {
        return TaskDetailFragment.newInstance();
    }
}
