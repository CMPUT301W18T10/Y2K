package com.example.syn_tax;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.*;


/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestElasticSearchController extends ActivityInstrumentationTestCase2 {
    public void testOnCreate() throws Exception {
    }

    public void testUpdateItem() throws Exception {
    }

    public void testUpdateUser() throws Exception {
    }

    public void testGetContext() throws Exception {
    }

    // Test to make sure UserR’s offline changes to tasks, to be displayed when they regain connectivity.
    private static JestDroidClient client;

    public TestElasticSearchController(){
        super(ElasticSearchController.class);
    }



    //Test to make sure were adding tasks to the database successfully
    public void testAddTask(){
        User testuser= new User("hamsemare", "test@g.com", "000-0000-0000");
        Task task= new Task("Assignment", "Complete a coding project", testuser, "Requested");
        ElasticSearchController.addTasks addtask = new ElasticSearchController.addTasks();
        addtask.execute(task);
    }



    //Test to make sure were getting tasks from the database successfully
    public class testGetTasks extends AsyncTask<String, Void, ArrayList<Task>>{

        @Override
        protected ArrayList<Task> doInBackground(String... strings) {
            testverifySettings();
            testAddTask();
            ArrayList<Task> tasks = new ArrayList<Task>();

            // TODO Build the query
            String keywords= "{\" from\" : 0, \"size\" : 10}";
            io.searchbox.core.Search search = new io.searchbox.core.Search.Builder(keywords).addIndex("test").addType("task").build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Task> foundTask= result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTask);
                    }
                else{
                    Log.i("Error", "Failed when searching for tasks");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");

            }
            return tasks;
        }
    }

    // Test to make sure were successfully connected to the Database
    public static void testverifySettings() {
        // TODO: Test the connection to the server
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
