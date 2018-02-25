package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/**
 * Created by hamdamare on 2018-02-24.
 */


/*System prompts user to view their tasks.
  User then views their tasks.
  System allows user to select from their tasks.
  User selects on a certain task
  */

public class TestSelectTask extends ActivityInstrumentationTestCase2 {

    public TestSelectTask()  {
        super(Task.class);
    }

    public void testGetTask() {
        User testuser = new User("hamare", "test@g.ca", "00-000-0000");
        TaskRequested testtask = new TaskRequested("testtitle","testdesc", testuser);
        AddTask testaddtask = new AddTask("testtitle","testdesc","requested",testuser);
        //now we have a task and we can see it
        testaddtask.addTask(testtask);
        Assert.assertTrue(testtask.hasTask(testtask));
    }

    public void testSelectTask() {
        User testuser = new User("hamare", "test@g.ca", "00-000-0000");
        TaskRequested testtask = new TaskRequested("testtitle","testdesc", testuser);
        AddTask testaddtask = new AddTask("testtitle","testdesc","requested",testuser);
        AddTask testaddtask2 = new AddTask("testtitle","testdesc","requested",testuser);
        //now we have a task and we can see it
        testaddtask.addTask(testtask);
        //allow user to select a task
        Assert.assertTrue(testtask.hasTask(testtask));
        //select task method is a boolean that is true when a user selects a task
        Assert.assertTrue(testtask.selectTask(testtask));
    }

}
