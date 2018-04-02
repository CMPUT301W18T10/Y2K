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
 * Last Modified 02/04/18 12:47 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BidActivity extends AppCompatActivity {

    public static ArrayAdapter<Bid> bidAdapter;
    public static ArrayList<Bid>bidList=  new ArrayList<Bid>();
    private static String taskTitle;

    public static final String POINTER = "bid_Position";
    private ListView bidListView;

    @Override

    /**
     * onCreate we'll show the user the the list of bids for that task title
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_bids );

        TextView title = findViewById(R.id.title );
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //GET THE task name
        Intent intent= getIntent();
        taskTitle= intent.getStringExtra ( "title" );

        bidListView = (ListView) findViewById(R.id.bidlist);
    }

    /**
     * onStart, continuously update the list adapters
     */
    protected void onStart() {
        super.onStart ();
        loadBids();
        bidAdapter = new BidAdapter (this,bidList);
        bidListView.setAdapter(bidAdapter);
    }
    /**
     * Loads in the bids for a task
     * Grab the list of bids for that task
     */
    private static void loadBids(){
        ArrayList<Bid> allBids;
        ElasticSearchController.getBids bids = new ElasticSearchController.getBids ();

        try {
            bids.execute("", taskTitle);
            allBids= bids.get();

            //IF CONNECTED THEN UPDATE
            //ELSE add the new tasks to the requested tasks
            if(allBids.size()>0){
                bidList=allBids;
            }
            else{
                bidList= new ArrayList<Bid> (  );
            }
            Log.e("sss", bidList.toString ());
        }

        catch(Exception e){
            allBids= new ArrayList<Bid> (  );
        }
    }



    /**
     * Button click for going to search activity
     * @param view is the current view
     * Go to the SearchActivity page
     */
    public void searchBtn(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * button click for going to the home activity
     * @param view is the current view
     * Go to the HomeActivity page
     */
    public void homeBtn(View view){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * button click for a user profile
     * @param view is the current view
     * Go to the UserProfileActivity
     */
    public void userInfo(View view){
        Intent intent= new Intent(this, UserProfileActivity.class);
        intent.putExtra("userInfo", LoginActivity.thisuser.retrieveInfo());
        startActivity(intent);
    }


}
