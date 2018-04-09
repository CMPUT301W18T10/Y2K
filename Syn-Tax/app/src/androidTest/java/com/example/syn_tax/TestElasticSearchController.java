package com.example.syn_tax;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.*;


/*
Citations:
https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html (March 16, 2018)
*/

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestElasticSearchController extends ActivityInstrumentationTestCase2 {
    private static Context mContext;

    /**
     * Gets the context of ElasticSearchController.getContext
     *
     * @throws Exception
     */
    public void testOnCreate() throws Exception {
        mContext = ElasticSearchController.getContext ();
    }


    /**
     * Test the update user
     * Test to make sure were updating the user in the database successfully
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testUpdateUser() throws ExecutionException, InterruptedException {
        User testuser = new User ( "hamsemare", "test@g.com", "000-0000-0000" );

        ElasticSearchController.addUsers addUser = new ElasticSearchController.addUsers ();
        addUser.execute ( testuser );

        User testnewuser = new User ( "hamsemare", "hamse@g.com", "999-000-0000" );
        ElasticSearchController.updateUser ( testuser, testnewuser );

        ElasticSearchController.getUsers users = new ElasticSearchController.getUsers ();
        users.execute ( "", "" );
        ArrayList<User> allUsers;
        allUsers = users.get ();

        boolean state = false;
        for (int i = 0; i < allUsers.size (); i++) {
            if (allUsers.get ( i ).getUsername ().equals ( testuser.getUsername () )) {
                state = true;
            }
        }

//        assertTrue ( state );
    }

    /**
     * @throws Exception
     */
    public void testGetContext() throws Exception {
    }

    // Test to make sure UserR’s offline changes to tasks, to be displayed when they regain connectivity.
    private static JestDroidClient client;


    /**
     * Constructor, calls the the constructor of the ElasticSearchController class
     */
    public TestElasticSearchController() {
        super ( ElasticSearchController.class );
    }


    /**
     * Test the add Task
     * Test to make sure were adding tasks to the database successfully
     * Also tests for the getTasks class
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testAddTask() throws ExecutionException, InterruptedException {
        User testuser = new User ( "hamsemare", "test@g.com", "000-0000-0000" );

        Task task = new Task ( "Assignment", "Complete a coding project", testuser, "requested",  null, 0.0, 0.0 );

        ElasticSearchController.addTasks addtask = new ElasticSearchController.addTasks ();
        addtask.execute ( task );

        ElasticSearchController.getTasks tasks = new ElasticSearchController.getTasks ();
        tasks.execute ( "", "" );
        ArrayList<Task> allTasks;
        allTasks = tasks.get ();

        boolean state = false;

        for (int i = 0; i < allTasks.size (); i++) {
            if (allTasks.get ( i ).getTitle ().equals ( task.getTitle () )) {
                state = true;
            }
        }

        //DO we have the task still in there
//        assertTrue ( !state );
    }

    /**
     * Test the add User (Create account)
     * Test to make sure were adding users to the database successfully
     * Also tests for the get Users class
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testAddUser() throws ExecutionException, InterruptedException {
        User testuser = new User ( "hamsemare", "test@g.com", "000-0000-0000" );

        ElasticSearchController.addUsers addUser = new ElasticSearchController.addUsers ();
        addUser.execute ( testuser );

        ElasticSearchController.getUsers users = new ElasticSearchController.getUsers ();
        users.execute ( "", "" );
        ArrayList<User> allUsers;
        allUsers = users.get ();

        boolean state = false;
        for (int i = 0; i < allUsers.size (); i++) {
            if (allUsers.get ( i ).getUsername ().equals ( testuser.getUsername () )) {
                state = true;
            }
        }

        assertTrue ( state );
    }

    /**
     * Test to make sure were getting tasks from the database successfully
     */
    public class testGetTasks extends AsyncTask<String, Void, ArrayList<Task>> {

        @Override
        protected ArrayList<Task> doInBackground(String... strings) {
            testverifySettings ();
            try {
                testAddTask ();
            } catch (ExecutionException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            ArrayList<Task> tasks = new ArrayList<Task> ();

            // TODO Build the query
            String keywords = "{\" from\" : 0, \"size\" : 10}";
            io.searchbox.core.Search search = new io.searchbox.core.Search.Builder ( keywords ).addIndex ( "test" ).addType ( "task" ).build ();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute ( search );
                if (result.isSucceeded ()) {
                    List<Task> foundTask = result.getSourceAsObjectList ( Task.class );
                    tasks.addAll ( foundTask );
                } else {
                    Log.i ( "Error", "Failed when searching for tasks" );
                }
            } catch (Exception e) {
                Log.i ( "Error", "Something went wrong when we tried to communicate with the elasticsearch server!" );

            }
            return tasks;
        }
    }



    /**
     * Test to make sure were successfully connected to the Database
     */
    public static void testverifySettings() {
        // TODO: Test the connection to the server
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder ( "http://cmput301.softwareprocess.es:8080" );
            DroidClientConfig config = builder.build ();
            JestClientFactory factory = new JestClientFactory ();
            factory.setDroidClientConfig ( config );
            client = (JestDroidClient) factory.getObject ();
        }
    }

    /**
     * Test for Connectivity
     */
    public void testConnectivity() {

        Boolean available = false;

        if (mContext == null) {
            return;
        }

        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService ( Context.CONNECTIVITY_SERVICE );
        assert manager != null;
        NetworkInfo network = manager.getActiveNetworkInfo ();

        if (network != null && network.isConnected ()) {
            available = true;
        }
        assertEquals ( available.toString (), ElasticSearchController.connected () );
    }
}
