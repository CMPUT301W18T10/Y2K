/*
 * Copyright (c) 2018 Term Winter 2018 . CMPUT 301. Team 43. University of Alberta. All Rights Reserved .
 * You may use , distribute, or modify the code under terms and conditions of the code of Students
 * Behaviour at University of Alberta.
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; Without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Last Modified 3/17/18 4:38 PM
 */

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
 *
 */
public class User {
    private String username;
    private ArrayList<String> info = new ArrayList<String>();
    private ArrayList<String> notification = new ArrayList<String>();

    private String id;
    private String NotificationTitle = "";
    private String NotificationMsg = "";

    /**
     * Constructor, Set the username, email, and phoneNUmber
     * @param username passing in the name
     * @param email passing in the email
     * @param phoneNumber passing in the phoneNumber
     */
    public User(String username, String email, String phoneNumber){
        this.username=username;
        info.add(username);
        info.add(email);
        info.add(phoneNumber);
        notification.add(NotificationTitle);
        notification.add(NotificationMsg);
    }

    public void setNotificationMsg(String title, String message){
        notification.add(title+": "+ message);
    }
    public void clearNotifications(){
        notification.clear();
    }

    public ArrayList<String> getNotification(){

        return notification;
    }
    public String getUsername(){
        return this.username;
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
        String message = info.get(0);
        return message;
    }
}
