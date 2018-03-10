package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
/*CITATIONS
https://stackoverflow.com/questions/17379002/java-lang-runtimeexception-cant-create-handler-inside-thread-that-has-not-call
Published Jun 6 '15
Used FEB 25,2018*/

public class TestCreateAccount extends ActivityInstrumentationTestCase2 {


    public TestCreateAccount() {
        super(CreateAccount.class);
    }

    // Test to make sure user can add a new Account.
//    public void testCreateAccount() {
//        Looper.prepare();
//        CreateAccount testNewAccount = new CreateAccount("hamda","testusername","000-0000-0000","test@g.ca","testpass");
//        User testNewUser = new User(testNewAccount);
//        testNewUser.addUser(testNewAccount);
//        assertTrue(testNewUser.hasNewUser(testNewAccount));
//
//
//        if(Looper.myLooper() == null) {
//            Looper.prepare();
//        }
//    }

}
