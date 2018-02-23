package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestPhoto extends ActivityInstrumentationTestCase2 {
    public TestPhoto(){
        super(Photo.class);
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
        for (int i=0; i<testTask.numOfPhotos(); i++){
            assertTrue(testTask.hasPhoto(testPhotos.get(i)));
        }
    }
}
