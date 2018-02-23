package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class TestCreateAccount extends ActivityInstrumentationTestCase2 {

    TestCreateAccount() {
        super(CreateAccount.class);
    }


    // Test to make sure user can add a new Account.
    public void testCreateAccount() {
        CreateAccount testNewAccount = new CreateAccount("hamda","testusername","000-0000-0000","test@g.ca","testpass");
        NewUser testNewUser = new NewUser(testNewAccount);
        testNewUser.addNewUser(testNewAccount);
        assertTrue(testNewUser.hasNewUser(testNewAccount));
    }

}
