package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestLocation extends ActivityInstrumentationTestCase2{
    public TestLocation(){
        super(Location.class);
    }

    // Test to make sure user can edit location of a task.
    public void testEditLocation(){
        Location testLocation= new Location();
        Location testNewLocation= new Location();
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        TaskRequested testTask= new TaskRequested("test", "testing add photo", testUser);

        testTask.addLocation(testLocation);
        testTask.editLocation(testNewLocation);

        assertFalse(testTask.hasLocation(testLocation));
        assertTrue(testTask.hasLocation(testNewLocation));
    }



    // Test to make sure user can add location to a task.
    public void testAddLocation(){
        Location testLocation= new Location();
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        TaskRequested testTask= new TaskRequested("test", "testing add photo", testUser);

        testTask.addLocation(testLocation);
        assertTrue(testTask.hasLocation(testLocation));
    }

    // Test to make sure user can get the location of a task.
    public void testGetLocation(){
        User testUser= new User("hamse", "test@g.ca", "000-0000-0000");
        Task testTask= new Task("test", "testing add photo", testUser);

        Location testLocation;
        testLocation= testTask.getLocation();
        assertTrue(testTask.hasLocation(testLocation));
    }

}
