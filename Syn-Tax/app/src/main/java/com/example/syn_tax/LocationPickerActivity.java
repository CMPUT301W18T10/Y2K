package com.example.syn_tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


public class LocationPickerActivity extends AppCompatActivity {


        int PLACE_LOCATION_REQUESTED = 1;

        TextView tvlocation;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_task);
            tvlocation = (TextView) findViewById(R.id.tvlocation);

        }

        public void goLocationPicker(View view) {
            //calling the place picker function
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {

                startActivityForResult(builder.build(LocationPickerActivity.this), PLACE_LOCATION_REQUESTED);

            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == PLACE_LOCATION_REQUESTED) {
                if(resultCode == RESULT_OK) {
                   Place location = PlacePicker.getPlace(LocationPickerActivity.this,data);
                    tvlocation.setText(location.getAddress());

                }

            }


        }




}
