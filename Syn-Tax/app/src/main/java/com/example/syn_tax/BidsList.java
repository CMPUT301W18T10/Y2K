package com.example.syn_tax;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by abdulazeezojetola on 2018-02-23.
 */

public class BidsList {

    private Bid bid;
    private ArrayList<Bid> bids;


    public ArrayList<Bid> returnBids(){
        return bids;
    }

    public void addBid(Bid newBid){
        bids.add(newBid);
    }

    public void deleteBid(int pos){
        bids.remove(pos);
    }

    public boolean hasBid(Bid bid){
        if (bids.contains(bid))
            return true;
        else
            return false;
    }

    public void clearBids(){
        for ( Bid bid: bids) {
                if (bid.getBidStatus() == "Declined"){
                    bids.remove(bid);
            }
        }
    }
}
