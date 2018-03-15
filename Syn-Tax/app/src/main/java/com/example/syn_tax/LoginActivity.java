package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    public static User thisuser;
    private ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


    public void loginBtn(View view) {
        TextView Usernm = findViewById(R.id.username);
        if(getThisUser(String.valueOf(Usernm)) == true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (ElasticSearchController.connected() == false){
            Toast.makeText(LoginActivity.this,"no connection",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast toasty = Toast.makeText(LoginActivity.this, "Invalid username has been entered.", Toast.LENGTH_SHORT);
            toasty.setGravity(Gravity.CENTER,0,300);
            toasty.show();
        }
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
        }
        catch (Exception e){
            userList = new ArrayList<User>();
        }
        if(userList.size() == 0){
            return false;
        }
        else{
            return true;
        }

    }

}
