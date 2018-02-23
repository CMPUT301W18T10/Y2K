package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestViewTaskp extends ActivityInstrumentationTestCase2 {

    public TestViewTaskp() {
        super(ViewTaskP.class);
    }


    //make sure user provider can see their provided tasks description includes bid
    public void testGetTask() {
        User testUser = new User("hamda", "test@g.ca","000-000-0000");
        Task testtask1 = new Task("hamda","desc", testUser);
        Location location = new Location();
        Task testtask2 = null;

        //if task has photo
        testtask1.getPhoto();
        //task must have location
        testtask2.getLocation();


        testtask2 = testtask2.getTask(testtask1);
        assertTrue(testtask2.hasTask(testtask2));

    }




}
