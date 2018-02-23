package com.example.syn_tax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserProfile extends AppCompatActivity {
    UserProfile userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public UserProfile getProfile() {
        return userprofile;
    }

    public boolean hasUserProfile() {
        return true;
    }
}
