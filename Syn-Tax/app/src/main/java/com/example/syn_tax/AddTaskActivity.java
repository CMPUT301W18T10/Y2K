
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
 * Last Modified 3/17/18 5:25 PM
 */

/*Citations https://www.youtube.com/watch?v=_xIWkCJZCu0
* March 10 2018*/


package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * AddTaskActivity Class
 *
 * March 14, 2018
 *
 * Adds a task to the database if the fields are valid
 * Inorder to add task you must have a title for it, and a description
 * @see ElasticSearchController
 *
 *
 */
public class AddTaskActivity extends AppCompatActivity{

    // Set Attributes to private
    private String ptaskTitle;
    private String pdescription;
    private String pstatus;
    private String id;



    //location
    int PLACE_LOCATION_REQUESTED = 1;
    TextView tvlocation;

    //Photo
    ImageView photoView;
    private Bitmap photo;
    private Integer photoStatus=0;
    //private so only this class knows of the id final cause its gonna remain the same
    private static final int RESULT_GET_IMAGE = 0;


    //Location
    ImageView location;
    private double longitude;
    private double latitude;
    private Integer locationStatus=0;


    /**
     * OnCreate Method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        tvlocation  = (TextView) findViewById(R.id.tvlocation);


        //UNDERLINE TITLE
        //TextView title = findViewById(R.id.title);
        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Grab the extras passed with the intent
        Intent intent= getIntent();
        ptaskTitle= intent.getStringExtra("title");
        pdescription= intent.getStringExtra("description");
        pstatus=intent.getStringExtra("status");


        //Add a photo from your gallary
        photoView=findViewById(R.id.photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });

        //ADD BUTTON
        Button addButton = (Button) findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data
                EditText titleTask = findViewById(R.id.taskTitle);
                String stitle = titleTask.getText().toString();

                EditText description = findViewById(R.id.description);
                String sdescription = description.getText().toString();


                TextView status = findViewById(R.id.status);
                String sstatus = status.getText().toString();

                TextView location = findViewById(R.id.tvlocation);
                String slocation = location.getText().toString();

                //Check to see if the task already exists
                //find(this.id);

                // Validate the user info
                if (isValid()) {
                    //Instantiate a object of type Task
                    // added in the username of the requester - Aidan
                    Task newtask = new Task(stitle, sdescription,LoginActivity.thisuser, sstatus);
                    // Check to add a photo to the task
                    if (photoStatus == 1) {
                        newtask.setPhoto(photo);
                    }
                    //Check to add a location to a task
                    if (locationStatus == 1) {
                        newtask.setLocation(latitude, longitude);
                    }
                    AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTasks();
                    execute.execute(newtask);

                    done();
                }
            }
        });
    }

    /**
     * Go to the Homepage
     */
    public void done(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


    /**
     * Validate the user inputs, make sure it meets all standards
     *
     * @return true for the data the user entered is valid
     * and false if the data the user entered is not in the correct format
     */
    public boolean isValid(){
        boolean valid=true;


        //title
        EditText taskTitle= findViewById(R.id.taskTitle);
        String stitle= taskTitle.getText().toString();

        //description
        EditText description= findViewById(R.id.description);
        String sdescription= description.getText().toString();

        //**********************CHECKS*****************************************
        //Check Title
        if (stitle.isEmpty()){
            taskTitle.setError("Enter Title");
            Toast.makeText(AddTaskActivity.this, "Enter a Title.", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        //Check description
        else if(sdescription.isEmpty()){
            description.setError("Enter Description");
            Toast.makeText(AddTaskActivity.this, "Enter a Description.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //Check duplicate as well

        //If checks are all good, it will return true
        return valid;
    }


    /**
     * invoke gallery when user clicks
     */
    public void addPhoto() {
        //invoke image gallery using an implicit intent
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);

        //where do we want to find this data
        File photoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String photoDirect = photoDirectory.getPath();

        //finally get the Uri rep
        Uri data = Uri.parse(photoDirect);

        //set the data and type(get all image types)
        galleryIntent.setDataAndType(data,"image/*");
        startActivityForResult(galleryIntent, RESULT_GET_IMAGE);
    }


    /**
     * method called when user has selected a picture from the gallery
     * here we set the image the user has uploaded
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //make sure the gallery intent actually called our method
        //Make sure the result was okay
        //Make sure that we actually have an image
        if (requestCode == RESULT_GET_IMAGE && resultCode == RESULT_OK && data != null) {
            //uniform resource indicator - shows us the address of the image tha has been selected
            Uri imageUri = data.getData();
            photoView.setImageURI(imageUri);

            //declare a stream tor read the image from the sd card
            InputStream inputStream;
            //we get an input stream based on the uri of the image
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                //getting a bitmap from the stream
                photo = BitmapFactory.decodeStream(inputStream);
                //show image to our user
                photoView.setImageBitmap(photo);
                photoStatus = 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG);
            }
        }
        if (requestCode == PLACE_LOCATION_REQUESTED) {
            if (resultCode == RESULT_OK) {
                Place location = PlacePicker.getPlace(AddTaskActivity.this, data);
                tvlocation.setText(location.getAddress());
            }
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
            startActivityForResult(builder.build(AddTaskActivity.this), PLACE_LOCATION_REQUESTED);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
        
    }
}
