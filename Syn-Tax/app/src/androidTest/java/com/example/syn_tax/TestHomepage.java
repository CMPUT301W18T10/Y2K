package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestHomepage extends ActivityInstrumentationTestCase2 {

    /**
     * Constructor, calls the constructor of the HomeActivity Class
     */
    public TestHomepage() {
        super(HomeActivity.class);
    }

<<<<<<< HEAD
    //homepage displays the tasks for both the user requested and the user provided
    //make sure that user requested can view their tasks
    public void testGetTask() {
        User testUser1 = new User("hamda", "test@g.ca","000-000-0000");
        Task testtask1 = new Task("hamda","desc",testUser1,"Requested");

=======
//    //homepage displays the tasks for both the user requested and the user provided
//    //make sure that user requested can view their tasks
//    public void testGetTask() {
//        User testUser1 = new User("hamda", "test@g.ca","000-000-0000");
//        Task testtask1 = new Task("hamda","desc",testUser1);
//
>>>>>>> 2ff02a249770e134a9c47ecbf9060a44d21a863e
//        TaskList tasks= new TaskList();
//        tasks.addTask(testtask1);
//
//        assertTrue(tasks.hasTask(testtask1));
<<<<<<< HEAD
    }
=======
//    }
>>>>>>> 2ff02a249770e134a9c47ecbf9060a44d21a863e
}
