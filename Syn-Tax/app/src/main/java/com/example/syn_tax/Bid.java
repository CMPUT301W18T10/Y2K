package com.example.syn_tax;

/**
 * Created by hamdamare on 2018-02-22.
 */

/**
 * Bid Class
 *
 * Feb 22, 2018
 *
 * keeps track of task providers bids
 *
 * Copyright GNU GENERAL PUBLIC LICENSE
 Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.

 Permissions of this strong copyleft license are conditioned on making available complete
 source code of licensed works and modifications, which include larger works using a licensed work,
 under the same license. Copyright and license notices must be preserved. Contributors provide an
 express grant of patent rights.
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
