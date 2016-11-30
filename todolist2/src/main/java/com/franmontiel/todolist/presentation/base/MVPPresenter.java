package com.franmontiel.todolist.presentation.base;

import com.franmontiel.todolist.bus.BusProvider;

/**
 * Created by Francisco J. Montiel on 30/11/16.
 */

public class MVPPresenter<V extends MVPView> {

    private MVPView view;

    protected void onResume(V view) {
        this.view = view;
        try {
            BusProvider.getInstance().register(this);
        } catch (org.greenrobot.eventbus.EventBusException e) {
            // TODO Fix for when there is no subscriber in the class registered
            // https://github.com/greenrobot/EventBus/issues/58
        }
    }

    protected void onPause(V view) {
        this.view = view;
        BusProvider.getInstance().unregister(this);
    }

    protected V getBoundView() {
        return (V) view;
    }


}
