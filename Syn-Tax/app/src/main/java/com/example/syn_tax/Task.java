package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class Task {


    private String id;
    private Photo photo;
    private ArrayList<Photo> photos;
    private ArrayList<User> userProvided;
    private User userRequested;
    private Boolean userRequestedNotfied = false;

    public Task(String title, String description, User user ){}


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }



    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public int numOfPhotos() {
        return photos.size();
    }

    public void addPhoto(Photo photo) {
    }

    public void deletePhoto(Photo photo) {
    }

    public boolean hasPhoto(Photo photo) {
        return true;
    }





    public Boolean getUserRNotified() {
        return userRequestedNotfied;
    }

    public void setUserRNotified(Boolean state){
        this.userRequestedNotfied= state;
    }



    public User getUserRequested(){
        return userRequested;
    }

    public ArrayList<User> getUserProvided(){
        return userProvided;
    }

    public void addUserProvided(User userP){
        this.userProvided.add(userP);
        //When a user is added that means they have bidded on the task
        //Notified
        this.setUserRNotified(true);
    }
}
