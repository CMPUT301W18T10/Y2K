package com.example.syn_tax;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;



/*
Citations: Used
https://stackoverflow.com/questions/30343011/how-to-check-if-an-android-device-is-online  ( March 14,2018)
https://developer.android.com/reference/java/io/FileOutputStream.html (March 14,2018)

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
 * Copyright GNU GENERAL PUBLIC LICENSE
    Version 3, 29 June 2007

    Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
    Everyone is permitted to copy and distribute verbatim copies
    of this license document, but changing it is not allowed.

    Permissions of this strong copyleft license are conditioned on making available complete
    source code of licensed works and modifications, which include larger works using a licensed work,
    under the same license. Copyright and license notices must be preserved. Contributors provide an
    express grant of patent rights.
 *
 */
public class ElasticSearchController extends Application {

    //Attributes

    private static Context context;
    private static Gson gson;
    private static JestDroidClient client;
    private static ArrayList<Task> taskList= new ArrayList<Task>();
    private static ArrayList<User> usersList= new ArrayList<User>();

    private static final String fileTasks="file1";


    /**
     * load from the local files and add to the database if we connect to the server
     */
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

    }


    /**
     * Constructor for the class
     */
    public ElasticSearchController(){

        if (connected()==true){
            loadFromFileTasks(fileTasks);

            for(int i = 0; i < taskList.size(); i++) {
                Task task= taskList.get(i);
                AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks();
                execute.execute(task);
            }

            //Empty the  list of tasks because we synced it to the database
            taskList.clear();

            for(int i = 0; i < usersList.size(); i++) {
                User user = usersList.get(i);

                AsyncTask<User, Void, Void> execute = new ElasticSearchController.addUsers();
                execute.execute(user);
            }
        }
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
     * @param filename
     */
    private static void loadFromFileTasks(String filename) {
        try {
            //Grab file
            FileInputStream file = ElasticSearchController.context.openFileInput(filename);

            BufferedReader in = new BufferedReader(new InputStreamReader(file));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Tasks>>(){}.getType();
            taskList = gson.fromJson(in, listType);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            taskList= new ArrayList<Task>();
        }
    }

    /**
     * If were not connected to the server write to a local file
     * @param filename
     */
    private static void saveInFileTasks(String filename) {
        try {
            //Grab file
            FileOutputStream file1 = ElasticSearchController.context.openFileOutput(filename, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(file1));

            Gson gson = new Gson();
            gson.toJson(taskList, out);

            out.flush();
            file1.close();

        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * GETTER METHODS:
     * Get the tasks
     */
    // TODO we need a function which gets task from elastic search
    public static class getTasks extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... search_parameters) {

            verifySettings();
            ArrayList<Task> tasks = new ArrayList<Task>();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if(!connected()){
                return tasks;
            }

            //If were connect to the network get tasks or a specific task
            //String for the search
            String searchString;

            if(Objects.equals(search_parameters[0], "")) {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            }
            else {
                searchString = "{\"query\":{\"match\":{\"task\":\"" + search_parameters[0] + "\"}}}";
            }
            // TODO Build the query
            Search search = new Search.Builder(searchString).addIndex("syn-tax").addType("tasks").build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundTask = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTask);
                } else {
                    Log.i("Error", "Elasticseach was not able to excute (get)");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
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

            verifySettings();
            ArrayList<User> users = new ArrayList<User>();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if(!connected()){
                return users;
            }

            //String for the search
            String searchString;
            if(search_parameters[0] == "") {
                searchString = "{\"from\" : 0, \"size\" : 500}";
            }

            else {
                searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";
            }
            // TODO Build the query
            Search search = new Search.Builder(searchString).addIndex("syn-tax").addType("users").build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<User> foundUsers= result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                }
                else{
                    Log.i("Error", "Elasticseach was not able to excute (get)");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return users;
        }
    }




    //*************************************************************************************************************************/
    //*************************************************************************************************************************/

    /**
     * ADD METHODS:
     *ADD TASK
     */
    // TODO we need a function which adds a Task to elastic search
    public static class addTasks extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected()){

                verifySettings();
                for (Task task : tasks) {
                    Index index = new Index.Builder(task).index("syn-tax").type("tasks").build();

                    try {

                        // where is the client?
                        DocumentResult result= client.execute(index);
                        if(result.isSucceeded()){
                            task.setId(result.getId());
                        }
                        else{
                            Log.i("Error", "Elasticseach was not able to excute (add)");
                        }

                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the tasks");
                    }
                }

                return null;
            }

            // ELSE WERE NOT CONNECTED WRITE TO LOCAL FILE
            taskList.addAll(Arrays.asList(tasks));
            //Call to save to file of tasks
            saveInFileTasks(fileTasks);
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
            if (connected()) {
                verifySettings();
                for (User user : users) {
                    Index index = new Index.Builder(user).index("syn-tax").type("users").build();

                    try {
                        // where is the client?
                        DocumentResult result = client.execute(index);
                        if (result.isSucceeded()) {
                            user.setId(result.getId());
                        }
                        else {
                            Log.i("Error", "Elasticseach was not able to excute for (add)");
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();

                        Log.i("Error", "The application failed to build and send the user");
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
    public static void updateTask(Task task1, Task task2){
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected()){
            ElasticSearchController.deleteTask delete = new ElasticSearchController.deleteTask();
            delete.execute(task1.getTitle());

            AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks();
            execute.execute(task2);
        }
    }

    /**
     * Update the user
     * If connected to the database, delete the old user, and replace with the new user

     * @param user1 oldUser
     * @param user2 newUser
     */
    public static void updateUser(User user1, User user2){
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected()){
            //Delete old one
            ElasticSearchController.deleteUser delete = new ElasticSearchController.deleteUser();
            delete.execute(user1.retrieveInfo().get(0));

            //Add new one
            AsyncTask<User, Void, Void> execute = new ElasticSearchController.addUsers();
            execute.execute(user2);
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

            verifySettings();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if(!connected()){
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"title\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery task= new DeleteByQuery.Builder(searchString).addIndex("syn-tax").addType("tasks").build();

                try {
                    // TODO delete the user
                    client.execute(task);
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
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

            verifySettings();
            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if(!connected()){
                //String for the search
                String searchString = "{\"query\":{\"match\":{\"username\":\"" + search_parameters[0] + "\"}}}";

                // TODO Build the delete by query
                DeleteByQuery user = new DeleteByQuery.Builder(searchString).addIndex("syn-tax").addType("users").build();

                try {
                    // TODO delete the user
                    client.execute(user);
                }
                catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
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
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


    /**
     * Check's Connectivity
     * @return true if were connected to the network and false if were not
     */
    public static boolean connected(){

        //Boolean to return whether or not were connected to the server
        boolean available=false;

        if (context == null){
            return false;
        }

        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo network=manager.getActiveNetworkInfo();


        if(network!= null && network.isConnected()){
            available=true;
        }

        return available;
    }

    /**
     * @return the context
     */
    public static Context getContext(){
        return context;
    }
}
