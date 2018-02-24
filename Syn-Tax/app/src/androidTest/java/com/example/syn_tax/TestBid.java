package com.example.syn_tax;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by abdulazeezojetola on 2018-02-23.
 */

public class TestBid extends ActivityInstrumentationTestCase2 {
    public TestBid(){
        super(BidsList.class);
    }

    public void testBidStatus(){

        /*
        *  make a new test bid and accept it then check if it's status has changed
        */
        Bid testBid = new Bid(23.00);
        testBid.setBidStatus("Accepted");
        assertEquals("Accepted", testBid.getBidStatus());
        /*
        *   change the status to declined and check if it changed
        */
        testBid.setBidStatus("Declined");
        assertEquals("Declined", testBid.getBidStatus());


        /*
        * Create bids and decline to test for clearing only the declined bids
        * */
        Bid testBid2 = new Bid(20.00);
        testBid2.setBidStatus("Accepted");
        Bid testBid3 = new Bid(33.00);
        testBid3.setBidStatus("Declined");

        /*
        * Create a new test bidList and add all the bids into the list
        * */
        BidsList testBidList = new BidsList();
        testBidList.addBid(testBid);
        assertTrue(testBidList.hasBid(testBid));

        testBidList.addBid(testBid2);
        testBidList.addBid(testBid3);

        /*
        * Clear the list and leave only the accepted Bid. should not have testbid1, or testbid3
        * */
        testBidList.clearBids();
        assertFalse(testBidList.hasBid(testBid));

        // Should have testbid2
        assertTrue(testBidList.hasBid(testBid2));




    }
}
