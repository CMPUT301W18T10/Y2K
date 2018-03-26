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
 * Last Modified 3/16/18 2:33 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

//this activity allows a user to view a task and edit it

/**
 * EditTaskActivity Class
 *March 18 2018
 *
 * Edits a task that the user has already created and adds the editied
 * task to the database if the fields are valid
 * inorder to edit a task there must be an already existing task
 * And in must have a unique title and description
 * @see ElasticSearchController
 *
 */


public class EditTaskActivity extends AppCompatActivity {
    public static int pos;
    int PLACE_LOCATION_REQUESTED = 1;


    private Integer photoStatus=0;
    private Bitmap photo;
    //private so only this class knows of the id final cause its gonna remain the same
    private static final int RESULT_GET_IMAGE = 0;


    //Location
    private Integer locationStatus=0;
    private Double latitude;
    private Double longitude;
    private String Otitle;

    /**
     * OnCreate Method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //get the data from the homeactivity
        Intent intent = getIntent();
        pos = Integer.parseInt(intent.getStringExtra(HomeActivity.POINTER));
        final Task task = HomeActivity.requestedTasks.get(pos);

        Otitle= task.getTitle ();
        //print the old task info

        if (!Objects.equals ( task.getStatus (), "requested" )){
            Button saveBtn= findViewById(R.id.updateBtn);
            saveBtn.setText("DONE");
            printTask(task);
            //Get Data
            EditText title = findViewById(R.id.editTaskTitle);
            EditText description = findViewById(R.id.editDescription);
            ImageView photo= findViewById ( R.id.editPhotoView );
            Button location= findViewById ( R.id.editLocation );
            EditText status= findViewById ( R.id.status );


            title.setFocusable(false);
            description.setClickable(false);

            description.setClickable(false);
            description.setFocusable(false);

            photo.setClickable(false);
            photo.setFocusable(false);

            location.setClickable(false);
            location.setFocusable(false);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid ()) {
                        //Instantiate a object of type Task
                        // added in the username of the requester - Aidan
                        Task tempTask = task;

                        EditText title = findViewById(R.id.editTaskTitle);
                        EditText description = findViewById(R.id.editDescription);
                        EditText status= findViewById ( R.id.status );

                        String stitle = title.getText().toString();
                        String sdesc = description.getText().toString();
                        String sstatus = status.getText().toString();

                        task.editTask(stitle, sdesc,LoginActivity.thisuser, sstatus);
                        ElasticSearchController.updateTask ( tempTask, task );
                        HomeActivity.requestedAdapter.notifyDataSetChanged();
                        updateButton();
                    }
                }
            });
        }

        else{

            printTask(task);

            //add a photo from the fallery
            ImageView editphoto = findViewById(R.id.editPhotoView);
            editphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPhoto();
                }
            });

            //Update Button

            Button updateButton  = (Button) findViewById(R.id.updateBtn);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edittitle = findViewById(R.id.editTaskTitle);
                    EditText editdescription = findViewById(R.id.editDescription);
                    EditText editstatus = findViewById(R.id.status);

                    String stitle = edittitle.getText().toString();
                    String sdesc = editdescription.getText().toString();
                    String sstatus = editstatus.getText().toString();

                    //valiidate user info
                    if(isValid()) {
                        //Instantiate a object of type Task
                        // added in the username of the requester - Aidan
                        //TODO delete the task from the elastic search then add this one
                        Task tempTask = task;
                        task.editTask(stitle, sdesc,LoginActivity.thisuser, sstatus);
                        // Check to add a photo to the task
                        if (photoStatus == 1) {
                            task.setPhoto(photo);
                        }
                        //Check to add a location to a task
                        if (locationStatus == 1) {
                            task.setLocation(latitude,longitude);
                        }

                        ElasticSearchController.updateTask ( tempTask, task );
                        HomeActivity.requestedAdapter.notifyDataSetChanged();
                        updateButton();
                    }
                }
            });
        }
    }


    /**
     * prints the Old task information on the edittaskactivity
     * @param task
     *
     */

    //print the old user information on this activity
    private void printTask(Task task) {
        String Otitle = task.getTitle ();
        String Odesc = task.getDescription ();
        Bitmap Ophoto = task.getPhoto();
        Double Olocation = task.getLat()+task.getLong();
        String str_Olocation  = Olocation.toString();
        String Ostatus= task.getStatus ();


        //set the title and description
        EditText edittitle =  findViewById(R.id.editTaskTitle);
        edittitle.setText(Otitle);
        EditText editdesc = findViewById(R.id.editDescription);
        editdesc.setText(Odesc);
        EditText editStatus= findViewById ( R.id.status );
        editStatus.setText ( Ostatus);

        //set the photo
        ImageView editphoto = findViewById(R.id.editPhotoView);
        editphoto.setImageBitmap(Ophoto);

        //set the Location
        TextView editlocation = findViewById(R.id.editLocation);
        editlocation.setText(str_Olocation);
    }


