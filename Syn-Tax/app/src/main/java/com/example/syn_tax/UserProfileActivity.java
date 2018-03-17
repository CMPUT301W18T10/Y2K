package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * User Profile of the user is shown
 * If the user is the Login user then your able to edit the profile
 * Else, You can only view the user profile
 *
 * @see User
 * @see LoginActivity
 *
 */
public class UserProfileActivity extends AppCompatActivity {

    //Attributes
    private ArrayList<String> info;

    /**
     * Set The fields to the data passed and depending on if the user passed is the Login User
     * you can edit it or you can only view it.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //GET THE USER INFO
        Intent intent= getIntent();
        info= intent.getStringArrayListExtra("userInfo");

        //SET THE DATA TO THEIR CORRESPONDING FIELDS
        set(info);

        //Only if the username corresponds to the username of the LoginActivity username
        if(Objects.equals(LoginActivity.thisuser.retrieveInfo().get(0), info.get(0))){
            edit();
        }
        else{
            view();
        }
    }

    /**
     * USER CAN EDIT THEIR PROFILE BECAUSE ITS THEIR PROFILE
     *
     */
    public void edit(){
        final EditText email = findViewById(R.id.email);
        final EditText phoneNumber = findViewById(R.id.phonenumber);
        final Button username= findViewById(R.id.username);

        //SAVE BUTTON, Update User Data
        Button saveBtn= findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make sure user data is valid
                if (isValid()){
                    String sEmail = email.getText().toString();
                    String sPhoneNumber = phoneNumber.getText().toString();

                    //Call update the data of the user
                    User tempUser= new User(username.getText().toString(), sEmail, sPhoneNumber);
                    ElasticSearchController.updateUser( LoginActivity.thisuser, tempUser );
                    LoginActivity.thisuser=tempUser;
                    done();
                }
            }
        });
    }

    /**
     * USER CAN NOT EDIT THE PROFILE BECAUSE ITS NOT THEIR PROFILE
     */
    public void view(){
        //Get Data
        EditText email = findViewById(R.id.email);
        EditText phoneNumber = findViewById(R.id.phonenumber);

        email.setFocusable(false);
        email.setClickable(false);

        phoneNumber.setClickable(false);
        phoneNumber.setFocusable(false);

        Button saveBtn= findViewById(R.id.saveBtn);
        saveBtn.setText("DONE");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    /**
     * SET THE DATA TO THEIR CORRESPONDING FIELDS
     * @param info is the ifo passed by the user, so we set the fields to the data passed
     */
    public void set(ArrayList<String> info){
        //SET THE USERNAME
        Button userButton = findViewById(R.id.username);
        userButton.setText(info.get(0));

        //SET THE EMAIL
        EditText email= findViewById(R.id.email);
        email.setText(info.get(1));


        //SET THE PHONE NUMBER
        EditText phoneNumber= findViewById(R.id.phonenumber);
        phoneNumber.setText(info.get(2));
    }


    /**
     * Validate the user inputs, make sure it meets all standards
     *
     * @return true for the data the user entered is valid
     * and false if the data the user entered is not in the correct format
     */
    public boolean isValid(){
        boolean valid=true;

        EditText email = findViewById(R.id.email);
        String sEmail = email.getText().toString();

        EditText phoneNumber = findViewById(R.id.phonenumber);
        String sPhoneNumber = phoneNumber.getText().toString();

        //**********************CHECKS*****************************************

        //CHECK EMAIL
        if (sEmail.isEmpty()){
            email.setError("Enter Email");
            Toast.makeText(UserProfileActivity.this, "Enter Email.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //CHECK PHONE NUMBER
        else if (sPhoneNumber.isEmpty()){
            phoneNumber.setError("Enter Phone Number");
            Toast.makeText(UserProfileActivity.this, "Enter Phone Number.", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        //If checks are all good, it will return true
        return valid;
    }


    /**
     * Directs the user to the HomeActivity
     */
    public void done(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    /**
     * Directs the user to the SearchActivity
     * @param view
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    /**
     * Directs the user to the HomeActivity
     * @param view
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    /**
     * Directs the user to the UserProfileActivity
     * Also passes in the user information of the user
     * @param view
     */
    public void userProfileBtn(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }
}
