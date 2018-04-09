package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.concurrent.ExecutionException;
/*CITATIONS
https://stackoverflow.com/questions/17379002/java-lang-runtimeexception-cant-create-handler-inside-thread-that-has-not-call
Published Jun 6 '15
Used FEB 25,2018*/

public class TestCreateAccount extends ActivityInstrumentationTestCase2 {

    /**
     * test to make sure we, the user can create an account that is not
     * already taken
     * @throws ExecutionException
     * @throws InterruptedException
     *
     * */

    public TestCreateAccount() {
        super(CreateAccount.class);
    }

//    // Test to make sure user can add a new Account.
//    public void testCreateAccount() throws ExecutionException, InterruptedException {
//
//        String username = "oj";
//        String email = "create@gmail.com";
//        String phone = "000-000-000";
//        CreateAccount testCreateAccount = new CreateAccount();
//
//        // first check if the above parameters are valid for a new user, then create an account then add
//        // it to the database
//
//        User testUser = new User(username,email,phone);
//        assertTrue(testCreateAccount.validUser(username,email,phone));
//        assertTrue(testCreateAccount.makeUser(username));
//
//        // add the user to database
//        ElasticSearchController.addUsers addUser = new ElasticSearchController.addUsers();
//        addUser.execute(testUser);
//
//        // since account is already in the database, test makeUser()
//        // to assert it returns false for a failed authentication
//        // meaning the username is already in the database.
//
////        assertFalse(testCreateAccount.makeUser(username));
//
//        // empty usernames or usernames greater than 8 characters and
//        // invalid emails and numbers are not allowed
//
//        username = "";
//        assertFalse(testCreateAccount.validUser(username,email,phone));
//        username = "usernametoolong";
//        assertFalse(testCreateAccount.validUser(username,email,phone));
//        username = "test";
//        email = "nothing"; //invalid email
//        phone = "000-0000-0000";        //invalid phone
//
////        assertFalse(testCreateAccount.validUser(username,email,phone));
//
//        ElasticSearchController.deleteUser deleteUser = new ElasticSearchController.deleteUser();
//        deleteUser.execute(username);
//
//
//    }


}
