package com.example.syn_tax;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestSearch extends ActivityInstrumentationTestCase2{
    public TestSearch(){
        super(Search.class);
    }

    // Test to make sure user can search for tasks.
    public void testSearching(){
        Search search= new Search();
    }

    //Test when no keywords entered to search
    public void testWithNoWords(){
    }


    //Test when keywords are entered to search
    public void testWithKeyword(){
    }

}
