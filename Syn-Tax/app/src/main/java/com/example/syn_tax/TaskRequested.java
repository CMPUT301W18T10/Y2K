package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TaskRequested {

    private String id;
    private Location location;
    private Photo photo;
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
    }

    public void deletePhoto(Photo photo) {
    }


    public boolean hasPhoto(Photo photo) {
        return true;
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


