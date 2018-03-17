package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by abdulazeezojetola on 2018-02-23.
 */

public class TestTaskStatus extends ActivityInstrumentationTestCase2 {

    public  TestTaskStatus(){
        super (Task.class);
    }

    // Test the status change of each of the task. options are "Bidded", "Assigned", "Done"
    // and "Requested"
    public void testStatus(){

        User testUser = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask = new Task("get status","new task",testUser,"Requested");

        testTask.setStatus("Bidded");
        assertEquals("Bidded", testTask.getStatus());

        User testUser2 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask2 = new Task("get status","get status",testUser, "Requested");

        testTask2.setStatus("Assigned");
        assertEquals("Assigned", testTask2.getStatus());

        User testUser3 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask3 = new Task("get status","get status",testUser,"Requested");

        testTask3.setStatus("Done");
        assertEquals("Done", testTask3.getStatus());

        User testUser4 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask4 = new Task("get status","get status",testUser,"Requested");

        testTask4.setStatus("Requested");
        assertEquals("Requested", testTask4.getStatus());

    }


}

