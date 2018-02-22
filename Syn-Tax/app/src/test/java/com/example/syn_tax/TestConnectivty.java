package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestConnectivty extends ActivityInstrumentationTestCase2 {
    public TestConnectivty(){
        super(Datebase.class);
    }

    // Test to make sure UserR’s offline changes to tasks, to be displayed when they regain connectivity.
    public void testConnectivity(){
    }
}
