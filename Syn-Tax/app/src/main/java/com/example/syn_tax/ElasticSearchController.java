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

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;



/*
Citations: Used
https://stackoverflow.com/questions/30343011/how-to-check-if-an-android-device-is-online  ( March 14,2018)
https://developer.android.com/reference/java/io/FileOutputStream.html (March 14,2018)
https://www.programcreek.com/java-api-examples/index.php?api=io.searchbox.core.DeleteByQuery (March 17,2018)
https://stackoverflow.com/questions/3875184/cant-create-handler-inside-thread-that-has-not-called-looper-prepare
*/

/**
 * Created by Hamsemare on 2018-02-22.
 */



/**
 * ElasticSearchController Class
 *
 * Feb 22, 2018
 *
 * Controls access to the database.
 *
 * @see Gson
 * @see User
 * @see Task
 *
 *
 *
 */
public class ElasticSearchController extends Application {

    //Attributes

    private static Context context;
    private static Gson gson;
    private static JestDroidClient client;
    private static ArrayList<Task> taskList = new ArrayList<Task> ();
    private static final String fileTasks = "file1";
    private static Boolean taskChange=false;


    /**
     * load from the local files and add to the database if we connect to the server
     */
    public void onCreate() {
        super.onCreate ();
        context = getApplicationContext ();
    }


    /**
     * Constructor for the class
     */
    public ElasticSearchController() {

    }


    //*************************************************************************************************************************/
    //*************************************************************************************************************************/
    //******** SAVING TO AND LOADING FROM LOCAL FILES *************************************************************************/
    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * TASKS
     * SAVING TO AND LOADING FROM LOCAL FILES
     * If were not connected to the server load from the local file
     *
     * @param filename
     */
    private static void loadFromFileTasks(String filename) {
        try {
            //Grab file
            FileInputStream file = ElasticSearchController.context.openFileInput ( filename );

            BufferedReader in = new BufferedReader ( new InputStreamReader ( file ) );
            Gson gson = new Gson ();

            Type listType = new TypeToken<ArrayList<Tasks>> () {
            }.getType ();
            taskList = gson.fromJson ( in, listType );
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            taskList = new ArrayList<Task> ();
        }
    }

    /**
     * If were not connected to the server write to a local file
     *
     * @param filename
     */
    private static void saveInFileTasks(String filename) {
        try {
            //Grab file
            FileOutputStream file1 = ElasticSearchController.context.openFileOutput ( filename, 0 );

            BufferedWriter out = new BufferedWriter ( new OutputStreamWriter ( file1 ) );

            Gson gson = new Gson ();
            gson.toJson ( taskList, out );

            out.flush ();
            file1.close ();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
            throw new RuntimeException ();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
            throw new RuntimeException ();
        }
    }


    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * GETTER METHODS:
     * Get the tasks
     * search parameters[0] name
     * search parameters[1] username associated
     */
    // TODO we need a function which gets task from elastic search
    public static class getTasks extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {

            verifySettings ();
            ArrayList<Task> tasks = new ArrayList<Task> ();



            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (!connected ()) {
                HomeActivity.tasksR.addAll ( taskList );
                taskList = new ArrayList<Task> ();

                return HomeActivity.tasksR;
            }

            //If there was a change when offline then update tasks in the server
            else if(taskChange && connected ()) {
                Log.e ( "s", "hh" );

                //Get all old tasks and delete, then add new tasks
                ElasticSearchController.deleteOld old= new ElasticSearchController.deleteOld ();
                String username= LoginActivity.thisuser.getUsername ();
                old.execute (username);

                for(int i=0; i< HomeActivity.tasksR.size ();i++){
                    Task task= HomeActivity.tasksR.get(i);

                    ElasticSearchController.addTasks add= new ElasticSearchController.addTasks ();
                    add.execute ( task );
                }
                //Change status back to false
                taskChange=false;
                //done updating
            }

