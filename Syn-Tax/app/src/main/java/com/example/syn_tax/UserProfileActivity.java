package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

public class UserProfileActivity extends AppCompatActivity {

    //Attributes
    private User user;
    private User tempUser;
    public TextView username;
    public Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent intent= getIntent();

        //GET THE USER
        //SET Temp USER as well
        user= new User("1111kjj", "dwq", "ek");
        tempUser=user;

        //Get THE DATA FOR THAT USER
        ArrayList<String> user_info=user.retrieveInfo();

        //SET THE DATA TO THEIR CORRESPONDING FIELDS
        set(user_info);


        //SAVE BUTTON, Update User Data
        saveBtn= findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data

                EditText email = findViewById(R.id.email);
                String sEmail = email.getText().toString();

                EditText phoneNumber = findViewById(R.id.phonenumber);
                String sPhoneNumber = phoneNumber.getText().toString();

                // Validate the user info
                if (isValid()) {
                    // UPDATE USER INFO
                    ElasticSearchController elasticSearchController = new ElasticSearchController();
                    //elasticSearchController.updateUser(tempUser, user);

                    done();
                }
            }
        });
    }


    //SET THE DATA TO THEIR CORRESPONDING FIELDS
    public void set(ArrayList<String> info){
        //SET THE USERNAME
        username= findViewById(R.id.username);
        username.setText(info.get(0));

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



    //Go To The Home ACTIVITY
    public void done(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    //GO TO SEARCH ACTIVITY
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    //GO TO HOME ACTIVITY
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    //GO TO USER PROFILE ACTIVITY
    public void userProfileBtn(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
