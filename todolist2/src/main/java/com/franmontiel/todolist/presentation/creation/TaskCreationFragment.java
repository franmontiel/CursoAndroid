package com.franmontiel.todolist.presentation.creation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.franmontiel.commons.navigation.NavigationHelper;
import com.franmontiel.todolist.R;
import com.franmontiel.todolist.presentation.base.MVPFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TaskCreationFragment
        extends MVPFragment<TaskCreationPresenter.TaskCreationView, TaskCreationPresenter>
        implements TaskCreationPresenter.TaskCreationView {

    public static void open(Fragment from) {
        NavigationHelper.open(from, TaskCreationActivity.class, null, null);
    }

    @BindView(R.id.titleField)
    EditText titleField;
    @BindView(R.id.descriptionField)
    EditText descriptionField;
    @BindView(R.id.isImportantCheck)
    CheckBox isImportantCheck;

    @OnClick(R.id.createTaskButton)
    public void onCreateClick() {
        getPresenter().onTaskCreationClick(
                titleField.getText().toString(),
                descriptionField.getText().toString(),
                isImportantCheck.isChecked()
        );
    }

    @Override
    public TaskCreationPresenter initalizePresenter() {
        return new TaskCreationPresenter();
    }

    @Override
    protected int getContentViewResource() {
        return R.layout.screen_task_creation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(getApplicationContext(), "Tarea creada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeScreen() {
        getActivity().finish();
//        getFragmentManager().beginTransaction().remove(this); // <--- Si usaramos navegación por fragment en lugar de por activity
    }
}
