package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
//keeps track of task providers bids
public class  Bid {

    private double bidAmount;
    private String bidUserName;

    public  Bid(double bidAmount, String bidUserName) {

    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    // UserR has options to "Accept" or "Decline" and then the bid status is set

    public void setBidUserName(String bidUserName){
        this.bidUserName = bidUserName;
    }
    public String getBidUserName(){
        return this.bidUserName;
    }



}
