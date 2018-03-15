package com.example.syn_tax;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;




//Citations: Used
// https://stackoverflow.com/questions/30343011/how-to-check-if-an-android-device-is-online  ( March 14,2018)
// https://developer.android.com/reference/java/io/FileOutputStream.html (March 14,2018)

/**
 * Created by Hamsemare on 2018-02-22.
 */


/**
 * Copyright Version 2.0, January 2004  http://www.apache.org/licenses/

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

//Controls access to the database.
public class ElasticSearchController extends Application {

    //Attributes

    private static Context context;
    private static Gson gson;
    private static JestDroidClient client;
    private static ArrayList<Task> taskList= new ArrayList<Task>();
    private static ArrayList<User> usersList= new ArrayList<User>();

    private static final String fileTasks="file1";


    //Constructor for the class
    //load from the local files and add to the database if we connect to the server
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

    }


    public ElasticSearchController(){

        if (connected()==true){
            loadFromFileTasks(fileTasks);

            for(int i = 0; i < taskList.size(); i++) {
                Task task= taskList.get(i);
                AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks();
                execute.execute(task);
            }

            //Empty the  list od tasks because we synced it to the database
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

    //TASKS

    //If were not connected to the server load from the local file
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

    //If were not connected to the server write to a local file
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
    //GETTER METHODS:

    //Get the tasks
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
            else {

                // TODO Build the query
                Search search = new Search.Builder(search_parameters[0]).addIndex("syn-tax").addType("task").build();

                try {
                    // TODO get the results of the query
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Task> foundTask = result.getSourceAsObjectList(Task.class);
                        tasks.addAll(foundTask);
                    } else {
                        Log.i("Error", "Elasticseach was not able to excute");
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
                return tasks;
            }
        }
    }

    //Get the user
    // TODO we need a function which gets task from elastic search
    public static class getUsers extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if(connected() == false){
                return users;
            }

            // TODO Build the query
            Search search = new Search.Builder(search_parameters[0]).addIndex("syn-tax").addType("user").build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<User> foundUsers= result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                }
                else{
                    Log.i("Error", "Elasticseach was not able to excute");
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
    //ADD METHODS:

    //ADD TASK
    // TODO we need a function which adds a Task to elastic search
    public static class addTasks extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected()){


                verifySettings();

                for (Task task : tasks) {
                    Index index = new Index.Builder(task).index("syn-tax").type("task").build();

                    try {

                        // where is the client?
                        DocumentResult result= client.execute(index);
                        if(result.isSucceeded()){
                            task.setId(result.getId());
                        }
                        else{
                            Log.i("Error", "Elasticseach was not able to excute");
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

    //ADD USER
    // TODO we need a function which adds a User to elastic search
    public static class addUsers extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {

            //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
            if (connected()) {
                verifySettings();
                for (User user : users) {
                    Index index = new Index.Builder(user).index("syn-tax").type("user").build();

                    try {
                        // where is the client?
                        DocumentResult result = client.execute(index);
                        if (result.isSucceeded()) {
                            user.setId(result.getId());
                        } else {
                            Log.i("Error", "Elasticseach was not able to excute");
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Log.i("error here",e.toString());
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

    //Update the Item
    //If connected to the database, delete the old task, and replace with the new task
    public void updateItem(Task task1, Task task2){
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected()){
            //ElasticSearchController.deleteTasks delete = new ElasticSearchController().deleteTasks();
            //delete.execute(task1);

            AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks();
            execute.execute(task2);
        }
    }

    //Update the user
    //If connected to the database, delete the old user, and replace with the new user
    public void updateUser(User user1, User user2){
        //FIRST CHECK TO SEE IF WERE CONNECTED TO THE DATABASE
        if (connected()){
            //ElasticSearchController.deleteUsers delete = new ElasticSearchController().deleteUsers();
            //delete.execute(user1);

            AsyncTask<User, Void, Void> execute = new ElasticSearchController.addUsers();
            execute.execute(user2);
        }
    }



    //*************************************************************************************************************************/
    //*************************************************************************************************************************/
    // DELETE METHODS TO DELETE TASKS AND BIDS

    //Delete a task

    //Delete a user



    //*************************************************************************************************************************/
    //*************************************************************************************************************************/
    //Connect to the database
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }





    //Check Connectivity
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

    public static class addTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            return null;
        }
    }
}
