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
 * Last Modified 3/17/18 4:58 PM
 */

package com.example.syn_tax;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * HomeActivity Class
 *
 * Feb 21, 2018
 *
 * This class is the home activity class, it displays
 * Two listviews with clickable list elements for the requested tasks
 * and distributed tasks of the logged in user
 *
 * @see Task
 * @see TaskAdapter
 * @see ElasticSearchController
 * @see AddTaskActivity
 * @see HomeActivity
 * @see SearchActivity
 * @see UserProfileActivity
 * @see LoginActivity
 */
public class HomeActivity extends AppCompatActivity {
    public static ArrayAdapter<Task> requestedAdapter;
    public static ArrayList<Task> requestedTasks=  new ArrayList<Task>();;
    public static ArrayAdapter<Task> distributedAdapter;
    public static ArrayList<Task> distributedTasks;
    public static final String POINTER = "Task_Position";
    private ListView requestedListView;
    private ListView distributedListView;


    @Override
    /**
     * onCreate we'll show the user the the list of tasks requested and provided and the title
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        TextView requestTitle = findViewById(R.id.requestCodeTitle);
        TextView distributeTitle = findViewById(R.id.distributeCodeTitle);

        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        requestTitle.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        distributeTitle.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //views for the 2 separate lists
        requestedListView = (ListView) findViewById(R.id.requestlist);
        distributedListView = (ListView) findViewById(R.id.distributelist);
        distributedTasks = new ArrayList<Task>();

    }

    /**
     * onStart, continuously update the list adapters
     */
    protected void onStart(){
        super.onStart();

        loadTaskListRequester();
        requestedAdapter = new TaskAdapter(this,requestedTasks);
        distributedAdapter = new TaskAdapter(this, distributedTasks);
        requestedListView.setAdapter(requestedAdapter);
        distributedListView.setAdapter(distributedAdapter);
    }

    /**
     * Loads in the tasks for a user
     * Grab the list of tasks we requested
     */
    private static void loadTaskListRequester(){
        ArrayList<Task> allTasksList;
        ElasticSearchController.getTasks allTasks = new ElasticSearchController.getTasks ();

        try {
            allTasks.execute("", LoginActivity.thisuser.getUsername ());
            allTasksList = allTasks.get();
            //IF CONNECTED THEN UPDATE
            //ELSE add the new tasks to the requested tasks
            if(allTasksList.size()>0){
                requestedTasks=allTasksList;
            }

            else{
                requestedTasks= new ArrayList<Task> (  );
            }
            Log.e("sss", requestedTasks.toString ());
        }

        catch(Exception e){
            allTasksList = new ArrayList<Task>();
        }
    }


    /**
     * Button click for adding a new task
     * @param view is the current view
     * Go to AddTaskActivity page
     */
    public void addTaskBtn(View view){
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("status", "");
        startActivity(intent);
    }

    /**
     * Button click for going to search activity
     * @param view is the current view
     * Go to the SearchActivity page
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * button click for going to the home activity
     * @param view is the current view
     * Go to the HomeActivity page
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * button click for a user profile
     * @param view is the current view
     * Go to the UserProfileActivity
     */
    public void userInfo(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
}
