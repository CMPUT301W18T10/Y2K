package com.example.syn_tax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hamsemare on 2018-02-21.
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
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private User requester;
    private User provider;




    //------------------------------CONSTRUCTOR-----------------------------------------
    public Task(String title, String description, String status ) {
        this.title= title;
        this.description=description;
        //this.requester= owner;
        this.status=status;
    }


    //SETTERS AND GETTERS FOR ID
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }


    //SETTERS AND GETTERS FOR STATUS
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    //SETTERS AND GETTERS FOR TITLE OF THE TASK
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    //SETTERS AND GETTERS FOR THE DESCRIPTION OF A TASK
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    //SETTERS AND GETTERS FOR THE USER REQUESTER OF THAT TASK
    public User getRequester() {
        return this.requester;
    }
    public void setRequester(User requester) {
        this.requester = requester;
    }


    //SETTERS AND GETTERS FOR THE USER PROVIDER FOR A TASK
    public User getProvider() {
        return this.provider;
    }
    public void setProvider(User provider) {
        this.provider = provider;
    }


    //SETTERS AND GETTERS FOR THE LOCATION OF A TASK
    public void setLocation(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }


    //SETTERS AND GETTERS FOR THE BIDS OF A TASK
    public ArrayList<Bid> returnBids(){
        return bids;
    }
    public void addBid(Bid newBid){
        bids.add(newBid);
    }
    public void deleteBid(int pos){
        bids.remove(pos);
    }
    public boolean hasBid(Bid bid){
        if (bids.contains(bid))
            return true;
        else
            return false;
    }
    public void clearBids(){
        for ( int i = (bids.size()-1); i >=0; i--) {
            Bid bid = bids.get(i);
            if (bid.getBidStatus() == "Declined"){
                bids.remove(bid);
            }
        }
    }


    //SETTERS AND GETTERS FOR THE A PHOTO OF A TASK
    public void setPhoto(Bitmap photo){
        if (photo != null) {
            this.photo= photo;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] b = byteArrayOutputStream.toByteArray();
            this.photoString = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }
    public Bitmap getPhoto(){
        if (this.photo == null &&  this.photoString!= null){
            byte[] decodeString = Base64.decode(this.photoString, Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return this.photo;
    }


    // METHOD TO CONVERT A TASK TO STRING TYPE
    @Override
    public String toString(){
        String message = this.title +  this.description + this.requester.toString();
        return message;
    }
}


