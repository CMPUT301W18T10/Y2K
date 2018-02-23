package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class TestEditUserProfile  extends ActivityInstrumentationTestCase2 {

    public TestEditUserProfile() {
        super(EditUserProfile.class);
    }

    public void testEditInfo() {
        String username = "hamda";
        String email = "test@g.ca";
        String phoneNumber = "000-0000-0000";


        User testUser= new User(username, email, phoneNumber);
        EditUserProfile testEdit = new EditUserProfile(testUser);
        testEdit.editUserProfile(testUser);
        assertTrue(testEdit.valid());

    }

}
