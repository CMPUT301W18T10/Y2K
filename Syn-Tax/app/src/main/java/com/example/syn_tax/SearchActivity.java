package com.example.syn_tax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listOfTasks = findViewById(R.id.searches);


    }

    public void searching(String keywords) {
        //DO SOMETHING
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
