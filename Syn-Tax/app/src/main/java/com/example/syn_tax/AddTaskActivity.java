/*Citations https://www.youtube.com/watch?v=_xIWkCJZCu0
* March 10 2018*/


package com.example.syn_tax;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddTaskActivity extends AppCompatActivity{

    // Set Attributes to private
    private String ptaskTitle;
    private String pdescription;
    private String pstatus;
    private String id;

    //Photo
    ImageView photoView;
    private Bitmap photo;
    private Integer photoStatus=0;
    //private so only this class knows of the id final cause its gonna remain the same
    private static final int RESULT_GET_IMAGE = 1;
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 1 ;


    //Location
    ImageView location;
    private double latitude;
    private double longitude;
    private Integer locationStatus=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //UNDERLINE TITLE
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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

        //Add a location to the task
        location = findViewById(R.id.locationView);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });




//        // Check to see if something is passed by the intent)
//        if (pstatus!=""){
//            //Set the fields
//            set();
//        }

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

                //Check to see if the task already exists
                //find(this.id);

                // Validate the user info
                if (isValid()) {
                    //Instantiate a object of type Task
                    Task task = new Task(stitle, sdescription, sstatus);
                    // Check to add a photo to the task
                    if (photoStatus == 1) {
                        task.setPhoto(photo);
                    }
                    //Check to add a location to a task
                    if (locationStatus == 1) {
                        task.setLocation(latitude, longitude);
                    }
                    AsyncTask<Task, Void, Void> execute = new ElasticSearchController.addTask();
                    execute.execute(task);
                    done();
                }
            }
        });
    }


    //Go To The HomePage
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





    //invoke gallery when user clicks
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


    //method called when user has selected a picture from the gallery
    //here we set the image the user has uploaded
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //make sure the gallery intent actually called our method
        //Make sure the result was okay
        //Make sure that we actually have an image
        if(requestCode == RESULT_GET_IMAGE && resultCode == RESULT_OK && data != null) {
            //uniform resource indicator - shows us the address of the image tha has been selected
            Uri imageUri = data.getData();
            photoView.setImageURI(imageUri);

            //declare a stream tor read the image from the sd card
            InputStream inputStream;
            //we get an input stream based on the uri of the image
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                //getting a bitmap from the stream
                photo =  BitmapFactory.decodeStream(inputStream);
                //show image to our user
                photoView.setImageBitmap(photo);
                photoStatus=1;

            }catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this,"Unable to open image",Toast.LENGTH_LONG);
            }
        }
    }




//    /**
//     * Set the following fields to the tasks attributes, so title, description, photos, location, and status
//     */
//    private void set() {
//        //Set the fields to the previous data passed by the intent
//        //Title
//        TextView tasktitle= findViewById(R.id.taskTitle);
//        tasktitle.setText(ptaskTitle);
//
//        //Description
//        TextView description=findViewById(R.id.description);
//        description.setText(pdescription);
//
//        //Status
//        TextView status=findViewById(R.id.status);
//        status.setText(pstatus);
//
//        //Photos
//
//        //Location
//    }



    //Add Location to the task
    public void addLocation() {
    }
}
