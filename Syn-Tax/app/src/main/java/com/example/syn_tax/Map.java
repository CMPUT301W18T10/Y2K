package com.example.syn_tax;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


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
*/

public class Map extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //Get the loongitude and latitude of a specific task
        //Call to the getLocation to add the marker
        //onMapReady(longitude, latitude)

    }

    public void onMapReady(double longitude, double latitude) {
        Uri gmmIntentUri = Uri.parse("geo.......");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }



    public String manageLocation(double longitude, double latitude) {

        String add = new String();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address a = addresses.get(0);
            add = a.getAddressLine(0) + ',' ;
            add += a.getCountryName();
            return add;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
}
