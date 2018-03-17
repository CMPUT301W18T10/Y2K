//package com.example.syn_tax;
//
///**
// * Created by hamdamare on 2018-02-22.
// */
//
///*CITATIONS
//https://stackoverflow.com/questions/17379002/java-lang-runtimeexception-cant-create-handler-inside-thread-that-has-not-call
//Published Jun 6 '15
//Used FEB 25,2018*/
//import android.test.ActivityInstrumentationTestCase2;
//import android.os.Looper;
//import android.util.Log;
//
//public class TestLogin extends ActivityInstrumentationTestCase2 {
//
//    public TestLogin() {
//        super(LoginActivity.class);
//    }
//
//
//
//    //Test when usermising info
//    public void testloginWithoutinfo() {
//        if(Looper.myLooper() == null) {
//            Looper.prepare();
//        }
//        String username = "";
//        String password = "";
//
//        LoginActivity testLogin = new LoginActivity();
//        try {
//            testLogin.login(username,password);
//        }
//        catch (Exception e) {
//            Log.i("Error", "Did not enter a username.");
//            e.printStackTrace();
//        }
//
//    }
//
//    //Test not missing info
//    public void testloginWithinfo() {
//        if(Looper.myLooper() == null) {
//            Looper.prepare();
//        }
//
//
//        String username = "Username";
//        String password = "Password";
//        LoginActivity testLogin = new LoginActivity();
//        try {
//            testLogin.login(username, password);
//        }
//        catch (Exception e) {
//            Log.i("Error","Error occured when logging in");
//            e.printStackTrace();
//        }
//    }
//}
