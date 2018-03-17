package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestContactInfoRetrieval extends ActivityInstrumentationTestCase2 {
    public TestContactInfoRetrieval(){
        super(UserProfileActivity.class);
    }

    // Test to make sure user successfully retrieves and can see the contact information of a username.
    public void testContactInfoRetrieval(){
        ArrayList<String> contactInfo = new ArrayList<String>();
        String username = "testUser";
        String email = "test@g.ca";
        String phoneNumber = "000-0000-0000";
        User testUser = new User(username, email, phoneNumber);
        contactInfo.add(username);
        contactInfo.add(email);
        contactInfo.add(phoneNumber);
        assertEquals(testUser.retrieveInfo().get(0), contactInfo.get(0));


        ArrayList<String> contactInfo2 = new ArrayList<String>();
        username= "user2";
        email = "test2@g.ca";
        phoneNumber="";
        User testUser2 = new User(username, email, phoneNumber);
        contactInfo2.add(username);
        contactInfo2.add(email);
        contactInfo2.add(phoneNumber);
        assertEquals(testUser2.retrieveInfo().get(0), contactInfo2.get(0));


        ArrayList<String> contactInfo3 = new ArrayList<String>();
        username= "";
        email = "";
        phoneNumber="";
        User testUser3 = new User(username, email, phoneNumber);
        contactInfo3.add(username);
        contactInfo3.add(email);
        contactInfo3.add(phoneNumber);
        assertEquals(testUser3.retrieveInfo().get(0), contactInfo3.get(0));
    }
}
