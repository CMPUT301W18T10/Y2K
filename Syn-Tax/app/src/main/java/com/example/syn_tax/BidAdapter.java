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
 * Last Modified 3/16/18 2:33 PM
 */

package com.example.syn_tax;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 * @author Aidan Paetsch
 * @date 3/11/2018
 * This class is an adapter for a bid element that displays an individual bid inside of listview
 */

public class BidAdapter extends ArrayAdapter<Bid> {
    private ArrayList<Bid> bids;
    private Task task;


    public BidAdapter(Context context, ArrayList<Bid> bids){

        super(context, R.layout.bid_list_item, bids);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.bid_list_item, parent, false);

        //FLAG TO TELL US IF WE ARE THE OWNER OF A THE TASK
        Boolean owner= true;


        //Get the task its self
        task= getItem ( pos ).getTask ();
        final Button accept= data.findViewById ( R.id.accept );
        Button decline= data.findViewById ( R.id.decline);

        //IF THE USER IS NOT THE OWNER OF THE TASK HIDE THE ACCEPT BUTTON AND DECLINE BUTTON
        String usernameOFTask= task.getRequester ().getUsername ();
        if(!LoginActivity.thisuser.getUsername ().equals ( usernameOFTask )){
            owner=false;
            accept.setVisibility ( View.GONE );
            decline.setVisibility ( View.GONE );
        }


        // getting the username and amount for a singular bid
        final String bidUser = getItem(pos).getBidUserName();
        double amount = getItem(pos).getBidAmount();

        // setting the textviews for a username and a bid
        TextView bid_bidUser = (TextView) data.findViewById(R.id.user);
        TextView bid_amount = (TextView) data.findViewById(R.id.abid);

        // layout for a singular bid
        LinearLayout thisBidButton = (LinearLayout) data.findViewById(R.id.bid_item);


        //setting the text views of a bid item
        bid_bidUser.setText(bidUser);
        bid_amount.setText(String.valueOf(amount));



        //click listener for a bid item
        if(!owner) {
            thisBidButton.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    bidClicked ();
                }
            } );
        }
        else {
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    acceptBtn(v, pos, bids);
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    declineBtn(v, bidUser);
                }
            });
        }


        return data;
    }


    private void acceptBtn(View v, int pos, ArrayList<Bid> bids){

        //SET THE TASK STATUS TO ASSIGN AND REMOVE EVERY OTHER BID ON THAT TASK
        //WHEN WE REMOVE A BID NOTIFY THAT USER
        // Elasticsearch ....
        String acceptedUser = getItem(pos).getBidUserName();
        ArrayList<String> allBidUsers = new ArrayList<String>();
        for(int i = 0; i < bids.size(); i++){
            allBidUsers.add(getItem(i).getBidUserName());
        }
        for(int i = 0; i < allBidUsers.size(); i++){
            if( allBidUsers.get(i) != acceptedUser) {
                ElasticSearchController.deleteBid delete = new ElasticSearchController.deleteBid();
                delete.execute(allBidUsers.get(i));
            }
        }
        //TODO: set status of task to assigned


    }


    private void declineBtn(View v, String bidUser){
        //TODO: stuff for declining a bid
        //REMOVE THE BID FROM THE TASK
        //ELasticsearch.....
        ElasticSearchController.deleteBid delete = new ElasticSearchController.deleteBid();
        delete.execute(bidUser);
    }


    private void bidClicked(){
        //TODO: figure out where a bid click is supposed to go
        //GO TO VIEW TASK PROVIDER PAGE SO THEY CAN EDIT THE TASK
        Intent intent= new Intent(getContext (), ViewTaskProviderActivity.class);
        intent.putExtra("title", task.getTitle ());
        intent.putExtra ( "username", task.getRequester ().getUsername () );
        ((Activity)getContext()).startActivityForResult(intent,0);
    }
}
