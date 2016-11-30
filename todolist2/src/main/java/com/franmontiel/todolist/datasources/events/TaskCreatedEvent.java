package com.franmontiel.todolist.datasources.events;

import com.franmontiel.todolist.bus.events.EntityEvent;
import com.franmontiel.todolist.entities.Task;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class TaskCreatedEvent extends EntityEvent<Task> {

    public TaskCreatedEvent(Task data) {
        super(data);
    }

    public TaskCreatedEvent(Task data, String tag) {
        super(data, tag);
    }
}
