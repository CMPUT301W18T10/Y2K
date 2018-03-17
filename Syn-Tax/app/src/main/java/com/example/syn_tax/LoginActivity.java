package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    public static User thisuser;
    private ArrayList<User> userList;
    private Button thisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UNDERLINE Titles
        //TextView title = findViewById(R.id.title);
        //title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        thisButton = findViewById(R.id.loginButton);
    }

    protected void onStart() {
        super.onStart();
        thisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ElasticSearchController.connected()) {
                    TextView Usernm = findViewById(R.id.username);
                    String str_username = Usernm.getText().toString();
                    //TODO OJ CHECK: if the username is not ""
                    if (getThisUser(str_username)) {
                        loginBtn();
                    } else {
                        Toast toasty = Toast.makeText(LoginActivity.this, "Invalid username has been entered.", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER, 0, 300);
                        toasty.show();
                    }
                } else {
                    Toast toasty = Toast.makeText(LoginActivity.this, "Cannot Login, No Internet Connection.", Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER, 0, 300);
                    toasty.show();
                }
            }
        });

    }

    private void loginBtn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createAccountBtn(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    //Check if username in database
    private boolean getThisUser(String username) {
        //Return true if authenticated and false if not authenticated
        Boolean authenticate = false;
        if (validUser(username)) {
            try {
                //Grab everything in the database for users
                ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
                allUsers.execute("");
                userList = allUsers.get();

                for (int i = 0; i < userList.size(); i++) {
                    //Check to see if the user entered a username in the system
                    if (userList.get(i).retrieveInfo().get(0).equals(username)) {
                        //If they did set thisuser to the username entered
                        LoginActivity.thisuser = userList.get(i);
                        authenticate = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Return if authenticated
        return authenticate;
    }

    private boolean validUser(String str_username) {
        boolean valid = false;
        if (str_username.length() == 0) {
            valid = false;
        } else {
            valid = true;

        }
        return valid;
    }
}

