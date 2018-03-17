package com.example.syn_tax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ArrayAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hamsemare on 2018-02-21.
 */

/**
 * Task Object, has a title, description, id, status, latitude, longitude, photo, list of bids
 * Also has a requester and provider.
 */
public class Task {

    //Private Attributes for a Task
    private String title;
    private String description;
    private String id;
    private String status;
    private double latitude;
    private double longitude;

    private transient Bitmap photo;
    private String photoString;

    private ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
    public static ArrayAdapter<Bid> bidAdapter; // For custom adapter to work
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private User requester;
    private User provider;




    //------------------------------CONSTRUCTOR-----------------------------------------

    /**
     * A task should have a title, description, a requester, status
     * @param title the name of the task
     * @param description a description of the task
     * @param requester the user that requested the task to be done
     * @param status the status of the task: requested, done, completed, or assigned
     */
    public Task(String title, String description,User requester, String status ) {
        this.title= title;
        this.description=description;
        this.requester= requester;
        this.status=status;
    }


    //SETTERS AND GETTERS FOR ID
    /**
     * Set the id of a task
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the id of a task
     * @return the id of the task
     */
    public String getId() {
        return id;
    }


    //SETTERS AND GETTERS FOR STATUS

    /**
     * Get the status of a task
     * @return the status of a task
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of a task
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    //SETTERS AND GETTERS FOR TITLE OF THE TASK

    /**
     * Get the title of a task
     * @return the title of a task
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title of a task
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    //SETTERS AND GETTERS FOR THE DESCRIPTION OF A TASK

    /**
     * Get the description of a task
     * @return the description of a task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    //SETTERS AND GETTERS FOR THE USER REQUESTER OF THAT TASK

    /**
     * Get the requester of a task
     * @return the requester of a task
     */
    public User getRequester() {
        return this.requester;
    }

    /**
     * Set the requester of a task
     * @param requester
     */
    public void setRequester(User requester) {
        this.requester = requester;
    }


    //SETTERS AND GETTERS FOR THE USER PROVIDER FOR A TASK

    /**
     * Get the provider of a task
     * @return the provider of a task
     */
    public User getProvider() {
        return this.provider;
    }

    /**
     * Set the provider of a task
     * @param provider
     */
    public void setProvider(User provider) {
        this.provider = provider;
    }


    //SETTERS AND GETTERS FOR THE LOCATION OF A TASK

    /**
     * Set the location of a task by passing in the latitude and longitude
     * @param lat its a double containing the latitude
     * @param lng its a double containing the longitude
     */
    public void setLocation(Double lat, Double lng){
        latitude = lat;
        longitude = lng;
    }

    /**
     * Get the Latitude of a a task
     * @return the latitude
     */
    public double getLat(){
        return latitude;
    }

    /**
     * Get the longitude of a task
     * @return the longitude
     */
    public double getLong(){
        return longitude;
    }

    //SETTERS AND GETTERS FOR THE BIDS OF A TASK

    /**
     * Get the List of bids
     * @return a arrayList of bids
     */
    public ArrayList<Bid> returnBids(){
        return bids;
    }

    /**
     * Add a bid for the task
     * @param newBid
     */
    public void addBid(Bid newBid){
        bids.add(newBid);
    }

    /**
     * Delete a bid for the task
     * @param pos
     */
    public void deleteBid(int pos){
        bids.remove(pos);
    }

    /**
     * Check if a bid is already bidded
     * @param bid
     * @return
     */
    public boolean hasBid(Bid bid){
        if (bids.contains(bid))
            return true;
        else
            return false;
    }

    /**
     * Clear the list of bids of task
     */
//    public void clearBids(){
//        for ( int i = (bids.size()-1); i >=0; i--) {
//            Bid bid = bids.get(i);
//            if (bid.getBidStatus() == "Declined"){
//                bids.remove(bid);
//            }
//        }
//    }


    //SETTERS AND GETTERS FOR THE A PHOTO OF A TASK

    /**
     * Set the photo of a task pass in the photo
     * @param photo
     */
    public void setPhoto(Bitmap photo){
        if (photo != null) {
            this.photo= photo;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] b = byteArrayOutputStream.toByteArray();
            this.photoString = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    /**
     * Get the photo of a task
     * @return the photo of a task
     */
    public Bitmap getPhoto(){
        if (this.photo == null &&  this.photoString!= null){
            byte[] decodeString = Base64.decode(this.photoString, Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return this.photo;
    }


    // METHOD TO CONVERT A TASK TO STRING TYPE

    /**
     * Method to convert the task to a string type
     * @return the title, description and the requester
     */
    @Override
    public String toString(){
        String message = this.title +  this.description + this.requester.toString();
        return message;
    }
}


