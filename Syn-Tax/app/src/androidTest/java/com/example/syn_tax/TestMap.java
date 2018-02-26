package com.example.syn_tax;

import android.location.Address;
import android.os.Looper;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */


public class TestMap extends ActivityInstrumentationTestCase2 {
    public TestMap(){
        super(Map.class);
    }


    // Test to make sure user can edit location of a task.
    public void testEditLocation(){
        if(Looper.myLooper() == null) {
            Looper.prepare();
        }

        Map address1= new Map();

        String name = address1.manageLocation(getActivity().getApplicationContext(), -113.503299,53.421866);
        Log.i("TODO", name);
        assertEquals(name, "Edmonton,Canada");

        name= address1.manageLocation(getActivity().getApplicationContext(), -75.700219,45.423520);
        Log.i("TODO", name);
        assertEquals(name, "Ottawa,Canada" );
    }



    // Test to make sure user can add location to a task.
    public void testAddLocation(){
        if(Looper.myLooper() == null) {
            Looper.prepare();
        }

        Map address1= new Map();
        String name1 = address1.manageLocation(getActivity().getApplicationContext(),-113.503299,53.421866);
        Log.i("TODO", name1);
        assertEquals(name1, "Edmonton,Canada");

        Map address= new Map();
        String name= address.manageLocation(getActivity().getApplicationContext(),-75.700219,45.423520);
        Log.i("TODO", name);
        assertEquals(name, "Ottawa,Canada" );
    }




    // Test to make sure user can get the location of a task.
    public void testGetLocation(){
        if(Looper.myLooper() == null) {
            Looper.prepare();
        }

        Map address= new Map();
        String name= address.manageLocation(getActivity().getApplicationContext(), -75.700219,45.423520);
        Log.i("TODO", name);
        assertEquals(name, "Ottawa,Canada" );
    }
}
