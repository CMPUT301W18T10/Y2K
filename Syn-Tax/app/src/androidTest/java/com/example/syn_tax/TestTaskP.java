package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */
public class TestTaskP extends ActivityInstrumentationTestCase2 {

    public TestTaskP() {
        super(Task.class);
    }

    //make sure that user can get their tasks
    public void testGetTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        TaskProvided testtaskR1 = new TaskProvided("hamda","desc",testUser);
        TaskProvided testtaskR2 = null;

        testtaskR2 = testtaskR2.getTask(testtaskR1);
        assertTrue(testtaskR2.hasTask(testtaskR2));

    }
    //make sure user can delete their tasks
    public void testDeleteTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        TaskProvided testtaskR1 = new TaskProvided("hamda","desc",testUser);
        AddTask testaddtask = new AddTask("hamda","desc","Requested");

        TaskProvided testtaskR2 = null;

        //we have a task
        testtaskR1.deleteTask(testtaskR2);

        assertFalse(testtaskR1.hasTask(testtaskR2));
    }
}