package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
//keeps track of task providers bids
public class Bid {

    private double bidAmount;
    private String bidStatus = "Awaiting Response";

    public  Bid(double bidAmount) {

    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    // UserR has options to "Accept" or "Decline" and then the bid status is set

    public void setBidStatus(String status) {
        this.bidStatus = status;
    }

    public String getBidStatus(){
        return bidStatus;
    }



}
