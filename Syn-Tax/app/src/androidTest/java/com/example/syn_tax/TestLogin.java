package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class TestLogin extends ActivityInstrumentationTestCase2{

    public TestLogin() {
        super(Login.class);
    }

    //Test when usermising info
    public void testloginWithoutinfo() {
        String username = "";
        String password = "";

        Login testLogin = new Login();
        try {
            testLogin.login(username,password);
        }
        catch (Exception e) {
            Log.i("Error", "Did not enter a username.");
            e.printStackTrace();
        }

    }

    //Test not missing info
    public void testloginWithinfo() {
        String username = "Username";
        String password = "Password";
        Login testLogin = new Login();
        try {
            testLogin.login(username, password);
        }
        catch (Exception e) {
            Log.i("Error","Error occured when logging in");
            e.printStackTrace();
        }
    }
}
