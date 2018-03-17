package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */

/**
 * keeps track of task providers bids
 */
public class Bid {

    private double bidAmount;
    private String bidUserName;

    /**
     * Constructor for a bid
     * @param bidAmount Amount bidded
     * @param bidUserName The person that made the bid
     */
    public  Bid(double bidAmount, String bidUserName) {
    }


    /**
     * Set the bid amount
     * @param bidAmount
     */
    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    /**
     * Get the bid amount
     * @return a double for the bid amount
     */
    public double getBidAmount() {
        return bidAmount;
    }

    /**
     * UserR has options to "Accept" or "Decline" and then the bid status is set
     * @param bidUserName
     */
    public void setBidUserName(String bidUserName){
        this.bidUserName = bidUserName;
    }

    /**
     * GET THE USERNAME OF THE PERSON THAT MADE THE BID
     * @return
     */
    public String getBidUserName(){
        return this.bidUserName;
    }



}