    /**
     * invoke gallery when user clicks
     */

    public void editPhoto() {
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
     *
     * @return true if user input is valid
     */
    //validates that user entered the correct information
    public boolean isValid(){
        boolean valid=true;
        //title
        EditText taskTitle = findViewById(R.id.editTaskTitle);
        String stitle= taskTitle.getText().toString();

        EditText status= findViewById ( R.id.status );
        String sstatus= status.getText ().toString ();

        //description
        //set the title and description

        EditText description = findViewById(R.id.editDescription);
        String sdescription= description.getText().toString();

        //**********************CHECKS*****************************************
        //Check Title if dublicates
        if (!stitle.equals ( Otitle )){
            ArrayList<Task> allTasks;
            try {
                if (!checkName ( stitle )) {
                    taskTitle.setError ( "Title is Taken." );
                    Toast.makeText ( EditTaskActivity.this, "Title is Taken.", Toast.LENGTH_SHORT ).show ();
                    valid = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } catch (ExecutionException e) {
                e.printStackTrace ();
            }
        }

        //Check Title
        else if (stitle.isEmpty()){
            taskTitle.setError("Enter Title");
            Toast.makeText(EditTaskActivity.this, "Enter a Title.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //Check description
        else if(sdescription.isEmpty()){
            description.setError("Enter Description");
            Toast.makeText(EditTaskActivity.this, "Enter a Description.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //Check status
        else if (sstatus.isEmpty()){
            status.setError("Enter status");
            Toast.makeText(EditTaskActivity.this, "Enter a status.", Toast.LENGTH_SHORT).show();
            valid=false;
        }


        else if ( !(sstatus.equals ( "requested" )) && !(sstatus.equals ( "bidded" )) &&
                !(sstatus.equals ( "done" )) && !(sstatus.equals ( "assigned" ))){
            status.setError ( "Enter status" );
            Toast.makeText(EditTaskActivity.this, "Invalid status.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //If checks are all good, it will return true
        return valid;
    }


    /**
     *  Check to see if the task already exists
     * @param title the title name of the task (String)
     * @return Boolean true if theres no duplicates and false if there is duplicates
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean checkName(String title) throws ExecutionException, InterruptedException {
        Boolean check= true;

        ElasticSearchController.getTasks tasks= new ElasticSearchController.getTasks();
        tasks.execute (title, "");
        ArrayList<Task> allTasks;
        allTasks=tasks.get();

        for(int i=0; i<allTasks.size (); i++){
            if(allTasks.get(i).getRequester ().getUsername ().equals ( LoginActivity.thisuser.getUsername () )){
                check=false;
            }
        }
        return check;
    }


    /**
     * intent to go back to the HomeActivity
     */
    private void updateButton() {
        Intent intent = new Intent(EditTaskActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    /**
     * method called when user selects a picture from the gallery here,
     * we set the image the user has uploaded
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //make sure the gallery intent actually called our method
        //Make sure the result was okay
        //Make sure that we actually have an image
        ImageView editphoto= findViewById ( R.id.editPhotoView );
        Button editlocation= findViewById ( R.id.editLocation );
        if (requestCode == RESULT_GET_IMAGE && resultCode == RESULT_OK && data != null) {
            //uniform resource indicator - shows us the address of the image tha has been selected
            Uri imageUri = data.getData();
            editphoto.setImageURI(imageUri);

            //declare a stream tor read the image from the sd card
            InputStream inputStream;
            //we get an input stream based on the uri of the image
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                //getting a bitmap from the stream
                photo = BitmapFactory.decodeStream(inputStream);
                //show image to our user
                editphoto.setImageBitmap(photo);
                photoStatus = 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG);
            }
        }
        if (requestCode == PLACE_LOCATION_REQUESTED) {
            if (resultCode == RESULT_OK) {
                Place location = PlacePicker.getPlace(EditTaskActivity.this, data);
                editlocation.setText(location.getAddress());
            }
        }
    }

    /**
     * pic a location
     * @param view
     */
    //method is called when user selects to pick a location
    public void goLocationPicker(View view) {
        //calling the place picker function
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(EditTaskActivity.this), PLACE_LOCATION_REQUESTED);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }

    }



}