            //If were connect to the network get tasks or a specific task
            //String for the search
            String searchString;

            //Get all
            if (Objects.equals ( search_parameters[0], "" ) && Objects.equals ( search_parameters[1], "" )) {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            }

            //Get the tasks of a particular task
            else if (Objects.equals ( search_parameters[0], "" )) {
                searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[1] + "\"}}}";
            }

            //Get the tasks of a task
            else {
                searchString = "{\"query\":{\"match\":{\"title\":\"" + search_parameters[0] + "\"}}}";
            }

            // TODO Build the query
            Search search = new Search.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "tasks" ).build ();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute ( search );
                if (result.isSucceeded ()) {
                    List<Task> foundTask = result.getSourceAsObjectList ( Task.class );
                    tasks.addAll ( foundTask );
                } else {
                    Log.i ( "Error", "Elasticseach was not able to excute (get)" );
                }
            } catch (Exception e) {
                Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );

                new Handler ( Looper.getMainLooper()).post( new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext (), "ERROR WHEN RETRIEVING TASKS, REFRESH PAGE", Toast.LENGTH_LONG).show();

                    }
                });
            }
            return tasks;
        }
    }


    /**
     * Get the user
     */
    // TODO we need a function which gets task from elastic search
    public static class getUsers extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {

            verifySettings ();
            ArrayList<User> users = new ArrayList<User> ();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (!connected ()) {
                return users;
            }

            //String for the search
            String searchString;
            if (search_parameters[0] == "") {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            } else {
                searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";
            }
            // TODO Build the query
            Search search = new Search.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "users" ).build ();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute ( search );
                if (result.isSucceeded ()) {
                    List<User> foundUsers = result.getSourceAsObjectList ( User.class );
                    users.addAll ( foundUsers );
                } else {
                    Log.i ( "Error", "Elasticseach was not able to excute (get)" );
                }
            } catch (Exception e) {
                Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
            }
            return users;
        }
    }


    /**
     * Get the bids of a task
     */
    // TODO we need a function which gets the bids of a task from elastic search
    public static class getBids extends AsyncTask<String, Void, ArrayList<Bid>> {
        @Override
        protected ArrayList<Bid> doInBackground(String... search_parameters) {

            verifySettings ();
            ArrayList<Bid> bids = new ArrayList<Bid> ();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (!connected ()) {
                return bids;
            }

            //String for the search
            String searchString;
            if (search_parameters[0] == "") {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            }
            else {
                searchString = "{\"query\":{\"match\":{\"title\":\"" + search_parameters[0] + "\"}}}";
            }
            // TODO Build the query
            Search search = new Search.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "bids" ).build ();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute ( search );
                if (result.isSucceeded ()) {
                    List<Bid> foundBids = result.getSourceAsObjectList ( Bid.class );
                    bids.addAll ( foundBids );
                } else {
                    Log.i ( "Error", "Elasticseach was not able to excute (get)" );
                }
            } catch (Exception e) {
                Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
            }
            return bids;
        }
    }



    /**
     * GETTER METHODS:
     * Get the tasks
     * search parameters[0] name
     * search parameters[1] username associated
     */
    // TODO we need a function which gets task from elastic search
    public static class searchingTasks extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {

            verifySettings ();
            ArrayList<Task> tasks = new ArrayList<Task> ();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (!connected ()) {
                return tasks;
            }

            //If were connect to the network get tasks or a specific task
            //String for the search
            String searchString;

            //Get all
            if (Objects.equals ( search_parameters[0], "" )) {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            }

            //Get the tasks of a task
            else {
                searchString = "{\"query\":{\"match\":{\"description\":\"" + search_parameters[0] + "\"}}}";
            }

            // TODO Build the query
            Search search = new Search.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "tasks" ).build ();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute ( search );
                if (result.isSucceeded ()) {
                    List<Task> foundTask = result.getSourceAsObjectList ( Task.class );
                    tasks.addAll ( foundTask );
                }
                else {
                    Log.i ( "Error", "Elasticseach was not able to excute (get)" );
                }
            }
            catch (Exception e) {
                Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );

                new Handler ( Looper.getMainLooper()).post( new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext (), "ERROR WHEN RETRIEVING TASKS, REFRESH PAGE", Toast.LENGTH_LONG).show();

                    }
                });
            }
            return tasks;
        }
    }



    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * ADD METHODS:
     * ADD TASK
     */
    // TODO we need a function which adds a Task to elastic search
    public static class addTasks extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {

                verifySettings ();
                for (Task task : tasks) {
                    Index index = new Index.Builder ( task ).index ( "syn-tax" ).type ( "tasks" ).build ();

                    try {

                        // where is the client?
                        DocumentResult result = client.execute ( index );
                        if (result.isSucceeded ()) {
                            task.setId ( result.getId () );
                        } else {
                            Log.i ( "Error", "Elasticseach was not able to excute (add)" );
                        }

                    } catch (Exception e) {
                        Log.i ( "Error", "The application failed to build and send the tasks" );
                    }
                }

                return null;
            }
            taskChange=true;
            // ELSE WERE NOT CONNECTED WRITE TO LOCAL FILE
            taskList.addAll ( Arrays.asList ( tasks ) );
            return null;
        }
    }

    /**
     * ADD USER
     */
    // TODO we need a function which adds a User to elastic search
    public static class addUsers extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {
                verifySettings ();
                for (User user : users) {
                    Index index = new Index.Builder ( user ).index ( "syn-tax" ).type ( "users" ).build ();

                    try {
                        // where is the client?
                        DocumentResult result = client.execute ( index );
                        if (result.isSucceeded ()) {
                            user.setId ( result.getId () );
                        } else {
                            Log.i ( "Error", "Elasticseach was not able to excute for (add)" );
                        }

                    } catch (Exception e) {
                        e.printStackTrace ();

                        Log.i ( "Error", "The application failed to build and send the user" );
                    }
                }
            }
            return null;
        }
    }

    /**
     * ADD METHODS:
     * ADD Bid
     */
    // TODO we need a function which adds a Task to elastic search
    public static class addBids extends AsyncTask<Bid, Void, Void> {

        @Override
        protected Void doInBackground(Bid... bids) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {

                verifySettings ();
                for (Bid bid : bids) {
                    Index index = new Index.Builder ( bid ).index ( "syn-tax" ).type ( "bids" ).build ();

                    try {

                        // where is the client?
                        DocumentResult result = client.execute ( index );
                        if (result.isSucceeded ()) {
                            bid.setId ( result.getId () );
                        } else {
                            Log.i ( "Error", "Elasticseach was not able to excute (add)" );
                        }

                    } catch (Exception e) {
                        Log.i ( "Error", "The application failed to build and send the tasks" );
                    }
                }
            }
            return null;
        }
    }


    //*************************************************************************************************************************/
    //*************************************************************************************************************************/
    //UPDATE METHODS:

    /**
     * Update the Item
     * If connected to the database, delete the old task, and replace with the new task
     *
     * @param task1 oldTask
     * @param task2 newTask
     */
    public static void updateTask(Task task1, Task task2) {
        ElasticSearchController.deleteTask delete = new ElasticSearchController.deleteTask ();
        delete.execute ( task1.getTitle () );

        AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks ();
        execute.execute ( task2 );
    }


    /**
     * Update the user
     * If connected to the database, delete the old user, and replace with the new user
     *
     * @param user1 oldUser
     * @param user2 newUser
     */
    public static void updateUser(User user1, User user2) {
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected ()) {
            //Delete old one

            String username = user1.getUsername ();
            ElasticSearchController.deleteUser delete = new ElasticSearchController.deleteUser ();
            delete.execute ( username );

            //Add new one
            AsyncTask<User, Void, Void> execute = new ElasticSearchController.addUsers ();
            execute.execute ( user2 );
        }
    }

    /**
     * Update the Bid
     * If connected to the database, delete the old bid, and replace with the new bid
     *
     * @param bid1 oldBid
     * @param bid2 newUBid
     */
    public static void updateBid(Bid bid1, Bid bid2) {
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected ()) {

            //Delete old one
            String username= bid1.getBidUserName ();
            ElasticSearchController.deleteBid delete = new ElasticSearchController.deleteBid ();
            delete.execute ( username );

             //Add new one
             AsyncTask<Bid, Void, Void> execute = new ElasticSearchController.addBids ();
             execute.execute ( bid2 );
        }
    }


    //*************************************************************************************************************************/
    //*************************************************************************************************************************/


    /**
     * DELETE METHOD TO DELETE TASKS
     * Delete's a task
     */
    // TODO we need a function which gets task from elastic search
    public static class deleteTask extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {

            verifySettings ();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"title\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery task = new DeleteByQuery.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "tasks" ).build ();

                try {
                    // TODO delete the user
                    client.execute ( task );
                } catch (Exception e) {
                    Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
                }
            }
            else {
                // ELSE WERE NOT CONNECTED WRITE TO LOCAL FILE
                for (int i = 0; i < HomeActivity.tasksR.size (); i++) {
                    if (HomeActivity.tasksR.get ( i ).getTitle () == search_parameters[0]) {
                        HomeActivity.tasksR.remove ( i );
                    }
                }
                taskChange=true;
            }
            return null;
        }
    }

    /**
     * DELETE METHOD TO DELETE TASKS WHEN UPDATING
     * Delete's a task
     */
    // TODO we need a function which gets task from elastic search
    public static class deleteOld extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {

            verifySettings ();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery task = new DeleteByQuery.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "tasks" ).build ();

                try {
                    // TODO delete the user
                    client.execute ( task );
                } catch (Exception e) {
                    Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
                }
            }
            return null;
        }
    }

    /**
     * Delete's a user
     */
    // TODO we need a function which gets task from elastic search
    public static class deleteUser extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {

            verifySettings ();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery olduser = new DeleteByQuery.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "users" ).build ();

                try {
                    // TODO delete the user
                    client.execute ( olduser );
                } catch (Exception e) {
                    Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
                }
            }
            return null;
        }
    }


    /**
     * Delete's a bid by looking at the username associated to a bid
     */
    // TODO we need a function which gets task from elastic search
    public static class deleteBid extends AsyncTask<String, Void, ArrayList<Bid>> {
        @Override
        protected ArrayList<Bid> doInBackground(String... search_parameters) {

            verifySettings ();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected ()) {
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery oldBid = new DeleteByQuery.Builder ( searchString ).addIndex ( "syn-tax" ).addType ( "bids" ).build ();

                try {
                    // TODO delete the user
                    client.execute ( oldBid );
                } catch (Exception e) {
                    Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );
                }
            }
            return null;
        }
    }








    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * Connect's to the database
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder ( "http://cmput301.softwareprocess.es:8080" );
            DroidClientConfig config = builder.build ();

            JestClientFactory factory = new JestClientFactory ();
            factory.setDroidClientConfig ( config );
            client = (JestDroidClient) factory.getObject ();
        }
    }


    /**
     * Check's Connectivity
     *
     * @return true if were connected to the network and false if were not
     */
    public static boolean connected() {

        //Boolean to return whether or not were connected to the server
        boolean available = false;

        if (context == null) {
            return false;
        }

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService ( CONNECTIVITY_SERVICE );
        assert manager != null;
        NetworkInfo network = manager.getActiveNetworkInfo ();


        if (network != null && network.isConnected ()) {
            available = true;
        }

        return available;
    }

    /**
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

}
