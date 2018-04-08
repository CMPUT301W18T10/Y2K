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

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    public static ArrayAdapter<Task> requestedRAdapter;
    public static ArrayAdapter<Task> biddedRAdapter;
    public static ArrayAdapter<Task> assignedRAdapter;
    public static ArrayAdapter<Task> biddedPAdapter;
    public static ArrayAdapter<Task> assignedPAdapter;


    public static ArrayList<Task> tasksR=  new ArrayList<Task>();;
    public static ArrayList<Task> requestedRtasks;
    public static ArrayList<Task> biddedRtasks;
    public static ArrayList<Task> assignedRtasks;
    public static ArrayList<Task> biddedPtasks;
    public static ArrayList<Task> assignedPtasks;


    public static final String POINTER = "Task_Position";

    //Provider ListViews
    private ListView biddedPListView;
    private ListView assignedPListView;

    //Requester ListViews
    private ListView requestedRListView;
    private ListView biddedRListView;
    private ListView assignedRListView;
    private String status;



    @Override
    /**
     * onCreate we'll show the user the the list of tasks requested and provided and the title
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //UNDERLINE Titles

        TextView requestTitle = findViewById(R.id.title2);
        TextView distributeTitle = findViewById(R.id.title1);
        TextView title1 = findViewById(R.id.rRequested);
        TextView  title2= findViewById(R.id.rBidded);
        TextView title3 = findViewById(R.id.rAssigned);
        TextView title4 = findViewById(R.id.pBidded);
        TextView title5= findViewById(R.id.pAssigned);

        requestTitle.setPaintFlags(requestTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        distributeTitle.setPaintFlags(distributeTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title1.setPaintFlags(title1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title2.setPaintFlags(title2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title3.setPaintFlags(title3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title4.setPaintFlags(title4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title5.setPaintFlags(title5.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //views for the lists
        requestedRListView = findViewById(R.id.requestedRList);
        biddedRListView = findViewById ( R.id.biddedRList );
        assignedRListView = findViewById ( R.id.assignedRList );

        biddedPListView = findViewById(R.id.biddedPList);
        assignedPListView = findViewById ( R.id.assignedPList );

    }

    /**
     * onStart, continuously update the list adapters
     */
    protected void onStart(){
        super.onStart();
        if (ElasticSearchController.connected ()){
            new NotifyUser().Display(HomeActivity.this);
        }


        //REQUESTER
        requestedRtasks= new ArrayList<Task> (  );
        biddedRtasks= new ArrayList<Task> (  );
        assignedRtasks = new ArrayList<Task> (  );

        //Provider
        biddedPtasks= new ArrayList<Task> (  );
        assignedPtasks= new ArrayList<Task> (  );

        if(ElasticSearchController.connected ()){
            //Update the task status
            try {
                update();
            } catch (ExecutionException e) {

                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }

        }
        else{
            //Load tasks from elastic search
            try {
                loadTasksNotConnected();
            } catch (ExecutionException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }

        }

        for(int i=0; i < tasksR.size (); i++) {
            if (tasksR.get ( i ).getStatus ().equals ( "requested" )) {
                requestedRtasks.add ( tasksR.get ( i ) );
            } else if (tasksR.get ( i ).getStatus ().equals ( "bidded" )) {
                biddedRtasks.add ( tasksR.get ( i ) );
            } else if (tasksR.get ( i ).getStatus ().equals ( "assigned" )) {

                assignedRtasks.add ( tasksR.get ( i ) );
            }
            status = tasksR.get(i).getStatus();
        }

        //Provider
        loadTaskListProviderBidded ();
        loadTaskListProviderAssigned ();


        //Set the adapter
        requestedRAdapter= new TaskAdapter ( this, requestedRtasks );
        biddedRAdapter= new TaskAdapter ( this, biddedRtasks );
        assignedRAdapter= new TaskAdapter ( this, assignedRtasks );

        biddedPAdapter= new TaskAdapter ( this, biddedPtasks );
        assignedPAdapter= new TaskAdapter ( this, assignedPtasks );

        //Set the list views
        requestedRListView.setAdapter ( requestedRAdapter );
        biddedRListView.setAdapter ( biddedRAdapter );
        assignedRListView.setAdapter ( assignedRAdapter );

        biddedPListView.setAdapter ( biddedPAdapter );
        assignedPListView.setAdapter ( assignedPAdapter );
    }


    /**
     * Loads in the tasks for a user
     * Grab the list of tasks we provided that we bidded on
     */
    private static void loadTaskListProviderBidded(){
        ArrayList<Task> allTasks= new ArrayList<Task> (  );
        ArrayList<Bid> allBids = new ArrayList<Bid> (  );
        ElasticSearchController.getBids bids = new ElasticSearchController.getBids ();

        try {
            bids.execute("", LoginActivity.thisuser.getUsername ());

            allBids= bids.get();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }

        //Grab all the bids that the task associated to the bid is not assigned
        for (int i=0;i<allBids.size ();i++){
            if (!allBids.get ( i ).getTask ().getStatus ().equals ( "assigned" )){
                //Update task status
                allTasks.add ( allBids.get ( i ).getTask ());
            }
        }

        Log.e("bidsP", allBids.toString ());
        //Set the bidded Provider tasks
        biddedPtasks=allTasks;
    }


    /**
     * Loads in the tasks for a user
     * Grab the list of tasks we provided that were assigned
     */
    private static void loadTaskListProviderAssigned(){
        ArrayList<Task> allTasksList = new ArrayList<Task> (  );
        ArrayList<Task> taskList = new ArrayList<Task> (  );
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks ();

        try {
            tasks.execute("", "");
            allTasksList= tasks.get ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }

        for(int i=0; i<allTasksList.size ();i++){
            Log.e("e", allTasksList.get ( i ).getStatus ());
            if(allTasksList.get ( i ).getStatus ().equals ( "assigned" )){
                if(allTasksList.get ( i ).getProvider ().getUsername ().equals ( LoginActivity.thisuser.getUsername () )){
                    taskList.add(allTasksList.get ( i ));
                }
            }
        }

        Log.e("assignedP", taskList.toString ());
        //Set the Assigned Provider tasks
        assignedPtasks=taskList;
    }

    /**
     * updates the status of all tasks
     */
    public static void update() throws ExecutionException, InterruptedException {
        ArrayList<Task> allTasks= new ArrayList<Task> (  );
        ArrayList<Task> myTasks= new ArrayList<Task> (  );
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks ();
        tasks.execute ("","");


        allTasks= tasks.get();
        for (int i=0; i < allTasks.size (); i++){
            //Update the status of a task

            ArrayList <Bid> allBids= new ArrayList<Bid> (  );
            ElasticSearchController.getBids bids= new ElasticSearchController.getBids ();
            bids.execute ( allTasks.get ( i ).getTitle (), "" );
            allBids= bids.get();

            //If the task is complete then delete the task and bids associated to it
            if (allTasks.get ( i ).getComplete ()){
                ElasticSearchController.deleteTask deleteTask= new ElasticSearchController.deleteTask ();
                deleteTask.execute ( allTasks.get ( i ).getTitle () );

                for(int j=0; j<allBids.size ();j++){
                    ElasticSearchController.deleteBid deleteBid= new ElasticSearchController.deleteBid ();
                    deleteBid.execute ( allBids.get ( j ).getTask ().getTitle (), allBids.get ( j ).getBidUserName () );
                }
            }


            //Set the status to assigned if provider for that task is not null
            else if(allTasks.get ( i ).getProvider() != null ) {
                    Task tempTask = new Task(allTasks.get(i).getTitle(), allTasks.get(i).getDescription(), allTasks.get(i).getRequester(), "assigned", allTasks.get(i).getProvider(), allTasks.get(i).getLat(), allTasks.get(i).getLong());
                    ElasticSearchController.updateTask(allTasks.get(i), tempTask);
            }


            //Set the status to bidded if there exist a bid on that task and its not assigned
            else if (allBids.size ()!=0 ) {
                for (int j = 0; j < allBids.size(); j++) {

                    String title= allBids.get ( i ).getTask ().getTitle ();
                    String desc=allBids.get ( i ).getTask ().getDescription ();
                    User requester= allBids.get ( i ).getTask ().getRequester ();

                    Double longitude = 65.232312;
                    Double latitudde = 117.834 ;

                    Task newTask= new Task(title,desc, requester,  "bidded", null, latitudde,longitude);
                    ElasticSearchController.updateTask (allTasks.get(i), newTask);
                }
            }


            //Set the status to requested if there no bid
            else if(allBids.size ()==0) {
                Log.e("title", allTasks.get(i).getTitle());

                    Task tempTask = new Task(allTasks.get(i).getTitle(), allTasks.get(i).getDescription(), allTasks.get(i).getRequester(), "requested", null, allTasks.get(i).getLat(), allTasks.get(i).getLong());
                    ElasticSearchController.updateTask(allTasks.get(i), tempTask);

            }

            //Wait a bit for changes to sync
            long num=300;
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }

            if( LoginActivity.thisuser.getUsername ().equals ( allTasks.get ( i ).getRequester ().getUsername () )){
                myTasks.add( allTasks.get ( i ));
            }
        }

        tasksR= myTasks;

        //TO set back to bidded from assigned, there must not be a provider
        //TO set back to requested from bidded or assigned, there must not exist a bid on the task
        //if done delete the task
    }


    /**
     * Loads tasks when not connected to the network
     */
    public void loadTasksNotConnected() throws ExecutionException, InterruptedException {
        ArrayList<Task> allTasks= new ArrayList<Task> (  );
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks ();
        tasks.execute ("","");
        allTasks= tasks.get();
        tasksR= allTasks;
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



    /**
     * User is directed to the code page to write code
     * @param view
     */
    public void codeBtn(View view){
        //Wait a bit for changes to sync
        if (status == "assigned") {
            long num = 300;
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, CodeProvider.class);

            startActivity(intent);
        }

        else {
            Toast.makeText(HomeActivity.this,"You have no assigned tasks",Toast.LENGTH_LONG);
        }
    }
}
