package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;


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

        User testuser = new User("hamare","test@g.ca","000-000-0000");
        Task task= new Task(title, description, testuser);
        TaskList tasks= new TaskList();

        Photo testphoto = new Photo(photoname);

        //LocationActivity testlocation = new LocationActivity();
        //task.addLocation(testlocation);

        tasks.addTask(task);
        task.addPhoto(testphoto);

        assertTrue(tasks.hasTask(task));


    }
}
