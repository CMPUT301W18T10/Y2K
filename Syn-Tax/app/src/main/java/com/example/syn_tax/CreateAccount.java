package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;



public class CreateAccount extends AppCompatActivity {

    private String id;
    private User newUser;
    private TextView username, email, phoneNumber;
    private String str_username, str_email, str_phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UNDERLINE Title
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phonenumber);
        str_username = String.valueOf(username);
        str_email = String.valueOf(email);
        str_phoneNumber = String.valueOf(phoneNumber);

    }
    private boolean makeUser(){
        ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
        allUsers.execute(String.valueOf(username));
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



    public void createAccountBtn(View view){
        if(makeUser() == true){
            newUser = new User(str_username,str_email,str_phoneNumber);
            AsyncTask<User,Void,Void> execute = new ElasticSearchController.addUsers();
            execute.execute(newUser);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
