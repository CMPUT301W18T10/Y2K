package com.example.syn_tax;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hamsemare on 2018-02-22.
 */

public class NotifyTest extends ActivityInstrumentationTestCase2{
    /**
     * Constructor, calls the construtor of Task Class
     */
    public NotifyTest(){
        super(Task.class);
    }


    public void testNotify(){
        User UserR= new User("hamse", "test@g.ca", "000-0000-0000");
        User UserR1= new User("", "", "");
        User UserR2= new User("geee", "t2@g.ca", "999-9999-0000");

        User UserP= new User("hams", "test@g.ca", "000-0000-0000");
        User UserP1= new User("hamda", "", "");
        User UserP2= new User("aidan", "t@g2.ca", "910-1000-0100");


        Task task= new Task("test", "testing add photo", UserR, "requested",  null, 0.0, 0.0);
        Task task1= new Task("test", "testing add photo", UserR1,"requested",  null, 0.0, 0.0);
        Task task2= new Task("test", "testing add photo", UserR2,"requested",  null, 0.0, 0.0);

        task.setProvider ( UserP );

        // TODO: Check
//        assertTrue(task.getUserRNotified());


        task1.setProvider(UserP1);
        // TODO: Check
//        assertTrue(task1.getUserRNotified());


        task2.setProvider(UserP2);
        // TODO: Check
//        assertTrue(task2.getUserRNotified());
    }
}
