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

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
*
* CreateAccount activity class
*
* March 18, 2018
*
    * First validates the user inputs then checks if the username
* is in the database, then creates an account if the username is unused
*
 * @see ElasticSearchController
 * @see
 *
 *
* */

public class CreateAccount extends AppCompatActivity {

    private User newUser;
    private TextView username, email, phoneNumber;
    private String str_username, str_email, str_phoneNumber;
    private Button newUserBtn;

    /**
     * onCreate Method
     *  @param savedInstanceState
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UNDERLINE Title
        //TextView title = findViewById(R.id.title);
        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        username =  findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phonenumber);
        newUserBtn = findViewById(R.id.saveBtn);
    }


    /**
     * onStart, continuously listen to the create account button and
     * check the connection and valid parameters. If the connection, username,
     * email and phone numbers are valid then create the account
     * */
    protected void onStart() {
        super.onStart();
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = username.getText().toString();
                str_email = email.getText().toString();
                str_phoneNumber = phoneNumber.getText().toString();
                if (validUser(str_username, str_email, str_phoneNumber)){
                    if (!ElasticSearchController.connected()) {
                        Toast toasty = Toast.makeText(CreateAccount.this, "Cannot create user, internet connection lost.", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER, 0, 0);
                        toasty.show();
                    }
                    else{
                        if (makeUser(str_username) && validUser(str_username,str_email,str_phoneNumber)) {
                            newUser = new User(str_username, str_email, str_phoneNumber);
                            ElasticSearchController.addUsers uploadUser = new ElasticSearchController.addUsers();
                            uploadUser.execute(newUser);
                            createAccountBtn();

                        }
                        else {
                            Toast toasty = Toast.makeText(CreateAccount.this, "Username already exists!", Toast.LENGTH_LONG);
                            toasty.setGravity(Gravity.CENTER, 0, 0);
                            toasty.show();
                        }
                    }
                }
            }
        });
    }

    /**
     * authenticate the username in the database
     *
     * @returns true if the username has been authenticated meaning
     * it is unique and unused and false if the username is taken
     *
     * */

    protected boolean makeUser(String username){
        //Return true if authenticated and false if not authenticated
        Boolean authenticate=true;
        ArrayList<User> userList;

        try {
            //Grab everything in the database for users
            ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
            allUsers.execute(username);
            userList = allUsers.get();

            //Check to see if the user entered a username in the system
            if(userList.size ()>=1){
                //If they did set thisuser to the username entered
                authenticate = false;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Return if authenticated
        return authenticate;
    }

    /**
     * @param str_email
     * @param str_phoneNumber
     * @param str_username
     *
     * Validates the usernmame, email and phone number that are input by
     * the user
     * */

    protected boolean validUser(String str_username, String str_email, String str_phoneNumber){
        str_username = username.getText().toString();
        str_email = email.getText().toString();
        str_phoneNumber = phoneNumber.getText().toString();
        if (str_username.length() > 8 | str_username.length() == 0){
            Toast toasty = Toast.makeText(CreateAccount.this,"Username must have less than 8 characters",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }
        if (!validEmail(str_email)){
            Toast toasty = Toast.makeText(CreateAccount.this,"Invalid Email",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }
        if (!validPhone(str_phoneNumber)){
            Toast toasty = Toast.makeText(CreateAccount.this,"Invalid Phone number",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }

        return true;
    }
    /**
     * @param str_email
     *
     * validates the email format
     * */

    private boolean validEmail(String str_email){
        return Patterns.EMAIL_ADDRESS.matcher(str_email).matches();
    }

    /**
     * @param str_phoneNumber
     *
     * validates the phone number given by the user
     * */
    private boolean validPhone(String str_phoneNumber){
        boolean valid = false;
        if (str_phoneNumber.length()< 9 || str_phoneNumber.length() > 13){
            valid = false;
        }
        else{
            valid = true;
        }

      return valid;
    }

    /**
     * starts the intent to go back to the login activity where user
     * can then log in
     * */
    private void createAccountBtn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
