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

    private String id;
    private User newUser;
    private TextView username, email, phoneNumber;
    private String str_username, str_email, str_phoneNumber;
    private Button newUserBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UNDERLINE Title
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        username =  findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phonenumber);


        newUserBtn = findViewById(R.id.saveBtn);

    }
    protected void onStart(){
        super.onStart();
        newUserBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ElasticSearchController.connected()){
                    if(makeUser()&& validUser()){
                        str_username = username.getText().toString();
                        str_email = email.getText().toString();
                        str_phoneNumber = phoneNumber.getText().toString();
                        //TODO: OJ DO CHECKS HERE
                        newUser = new User(str_username, str_email, str_phoneNumber);
                        ElasticSearchController.addUsers uploadUser = new ElasticSearchController.addUsers();
                        uploadUser.execute(newUser);
                        createAccountBtn();

                    }
                    else{
                        Toast toasty = Toast.makeText(CreateAccount.this,"Username already exists!",Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER,0,0);
                        toasty.show();
                    }
                }
                else{
                    Toast toasty = Toast.makeText(CreateAccount.this, "Cannot create user, internet connection lost.",Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER,0,0);
                    toasty.show();
                }
            }
        });

    }
    private boolean makeUser(){
        ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
        allUsers.execute(str_username);
        ArrayList<User> userList;
        try{
            userList = allUsers.get();
        }
        catch (Exception e){
            userList = new ArrayList<User>();
        }
        if(userList.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validUser(){
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
        return Patterns.PHONE.matcher(str_phoneNumber).matches();
    }


    private void createAccountBtn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
