package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */
public class TestTask extends ActivityInstrumentationTestCase2 {

    public TestTask() {
        super(TaskList.class);
    }

    //make sure that user can get their tasks
    public void testGetTask() {
        User testUser = new User("hamda", "test@g.ca", "000-000-0000");
        Task testtaskR1 = new Task("hamda", "desc", testUser);
        //Add it
        TaskList tasks = new TaskList();
        tasks.addTask(testtaskR1);

        //Get it
        int num= tasks.getPos(testtaskR1);
        Task task= tasks.getTask(num);

        //Test it
        assertEquals(task, testtaskR1);
    }


    //make sure user can delete their tasks
    public void testDeleteTask() {
        User testUser = new User("hamda", "test@g.ca", "000-000-0000");
        Task testtaskR1 = new Task("hamda", "desc", testUser);

        TaskList tasks = new TaskList();
        tasks.addTask(testtaskR1);

        //Do we have a task
        if(tasks.hasTask(testtaskR1)){
            tasks.deleteTask(testtaskR1);
        }
        assertFalse(tasks.hasTask(testtaskR1));
    }


    //EDIT A TASK
    public void testEditTask() {
        User testUser = new User("hamda", "test@g.ca", "000-000-0000");
        Task testtaskR1 = new Task("hamda", "desc", testUser);

        //Add it
        TaskList tasks = new TaskList();
        tasks.addTask(testtaskR1);


        assertTrue(tasks.hasTask(testtaskR1));

        //NOW EDIT IT
        Task testtaskR2 = new Task("hada", "desc", testUser);

        if (tasks.hasTask(testtaskR1)){
            int num= tasks.getPos(testtaskR1);
            tasks.editTask(num, testtaskR2);
            tasks.deleteTask(testtaskR1);
        }
        else{
            tasks.addTask(testtaskR2);
        }

        //TEST IT
        assertFalse(tasks.hasTask(testtaskR1));
        assertTrue(tasks.hasTask(testtaskR2));
    }
}
