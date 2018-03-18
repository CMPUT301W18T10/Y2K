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

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public BidAdapter(Context context, ArrayList<Bid> bids){
        super(context, R.layout.bid_list_item, bids);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.bid_list_item, parent, false);

        // getting the username and amount for a singular bid
        String bidUser = getItem(pos).getBidUserName();
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
        thisBidButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bidClicked();
            }
        });




        return data;
    }
    public void acceptBtn(View v){
        //TODO: stuff for accepting a bid

    }
    public void declineBtn(View v){
        //TODO: stuff for declining a bid

    }
    private void bidClicked(){
        //TODO: figure out where a bid click is supposed to go
    }
}
