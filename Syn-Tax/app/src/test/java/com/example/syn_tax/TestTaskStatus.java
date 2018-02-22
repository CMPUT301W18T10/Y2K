package com.example.syn_tax;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by abdulazeezojetola on 2018-02-21.
 */

public class TestTaskStatus extends ActivityInstrumentationTestCase2 {
    public TestTaskStatus(){
        super(Task.class);
    }

    public void TestStatus() {
        Task task = new Task("Test", "Status Test", "Requested", 25.50);

        // When a task is initialized it status is requested, so first, check to see if the status equals
        // requested
        assertEquals("Requested", task.getTaskStatus());

        // Change task to assigned - this is after a bid has been accepted and assigned to a provider
        // then check if the status has indeed changed
        task.setTaskStatus("Assigned");
        assertEquals("Assigned", task.getTaskStatus());

        // change to completed when a task her been completed and no longer can be bidded on
        // check if it has changed using assertEquals
        task.setTaskStatus("Completed");
        assertEquals("Completed", task.getTaskStatus());

        // After a bid has been made on a task, the status changes
        // using assertequal to check if the status is correct
        task.setTaskStatus("Bidded");
        assertEquals("Bidded", task.getTaskStatus());

    }

    public void TestBids(){
        Task task = new Task("Test", "Bid Test", "Bidded", 20.00);

    }

}
