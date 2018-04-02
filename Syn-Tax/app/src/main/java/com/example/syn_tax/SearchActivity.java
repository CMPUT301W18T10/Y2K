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
 */

package com.example.syn_tax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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
    private ListView listOfTasks;
    private ArrayList<Task> specificTasks= new ArrayList<Task> (  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listOfTasks = findViewById(R.id.searches);
        Button search = (Button) findViewById(R.id.search);

        //Initially grab all the tasks from ELastic search, by passing in nothing(Empty string)
        try {
            searching ( "" );
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        //When the user selects the searchBtn then call searching to return the list of tasks
        //Match the keywords entered
        search.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                EditText keywords= findViewById(R.id.keywords);
                String words= keywords.getText().toString ();
                //Pass in the keywords to searching to get tasks that match
                try {
                    searching(words);
                } catch (ExecutionException e) {
                    e.printStackTrace ();
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        } );
    }


    /**
     * User searches passing in a keyword to match a task title
     * @param keywords String
     */
    public void searching(String keywords) throws ExecutionException, InterruptedException {
        //DO SOMETHING
        Log.e("s", keywords);

        if (ElasticSearchController.connected ()){
            //Get all the tasks with the keywords entered
            ElasticSearchController.searchingTasks search= new ElasticSearchController.searchingTasks ();
            search.execute ( keywords );

            ArrayList<Task> tasks = search.get ();

            //Filter through the tasks we got to make sure status== "requested" or "bidded"
            for(int i=0; i< tasks.size (); i++){
                String taskname= tasks.get ( i ).getRequester ().getUsername ();
                String myname= LoginActivity.thisuser.getUsername ();

                if(!taskname.equals ( myname )){

                    if (tasks.get(i).getStatus ().equals ( "requested" )){
                        specificTasks.add(tasks.get ( i ));
                    }
                    else if (tasks.get ( i ).getStatus ().equals ( "bidded" )){
                        specificTasks.add(tasks.get ( i ));
                    }
                }
            }

            Log.e("dkk", specificTasks.toString ());

            //CALL TO SET THE ADAPTER FOR THE LIST VIEW
        }
    }


    /**
     * Pick a location
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
     * @param view
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the HomeActivity
     * @param view
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * User directed to the UserProfileActivity
     * Also passes in the user information of the user
     * @param view
     */
    public void userInfo(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
}
