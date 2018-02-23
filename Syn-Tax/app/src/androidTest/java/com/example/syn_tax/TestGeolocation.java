package com.example.syn_tax;

import android.location.Address;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class TestGeolocation extends ActivityInstrumentationTestCase2{
    public TestGeolocation(){
        super(Location.class);
    }

    // Test to make sure user can edit location of a task.
    public void testEditLocation(){
        Geolocation geolocation = new Geolocation();

        Address address1;
        address1 = geolocation.setSpecificLocation(getActivity(), super.getActivity(), "T5Y2V8");
        assertEquals(address1.getCountryName().toLowerCase(), "canada");
        assertEquals(address1.getLocality().toLowerCase(), "edmonton");

        address1=  geolocation.setSpecificLocation(getActivity(), super.getActivity(), "K2J2J9");
        assertEquals(address1.getCountryName().toLowerCase(), "canada");
        assertEquals(address1.getLocality().toLowerCase(), "ottawa");
    }



    // Test to make sure user can add location to a task.
    public void testAddLocation(){
        Geolocation geolocation = new Geolocation();

        Address address1;
        address1 = geolocation.setSpecificLocation(getActivity(), super.getActivity(), "T5Y2V8");
        assertEquals(address1.getCountryName().toLowerCase(), "canada");
        assertEquals(address1.getLocality().toLowerCase(), "edmonton");

        Address address;
        address = geolocation.setSpecificLocation(getActivity(), super.getActivity(), "K2J2J9");
        assertEquals(address.getCountryName().toLowerCase(), "canada");
        assertEquals(address.getLocality().toLowerCase(), "ottawa");
    }



    // Test to make sure user can get the location of a task.
    public void testGetLocation(){
        Address address;
        Geolocation geolocation = new Geolocation();
        address = geolocation.getCurrentLocation(getActivity(), super.getActivity());
        assertEquals(address.getCountryName().toLowerCase(), "canada");
        assertEquals(address.getLocality().toLowerCase(), "edmonton");
    }


}
