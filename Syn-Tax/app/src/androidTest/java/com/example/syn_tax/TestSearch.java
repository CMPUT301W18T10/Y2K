package com.example.syn_tax;

import android.content.Intent;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by Hamsemare on 2018-02-21.
 */

/*CITATIONS:
FROM https://stackoverflow.com/questions/23038682/java-lang-runtimeexception-only-one-looper-may-be-created-per-thread
PUBLISHED Nov 9,2015
USED FEB 25,2018
*/

public class TestSearch extends ActivityInstrumentationTestCase2{
    /**
     * Constructor, calls the constructor of the SearchActivity Class
     */
    public TestSearch(){
        super(SearchActivity.class);
    }


    /**
     * Test when keywords are entered to search for tasks.
     */
    public void testWithKeyword() {
        if (Looper.myLooper() == null){
            Looper.prepare();
        }
        String keywords = "aaaaa";
        SearchActivity testsearch = new SearchActivity();

        try {
            testsearch.searching(keywords);
        } catch (IllegalArgumentException e) {
            Log.e("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        } catch (RuntimeException e) {
            Log.e("Error", "Error occured when searching for tasks.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }
    }
}
