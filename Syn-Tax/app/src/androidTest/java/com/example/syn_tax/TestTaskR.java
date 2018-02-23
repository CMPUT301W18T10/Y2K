package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */
public class TestTaskR  extends ActivityInstrumentationTestCase2 {

    public TestTaskR() {
        super(Task.class);
    }

    //make sure that user can get their tasks
    public void testGetTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        TaskRequested testtaskR1 = new TaskRequested("hamda","desc",testUser);
        TaskRequested testtaskR2 = null;

        testtaskR2 = testtaskR2.getTask(testtaskR1);
        assertTrue(testtaskR2.hasTask(testtaskR2));

    }
    //make sure user can delete their tasks
    public void testDeleteTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        TaskRequested testtaskR1 = new TaskRequested("hamda","desc",testUser);
        AddTask testaddtask = new AddTask("hamda","desc","Requested");

        TaskRequested testtaskR2 = null;

        //we have a task
        testaddtask.addTask(testtaskR2);
        testtaskR1.deleteTask(testtaskR2);

        assertFalse(testtaskR1.hasTask(testtaskR2));

    }

    public void testEditTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        TaskRequested testtaskR1 = new TaskRequested("hamda","desc",testUser);
        TaskRequested testtaskR2 = null;

        testtaskR2 = testtaskR2.getTask(testtaskR1);
        assertTrue(testtaskR1.hasTask(testtaskR1));
        assertEquals(testtaskR2,testtaskR1);

    }
}