package com.example.syn_tax;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TaskRequested {

    private String id;
    private Location location;
    private Photo photo;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private TaskRequested taskrequested;
    private TaskProvided taskprovided;

    public boolean hasTask(TaskRequested testtaskR) {
        return true;
    }


    public TaskRequested(String title, String description, User user){}


    public void setId(String id) {
        this.id = id;
    }


    public Photo getPhoto() {
        return photo;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public void deletePhoto(Photo photo) {
        photos.removeAll(Collections.singleton(photo));
    }


    public boolean hasPhoto(Photo photo) {

        if (photos.contains(photo)){
            return true;
        }
        else
            return false;
    }


    public Location getLocation() {
        return location;
    }

    public void addLocation(Location testLocation) {
    }

    public void editLocation(Location testNewLocation) {
    }


    public boolean hasLocation(Location testLocation) {
        return true;
    }


    public TaskRequested getTask(TaskRequested task) {
        return task;
    }

    public void deleteTask(TaskRequested task) {
    }


    public TaskRequested getTaskRequested() {
        return taskrequested;
    }
    public TaskProvided getTaskProvided() {
        return taskprovided;
    }

    public boolean selectTask(TaskRequested testtask) {
        return true;
    }
}


