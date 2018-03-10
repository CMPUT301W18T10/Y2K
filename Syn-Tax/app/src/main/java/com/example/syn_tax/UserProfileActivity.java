package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void userProfileBtn(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
