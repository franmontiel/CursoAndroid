package com.franmontiel.todolist.bus.events;

public class EntityEvent<T> {

    private T data;
    private String tag;

    public EntityEvent(T data) {
        this(data, null);
    }

    public EntityEvent(T data, String tag) {
        this.data = data;
        this.tag = tag;
    }

    public T getData() {
        return data;
    }


    public String getTag() {
        return tag;
    }

}
