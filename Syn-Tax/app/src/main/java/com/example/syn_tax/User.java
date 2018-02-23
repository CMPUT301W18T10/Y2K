package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class User {
    public ArrayList<String> contactInfo = new ArrayList<>();

    public User(String username, String email, String phoneNumber){
        contactInfo.add(username);
        contactInfo.add(email);
        contactInfo.add(phoneNumber);
    }

    public ArrayList<String> retrieveContactInfo() {
        return contactInfo;
    }
}
