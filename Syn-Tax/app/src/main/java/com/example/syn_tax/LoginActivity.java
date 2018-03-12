package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    public static String username;// Aidan added this so that custom adapters would stop throwing errors
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


    public void login(String username) {
    }

    public void loginBtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createAccountBtn(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}
