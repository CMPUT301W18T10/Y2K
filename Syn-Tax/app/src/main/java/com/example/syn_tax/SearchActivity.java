package com.example.syn_tax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


/**
 * SearchActivity Class
 *
 * Feb 22, 2018
 *
 * Search for a task
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
public class SearchActivity extends AppCompatActivity {
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listOfTasks = findViewById(R.id.searches);
        EditText keywords= findViewById(R.id.keywords);
        Button search = (Button) findViewById(R.id.search);
    }

    /**
     * User searches passing in a keyword to match a task title
     * @param keywords String
     */
    public void searching(String keywords) {
        //DO SOMETHING
    }

    /**
     * User directed to the searchActivity
     * @param view
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the HomeActivity
     * @param view
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the UserProfileActivity
     * Also passes in the user information of the user
     * @param view
     */
    public void userInfo(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
}
