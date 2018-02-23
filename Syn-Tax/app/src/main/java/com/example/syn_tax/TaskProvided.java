package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TaskProvided {

    private String id;
    private Location location;
    private Photo photo;
    private Bid lowestbid;
    private TaskRequested taskrequested;
    private TaskProvided taskprovided;

    public boolean hasTask(TaskProvided testtaskP) {
        return true;
    }
    public TaskProvided(String title, String description, User user){}


    public void setId(String id) {
        this.id = id;
    }



    public Photo getPhoto() {
        return photo;
    }



    public Location getLocation() {
        return location;
    }


    public TaskProvided getTask(TaskProvided task) {
        return task;
    }


    public void deleteTask(TaskProvided task) {
    }

    public Bid getLowestBid() {
        return lowestbid;
    }

    public TaskRequested getTaskRequested() {
        return taskrequested;
    }
    public TaskProvided getTaskProvided() {
        return taskprovided;
    }
}



