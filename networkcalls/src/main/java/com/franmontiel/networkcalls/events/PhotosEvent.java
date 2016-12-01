package com.franmontiel.networkcalls.events;

import com.franmontiel.networkcalls.entities.Photo;

import java.util.List;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class PhotosEvent extends EntityEvent<List<Photo>> {

    public PhotosEvent(List<Photo> data) {
        super(data);
    }

    public PhotosEvent(List<Photo> data, String tag) {
        super(data, tag);
    }
}
