package com.example.syn_tax;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class Task {

    private String id;
    private Location location;
    private Photo photo;

    private TaskRequested taskrequested;
    private TaskProvided taskprovided;


    public Task(String title, String description, User user){}


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

    public void deleteTask(Task task) {}



    public boolean hasLocation(Location testLocation) {
        return true;
    }


    public boolean hasTask(Task task) {
        return true;
    }

    public Task getTask(Task task) {
        return task;
    }

    public TaskRequested getTaskRequested() {
        return taskrequested;
    }
    public TaskProvided getTaskProvided() {
        return taskprovided;
    }

    public void addUserProvided(User userP) {
    }


    Boolean userRNotified = true;
    public boolean getUserRNotified() {
        return userRNotified;
    }
}
