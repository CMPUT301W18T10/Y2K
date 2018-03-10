package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestUserProfileActivity extends ActivityInstrumentationTestCase2 {

    public TestUserProfileActivity() {
        super(User.class);
    }

    public void testEditInfo() {
        User user= new User("hamda","test@g.ca", "000-0000-0000");


        user.editProfile("hamse", "test1@g.ca", "000-999-0000");

        assertEquals(user.retrieveContactInfo().get(0), "hamse");
        assertEquals(user.retrieveContactInfo().get(1), "test1@g.ca");
        assertEquals(user.retrieveContactInfo().get(2), "000-999-0000");
    }
   
}
