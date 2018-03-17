package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

/**
 * User Class
 *
 * Feb 21, 2018
 *
 * User Object, has a id, username, email, and a phoneNumber
 *
 * Copyright GNU GENERAL PUBLIC LICENSE
 Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.

 Permissions of this strong copyleft license are conditioned on making available complete
 source code of licensed works and modifications, which include larger works using a licensed work,
 under the same license. Copyright and license notices must be preserved. Contributors provide an
 express grant of patent rights.
 */
public class User {
    private ArrayList<String> info = new ArrayList<String>();
    private String id;

    /**
     * Constructor, Set the username, email, and phoneNUmber
     * @param username passing in the name
     * @param email passing in the email
     * @param phoneNumber passing in the phoneNumber
     */
    public User(String username, String email, String phoneNumber){
        info.add(username);
        info.add(email);
        info.add(phoneNumber);
    }

    /**
     * Get the id of a user
     * @return the id of a user
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of a user
     * @param id passing in the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the username, email, and phoneNumber in a arrayList
     * @return a arrayList containing a username, email, and phoneNumber
     */
    public ArrayList<String> retrieveInfo() {
        return info;
    }


    /**
     * Edit the user profile,
     * changing the email, and phoneNUmber
     * @param name is username
     * @param email is email
     * @param phoneNumber is phoneNumber
     */
    public void editProfile(String name, String email, String phoneNumber) {
        info.set(0, name);
        info.set(1, email);
        info.set(2,phoneNumber);
    }

    /**
     * Method to convert the user to a string type
     * @return the username
     */
    @Override
    public String toString(){
        String message = "Username: "+ info.get(0);
        return message;
    }
}
