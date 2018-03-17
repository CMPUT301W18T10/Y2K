package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * MainActivity Class
 *
 * Feb 22, 2018
 *
 * This Class displays buttons to the user, directing the user to either
 * add a task, search for a task to do, go to homeActivity, or go to their userProfile
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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Button userButton = findViewById(R.id.usernameBtn);
        userButton.setText(LoginActivity.thisuser.retrieveInfo().get(0));
    }


    /**
     * Directs the user to the AddTaskActivity
     * @param view
     */
    public void requestCodeBtn(View view){
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("status", "");
        startActivity(intent);
    }

    /**
     * Directs the user to the SearchActivity
     * @param view
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * Directs the user to the HomeActivity
     * @param view
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Directs the user to the UserProfileActivity
     * Also passes in the user information of the user
     * @param view
     */
    public void userInfo(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
}
