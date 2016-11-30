package com.franmontiel.todolist.datasources.events;

import com.franmontiel.todolist.bus.events.EntityEvent;
import com.franmontiel.todolist.entities.Task;

import java.util.List;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TasksEvent extends EntityEvent<List<Task>> {

    public TasksEvent(List<Task> data) {
        super(data);
    }

    public TasksEvent(List<Task> data, String tag) {
        super(data, tag);
    }
}
