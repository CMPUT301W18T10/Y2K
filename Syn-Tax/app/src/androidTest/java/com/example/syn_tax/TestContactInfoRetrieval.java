package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestContactInfoRetrieval extends ActivityInstrumentationTestCase2 {
    public TestContactInfoRetrieval(){
        super(UserProfile.class);
    }

    // Test to make sure user successfully retrieves and can see the contact information of a username.
    public void testContactInfoRetrieval(){
        ArrayList<String> contactInfo = new ArrayList<>();
        String username = "testUser";
        String email = "test@g.ca";
        String phoneNumber = "000-0000-0000";

        User testUser = new User(username, email, phoneNumber);
        contactInfo.add(username);
        contactInfo.add(email);
        contactInfo.add(phoneNumber);

        assertEquals(testUser.retrieveContactInfo(), contactInfo);
    }
}
