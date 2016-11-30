package com.franmontiel.todolist.bus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class BusProvider {
    private static final EventBus BUS = new EventBus();

    public static EventBus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
