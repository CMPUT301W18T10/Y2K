package com.example.syn_tax;

import android.content.Intent;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by Hamsemare on 2018-02-21.
 */

/*CITATIONS:
FROM https://stackoverflow.com/questions/23038682/java-lang-runtimeexception-only-one-looper-may-be-created-per-thread
PUBLISHED Nov 9,2015
USED FEB 25,2018
*/

public class TestSearch extends ActivityInstrumentationTestCase2{
    public TestSearch(){
        super(SearchActivity.class);
    }



    //Test when no keywords entered to search for tasks.
    public void testWithNoWords(){
        if (Looper.myLooper() == null){
            Looper.prepare();
        }

        SearchActivity testsearch= new SearchActivity();
        testsearch.searching("");
        try {
            testsearch.searching("");
        }
        catch (IllegalArgumentException e) {
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        }
        catch (RuntimeException e){
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        }
    }



    //Test when keywords are entered to search for tasks.
    public void testWithKeyword() {
        if (Looper.myLooper() == null){
            Looper.prepare();
        }
        String keywords = "aaaaa";
        SearchActivity testsearch = new SearchActivity();

        try {
            testsearch.searching(keywords);
        } catch (IllegalArgumentException e) {
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        } catch (RuntimeException e) {
            Log.i("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        }
    }
}
