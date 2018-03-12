package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */
//keeps track of task providers bids
public class  Bid {

    private double bidAmount;
    private String bidStatus = "Awaiting Response";
    private String bidUserName;//TODO: added bid user name

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
    //TODO: added setters and getters for username
    public void setBidUserName(String bidUserName){
        this.bidUserName = bidUserName;
    }
    public String getBidUserName(){
        return this.bidUserName;
    }



}
