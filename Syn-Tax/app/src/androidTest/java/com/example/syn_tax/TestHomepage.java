package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestHomepage extends ActivityInstrumentationTestCase2 {

    public TestHomepage() {
        super(HomeActivity.class);
    }

    //homepage displays the tasks for both the user requested and the user provided
    //make sure that user requested can view their tasks
    public void testRGetTask() {
        User testUser1 = new User("hamda", "test@g.ca","000-000-0000");
        Task testtask1 = new Task("hamda","desc",testUser1);
        TaskRequested testtaskR;

        testtaskR = testtask1.getTaskRequested();
        assertTrue(testtaskR.hasTask(testtaskR));
    }


    //make sure that user requested can view their tasks
    public  void testPGetTask() {
        User testUser2 = new User("hamda", "test@g.ca","000-000-0000");
        Task testtask2 = new Task("hamda","desc",testUser2);
        TaskProvided testtaskP;

        testtaskP = testtask2.getTaskProvided();
        assertTrue(testtaskP.hasTask(testtaskP));

    }
}
