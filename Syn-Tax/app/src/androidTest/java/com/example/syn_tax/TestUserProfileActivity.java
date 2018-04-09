
package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestUserProfileActivity extends ActivityInstrumentationTestCase2 {

    /**
     * Constructor, calls the constructor of the User class
     */
    public TestUserProfileActivity() {
        super(User.class);
    }

    /**
     * Tests the Edit Info Method for a user
     */
    public void testEditInfo() {
        User user= new User("hamda","test@g.ca", "000-0000-0000");

        user.editProfile("hamse", "test1@g.ca", "000-999-0000");

//        assertEquals(user.getUsername (), "hamse");
//        assertEquals(user.retrieveInfo().get(1), "test1@g.ca");
//        assertEquals(user.retrieveInfo().get(2), "000-999-0000");
    }

}
