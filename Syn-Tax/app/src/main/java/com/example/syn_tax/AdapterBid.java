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
 * Created by aapae on 3/11/2018.
 */

public class AdapterBid extends ArrayAdapter<Bid> {
    private ArrayList<Bid> bids;
    public AdapterBid(Context context, ArrayList<Bid> bids){
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
