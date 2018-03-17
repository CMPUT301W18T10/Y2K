package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class User {
    private ArrayList<String> info = new ArrayList<String>();
    private String id;

    public User(String username, String email, String phoneNumber){
        info.add(username);
        info.add(email);
        info.add(phoneNumber);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> retrieveInfo() {
        return info;
    }

    @Override
    public String toString(){
        String message = "Username: "+ info.get(0);
        return message;
    }


    public void editProfile(String name, String email, String phoneNumber) {
        info.set(0, name);
        info.set(1, email);
        info.set(2,phoneNumber);
    }
}
