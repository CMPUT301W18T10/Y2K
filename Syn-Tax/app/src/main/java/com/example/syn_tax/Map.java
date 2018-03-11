package com.example.syn_tax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


/*CITATIONS:
Published on 6 Mar 2017
FROM Bright Varghese https://www.youtube.com/watch?v=qS1E-Vrk60E
USED Feb 22, 2018

Last update was July 25, 2017.
FROM https://developers.google.com/maps/documentation/urls/android-intents
USED Feb 22, 2018


Citations:
FROM: https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
PUBLISHED: May 25, 2017
USED: FEB 25,2018
*/


public class Map extends FragmentActivity {


    public Map(){}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //Get the longitude and latitude of a specific task
        //Call to the getLocation to add the marker
        //onMapReady(longitude, latitude)

    }

    public void onMapReady(double longitude, double latitude) {
        Uri gmmIntentUri = Uri.parse("geo.......");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }


    public static String manageLocation(Context context, double longitude, double latitude) {

        String add = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                add = address.getLocality() + ',';
                add += address.getCountryName();
                Log.i("Location", add);
                return add;
            }
            else {
                Log.i("Location", "No Address returned!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", "Failed, Can not get Location");
        }
        return "";
    }
}
