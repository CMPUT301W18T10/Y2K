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
        Task testTask = new Task("get status","new task",testUser,"requested");

        testTask.setStatus("bidded");
        assertEquals("bidded", testTask.getStatus());

        User testUser2 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask2 = new Task("get status","get status",testUser, "requested");

        testTask2.setStatus("assigned");
        assertEquals("assigned", testTask2.getStatus());

        User testUser3 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask3 = new Task("get status","get status",testUser,"requested");

        testTask3.setStatus("done");
        assertEquals("done", testTask3.getStatus());

        User testUser4 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask4 = new Task("get status","get status",testUser,"requested");

        testTask4.setStatus("requested");
        assertEquals("requested", testTask4.getStatus());

    }


}

