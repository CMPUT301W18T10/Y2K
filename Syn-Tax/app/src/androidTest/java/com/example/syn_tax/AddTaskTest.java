package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */
public class AddTaskTest extends ActivityInstrumentationTestCase2 {

    public AddTaskTest() {
        super(AddTask.class);
    }

    //to make sure user can add tasks
    public void testAddTask() {
        String title = "test";
        String description = "testdesc";
        String status = "Requested";
        String photoname = "photoname";

        User testuser = new User("hamare","test@g.ca","000-000=0000");
        Photo testphoto = new Photo(photoname);
        Location testlocation = new Location();
        AddTask testaddTask = new AddTask(title, description, status,testuser);
        User testUser= new User("hamda", "test@g.ca", "000-0000-0000");
        TaskRequested testTaskR= new TaskRequested("test", "testing add photo", testUser);

        testaddTask.addPhoto(testphoto);
        testaddTask.addLocation(testlocation);


        assertTrue(testaddTask.hasTask(testTaskR));
    }

}