package com.franmontiel.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Francisco J. Montiel on 28/11/16.
 */

public class TaskRecyclerFragment extends Fragment {

    public static TaskRecyclerFragment newInstance() {

        Bundle args = new Bundle();

        TaskRecyclerFragment fragment = new TaskRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
