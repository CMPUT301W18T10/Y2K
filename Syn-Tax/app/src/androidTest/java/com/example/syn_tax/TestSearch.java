package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestSearch extends ActivityInstrumentationTestCase2{
    public TestSearch(){
        super(Search.class);
    }

    //Test when no keywords entered to search for tasks.
    public void testWithNoWords(){
        String keywords="";
        Search testsearch= new Search();

        try {
            testsearch.searching(keywords);
        }

        catch (Exception e) {
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        }
    }


    //Test when keywords are entered to search for tasks.
    public void testWithKeyword(){
        String keywords="aa";
        Search testsearch= new Search();
        try {
            testsearch.searching(keywords);
        }
        catch (Exception e) {
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        }
    }

}
