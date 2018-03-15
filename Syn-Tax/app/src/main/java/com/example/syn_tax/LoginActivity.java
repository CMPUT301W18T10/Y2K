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


public class LoginActivity extends AppCompatActivity {
    public static User thisuser;
    private ArrayList<User> userList;
    private Button thisButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        thisButton = findViewById(R.id.loginButton);
    }

    protected void onStart() {
        super.onStart();
        thisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ElasticSearchController.connected()){
                    TextView Usernm = findViewById(R.id.username);
                    String str_username = Usernm.getText().toString();
                    if(getThisUser(str_username)) {
                        loginBtn();
                    }
                    else{
                        Toast toasty = Toast.makeText(LoginActivity.this, "Invalid username has been entered.", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER,0,300);
                        toasty.show();
                    }
                }
                else{
                    Toast toasty = Toast.makeText(LoginActivity.this,"Cannot Login, No Internet Connection.",Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER,0,300);
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
    private boolean getThisUser(String username){
        ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
        allUsers.execute(username);
        try{
            userList = allUsers.get();
            Log.e("users here", userList.toString());
            if(userList.size() == 0){
                return false;
            }
            if(userList.get(0).retrieveInfo().get(0) == username){
                thisuser = userList.get(0);
                return true;
            }

        }
        catch (Exception e){
            userList = new ArrayList<User>();
        }
        return false;
    }

}
