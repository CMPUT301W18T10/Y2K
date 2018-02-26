package com.example.syn_tax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {
    private String id;
    private NewUser newUser;

    public void setId(String id) {
        this.id = id;
    }


    //arraylist containing user contact information
    private ArrayList<String> contactinfo = new ArrayList<>();

    //arraylist containning user password and username
    private ArrayList<String> newuser = new ArrayList<>();


    public CreateAccount(String name, String username, String email, String phonenumber, String password) {
        ArrayList<String> newuser = new ArrayList<>();
        ArrayList<String> contactinfo = new ArrayList<>();
        newuser.add(name);
        newuser.add(password);
        newuser.add(id);
        contactinfo.add(phonenumber);
        contactinfo.add(email);
    }

    public ArrayList<String> getContactInfo() {
        return contactinfo;
    }


    public ArrayList<String> getNewUser() {
        return newuser;
    }
}
