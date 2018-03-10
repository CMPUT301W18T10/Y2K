package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {
    private String id;
    private User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UNDERLINE Title
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


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


    public void createAccountBtn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
