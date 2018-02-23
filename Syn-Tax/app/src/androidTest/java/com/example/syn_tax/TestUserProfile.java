package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestUserProfile extends ActivityInstrumentationTestCase2 {
    public TestUserProfile() {
        super(UserProfile.class);
    }

    //test to make sure user can view their profile
    public void testGetUserProfile() {
        User testUser = new User("hamda", "test@g.ca", "000-000-00000");
        UserProfile testUserProfile = null;

        testUserProfile = testUserProfile.getProfile();
        assertTrue(testUserProfile.hasUserProfile());


    }

}
