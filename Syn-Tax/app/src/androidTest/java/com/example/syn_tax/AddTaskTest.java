package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by hamdamare on 2018-02-22.
 */
public class AddTaskTest extends ActivityInstrumentationTestCase2 {

    public AddTaskTest() {
        super(AddTaskActivity.class);
    }

    //to make sure user can add tasks
    /*
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
     // Test to make sure user can add a photograph to their task.
    public void testAddPhoto(){
        Photo testPhoto= new Photo("pic");
        Photo testPhoto2= new Photo("pic2");
        Photo testPhoto3= new Photo("pic3");
        Photo testPhoto4= new Photo("pic4");
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        Task testTask= new Task("test", "testing add photo", testUser);

        testTask.addPhoto(testPhoto);
        testTask.addPhoto(testPhoto2);
        testTask.addPhoto(testPhoto3);
        testTask.addPhoto(testPhoto4);

        assertTrue(testTask.hasPhoto(testPhoto));
        assertTrue(testTask.hasPhoto(testPhoto2));
        assertTrue(testTask.hasPhoto(testPhoto3));
        assertTrue(testTask.hasPhoto(testPhoto4));
    }

    // Test to make sure user can delete a photograph to their task.
    public void testDeletePhoto(){
        Photo testPhoto= new Photo("pic");
        Photo testPhoto2= new Photo("Pic1");
        Photo testPhoto3= new Photo("pic2");
        Photo testPhoto4= new Photo("pics");
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        Task testTask= new Task("test", "testing add photo", testUser);

        testTask.addPhoto(testPhoto);
        testTask.addPhoto(testPhoto2);
        testTask.addPhoto(testPhoto3);
        testTask.addPhoto(testPhoto4);

        assertTrue(testTask.hasPhoto(testPhoto));
        assertTrue(testTask.hasPhoto(testPhoto2));
        assertTrue(testTask.hasPhoto(testPhoto3));
        assertTrue(testTask.hasPhoto(testPhoto4));

        testTask.deletePhoto(testPhoto);
        testTask.deletePhoto(testPhoto2);
        testTask.deletePhoto(testPhoto3);
        testTask.deletePhoto(testPhoto4);

        assertFalse(testTask.hasPhoto(testPhoto));
        assertFalse(testTask.hasPhoto(testPhoto2));
        assertFalse(testTask.hasPhoto(testPhoto3));
        assertFalse(testTask.hasPhoto(testPhoto4));

    }

    // Test to make sure user can get a photograph from a task.
    public void testGetPhotos(){
        Photo testPhoto= new Photo("pic");
        Photo testPhoto2= new Photo("pic2");
        Photo testPhoto3= new Photo("pic3");
        Photo testPhoto4= new Photo("pic4");

        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        Task testTask= new Task("test", "testing add photo", testUser);

        testTask.addPhoto(testPhoto);
        testTask.addPhoto(testPhoto2);
        testTask.addPhoto(testPhoto3);
        testTask.addPhoto(testPhoto4);

        ArrayList<Photo> testPhotos= testTask.getPhotos();
        for (int i=0; i<(testPhotos.size()-1); i++){
            assertTrue(testTask.hasPhoto(testPhotos.get(i)));
        }
    }

    /*
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

     // Test the status change of each of the task. options are "Bidded", "Assigned", "Done"
    // and "Requested"
    public void testStatus(){

        User testUser = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask = new Task("get status","get status",testUser);

        testTask.setStatus("Bidded");
        assertEquals("Bidded", testTask.getStatus());

        User testUser2 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask2 = new Task("get status","get status",testUser);

        testTask2.setStatus("Assigned");
        assertEquals("Assigned", testTask2.getStatus());

        User testUser3 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask3 = new Task("get status","get status",testUser);

        testTask3.setStatus("Done");
        assertEquals("Done", testTask3.getStatus());

        User testUser4 = new User("Oj","test@gmail.com", "999-999-999");
        Task testTask4 = new Task("get status","get status",testUser);

        testTask4.setStatus("Requested");
        assertEquals("Requested", testTask4.getStatus());

    }

    */
}
