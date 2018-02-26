package com.example.syn_tax;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class Task {

    private String title;
    private String description;
    private User userR;
    private String id;
    private LocationActivity location;
    private Photo photo;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private String status;
    private boolean userRNotified = true;


    //-------------CONSTRUCTOR------------------
    public Task(String title, String description, User user) {
        this.title= title;
        this.description=description;
        this.userR= user;
    }

    public void setId(String id) {
        this.id = id;
    }

    //-------------PHOTOS------------------

    public ArrayList<Photo> getPhotos(){
        return photos;
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



    //-------------STATUS------------------

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public boolean hasTask(Task task) {
        return true;
    }

    public void addUserProvided(User userP) {
       this.userRNotified = true;
    }

    public boolean getUserRNotified() {
        return userRNotified;
    }

    public void addLocation(LocationActivity location) {
        this.location=location;
    }
}


