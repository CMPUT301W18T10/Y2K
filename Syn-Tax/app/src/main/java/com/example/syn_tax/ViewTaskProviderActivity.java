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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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
    private Double lowestAmount;
    private String title;
    private String status;
    private String  state;
    private Task task;
    private Bid oldBid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_p);

        Intent intent = getIntent();
        //need to get the location of where the user clicks on search
        status=intent.getStringExtra ( "status" );
        title= intent.getStringExtra ( "title" );
        state= intent.getStringExtra ( "sss");


        //From the Home Page get the task
        if(!state.equals("search")) {
            pos = Integer.parseInt(intent.getStringExtra(HomeActivity.POINTER));
            //set bidMyTitle to "my bid"
            TextView bidTitle = findViewById ( R.id.bidMyTitle );
            bidTitle.setText ( "My Bid:" );

            if (status.equals ( "assigned" )) {
                task = HomeActivity.assignedPtasks.get ( pos );
                //Set the code button to visible
                Button code= findViewById ( R.id.codeBtn );
                code.setVisibility ( View.VISIBLE );
            }
            else if (status.equals ( "bidded" )) {
                task = HomeActivity.biddedPtasks.get ( pos );
            }

            //Get my Bid on the task
            EditText amount= findViewById ( R.id.myAmount );
            ArrayList<Bid> allBids= new ArrayList<Bid> (  );

            ElasticSearchController.getBids bids= new ElasticSearchController.getBids ();
            bids.execute ( "", LoginActivity.thisuser.getUsername () );
            try {
                allBids=bids.get ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } catch (ExecutionException e) {
                e.printStackTrace ();
            }

            try{
                for(int i=0;i<allBids.size ();i++){
                    if(allBids.get(i).getTask ().getTitle ().equals ( task.getTitle () )){
                        Double cost= allBids.get(i).getBidAmount ();
                        amount.setText(cost.toString ());
                        break;
                    }
                }
            }
            catch (NullPointerException e){
                Log.e("e", "title errror");
            }
        }


        else{
            //From the search page find
            pos = Integer.parseInt(intent.getStringExtra(SearchActivity.POINTER));
            task= SearchActivity.specificTasks.get ( pos );
        }


        //Get the old bid if it exists
        ArrayList<Bid> allBids= new ArrayList<Bid> (  );
        ElasticSearchController.getBids bids= new ElasticSearchController.getBids ();
        bids.execute ( "", LoginActivity.thisuser.getUsername () );
        try {
            allBids=bids.get ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }

        for(int i=0;i<allBids.size ();i++){
            if(allBids.get(i).getTask ().getTitle ().equals ( task.getTitle () )){
                oldBid= allBids.get ( i );
                break;
            }
        }




        //Get the lowest bid
        try {
            if(task.getLowestBid ()!=null){
                lowestAmount= task.getLowestBid ().getBidAmount ();
            }
            else{
                lowestAmount=null;
            }
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        catch (NullPointerException e){
            Log.e("e", "error lowest bid");
        }

        try {
            printTask();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }


        Button saveBtn=findViewById ( R.id.saveBtn );
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert the string to a Double if the string is not empty else set it to 0
                EditText amount= findViewById ( R.id.myAmount );
                String sAmount= amount.getText ().toString ();

                Double dAmount;
                if(sAmount.length()>0){
                    dAmount= Double.parseDouble(sAmount);
                }
                else {
                    dAmount=0.0;
                }
                if (isValid() && dAmount>0){

                    //add bid
                    Bid newBid= new Bid ( LoginActivity.thisuser.getUsername (),dAmount, task,task.getTitle (),task.getRequester ().getUsername () );

                    //or update the bid
                    if (oldBid!=null){
                        ElasticSearchController.updateBid ( oldBid.getBidUserName (), newBid );
                    }

                    //add bid because i have never bidded on this task before
                    else{
                        AsyncTask<Bid, Void, Void> execute = new ElasticSearchController.addBids ();
                        execute.execute ( newBid );
                    }

                    homeBtn ();
                }

                else if (dAmount<=0){
                    amount.setError("Invalid Bid. ");
                    Toast.makeText(ViewTaskProviderActivity.this, "Enter a Bid.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    /**
     * Set the following for the the fields
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void printTask() throws ExecutionException, InterruptedException  {

        try{
            Bitmap photo = task.getPhoto ();

            Double location = task.getLat () + task.getLong ();
            String str_location = location.toString ();

            //set title
            TextView taskTitle = findViewById ( R.id.title );
            taskTitle.setText ( title );

            //Set the username
            TextView username = findViewById ( R.id.username );
            username.setText ( task.getRequester ().getUsername () );

            //Set the description
            TextView description = findViewById ( R.id.description );
            description.setText ( task.getDescription () );

            //Set the photo
            ImageView photoview = findViewById ( R.id.photoBtn );
            photoview.setImageBitmap ( photo );

            //Set the location
            //Also allow user to view the task on a map
            TextView getlocation = findViewById ( R.id.location );
            getlocation.setText ( str_location );

            //Set the status
            TextView taskStatus = findViewById ( R.id.status );
            taskStatus.setText ( status );

            //Set the lowest amount
            TextView lowest = findViewById ( R.id.lowest );
            if (lowestAmount==null){
                lowest.setText ( "NONE");
            }
            else {
                String amount = lowestAmount.toString ();
                lowest.setText ( amount );
            }
        }

        catch (NullPointerException e){
            Log.e("e","Error loading");
        }
    }

    /**
     * Checks if the user entered a correct value for the amount
     * @return
     */
    public Boolean isValid(){
        Boolean valid=true;
        EditText amount= findViewById ( R.id.myAmount );
        String sAmount= amount.getText ().toString ();
        //Check amount
        if(sAmount.isEmpty()){
            amount.setError("Invalid Bid.");
            Toast.makeText(ViewTaskProviderActivity.this, "Enter a Bid.", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        return valid;
    }




    /**
     * User is directed to the HomeActivity
     */
    public void homeBtn(){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    /**
     * User is directed to the code page to write code
     * @param view
     */
    public void codeBtn(View view){
        Intent intent= new Intent(this, CodeProvider.class);
        startActivity(intent);
    }
}
