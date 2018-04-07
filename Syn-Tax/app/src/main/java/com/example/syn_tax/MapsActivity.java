/*
 * Copyright (c) 2018 Term Winter 2018 . CMPUT 301. Team 43. University of Alberta. All Rights Reserved .
 * You may use , distribute, or modify the code under terms and conditions of the code of Students
 * Behaviour at University of Alberta.
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; Without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Last Modified 24/03/18 2:46 AM
 */

package com.example.syn_tax;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by hamdamare on 2018-03-24.
 */

public class MapsActivity extends Activity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private boolean needsInit = false;
    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};

    private GoogleMap mMap;
    Location location;
    MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


    /**
     * onCreate MapActivity
     * The location of an item is set to this Activity
     * The location is displayed in google maps.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map);
        //double Lat = Task.getLat();
        //double Lng = HomeActivity.;
        //setUpMapIfNeeded(Lat, Lng);

//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);




    }

    /**
     * Setting up the map.
     * Here the google map is brought up.
     * The location of the user is displayed
     *
     * @param Lat
     * @param Lng
     */
    private void setUpMapIfNeeded(double Lat, double Lng) {

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {


            Uri gmmIntentUri = Uri.parse("geo:<" + String.valueOf(Lat) + ">,<" + String.valueOf(Lng) + ">?q=<" + String.valueOf(Lat) + ">,<" + String.valueOf(Lng) + ">(Item Location)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {


                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub

                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    }
                });

            }
        }
    }
}

