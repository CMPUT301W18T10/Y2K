package com.example.syn_tax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        SearchView searchTasks= findViewById(R.id.searchbar);

    }

    public void searching(String keywords) {
        //DO SOMETHING
    }

    //public void startActivity() {
    //}
}
