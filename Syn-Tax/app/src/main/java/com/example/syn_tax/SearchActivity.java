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
 * Last Modified 3/17/18 4:38 PM
 *
 * CITATIONS https://stackoverflow.com/questions/22063842/check-if-a-latitude-and-longitude-is-within-a-circle?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 * April 6 2018
 */

package com.example.syn_tax;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/*
Citations:
https://stackoverflow.com/questions/30416322/android-not-auto-select-an-edittext-field
*/

/**
 * SearchActivity Class
 *
 * Feb 22, 2018
 *
 * Search for a particular task
 *
 * @see SearchActivity
 * @see HomeActivity
 * @see UserProfileActivity
 */
public class SearchActivity extends AppCompatActivity {
    int PLACE_LOCATION_REQUESTED = 1;
    private String keywords;
    private Double latti;
    private Double longi;
    private ListView listOfTasks;
    public static ArrayList<Task> specificTasks = new ArrayList<Task>();
    private SearchAdapter searchAdapter;
    public static final String POINTER = "Task_Position";
    static final int REQUEST_LOCATION = 1;



    LocationManager locationManager;

    @Override
    /**
     * On create set up the search activity by updating all tasks and showing all tasks requested or bidded
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLatitude();
        //getLongitude();

        listOfTasks = findViewById(R.id.searches);
        Button search = (Button) findViewById(R.id.search);

        try {
            HomeActivity.update();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Initially grab all the tasks from ELastic search, by passing in nothing(Empty string)
        try {
            searching("");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //When the user selects the searchBtn then call searching to return the list of tasks
        //Match the keywords entered
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText keywords = findViewById(R.id.keywords);
                String words = keywords.getText().toString();
                //Pass in the keywords to searching to get tasks that match
                try {
                    searching(words);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Get the latitude of a task
     * @return the latitude of a task
     */
    //get our current locations latitude
    double getLatitude() {
        Double latti = 0.0;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }

        else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latti = location.getLatitude();
            } else {
                System.out.println("Cannot find current location");

            }
        }

        return latti;
    }

    /**
     * @return the longitude of a task
     */
    //get our current locations longitude
    double getLongitude() {
        Double latti = 0.0;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }

        else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latti = location.getLongitude();
            } else {
                System.out.println("Cannot find current location");

            }
        }

        return latti;
    }







    /**
     * User searches passing in a keyword to match a task title
     *
     * @param keywords String
     */
    public void searching(String keywords) throws ExecutionException, InterruptedException {

        //DO SOMETHING
        Log.e("s", keywords);

        if (ElasticSearchController.connected()) {
            //Get all the tasks with the keywords entered
            ElasticSearchController.searchingTasks search = new ElasticSearchController.searchingTasks();
            search.execute(keywords);

            ArrayList<Task> tasks = search.get();
            ArrayList<Task> results = new ArrayList<Task>();

            String myname = LoginActivity.thisuser.getUsername();
            //Filter through the tasks we got to make sure status== "requested" or "bidded"
            for (int i = 0; i < tasks.size(); i++) {
                String taskname = tasks.get(i).getRequester().getUsername();


                //check if a task is within 5 km
                //initially we set this to false

                //first we need our task location and our current location
                Double taskLongi = tasks.get(i).getLong();
                //System.out.println(taskLongi);

                Double taskLatti = tasks.get(i).getLat();
                //System.out.println(taskLatti);

                Double myLongi = getLongitude();
                Double myLatti = getLatitude();
                //System.out.println(myLatti);
                //System.out.println(myLongi);

                //then we compare our location with our task location
                if (distance(myLatti,myLongi,taskLatti,taskLongi)> 3.10686 || distance(myLatti,myLongi,taskLatti,taskLongi)<= 3.10686) {

                    Toast.makeText(SearchActivity.this,"This task is too far",Toast.LENGTH_SHORT);
                    //System.out.println("Task is too far");

                }
                else {
                    //System.out.println("Task is good ");
                    if (tasks.get(i).getStatus().equals("requested")) {
                        results.add(tasks.get(i));

                    }
                    else if (tasks.get(i).getStatus().equals("bidded")) {
                        results.add(tasks.get(i));
                    }

                }

                specificTasks = results;
                Log.e("dkk", specificTasks.toString());

                //CALL TO SET THE ADAPTER FOR THE LIST VIEW
                //Set the adapter
                searchAdapter = new SearchAdapter(this, specificTasks);
                //Set the list views
                listOfTasks.setAdapter(searchAdapter);
            }
        }
    }


    /**
     * Pick a location
     *
     * @param view
     */
    public void goLocationPicker(View view) {
        //calling the place picker function
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(SearchActivity.this), PLACE_LOCATION_REQUESTED);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    /**
     * User directed to the searchActivity
     *
     * @param view
     */
    public void searchBtn(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the HomeActivity
     *
     * @param view
     */
    public void homeBtn(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the UserProfileActivity
     * Also passes in the user information of the user
     *
     * @param view
     */
    public void userInfo(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_LOCATION:
                //getLocation();
                getLatitude();
                getLongitude();
                break;
        }
    }


    /**
     * calculates the distance between two locations in MILES
     * @param lat1 my current latitude
     * @param lng1 my current longitude
     * @param lat2 task latitude
     * @param lng2 task longitude
     * @return the distance between both of them
     */
    public double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }

}

