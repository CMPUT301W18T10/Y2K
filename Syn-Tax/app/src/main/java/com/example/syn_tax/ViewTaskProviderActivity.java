/*
 * Copyright (c) 2018 Term Winter 2018 . CMPUT 301. Team 43. University of Alberta. All Rights Reserved .
 * You may use , distribute, or modify the code under terms and conditions of the code of Students
 * Behaviour at University of Alberta.
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; Without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Last Modified 3/17/18 4:38 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * ViewTaskProviderActivity Class
 *
 * Feb 22, 2018
 *
 * Allows a provider to view a task
 * @see HomeActivity
 *
 */
public class ViewTaskProviderActivity extends AppCompatActivity {
    public static int pos;
    int PLACE_LOCATION_REQUESTED = 1;

    private Integer photoStatus = 0;
    private Bitmap photo;

    private static final int RESULT_GET_IMAGE = 0;

    //location
    private Integer locationStatus =0 ;
    private Double latitude;
    private Double longitude;

    private String Title;
    private String Descriptions;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_p);

        //printing the task information
        Task task;


        //UNDERLINE Titles
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent intent = getIntent();
        //need to get the location of where the user clicks on search
        pos = Integer.parseInt(intent.getStringExtra(SearchActivity.POINTER));
        //some function in search that loads the tasks and adds it to a list
        //todo change Tasks to the name of the provided list instead
        final Task task = HomeActivity.providedTasks.get(pos);

        printTask(task);
        Title = task.getTitle();
        Descriptions = task.getDescription();

        //getData
        EditText username = findViewById(R.id.username);
        EditText description = findViewById(R.id.description);
        ImageView photo = findViewById(R.id.photoBtn);
        EditText location = findViewById(R.id.location);
        EditText status = findViewById(R.id.status);
        EditText bid = findViewById(R.id.lowestBid);
        final Button saveBtn = (Button) findViewById(R.id.savebutton);

        title.setFocusable(false);
        description.setFocusable(false);
        location.setFocusable(false);
        bid.setFocusable(false);

        description.setClickable(false);
        title.setClickable(false);
        location.setClickable(false);
        bid.setClickable(false);
        photo.setClickable(false);


        //get user bid and add it to our bidlist
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mybids = (EditText) findViewById(R.id.bid);
                String mybid_a = mybids.getText().toString();
                Double my_bid_amount = Double.parseDouble(mybid_a);

                String str_username = LoginActivity.thisuser.toString();


                Bid mybid_amounts = new Bid(my_bid_amount,str_username);
                task.addBid(mybid_amounts);
                saveBtn();
                //todo check if user can have more than one bid on a task
                //todo if not change bids to only add if username is not there already

            }
        });
    }

    private void saveBtn() {
        Intent intent = new Intent(ViewTaskProviderActivity.this,HomeActivity.class);
        startActivity(intent);
        //todo bids should get mybid amount
    }


    private void printTask(Task task) {
        String username = LoginActivity.thisuser.toString();
        String desc = task.getDescription();
        Bitmap photo = task.getPhoto();
        Double location = task.getLat()+task.getLong();
        String str_location = location.toString();
        Bid lowestbid = task.getLowestBid();
        String str_lowestbid = lowestbid.toString();

        ImageView photoview = findViewById(R.id.photoBtn);
        photoview.setImageBitmap(photo);

        TextView getlocation = findViewById(R.id.location);
        getlocation.setText(str_location);
    }

    /**
     * User is directed to the HomeActivity
     * @param view
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}
