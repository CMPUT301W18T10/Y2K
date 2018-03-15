package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



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
        str_username = username.getText().toString();
        str_email = email.getText().toString();
        str_phoneNumber = phoneNumber.getText().toString();

        newUserBtn = findViewById(R.id.saveBtn);

    }
    protected void onStart(){
        super.onStart();
        newUserBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ElasticSearchController.connected()){
                    if(makeUser()){
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


    private void createAccountBtn(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
