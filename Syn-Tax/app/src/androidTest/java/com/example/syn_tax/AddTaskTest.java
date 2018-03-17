package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by hamdamare on 2018-02-22.
 */
public class AddTaskTest extends ActivityInstrumentationTestCase2 {

    /**
     * Constructor, calls the constructor of AddTaskActivity
     */
    public AddTaskTest() {
        super(AddTaskActivity.class);
    }


    /**
     * Test to make sure user, we can add tasks
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testAddTask() throws ExecutionException, InterruptedException {
        String title = "test";
        String description = "testdesc";
        String status = "Requested";
        String photoname = "photoname";

        User testuser = new User("hamare","test@g.ca","000-000-0000");
        Task task= new Task(title, description, testuser, status);

        //Add it
        ElasticSearchController.addTasks addtasks= new ElasticSearchController.addTasks ();
        addtasks.execute ( task );

        //Get it
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks();
        tasks.execute ("");
        ArrayList<Task> allTasks;
        allTasks=tasks.get();
        boolean state=false;

        for(int i=0; i< allTasks.size(); i++){
            if(allTasks.get ( i).getTitle ().equals ( task.getTitle ()  )){
                state=true;
            }
        }

        //DO we have the task still in there
        assertTrue(state);
    }


    /**
     * Test to make sure that user, we can get their tasks
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testGetTask() throws ExecutionException, InterruptedException {
        User testUser = new User("hamda", "test@g.ca", "000-000-0000");
        Task testtaskR1 = new Task("hamda", "desc", testUser, "requested");

        //Add it
        ElasticSearchController.addTasks addtasks= new ElasticSearchController.addTasks ();
        addtasks.execute ( testtaskR1 );

        //Get it
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks();
        tasks.execute ("");
        ArrayList<Task> allTasks;
        allTasks=tasks.get();
        boolean state=false;

        for(int i=0; i< allTasks.size(); i++){
            if(allTasks.get ( i).getTitle ().equals ( testtaskR1.getTitle ()  )){
                state=true;
            }
        }

        //DO we have the task still in there
        assertTrue(state);
    }

    /**
     * EDIT A TASK
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testEditTask() throws ExecutionException, InterruptedException {
        User testUser = new User("hamda", "test@g.ca", "000-000-0000");
        Task testtask = new Task("hamda", "desc", testUser, "requested");

        //Add it
        ElasticSearchController.addTasks addtasks= new ElasticSearchController.addTasks ();
        addtasks.execute ( testtask);

        //update it
        Task testtaskR1= new Task("Hamse", "", testUser, "requested");
        ElasticSearchController.updateTask (testtask, testtaskR1);


        //Check if its not in there
        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks();
        tasks.execute ("");
        ArrayList<Task> allTasks;
        allTasks=tasks.get();
        boolean state=false;

        for(int i=0; i< allTasks.size(); i++){
            if(allTasks.get ( i).getTitle ().equals ( testtaskR1.getTitle ()  )){
                state=true;
            }
        }

        //DO we have the task still in there
        assertTrue(state);
    }


    /**
     * Test the status change of each of the task. options are "Bidded", "Assigned", "Done" and "Requested"
     */
    public void testStatus(){

        User testUser = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask = new Task("get status","get status",testUser, "requested");

        testTask.setStatus("Bidded");
        assertEquals("Bidded", testTask.getStatus());

        User testUser2 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask2 = new Task("get status","get status",testUser2, "requested");

        testTask2.setStatus("Assigned");
        assertEquals("Assigned", testTask2.getStatus());

        User testUser3 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask3 = new Task("get status","get status",testUser3, "requested");

        testTask3.setStatus("Done");
        assertEquals("Done", testTask3.getStatus());

        User testUser4 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask4 = new Task("get status","get status",testUser4, "requested");

        testTask4.setStatus("Requested");
        assertEquals("Requested", testTask4.getStatus());

    }

}
