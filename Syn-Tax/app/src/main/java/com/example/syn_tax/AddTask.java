package com.example.syn_tax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddTask extends AppCompatActivity {

    private String id;
    private Location location;
    private Photo photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }


    public AddTask(String title,String description, String status, User user) {}

    public void setId(String id) {
        this.id = id;
    }

    public void addLocation(Location testLocation) {}


    public void addPhoto(Photo photo) {}

    public boolean hasTask(TaskRequested task) {
        return true;
    }


    public void addTask(TaskRequested task) {
    }
}
