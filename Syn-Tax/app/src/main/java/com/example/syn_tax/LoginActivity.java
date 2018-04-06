


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
 * Last Modified 3/17/18 5:21 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * LoginActivity class
 *
 * Marcg 18, 2018
 *
 *
 * Searches the database for the username input, if the username does not exist
 * it prompts a pop up. If the username exists it opens the mainActivity
 * related to the username
 *
 * @see ElasticSearchController
 * @see CreateAccount
 * @see MainActivity
 *
 * */


public class LoginActivity extends AppCompatActivity {
    public static User thisuser;
    private ArrayList<User> userList;
    private Button thisButton;

    /**
     * onCreate method
     * @param savedInstanceState
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UNDERLINE Titles
        //TextView title = findViewById(R.id.title);
        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        thisButton = findViewById(R.id.loginButton);
    }

    /**
     * onStart it listens for the login button and checks for the connection to the database
     * If the phone is connected it checks if the username is in use
     *
     * */
    protected void onStart() {
        super.onStart();
        thisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ElasticSearchController.connected())) {
                    Toast toasty = Toast.makeText ( LoginActivity.this, "Cannot Login, No Internet Connection.", Toast.LENGTH_LONG );
                    toasty.setGravity ( Gravity.CENTER, 0, 300 );
                    toasty.show ();
                }

                else{
                    TextView Usernm = findViewById(R.id.username);
                    String str_username = Usernm.getText().toString();
                    if (getThisUser(str_username)) {
                        loginBtn();
                    }
                    else {
                        Toast toasty = Toast.makeText(LoginActivity.this, "Invalid username has been entered.", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER, 0, 300);
                        toasty.show();
                    }
                }
            }
        });

    }
    /**
     * starts the mainActivity related to the username
     * */

    private void loginBtn() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the createAccount activity
     * */
    public void createAccountBtn(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    /**
     * @param username
     *
     *checks the database to see if there is a username match
     * */
    protected boolean getThisUser(String username) {
        //Return true if authenticated and false if not authenticated
        Boolean authenticate = false;
        if (validUser(username)) {
            try {
                //Grab everything in the database for users
                ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
                allUsers.execute(username);

                userList = allUsers.get();

                //Check to see if the user entered a username in the system
                if(userList.size ()>=1){
                    LoginActivity.thisuser = userList.get(0);
                    authenticate = true;
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Return if authenticated
        return authenticate;
    }

    /**
     * @param str_username
     *
     * checks for a valid username (empty field)
     * @returns true if the field is not empty and false if it is empty
     * */
    protected boolean validUser(String str_username) {
        boolean valid = false;
        if (str_username.length() == 0) {
            valid = false;
        } else {
            valid = true;

        }
        return valid;
    }
}

