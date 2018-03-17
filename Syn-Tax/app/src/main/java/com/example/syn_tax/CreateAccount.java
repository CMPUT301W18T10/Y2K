package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class CreateAccount extends AppCompatActivity {

    private User newUser;
    private TextView username, email, phoneNumber;
    private String str_username, str_email, str_phoneNumber;
    private Button newUserBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UNDERLINE Title
        //TextView title = findViewById(R.id.title);
        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        username =  findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phonenumber);
        newUserBtn = findViewById(R.id.saveBtn);
    }

    protected void onStart() {
        super.onStart();
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = username.getText().toString();
                str_email = email.getText().toString();
                str_phoneNumber = phoneNumber.getText().toString();
                //TODO: OJ DO CHECKS HERE: IF THE any of the fields are EMPTY, and
                // TODO: check the right size for each one (ON ECLASS )
                //TODO CHECKS FOR LOGIN AND CREATE ACCOUNT
                if (ElasticSearchController.connected()) {

                    if (makeUser(str_username) && validUser(str_username,str_email,str_phoneNumber)) {
                        newUser = new User(str_username, str_email, str_phoneNumber);
                        ElasticSearchController.addUsers uploadUser = new ElasticSearchController.addUsers();
                        uploadUser.execute(newUser);
                        createAccountBtn();

                    } else {
                        Toast toasty = Toast.makeText(CreateAccount.this, "Username already exists!", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER, 0, 0);
                        toasty.show();
                    }
                } else {
                    Toast toasty = Toast.makeText(CreateAccount.this, "Cannot create user, internet connection lost.", Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER, 0, 0);
                    toasty.show();
                }
            }
        });
    }

    private boolean makeUser(String username){
        //Return true if authenticated and false if not authenticated
        Boolean authenticate=true;
        ArrayList<User> userList;
        if (validUser(str_username, str_email, str_phoneNumber)) {
            try {
                //Grab everything in the database for users
                ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
                allUsers.execute("");
                userList = allUsers.get();


                for (int i = 0; i < userList.size(); i++) {
                    //Check to see if the user entered a username in the system
                    if (userList.get(i).retrieveInfo().get(0).equals(username)) {
                        //If they did set thisuser to the username entered
                        authenticate = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Return if authenticated
        return authenticate;
    }

    private boolean validUser(String str_username, String str_email, String str_phoneNumber){
        str_username = username.getText().toString();
        str_email = email.getText().toString();
        str_phoneNumber = phoneNumber.getText().toString();
        if (str_username.length() > 8 | str_username.length() == 0){
            Toast toasty = Toast.makeText(CreateAccount.this,"Username must have less than 8 characters",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }
        if (!validEmail(str_email)){
            Toast toasty = Toast.makeText(CreateAccount.this,"Invalid Email",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }
        if (!validPhone(str_phoneNumber)){
            Toast toasty = Toast.makeText(CreateAccount.this,"Invalid Phone number",Toast.LENGTH_LONG);
            toasty.setGravity(Gravity.CENTER,0,0);
            toasty.show();
            return false;
        }

        return true;
    }

    private boolean validEmail(String str_email){
        return Patterns.EMAIL_ADDRESS.matcher(str_email).matches();
    }

    private boolean validPhone(String str_phoneNumber){
        boolean valid = false;
        if (str_phoneNumber.length()< 6 || str_phoneNumber.length() > 11){
            valid = false;
        }
        else{
            valid = true;
        }

      return valid;
    }


    private void createAccountBtn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
