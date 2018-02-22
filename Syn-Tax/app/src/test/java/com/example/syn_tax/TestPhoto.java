package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestPhoto extends ActivityInstrumentationTestCase2 {
    public TestPhoto(){
        super(Photo.class);
    }

    // Test to make sure user can add a photograph to their task.
    public void testAddPhoto(){
        Photo photo= new Photo();
    }

    // Test to make sure user can delete a photograph to their task.
    public void testDeletePhoto(){
        Photo photo= new Photo();
    }
}
