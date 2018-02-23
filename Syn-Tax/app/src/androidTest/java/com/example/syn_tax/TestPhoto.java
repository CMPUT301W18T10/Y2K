package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestPhoto extends ActivityInstrumentationTestCase2 {
    public TestPhoto(){
        super(Photo.class);
    }

    // Test to make sure user can add a photograph to their task.
    public void testAddPhoto(){
        Photo testPhoto= new Photo();
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        TaskRequested testTask= new TaskRequested("test", "testing add photo", testUser);

        testTask.addPhoto(testPhoto);
        assertTrue(testTask.hasPhoto(testPhoto));
    }

    // Test to make sure user can delete a photograph to their task.
    public void testDeletePhoto(){
        Photo testPhoto= new Photo();
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        TaskRequested testTask= new TaskRequested("test", "testing add photo", testUser);

        testTask.addPhoto(testPhoto);
        testTask.deletePhoto(testPhoto);
        assertFalse(testTask.hasPhoto(testPhoto));
    }

    // Test to make sure user can get a photograph from a task.
    public void testGetPhoto(){
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        Task testTask= new Task("test", "testing add photo", testUser);

        Photo testPhoto;
        testPhoto= testTask.getPhoto();
        assertTrue(testTask.hasPhoto(testPhoto));
    }

}
