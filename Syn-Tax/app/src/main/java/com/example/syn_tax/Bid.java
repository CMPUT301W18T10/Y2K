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
 * Last Modified 3/17/18 4:38 PM
 */

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
 */
public class Bid {

    private double bidAmount;
    private String bidUserName;
    private Task task;
    private String id;
    private String taskname;
    private String bidOwner;

    /**
     * Constructor for a bid
     * @param usernameP The person that made the bid
     * @param amount Amount bidded
     * @param bidtask the task the person bidded on
     * @param title title of the task
     */
    public Bid(String usernameP, double amount, Task bidtask, String title, String usernameR) {
        bidUserName=usernameP;
        bidAmount=amount;
        task=bidtask;
        taskname= title;
        bidOwner=usernameR;
    }

    //SETTERS AND GETTERS FOR ID
    /**
     * Set the id of a bid
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the id of a bid
     * @return the id of the bid
     */
    public String getId() {
        return id;
    }


    /**
     * @return the list of tasks
     */
    public Task getTask(){
        return task;
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

    public String getBidOwner(){
            return this.bidOwner;
    }

}
