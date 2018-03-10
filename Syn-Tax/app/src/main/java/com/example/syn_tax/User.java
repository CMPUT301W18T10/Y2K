package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class User {
    private ArrayList<String> contactInfo = new ArrayList<String>();

    public User(String username, String email, String phoneNumber){
        contactInfo.add(username);
        contactInfo.add(email);
        contactInfo.add(phoneNumber);
    }

    public ArrayList<String> retrieveContactInfo() {
        return contactInfo;
    }

    public void editProfile(String username, String email, String phoneNumber){
        contactInfo.add(0, username);
        contactInfo.add(1, email);
        contactInfo.add(2, phoneNumber);
    }

    @Override
    public String toString(){
        String message = contactInfo.get(0)+ contactInfo.get(1)+ contactInfo.get(2);
        return message;
    }
}
