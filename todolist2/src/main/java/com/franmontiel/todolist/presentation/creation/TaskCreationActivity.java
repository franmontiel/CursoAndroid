package com.franmontiel.todolist.presentation.creation;

import android.support.v4.app.Fragment;

import com.franmontiel.commons.screens.FragmentContainerActivity;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TaskCreationActivity extends FragmentContainerActivity {

    @Override
    protected Fragment instantiateFragment() {
        return new TaskCreationFragment();
    }
}
