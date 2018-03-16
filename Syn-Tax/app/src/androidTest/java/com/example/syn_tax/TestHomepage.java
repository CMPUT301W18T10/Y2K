package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestHomepage extends ActivityInstrumentationTestCase2 {

    public TestHomepage() {
        super(HomeActivity.class);
    }

//    //homepage displays the tasks for both the user requested and the user provided
//    //make sure that user requested can view their tasks
//    public void testGetTask() {
//        User testUser1 = new User("hamda", "test@g.ca","000-000-0000");
//        Task testtask1 = new Task("hamda","desc",testUser1);
//
//        TaskList tasks= new TaskList();
//        tasks.addTask(testtask1);
//
//        assertTrue(tasks.hasTask(testtask1));
//    }
}
