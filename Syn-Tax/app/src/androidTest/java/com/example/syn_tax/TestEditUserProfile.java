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
        User user= new User("hamda","test@g.ca", "000-0000-0000");

        EditUserProfile userEdit = new EditUserProfile(user);

        userEdit.editUserProfile("hamse", "test1@g.ca", "000-999-0000");



        assertEquals(user.retrieveContactInfo().get(0), "hamse");
        assertEquals(user.retrieveContactInfo().get(1), "test1@g.ca");
        assertEquals(user.retrieveContactInfo().get(2), "000-999-0000");
    }
}
