package com.franmontiel.todolist;

import com.franmontiel.todolist.entities.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco J. Montiel on 29/11/16.
 */

public class TaskGenerator {

    static List<Task> generateFakeTasks() {
        List<Task> tasks = new ArrayList<>();

        int i = 1;
        tasks.add(new Task("Sacar al perro", "", true));
        tasks.add(new Task("Comprar", "Que no se me olvide comprar Sal", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Lavar los platos", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Lavar la ropa", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Sacar la basura", "", true));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));
        tasks.add(new Task("Task " + i++));

        return tasks;
    }
}
