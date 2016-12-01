package com.franmontiel.networkcalls.events;

import com.franmontiel.networkcalls.entities.User;

import java.util.List;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class UsersEvent extends EntityEvent<List<User>> {

    public UsersEvent(List<User> data) {
        super(data);
    }

    public UsersEvent(List<User> data, String tag) {
        super(data, tag);
    }
}
