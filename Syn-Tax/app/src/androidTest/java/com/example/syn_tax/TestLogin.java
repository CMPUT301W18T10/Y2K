package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */

/*CITATIONS
https://stackoverflow.com/questions/17379002/java-lang-runtimeexception-cant-create-handler-inside-thread-that-has-not-call
Published Jun 6 '15
Used FEB 25,2018*/
import android.test.ActivityInstrumentationTestCase2;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;



public class TestLogin extends ActivityInstrumentationTestCase2 {
    private ArrayList<User> userList;

    public TestLogin() {
        super(LoginActivity.class);
    }

    /**
     * test to make sure we, the user can log into an account
     * that exists. mainly making sure the restrictions apply
     * @throws ExecutionException
     * @throws InterruptedException
     *
     * */


    public void testLogin() throws ExecutionException, InterruptedException {
        String username = "oj";
        User testUser = new User(username,"test@gmail.com","000-000-000");

        String username2 = "";
        User testUser2 = new User(username,"test@gmail.com","000-000-000");
        //Add the user to database


        ElasticSearchController.addUsers addUser = new ElasticSearchController.addUsers();
        addUser.execute (testUser);



        //Test if user is in database. login will not work if the user
        // is not in the database - if valid is false;

        LoginActivity testLogin = new LoginActivity();
        assertEquals(testLogin.getThisUser(username), true);

        // empty usernames are not valid
        assertEquals(testLogin.validUser(username2),false);

        ElasticSearchController.deleteUser deleteUser = new ElasticSearchController.deleteUser();
        deleteUser.execute(username);
    }
}



