package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    public static ArrayAdapter<Task> requestedAdapter;
    public static ArrayList<Task> requestedTasks;
    public static ArrayAdapter<Task> distributedAdapter;
    public static ArrayList<Task> distributedTasks;
    public static final String POINTER = "Task_Position";
    private ListView requestedListView;
    private ListView distributedListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        TextView requestTitle = findViewById(R.id.requestCodeTitle);
        TextView distributeTitle = findViewById(R.id.distributeCodeTitle);

        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        requestTitle.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        distributeTitle.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //views for the 2 separate lists
        requestedListView = (ListView) findViewById(R.id.requestlist);
        distributedListView = (ListView) findViewById(R.id.distributelist);
    }
//    protected void onStart(){
//        super.onStart();
//        // calling custom adapters and setting list views
//        requestedAdapter = new AdapterTask(this,requestedTasks);
//        distributedAdapter = new AdapterTask(this, distributedTasks);
//        requestedListView.setAdapter(requestedAdapter);
//        distributedListView.setAdapter(distributedAdapter);
//    }

    public void addTaskBtn(View view){
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("status", "");
        startActivity(intent);
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
